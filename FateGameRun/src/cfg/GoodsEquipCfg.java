package cfg;

import java.util.HashMap;

public class GoodsEquipCfg
{
	public static final String TYPE = "goods_equip";
	
	private static HashMap<Integer, GoodsEquipCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static GoodsEquipCfg get(int id)
	{
		return cfgs.get(id);
	}
	
	/** id */
	public int id = 0;
	/** 物品名 */
	public String name = "";
	/** 装备类型 */
	public byte type = 0;
	/** 武器外观 */
	public String look = "";
	/** 基础生命值 */
	public int baseHp = 0;
	/** 基础魔法值 */
	public int baseMp = 0;
	/** 基础移动速度 */
	public int baseMoveSpeed = 0;
	/** 基础攻击力 */
	public int baseAttack = 0;
	/** 基础攻击速度 */
	public float baseAttackSpeed = 0.0f;
	/** 基础护甲值 */
	public int baseArmor = 0;
	/** 基础护甲穿透 */
	public int baseArmorPene = 0;
	/** 基础法术强度 */
	public int baseSpellPower = 0;
	/** 基础法术免疫 */
	public int baseSpellImmun = 0;
	/** 基础法术穿透 */
	public int baseSpellPene = 0;
	/** 宝石孔数 */
	public int gemHoleNum = 0;
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			GoodsEquipCfg c = new GoodsEquipCfg();
			c.id = Integer.parseInt( dataDic.get("id")[i] );
			c.name = dataDic.get("物品名")[i];
			c.type = Byte.parseByte( dataDic.get("装备类型")[i] );
			c.look = dataDic.get("武器外观")[i];
			c.baseHp = Integer.parseInt( dataDic.get("基础生命值")[i] );
			c.baseMp = Integer.parseInt( dataDic.get("基础魔法值")[i] );
			c.baseMoveSpeed = Integer.parseInt( dataDic.get("基础移动速度")[i] );
			c.baseAttack = Integer.parseInt( dataDic.get("基础攻击力")[i] );
			c.baseAttackSpeed = Float.parseFloat( dataDic.get("基础攻击速度")[i] );
			c.baseArmor = Integer.parseInt( dataDic.get("基础护甲值")[i] );
			c.baseArmorPene = Integer.parseInt( dataDic.get("基础护甲穿透")[i] );
			c.baseSpellPower = Integer.parseInt( dataDic.get("基础法术强度")[i] );
			c.baseSpellImmun = Integer.parseInt( dataDic.get("基础法术免疫")[i] );
			c.baseSpellPene = Integer.parseInt( dataDic.get("基础法术穿透")[i] );
			c.gemHoleNum = Integer.parseInt( dataDic.get("宝石孔数")[i] );
			System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	@Override
	public String toString()
	{
		return "[GoodsEquipCfg] id:" + id + " name=" + name + " type=" + type + " look=" + look
				+ " baseHp=" + baseHp + " baseMp=" + baseMp + " baseMoveSpeed=" + baseMoveSpeed 
				+ " baseAttack=" + baseAttack + " baseAttackSpeed=" + baseAttackSpeed
				+ " baseArmor=" + baseArmor + " baseArmorPene=" + baseArmorPene
				+ " baseSpellPower=" + baseSpellPower + " baseSpellImmun=" + baseSpellImmun
				+ " baseSpellPene=" + baseSpellPene + " gemHoleNum=" + gemHoleNum;
	}
	
}
