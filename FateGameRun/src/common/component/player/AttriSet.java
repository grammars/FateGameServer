package common.component.player;

import game.core.sprite.Creature;
import game.core.sprite.Player;
import game.core.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;

import message.creature.CreatureMsg;
import common.struct.player.StAttriSet;
import common.struct.player.StAttributes;

public class AttriSet extends StAttriSet
{
	/** 归属 */
	public Creature parent;
	
	/** 获取指定比例的当前生命值 */
	public int rateCurHp(double rat)
	{
		int value = (int)( (double)current.hp * rat );
		return value;
	}
	/** 获取指定比例的当前魔法值 */
	public int rateCurMp(double rat)
	{
		int value = (int)( (double)current.mp * rat );
		return value;
	}

	//==属性统计BEGIN=========
	
	/** 当前生命值 */
	public int curHp() { return current.hp; }
	/** 当前魔法值 */
	public int curMp() { return current.mp; }
	
	/** 生命值 */
	public int hp() { return ( base.hp + equip.hp + skill.hp + buff.hp ); }
	/** 魔法值 */
	public int mp() { return ( base.mp + equip.mp + skill.mp + buff.mp ); }
	
	/** 移动速度 */
	public int moveSpeed() { return ( base.moveSpeed + equip.moveSpeed + skill.moveSpeed + buff.moveSpeed ); }
	
	/** 攻击力 */
	public int attack() { return ( base.attack + equip.attack + skill.attack + buff.attack ); }
	/** 攻击速度 */
	public float attackSpeed() { return ( base.attackSpeed + equip.attackSpeed + skill.attackSpeed + buff.attackSpeed ); }
	/** 护甲值 */
	public int armor() { return ( base.armor + equip.armor + skill.armor + buff.armor ); }
	/** 护甲穿透 */
	public int armorPene() { return ( base.armorPene + equip.armorPene + skill.armorPene + buff.armorPene ); }
	
	/** 法术强度 */
	public int spellPower() { return ( base.spellPower + equip.spellPower + skill.spellPower + buff.spellPower ); }
	/** 法术免疫 */
	public int spellImmun() { return ( base.spellImmun + equip.spellImmun + skill.spellImmun + buff.spellImmun ); }
	/** 法术穿透 */
	public int spellPene() { return ( base.spellPene + equip.spellPene + skill.spellPene + buff.spellPene ); }
	
	//===========属性统计END==
	
	public AttriSet(Creature parent)
	{
		this.parent = parent;
		initValue();
	}
	
	/** 全面恢复健康状态
	 * @param broadcast 是否需要广播 */
	public void recover(boolean broadcast)
	{
		if(broadcast)
		{
			changeCurHpTo(hp());
			changeCurMpTo(mp());
		}
		else
		{
			current.hp = hp();
			current.mp = mp();
		}
	}
	
	/** 获得附近包含自身的Player的UID */
	private List<Long> getNearPlayerUids()
	{
		ArrayList<Long> uids = new ArrayList<>();
		Sprite src = this.parent;
		List<Player> players = src.getNear(true);
		for(Player player : players)
		{
			uids.add(player.getBundle().getUid());
		}
		return uids;
	}
	
	/** 获得源Sprite的tid */
	private int getParentTid()
	{
		Sprite src = this.parent;
		return src.tid;
	}
	
	/** 根据属性源类型获得属性 */
	public StAttributes getAttri(byte src)
	{
		switch(src)
		{
		case SRC_BASE: return base;
		case SRC_EQUIP: return equip;
		case SRC_SKILL: return skill;
		case SRC_BUFF: return buff;
		}
		return null;
	}
	
	//===属性改变BEG==============
	
	/** 改变当前生命值 */
	public void changeCurHp(int delt)
	{
		if(delt == 0) { return; }
		current.hp += delt;
		if(current.hp > hp()) { current.hp = hp(); }
		if(current.hp < 0) { current.hp = 0; }
		List<Long> uids = getNearPlayerUids();
		for(Long id : uids)
		{
			CreatureMsg.getInstance().sendChangeCurHp(getParentTid(), current.hp, delt, id);
		}
		this.parent.ai.curHpChange(delt);
		checkDead();
	}
	public void changeCurHpTo(int value)
	{
		int delt = value - current.hp;
		changeCurHp(delt);
	}
	
	/** 改变当前魔法值 */
	public void changeCurMp(int delt)
	{
		if(delt == 0) { return; }
		current.mp += delt;
		if(current.mp > mp()) { current.mp = mp(); }
		if(current.mp < 0) { current.mp = 0; }
		List<Long> uids = getNearPlayerUids();
		for(Long id : uids)
		{
			CreatureMsg.getInstance().sendChangeCurMp(getParentTid(), current.mp, delt, id);
		}
		this.parent.ai.curMpChange(delt);
	}
	public void changeCurMpTo(int value)
	{
		int delt = value - current.mp;
		changeCurMp(delt);
	}
	
	/** 改变生命值 */
	public void changeHp(int delt, byte src)
	{
		if(delt == 0) { return; }
		StAttributes attri = getAttri(src);
		attri.hp += delt;
		List<Long> uids = getNearPlayerUids();
		for(Long id : uids)
		{
			CreatureMsg.getInstance().sendChangeHp(getParentTid(), src, attri.hp, delt, id);
		}
	}
	public void changeHpTo(int value, byte src)
	{
		int delt = value - getAttri(src).hp;
		changeHp(delt, src);
	}
	
	/** 改变魔法值 */
	public void changeMp(int delt, byte src)
	{
		if(delt == 0) { return; }
		StAttributes attri = getAttri(src);
		attri.mp += delt;
		List<Long> uids = getNearPlayerUids();
		for(Long id : uids)
		{
			CreatureMsg.getInstance().sendChangeMp(getParentTid(), src, attri.mp, delt, id);
		}
	}
	public void changeMpTo(int value, byte src)
	{
		int delt = value - getAttri(src).mp;
		changeMp(delt, src);
	}
	
