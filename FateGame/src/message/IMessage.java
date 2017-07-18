package message;

import org.apache.mina.core.buffer.IoBuffer;

import framework.SessHand;

public interface IMessage
{	
	/** 处理收到Server的消息 */
	public void recv(SessHand sess, int subMid, IoBuffer buffer, long clientBundleId);
	
	/** [框架核心]主消息号 */
	public static final int FRAMEWORK_MID = 158000;
	
}
