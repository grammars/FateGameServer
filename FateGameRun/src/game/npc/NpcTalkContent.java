package game.npc;

import org.apache.mina.core.buffer.IoBuffer;

import framework.net.IBT;

public class NpcTalkContent
{
	/** 对话主内容 */
	public String text = "";
	/** 关联任务id */
	public int taskId = 0;
	/** 是否有可交掉的任务 */
	public boolean hasDoneTask = false;
	/** 是否有可接的任务 */
	public boolean hasAcceptableTask = false;
	/** 是否正在做的任务 */
	public boolean hasDoingTask = false;
	
	public NpcTalkContent()
	{
		//
	}
	
	public void write(IoBuffer buffer)
	{
		IBT.writeInt(buffer, taskId);
		IBT.writeBoolean(buffer, hasDoneTask);
		IBT.writeBoolean(buffer, hasAcceptableTask);
		IBT.writeBoolean(buffer, hasDoingTask);
		IBT.writeString(buffer, text);
	}
	
}
