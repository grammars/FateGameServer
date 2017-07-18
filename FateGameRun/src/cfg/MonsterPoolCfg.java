package cfg;

import java.util.Collection;
import java.util.HashMap;

public class MonsterPoolCfg
{
	public static final String TYPE = "monster_pool";
	
	private static HashMap<Integer, MonsterPoolCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static MonsterPoolCfg get(int id)
	{
		return cfgs.get(id);
	}
	/** 获得全部的MapInfoCfg */
	public static Collection<MonsterPoolCfg> getAll()
	{
		return cfgs.values();
	}
	
	/** id */
	public int id = 0;
	/** 地图id */
	public int mapId = 0;
	/** 地图x */
	public int mapX = 0;
	/** 地图y */
	public int mapY = 0;
	/** 范围半径x */
	public int rX = 0;
	/** 范围半径y */
	public int rY = 0;
	/** 刷新频率 */
	public int frequency = 0;
	/** 怪物id */
	public int monsterId = 0;
	/** 最大数量 */
	public int maxCount = 0;
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			MonsterPoolCfg c = new MonsterPoolCfg();
			c.id = Integer.parseInt( dataDic.get("id")[i] );
			c.mapId = Integer.parseInt( dataDic.get("地图id")[i] );
			c.mapX = Integer.parseInt( dataDic.get("地图x")[i] );
			c.mapY = Integer.parseInt( dataDic.get("地图y")[i] );
			c.rX = Integer.parseInt( dataDic.get("范围半径x")[i] );
			c.rY = Integer.parseInt( dataDic.get("范围半径y")[i] );
			c.frequency = Integer.parseInt( dataDic.get("刷新频率")[i] );
			c.monsterId = Integer.parseInt( dataDic.get("怪物id")[i] );
			c.maxCount = Integer.parseInt( dataDic.get("最大数量")[i] );
			
			cfgs.put(c.id, c);
			System.out.println(c);
		}
	}
	
	@Override
	public String toString()
	{
		return "[MonsterPoolCfg] id:" + id + " mapId:" + mapId + " mapX=" + mapX + " mapY=" + mapY
				+ " rX=" + rX + " rY=" + rY + " frequency=" + frequency
				+ " monsterId=" + monsterId + " maxCount=" + maxCount;
	}
}
