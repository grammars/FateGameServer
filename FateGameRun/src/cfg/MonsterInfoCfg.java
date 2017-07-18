package cfg;

import java.util.Collection;
import java.util.HashMap;

import common.struct.player.StAttributes;
import utils.StrParser;

public class MonsterInfoCfg
{
	public static final String TYPE = "monster_info";
	
	private static HashMap<Integer, MonsterInfoCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static MonsterInfoCfg get(int id)
	{
		return cfgs.get(id);
	}
	/** 获得全部的MapInfoCfg */
	public static Collection<MonsterInfoCfg> getAll()
	{
		return cfgs.values();
	}
	
	/** id */
	public int id = 0;
	/** 名字 */
	public String name = "";
	/** 性别 */
	public byte sex = 0;
	/** 职业 */
	public byte voc = 0;
	/** 等级 */
	public int level = 0;
	/** 外观id */
	public String look = "";
	/** 经验值 */
	public int exp = 0;
	/** 视野x */
	public int viewX = 0;
	/** 视野y */
	public int viewY = 0;
	/** 巡视半径x */
	public int walkRX = 0;
	/** 巡视半径y */
	public int walkRY = 0;
	/** 技能ids */
	public int[] skillIds = new int[0];
	/** 技能levels */
	public int[] skillLevels = new int[0];
	/** 掉落物 */
	public int dropId = 0;
	/** 掉落几率 */
	public int dropProb = 0;
	/** 掉落几率分母 */
	public static final double PD = 1000000;

	/** 本次是否会掉落 */
	public boolean willDrop()
	{
		int r = (int)( PD*Math.random() );
		if(r < dropProb) { return true; }
		return false;
	}
	
	public StAttributes attri = new StAttributes();
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			MonsterInfoCfg c = new MonsterInfoCfg();
			c.id = Integer.parseInt( dataDic.get("id")[i] );
			c.name = dataDic.get("名字")[i];
			c.sex = Byte.parseByte( dataDic.get("性别")[i] );
			c.voc = Byte.parseByte( dataDic.get("职业")[i] );
			c.level = Integer.parseInt( dataDic.get("等级")[i] );
			c.look = dataDic.get("外观id")[i];
			c.exp = Integer.parseInt( dataDic.get("经验值")[i] );
			c.viewX = Integer.parseInt( dataDic.get("视野x")[i] );
			c.viewY = Integer.parseInt( dataDic.get("视野y")[i] );
			c.walkRX = Integer.parseInt( dataDic.get("巡视半径x")[i] );
			c.walkRY = Integer.parseInt( dataDic.get("巡视半径y")[i] );
			c.dropId = Integer.parseInt( dataDic.get("掉落物")[i] );
			c.dropProb = Integer.parseInt( dataDic.get("掉落几率")[i] );
			
			String skillIdsStr = dataDic.get("技能ids")[i];
			c.skillIds = StrParser.str2arr(skillIdsStr, ",", (int)0);
			String skillLevelsStr = dataDic.get("技能levels")[i];
			c.skillLevels = StrParser.str2arr(skillLevelsStr, ",", (int)0);
			
			c.attri.hp = Integer.parseInt( dataDic.get("生命值")[i] );
			c.attri.mp = Integer.parseInt( dataDic.get("魔法值")[i] );
			c.attri.moveSpeed = Integer.parseInt( dataDic.get("移动速度")[i] );
			c.attri.attack = Integer.parseInt( dataDic.get("攻击")[i] );
			c.attri.attackSpeed = Float.parseFloat( dataDic.get("攻击速度")[i] );
			c.attri.armor = Integer.parseInt( dataDic.get("护甲")[i] );
			c.attri.armorPene = Integer.parseInt( dataDic.get("护甲穿透")[i] );
			c.attri.spellPower = Integer.parseInt( dataDic.get("法术强度")[i] );
			c.attri.spellImmun = Integer.parseInt( dataDic.get("法术免疫")[i] );
			c.attri.spellPene = Integer.parseInt( dataDic.get("法术穿透")[i] );
			
			cfgs.put(c.id, c);
			System.out.println(c);
		}
	}
	
	@Override
	public String toString()
	{
		return "[MonsterInfoCfg] id:" + id + " name:" + name + " sex=" + sex
				+ " voc=" + voc + " level=" + level + " look=" + look + " exp=" + exp
				+ " viewX=" + viewX + " viewY=" + viewY + " walkRX=" + walkRX + " walkRY=" + walkRY
				+ " attri=" + attri;
	}
}
