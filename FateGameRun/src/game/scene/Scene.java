package game.scene;

import framework.Log;
import framework.net.IBT;
import game.core.sprite.Creature;
import game.core.sprite.Door;
import game.core.sprite.Drop;
import game.core.sprite.Effect;
import game.core.sprite.Monster;
import game.core.sprite.Npc;
import game.core.sprite.Player;
import game.core.sprite.Sprite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import message.scene.SceneMsg;

import org.apache.mina.core.buffer.IoBuffer;

import cfg.MapInfoCfg;
import utils.VennUtils;
import common.Macro;
import common.define.SpriteType;
import ds.geom.Point;

public class Scene implements Runnable
{
	protected MapData mapData;
	public void setMapData(MapData value)
	{
		this.mapData = value;
	}
	public MapData getMapData() { return this.mapData; }
	
	protected MapInfoCfg config;
	public void setConfig(MapInfoCfg value)
	{
		this.config = value;
	}
	public MapInfoCfg getConfig() { return this.config; }
	
	
	/** 获取场景编号[只读] */
	public int getId() { return mapData.mapId; }
	
	/** 场景区域集合x轴数量 */
	private int districtCX;
	/** 场景区域集合y轴数量 */
	private int districtCY;
	/** 场景区域集合 */
	protected District[][] districts;
	
	/** 场景中所有[abs]Sprite */
	protected Map<Integer, Sprite> spriteMap;
	/** 场景中所有Door */
	protected Map<Integer, Door> doorMap;
	/** 场景中所有Drop */
	protected Map<Integer, Drop> dropMap;
	/** 场景中所有Effect */
	protected Map<Integer, Effect> effectMap;
	/** 场景中所有[abs]Creature */
	protected Map<Integer, Creature> creatureMap;
	/** 场景中所有Npc */
	protected Map<Integer, Npc> npcMap;
	/** 场景中所有Monster */
	protected Map<Integer, Monster> monsterMap;
	/** 场景中所有Player */
	protected Map<Integer, Player> playerMap;
	
	public Sprite getSprite(int tid) { return spriteMap.get(tid); }
	public Door getDoor(int tid) { return doorMap.get(tid); }
	public Drop getDrop(int tid) { return dropMap.get(tid); }
	public Effect getEffect(int tid) { return effectMap.get(tid); }
	public Creature getCreature(int tid) { return creatureMap.get(tid); }
	public Npc getNpc(int tid) { return npcMap.get(tid); }
	public Monster getMonster(int tid) { return monsterMap.get(tid); }
	public Player getPlayer(int tid) { return playerMap.get(tid); }
	
	public ScheduledExecutorService service;
	
	public Scene()
	{
		service = Executors.newScheduledThreadPool(1);
		
		spriteMap = new ConcurrentHashMap<>();
		doorMap = new ConcurrentHashMap<>();
		dropMap = new ConcurrentHashMap<>();
		effectMap = new ConcurrentHashMap<>();
		creatureMap = new ConcurrentHashMap<>();
		npcMap = new ConcurrentHashMap<>();
		monsterMap = new ConcurrentHashMap<>();
		playerMap = new ConcurrentHashMap<>();
	}
	
	/** 启动场景服务 */
	public void start()
	{
		this.districtCX = (int)Math.ceil((double)mapData.gridCountX/District.SIZE_HORIZONTAL);
		this.districtCY = (int)Math.ceil((double)mapData.gridCountY/District.SIZE_VERTICAL);
		Log.game.debug("地图id" + this.getId() + " districtCX=" + districtCX + " districtCY=" + districtCY);
		this.districts = new District[districtCY][districtCX];
		for(int y = 0; y < districtCY; y++)
		{
			for(int x = 0; x < districtCX; x++)
			{
				this.districts[y][x] = new District(x, y, districtCX, districtCY, this);
			}
		}
		for(int y = 0; y < districtCY; y++)
		{
			for(int x = 0; x < districtCX; x++)
			{
				this.districts[y][x].start();
			}
		}
		service.scheduleAtFixedRate(this, 10000, Macro.FRAME_TIME, TimeUnit.MILLISECONDS);
	}
	
