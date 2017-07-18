package data;

import cfg.CfgManager;
import data.oper.CheckNameDbOper;
import message.login.LoginMsg;
import message.player.PlayerDataMsg;
import framework.AbsSrv;
import framework.AppContext;
import framework.db.DbPool;
import framework.define.SidType;

public class DataSrv extends AbsSrv
{
	private static DataSrv instance;
	public static DataSrv getInstance()
	{
		if(instance == null) { instance = new DataSrv(); }
		return instance;
	}
	
	public DataSrv()
	{
		super(SidType.DATA);
	}
	
	@Override
	public void start()
	{
		AppContext.setSessManager(DataSessManager.getInstance());
		super.start();
		CfgManager.load();
		DbPool.getInstance().initialize();
		CheckNameDbOper.initialize();
	}
	
	@Override
	protected void initMsgHandler()
	{
		super.initMsgHandler();
		AppContext.addMsgHandler(LoginMsg.MID, LoginMsg.getInstance());
		AppContext.addMsgHandler(PlayerDataMsg.MID, PlayerDataMsg.getInstance());
	}

}
