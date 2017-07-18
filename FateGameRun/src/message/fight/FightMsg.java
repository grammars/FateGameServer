package message.fight;

import org.apache.mina.core.buffer.IoBuffer;

import framework.ClientBundleManager;
import framework.MsgBuffer;
import framework.SessHand;
import framework.define.SidType;
import framework.net.IBT;
import game.GameClientBundle;
import game.core.sprite.Creature;
import game.core.sprite.Player;
import game.fight.FightManager;
import game.player.PlayerManager;
import message.IMessage;
import message.MessageId;

public class FightMsg implements IMessage
{
	private static FightMsg instance;
	public static FightMsg getInstance()
	{
		if(instance == null) { instance = new FightMsg(); }
		return instance;
	}
	
	/** [战斗]主消息号 */
	public static final int MID = MessageId.FIGHT_MID;

	/** client向game请求改变PK模式  */
	private static final int CHANGE_PKMODE_REQ_C2G = 1;
	/** game向client通知改变PK模式  */
	private static final int PKMODE_INFO_G2C = 2;
	
	/** client向game通知预备普通攻击 */
	private static final int PLAYER_HIT_PREPARE_C2G = 11;
	/** game向client通知预备普通攻击 */
	private static final int CRET_HIT_PREPARE_G2C = 12;
	/** client向game通知执行普通攻击 */
	private static final int PLAYER_HIT_EXECUTE_C2G = 13;
	/** game向client通知执行普通攻击 */
	private static final int CRET_HIT_EXECUTE_G2C = 14;
	
	/** client向game通知预备Aim技能 */
	private static final int PLAYER_SKILL_AIM_PREPARE_C2G = 21;
	/** game向client通知预备Aim技能 */
	private static final int CRET_SKILL_AIM_PREPARE_G2C = 22;
	/** client向game通知执行Aim技能 */
	private static final int PLAYER_SKILL_AIM_EXECUTE_C2G = 23;
	/** game向client通知执行Aim技能 */
	private static final int CRET_SKILL_AIM_EXECUTE_G2C = 24;
	
	/** client向game请求施放Aoe技能 */
	private static final int PLAYER_SKILL_AOE_FIRE_C2G = 25;
	
	/** game向client通知技能完成了一次作用 */
	private static final int SKILL_EFFECT_G2C = 30;
	/** game向client发送技能不满足施放条件的提示 */
	private static final int PLAYER_SKILL_UNAVAILABLE = 39;
	
	/** game向client发送Creature死亡通知 */
	private static final int CRET_DEAD_INFO_G2C = 40;
	/** game向client发送Player死亡通知 */
	private static final int PLAYER_DEAD_INFO_G2C = 41;
	/** client向game发送Player复活的请求 */
	private static final int PLAYER_REBIRTH_REQ_C2G = 42;
	/** game向client发送Player复活的结果 */
	private static final int PLAYER_REBIRTH_RPL_G2C = 43;
	
	@Override
	public void recv(SessHand sess, int subMid, IoBuffer buffer, long clientBundleId)
	{
		GameClientBundle bundle = (GameClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle == null) { return; }
		switch(subMid)
		{
		case CHANGE_PKMODE_REQ_C2G:
			recvChangePkModeReq_C2G(buffer, bundle.player);
			break;
			
		case PLAYER_HIT_PREPARE_C2G:
			recvPlayerHitPrepare_C2G(buffer, bundle.player);
			break;
		case PLAYER_HIT_EXECUTE_C2G:
			recvPlayerHitExecute_C2G(buffer, bundle.player);
			break;
			
		case PLAYER_SKILL_AIM_PREPARE_C2G:
			recvPlayerSkillAimPrepare_C2G(buffer, bundle.player);
			break;
		case PLAYER_SKILL_AIM_EXECUTE_C2G:
			recvPlayerSkillAimExecute_C2G(buffer, bundle.player);
			break;
			
		case PLAYER_SKILL_AOE_FIRE_C2G:
			recvPlayerSkillAoeFire_C2G(buffer, bundle.player);
			break;
			
		case PLAYER_REBIRTH_REQ_C2G:
			recvPlayerRebirthReq_C2G(buffer, bundle.player);
			break;
		}
	}
	
	/** recv( client向game请求改变PK模式 ) */
	private void recvChangePkModeReq_C2G(IoBuffer buffer, Player player)
	{
		int mode = IBT.readInt(buffer);
		player.setPkMode(mode);
	}
	
