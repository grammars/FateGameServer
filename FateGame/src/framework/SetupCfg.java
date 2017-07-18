package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import framework.define.SidType;

public class SetupCfg
{
	private static boolean LOADED = false;
	
	/** 加载配置文件 */
	public static void load()
	{
		LOADED = true;
		File file = new File(FilePath.getSetupCfg());
		FileInputStream inputStream;
		Properties p = new Properties();
		try
		{
			inputStream = new FileInputStream(file);
			p.load(inputStream); 
		}
		catch (FileNotFoundException e)
		{
			LOADED = false;
			e.printStackTrace();
		}
		catch (IOException e)
		{   
			LOADED = false;
			e.printStackTrace();    
		}
		
		if(LOADED)
		{
			SetupCfg.AUTHOR = p.getProperty("AUTHOR");
			SetupCfg.APP_ID = p.getProperty("APP_ID");
			SetupCfg.APP_SKEY = p.getProperty("APP_SKEY");
			SetupCfg.LOCAL_IP = p.getProperty("LOCAL_IP");
			SetupCfg.LOGIN_SRV_IP = p.getProperty("LOGIN_SRV_IP");
			SetupCfg.LOGIN_SRV_PORT = Integer.parseInt(p.getProperty("LOGIN_SRV_PORT"));
			SetupCfg.GATE_SRV_IP = p.getProperty("GATE_SRV_IP");
			SetupCfg.GATE_SRV_PORT = Integer.parseInt(p.getProperty("GATE_SRV_PORT"));
			SetupCfg.GAME_SRV_IP = p.getProperty("GAME_SRV_IP");
			SetupCfg.GAME_SRV_PORT = Integer.parseInt(p.getProperty("GAME_SRV_PORT"));
			SetupCfg.DATA_SRV_IP = p.getProperty("DATA_SRV_IP");
			SetupCfg.DATA_SRV_PORT = Integer.parseInt(p.getProperty("DATA_SRV_PORT"));
			SetupCfg.INIT_MAP_ID = Integer.parseInt(p.getProperty("INIT_MAP_ID"));
			SetupCfg.INIT_MAP_X = Integer.parseInt(p.getProperty("INIT_MAP_X"));
			SetupCfg.INIT_MAP_Y = Integer.parseInt(p.getProperty("INIT_MAP_Y"));
		}
		
		print();
	}
	
	/** 打印配置 */
	private static void print()
	{
		String ret = LOADED ? "配置加载成功\n" : "配置加载失败\n";
		ret += "AUTHOR:" + AUTHOR + "\n";
		ret += "APP_ID:" + APP_ID + "\n";
		ret += "APP_SKEY:" + APP_SKEY + "\n";
		ret += "LOCAL_IP:" + LOCAL_IP + "\n";
		ret += "LOGIN_SRV_IP:" + LOGIN_SRV_IP + "\n";
		ret += "LOGIN_SRV_PORT:" + LOGIN_SRV_PORT + "\n";
		ret += "GATE_SRV_IP:" + GATE_SRV_IP + "\n";
		ret += "GATE_SRV_PORT:" + GATE_SRV_PORT + "\n";
		ret += "GAME_SRV_IP:" + GAME_SRV_IP + "\n";
		ret += "GAME_SRV_PORT:" + GAME_SRV_PORT + "\n";
		ret += "DATA_SRV_IP:" + DATA_SRV_IP + "\n";
		ret += "DATA_SRV_PORT:" + DATA_SRV_PORT + "\n";
		ret += "INIT_MAP_ID:" + INIT_MAP_ID + "\n";
		ret += "INIT_MAP_X:" + INIT_MAP_X + "\n";
		ret += "INIT_MAP_Y:" + INIT_MAP_Y + "\n";
		System.out.println(ret);
	}
	
	/** 根据SidType获得目标IP */
	public static String getIpBySidType(byte sidType)
	{
		switch(sidType)
		{
		case SidType.LOGIN: return SetupCfg.LOGIN_SRV_IP;
		case SidType.GATE: return SetupCfg.GATE_SRV_IP;
		case SidType.GAME: return SetupCfg.GAME_SRV_IP;
		case SidType.DATA: return SetupCfg.DATA_SRV_IP;
		default: return null;
		}
	}
	
	/** 根据SidType获得目标port */
	public static int getPortBySidType(byte sidType)
	{
		switch(sidType)
		{
		case SidType.LOGIN: return SetupCfg.LOGIN_SRV_PORT;
		case SidType.GATE: return SetupCfg.GATE_SRV_PORT;
		case SidType.GAME: return SetupCfg.GAME_SRV_PORT;
		case SidType.DATA: return SetupCfg.DATA_SRV_PORT;
		default: return 0;
		}
	}
	
	/** 作者 */
	public static String AUTHOR;
	/** 应用的id */
	public static String APP_ID;
	/** 应用的密钥 */
	public static String APP_SKEY;
	/** 默认本机IP */
	public static String LOCAL_IP;
	/** LoginSrv的IP */
	public static String LOGIN_SRV_IP;
	/** LoginSrv的port */
	public static int LOGIN_SRV_PORT;
	/** GateSrv的IP */
	public static String GATE_SRV_IP;
	/** GateSrv的port */
	public static int GATE_SRV_PORT;
	/** GameSrv的IP */
	public static String GAME_SRV_IP;
	/** GameSrv的port */
	public static int GAME_SRV_PORT;
	/** DataSrv的IP */
	public static String DATA_SRV_IP;
	/** DataSrv的port */
	public static int DATA_SRV_PORT;
	/** 初始化地图id */
	public static int INIT_MAP_ID;
	/** 初始化地图x */
	public static int INIT_MAP_X;
	/** 初始化地图y */
	public static int INIT_MAP_Y;
	
}
