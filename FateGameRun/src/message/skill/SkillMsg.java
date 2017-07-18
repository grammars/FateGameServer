package message.skill;

import org.apache.mina.core.buffer.IoBuffer;

import common.struct.skill.StSkillSet;
import framework.MsgBuffer;
import framework.SessHand;
import framework.define.SidType;
import message.IMessage;
import message.MessageId;

public class SkillMsg implements IMessage
{
	private static SkillMsg instance;
	public static SkillMsg getInstance()
	{
		if(instance == null) { instance = new SkillMsg(); }
		return instance;
	}
	
	/** [技能]主消息号 */
	public static final int MID = MessageId.SKILL_MID;

	/** game向client发送初始化技能集 */
	private static final int INIT_SKILL_SET_G2C = 1;

	@Override
	public void recv(SessHand sess, int subMid, IoBuffer buffer, long clientBundleId)
	{
	}
	
	/** send( game向client发送初始化技能集 ) */
	public void sendInitSkillSet_G2C(StSkillSet sks, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, INIT_SKILL_SET_G2C, SidType.CLIENT, clientBundleId);
		sks.write(msg.buffer);
		msg.send();
	}

}
