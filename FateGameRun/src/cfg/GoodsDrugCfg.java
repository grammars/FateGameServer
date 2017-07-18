package cfg;

import java.util.HashMap;

public class GoodsDrugCfg
{
	public static final String TYPE = "goods_drug";
	
	private static HashMap<Integer, GoodsDrugCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static GoodsDrugCfg get(int id)
	{
		return cfgs.get(id);
	}
	
	/** id */
	public int id = 0;
	/** 物品名 */
	public String name = "";
	/** CD时间 */
	public int cdTime = 0;
	/** 比例生命值 */
	public float ratHp = 0;
	/** 绝对生命值 */
	public int absHp = 0;
	/** 比例魔法值 */
	public float ratMp = 0;
	/** 绝对魔法值 */
	public int absMp = 0;
	/** 增加Buff */
	public int addBuff = 0;
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			GoodsDrugCfg c = new GoodsDrugCfg();
			c.id = Integer.parseInt( dataDic.get("id")[i] );
			c.name = dataDic.get("物品名")[i];
			c.cdTime = Integer.parseInt( dataDic.get("CD时间")[i] );
			c.ratHp = Float.parseFloat( dataDic.get("比例生命值")[i] );
			c.absHp = Integer.parseInt( dataDic.get("绝对生命值")[i] );
			c.ratMp = Float.parseFloat( dataDic.get("比例魔法值")[i] );
			c.absMp = Integer.parseInt( dataDic.get("绝对魔法值")[i] );
			c.addBuff = Integer.parseInt( dataDic.get("增加Buff")[i] );
			System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	@Override
	public String toString()
	{
		return "[GoodsDrugCfg] id:" + id + " name=" + name + " cdTime=" + cdTime
				+ " ratHp=" + ratHp + " absHp=" + absHp + " ratMp=" + ratMp 
				+ " absMp=" + absMp + " addBuff=" + addBuff;
	}
}
