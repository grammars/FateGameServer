package framework.db;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import framework.Log;

public class BeanDAO
{
	/** 表名 */
	protected String table;
	
	/** 主键名 */
	protected String primaryKey;

	/** 对于bean类 */
	protected Class<?> beanClass;
	
	/** insert的sql语句 */
	protected String insertSql;
	/** 更新的sql语句 */
	protected String updateSql;
	/** 查询的sql语句 */
	protected String querySql;
	/** 更新的sql语句 */
	protected String deletSql;
	
	/** 创建时不被写入的字段 */
	protected Set<String> notInserts = new HashSet<String>();
	/** 更新时不被覆盖的字段 */
	protected Set<String> notUpdates = new HashSet<String>();
	
	public BeanDAO(String table, Class<?> beanClass)
	{
		this.table = table;
		this.beanClass = beanClass;
		initPrimaryKey();
		initNotUseFileds();
		initSql();
	}
	
	/** 初始化主键名 */
	protected void initPrimaryKey()
	{
		primaryKey = "uid";
	}
	
	/** 初始化不使用的字段 */
	protected void initNotUseFileds()
	{
		notInserts.add("unused");
		notUpdates.add("unused");
		notUpdates.add(primaryKey);
	}

	/** 初始化sql语句 */
	protected void initSql()
	{
		this.insertSql = sqlInsert();
		this.updateSql = sqlUpdate();
		this.querySql = sqlQuery();
		this.deletSql = sqlDelete();
	}
	
