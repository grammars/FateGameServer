package cfg;

import java.util.HashMap;

import common.define.SkillDef;
import framework.Log;

public class SkillCfg
{
	public static final String TYPE = "skill";
	
	private static HashMap<Integer, SkillCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static SkillCfg get(int id)
	{
		return cfgs.get(id);
	}
	
	/** 效果类型{伤害型} */
	public static final int EFF_HARM = 0;
	/** 效果类型{治疗型} */
	public static final int EFF_CURE = 1;
	
	/** id */
	public int id = 0;
	/** 技能名 */
	public String name = "";
	/** CD时间 */
	public int cdTime = 0;
	/** 施放距离 */
	public float useDistance = 0;
	/** 类型 */
	public int type = 0;
	/** 效果类型 */
	public int effType = 0;
	/** 扩展id */
	public int extId = 0;
	/** 作用效果组 */
	public String effsStr = "";
	/** buffId */
	public int buffId = 0;
	/** 消耗计算方式 */
	public String costType;
	/** 消耗计算参数 */
	public String costParamsStr;
	/** icon */
	public String icon = "";
	/** 描述 */
	public String desc = "";
	
	
	public SkillExtAimCfg extAim;
	public SkillExtAoeCfg extAoe;
	public SkillExtPathCfg extPath;
	
	public double[] costParams;
	public SkillEffCfg[] effs;
	public BuffCfg buffCfg;
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			SkillCfg c = new SkillCfg();
			c.id = Integer.parseInt( dataDic.get("id")[i] );
			c.name = dataDic.get("技能名")[i];
			c.cdTime = Integer.parseInt( dataDic.get("CD时间")[i] );
			c.useDistance = Float.parseFloat( dataDic.get("施放距离")[i] );
			c.type = Integer.parseInt( dataDic.get("类型")[i] );
			c.effType = Integer.parseInt( dataDic.get("效果类型")[i] );
			c.extId = Integer.parseInt( dataDic.get("扩展id")[i] );
			c.effsStr = dataDic.get("作用效果组")[i];
			c.buffId = Integer.parseInt( dataDic.get("buffId")[i] );
			c.costType = dataDic.get("消耗计算方式")[i];
			c.costParamsStr = dataDic.get("消耗计算参数")[i];
			c.icon = dataDic.get("icon")[i];
			c.desc = dataDic.get("描述")[i];
			//System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	/** 加工模版配置 */
	public static void process()
	{
		for( SkillCfg c : cfgs.values() )
		{
			c.setup();
		}
	}
	
	/** setup */
	private void setup()
	{
		handleExt();
		handleCost();
		handleEffs();
		handleBuff();
	}
	
	/** 链接扩展配置 */
	private void handleExt()
	{
		switch(this.type)
		{
		case SkillDef.TYPE_AIM:
			extAim = SkillExtAimCfg.get(extId);
			if(extAim==null) { Log.game.fatal("找不到id="+extId+"的Aim技能扩展"); }
			break;
		case SkillDef.TYPE_AOE:
			extAoe = SkillExtAoeCfg.get(extId);
			if(extAoe==null) { Log.game.fatal("找不到id="+extId+"的Aoe技能扩展"); }
			break;
		case SkillDef.TYPE_PATH:
			extPath = SkillExtPathCfg.get(extId);
			if(extPath==null) { Log.game.fatal("找不到id="+extId+"的Path技能扩展"); }
			break;
		}
	}
	
	/** 解析消耗 */
	private void handleCost()
	{
		String[] szParams = costParamsStr.split(";");
		final int PARAMS_SIZE = szParams.length;
		costParams = new double[PARAMS_SIZE];
		for(int i = 0; i < PARAMS_SIZE; i++)
		{
			costParams[i] = Double.parseDouble(szParams[i]);
		}
	}
	
	/** 处理技能效用 */
	private void handleEffs()
	{
		if("0".equals(effsStr))
		{
			effs = new SkillEffCfg[0];
			return;
		}
		String[] szEffs = effsStr.split(",");
		final int EFFS_SIZE = szEffs.length;
		effs = new SkillEffCfg[EFFS_SIZE];
		for(int i = 0; i < EFFS_SIZE; i++)
		{
			int effId = Integer.parseInt(szEffs[i]);
			effs[i] = SkillEffCfg.get(effId);
		}
		
	}
	
	/** 链接buff配置 */
	private void handleBuff()
	{
		buffCfg = BuffCfg.get(this.buffId);
	}
	
	@Override
	public String toString()
	{
		return "[SkillCfg] id:" + id + " name:" + name + " cdTime=" + cdTime + " useDistance=" + useDistance
				+ " type=" + type + " effType=" + effType + " extId=" + extId + " buffId=" + buffId + " costType=" + costType
				+ " costParamsStr=" + costParamsStr + " icon=" + icon + " desc=" + desc;
	}
	
}
