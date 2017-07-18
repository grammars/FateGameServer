package data.oper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Iterator;

import framework.db.DbPool;
import framework.db.DbUtils;

public class CheckNameDbOper
{
	/** 内存缓存的名字 */
	private static HashSet<String> namesCache = new HashSet<>();
	
	public static void initialize()
	{
		Connection conn = DbPool.getInstance().getConn();
		PreparedStatement st = null;
		ResultSet rs = null;
		String querySql = "SELECT * FROM `names`";
		try
		{
			st = conn.prepareStatement(querySql);
			rs = st.executeQuery();
			while (rs.next())
			{
				namesCache.add(rs.getString("name"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.close(rs, st, conn);
		}
		
		printCache();
	}
	
	public static boolean exist(String name)
	{
		if(namesCache.contains(name)) { return true; }
		return false;
	}
	
	public static void add(String name)
	{
		namesCache.add(name);
		//向names表中插入name
		String sql = "INSERT INTO `names` (`name`) VALUES ('"+name+"');";
		DbUtils.sqlExe(sql);
	}
	
	public static void remove(String name)
	{
		namesCache.remove(name);
		//从names表中移除name;
		String sql = "DELETE FROM `names` WHERE `name` = '"+name+"'";
		DbUtils.sqlExe(sql);
	}
	
	public static void printCache()
	{
		Iterator<String> iter = namesCache.iterator();
		System.out.print("数据库中的名字表：");
		while(iter.hasNext())
		{
			System.out.print(iter.next() + ", ");
		}
		System.out.println();
	}
	
}
