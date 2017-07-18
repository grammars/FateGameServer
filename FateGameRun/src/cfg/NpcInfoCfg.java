package cfg;

import java.util.Collection;
import java.util.HashMap;

public class NpcInfoCfg
{
	public static final String TYPE = "npc_info";
	
	private static HashMap<Integer, NpcInfoCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static NpcInfoCfg get(int id)
	{
		return cfgs.get(id);
	}
	/** 获得全部的NpcInfoCfg */
	public static Collection<NpcInfoCfg> getAll()
	{
		return cfgs.values();
	}
	
	/** npcId */
	public int id = 0;
	/** 名字 */
	public String name = "";
	/** 外观id */
	public String look = "";
	/** mapId */
	public int mapId = 0;
	/** mapX */
	public int mapX = 0;
	/** mapY */
	public int mapY = 0;
	/** 方向 */
	public byte direction = 0;
	/** 对话内容 */
	public String talkContent = "";
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			NpcInfoCfg c = new NpcInfoCfg();
			c.id = Integer.parseInt( dataDic.get("id")[i] );
			c.name = dataDic.get("名字")[i];
			c.look = dataDic.get("外观id")[i];
			c.mapId = Integer.parseInt( dataDic.get("mapId")[i] );
			c.mapX = Integer.parseInt( dataDic.get("mapX")[i] );
			c.mapY = Integer.parseInt( dataDic.get("mapY")[i] );
			c.direction = Byte.parseByte( dataDic.get("方向")[i] );
			c.talkContent = dataDic.get("对话内容")[i];
			System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	@Override
	public String toString()
	{
		return "[NpcInfoCfg] id:" + id + " name:" + name
				+ " look=" + look + " map=" + mapId + "(" + mapX + "," + mapY + ")"
				+ " direction=" + direction;
	}
}
