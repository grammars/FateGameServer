package message.creature;

import org.apache.mina.core.buffer.IoBuffer;

import framework.ClientBundleManager;
import framework.MsgBuffer;
import framework.SessHand;
import framework.define.SidType;
import framework.net.IBT;
import game.GameClientBundle;
import message.IMessage;
import message.MessageId;

public class CreatureMsg implements IMessage
{
	private static CreatureMsg instance;
	public static CreatureMsg getInstance()
	{
		if(instance == null) { instance = new CreatureMsg(); }
		return instance;
	}
	/** [生物数据]主消息号 */
	public static final int MID = MessageId.CREATURE_MID;
	
	/** game发送给client 改变当前生命值 */
	private static final int CHANGE_CUR_HP_G2C = 1;
	/** game发送给client 改变当前魔法值 */
	private static final int CHANGE_CUR_MP_G2C = 2;
	/** game发送给client 改变生命值 */
	private static final int CHANGE_HP_G2C = 3;
	/** game发送给client 改变魔法值 */
	private static final int CHANGE_MP_G2C = 4;
	/** game发送给client 改变移动速度 */
	private static final int CHANGE_MOVE_SPEED_G2C = 5;
	/** game发送给client 改变攻击力 */
	private static final int CHANGE_ATTACK_G2C = 6;
	/** game发送给client 改变攻击速度 */
	private static final int CHANGE_ATTACK_SPEED_G2C = 7;
	/** game发送给client 改变护甲值 */
	private static final int CHANGE_ARMOR_G2C = 8;
	/** game发送给client 改变护甲穿透 */
	private static final int CHANGE_ARMOR_PENETRATION_G2C = 9;
	/** game发送给client 改变法术强度 */
	private static final int CHANGE_SPELL_POWER_G2C = 10;
	/** game发送给client 改变法术免疫 */
	private static final int CHANGE_SPELL_IMMUNITY_G2C = 11;
	/** game发送给client 改变法术穿透 */
	private static final int CHANGE_SPELL_PENETRATION_G2C = 12;
	

	@Override
	public void recv(SessHand sess, int subMid, IoBuffer buffer, long clientBundleId)
	{
		GameClientBundle bundle = (GameClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle == null) { return; }
	}
	
	/** send( game发送给client 改变当前生命值 ) */
	public void sendChangeCurHp(int spriteId, int value, int delt, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CHANGE_CUR_HP_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, spriteId);
		IBT.writeInt(msg, value);
		IBT.writeInt(msg, delt);
		msg.send();
	}
	
	/** send( game发送给client 改变当前魔法值 ) */
	public void sendChangeCurMp(int spriteId, int value, int delt, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CHANGE_CUR_MP_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, spriteId);
		IBT.writeInt(msg, value);
		IBT.writeInt(msg, delt);
		msg.send();
	}
	
	/** send( game发送给client 改变生命值 ) */
	public void sendChangeHp(int spriteId, byte src, int value, int delt, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CHANGE_HP_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, spriteId);
		IBT.writeByte(msg, src);
		IBT.writeInt(msg, value);
		IBT.writeInt(msg, delt);
		msg.send();
	}
	
	/** send( game发送给client 改变魔法值 ) */
	public void sendChangeMp(int spriteId, byte src, int value, int delt, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CHANGE_MP_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, spriteId);
		IBT.writeByte(msg, src);
		IBT.writeInt(msg, value);
		IBT.writeInt(msg, delt);
		msg.send();
	}
	
	/** send( game发送给client 改变移动速度 ) */
	public void sendChangeMoveSpeed(int spriteId, byte src, int value, int delt, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CHANGE_MOVE_SPEED_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, spriteId);
		IBT.writeByte(msg, src);
		IBT.writeInt(msg, value);
		IBT.writeInt(msg, delt);
		msg.send();
	}
	
	/** send( game发送给client 改变攻击力 ) */
	public void sendChangeAttack(int spriteId, byte src, int value, int delt, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CHANGE_ATTACK_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, spriteId);
		IBT.writeByte(msg, src);
		IBT.writeInt(msg, value);
		IBT.writeInt(msg, delt);
		msg.send();
	}
	
	/** send( game发送给client 改变攻击速度 ) */
	public void sendChangeAttackSpeed(int spriteId, byte src, float value, float delt, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CHANGE_ATTACK_SPEED_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, spriteId);
		IBT.writeByte(msg, src);
		IBT.writeFloat(msg, value);
		IBT.writeFloat(msg, delt);
		msg.send();
	}
	
	/** send( game发送给client 改变护甲值 ) */
	public void sendChangeArmor(int spriteId, byte src, int value, int delt, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CHANGE_ARMOR_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, spriteId);
		IBT.writeByte(msg, src);
		IBT.writeInt(msg, value);
		IBT.writeInt(msg, delt);
		msg.send();
	}
	
	/** send( game发送给client 改变护甲穿透 ) */
	public void sendChangeArmorPenetration(int spriteId, byte src, int value, int delt, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CHANGE_ARMOR_PENETRATION_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, spriteId);
		IBT.writeByte(msg, src);
		IBT.writeInt(msg, value);
		IBT.writeInt(msg, delt);
		msg.send();
	}
	
	/** send( game发送给client 改变法术强度 ) */
	public void sendChangeSpellPower(int spriteId, byte src, int value, int delt, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CHANGE_SPELL_POWER_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, spriteId);
		IBT.writeByte(msg, src);
		IBT.writeInt(msg, value);
		IBT.writeInt(msg, delt);
		msg.send();
	}
	
	/** send( game发送给client 改变法术免疫 ) */
	public void sendChangeSpellImmunity(int spriteId, byte src, int value, int delt, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CHANGE_SPELL_IMMUNITY_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, spriteId);
		IBT.writeByte(msg, src);
		IBT.writeInt(msg, value);
		IBT.writeInt(msg, delt);
		msg.send();
	}
	
	/** send( game发送给client 改变法术穿透 ) */
	public void sendChangeSpellPenetration(int spriteId, byte src, int value, int delt, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CHANGE_SPELL_PENETRATION_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, spriteId);
		IBT.writeByte(msg, src);
		IBT.writeInt(msg, value);
		IBT.writeInt(msg, delt);
		msg.send();
	}

}
