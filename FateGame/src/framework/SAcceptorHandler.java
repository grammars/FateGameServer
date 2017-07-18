package framework;

import java.io.IOException;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import framework.define.SidType;

public class SAcceptorHandler implements IoHandler
{

	@Override
	public void sessionCreated(IoSession session) throws Exception
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception
	{
		Log.system.debug("SAcceptorHandler::sessionOpened id=" + session.getId() + " " + session.getRemoteAddress());
		
		SessHand sh = new SessHand(session);
		sh.attachSession();
		if(AppContext.getSidType() == SidType.LOGIN || AppContext.getSidType() == SidType.GATE)
		{
			sh.isClient = true;
		}
		
		SessManager sessManager = AppContext.getSessManager();
		if(sessManager != null)
		{
			sessManager.sessionOpened(sh);
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception
	{
		Log.system.debug("SAcceptorHandler::sessionClosed id=" + session.getId() + " " + session.getRemoteAddress());
		SessHand sh = (SessHand) session.removeAttribute(SessAttriKey.SESS_HAND);
		if(sh != null)
		{
			SessManager sessManager = AppContext.getSessManager();
			if(sessManager != null)
			{
				sessManager.sessionClosed(sh);
			}
			sh.disAttachSession();
		}
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception
	{
		Log.system.debug("SAcceptorHandler::exceptionCaught");
		if(cause instanceof IOException)
		{
			//正常关闭链接会触发的异常
		}
		else
		{
			cause.printStackTrace();
		}
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception
	{
		IoBuffer buffer = (IoBuffer)message;
		SessHand sh = (SessHand) session.getAttribute(SessAttriKey.SESS_HAND);
		
		SessManager sessManager = AppContext.getSessManager();
		if(sessManager != null)
		{
			sessManager.messageReceived(sh, buffer);
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception
	{
		// TODO Auto-generated method stub
	}

}
