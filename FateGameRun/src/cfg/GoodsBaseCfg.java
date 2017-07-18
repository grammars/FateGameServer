package cfg;

import java.util.Collection;
import java.util.HashMap;

public class GoodsBaseCfg
{
	public static final String TYPE = "goods_base";
	
	private static HashMap<Integer, GoodsBaseCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static GoodsBaseCfg get(int id)
	{
		return cfgs.get(id);
	}
	/** 获得全部的GoodsBaseCfg */
	public static Collection<GoodsBaseCfg> getAll()
	{
		return cfgs.values();
	}
	
	/** id */
	public int id = 0;
	/** 物品名 */
	public String name = "";
	/** 类型 */
	public int type = 0;
	/** icon */
	public String icon = "";
	/** 要求等级下限 */
	public int reqLevMin = 0;
	/** 要求等级上限 */
	public int reqLevMax = 0;
	/** 要求职业 */
	public byte reqVoc = 0;
	/** 说明 */
	public String desc = "";
	/** 最大堆叠数 */
	public int maxHeap = 1;
	/** 是否可召回 */
	public boolean canRecall = true;
	/** 召回价 */
	public int recallPrice = 0;
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			GoodsBaseCfg c = new GoodsBaseCfg();
			c.id = Integer.parseInt( dataDic.get("id")[i] );
			c.name = dataDic.get("物品名")[i];
			c.type = Integer.parseInt( dataDic.get("类型")[i] );
			c.icon = dataDic.get("icon")[i];
			c.reqLevMin = Integer.parseInt( dataDic.get("要求等级下限")[i] );
			c.reqLevMax = Integer.parseInt( dataDic.get("要求等级上限")[i] );
			c.reqVoc = Byte.parseByte( dataDic.get("要求职业")[i] );
			c.desc = dataDic.get("说明")[i];
			c.maxHeap = Integer.parseInt( dataDic.get("最大堆叠数")[i] );
			c.canRecall = Integer.parseInt( dataDic.get("是否可召回")[i] ) == 1;
			c.recallPrice = Integer.parseInt( dataDic.get("召回价")[i] );
			System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	@Override
	public String toString()
	{
		return "[GoodsBaseCfg] id:" + id + " name=" + name + " type=" + type
				+ " icon=" + icon + " reqLevMin=" + reqLevMin + " reqLevMax=" + reqLevMax
				+ " reqVoc=" + reqVoc + " desc=" + desc
				+ " maxHeap=" + maxHeap + " canRecall=" + canRecall
				+ " recallPrice=" + recallPrice;
	}
	
}
