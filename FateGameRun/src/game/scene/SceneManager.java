package game.scene;

import game.core.sprite.Creature;
import game.core.sprite.Monster;
import game.core.sprite.Sprite;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class SceneManager
{
	/** 启动并加载场景 */
	public static void start()
	{
		SceneLoader.loadScenes();
		SceneLoader.loadNpcs();
		SceneLoader.loadDoors();
		SceneLoader.loadMonsters();
	}
	
	/** 停止并释放场景 */
	public static void stop()
	{
		
	}
	
	/** key:地图id, value:场景Scene */
	private static HashMap<Integer, Scene> scenes = new HashMap<>();
	/** 添加场景 */
	public static void addScene(Integer mapId, Scene scene)
	{
		scenes.put(mapId, scene);
	}
	/** 获取场景 */
	public static Scene getScene(Integer mapId)
	{
		return scenes.get(mapId);
	}
	
	//======================场景搜查====BEG=======================================
	
	/** 在所有场景中搜查Sprite */
	public static Sprite getSprite(int tid)
	{
		Iterator<Entry<Integer, Scene>> iter = scenes.entrySet().iterator();
		Sprite target = null;
		while(iter.hasNext())
		{
			Scene scene = iter.next().getValue();
			target = scene.getSprite(tid);
			if(target != null) { break; }
		}
		return target;
	}
	
	/** 在所有场景中搜查Creature */
	public static Creature getCreature(int tid)
	{
		Iterator<Entry<Integer, Scene>> iter = scenes.entrySet().iterator();
		Creature target = null;
		while(iter.hasNext())
		{
			Scene scene = iter.next().getValue();
			target = scene.getCreature(tid);
			if(target != null) { break; }
		}
		return target;
	}
	
	/** 在所有场景中搜查Monster */
	public static Monster getMonster(int tid)
	{
		Iterator<Entry<Integer, Scene>> iter = scenes.entrySet().iterator();
		Monster target = null;
		while(iter.hasNext())
		{
			Scene scene = iter.next().getValue();
			target = scene.getMonster(tid);
			if(target != null) { break; }
		}
		return target;
	}
	
	//======================场景搜查====END=======================================
	
}
