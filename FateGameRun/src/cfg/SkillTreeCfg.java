package cfg;

import java.util.HashMap;

import utils.StrParser;
import utils.ToStr;

public class SkillTreeCfg
{
	public static final String TYPE = "skill_tree";
	
	private static HashMap<Integer, SkillTreeCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static SkillTreeCfg get(int id)
	{
		return cfgs.get(id);
	}
	
	/** 所属职业 */
	public int id = 0;
	/** 描述 */
	public String desc = "";
	/** 技能ids */
	public int[] skillIds = new int[0];
	/** 技能levels */
	public int[] skillLevels = new int[0];
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			SkillTreeCfg c = new SkillTreeCfg();
			c.id = Integer.parseInt( dataDic.get("所属职业")[i] );
			c.desc = dataDic.get("描述")[i];
			
			String skillIdsStr = dataDic.get("技能ids")[i];
			c.skillIds = StrParser.str2arr(skillIdsStr, ",", (int)0);
			String skillLevelsStr = dataDic.get("技能levels")[i];
			c.skillLevels = StrParser.str2arr(skillLevelsStr, ",", (int)0);
			
			System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	@Override
	public String toString()
	{
		return "[SkillTreeCfg] id:" + id + " desc:" + desc
				+ " skillIds=" + ToStr.t(skillIds) + " skillLevels=" + ToStr.t(skillLevels);
	}
	
}
