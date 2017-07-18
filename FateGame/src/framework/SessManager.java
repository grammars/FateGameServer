package framework;

import message.IMessage;

import org.apache.mina.core.buffer.IoBuffer;

public abstract class SessManager
{
	/** 会话打开
	 * @param sh 会话处理  */
	abstract public void sessionOpened(SessHand sh);
	
	/** 收到消息处理
	 * @param sh 会话处理
	 * @param buffer 消息体  */
	abstract public void messageReceived(SessHand sh, IoBuffer buffer);
	
	/** 会话关闭
	 * @param sh 会话处理  */
	abstract public void sessionClosed(SessHand sh);
	
	/** 常规的处理接收到的消息 
	 * @param sh 会话对象
	 * @param buffer 消息体 */
	protected void handleRecv(SessHand sh, IoBuffer buffer)
	{
		int mainMid = buffer.getInt();
		int subMid = buffer.getInt();
		@SuppressWarnings("unused")
		byte target = buffer.get();
		long clientBundleId = buffer.getLong();
		IMessage msgHandler = AppContext.getMsgHandler(mainMid);
		if(msgHandler != null)
		{
			long startTime = System.currentTimeMillis();
			msgHandler.recv(sh, subMid, buffer, clientBundleId);
			long costTime = System.currentTimeMillis() - startTime;
			if(costTime > 100)
			{
				Log.system.debug("消息("+mainMid+":"+subMid+")耗时"+costTime+"ms");
			}
		}
		else
		{
			Log.system.error("缺少对应的消息处理器 mainMid="+mainMid);
		}
	}
	
}
