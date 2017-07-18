package cfg;

import game.core.sprite.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import utils.StrParser;
import common.Macro;
import ds.Pair;

public class TaskCfg
{
	public static final String TYPE = "task";
	
	private static HashMap<Integer, TaskCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static TaskCfg get(int id)
	{
		return cfgs.get(id);
	}
	/** 获得全部的TaskCfg */
	public static Collection<TaskCfg> getAll()
	{
		return cfgs.values();
	}
	
	/** 无意义的任务事务 */
	public static final byte ACT_NULL = 0;
	/** 跟NPC对话的任务事务 */
	public static final byte ACT_TALK_NPC = 1;
	/** 杀怪物的任务事务 */
	public static final byte ACT_KILL_MONSTER = 2;
	/** 收集物品的任务事务 */
	public static final byte ACT_COLLECT = 3;
	/** 通过副本的任务事务 */
	public static final byte ACT_PASS_ZONE = 4;
	
	/** id */
	public int id = 0;
	/** 事务类型 */
	public byte actType = 0;
	/** 任务名称 */
	public String name = "";
	/** 任务描述 */
	public String desc = "";
	/** 等级要求下限 */
	public int levMinReq = 0;
	/** 等级要求上限 */
	public int levMaxReq = 0;
	/** 前置任务 */
	public int prevTaskId = -1;
	/** 下一个任务 */
	public int nextTaskId = -1;
	/** 接任务npc */
	public int begNpc = 0;
	/** 交任务npc */
	public int endNpc = 0;
	/** 目标npcId */
	public int tarNpcId = 0;
	/** 目标怪物id */
	public int tarMonsterId = 0;
	/** 目标怪物数量 */
	public int tarMonsterNum = 0;
	/** 目标物品id */
	public int tarGoodsId = 0;
	/** 目标物品数量 */
	public int tarGoodsNum = 0;
	/** 目标副本id */
	public int tarZoneId = 0;
	/** 奖励经验 */
	public int rwdExp = 0;
	/** 奖励铜钱 */
	public int rwdCoins = 0;
	/** 奖励绑定铜钱 */
	public int rwdBindCoins = 0;
	/** 奖励元宝 */
	public int rwdIngot = 0;
	/** 奖励绑定元宝 */
	public int rwdBindIngot = 0;
	/** 奖励物品 */
	public String rwdGoodsStr = "";//形如9100341:2#9100342:13
	/** 接任务对话 */
	public String taskAcceptTalk = "";
	/** 任务中对话 */
	public String taskDoingTalk = "";
	/** 任务完成对话 */
	public String taskDoneTalk = "";
	
	/** 奖励物品 */
	public List<Pair<Integer, Integer>> rwdGoodsList;
	
	
	/** 玩家符合任务需求 */
	public static final byte AP_OK = 0;
	/** 玩家等级太低 */
	public static final byte AP_LEV_TOO_LOW = 1;
	/** 玩家等级太高 */
	public static final byte AP_LEV_TOO_HIGH = 2;
	/** 玩家已完成该任务，不能再接这个任务了 */
	public static final byte AP_HAS_FINISHED = 3;
	/** 没有完成前置任务，不可接这个任务 */
	public static final byte AP_PRE_UNDO = 4;
	/** 判断该玩家是否可以接取这个任务 */
	public byte availableFor(Player player)
	{
		if(this.levMinReq > 0 && this.levMinReq > player.level) { return AP_LEV_TOO_LOW; }
		if(this.levMaxReq > 0 && this.levMaxReq <= player.level) { return AP_LEV_TOO_HIGH; }
		if(player.task.hasFinished(this.id)) { return AP_HAS_FINISHED; }
		if(this.prevTaskId > 0 && false==player.task.hasFinished(this.prevTaskId)) { return AP_PRE_UNDO; }
		return AP_OK;
	}
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			TaskCfg c = new TaskCfg();
			c.id = Integer.parseInt( dataDic.get("id")[i] );
			c.actType = Byte.parseByte( dataDic.get("事务类型")[i] );
			c.name = dataDic.get("任务名称")[i];
			c.desc = dataDic.get("任务描述")[i];
			
			c.levMinReq = Integer.parseInt( dataDic.get("等级要求下限")[i] );
			c.levMaxReq = Integer.parseInt( dataDic.get("等级要求上限")[i] );
			c.prevTaskId = Integer.parseInt( dataDic.get("前置任务")[i] );
			c.nextTaskId = Integer.parseInt( dataDic.get("下一个任务")[i] );
			c.begNpc = Integer.parseInt( dataDic.get("接任务npc")[i] );
			c.endNpc = Integer.parseInt( dataDic.get("交任务npc")[i] );
			
			c.tarNpcId = Integer.parseInt( dataDic.get("目标npcId")[i] ); 
			c.tarMonsterId = Integer.parseInt( dataDic.get("目标怪物id")[i] );
			c.tarMonsterNum = Integer.parseInt( dataDic.get("目标怪物数量")[i] );
			c.tarGoodsId = Integer.parseInt( dataDic.get("目标物品id")[i] );
			c.tarGoodsNum = Integer.parseInt( dataDic.get("目标物品数量")[i] );
			c.tarZoneId = Integer.parseInt( dataDic.get("目标副本id")[i] );
			
			c.rwdExp = Integer.parseInt( dataDic.get("奖励经验")[i] );
			c.rwdCoins = Integer.parseInt( dataDic.get("奖励铜钱")[i] );
			c.rwdBindCoins = Integer.parseInt( dataDic.get("奖励绑定铜钱")[i] );
			c.rwdIngot = Integer.parseInt( dataDic.get("奖励元宝")[i] );
			c.rwdBindIngot = Integer.parseInt( dataDic.get("奖励绑定元宝")[i] );
			c.rwdGoodsStr = dataDic.get("奖励物品")[i];
			
			c.taskAcceptTalk = dataDic.get("接任务对话")[i];
			c.taskDoingTalk = dataDic.get("任务中对话")[i];
			c.taskDoneTalk = dataDic.get("任务完成对话")[i];
			
			c.digest();
			System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	private void digest()
	{
		if(!Macro.isNull(rwdGoodsStr))
		{
			rwdGoodsList = StrParser.toIntIntPair(rwdGoodsStr);
		}
	}
	
	@Override
	public String toString()
	{
		return "[TaskCfg] id:" + id + " name:" + name + " desc:" + desc
				+ " nextTaskId:" + nextTaskId + " tarNpcId:" + tarNpcId
				+ " tarMonsterId:" + tarMonsterId + " tarMonsterNum:" + tarMonsterNum
				+ " tarGoodsId:" + tarGoodsId + " tarGoodsNum:" + tarGoodsNum
				+ " tarZoneId:" + tarZoneId + " rwdExp:" + rwdExp
				+ " rwdCoins:" + rwdCoins + " rwdBindCoins:" + rwdBindCoins
				+ " rwdIngot:" + rwdIngot + " rwdBindIngot:" + rwdBindIngot
				+ " rwdGoodsStr:" + rwdGoodsStr;
	}
}
