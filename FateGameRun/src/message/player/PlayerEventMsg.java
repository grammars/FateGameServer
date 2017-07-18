package message.player;

import org.apache.mina.core.buffer.IoBuffer;

import framework.ClientBundleManager;
import framework.MsgBuffer;
import framework.SessHand;
import framework.define.SidType;
import game.GameClientBundle;
import gate.GateClientBundle;
import message.IMessage;
import message.MessageId;

public class PlayerEventMsg implements IMessage
{
	private static PlayerEventMsg instance;
	public static PlayerEventMsg getInstance()
	{
		if(instance == null) { instance = new PlayerEventMsg(); }
		return instance;
	}
	
	/** [玩家事件]主消息号 */
	public static final int MID = MessageId.PLAYER_EVENT_MID;
	
	/** client向gate发送玩家登录网关消息  */
	public static final int PLAYER_LOGIN_GATE_REQ_C2G = 1;
	/** gate向client回复玩家登录网关成功消息  */
	public static final int PLAYER_LOGIN_GATE_RPL_G2C = 2;
	/** gate向game发送玩家下线消息  */
	private static final int PLAYER_OFFLINE_REQ_G2G = 3;
	/** game向gate要求断开玩家连接消息 */
	private static final int PLAYER_KICKLINE_REQ_G2G = 4;
	

	@Override
	public void recv(SessHand sess, int subMid, IoBuffer buffer, long clientBundleId)
	{
		switch(subMid)
		{
		case PLAYER_LOGIN_GATE_REQ_C2G:
			recvPlayerLoginGateReq_C2G(sess, buffer, clientBundleId);
			break;
		case PLAYER_OFFLINE_REQ_G2G:
			recvPlayerOfflineReq_G2G(sess, buffer, clientBundleId);
			break;
		case PLAYER_KICKLINE_REQ_G2G:
			recvPlayerKicklineReq_G2G(sess, buffer, clientBundleId);
			break;
		}
	}
	
	/** recv( client向gate发送玩家登录网关消息 ) */
	private void recvPlayerLoginGateReq_C2G(SessHand clientSess, IoBuffer buffer, long clientBundleId)
	{
		GateClientBundle bundle = new GateClientBundle(clientBundleId, clientSess);
		sendPlayerLoginGateRpl_G2C(bundle);
	}
	/** send( gate向client回复玩家登录网关成功消息 ) */
	public void sendPlayerLoginGateRpl_G2C(GateClientBundle bundle)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_LOGIN_GATE_RPL_G2C);
		bundle.getClientSess().send(msg);
	}
	
	/** send( gate向game发送玩家下线消息 ) */
	public void sendPlayerOfflineReq_G2G(long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_OFFLINE_REQ_G2G, SidType.GAME, clientBundleId);
		msg.send();
	}
	/** recv( gate向game发送玩家下线消息 ) */
	private void recvPlayerOfflineReq_G2G(SessHand serverSH, IoBuffer buffer, long clientBundleId)
	{
		GameClientBundle bundle = (GameClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle != null)
		{
			System.err.println("GameSrv将玩家" + bundle.player.name + "下线");
			bundle.dispose();
		}
		else
		{
			System.err.println("GameSrv找不到bundle id=" + clientBundleId);
		}
	}
	
	/** send( game向gate要求断开玩家连接消息 ) */
	public void sendPlayerKicklineReq_G2G(long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_KICKLINE_REQ_G2G, SidType.GATE, clientBundleId);
		msg.send();
	}
	
	/** recv( game向gate要求断开玩家连接消息 ) */
	private void recvPlayerKicklineReq_G2G(SessHand serverSH, IoBuffer buffer, long clientBundleId)
	{
		GateClientBundle bundle = (GateClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle != null)
		{
			bundle.dispose();
		}
	}
	
}
