package common.component.task;

import cfg.TaskCfg;
import common.struct.task.StTaskItem;

public class TaskItem extends StTaskItem
{
	/** 任务模版配置 */
	public TaskCfg config;
	
	public TaskItem()
	{
		//
	}
	
	public void setup(int taskId)
	{
		this.taskId = taskId;
		this.state = ST_ING;
		build();
	}
	
	private void build()
	{
		config = TaskCfg.get(this.taskId);
	}
	
	/** 导入结构数据 */
	public void importData(StTaskItem data)
	{
		this.taskId = data.taskId;
		this.state = data.state;
		this.talkedNpc = data.talkedNpc;
		this.killMonsterNum = data.killMonsterNum;
		this.gotGoodsNum = data.gotGoodsNum;
		this.passZone = data.passZone;
		build();
	}
	
	/** 导出结构数据 */
	public StTaskItem exportData()
	{
		StTaskItem data = new StTaskItem();
		data.taskId = this.taskId;
		data.state = this.state;
		data.talkedNpc = this.talkedNpc;
		data.killMonsterNum = this.killMonsterNum;
		data.gotGoodsNum = this.gotGoodsNum;
		data.passZone = this.passZone;
		return data;
	}
	
}
