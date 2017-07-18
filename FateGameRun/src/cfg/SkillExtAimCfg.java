package cfg;

import java.util.HashMap;

public class SkillExtAimCfg
{
	public static final String TYPE = "skill_ext_aim";
	
	private static HashMap<Integer, SkillExtAimCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static SkillExtAimCfg get(int id)
	{
		return cfgs.get(id);
	}
	
	/** id */
	public int id = 0;
	/** 技能名 */
	public String name = "";
	/** Aim对自己使用 */
	public boolean aimSelf = false;
	/** Aim最大目标数 */
	public int aimMaxCount = 0;
	/** Aim范围 */
	public int aimRange = 0;
	/** Aim飞行特效 */
	public String aimFlyEffect = "";
	/** Aim飞行每格耗时 */
	public int aimFlyGridTime = 0;
	/** Aim作用特效 */
	public String aimHitEffect = "";
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			SkillExtAimCfg c = new SkillExtAimCfg();
			c.id = Integer.parseInt( dataDic.get("id")[i] );
			c.name = dataDic.get("技能名")[i];
			c.aimSelf = Integer.parseInt( dataDic.get("Aim对自己使用")[i] ) == 1;
			c.aimMaxCount = Integer.parseInt( dataDic.get("Aim最大目标数")[i] );
			c.aimRange = Integer.parseInt( dataDic.get("Aim范围")[i] );
			c.aimFlyEffect = dataDic.get("Aim飞行特效")[i];
			c.aimFlyGridTime = Integer.parseInt( dataDic.get("Aim飞行每格耗时")[i] );
			c.aimHitEffect = dataDic.get("Aim作用特效")[i];
			//System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	@Override
	public String toString()
	{
		return "[SkillExtAimCfg] id:" + id + " name:" + name
				+ " aimSelf=" + aimSelf + " aimMaxCount=" + aimMaxCount 
				+ " aimRange=" + aimRange + " aimFlyEffect=" + aimFlyEffect
				+ " aimFlyGridTime=" + aimFlyGridTime + " aimHitEffect=" + aimHitEffect;
	}
	
}