	/** send( game向client通知改变PK模式 ) */
	public void sendPkModeInfo_G2C(int mode, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PKMODE_INFO_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, mode);
		msg.send();
	}
	
	/** recv( client向game通知预备普通攻击 ) */
	private void recvPlayerHitPrepare_C2G(IoBuffer buffer, Player player)
	{
		int tarTid = IBT.readInt(buffer);
		byte face = IBT.readByte(buffer);
		player.direction = face;
		Creature target = player.getScene().getCreature(tarTid);
		FightManager.hit.prepare(player, target);
	}
	
	/** send( game向client通知预备普通攻击 ) */
	public void sendCretHitPrepare_G2C(int attackerId, byte face, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CRET_HIT_PREPARE_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, attackerId);
		IBT.writeByte(msg, face);
		msg.send();
	}
	
	/** recv( client向game通知执行普通攻击 ) */
	private void recvPlayerHitExecute_C2G(IoBuffer buffer, Player player)
	{
		int tarTid = IBT.readInt(buffer);
		Creature target = player.getScene().getCreature(tarTid);
		if(target == null)
		{
			return;
		}
		FightManager.hit.execute(player, target);
	}
	
	/** send( game向client通知执行普通攻击 ) */
	public void sendCretHitExecute_G2C(int attackerId, int targetId, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CRET_HIT_EXECUTE_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, attackerId);
		IBT.writeInt(msg, targetId);
		msg.send();
	}
	
	/** recv( client向game通知预备Aim技能 ) */
	private void recvPlayerSkillAimPrepare_C2G(IoBuffer buffer, Player player)
	{
		int skillId = IBT.readInt(buffer);
		byte face = IBT.readByte(buffer);
		
		player.direction = face;
		FightManager.aim.prepare(player, skillId);
	}
	
	/** send( game向client通知预备Aim技能 ) */
	public void sendCretSkillAimPrepare_G2C(int userTid, byte face, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CRET_SKILL_AIM_PREPARE_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, userTid);
		IBT.writeByte(msg, face);
		msg.send();
	}
	
	/** recv( client向game通知执行Aim技能 ) */
	private void recvPlayerSkillAimExecute_C2G(IoBuffer buffer, Player player)
	{
		int skillId = IBT.readInt(buffer);
		int tarTid = IBT.readInt(buffer);
		FightManager.aim.execute(player, tarTid, skillId);
	}
	
	/** send( game向client通知执行Aim技能 ) */
	public void sendCretSkillAimExecute_G2C(int userTid, int[] tarTids, int skillId, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CRET_SKILL_AIM_EXECUTE_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, userTid);
		IBT.writeInt(msg, tarTids.length);
		for(int i = 0; i < tarTids.length; i++)
		{
			IBT.writeInt(msg, tarTids[i]);
		}
		IBT.writeInt(msg, skillId);
		msg.send();
	}
	
	/** recv( client向game请求施放Aoe技能 ) */
	private void recvPlayerSkillAoeFire_C2G(IoBuffer buffer, Player player)
	{
		int skillId = IBT.readInt(buffer);
		int fireGX = IBT.readInt(buffer);
		int fireGY = IBT.readInt(buffer);
		byte face = IBT.readByte(buffer);
		
		player.direction = face;
		FightManager.aoe.fire(player, skillId, fireGX, fireGY);
	}
	
	/** send( game向client通知技能完成了一次作用 ) */
	public void sendSkillEffect_G2C(int tarTid, int skillId, int skillLev, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, SKILL_EFFECT_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, tarTid);
		IBT.writeInt(msg, skillId);
		IBT.writeInt(msg, skillLev);
		msg.send();
	}
	
	/** send( game向client发送技能不满足施放条件的提示 ) */
	public void sendPlayerSkillUnavailable(long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_SKILL_UNAVAILABLE, SidType.CLIENT, clientBundleId);
		msg.send();
	}
	
	/** send( game向client发送Creature死亡通知 ) */
	public void sendCretDeadInfo_G2C(int deadTid, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CRET_DEAD_INFO_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, deadTid);
		msg.send();
	}
	
	/** send( game向client发送Player死亡通知 ) */
	public void sendPlayerDeadInfo_G2C(byte deathCause, Creature killer, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_DEAD_INFO_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeByte(msg, deathCause);
		IBT.writeString(msg, killer.name);
		msg.send();
	}
	
	/** recv( client向game发送Player复活的请求 ) */
	private void recvPlayerRebirthReq_C2G(IoBuffer buffer, Player player)
	{
		byte rebirthWay = IBT.readByte(buffer);
		PlayerManager.rebirth.rebirth(rebirthWay, player);
	}
	
	/** send( game向client发送Player复活的结果 ) */
	public void sendPlayerRebirthRpl_G2C(int srcTid, byte errCode, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_REBIRTH_RPL_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, srcTid);
		IBT.writeByte(msg, errCode);
		msg.send();
	}
	
}
