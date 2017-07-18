package cfg;

import java.util.HashMap;

public class BuffCfg
{
	public static final String TYPE = "buff";
	
	private static HashMap<Integer, BuffCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static BuffCfg get(int id)
	{
		return cfgs.get(id);
	}
	
	/** 立即生效的buff */
	public static final int TYPE_IMM = 0;
	/** 逐步生效的buff */
	public static final int TYPE_GRAD = 1;
	
	/** id */
	public int id = 0;
	/** 名称 */
	public String name = "";
	/** 类型 */
	public int type = 0;
	/** 是否存储 */
	public boolean needStore = false;
	/** 最大叠加数 */
	public int maxStack = 0;
	/** 描述 */
	public String desc = "";
	
	/** 持续时间 */
	public int duration = 0;
	/** 作用延迟 */
	public int delay = 0;
	/** 作用间隔 */
	public int interval = 0;
	/** icon */
	public String icon = "";
	/** 身上特效 */
	public String effect = "";
	
	/** 比例当前生命值 */
	public double ratCurHp = 0;
	/** 绝对当前生命值 */
	public double absCurHp = 0;
	/** 比例当前魔法值 */
	public double ratCurMp = 0;
	/** 绝对当前魔法值 */
	public double absCurMp = 0;
	/** 比例生命值 */
	public double ratHp = 0;
	/** 绝对生命值 */
	public double absHp = 0;
	/** 比例魔法值 */
	public double ratMp = 0;
	/** 绝对魔法值 */
	public double absMp = 0;
	/** 比例移动速度 */
	public double ratMoveSpeed = 0;
	/** 绝对移动速度 */
	public double absMoveSpeed = 0;
	/** 比例攻击力 */
	public double ratAttack = 0;
	/** 绝对攻击力 */
	public double absAttack = 0;
	/** 比例攻击速度 */
	public double ratAttackSpeed = 0;
	/** 绝对攻击速度 */
	public double absAttackSpeed = 0;
	/** 比例护甲值 */
	public double ratArmor = 0;
	/** 绝对护甲值 */
	public double absArmor = 0;
	/** 比例护甲穿透 */
	public double ratArmorPene = 0;
	/** 绝对护甲穿透 */
	public double absArmorPene = 0;
	/** 比例法术强度 */
	public double ratSpellPower = 0;
	/** 绝对法术强度 */
	public double absSpellPower = 0;
	/** 比例法术免疫 */
	public double ratSpellImmun = 0;
	/** 绝对法术免疫 */
	public double absSpellImmun = 0;
	/** 比例法术穿透 */
	public double ratSpellPene = 0;
	/** 绝对法术穿透 */
	public double absSpellPene = 0;
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			BuffCfg c = new BuffCfg();
			c.id = Integer.parseInt( dataDic.get("id")[i] );
			c.name = dataDic.get("名称")[i];
			c.type = Integer.parseInt( dataDic.get("类型")[i] );
			c.needStore = Integer.parseInt( dataDic.get("是否存储")[i] ) == 1;
			c.maxStack = Integer.parseInt( dataDic.get("最大叠加数")[i] );
			c.desc = dataDic.get("描述")[i];
			c.duration = Integer.parseInt( dataDic.get("持续时间")[i] );
			c.delay = Integer.parseInt( dataDic.get("作用延迟")[i] );
			c.interval = Integer.parseInt( dataDic.get("作用间隔")[i] );
			c.icon = dataDic.get("icon")[i];
			c.effect = dataDic.get("身上特效")[i];
			c.ratCurHp = Float.parseFloat( dataDic.get("比例当前生命值")[i] );
			c.absCurHp = Float.parseFloat( dataDic.get("绝对当前生命值")[i] );
			c.ratCurMp = Float.parseFloat( dataDic.get("比例当前魔法值")[i] );
			c.absCurMp = Float.parseFloat( dataDic.get("绝对当前魔法值")[i] );
			c.ratHp = Float.parseFloat( dataDic.get("比例生命值")[i] );
			c.absHp = Float.parseFloat( dataDic.get("绝对生命值")[i] );
			c.ratMp = Float.parseFloat( dataDic.get("比例魔法值")[i] );
			c.absMp = Float.parseFloat( dataDic.get("绝对魔法值")[i] );
			c.ratMoveSpeed = Float.parseFloat( dataDic.get("比例移动速度")[i] );
			c.absMoveSpeed = Float.parseFloat( dataDic.get("绝对移动速度")[i] );
			c.ratAttack = Float.parseFloat( dataDic.get("比例攻击力")[i] );
			c.absAttack = Float.parseFloat( dataDic.get("绝对攻击力")[i] );
			c.ratAttackSpeed = Float.parseFloat( dataDic.get("比例攻击速度")[i] );
			c.absAttackSpeed = Float.parseFloat( dataDic.get("绝对攻击速度")[i] );
			c.ratArmor = Float.parseFloat( dataDic.get("比例护甲值")[i] );
			c.absArmor = Float.parseFloat( dataDic.get("绝对护甲值")[i] );
			c.ratArmorPene = Float.parseFloat( dataDic.get("比例护甲穿透")[i] );
			c.absArmorPene = Float.parseFloat( dataDic.get("绝对护甲穿透")[i] );
			c.ratSpellPower = Float.parseFloat( dataDic.get("比例法术强度")[i] );
			c.absSpellPower = Float.parseFloat( dataDic.get("绝对法术强度")[i] );
			c.ratSpellImmun = Float.parseFloat( dataDic.get("比例法术免疫")[i] );
			c.absSpellImmun = Float.parseFloat( dataDic.get("绝对法术免疫")[i] );
			c.ratSpellPene = Float.parseFloat( dataDic.get("比例法术穿透")[i] );
			c.absSpellPene = Float.parseFloat( dataDic.get("绝对法术穿透")[i] );
			//System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	@Override
	public String toString()
	{
		return "[BuffCfg] id:" + id + " name:" + name + " needStore=" + needStore
				+ " maxStack=" + maxStack + " desc=" + desc + " duration=" + duration
				+ " interval=" + interval + " icon=" + icon + " effect=" + effect
				+ " ratCurHp=" + ratCurHp + " absCurHp=" + absCurHp
				+ " ratCurMp=" + ratCurMp + " absCurMp=" + absCurMp
				+ " ratHp=" + ratHp + " absHp=" + absHp + " ratMp=" + ratMp + " absMp=" + absMp
				+ " ratMoveSpeed=" + ratMoveSpeed + " absMoveSpeed=" + absMoveSpeed 
				+ " ratAttack=" + ratAttack + " absAttack=" + absAttack
				+ " ratAttackSpeed=" + ratAttackSpeed + " absAttackSpeed=" + absAttackSpeed 
				+ " ratArmor=" + ratArmor + " absArmor=" + absArmor
				+ " ratArmorPene=" + ratArmorPene + " absArmorPene=" + absArmorPene
				+ " ratSpellPower=" + ratSpellPower + " absSpellPower=" + absSpellPower
				+ " ratSpellImmun=" + ratSpellImmun + " absSpellImmun=" + absSpellImmun
				+ " ratSpellPene=" + ratSpellPene + " absSpellPene=" + absSpellPene;
	}
	
}
