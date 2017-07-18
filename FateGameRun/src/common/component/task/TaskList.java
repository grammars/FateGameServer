package common.component.task;

import game.core.sprite.Player;

import java.util.ArrayList;
import java.util.List;

import common.struct.task.StTaskItem;
import common.struct.task.StTaskList;

public class TaskList
{
	/** 已完成的任务id们(主线任务的id会被推入其中) */
	public List<Integer> finishIds = new ArrayList<>();
	
	public List<TaskItem> items = new ArrayList<>();
	
	Player owner;
	
	public TaskList(Player owner)
	{
		this.owner = owner;
	}
	
	/** 是否已完成过此主线任务 */
	public boolean hasFinished(int taskId)
	{
		if(finishIds.contains(taskId))
		{
			return true;
		}
		return false;
	}
	
	/** 获取指定id的任务 */
	public TaskItem getTask(int taskId)
	{
		for(TaskItem item : items)
		{
			if(item.taskId == taskId)
			{
				return item;
			}
		}
		return null;
	}
	
	/** 添加一个任务 */
	public void addTask(TaskItem item)
	{
		items.add(item);
	}
	
	/** 移除一个任务 */
	public TaskItem removeTask(int taskId)
	{
		for(int i = items.size()-1; i >= 0; i--)
		{
			TaskItem item = items.get(i);
			if(item.taskId == taskId)
			{
				items.remove(i);
				return item;
			}
		}
		return null;
	}
	
	/** 导入结构数据 */
	public void importData(StTaskList data)
	{
		this.finishIds = data.finishIds;
		items.clear();
		int num = data.items.size();
		for(int i = 0; i < num; i++)
		{
			StTaskItem st = data.items.get(i);
			TaskItem item = new TaskItem();
			item.importData(st);
			items.add(item);
		}
	}
	
	/** 导出结构数据 */
	public StTaskList exportData()
	{
		StTaskList data = new StTaskList();
		data.finishIds = this.finishIds;
		int num = items.size();
		for(int i = 0; i < num; i++)
		{
			TaskItem item = items.get(i);
			StTaskItem st = item.exportData();
			data.items.add(st);
		}
		return data;
	}
	
}
