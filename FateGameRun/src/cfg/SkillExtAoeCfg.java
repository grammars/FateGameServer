package cfg;

import java.util.HashMap;

import ds.geom.Point;

public class SkillExtAoeCfg
{
	public static final String TYPE = "skill_ext_aoe";
	
	private static HashMap<Integer, SkillExtAoeCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static SkillExtAoeCfg get(int id)
	{
		return cfgs.get(id);
	}
	
	/** id */
	public int id = 0;
	/** 技能名 */
	public String name = "";
	/** Aoe范围 */
	public String aoeRangeStr = "";
	/** Aoe预览 */
	public String aoePreview = "";
	/** Aoe特效 */
	public String aoeEffect = "";
	/** Aoe特效时长 */
	public int aoeEffectTime = 0;
	/** Aoe生命时长 */
	public int aoeLifeTime = 0;
	/** Aoe作用延迟 */
	public int aoeUseDelay = 0;
	/** Aoe作用次数 */
	public int aoeUseTimes = 0;
	/** Aoe作用间隔 */
	public int aoeUseInterval = 0;
	
	/** 技能作用范围 */
	public Point[] range;
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			SkillExtAoeCfg c = new SkillExtAoeCfg();
			c.id = Integer.parseInt( dataDic.get("id")[i] );
			c.name = dataDic.get("技能名")[i];
			c.aoeRangeStr = dataDic.get("Aoe范围")[i];
			c.aoePreview = dataDic.get("Aoe预览")[i];
			c.aoeEffect = dataDic.get("Aoe特效")[i];
			c.aoeEffectTime = Integer.parseInt( dataDic.get("Aoe特效时长")[i] );
			c.aoeLifeTime = Integer.parseInt( dataDic.get("Aoe生命时长")[i] );
			c.aoeUseDelay = Integer.parseInt( dataDic.get("Aoe作用延迟")[i] );
			c.aoeUseTimes = Integer.parseInt( dataDic.get("Aoe作用次数")[i] );
			c.aoeUseInterval = Integer.parseInt( dataDic.get("Aoe作用间隔")[i] );
			
			c.setup();
			//System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	public void setup()
	{
		String[] szPoints = aoeRangeStr.split(";");
		range = new Point[szPoints.length];
		for(int i = 0; i < szPoints.length; i++)
		{
			String[] szPt = szPoints[i].split(",");
			String szPtX = szPt[0];
			String szPtY = szPt[1];
			Point pt = new Point(Integer.parseInt(szPtX), Integer.parseInt(szPtY));
			range[i] = pt;
		}
	}
	
	@Override
	public String toString()
	{
		return "[SkillExtAoeCfg] id:" + id + " name:" + name + " aoeRangeStr=" + aoeRangeStr
				+ " aoePreview=" + aoePreview
				+ " aoeEffect=" + aoeEffect + " aoeEffectTime=" + aoeEffectTime
				+ " aoeLifeTime=" + aoeLifeTime + " aoeUseDelay=" + aoeUseDelay
				+ " aoeUseTimes=" + aoeUseTimes + " aoeUseInterval=" + aoeUseInterval;
	}
}
