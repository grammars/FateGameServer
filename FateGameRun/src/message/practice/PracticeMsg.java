package message.practice;

import org.apache.mina.core.buffer.IoBuffer;

import common.struct.practice.StPracticeData;
import framework.ClientBundleManager;
import framework.MsgBuffer;
import framework.SessHand;
import framework.define.SidType;
import framework.net.IBT;
import game.GameClientBundle;
import game.core.sprite.Player;
import game.practice.PracticeManager;
import message.IMessage;
import message.MessageId;

public class PracticeMsg implements IMessage
{
	private static PracticeMsg instance;
	public static PracticeMsg getInstance()
	{
		if(instance == null) { instance = new PracticeMsg(); }
		return instance;
	}
	/** [修炼]主消息号 */
	public static final int MID = MessageId.PRACTICE_MID;
	
	/** game向client通知修炼信息初始化 */
	private static final int INIT_DATA_G2C = 1;
	/** client向game请求提升修炼等级 */
	private static final int LEVEL_UP_REQ_C2G = 2;
	/** game向client返回提升修炼等级结果 */
	private static final int LEVEL_UP_RPL_G2C = 3;
	
	@Override
	public void recv(SessHand sess, int subMid, IoBuffer buffer, long clientBundleId)
	{
		GameClientBundle bundle = (GameClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle == null) { return; }
		switch(subMid)
		{
		case LEVEL_UP_REQ_C2G:
			recvLevelUpReq_C2G(buffer, bundle.player);
			break;
		}
	}
	
	/** send( game向client通知修炼信息初始化 ) */
	public void sendInitData_G2C(StPracticeData data, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, INIT_DATA_G2C, SidType.CLIENT, clientBundleId);
		data.write(msg.buffer);
		msg.send();
	}

	/** recv( client向game请求提升修炼等级 ) */
	private void recvLevelUpReq_C2G(IoBuffer buffer, Player player)
	{
		
		PracticeManager.tryLevelUp(player);
	}
	
	/** game向client返回提升修炼等级结果 */
	public void sendLevelUpRpl_G2C(byte errCode, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, LEVEL_UP_RPL_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeByte(msg, errCode);
		msg.send();
	}

}
