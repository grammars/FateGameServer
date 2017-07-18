package game.core.sprite;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

import cfg.VocCfg;
import utils.Utils;
import message.fight.FightMsg;
import framework.net.IBT;
import game.ai.CreatureAI;
import game.core.sprite.parts.CreatureStatus;
import game.core.sprite.parts.NormalHitUnit;
import game.skill.SkillManager;
import common.component.buff.Buff;
import common.component.buff.BuffSet;
import common.component.player.AttriSet;
import common.component.skill.SkillSet;
import common.define.SexType;
import common.struct.buff.StBuffSet;
import common.struct.skill.StSkillSet;

public abstract class Creature extends Sprite
{
	/** 是否存活 */
	public boolean alive = true;
	/** 名字 */
	public String name;
	/** 性别 */
	public byte sex;
	/** 职业 */
	public byte voc;
	/** 等级 */
	public int level;
	
	/** 生物属性 */
	public AttriSet attris = new AttriSet(this);
	/** 技能信息 */
	public SkillSet skills = new SkillSet();
	
	/** Buff组 */
	public BuffSet buffs = new BuffSet(this);
	/** 获取Buff */
	public Buff getBuff(int id)
	{
		return buffs.get(id);
	}
	/** 添加Buff */
	public Buff addBuff(Buff buff)
	{
		return buffs.add(buff);
	}
	/** 移除Buff */
	public Buff removeBuff(int id)
	{
		return buffs.remove(id);
	}
	
	/** 普通攻击控制单元 */
	public NormalHitUnit hit = new NormalHitUnit(this);
	
	/** 状态 */
	public CreatureStatus status = new CreatureStatus();
	/** 获取AI */
	public CreatureAI ai = new CreatureAI(this);
	
	@Override
	protected void createParts()
	{
		super.createParts();
		buffs = new BuffSet(this);
	}
	
	@Override
	public void update()
	{
		super.update();
		if(buffs != null) { buffs.update(); }
	}
	
	/** 死亡处理 */
	public void dead()
	{
		this.alive = false;
		
		List<Player> players = this.getNear(true);
		for(Player player : players)
		{
			FightMsg.getInstance().sendCretDeadInfo_G2C(this.tid, player.getBundle().getUid());
		}
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		
		if(buffs != null)
		{
			buffs.dispose();
			buffs = null;
		}
	}
	
	@Override
	public void writeAvatar(IoBuffer buffer)
	{
		super.writeAvatar(buffer);
		
		IBT.writeBoolean(buffer, alive);
		IBT.writeString(buffer, name);
		IBT.writeByte(buffer, sex);
		IBT.writeByte(buffer, voc);
		IBT.writeInt(buffer, level);
		
		attris.write(buffer);
		buffs.exportData().write(buffer);
	}
	
	@Override
	public void write(IoBuffer buffer)
	{
		super.write(buffer);
		
		IBT.writeBoolean(buffer, alive);
		IBT.writeString(buffer, name);
		IBT.writeByte(buffer, sex);
		IBT.writeByte(buffer, voc);
		IBT.writeInt(buffer, level);
		
		attris.write(buffer);
		skills.exportData().write(buffer);
		buffs.exportData().write(buffer);
	}
	
	@Override
	public void read(IoBuffer buffer)
	{
		super.read(buffer);
		
		alive = IBT.readBoolean(buffer);
		name = IBT.readString(buffer);
		sex = IBT.readByte(buffer);
		voc = IBT.readByte(buffer);
		level = IBT.readInt(buffer);
		
		attris.read(buffer);
		skills.importData(new StSkillSet().read(buffer));
		buffs.importData(new StBuffSet().read(buffer));
	}
	
	/** 因为职业变化而更新技能 */
	public void updateSkillForVoc()
	{
		this.skills = SkillManager.createSkillForVoc(this.voc);
	}
	
	/** toCreatureString */
	protected String toCreatureString()
	{
		return " alive=" + alive + " name=" + name + " sex=" + SexType.Str(sex)
			+ " voc=" + VocCfg.getVocName(voc) + " level=" + level;
	}
	
	/** toString */
	public String toString()
	{
		return "[Creature] " + toCreatureString();
	}
	
}
