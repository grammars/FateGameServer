package framework;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

/** 包含IoSession的会话 */
public class SessHand
{
	private IoSession session;
	public IoSession getSession() { return session; }
	
	public boolean connected = false;
	
	public boolean isClient = false;
	
	@SuppressWarnings("unused")
	private SessHand() {}
	
	public SessHand(IoSession session)
	{
		this.session = session;
	}
	
	public void attachSession()
	{
		this.session.setAttribute(SessAttriKey.SESS_HAND, this);
		this.connected = true;
	}
	
	public void disAttachSession()
	{
		if(this.session != null)
		{
			this.session.removeAttribute(SessAttriKey.SESS_HAND);
			this.session = null;
		}
		this.connected = false;
	}
	
	/** 发送消息 */
	public void send(MsgBuffer msg)
	{
		send(msg.createMsg());
	}
	
	/** 发送消息 */
	public void send(IoBuffer buffer)
	{
		if(this.connected)
		{
			this.session.write(buffer);
		}
		else
		{
			Log.system.error("SessHand::send失败,connected=false");
		}
	}
	
	/** 关闭会话并释放 */
	public void dispose()
	{
		if(this.session != null && this.session.isConnected())
		{
			this.session.close(false);
		}
		disAttachSession();
	}

}
