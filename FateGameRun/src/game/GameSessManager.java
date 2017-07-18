package game;

import org.apache.mina.core.buffer.IoBuffer;

import framework.SessManager;
import framework.SessHand;

public class GameSessManager extends SessManager
{
	private static GameSessManager instance;
	public static GameSessManager getInstance()
	{
		if(instance == null) { instance = new GameSessManager(); }
		return instance;
	}

	private GameSessManager() {}
	
	@Override
	public void sessionOpened(SessHand sh)
	{
		//
	}
	
	@Override
	public void sessionClosed(SessHand sh)
	{
		//
	}
	
	@Override
	public void messageReceived(SessHand sh, IoBuffer buffer)
	{
		handleRecv(sh, buffer);
	}

}
