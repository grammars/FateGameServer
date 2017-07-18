package framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientBundleManager implements Runnable
{	
	private static ClientBundleManager instance;
	public static ClientBundleManager getInstance()
	{
		if(instance == null) { instance = new ClientBundleManager(); }
		return instance;
	}
	
	/** key:clientBundleUid[Long] value:bundle[ClientBundle] */
	private Map<Long, ClientBundle> bundleMap = new HashMap<Long, ClientBundle>();
	
	/** key:sh[SessHand] value:bundle[ClientBundle] */
	private Map<SessHand, ClientBundle> sessBundleMap = new HashMap<SessHand, ClientBundle>();
	
	ScheduledExecutorService service;
	
	private ClientBundleManager()
	{
		//
	}
	
	/** 添加一个ClientBundle */
	public void addBundle(ClientBundle bundle)
	{
		bundleMap.put(bundle.getUid(), bundle);
		if(bundle.getClientSess() != null)
		{
			sessBundleMap.put(bundle.getClientSess(), bundle);
		}
	}
	/** 获得一个ClientBundle */
	public ClientBundle getBundle(Long uid)
	{
		return bundleMap.get(uid);
	}
	/** 获得一个ClientBundle通过SessHand */
	public ClientBundle getBundle(SessHand sh)
	{
		return sessBundleMap.get(sh);
	}
	/** 移除一个ClientBundle */
	public ClientBundle removeBundle(Long uid)
	{
		ClientBundle bundle = bundleMap.remove(uid);
		if(bundle != null && bundle.getClientSess() != null)
		{
			sessBundleMap.remove(bundle.getClientSess());
		}
		return bundle;
	}
	
//	/** 尝试获取ClientBundle，如果不存在，就new一个 */
//	public <T extends ClientBundle> T retrieveBundle(Long uid, Class<T> clazz, Object...args)
//	{
//		@SuppressWarnings("unchecked")
//		T bundle = (T) getBundle(uid);
//		if(bundle != null)
//		{
//			return bundle;
//		}
//		bundle = Utils.newInstance(clazz, args);
//		return bundle;
//	}
	
	/** 启动 */
	public void start()
	{
		stop();
		service = Executors.newScheduledThreadPool(1);
		service.scheduleAtFixedRate(this, 5, 15, TimeUnit.SECONDS);
	}
	
	/** 停止 */
	public void stop()
	{
		if(service != null)
		{
			service.shutdownNow();
			service = null;
		}
	}
	
	@Override
	public void run()
	{
		//Log.system.debug("ClientBundleManager==>running");
		ArrayList<ClientBundle> toBeRemoved = new ArrayList<>();
		Iterator<Entry<Long, ClientBundle>> iter = bundleMap.entrySet().iterator();
		while(iter.hasNext())
		{
			ClientBundle bundle = iter.next().getValue();
			if(bundle.isTimeout())
			{
				Log.system.fatal("bundle("+bundle.getUid()+")因超时被丢弃");
				toBeRemoved.add(bundle);
			}
		}
		for(int i = 0; i < toBeRemoved.size(); i++)
		{
			removeBundle( toBeRemoved.get(i).getUid() );
		}
	}
	
}