	/** 停止场景服务 */
	public void stop()
	{
		service.shutdown();
		for(int y = 0; y < districtCY; y++)
		{
			for(int x = 0; x < districtCX; x++)
			{
				this.districts[y][x].stop();
			}
		}
	}
	
	/**
	 * 通过坐标点找到定位到场景中的某个区域
	 * @param gx 场景坐标点x
	 * @param gy 场景坐标点y
	 * @return 场景区域
	 */
	public District getDistrict(int gx, int gy)
	{
		int iX = (int)Math.floor((double)gx/District.SIZE_HORIZONTAL);
		int iY = (int)Math.floor((double)gy/District.SIZE_VERTICAL);
		return getDistrictByIndex(iX, iY);
	}
	
	/**
	 * 通过序号找到定位到场景中的某个区域
	 * @param iX 区域序号X
	 * @param iY 区域序号Y
	 * @return 场景区域
	 */
	public District getDistrictByIndex(int iX, int iY)
	{
		if(iX < 0 || iY < 0)
		{
			return null;
		}
		if(iY >= this.districts.length || iX >= this.districts[0].length)
		{
			return null;
		}
		return this.districts[iY][iX];
	}
	
	/** 获得指定点上的Sprite */
	public List<Sprite> getSpritesOnPoint(int x, int y)
	{
		List<Sprite> them = new ArrayList<>();
		District dis = getDistrict(x, y);
		if(dis!=null) { return dis.getSpritesOnPoint(x,y); }
		return them;
	}
	
	/** 获取指定范围里的Creature <br>
	 * xRange=[x,x+width) <br>
	 * yRange=[y,y+height) <br>
	 * @param x 起始坐标x点
	 * @param y 起始坐标y点
	 * @param width 矩形范围的宽
	 * @param height 矩形范围的高 */
	public List<Creature> getCreaturesInRect(int x, int y, int width, int height)
	{
		List<Creature> them = new ArrayList<>();
		List<District> scope = getDistrict(x, y).getScope();
		for(District dis : scope)
		{
			Iterator<Creature> iter = dis.getCreatureMap().values().iterator();
			while(iter.hasNext())
			{
				Creature cret = iter.next();
				if(cret.x >= x && cret.x < x+width && cret.y >= y && cret.y < y+height)
				{
					them.add(cret);
				}
			}
		}
		return them;
	}
	
	/** 添加Sprite */
	public void addSprite(Sprite sp)
	{
		Log.game.debug("添加Sprite=>" + sp + " " + sp.tid);
		
		sp.mapId = this.mapData.mapId;
		
		spriteMap.put(sp.tid, sp);
		if(sp instanceof Door) { doorMap.put(sp.tid, (Door)sp); }
		if(sp instanceof Drop) { dropMap.put(sp.tid, (Drop)sp); }
		if(sp instanceof Effect) { effectMap.put(sp.tid, (Effect)sp); }
		if(sp instanceof Creature) { creatureMap.put(sp.tid, (Creature)sp); }
		if(sp instanceof Npc) { npcMap.put(sp.tid, (Npc)sp); }
		if(sp instanceof Monster) { monsterMap.put(sp.tid, (Monster)sp); }
		if(sp instanceof Player) { playerMap.put(sp.tid, (Player)sp); }
		
		District enter = getDistrict(sp.x, sp.y);
		if(enter == null)
		{
			Log.game.error("获取District为null,有超出区域的对象存在 ==>" + sp);
			return;
		}
		enter.addSprite(sp);
		sp.addScene(this);
		interactAdd(sp, enter.getScope());
	}
	
	/** 添加到场景中引发的互动处理 */
	private void interactAdd(Sprite source, List<District> scope)
	{
		List<Sprite> others =  new ArrayList<>();
		for(int i = 0; i < scope.size(); i++)
		{
			District dis = scope.get(i);
			Iterator<Sprite> iter = dis.getSpriteMap().values().iterator();
			while(iter.hasNext())
			{
				Sprite other = iter.next();
				
				if(other == source) { continue; }
				
				if( other.canView(source) && (!other.hasInView(source)) )
				{
					other.addView(source);
					if(other instanceof Player)
					{
						Long otherCBID = other.toPlayer().getBundle().getUid();
						SceneMsg.getInstance().sendSceneAddSprite_G2C(otherCBID, source);
					}
				}
				
				if( source.canView(other) && (!source.hasInView(other)) )
				{
					source.addView(other);
					others.add(other);
				}
			}
		}
		
		if(others.size() > 0)
		{
			if(source.type == SpriteType.PLAYER)
			{
				Long clientBundleId = source.toPlayer().getBundle().getUid();
				SceneMsg.getInstance().sendSceneAddSprites_G2C(clientBundleId, others);
			}
		}
	}
	