	/** 改变移动速度 */
	public void changeMoveSpeed(int delt, byte src)
	{
		if(delt == 0) { return; }
		StAttributes attri = getAttri(src);
		attri.moveSpeed += delt;
		List<Long> uids = getNearPlayerUids();
		for(Long id : uids)
		{
			CreatureMsg.getInstance().sendChangeMoveSpeed(getParentTid(), src, attri.moveSpeed, delt, id);
		}
		this.parent.ai.moveSpeedChange(delt);
	}
	public void changeMoveSpeedTo(int value, byte src)
	{
		int delt = value - getAttri(src).moveSpeed;
		changeMoveSpeed(delt, src);
	}
	
	/** 改变攻击力 */
	public void changeAttack(int delt, byte src)
	{
		if(delt == 0) { return; }
		StAttributes attri = getAttri(src);
		attri.attack += delt;
		List<Long> uids = getNearPlayerUids();
		for(Long id : uids)
		{
			CreatureMsg.getInstance().sendChangeAttack(getParentTid(), src, attri.attack, delt, id);
		}
	}
	public void changeAttackTo(int value, byte src)
	{
		int delt = value - getAttri(src).attack;
		changeAttack(delt, src);
	}
	
	/** 改变攻击速度 */
	public void changeAttackSpeed(float delt, byte src)
	{
		if(delt == 0) { return; }
		StAttributes attri = getAttri(src);
		attri.attackSpeed += delt;
		List<Long> uids = getNearPlayerUids();
		for(Long id : uids)
		{
			CreatureMsg.getInstance().sendChangeAttackSpeed(getParentTid(), src, attri.attackSpeed, delt, id);
		}
	}
	public void changeAttackSpeedTo(float value, byte src)
	{
		float delt = value - getAttri(src).attackSpeed;
		changeAttackSpeed(delt, src);
	}
	
	/** 改变护甲值 */
	public void changeArmor(int delt, byte src)
	{
		if(delt == 0) { return; }
		StAttributes attri = getAttri(src);
		attri.armor += delt;
		List<Long> uids = getNearPlayerUids();
		for(Long id : uids)
		{
			CreatureMsg.getInstance().sendChangeArmor(getParentTid(), src, attri.armor, delt, id);
		}
	}
	public void changeArmorTo(int value, byte src)
	{
		int delt = value - getAttri(src).armor;
		changeArmor(delt, src);
	}
	
	/** 改变护甲穿透 */
	public void changeArmorPene(int delt, byte src)
	{
		if(delt == 0) { return; }
		StAttributes attri = getAttri(src);
		attri.armorPene += delt;
		List<Long> uids = getNearPlayerUids();
		for(Long id : uids)
		{
			CreatureMsg.getInstance().sendChangeArmorPenetration(getParentTid(), src, attri.armorPene, delt, id);
		}
	}
	public void changeArmorPeneTo(int value, byte src)
	{
		int delt = value - getAttri(src).armorPene;
		changeArmorPene(delt, src);
	}
	
	/** 改变法术强度 */
	public void changeSpellPower(int delt, byte src)
	{
		if(delt == 0) { return; }
		StAttributes attri = getAttri(src);
		attri.spellPower += delt;
		List<Long> uids = getNearPlayerUids();
		for(Long id : uids)
		{
			CreatureMsg.getInstance().sendChangeSpellPower(getParentTid(), src, attri.spellPower, delt, id);
		}
	}
	public void changeSpellPowerTo(int value, byte src)
	{
		int delt = value - getAttri(src).spellPower;
		changeSpellPower(delt, src);
	}
	
	/** 改变法术免疫 */
	public void changeSpellImmun(int delt, byte src)
	{
		if(delt == 0) { return; }
		StAttributes attri = getAttri(src);
		attri.spellImmun += delt;
		List<Long> uids = getNearPlayerUids();
		for(Long id : uids)
		{
			CreatureMsg.getInstance().sendChangeSpellImmunity(getParentTid(), src, attri.spellImmun, delt, id);
		}
	}
	public void changeSpellImmunTo(int value, byte src)
	{
		int delt = value - getAttri(src).spellImmun;
		changeSpellImmun(delt, src);
	}
	
	/** 改变法术穿透 */
	public void changeSpellPene(int delt, byte src)
	{
		if(delt == 0) { return; }
		StAttributes attri = getAttri(src);
		attri.spellPene += delt;
		List<Long> uids = getNearPlayerUids();
		for(Long id : uids)
		{
			CreatureMsg.getInstance().sendChangeSpellPenetration(getParentTid(), src, attri.spellPene, delt, id);
		}
	}
	public void changeSpellPeneTo(int value, byte src)
	{
		int delt = value - getAttri(src).spellPene;
		changeSpellPene(delt, src);
	}
	
	//==============属性改变END===
	
	/** 检查死亡 */
	private void checkDead()
	{
		if(current.hp <= 0)
		{
			this.parent.dead();
		}
	}
	
	/** 导入结构数据 */
	public void importData(StAttriSet data)
	{
		this.current.copy(data.current);
		this.base.copy(data.base);
		this.equip.copy(data.equip);
		this.skill.copy(data.skill);
		this.buff.copy(data.buff);
	}
	
	/** 导出结构数据 */
	public StAttriSet exportData()
	{
		StAttriSet data = new StAttriSet();
		data.current.copy(this.current);
		data.base.copy(this.base);
		data.equip.copy(this.equip);
		data.skill.copy(this.skill);
		data.buff.copy(this.buff);
		return data;
	}
	
}
