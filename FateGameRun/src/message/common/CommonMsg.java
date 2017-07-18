package message.common;

import org.apache.mina.core.buffer.IoBuffer;

import common.struct.buff.StBuff;
import framework.ClientBundleManager;
import framework.MsgBuffer;
import framework.SessHand;
import framework.define.SidType;
import framework.net.IBT;
import game.GameClientBundle;
import message.IMessage;
import message.MessageId;
import message.buff.BuffMsg;

public class CommonMsg implements IMessage
{
	private static CommonMsg instance;
	public static CommonMsg getInstance()
	{
		if(instance == null) { instance = new CommonMsg(); }
		return instance;
	}
	/** [通用]主消息号 */
	public static final int MID = MessageId.COMMON_MID;
	
	/** game向client发送提示消息 */
	private static final int ALERT_G2C = 1;
	
	@Override
	public void recv(SessHand sess, int subMid, IoBuffer buffer, long clientBundleId)
	{
		GameClientBundle bundle = (GameClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle == null) { return; }
	}

	/** send( game向client发送提示消息 ) */
	public void sendAlert_G2C(String text, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, ALERT_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeString(msg, text);
		msg.send();
	}
	
}
