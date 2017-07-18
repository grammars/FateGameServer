package framework.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtils
{
	/** 简易执行sql语句 */
	public static void sqlExe(String sql)
	{
		Connection conn = DbPool.getInstance().getConn();
		PreparedStatement st = null;
		try
		{
			st = conn.prepareStatement(sql);
			st.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.close(null, st, conn);
		}
	}
	
	/** 关闭并释放资源 */
	public static void close(ResultSet rs, Statement ps, Connection con)
	{
		try
		{
			if (rs != null)
			{
				rs.close();
			}
			if (ps != null)
			{
				ps.close();
			}
			if (con != null && !con.getAutoCommit())
			{
				con.commit();
			}
			if (con != null && !con.isClosed())
			{
				con.close();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
}
