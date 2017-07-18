package message.npc;

import org.apache.mina.core.buffer.IoBuffer;

import framework.ClientBundleManager;
import framework.MsgBuffer;
import framework.SessHand;
import framework.define.SidType;
import framework.net.IBT;
import game.GameClientBundle;
import game.core.sprite.Player;
import game.npc.NpcManager;
import game.npc.NpcTalkContent;
import message.IMessage;
import message.MessageId;
import message.fight.FightMsg;

public class NpcMsg implements IMessage
{
	private static NpcMsg instance;
	public static NpcMsg getInstance()
	{
		if(instance == null) { instance = new NpcMsg(); }
		return instance;
	}
	
	/** [npc]主消息号 */
	public static final int MID = MessageId.NPC_MID;

	/** client向game请求跟npc对话  */
	private static final int TALK_WITH_NPC_REQ_C2G = 1;
	/** game向client发送npc对话内容 */
	private static final int NPC_TALK_CONTENT_G2C = 2;

	@Override
	public void recv(SessHand sess, int subMid, IoBuffer buffer, long clientBundleId)
	{
		GameClientBundle bundle = (GameClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle == null) { return; }
		switch(subMid)
		{
		case TALK_WITH_NPC_REQ_C2G:
			recvTalkWithNpcReq_C2G(buffer, bundle.player);
			break;
		}
	}
		
	/** recv( client向game请求跟npc对话 ) */
	private void recvTalkWithNpcReq_C2G(IoBuffer buffer, Player player)
	{
		int npcCfgId = IBT.readInt(buffer);
		NpcManager.talkWithNpc(player, npcCfgId);
	}
		
	/** send( game向client发送npc对话内容 ) */
	public void sendNpcTalkContent_G2C(NpcTalkContent content, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, NPC_TALK_CONTENT_G2C, SidType.CLIENT, clientBundleId);
		content.write(msg.buffer);
		msg.send();
	}

}
