package message.gm.params;

import message.task.TaskMsg;
import common.component.task.TaskItem;

import game.GameClientBundle;
import game.core.sprite.Player;
import game.task.TaskManager;

public class GmTaskPC
{
	/** 添加任务 */
	private static final int ADD_TASK = 1;
	/** 清空任务 */
	private static final int CLEAR_TASKS = 2;
	
	public static void cmdHandler(int subCmdId, GameClientBundle bundle, byte byte0, byte byte1, 
			int int0, int int1, int int2, float float0, double double0, 
			long long0, long long1, String str0, String str1)
	{
		Player player = bundle.player;
		switch(subCmdId)
		{
		case ADD_TASK:
			H_ADD_TASK(player, int0);
			break;
		case CLEAR_TASKS:
			H_CLEAR_TASKS(player);
			break;
		}
	}
	
	private static void H_ADD_TASK(Player player, int taskId)
	{
		System.out.println(player.name + "添加任务" + taskId);
		TaskManager.addTask(player, taskId, false);
	}
	
	private static void H_CLEAR_TASKS(Player player)
	{
		System.out.println(player.name + "清空任务");
		for(TaskItem t : player.task.items)
		{
			TaskMsg.getInstance().sendRemoveTask_G2C(t.taskId, player.getBundle().getUid());
		}
		player.task.items.clear();
		player.task.finishIds.clear();
	}
}