	/** 根据数据bean生成插入语句 */
	protected String sqlInsert()
	{
		Map<String, Object> propertyMap;
		String insertSql = "";
		try
		{
			Object bean = beanClass.newInstance();
			propertyMap = PropertyUtils.describe(bean);
			StringBuffer sqlSb = new StringBuffer();
			StringBuffer fieldSb = new StringBuffer();
			int valueCount = 0;
			sqlSb.append("insert  into " + table + " (");
			for (Entry<String, Object> entry : propertyMap.entrySet())
			{
				String name = (String) entry.getKey();
				if (!isDbField(name, false))
				{
					continue;
				}
				fieldSb.append(" ");
				fieldSb.append(name);
				fieldSb.append(",");
				valueCount++;
			}
			fieldSb.deleteCharAt(fieldSb.length() - 1);
			sqlSb.append(fieldSb);
			sqlSb.append(") values (");
			for (int i = 0; i < valueCount; i++)
			{
				sqlSb.append(" ?,");
			}
			sqlSb.deleteCharAt(sqlSb.length() - 1);
			sqlSb.append(") on duplicate key update ");
			for (Entry<String, Object> entry : propertyMap.entrySet())
			{
				String name = entry.getKey();
				if (!isDbField(name, false))
				{
					continue;
				}
				sqlSb.append(" ");
				sqlSb.append(name);
				sqlSb.append(" = values(" + name + "),");
			}
			sqlSb.deleteCharAt(sqlSb.length() - 1);
			insertSql = sqlSb.toString();
			Log.db.debug(table + " insert 语句为--->" + insertSql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return insertSql;
	}
	
	/** 根据数据bean生成更新语句 */
	protected String sqlUpdate()
	{
		Map<String, Object> propertyMap;
		String updateSql = "";
		try
		{
			Object bean = beanClass.newInstance();
			propertyMap = PropertyUtils.describe(bean);
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append("update " + table + " set");
			for (Entry<String, Object> entry : propertyMap.entrySet())
			{
				String name = entry.getKey();
				// 不存库或作为条件使用的字段
				if (!isDbField(name, true))
				{
					continue;
				}
				sqlSb.append(" ");
				sqlSb.append(name);
				sqlSb.append(" = ?,");

			}
			sqlSb.deleteCharAt(sqlSb.length() - 1);
			// 获取roleId
			sqlSb.append(" where "+primaryKey+" = ?");
			updateSql = sqlSb.toString();
			Log.db.debug(table + " update 语句为--->" + updateSql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return updateSql;
	}
	
	/** 根据数据bean生成查询语句 */
	protected String sqlQuery()
	{
		return "select * from " + table + " where "+primaryKey+" = ?";
	}
	
	/** 根据数据bean生成删除语句 */
	protected String sqlDelete()
	{
		return "delete from " + table + " where "+primaryKey+" = ?";
	}

	/** 是否是数据库的需要的字段 */
	protected boolean isDbField(String name, boolean isUpdate)
	{
		if (name.equals("class"))
			return false;
		if (isUpdate)
		{
			if (notUpdates.contains(name))
				return false;
			return true;
		}
		else
		{
			if (notInserts.contains(name))
				return false;
			return true;
		}
	}
	
	/**
	 * 存储bean
	 */
	public boolean insertBean(Object bean)
	{
		Connection conn = DbPool.getInstance().getConn();
		PreparedStatement st = null;
		try
		{
			st = conn.prepareStatement(insertSql);
			Object[] values = getBeanValues(bean, false);
			// 设定sql中的? ? ?
			setParameters(st, values);
			st.executeUpdate();
			conn.commit();
			return st.getUpdateCount() > -1;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.close(null, st, conn);
		}
		return false;
	}
	
	/**
	 * 更新bean数据
	 */
	public boolean updateBean(Object bean)
	{
		Connection conn = DbPool.getInstance().getConn();
		if (bean instanceof Collection)
		{
			throw new IllegalArgumentException("参数不能为集合!");
		}
		PreparedStatement st = null;
		try
		{
			st = conn.prepareStatement(updateSql);
			Object[] values = getBeanValues(bean, true);
			// 设定sql中的? ? ?
			setParameters(st, values);
			st.executeUpdate();//st.executeBatch();
			conn.commit();
			return st.getUpdateCount() > -1;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.close(null, st, conn);
		}
		return false;
	}
	
	/**
	 * 查询一个数据 bean
	 */
	public Object queryBean(Object uid)
	{
		Connection conn = DbPool.getInstance().getConn();
		PreparedStatement st = null;
		ResultSet rs = null;
		Object bean = null;
		try
		{
			bean = beanClass.newInstance();
			st = conn.prepareStatement(querySql);
			setParameter(st, 1, uid);
			rs = st.executeQuery();
			if (rs.next())
			{
				setBeanValue(rs, bean);
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
		return bean;
	}

	/**
	 * 删除一个数据 bean
	 */
	public boolean deleteBean(Object uid)
	{
		Connection conn = DbPool.getInstance().getConn();
		PreparedStatement st = null;
		ResultSet rs = null;
		try
		{
			st = conn.prepareStatement(deletSql);
			setParameter(st, 1, uid);
			st.executeUpdate();
			conn.commit();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.close(rs, st, conn);
		}
		return false;
	}
	
	/**
	 * 判读数据类型，设定Parameter
	 * @throws SQLException
	 */
	private static void setParameter(PreparedStatement ps, int i, Object obj)
			throws SQLException
	{
		if (obj == null)
		{
			ps.setObject(i, null);
			return;
		}

		if (obj instanceof byte[])
		{
			ps.setBytes(i, (byte[]) obj);
		}
		else if (obj instanceof Calendar)
		{
			Calendar cal = (Calendar) obj;
			ps.setDate(i, new Date(cal.getTimeInMillis()));
		}
		else
		{
			ps.setObject(i, obj);
		}
	}

	/**
	 * 设定一组参数
	 * @throws SQLException
	 */
	private static void setParameters(PreparedStatement ps, Object[] params)
			throws SQLException
	{
		for (int i = 1; i <= params.length; i++)
		{
			setParameter(ps, i, params[i - 1]);
		}
	}
	
	/** 依据反射,设定bean对象的属性值 */
	protected void setBeanValue(ResultSet rs, Object bean) throws Exception
	{
		String columnName = null;
		Object value = null;
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		if (cols == 0)
			return;
		for (int i = 1; i <= cols; i++)
		{
			columnName = rsmd.getColumnLabel(i);
			value = rs.getObject(i);
			if (value == null)
				continue;
			if (value instanceof java.sql.Date)
			{
				java.sql.Date d = (java.sql.Date) value;
				BeanUtils.setProperty(bean, columnName, new Date(d.getTime()));
			}
			else if (value instanceof java.sql.Timestamp)
			{
				Timestamp d = (Timestamp) value;
				BeanUtils.setProperty(bean, columnName, new Date(d.getTime()));
			}
			else if (value instanceof java.math.BigInteger)
			{
				BigInteger d = (BigInteger) value;
				BeanUtils.setProperty(bean, columnName, Long.parseLong(d
						.toString()));
			}
			else
			{
				try
				{
					BeanUtils.setProperty(bean, columnName, value);
				}
				catch (Exception e)
				{
					System.err.println("object:" + bean + "    columnName:"
							+ columnName + "   value:" + value);
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 得到bean中所有作为参数的字段值
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public Object[] getBeanValues(Object bean, boolean isUpdate)
			throws Exception
	{
		Map<String, Object> propertyMap = PropertyUtils.describe(bean);
		// 获取所有保存的字段
		List<Object> values = new ArrayList<Object>();
		for (Entry<String, Object> ent : propertyMap.entrySet())
		{
			String name = ent.getKey();
			Object value = ent.getValue();
			if (!isDbField(name, isUpdate))
			{
				continue;
			}
			values.add(value);
		}
		if (isUpdate)
		{
			addUpdatekeyParams(propertyMap, values);
		}
		return values.toArray(new Object[values.size()]);
	}

	/** 更新时作为where后的参数，默认为bean的 id */
	private void addUpdatekeyParams(Map<String, Object> propertyMap,
			List<Object> values)
	{
		Object uid = propertyMap.get(primaryKey);
		if (uid != null)
			values.add(uid);
	}


}
