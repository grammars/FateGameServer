package common.struct.player;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONObject;

import utils.JsonUtils;

public class StAttriSet
{
	/** 属性源:自身 */
	public static final byte SRC_BASE = 0;
	/** 属性源:装备附加 */
	public static final byte SRC_EQUIP = 1;
	/** 属性源:技能附加 */
	public static final byte SRC_SKILL = 2;
	/** 属性源:buff附加 */
	public static final byte SRC_BUFF = 3;
	
	/** 属性现状 */
	public StAttributes current = new StAttributes();
	
	/** 自身属性 */
	public StAttributes base = new StAttributes();
	/** 装备附加属性 */
	public StAttributes equip = new StAttributes();
	/** 技能附加属性 */
	public StAttributes skill = new StAttributes();
	/** buff附加属性 */
	public StAttributes buff = new StAttributes();
	
	public StAttriSet()
	{
		initValue();
	}
	
	/** 初始化设定值 */
	protected void initValue()
	{
		current.hp = base.hp = 2550;
		current.mp = base.mp = 240;
		
		current.moveSpeed = base.moveSpeed = 150;
		
		current.attack = base.attack = 56;
		current.attackSpeed = base.attackSpeed = 0.6f;
		current.armor = base.armor = 20;
		current.armorPene = base.armorPene = 5;
		current.spellPower = base.spellPower = 15;
		current.spellImmun = base.spellImmun = 10;
		current.spellPene = base.spellPene = 5;
	}
	
	/** 写入IoBuffer */
	public void write(IoBuffer buffer)
	{
		current.write(buffer);
		base.write(buffer);
		equip.write(buffer);
		skill.write(buffer);
		buff.write(buffer);
	}
	
	/** 从IoBuffer读出 */
	public void read(IoBuffer buffer)
	{
		current.read(buffer);
		base.read(buffer);
		equip.read(buffer);
		skill.read(buffer);
		buff.read(buffer);
	}
	
	/** 数据库存储编码 */
	public JSONObject encode()
	{
		JSONObject jso = new JSONObject();
		JsonUtils.putJSONArray(jso, "current", current.encode());
		JsonUtils.putJSONArray(jso, "base", base.encode());
		JsonUtils.putJSONArray(jso, "equip", equip.encode());
		JsonUtils.putJSONArray(jso, "skill", skill.encode());
		JsonUtils.putJSONArray(jso, "buff", buff.encode());
		return jso;
	}
	
	/** 数据库存储解码 */
	public void decode(JSONObject jso)
	{
		current.decode( JsonUtils.getJSONArray(jso, "current") );
		base.decode( JsonUtils.getJSONArray(jso, "base") );
		equip.decode( JsonUtils.getJSONArray(jso, "equip") );
		skill.decode( JsonUtils.getJSONArray(jso, "skill") );
		buff.decode( JsonUtils.getJSONArray(jso, "buff") );
	}
	
	public String toString()
	{
		return "[StAttriSet] current=" + current + " base=" + base + 
				" equip=" + equip + " skill=" + skill + " buff=" + buff;
	}
}