	/** 移除Sprite */
	public void removeSprite(Sprite sp)
	{
		removeSprite(sp.tid);
	}
	/** 移除Sprite */
	public void removeSprite(Integer tid)
	{
		Sprite sp = spriteMap.remove(tid);
		
		Log.game.debug("移除Sprite" + tid + " " + sp);
		
		doorMap.remove(tid);
		dropMap.remove(tid);
		effectMap.remove(tid);
		creatureMap.remove(tid);
		npcMap.remove(tid);
		monsterMap.remove(tid);
		playerMap.remove(tid);
		
		if(sp == null) { return; }
		
		District leave = getDistrict(sp.x, sp.y);
		if(leave == null)
		{
			Log.game.error("获取District为null,有超出区域的对象存在 ==>" + sp);
			return;
		}
		leave.removeSprite(sp);
		interactRemove(sp, leave.getScope());
		sp.removeScene(this);
	}
	
	/** 从场景中移除引发的互动处理 */
	private void interactRemove(Sprite source, List<District> scope)
	{
		List<Sprite> others =  new ArrayList<>();
		
		for(District dis : scope)
		{
			Iterator<Sprite> iter = dis.getSpriteMap().values().iterator();
			while(iter.hasNext())
			{
				Sprite other = iter.next();
				if(other == source) { continue; }
				
				if( other.hasInView(source) )
				{
					other.removeView(source);
					if(other instanceof Player)
					{
						Long otherCBID = other.toPlayer().getBundle().getUid();
						SceneMsg.getInstance().sendSceneRemoveSprite_G2C(otherCBID, source.tid);
					}
				}
				
				if( source.hasInView(other) )
				{
					source.removeView(other);
					others.add(other);
				}
			}
		}
		
		if(others.size() > 0)
		{
			if(source.type == SpriteType.PLAYER)
			{
				Long clientBundleId = source.toPlayer().getBundle().getUid();
				List<Integer> tids = new ArrayList<>();
				Iterator<Sprite> iter = others.iterator();
				while(iter.hasNext())
				{
					tids.add(iter.next().tid);
				}
				SceneMsg.getInstance().sendSceneRemoveSprites_G2C(clientBundleId, tids);
			}
		}
	}
	
	/** sprite移动 */
	public void moveSprite(Sprite sprite, int tx, int ty)
	{
		//int oldX = sprite.getX();
		//int oldY = sprite.getY();
		District oldDis = sprite.getDistrict();
		District newDis = getDistrict(tx, ty);
		
		sprite.x = tx;
		sprite.y = ty;
		if(!oldDis.equals(newDis))
		{
			oldDis.removeSprite(sprite);
			newDis.addSprite(sprite);
		}
		
		List<District> scope = VennUtils.merge(oldDis.getScope(), newDis.getScope());
		interactMove(sprite, scope);
	}
	
