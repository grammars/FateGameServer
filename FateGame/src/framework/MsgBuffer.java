package framework;

import org.apache.mina.core.buffer.IoBuffer;

import framework.define.SidType;
import framework.net.IBT;

public class MsgBuffer
{
	public IoBuffer buffer;
	/** [SidType]发送对象的会话类型 */
	public byte sendTarget;
	
	/** clientBundleId位于消息体的位置 */
	public static final int ClientBundleIdIndex = 9;
	
	/** 服务器间通讯
	 * @param target 发送目标会话类型[SidType]
	 * @param clientBundleId 客户端关联id */
	public MsgBuffer(int mainMid, int subMid, byte target, long clientBundleId)
	{
		initBuffer(mainMid, subMid, target, clientBundleId);
	}
	
	/** 直接发给客户端[非经过Gate]{Login和Gate通常使用这个} */
	public MsgBuffer(int mainMid, int subMid)
	{
		initBuffer(mainMid, subMid, SidType.CLIENT, 0);
	}
	
	protected void initBuffer(int mainMid, int subMid, byte target, long clientBundleId)
	{
		this.sendTarget = target;
		buffer = IoBuffer.allocate(0).setAutoExpand(true);
		IBT.writeInt(buffer, mainMid);
		IBT.writeInt(buffer, subMid);
		IBT.writeByte(buffer, target);
		IBT.writeLong(buffer, clientBundleId);
	}
	
	public IoBuffer createMsg()
	{
		buffer.flip();
		return addMsgSize(buffer);
	}
	
	public void send()
	{
		AppContext.sendMsg(this);
	}
	
	public static IoBuffer addMsgSize(IoBuffer buffer)
	{
		final int msgSize = buffer.limit();
		//Log.system.debug("MsgBuffer::addMsgSize() msgSize=>" + msgSize);
		IoBuffer msg = IoBuffer.allocate(msgSize+4);
		IBT.writeInt(msg, msgSize);
		byte[] bytes = new byte[msgSize];
		System.arraycopy(buffer.array(), 0, bytes, 0, msgSize);
		msg.put(bytes);
		msg.flip();
		return msg;
	}
	
}
