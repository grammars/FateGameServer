package game;

import java.util.HashMap;

public class Global
{
	/** key:playerUid, value:GameClientBundle */
	private static HashMap<Long, GameClientBundle> playerBundleMap = new HashMap<>();
	/** 添加GameClientBundle */
	public static void addBundle(Long playerUid, GameClientBundle bundle)
	{
		GameClientBundle oldBundle = Global.playerBundleMap.remove(playerUid);
		if(oldBundle != null)
		{
			System.err.println("角色：" + bundle.player.name + "被顶号");
			oldBundle.dispose();
		}
		playerBundleMap.put(playerUid, bundle);
	}
	/** 移除GameClientBundle */
	public static GameClientBundle removeBundle(Long playerUid)
	{
		GameClientBundle bundle = playerBundleMap.remove(playerUid);
		return bundle;
	}
	
}
