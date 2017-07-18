package game.npc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import message.npc.NpcMsg;
import cfg.TaskCfg;
import common.component.task.TaskItem;
import common.struct.task.StTaskItem;
import game.core.sprite.Npc;
import game.core.sprite.Player;
import game.task.TaskManager;

public class NpcManager
{
	private static Map<Integer, Npc> npcs = new HashMap<>();
	/** 注册一个npc */
	public static void regNpc(int npcCfgId, Npc npc)
	{
		npcs.put(npcCfgId, npc);
	}
	/** 根据npc模版id获得一个npc */
	public static Npc getNpc(int npcCfgId) { return npcs.get(npcCfgId); }
	
	/** 游戏启动时候，当场景中创建了npc之后 */
	public static void start()
	{
		mountTasks();
	}
	
	/** 往对应npc身上挂载相关任务 */
	private static void mountTasks()
	{
		Iterator<TaskCfg> iter = TaskCfg.getAll().iterator();
		while(iter.hasNext())
		{
			TaskCfg tc = iter.next();
			Npc begNpc = getNpc(tc.begNpc);
			if(begNpc != null) begNpc.begTasks.add(tc);
			Npc endNpc = getNpc(tc.endNpc);
			if(endNpc != null) endNpc.endTasks.add(tc);
		}
	}
	
	/** 与npc对话 */
	public static void talkWithNpc(Player player, int npcCfgId)
	{
		Npc npc = getNpc(npcCfgId);
		if(npc == null)
		{
			player.alert("根本不存在这个NPC，你在逗我吗？");
			return;
		}
		
		TaskManager.handleTalkNpc(player, npc.getConfig().id);
		
		NpcTalkContent content = new NpcTalkContent();
		handleTalkForTask(content, player, npc);
		if(content.hasDoneTask==false&&content.hasAcceptableTask==false&&content.hasDoingTask==false)
		{
			content.text = npc.getConfig().talkContent;
		}
		NpcMsg.getInstance().sendNpcTalkContent_G2C(content, player.getBundle().getUid());
	}
	
	/** 处理任务部分的对话内容 */
	private static void handleTalkForTask(NpcTalkContent content, Player player, Npc npc)
	{
		checkDoneTask(content, player, npc);
		if(!content.hasDoneTask)
		{
			checkAcceptableTask(content, player, npc);
			if(!content.hasAcceptableTask)
			{
				checkDoingTask(content, player, npc);
			}
		}
	}
	
	/** 检查是否有可交掉的任务 */
	private static boolean checkDoneTask(NpcTalkContent content, Player player, Npc npc)
	{
		for(TaskCfg cfg : npc.endTasks)
		{
			for(TaskItem task : player.task.items)
			{
				if(task.taskId == cfg.id && task.state==StTaskItem.ST_DONE)
				{
					content.taskId = cfg.id;
					content.hasDoneTask = true;
					content.text = cfg.taskDoneTalk;
					return true;
				}
			}
		}
		return false;
	}
	
	/** 检查是否有可接任务 */
	private static boolean checkAcceptableTask(NpcTalkContent content, Player player, Npc npc)
	{
		for(TaskCfg cfg : npc.begTasks)
		{
			if( TaskCfg.AP_OK != cfg.availableFor(player) ) { continue; }
			boolean hasGotTask = false;
			for(TaskItem task : player.task.items)
			{
				if(task.taskId == cfg.id)
				{
					hasGotTask = true;
					break;
				}
			}
			if(hasGotTask) { continue; }
			content.taskId = cfg.id;
			content.hasAcceptableTask = true;
			if(content.hasAcceptableTask)
			{
				content.text = cfg.taskAcceptTalk;
				return true;
			}
		}
		return false;
	}
	
	/** 检查是否有正在做的任务 */
	private static boolean checkDoingTask(NpcTalkContent content, Player player, Npc npc)
	{
		for(TaskCfg cfg : npc.endTasks)
		{
			for(TaskItem task : player.task.items)
			{
				if(task.taskId == cfg.id && task.state==StTaskItem.ST_ING)
				{
					content.taskId = cfg.id;
					content.hasDoingTask = true;
					content.text = cfg.taskDoingTalk;
					return true;
				}
			}
		}
		return false;
	}
	
}
