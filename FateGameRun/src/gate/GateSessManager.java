package gate;

import org.apache.mina.core.buffer.IoBuffer;

import framework.AppContext;
import framework.ClientBundleManager;
import framework.MsgBuffer;
import framework.SessManager;
import framework.SessHand;
import framework.define.SidType;
import message.IMessage;
import message.player.PlayerEventMsg;

public class GateSessManager extends SessManager
{
	private static GateSessManager instance;
	public static GateSessManager getInstance()
	{
		if(instance == null) { instance = new GateSessManager(); }
		return instance;
	}
	
	private GateSessManager() {}
	
	@Override
	public void sessionOpened(SessHand sess)
	{
	}
	
	@Override
	public void sessionClosed(SessHand sess)
	{
		GateClientBundle bundle = (GateClientBundle) ClientBundleManager.getInstance().getBundle(sess);
		if(bundle == null)
		{
			System.err.println("网关服丢失bundle");
			return;
		}
		System.err.println("网关服：客户端下线bundle.uid=" + bundle.getUid());
		PlayerEventMsg.getInstance().sendPlayerOfflineReq_G2G(bundle.getUid());
		bundle.dispose();
	}
	
	@Override
	public void messageReceived(SessHand sess, IoBuffer buffer)
	{
		if(buffer.remaining() < 16)
		{
			return;
		}
		int mainMid = buffer.getInt();
		int subMid = buffer.getInt();
		byte target = buffer.get();
		long clientBundleId = buffer.getLong();
		if(target == SidType.GATE || target == SidType.SERVER)
		{
			IMessage msgHandler = AppContext.getMsgHandler(mainMid);
			if(msgHandler != null)
			{
				msgHandler.recv(sess, subMid, buffer, clientBundleId);
			}
		}
		else if(target == SidType.GAME)
		{
			buffer.putLong(MsgBuffer.ClientBundleIdIndex, clientBundleId);
			buffer.position(0);
			AppContext.sendMsg(SidType.GAME, MsgBuffer.addMsgSize(buffer));
		}
		else if(target == SidType.CLIENT)
		{
			GateClientBundle bundle = (GateClientBundle) ClientBundleManager.getInstance().getBundle(clientBundleId);
			if(bundle != null)
			{
				bundle.getClientSess().send(MsgBuffer.addMsgSize(buffer));
				//System.err.println("向客户端("+clientBundleId+")"+"发送了消息:(" + mainMid + "_" + subMid + ")");
			}
			else
			{
				System.err.println("试图向客户端发送消息("+mainMid+"_"+subMid+"),但是clientBundleId=" + clientBundleId + "已丢失");
			}
		}
	}

}
