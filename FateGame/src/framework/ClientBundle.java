package framework;

public class ClientBundle
{
	/** 唯一id */
	protected Long uid;
	public Long getUid() { return uid; }
	
	/** 关联客户端会话[可能不存在,Game Data就不存在关联客户端会话] */
	protected SessHand clientSess;
	public SessHand getClientSess() { return clientSess; }
	
	/** 最近一次活跃时间 */
	private long lastActiveTime;
	
	public ClientBundle(Long uid, SessHand clientSess)
	{
		init(uid, clientSess);
	}
	
	public ClientBundle(Long uid)
	{
		init(uid, null);
	}
	
	protected void init(Long uid, SessHand clientSess)
	{
		this.uid = uid;
		this.clientSess = clientSess;
		active();
		ClientBundleManager.getInstance().addBundle(this);
	}
	
	/** 激活一下 */
	public void active()
	{
		lastActiveTime = System.currentTimeMillis();
	}
	
	/** 获得超时时间(ms) */
	protected long getTimeout()
	{
		return 3600*1000;
	}
	
	/** 判断是否超时 */
	public boolean isTimeout()
	{
		long pass = System.currentTimeMillis() - lastActiveTime;
		return pass > getTimeout();
	}
	
	/** 释放掉 */
	public void dispose()
	{
		ClientBundleManager.getInstance().removeBundle(uid);
	}
}
