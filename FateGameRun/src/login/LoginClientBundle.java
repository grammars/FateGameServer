package login;

import common.struct.login.StAccountInfo;
import common.beans.PlayerBean;
import framework.ClientBundle;
import framework.SessHand;

public class LoginClientBundle extends ClientBundle
{
	public StAccountInfo account = new StAccountInfo();
	
	public PlayerBean player = new PlayerBean();
	
	public LoginClientBundle(Long uid, SessHand clientSess)
	{
		super(uid, clientSess);
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		clientSess.dispose();
	}
	
}
