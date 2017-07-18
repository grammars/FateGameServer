package game.scene.monster;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import framework.Log;
import game.core.sprite.Monster;
import game.scene.Scene;
import game.scene.SceneManager;
import cfg.MonsterInfoCfg;
import cfg.MonsterPoolCfg;

public class MonsterPool implements Runnable
{
	/** 所属场景 */
	private Scene scene;
	/** 怪物池配置 */
	private MonsterPoolCfg poolCfg;
	/** 怪物信息配置 */
	private MonsterInfoCfg monsterCfg;
	
	ScheduledExecutorService service;
	
	/** 活着的怪物数量 */
	private int activeMonsterCount = 0;
	
	public MonsterPool(MonsterPoolCfg cfg)
	{
		this.poolCfg = cfg;
	}
	
	/** 怪物池开始运作 */
	public void start()
	{
		scene = SceneManager.getScene(poolCfg.mapId);
		if(scene == null)
		{
			Log.game.error("怪物池所需要的Scene不存在");
			return;
		}
		monsterCfg = MonsterInfoCfg.get(poolCfg.monsterId);
		if(monsterCfg == null)
		{
			Log.game.error("怪物池所需要的MonsterInfoCfg不存在");
			return;
		}
		
		createInitMonsters();
		
		service = Executors.newScheduledThreadPool(1);
		service.scheduleAtFixedRate(this, this.poolCfg.frequency, this.poolCfg.frequency, TimeUnit.SECONDS);
	}
	
	/** 怪物池停止运作 */
	public void stop()
	{
		if(service != null)
		{
			service.shutdownNow();
			service = null;
		}
	}
	
	/** 增加活跃怪物数 */
	private void increaseActive()
	{
		activeMonsterCount++;
	}
	/** 减少活跃怪物数 */
	public void decreaseActive()
	{
		activeMonsterCount--;
	}
	/** 已达到最大活跃怪物数 */
	private boolean reachMaxActive()
	{
		return activeMonsterCount >= poolCfg.maxCount;
	}
	
	@Override
	public void run()
	{
		//Log.system.debug("MonsterPool-"+poolCfg.id+"==>running");
		if( reachMaxActive() )
		{
			return;
		}
		Log.system.debug("MonsterPool-"+poolCfg.id+"==>creating");
		
		createMonster();
		
		increaseActive();
	}
	
	/** 创建初始化的怪物 */
	private void createInitMonsters()
	{
		while( !reachMaxActive() )
		{
			createMonster();
			increaseActive();
		}
	}
	
	/** 创建一个monster */
	private void createMonster()
	{
		Monster monster = new Monster();
		monster.setParent(this);
		monster.setConfig(monsterCfg);
		monster.mapId = poolCfg.mapId;
		int offsetX = (int)(Math.random() * (double)poolCfg.rX);
		int offsetY = (int)(Math.random() * (double)poolCfg.rY);
		int bornX = poolCfg.mapX + offsetX;
		int bornY = poolCfg.mapY + offsetY;
		monster.setBornX(bornX);
		monster.setBornY(bornY);
		byte dir = (byte)Math.floor((Math.random() * 8));
		monster.direction = dir;
		scene.addSprite(monster);
	}
	
}
