package message.buff;

import org.apache.mina.core.buffer.IoBuffer;

import common.struct.buff.StBuff;
import framework.ClientBundleManager;
import framework.MsgBuffer;
import framework.SessHand;
import framework.define.SidType;
import game.GameClientBundle;
import message.IMessage;
import message.MessageId;

public class BuffMsg implements IMessage
{
	private static BuffMsg instance;
	public static BuffMsg getInstance()
	{
		if(instance == null) { instance = new BuffMsg(); }
		return instance;
	}
	/** [Buff]主消息号 */
	public static final int MID = MessageId.BUFF_MID;
	
	/** game向client通知添加buff */
	private static final int ADD_BUFF_G2C = 1;
	/** game向client通知移除buff */
	private static final int REMOVE_BUFF_G2C = 2;
	/** game向client通知buff的一次作用 */
	private static final int BUFF_USE_G2C = 5;
	
	@Override
	public void recv(SessHand sess, int subMid, IoBuffer buffer, long clientBundleId)
	{
		GameClientBundle bundle = (GameClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle == null) { return; }
	}
	
	/** send( game向client通知添加buff ) */
	public void sendAddBuff_G2C(StBuff buff, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, ADD_BUFF_G2C, SidType.CLIENT, clientBundleId);
		buff.write(msg.buffer);
		msg.send();
	}
	
	/** send( game向client通知移除buff ) */
	public void sendRemoveBuff_G2C(StBuff buff, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, REMOVE_BUFF_G2C, SidType.CLIENT, clientBundleId);
		buff.write(msg.buffer);
		msg.send();
	}

	/** send( game向client通知buff的一次作用 ) */
	public void sendBuffUse_G2C(StBuff buff, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, BUFF_USE_G2C, SidType.CLIENT, clientBundleId);
		buff.write(msg.buffer);
		msg.send();
	}
}
