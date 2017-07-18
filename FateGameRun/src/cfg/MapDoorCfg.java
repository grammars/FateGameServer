package cfg;

import java.util.Collection;
import java.util.HashMap;

public class MapDoorCfg
{
	public static final String TYPE = "map_door";
	
	private static HashMap<Integer, MapDoorCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static MapDoorCfg get(int id)
	{
		return cfgs.get(id);
	}
	/** 获得全部的MapDoorCfg */
	public static Collection<MapDoorCfg> getAll()
	{
		return cfgs.values();
	}
	/** 根据fromMapId,fromMapX,fromMapY获取MapDoorCfg */
	public static MapDoorCfg query(int fromMapId, int fromMapX, int fromMapY)
	{
		Collection<MapDoorCfg> coll = getAll();
		for(MapDoorCfg c : coll)
		{
			if(c.fromMapId==fromMapId && c.fromMapX==fromMapX && c.fromMapY==fromMapY)
			{
				return c;
			}
		}
		return null;
	}
	
	/** id */
	public int id = 0;
	/** fromMapId */
	public int fromMapId = 0;
	/** fromMapX */
	public int fromMapX = 0;
	/** fromMapY */
	public int fromMapY = 0;
	/** toMapId */
	public int toMapId = 0;
	/** toMapX */
	public int toMapX = 0;
	/** toMapY */
	public int toMapY = 0;
	/** desc */
	public String desc = "";
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			MapDoorCfg c = new MapDoorCfg();
			c.id = Integer.parseInt( dataDic.get("id")[i] );
			c.fromMapId = Integer.parseInt( dataDic.get("fromMapId")[i] );
			c.fromMapX = Integer.parseInt( dataDic.get("fromMapX")[i] );
			c.fromMapY = Integer.parseInt( dataDic.get("fromMapY")[i] );
			c.toMapId = Integer.parseInt( dataDic.get("toMapId")[i] );
			c.toMapX = Integer.parseInt( dataDic.get("toMapX")[i] );
			c.toMapY = Integer.parseInt( dataDic.get("toMapY")[i] );
			c.desc = ( dataDic.get("desc")[i] );
			System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	@Override
	public String toString()
	{
		return "[MapDoorCfg] id:" + id + " fromMapId:" + fromMapId
				+ " fromMapX:" + fromMapX + " fromMapY:" + fromMapY
				+ " toMapId:" + toMapId + " toMapX:" + toMapX
				+ " toMapY:" + toMapY + " desc:" + desc;
	}
}
