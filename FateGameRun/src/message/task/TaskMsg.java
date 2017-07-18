package message.task;

import org.apache.mina.core.buffer.IoBuffer;

import common.struct.task.StTaskItem;
import common.struct.task.StTaskList;
import framework.ClientBundleManager;
import framework.MsgBuffer;
import framework.SessHand;
import framework.define.SidType;
import framework.net.IBT;
import game.GameClientBundle;
import game.core.sprite.Player;
import game.task.TaskManager;
import message.IMessage;
import message.MessageId;

public class TaskMsg implements IMessage
{
	private static TaskMsg instance;
	public static TaskMsg getInstance()
	{
		if(instance == null) { instance = new TaskMsg(); }
		return instance;
	}
	
	/** [任务]主消息号 */
	public static final int MID = MessageId.TASK_MID;

	/** game向client发送初始化任务列表 */
	private static final int INIT_TASK_LIST_G2C = 1;
	/** game向client发送新增任务 */
	private static final int ADD_TASK_G2C = 2;
	/** game向client发送删除任务 */
	private static final int REMOVE_TASK_G2C = 3;
	/** game向client发送更新任务 */
	private static final int UPDATE_TASK_G2C = 4;
	
	/** client向game请求接受任务 */
	private static final int ACCEPT_TASK_REQ_C2G = 11;
	/** game向client发送接受任务的请求返回结果 */
	private static final int ACCEPT_TASK_RPL_G2C = 13;
	/** client向game请求完成任务 */
	private static final int FINISH_TASK_REQ_C2G = 12;
	/** game向client发送完成任务的请求返回结果 */
	private static final int FINISH_TASK_RPL_G2C = 14;

	@Override
	public void recv(SessHand sess, int subMid, IoBuffer buffer, long clientBundleId)
	{
		GameClientBundle bundle = (GameClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle == null) { return; }
		switch(subMid)
		{
		case ACCEPT_TASK_REQ_C2G:
			recvAcceptTaskReq_C2G(buffer, bundle.player);
			break;
		case FINISH_TASK_REQ_C2G:
			recvFinishTaskReq_C2G(buffer, bundle.player);
			break;
		}
	}
	
	/** send( game向client发送初始化任务列表 ) */
	public void sendInitTaskList_G2C(StTaskList data, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, INIT_TASK_LIST_G2C, SidType.CLIENT, clientBundleId);
		data.write(msg.buffer);
		msg.send();
	}

	/** send( game向client发送新增任务 ) */
	public void sendAddTask_G2C(StTaskItem item, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, ADD_TASK_G2C, SidType.CLIENT, clientBundleId);
		item.write(msg.buffer);
		msg.send();
	}
	
	/** send( game向client发送删除任务 ) */
	public void sendRemoveTask_G2C(int taskId, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, REMOVE_TASK_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, taskId);
		msg.send();
	}
	
	/** send( game向client发送更新任务 ) */
	public void sendUpdateTask_G2C(StTaskItem item, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, UPDATE_TASK_G2C, SidType.CLIENT, clientBundleId);
		item.write(msg.buffer);
		msg.send();
	}

	/** recv( client向game请求接受任务 ) */
	private void recvAcceptTaskReq_C2G(IoBuffer buffer, Player player)
	{
		int taskId = IBT.readInt(buffer);
		TaskManager.playerAcceptTask(player, taskId);
	}
	
	/** send( game向client发送接受任务的请求返回结果 ) */
	public void sendAcceptTaskRpl_G2C(byte errCode, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, ACCEPT_TASK_RPL_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeByte(msg, errCode);
		msg.send();
	}
	
	/** recv( client向game请求完成任务 ) */
	private void recvFinishTaskReq_C2G(IoBuffer buffer, Player player)
	{
		int taskId = IBT.readInt(buffer);
		TaskManager.playerFinishTask(player, taskId);
	}
	
	/** send( game向client发送完成任务的请求返回结果 ) */
	public void sendFinishTaskRpl_G2C(byte errCode, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, FINISH_TASK_RPL_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeByte(msg, errCode);
		msg.send();
	}
	
}
