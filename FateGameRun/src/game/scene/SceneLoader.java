package game.scene;

import java.util.Iterator;

import utils.FileUtils;
import cfg.MapDoorCfg;
import cfg.MapInfoCfg;
import cfg.MonsterPoolCfg;
import cfg.NpcInfoCfg;
import framework.FilePath;
import framework.Log;
import game.core.sprite.Door;
import game.core.sprite.Npc;
import game.npc.NpcManager;
import game.scene.monster.MonsterPool;

public class SceneLoader
{
	/** 加载所有的场景 */
	public static void loadScenes()
	{
		Iterator<MapInfoCfg> iter = MapInfoCfg.getAll().iterator();;
		while(iter.hasNext())
		{
			MapInfoCfg cfg = iter.next();
			byte[] bytes =  FileUtils.readBytesFile(FilePath.getMapData(cfg.id));
			MapData data = new MapData();
			data.decode(bytes);
			Scene scene = new Scene();
			scene.setMapData(data);
			scene.setConfig(cfg);
			SceneManager.addScene(data.mapId, scene);
			scene.start();
		}
	}
	
	/** 加载npc */
	public static void loadNpcs()
	{
		Iterator<NpcInfoCfg> iter = NpcInfoCfg.getAll().iterator();
		while(iter.hasNext())
		{
			NpcInfoCfg cfg = iter.next();
			Npc npc = new Npc();
			npc.setConfig(cfg);
			Scene scene = SceneManager.getScene(cfg.mapId);
			if(scene == null)
			{
				Log.game.error("NPC找不到对应的场景 " + cfg);
				continue;
			}
			scene.addSprite(npc);
			NpcManager.regNpc(cfg.id, npc);
		}
	}
	
	/** 加载传送门 */
	public static void loadDoors()
	{
		Iterator<MapDoorCfg> iter = MapDoorCfg.getAll().iterator();
		while(iter.hasNext())
		{
			MapDoorCfg cfg = iter.next();
			Door door = new Door();
			door.setConfig(cfg);
			Scene scene = SceneManager.getScene(cfg.fromMapId);
			if(scene == null)
			{
				Log.game.error("传送门找不到对应的场景 " + cfg);
				continue;
			}
			scene.addSprite(door);
		}
	}
	
	/** 加载怪物 */
	public static void loadMonsters()
	{
		Iterator<MonsterPoolCfg> iter = MonsterPoolCfg.getAll().iterator();
		while(iter.hasNext())
		{
			MonsterPoolCfg cfg = iter.next();
			MonsterPool pool = new MonsterPool(cfg);
			pool.start();
		}
	}
	
}
