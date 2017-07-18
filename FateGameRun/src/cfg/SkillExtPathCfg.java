package cfg;

import java.util.HashMap;

public class SkillExtPathCfg
{
	public static final String TYPE = "skill_ext_path";
	
	private static HashMap<Integer, SkillExtPathCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static SkillExtPathCfg get(int id)
	{
		return cfgs.get(id);
	}
	
	/** id */
	public int id = 0;
	/** 技能名 */
	public String name = "";
	/** Path飞行特效 */
	public String pathFlyEffect = "";
	/** Path飞行每格耗时 */
	public int pathFlyGridTime = 0;
	/** Path最大作用次数 */
	public int pathUseMaxTimes = 0;
	/** Path停飞作用次数 */
	public int pathStopFlyUseTimes = 0;
	/** Path作用特效 */
	public String pathHitEffect = "";
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			SkillExtPathCfg c = new SkillExtPathCfg();
			c.id = Integer.parseInt( dataDic.get("id")[i] );
			c.name = dataDic.get("技能名")[i];
			c.pathFlyEffect = dataDic.get("Path飞行特效")[i];
			c.pathFlyGridTime = Integer.parseInt( dataDic.get("Path飞行每格耗时")[i] );
			c.pathUseMaxTimes = Integer.parseInt( dataDic.get("Path最大作用次数")[i] );
			c.pathStopFlyUseTimes = Integer.parseInt( dataDic.get("Path停飞作用次数")[i] );
			c.pathHitEffect = dataDic.get("Path作用特效")[i];
			//System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	@Override
	public String toString()
	{
		return "[SkillExtPathCfg] id:" + id + " name:" + name 
				+ " pathFlyEffect=" + pathFlyEffect + " pathFlyGridTime=" + pathFlyGridTime
				+ " pathUseMaxTimes=" + pathUseMaxTimes + " pathStopFlyUseTimes=" + pathStopFlyUseTimes
				+ " pathHitEffect=" + pathHitEffect;
	}
	
}
