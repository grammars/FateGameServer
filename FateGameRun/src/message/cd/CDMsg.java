package message.cd;

import org.apache.mina.core.buffer.IoBuffer;

import common.struct.cd.StCDInfo;
import common.struct.cd.StCDUint;
import framework.ClientBundleManager;
import framework.MsgBuffer;
import framework.SessHand;
import framework.define.SidType;
import framework.net.IBT;
import game.GameClientBundle;
import message.IMessage;
import message.MessageId;

public class CDMsg implements IMessage
{
	private static CDMsg instance;
	public static CDMsg getInstance()
	{
		if(instance == null) { instance = new CDMsg(); }
		return instance;
	}
	/** [cd]主消息号 */
	public static final int MID = MessageId.CD_MID;
	
	/** game向client通知初始化cd信息 */
	private static final int INIT_CD_G2C = 1;
	/** game向client通知添加cd信息 */
	private static final int ADD_CD_G2C = 2;
	/** game向client通知移除cd信息 */
	private static final int REMOVE_CD_G2C = 3;
	
	@Override
	public void recv(SessHand sess, int subMid, IoBuffer buffer, long clientBundleId)
	{
		GameClientBundle bundle = (GameClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle == null) { return; }
	}
	
	/** send( game向client通知初始化cd信息 ) */
	public void sendInitCD_G2C(StCDUint data, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, INIT_CD_G2C, SidType.CLIENT, clientBundleId);
		data.write(msg.buffer);
		msg.send();
	}
	
	/** send( game向client通知添加cd信息 ) */
	public void sendAddCD_G2C(StCDInfo info, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, ADD_CD_G2C, SidType.CLIENT, clientBundleId);
		info.write(msg.buffer);
		msg.send();
	}
	
	/** send( game向client通知移除cd信息 ) */
	public void sendRemoveCD_G2C(byte type, int id, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, REMOVE_CD_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeByte(msg, type);
		IBT.writeInt(msg, id);
		msg.send();
	}

}
