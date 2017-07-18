package framework;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log
{
	/** 系统log */
	public static Logger system = Logger.getLogger("[系统]");
	/** 登录log */
	public static Logger login = Logger.getLogger("[登录]");
	/** 游戏log */
	public static Logger game = Logger.getLogger("[游戏]");
	/** DB log */
	public static Logger db = Logger.getLogger("[DB]");
	/** 网关 log */
	public static Logger gate = Logger.getLogger("[网关]");
	
	/** 初始化日志文件 */
	public static void init()
	{
		try
		{
			Properties props = new Properties();
			FileInputStream log4jStream = new FileInputStream(FilePath.getLogCfg());
			props.load(log4jStream);
			log4jStream.close();
			PropertyConfigurator.configure(props);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
