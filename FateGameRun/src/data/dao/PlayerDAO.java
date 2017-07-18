package data.dao;

import common.beans.PlayerBean;
import framework.db.BeanDAO;

public class PlayerDAO extends BeanDAO
{
	private static PlayerDAO instance;
	public static PlayerDAO getInstance()
	{
		if(instance == null) { instance = new PlayerDAO(); }
		return instance;
	}
	
	public PlayerDAO()
	{
		super("player", PlayerBean.class);
	}
	
	@Override
	protected void initNotUseFileds()
	{
		super.initNotUseFileds();
		notUpdates.add("createTime");
		notInserts.add("mid");
		notUpdates.add("mid");
	}
}
