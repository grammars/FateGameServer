package gate;

import framework.ClientBundle;
import framework.Log;
import framework.SessHand;

public class GateClientBundle extends ClientBundle
{
	
	public GateClientBundle(Long uid, SessHand clientSess)
	{
		super(uid, clientSess);
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		clientSess.dispose();
		Log.gate.debug("网关断开与客户端的连接");
	}
	
}
