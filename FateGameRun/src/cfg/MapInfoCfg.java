package cfg;

import java.util.Collection;
import java.util.HashMap;

public class MapInfoCfg
{
	public static final String TYPE = "map_info";
	
	private static HashMap<Integer, MapInfoCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static MapInfoCfg get(int id)
	{
		return cfgs.get(id);
	}
	/** 获得全部的MapInfoCfg */
	public static Collection<MapInfoCfg> getAll()
	{
		return cfgs.values();
	}
	
	/** 地图id */
	public int id = 0;
	/** 地图名称 */
	public String name = "";
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			MapInfoCfg c = new MapInfoCfg();
			c.id = Integer.parseInt( dataDic.get("id")[i] );
			c.name = dataDic.get("地图名称")[i];
			//System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	@Override
	public String toString()
	{
		return "[MapInfoCfg] id:" + id + " name:" + name;
	}
}
