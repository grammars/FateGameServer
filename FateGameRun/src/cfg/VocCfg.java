package cfg;

import java.util.Collection;
import java.util.HashMap;

public class VocCfg
{
	public static final String TYPE = "voc";
	
	private static HashMap<Byte, VocCfg> cfgs = new HashMap<>();
	/** 根据id获取单个配置 */
	public static VocCfg get(byte id)
	{
		return cfgs.get(id);
	}
	/** 获得全部的VocCfg */
	public static Collection<VocCfg> getAll()
	{
		return cfgs.values();
	}
	
	/** 职业id */
	public byte id = 0;
	/** 职业名称 */
	public String name = "";
	/** 职业描述 */
	public String desc = "";
	/** 普攻远程 */
	public boolean isRemoteHit = false;
	/** 普攻距离 */
	public float hitDistance = 1.6f;
	/** 普攻飞行毫秒 */
	public int hitFlyTime = 0;
	/** 普工飞行特效 */
	public String hitFlyEffect = "null";
	
	
	/** 解析 */
	public static void parse(HashMap<String, String[]> dataDic, int itemCount)
	{
		for(int i = 0; i < itemCount; i++)
		{
			VocCfg c = new VocCfg();
			c.id = Byte.parseByte( dataDic.get("职业id")[i] );
			c.name = dataDic.get("职业名称")[i];
			c.desc = dataDic.get("职业描述")[i];
			c.isRemoteHit = Integer.parseInt( dataDic.get("普攻远程")[i] ) != 0;
			c.hitDistance = Float.parseFloat( dataDic.get("普攻距离")[i] ); 
			c.hitFlyTime = Integer.parseInt( dataDic.get("普攻飞行毫秒")[i] );
			c.hitFlyEffect = dataDic.get("普工飞行特效")[i];
			System.out.println(c);
			cfgs.put(c.id, c);
		}
	}
	
	/** 根据职业id获取职业名称 */
	public static String getVocName(byte vocId)
	{
		VocCfg c = get(vocId);
		if(c != null) { return c.name; }
		return "未定义职业";
	}
	
	@Override
	public String toString()
	{
		return "[VocCfg] id:" + id + " name:" + name + " desc:" + desc
				+ " isRemoteHit:" + isRemoteHit + " hitDistance:" + hitDistance
				+ " hitFlyTime:" + hitFlyTime + " hitFlyEffect:" + hitFlyEffect;
	}
}
