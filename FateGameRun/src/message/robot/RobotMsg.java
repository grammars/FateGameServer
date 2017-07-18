package message.robot;

import org.apache.mina.core.buffer.IoBuffer;

import framework.MsgBuffer;
import framework.SessHand;
import framework.define.SidType;
import framework.net.IBT;
import game.robot.RobotManager;
import message.IMessage;
import message.MessageId;
import message.login.LoginMsg;

public class RobotMsg implements IMessage
{
	private static RobotMsg instance;
	public static RobotMsg getInstance()
	{
		if(instance == null) { instance = new RobotMsg(); }
		return instance;
	}
	
	/** [机器人]主消息号 */
	public static final int MID = MessageId.ROBOT_MID;
	
	/** AppRobot向Game请求创建一个机器人玩家 */
	private static final int CREATE_ROBOT_REQ_R2G = 1;
	/** Game向AppRobot返回机器人创建结果 */
	public static final int CREATE_ROBOT_RPL_G2R = 2;
	
	@Override
	public void recv(SessHand sess, int subMid, IoBuffer buffer, long clientBundleId)
	{
		switch(subMid)
		{
		case CREATE_ROBOT_REQ_R2G:
			recvCreateRobotReq_R2G(sess, buffer, clientBundleId);
			break;
		}
	}
	
	/** send( AppRobot向Game请求创建一个机器人玩家 ) */
	public void sendCreateRobotReq_R2G(SessHand rsh, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CREATE_ROBOT_REQ_R2G, SidType.GAME, clientBundleId);
		rsh.send(msg);
	}
	
	/** recv( AppRobot向Game请求创建一个机器人玩家 ) */
	private void recvCreateRobotReq_R2G(SessHand rsh, IoBuffer buffer, long clientBundleId)
	{
		System.out.println("Game收到AppRobot向Game请求创建一个机器人玩家"+clientBundleId);
		RobotManager.createRobot(clientBundleId);
		sendCreateRobotRpl_G2R(clientBundleId);
	}
	
	/** send( Game向AppRobot返回机器人创建结果 ) */
	private void sendCreateRobotRpl_G2R(long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CREATE_ROBOT_RPL_G2R, SidType.CLIENT, clientBundleId);
		IBT.writeString(msg, "创建机器人成功");
		msg.send();
	}

}
