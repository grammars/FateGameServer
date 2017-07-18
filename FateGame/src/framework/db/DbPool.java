package framework.db;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import framework.FilePath;

public class DbPool
{
	private static DbPool instance;
	public static DbPool getInstance()
	{
		if(instance == null) { instance = new DbPool(); }
		return instance;
	}
	
	private BasicDataSource ds;
	
	public void initialize()
	{
		InputStream in = null;
		Properties p = new Properties();
		try
		{

			String cfgPath = FilePath.getDbcpCfg();
			in = new FileInputStream(cfgPath);
			p.load(in);
			in.close();
			ds = BasicDataSourceFactory.createDataSource(p);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/** 获取连接 ,提交方式为手动提交 */
	public synchronized Connection getConn()
	{
		Connection conn = null;
		try
		{
			conn = ds.getConnection();
			conn.setAutoCommit(false);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return conn;
	}
	
	/** 关闭连接池 */
	public synchronized void release()
	{
		try
		{
			if(ds != null)
			{
				ds.close();
				ds = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
