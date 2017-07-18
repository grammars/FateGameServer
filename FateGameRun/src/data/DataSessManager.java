package data;

import org.apache.mina.core.buffer.IoBuffer;

import framework.SessManager;
import framework.SessHand;

public class DataSessManager extends SessManager
{
	private static DataSessManager instance;
	public static DataSessManager getInstance()
	{
		if(instance == null) { instance = new DataSessManager(); }
		return instance;
	}
	
	private DataSessManager() {}
	
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
