import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.lf5.LogLevel;
import org.apache.mina.core.buffer.IoBuffer;

import utils.StrParser;
import utils.Utils;
import framework.ClientBundle;
import framework.ClientBundleManager;
import framework.Log;
import framework.SetupCfg;
import framework.db.DbPool;


public class Tester
{

	public static void main(String[] args)
	{
		SetupCfg.load();
		Log.init();
		
		/*
		ClientBundle[] bs = new ClientBundle[6];
		for(int i = 0; i < bs.length; i++)
		{
			bs[i] = new ClientBundle(new Long(i));
			ClientBundleManager.getInstance().addBundle(bs[i]);
		}
		ClientBundleManager.getInstance().start();
		*/
		
		boolean[] result = StrParser.str2arr("true,4,-1,0,false", ",", true);
		//show( result );
	}
	
	private static void show(boolean[] arr)
	{
		for(int i = 0; i < arr.length; i++)
		{
			System.out.println(arr[i]);
		}
	}
	
}
