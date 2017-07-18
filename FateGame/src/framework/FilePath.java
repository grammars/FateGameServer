package framework;

import java.io.File;

/** 涉及到本地路径相关的 */
public class FilePath
{
	private static String cfgDir = null;
	/** 获取配置文件夹，已包含/ */
	public static String getCfgDir()
	{
		if(cfgDir == null)
		{
			File f = new File(System.getProperty("user.dir"));
			cfgDir = f.getParent() + File.separator + "FateCfg" + File.separator;
		}
		return cfgDir;
	}
	
	/** 获得SetupCfg */
	public static String getSetupCfg()
	{
		return getCfgDir() + "setup.properties";
	}
	
	/** 获得log4j配置 */
	public static String getLogCfg()
	{
		return getCfgDir() + "log4j.properties";
	}
	
	/** 获得dbcp配置 */
	public static String getDbcpCfg()
	{
		return getCfgDir() + "dbcp.properties";
	}
	
	/** 获得地图配置 */
	public static String getMapData(int mapId)
	{
		return getCfgDir() + "map/" + mapId + ".map";
	}
}
