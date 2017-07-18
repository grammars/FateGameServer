package common.struct.player;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONArray;

import utils.JsonUtils;
import framework.net.IBT;

/**
 * 属性包
 * */
public class StAttributes
{
	/** 属性类型{当前生命值} */
	public static final String TYPE_CUR_HP = "curHp";
	/** 属性类型{当前魔法值} */
	public static final String TYPE_CUR_MP = "curMp";
	/** 属性类型{生命值} */
	public static final String TYPE_HP = "hp";
	/** 属性类型{魔法值} */
	public static final String TYPE_MP = "mp";
	/** 属性类型{移动速度} */
	public static final String TYPE_MOVE_SPEED = "moveSpeed";
	/** 属性类型{攻击力} */
	public static final String TYPE_ATTACK = "attack";
	/** 属性类型{攻击速度} */
	public static final String TYPE_ATTACK_SPEED = "attackSpeed";
	/** 属性类型{护甲值} */
	public static final String TYPE_ARMOR = "armor";
	/** 属性类型{护甲穿透} */
	public static final String TYPE_ARMOR_PENE = "armorPene";
	/** 属性类型{法术强度} */
	public static final String TYPE_SPELL_POWER = "spellPower";
	/** 属性类型{法术免疫} */
	public static final String TYPE_SPELL_IMMUN = "spellImmun";
	/** 属性类型{法术穿透} */
	public static final String TYPE_SPELL_PENE = "spellPene";
	
	/** 生命值 */
	public int hp = 0;
	/** 魔法值 */
	public int mp = 0;
	
	/** 移动速度 */
	public int moveSpeed = 0;
	
	/** 攻击力 */
	public int attack = 0;
	/** 攻击速度 */
	public float attackSpeed = 0.0f;
	/** 护甲值 */
	public int armor = 0;
	/** 护甲穿透 */
	public int armorPene = 0;
	
	/** 法术强度 */
	public int spellPower = 0;
	/** 法术免疫 */
	public int spellImmun = 0;
	/** 法术穿透 */
	public int spellPene = 0;
	
	public StAttributes()
	{
		//
	}
	
	/** 重置归零 */
	public void reset()
	{
		hp = 0;
		mp = 0;
		moveSpeed = 0;
		attack = 0;
		attackSpeed = 0.0f;
		armor = 0;
		armorPene = 0;
		spellPower = 0;
		spellImmun = 0;
		spellPene = 0;
	}
	
	/** 写入IoBuffer */
	public void write(IoBuffer buffer)
	{
		IBT.writeInt(buffer, hp);
		IBT.writeInt(buffer, mp);
		
		IBT.writeInt(buffer, moveSpeed);
		
		IBT.writeInt(buffer, attack);
		IBT.writeFloat(buffer, attackSpeed);
		IBT.writeInt(buffer, armor);
		IBT.writeInt(buffer, armorPene);
		
		IBT.writeInt(buffer, spellPower);
		IBT.writeInt(buffer, spellImmun);
		IBT.writeInt(buffer, spellPene);
	}
	
	/** 从IoBuffer读出 */
	public void read(IoBuffer buffer)
	{
		hp = IBT.readInt(buffer);
		mp = IBT.readInt(buffer);
		
		moveSpeed = IBT.readInt(buffer);
		
		attack = IBT.readInt(buffer);
		attackSpeed = IBT.readFloat(buffer);
		armor = IBT.readInt(buffer);
		armorPene = IBT.readInt(buffer);
		
		spellPower = IBT.readInt(buffer);
		spellImmun = IBT.readInt(buffer);
		spellPene = IBT.readInt(buffer);
	}
	
	/** 拷贝 */
	public void copy(StAttributes attri)
	{
		hp = attri.hp;
		mp = attri.mp;
		
		moveSpeed = attri.moveSpeed;
		
		attack = attri.attack;
		attackSpeed = attri.attackSpeed;
		armor = attri.armor;
		armorPene = attri.armorPene;
		
		spellPower = attri.spellPower;
		spellImmun = attri.spellImmun;
		spellPene = attri.spellPene;
	}
	
	public JSONArray encode()
	{
		JSONArray jarr = new JSONArray();
		JsonUtils.putInt(jarr, hp);
		JsonUtils.putInt(jarr, mp);
		JsonUtils.putInt(jarr, moveSpeed);
		JsonUtils.putInt(jarr, attack);
		JsonUtils.putFloat(jarr, attackSpeed);
		JsonUtils.putInt(jarr, armor);
		JsonUtils.putInt(jarr, armorPene);
		JsonUtils.putInt(jarr, spellPower);
		JsonUtils.putInt(jarr, spellImmun);
		JsonUtils.putInt(jarr, spellPene);
		return jarr;
	}
	
	public void decode(JSONArray jarr)
	{
		hp = JsonUtils.getInt(jarr, 0);
		mp = JsonUtils.getInt(jarr, 1);
		moveSpeed = JsonUtils.getInt(jarr, 2);
		attack = JsonUtils.getInt(jarr, 3);
		attackSpeed = JsonUtils.getFloat(jarr, 4);
		armor = JsonUtils.getInt(jarr, 5);
		armorPene = JsonUtils.getInt(jarr, 6);
		spellPower = JsonUtils.getInt(jarr, 7);
		spellImmun = JsonUtils.getInt(jarr, 8);
		spellPene = JsonUtils.getInt(jarr, 9);
	}
	
	public String toString()
	{
		return "[AttriAbility] hp=" + hp + " mp=" + mp +
			" moveSpeed=" + moveSpeed + 
			" attack=" + attack + " attackSpeed=" + attackSpeed +
			" armor=" + armor + " armorPenetration=" + armorPene + 
			" spellPower=" + spellPower + " spellImmunity=" + spellImmun +
			" spellPenetration=" + spellPene;
	}
}
