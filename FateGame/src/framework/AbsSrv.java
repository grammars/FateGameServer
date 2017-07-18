package framework;

import message.FrameworkMsg;
import framework.define.SidType;

public abstract class AbsSrv
{	
	/** [SidType] */
	protected byte srvType;
	
	protected SAcceptor acceptor;
	
	public AbsSrv(byte srvType)
	{
		this.srvType = srvType;
		AppContext.setSidType(this.srvType);
	}
	
	/** 开启服务 */
	public void start()
	{
		Log.init();
		SetupCfg.load();
		
		initMsgHandler();
		
		acceptor = new SAcceptor(this.srvType);
		acceptor.start();
		
		ClientBundleManager.getInstance().start();
	}
	
	/** 初始化消息处理 */
	protected void initMsgHandler()
	{
		AppContext.addMsgHandler(FrameworkMsg.MID, FrameworkMsg.getInstance());
	}
	
	/** 关闭服务 */
	public void stop()
	{
		if(acceptor != null)
		{
			acceptor.stop();
		}
		
		ClientBundleManager.getInstance().stop();
	}
}
