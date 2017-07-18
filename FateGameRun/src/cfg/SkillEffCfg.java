package cfg;

import java.util.HashMap;

public class SkillEffCfg
{
	public static final String TYPE = "skill_eff";
	
	private static HashMap<Integer, SkillEffCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static SkillEffCfg get(int id)
	{
		return cfgs.get(id);
	}
	
	/** 效果类型{伤害型} */
	public static final int EFF_HARM = 0;
	/** 效果类型{治疗型} */
	public static final int EFF_CURE = 1;
	
	/** 结算类型{真实伤害/治疗} */
	public static final int CAL_REAL = 0;
	/** 结算类型{物理伤害} */
	public static final int CAL_PHYSIC = 1;
	/** 结算类型{魔法伤害} */
	public static final int CAL_MAGIC = 2;
	
	/** id */
	public int id = 0;
	/** 描述 */
	public String desc = "";
	/** 数值类型 */
	public String attriType = "";
	/** 结算类型 */
	public int calType = 0;
	/** 效果类型 */
	public int effType = 0;
	/** 效果基础值 */
	public int baseValue = 0;
	
	/** 效果因子施放者等级 */
	public double fUserLevel = 0;
	/** 效果因子作用者等级 */
	public double fTarLevel = 0;
	/** 效果因子技能等级 */
	public double fSkillLevel = 0;
	
	/** 效果因子施放者当前生命值 */
	public double fUserCurHp = 0;
	/** 效果因子作用者当前生命值 */
	public double fTarCurHp = 0;
	/** 效果因子施放者生命值 */
	public double fUserHp = 0;
	/** 效果因子作用者生命值 */
	public double fTarHp = 0;
	
	/** 效果因子施放者当前魔法值 */
	public double fUserCurMp = 0;
	/** 效果因子作用者当前魔法值 */
	public double fTarCurMp = 0;
	/** 效果因子施放者魔法值 */
	public double fUserMp = 0;
	/** 效果因子作用者魔法值 */
	public double fTarMp = 0;
	
	/** 效果因子施放者攻击力 */
	public double fUserAttack = 0;
	/** 效果因子作用者攻击力 */
	public double fTarAttack = 0;
	/** 效果因子施放者攻击速度 */
	public double fUserAttackSpeed = 0;
	/** 效果因子作用者攻击速度 */
	public double fTarAttackSpeed = 0;
	/** 效果因子施放者护甲值 */
	public double fUserArmor = 0;
	/** 效果因子作用者护甲值 */
	public double fTarArmor = 0;
	/** 效果因子施放者护甲穿透 */
	public double fUserArmorPene = 0;
	/** 效果因子作用者护甲穿透 */
	public double fTarArmorPene = 0;
	/** 效果因子施放者法术强度 */
	public double fUserSpellPower = 0;
	/** 效果因子作用者法术强度 */
	public double fTarSpellPower = 0;
	/** 效果因子施放者法术免疫 */
	public double fUserSpellImmun = 0;
	/** 效果因子作用者法术免疫 */
	public double fTarSpellImmun = 0;
	/** 效果因子施放者法术穿透 */
	public double fUserSpellPene = 0;
	/** 效果因子作用者法术穿透 */
	public double fTarSpellPene = 0;
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			SkillEffCfg c = new SkillEffCfg();
			c.id = Integer.parseInt( dataDic.get("id")[i] );
			c.desc = dataDic.get("描述")[i];
			c.attriType = dataDic.get("数值类型")[i];
			c.calType = Integer.parseInt( dataDic.get("结算类型")[i] );
			c.effType = Integer.parseInt( dataDic.get("效果类型")[i] );
			c.baseValue = Integer.parseInt( dataDic.get("效果基础值")[i] );
			c.fUserLevel = Double.parseDouble( dataDic.get("效果因子施放者等级")[i] );
			c.fTarLevel = Double.parseDouble( dataDic.get("效果因子作用者等级")[i] );
			c.fSkillLevel = Double.parseDouble( dataDic.get("效果因子技能等级")[i] );
			c.fUserCurHp = Double.parseDouble( dataDic.get("效果因子施放者当前生命值")[i] );
			c.fTarCurHp = Double.parseDouble( dataDic.get("效果因子作用者当前生命值")[i] );
			c.fUserHp = Double.parseDouble( dataDic.get("效果因子施放者生命值")[i] );
			c.fTarHp = Double.parseDouble( dataDic.get("效果因子作用者生命值")[i] );
			c.fUserCurMp = Double.parseDouble( dataDic.get("效果因子施放者当前魔法值")[i] );
			c.fTarCurMp = Double.parseDouble( dataDic.get("效果因子作用者当前魔法值")[i] );
			c.fUserMp = Double.parseDouble( dataDic.get("效果因子施放者魔法值")[i] );
			c.fTarMp = Double.parseDouble( dataDic.get("效果因子作用者魔法值")[i] );
			c.fUserAttack = Double.parseDouble( dataDic.get("效果因子施放者攻击力")[i] );
			c.fTarAttack = Double.parseDouble( dataDic.get("效果因子作用者攻击力")[i] );
			c.fUserAttackSpeed = Double.parseDouble( dataDic.get("效果因子施放者攻击速度")[i] );
			c.fTarAttackSpeed = Double.parseDouble( dataDic.get("效果因子作用者攻击速度")[i] );
			c.fUserArmor = Double.parseDouble( dataDic.get("效果因子施放者护甲值")[i] );
			c.fTarArmor = Double.parseDouble( dataDic.get("效果因子作用者护甲值")[i] );
			c.fUserArmorPene = Double.parseDouble( dataDic.get("效果因子施放者护甲穿透")[i] );
			c.fTarArmorPene = Double.parseDouble( dataDic.get("效果因子作用者护甲穿透")[i] );
			c.fUserSpellPower = Double.parseDouble( dataDic.get("效果因子施放者法术强度")[i] );
			c.fTarSpellPower = Double.parseDouble( dataDic.get("效果因子作用者法术强度")[i] );
			c.fUserSpellImmun = Double.parseDouble( dataDic.get("效果因子施放者法术免疫")[i] );
			c.fTarSpellImmun = Double.parseDouble( dataDic.get("效果因子作用者法术免疫")[i] );
			c.fUserSpellPene = Double.parseDouble( dataDic.get("效果因子施放者法术穿透")[i] );
			c.fTarSpellPene = Double.parseDouble( dataDic.get("效果因子作用者法术穿透")[i] );
			//System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	@Override
	public String toString()
	{
		return "[SkillEffCfg] id:" + id + " desc=" + desc + " attriType=" + attriType +
				" calType=" + calType + " effType=" + effType + " baseValue=" + baseValue;
	}
	
}
