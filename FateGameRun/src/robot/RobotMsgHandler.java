package robot;

import message.player.PlayerEventMsg;
import message.robot.RobotMsg;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import framework.net.IBT;

public class RobotMsgHandler implements IoHandler
{
	public RobotClient rc;
	
	public RobotMsgHandler(RobotClient rc)
	{
		this.rc = rc;
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionOpened(IoSession session) throws Exception
	{
		//System.out.println("robot:sessionOpened");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception
	{
		//System.out.println("robot:sessionClosed");
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
		System.out.println("robot:exceptionCaught");
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception
	{
		System.out.println("robot:messageReceived");
		IoBuffer buffer = (IoBuffer)message;
		int mainMid = buffer.getInt();
		int subMid = buffer.getInt();
		byte target = buffer.get();
		long clientBundleId = buffer.getLong();
		System.out.println("mainMid="+mainMid+" subMid="+subMid+" target="+target+" clientBundleId="+clientBundleId);
		//String res = IBT.readString(buffer);
		//System.out.println("res=" + res);
		if(mainMid==PlayerEventMsg.MID && subMid==PlayerEventMsg.PLAYER_LOGIN_GATE_RPL_G2C)
		{
			RobotMsg.getInstance().sendCreateRobotReq_R2G(rc.sh, rc.clientUid);
		}
		else if(mainMid==RobotMsg.MID && subMid==RobotMsg.CREATE_ROBOT_RPL_G2R)
		{
			String res = IBT.readString(buffer);
			System.out.println("res=" + res);
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception
	{
		// TODO Auto-generated method stub

	}

}
