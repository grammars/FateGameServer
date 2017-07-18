package cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelfLookCfg
{
	public static final String TYPE = "self_look";
	
	private static List<SelfLookCfg> cfgs = new ArrayList<>();
	/** 获取外观id
	 * @param voc 职业
	 * @param sex 性别
	 * @param pracLev 修炼等级 */
	public static String getLook(int voc, int sex, int pracLev)
	{
		for(SelfLookCfg cfg : cfgs)
		{
			if( cfg.voc == voc && cfg.sex == sex
				&& pracLev >= cfg.pracLevMin && pracLev < cfg.pracLevMax )
			{
				return cfg.look;
			}
		}
		return "";
	}
	
	/** 序号 */
	public int id = 0;
	/** 职业 */
	public byte voc = 0;
	/** 性别 */
	public byte sex = 0;
	/** 修炼等级下限
	 * [下限,上限) */
	public int pracLevMin = 0;
	/** 修炼等级上限
	 * [下限,上限) */
	public int pracLevMax = 0;
	/** 外观id */
	public String look = "";
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			SelfLookCfg c = new SelfLookCfg();
			c.id = Integer.parseInt( dataDic.get("序号")[i] );
			c.voc = Byte.parseByte( dataDic.get("职业")[i] );
			c.sex = Byte.parseByte( dataDic.get("性别")[i] );
			c.pracLevMin = Integer.parseInt( dataDic.get("修炼等级下限")[i] );
			c.pracLevMax = Integer.parseInt( dataDic.get("修炼等级上限")[i] );
			c.look = dataDic.get("外观id")[i];
			//System.out.println(c);
			cfgs.add(c);
		}
	}
	
	@Override
	public String toString()
	{
		return "[SelfLookCfg] id:" + id + " voc:" + voc
				+ " sex=" + sex + " lev:[" + pracLevMin + "," + pracLevMax + ") look=" + look;
	}
}
