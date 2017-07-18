package game.task;

import cfg.TaskCfg;
import message.task.TaskMsg;
import common.component.goods.GoodsFactory;
import common.component.goods.GoodsInfo;
import common.component.goods.GoodsOperEnum;
import common.component.task.TaskItem;
import common.struct.task.StTaskItem;
import ds.Pair;
import game.core.sprite.Monster;
import game.core.sprite.Player;

public class TaskManager
{
	/** 给玩家增加任务 */
	public static void addTask(Player player, int taskId, boolean checkAvailable)
	{
		TaskItem item = new TaskItem();
		item.setup(taskId);
		TaskCfg cfg = item.config;
		if(cfg.actType == TaskCfg.ACT_COLLECT)
		{
			item.gotGoodsNum = player.bag.getTotalNum(cfg.tarGoodsId);
			if(item.gotGoodsNum >= cfg.tarGoodsNum)
			{
				item.state = TaskItem.ST_DONE;
			}
		}
		
		if(checkAvailable)
		{
			if( TaskCfg.AP_OK != cfg.availableFor(player) )
			{
				player.alert("不符合接此任务的条件");
				return;
			}
		}
		
		player.task.addTask(item);
		TaskMsg.getInstance().sendAddTask_G2C(item.exportData(), player.getBundle().getUid());
	}
	
	/** 给玩家删除任务 */
	public static void removeTask(Player player, int taskId)
	{
		player.task.removeTask(taskId);
		TaskMsg.getInstance().sendRemoveTask_G2C(taskId, player.getBundle().getUid());
	}
	
	/** 玩家请求接受任务 */
	public static void playerAcceptTask(Player player, int taskId)
	{
		addTask(player, taskId, true);
	}
	
	/** 玩家请求完成任务 */
	public static void playerFinishTask(Player player, int taskId)
	{
		TaskItem item = player.task.getTask(taskId);
		if(item == null)
		{
			player.alert("你没有接到这个任务，谈何完成任务？你是作弊的吧？");
			return;
		}
		final byte EC_SUCC = 0;//成功完成任务
		final byte EC_FAIL = 1;//这个任务还没有完成
		byte errCode = 0;
		if(item.state == StTaskItem.ST_DONE)
		{
			if(item.config.actType == TaskCfg.ACT_COLLECT)
			{
				GoodsOperEnum oper = player.bag.deductItem(item.config.tarGoodsId, item.config.tarGoodsNum);
				if(oper != GoodsOperEnum.SUCC)
				{
					player.alert("你的任务物品还不够，请继续收集！");
					errCode = EC_FAIL;
					TaskMsg.getInstance().sendFinishTaskRpl_G2C(errCode, player.getBundle().getUid());
					return;
				}
			}
			removeTask(player, taskId);
			//如果是主线任务就添加到finishIds中去
			player.task.finishIds.add(taskId);
			//并发放物品奖励
			for(Pair<Integer, Integer> rwd : item.config.rwdGoodsList)
			{
				GoodsInfo gi = GoodsFactory.createInfo(rwd.key);
				gi.num = rwd.value;
				player.bag.addItem(gi);
			}
			//发送数值奖励
			
			errCode = EC_SUCC;
		}
		else
		{
			errCode = EC_FAIL;
		}
		TaskMsg.getInstance().sendFinishTaskRpl_G2C(errCode, player.getBundle().getUid());
		
		if(item.config.nextTaskId > 0)
		{
			addTask(player, item.config.nextTaskId, true);
		}
	}
	
	/** 处理跟npc对话 */
	public static void handleTalkNpc(Player player, int npcId)
	{
		for(TaskItem task : player.task.items)
		{
			if(task.config.actType != TaskCfg.ACT_TALK_NPC) { continue; }
			if(task.config.tarNpcId == npcId)
			{
				task.state = StTaskItem.ST_DONE;
				task.talkedNpc = true;
				TaskMsg.getInstance().sendUpdateTask_G2C(task.exportData(), player.getBundle().getUid());
				break;
			}
		}
	}
	
	/** 处理杀怪 */
	public static void handleKillMonster(Player player, Monster monster)
	{
		for(TaskItem task : player.task.items)
		{
			if(task.config.actType != TaskCfg.ACT_KILL_MONSTER) { continue; }
			if(task.config.tarMonsterId == monster.getConfig().id)
			{
				if(task.killMonsterNum >= task.config.tarMonsterNum)
				{
					continue;
				}
				task.killMonsterNum++;
				if(task.killMonsterNum >= task.config.tarMonsterNum)
				{
					task.state = StTaskItem.ST_DONE;
				}
				TaskMsg.getInstance().sendUpdateTask_G2C(task.exportData(), player.getBundle().getUid());
				break;
			}
		}
	}
	
	/** 处理收集物品 */
	public static void handleCollect(Player player, GoodsInfo item)
	{
		for(TaskItem task : player.task.items)
		{
			if(task.config.actType != TaskCfg.ACT_COLLECT) { continue; }
			if(task.config.tarGoodsId == item.baseCfgId)
			{
				int numInBag = player.bag.getTotalNum(task.config.tarGoodsId);
				if(numInBag != task.gotGoodsNum)
				{
					task.gotGoodsNum = numInBag;
					if(task.gotGoodsNum >= task.config.tarGoodsNum)
					{
						task.state = StTaskItem.ST_DONE;
					}
					TaskMsg.getInstance().sendUpdateTask_G2C(task.exportData(), player.getBundle().getUid());
				}
				break;
			}
		}
	}
	
	/** 处理副本 */
	public static void handlePassZone(Player player, int zoneId)
	{
		for(TaskItem task : player.task.items)
		{
			if(task.config.actType != TaskCfg.ACT_PASS_ZONE) { return; }
			if(task.config.tarZoneId == zoneId)
			{
				task.passZone = true;
				task.state = StTaskItem.ST_DONE;
				TaskMsg.getInstance().sendUpdateTask_G2C(task.exportData(), player.getBundle().getUid());
			}
		}
	}
	
}
