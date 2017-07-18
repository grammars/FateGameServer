package gate;

import message.player.PlayerEventMsg;
import framework.AbsSrv;
import framework.AppContext;
import framework.SConnector;
import framework.define.SidType;

public class GateSrv extends AbsSrv
{
	private static GateSrv instance;
	public static GateSrv getInstance()
	{
		if(instance == null) { instance = new GateSrv(); }
		return instance;
	}
	
	private SConnector connGame;
	
	public GateSrv()
	{
		super(SidType.GATE);
	}

	@Override
	public void start()
	{
		AppContext.setSessManager(GateSessManager.getInstance());
		super.start();
		connGame = new SConnector(SidType.GATE, SidType.GAME);
		connGame.start();
	}
	
	@Override
	protected void initMsgHandler()
	{
		super.initMsgHandler();
		AppContext.addMsgHandler(PlayerEventMsg.MID, PlayerEventMsg.getInstance());
	}
	
}
