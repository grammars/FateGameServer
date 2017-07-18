package login;

import org.apache.mina.core.buffer.IoBuffer;

import utils.Utils;
import framework.ClientBundleManager;
import framework.SessManager;
import framework.SessHand;

public class LoginSessManager extends SessManager
{
	private static LoginSessManager instance;
	public static LoginSessManager getInstance()
	{
		if(instance == null) { instance = new LoginSessManager(); }
		return instance;
	}
	
	private LoginSessManager() {}
	
	@Override
	public void sessionOpened(SessHand sh)
	{
		if(sh.isClient)
		{
			@SuppressWarnings("unused")
			LoginClientBundle bundle = new LoginClientBundle(Utils.createUidLong(), sh);
		}
	}
	
	@Override
	public void sessionClosed(SessHand sh)
	{
		LoginClientBundle bundle = (LoginClientBundle) ClientBundleManager.getInstance().getBundle(sh);
		if(bundle != null)
		{
			bundle.dispose();
		}
	}

	@Override
	public void messageReceived(SessHand sh, IoBuffer buffer)
	{
		handleRecv(sh, buffer);
	}

}
