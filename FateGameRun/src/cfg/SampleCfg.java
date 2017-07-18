package cfg;

import java.util.HashMap;

public class SampleCfg
{
	public static final String TYPE = "sample";
	
	private static HashMap<Integer, SampleCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static SampleCfg get(int id)
	{
		return cfgs.get(id);
	}
	
	/** 整数字段 */
	public int id = 0;
	/** 字符串字段 */
	public String name = "";
	/** 浮点字段 */
	public double score = 0;
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			SampleCfg c = new SampleCfg();
			c.id = Integer.parseInt( dataDic.get("整数字段")[i] );
			c.name = dataDic.get("字符串字段")[i];
			c.score = Double.parseDouble( dataDic.get("浮点字段")[i] );
			//System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	@Override
	public String toString()
	{
		return "[SampleCfg] id:" + id + " name:" + name
				+ " score=" + score;
	}
	
}