	/** 在场景中移动引发的互动处理 */
	private void interactMove(Sprite source, List<District> scope)
	{
		List<Sprite> othersToAdd =  new ArrayList<>();
		List<Sprite> othersToRemove =  new ArrayList<>();
		
		for(District dis : scope)
		{
			Iterator<Sprite> iter = dis.getSpriteMap().values().iterator();
			while(iter.hasNext())
			{
				Sprite other = iter.next();
				if(other == source) { continue; }
				
				//原先other看不到source，现在other能够看到source了
				if( ( !other.hasInView(source) ) && other.canView(source) )
				{
					other.addView(source);
					if(other instanceof Player)
					{
						Long otherCBID = other.toPlayer().getBundle().getUid();
						SceneMsg.getInstance().sendSceneAddSprite_G2C(otherCBID, source);
						
						tellNewDisMoving(source, other.toPlayer());
					}
				}
				
				//原先other已看到source，现在other却看不到source了
				if( other.hasInView(source) && ( !other.canView(source) ) )
				{
					other.removeView(source);
					if(other instanceof Player)
					{
						Long otherCBID = other.toPlayer().getBundle().getUid();
						SceneMsg.getInstance().sendSceneRemoveSprite_G2C(otherCBID, source.tid);
					}
				}
				
				//原来other就能看到source，现在other依旧能够看得到source
				if( other.hasInView(source) && other.canView(source) )
				{
					//
				}
				
				//原来source看不到other，现在source可以看到other了
				if( ( !source.hasInView(other) ) && source.canView(other) )
				{
					source.addView(other);
					othersToAdd.add(other);
				}
				
				//原来source已看到other，现在source看不到other了
				if( source.hasInView(other) && ( !source.canView(other) ) )
				{
					source.removeView(other);
					othersToRemove.add(other);
				}
			}
		}
		
		if(othersToAdd.size() > 0)
		{
			if(source.type == SpriteType.PLAYER)
			{
				Long clientBundleId = source.toPlayer().getBundle().getUid();
				SceneMsg.getInstance().sendSceneAddSprites_G2C(clientBundleId, othersToAdd);
			}
		}
		
		if(othersToRemove.size() > 0)
		{
			if(source.type == SpriteType.PLAYER)
			{
				Long clientBundleId = source.toPlayer().getBundle().getUid();
				List<Integer> tids = new ArrayList<>();
				Iterator<Sprite> iter = othersToRemove.iterator();
				while(iter.hasNext())
				{
					tids.add(iter.next().tid);
				}
				SceneMsg.getInstance().sendSceneRemoveSprites_G2C(clientBundleId, tids);
			}
		}
	}
	
	private void tellNewDisMoving(Sprite mover, Player player)
	{
		Point[] leftPath = mover.getLeftPath();
		if(leftPath == null) { return; }
		long clientBundleId = player.getBundle().getUid();
		SceneMsg.getInstance().sendSpriteMovePath_G2C(leftPath, mover, clientBundleId);
	}
	
	
	/** sprite止步 */
	public void stopSprite(Sprite sprite, int tx, int ty)
	{
		//int oldX = sprite.getX();
		//int oldY = sprite.getY();
		District oldDis = sprite.getDistrict();
		District newDis = getDistrict(tx, ty);
		if(oldDis == null || newDis == null)
		{
			return;
		}
		
		sprite.x = tx;
		sprite.y = ty;
		if(!oldDis.equals(newDis))
		{
			oldDis.removeSprite(sprite);
			newDis.addSprite(sprite);
		}
		
		List<District> scope = VennUtils.merge(oldDis.getScope(), newDis.getScope());
		interactStop(sprite, scope);
	}
	
	/** 在场景中止步引发的互动处理 */
	private void interactStop(Sprite source, List<District> scope)
	{	
		for(District dis : scope)
		{
			Iterator<Sprite> iter = dis.getSpriteMap().values().iterator();
			while(iter.hasNext())
			{
				Sprite other = iter.next();
				if(other == source) { continue; }
				
				if(other instanceof Player)
				{
					Long otherCBID = other.toPlayer().getBundle().getUid();
					SceneMsg.getInstance().sendSpriteMoveStop_G2C(source, otherCBID);
				}
			}
		}	
	}
	
	@Override
	public void run()
	{
		long begTime = System.currentTimeMillis();
		
		try
		{
			Collection<Sprite> sprites = spriteMap.values();
			
			Iterator<Sprite> iter = sprites.iterator();
			while(iter.hasNext())
			{
				Sprite sprite = iter.next();
				sprite.update();
			}
		}
		catch(Exception e)
		{
			//不try catch的话，一旦 try中抛出异常，会使ScheduledExecutorService停止
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		long costTime = endTime - begTime;
		
		if(costTime > Macro.FRAME_TIME)
		{
			Log.game.error("场景更新消耗超时：" + costTime);
		}
	}
	
	/** 写入场景信息 */
	public void writeSceneInfo(IoBuffer buffer)
	{
		IBT.writeInt(buffer, mapData.mapId);
	}
	
}
