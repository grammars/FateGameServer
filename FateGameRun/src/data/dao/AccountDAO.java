package data.dao;

import common.struct.login.StAccountInfo;
import framework.db.BeanDAO;

public class AccountDAO extends BeanDAO
{
	private static AccountDAO instance;
	public static AccountDAO getInstance()
	{
		if(instance == null) { instance = new AccountDAO(); }
		return instance;
	}
	
	public AccountDAO()
	{
		super("account", StAccountInfo.class);
	}
	
	@Override
	protected void initNotUseFileds()
	{
		super.initNotUseFileds();
	}
}
