package login;

import message.login.LoginMsg;
import message.player.PlayerDataMsg;
import framework.AbsSrv;
import framework.AppContext;
import framework.SConnector;
import framework.define.SidType;

public class LoginSrv extends AbsSrv
{
	private static LoginSrv instance;
	public static LoginSrv getInstance()
	{
		if(instance == null) { instance = new LoginSrv(); }
		return instance;
	}
	
	private SConnector connData;
	private SConnector connGame;
	
	public LoginSrv()
	{
		super(SidType.LOGIN);
	}

	@Override
	public void start()
	{
		AppContext.setSessManager(LoginSessManager.getInstance());
		super.start();
		connData = new SConnector(SidType.LOGIN, SidType.DATA);
		connData.start();
		connGame = new SConnector(SidType.LOGIN, SidType.GAME);
		connGame.start();
	}
	
	@Override
	protected void initMsgHandler()
	{
		super.initMsgHandler();
		AppContext.addMsgHandler(LoginMsg.MID, LoginMsg.getInstance());
		AppContext.addMsgHandler(PlayerDataMsg.MID, PlayerDataMsg.getInstance());
	}
}
