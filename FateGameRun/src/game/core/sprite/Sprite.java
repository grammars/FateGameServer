package game.core.sprite;

import framework.Log;
import framework.net.IBT;
import game.core.action.Actor;
import game.scene.District;
import game.scene.MapData;
import game.scene.Scene;
import game.scene.SceneManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.buffer.IoBuffer;

import cfg.MapDoorCfg;
import message.scene.SceneMsg;
import utils.Utils;
import common.define.SpriteType;
import ds.geom.Point;

public abstract class Sprite extends Actor
{
	/** 面向距离 */
	public static final float TOWARD_DISTANCE = 1.6f;
	
	/** 精灵类型 */
	public byte type = SpriteType.NULL;
	/** 临时id */
	public int tid;
	
	/** 唯一id */
	public long uid;
	/** 朝向 */
	public byte direction;
	/** 地图id */
	public int mapId;
	/** 地图x */
	public int x;
	/** 地图y */
	public int y;
	/** 外观 */
	public String look;
	
	/** 是否需要被释放删除,从内存中彻底移除 */
	protected boolean deleted = false;
	/** 是否需要被释放删除,从内存中彻底移除 */
	public boolean isDeleted() { return deleted; }
	
	/** 关联场景 */
	protected Scene scene;
	/** 所处区域 */
	protected District district;
	/** 移动路径 */
	protected Point[] path;
	/** 当前移动步骤 */
	protected int step;
	
	/** 水平（x轴）方向上视野的最大距离，以玩家为中心计算 */
	protected int viewX = 20;//20;
	public void setViewX(int value) { this.viewX = value; }
	public int getViewX() { return this.viewX; }
	/** 垂直（y轴）方向上视野的最大距离，以玩家为中心计算 */
	protected int viewY = 16;//16
	public void setViewY(int value) { this.viewY = value; }
	public int getViewY() { return this.viewY; }
	
	/** 视野中所有[abs]Sprite */
	protected Map<Integer, Sprite> viewSprite;
	public Map<Integer, Sprite> getViewSprite() { return viewSprite; }
	/** 视野中所有Door */
	protected Map<Integer, Door> viewDoor;
	public Map<Integer, Door> getViewDoor() { return viewDoor; }
	/** 视野中所有Drop */
	protected Map<Integer, Drop> viewDrop;
	public Map<Integer, Drop> getViewDrop() { return viewDrop; }
	/** 视野中所有Effect */
	protected Map<Integer, Effect> viewEffect;
	public Map<Integer, Effect> getViewEffect() { return viewEffect; }
	/** 视野中所有[abs]Creature */
	protected Map<Integer, Creature> viewCreature;
	public Map<Integer, Creature> getView() { return viewCreature; }
	/** 视野中所有Npc */
	protected Map<Integer, Npc> viewNpc;
	public Map<Integer, Npc> getViewNpc() { return viewNpc; }
	/** 视野中所有Monster */
	protected Map<Integer, Monster> viewMonster;
	public Map<Integer, Monster> getViewMonster() { return viewMonster; }
	/** 视野中所有Player */
	protected Map<Integer, Player> viewPlayer;
	public Map<Integer, Player> getViewPlayer() { return viewPlayer; }
	
	protected long bornTime;
	/** 获得存活了的时间(ms) */
	public int getAliveTime()
	{
		return (int)(Utils.now()-bornTime);
	}
	
	public Sprite()
	{
		bornTime = Utils.now();
		
		this.tid = Utils.createTidInt();
		
		viewSprite = new ConcurrentHashMap<>();
		viewDoor = new ConcurrentHashMap<>();
		viewDrop = new ConcurrentHashMap<>();
		viewEffect = new ConcurrentHashMap<>();
		viewCreature = new ConcurrentHashMap<>();
		viewNpc = new ConcurrentHashMap<>();
		viewMonster = new ConcurrentHashMap<>();
		viewPlayer = new ConcurrentHashMap<>();
		
		createParts();
	}
	
	public AoeSkill toAoeSkill() { return (AoeSkill)this; }
	public PathSkill toPathSkill() { return (PathSkill)this; }
	public Door toDoor() { return (Door)this; }
	public Drop toDrop() { return (Drop)this; }
	public Effect toEffect() { return (Effect)this; }
	public Creature toCreature() { return (Creature)this; }
	public Npc toNpc() { return (Npc)this; }
	public Monster toMonster() { return (Monster)this; }
	public Player toPlayer() { return (Player)this; }
	
	/** 构造部件 */
	protected void createParts()
	{
		
	}
	
	/** 附加场景 */
	public void addScene(Scene scene)
	{
		this.scene = scene;
	}
	/** 移除场景 */
	public void removeScene(Scene scene)
	{
		if(this.scene == null) { return; }
		if(this.scene.getId() == scene.getId())
		{
			this.scene = null;
		}
	}
	/** 获得对应场景 */
	public Scene getScene() { return this.scene; }
	
	/** 附加区域 */
	public void addDistrict(District district)
	{
		this.district = district;
	}
	/** 移除区域 */
	public void removeDistrict(District district)
	{
		if(this.district == district)
		{
			this.district = null;
		}
	}
	/** 获取区域 */
	public District getDistrict() { return this.district; }
	
	/** 是否已在视野中 */
	public boolean hasInView(Sprite sp)
	{
		return viewSprite.get(sp.tid) != null;
	}
	
	/** 是否看得到 */
	public boolean canView(Sprite sp)
	{
		if(sp.getScene() != this.scene) { return false; }
		int Δx = Math.abs(this.x - sp.x);
		if(Δx > viewX) { return false; }
		int Δy = Math.abs(this.y - sp.y);
		if(Δy > viewY) { return false; }
		return true;
	}
	
	/** 进入视野 */
	public void addView(Sprite sp)
	{
		viewSprite.put(sp.tid, sp);
		if(sp instanceof Door) { viewDoor.put(sp.tid, (Door)sp); }
		if(sp instanceof Drop) { viewDrop.put(sp.tid, (Drop)sp); }
		if(sp instanceof Effect) { viewEffect.put(sp.tid, (Effect)sp); }
		if(sp instanceof Creature) { viewCreature.put(sp.tid, (Creature)sp); }
		if(sp instanceof Npc) { viewNpc.put(sp.tid, (Npc)sp); }
		if(sp instanceof Monster) { viewMonster.put(sp.tid, (Monster)sp); }
		if(sp instanceof Player) { viewPlayer.put(sp.tid, (Player)sp); }
	}
	
	/** 离开视野 */
	public void removeView(Sprite sp)
	{
		viewSprite.remove(sp.tid);
		viewDoor.remove(sp.tid);
		viewDrop.remove(sp.tid);
		viewEffect.remove(sp.tid);
		viewCreature.remove(sp.tid);
		viewNpc.remove(sp.tid);
		viewMonster.remove(sp.tid);
		viewPlayer.remove(sp.tid);
	}
	
	/** 清理视野 */
	public void clearView()
	{
		viewSprite.clear();
		viewDoor.clear();
		viewDrop.clear();
		viewEffect.clear();
		viewCreature.clear();
		viewNpc.clear();
		viewMonster.clear();
		viewPlayer.clear();
	}
	
	/** 设置移动路径 */
	public void setMovePath(Point[] path)
	{
		if( false == checkMovePath(path) )
		{
			Log.game.error("该移动路径不合法{" + tid + "}");
			moveBack();
			return;
		}
		this.path = path;
		this.step = 0;
		broadMovePathToPlayers(path);
	}
	
	/** 设置移动单步 */
	public void setMoveStep(int tx, int ty)
	{
		if(this.path == null || this.scene == null)
		{ 
			moveBack();
			return;
		}
		Point should = this.path[this.step];
		this.step++;
		if(should.equals(tx, ty))
		{
			this.scene.moveSprite(this, tx, ty);
			if(this.step == this.path.length)
			{
				//System.err.println("这是最后一步了");
				handleStopMove();
				checkOnDoor();
			}
		}
		else
		{
			moveBack();
		}
	}
	
	/** 停止移动 */
	public void stopMove(int tx, int ty)
	{
		this.scene.stopSprite(this, tx, ty);
		handleStopMove();
	}
	
	/** 移动到某坐标点
	 * @param int tx 目标坐标点x
	 * @param int tx 目标坐标点x
	 * @return boolean 是否移动成功 */
	public boolean moveTo(int tx, int ty)
	{
		if(this.scene == null) { return false; }
		if(tx == this.x && ty == this.y) { return true; }
		if( false == this.scene.getMapData().canMove(tx, ty) ) { return false; }
		this.scene.moveSprite(this, tx, ty);
		broadMoveStepToPlayers(tx, ty);
		return true;
	}
	
	/** 处理停止移动 */
	protected void handleStopMove()
	{
		this.step = 0;
		this.path = null;
	}
	
	/** 检查是否在传送门上 */
	protected void checkOnDoor()
	{
		MapDoorCfg cfg = MapDoorCfg.query(this.mapId, this.x, this.y);
		if(cfg != null)
		{
			System.err.println("可以传送到" + cfg);
			changeMap(cfg.toMapId, cfg.toMapX, cfg.toMapY);
		}
	}
	
	/** 改变所在场景地图 */
	public void changeMap(int toMapId, int toMapX, int toMapY)
	{
		this.scene.removeSprite(this);
		this.clearView();
		Scene toScene = SceneManager.getScene(toMapId);
		
		//一定要先发地图改变消息，然后在addSprite，客户端会在接收到地图改变消息的时候清空avatar
		if(this.type == SpriteType.PLAYER)
		{
			SceneMsg.getInstance().sendSceneChangeMap_G2C(toScene, toMapX, toMapY, this.toPlayer().getBundle().getUid());
		}
		
		this.x = toMapX;
		this.y = toMapY;
		toScene.addSprite(this);
	}
	
	/** 回退 */
	protected void moveBack()
	{
		handleStopMove();
		if(this.type == SpriteType.PLAYER)
		{
			SceneMsg.getInstance().sendPlayerMoveBack_G2C(this.x, this.y, this.toPlayer().getBundle().getUid());
		}
	}
	
	/** 检查路径是否合法
	 * @return true:合法 / false:不合法 */
	protected boolean checkMovePath(Point[] path)
	{
		for(int i = 0; i < path.length; i++)
		{
			if(false == checkMovePoint(path[i])) { return false; }
		}
		return true;
	}
	
	/** 检查路径是否合法
	 * @return true:合法 / false:不合法 */
	protected boolean checkMovePoint(Point pt)
	{
		if(scene != null && scene.getMapData() != null)
		{
			MapData md = scene.getMapData();
			return md.canMove(pt.x, pt.y);
		}
		return false;
	}
	
	/** 向周围玩家广播自己的移动路径 */
	protected void broadMovePathToPlayers(Point[] path)
	{
		List<Player> players = this.getNear(false);
		for(Player player : players)
		{
			SceneMsg.getInstance().sendSpriteMovePath_G2C(path, this, player.getBundle().getUid());
		}
	}
	
	/** 向周围玩家广播自己的移动步伐 */
	protected void broadMoveStepToPlayers(int px, int py)
	{
		List<Player> players = this.getNear(false);
		for(Player player : players)
		{
			SceneMsg.getInstance().sendSpriteMoveToPoint_G2C(px, py, this, player.getBundle().getUid());
		}
	}
	
	/** 获得未走完的剩余路径 */
	public Point[] getLeftPath()
	{
		if(this.path == null) return null;
		int leftSize = this.path.length-step;
		if(leftSize <= 0) return null;
		Point [] left = new Point[leftSize];
		for(int i = step, j = 0; i < this.path.length; i++, j++)
		{
			left[j] = this.path[i];
		}
		return left;
	}
	
	/** 获取需要被广播的附近的Player
		@param includeSelf 包含自身 */
	public List<Player> getNear(boolean includeSelf)
	{
		List<Player> list = new ArrayList<>();
		if(scene == null) { return list; }
		District sd = scene.getDistrict(this.x, this.y);
		if(sd == null)
		{
			return list;
		}
		List<District> scope = sd.getScope();
		for(int i = 0; i < scope.size(); i++)
		{
			District dis = scope.get(i);
			Iterator<Sprite> iter = dis.getSpriteMap().values().iterator();
			while(iter.hasNext())
			{
				Sprite sprite = iter.next();
				if(sprite instanceof Player)
				{
					if(sprite == this)
					{
						continue;
					}
					if(sprite.canView(this))
					{
						list.add(sprite.toPlayer());
					}
				}
			}
		}
		if( includeSelf && (this instanceof Player) )
		{
			list.add(this.toPlayer());
		}
		return list;
	}
	
	/** 被Scene调用的更新方法 */
	public void update()
	{
		runAction();
	}
	
	/** 被释放 */
	public void dispose()
	{
		deleted = true;
	}
	
	/** 获得位置信息用于调试打印 */
	public String tracePosition()
	{
		return "[" + this.mapId + "(" + this.x + "," + this.y + ")]";
	}
	
	/** 写入可视对象数据 */
	public void writeAvatar(IoBuffer buffer)
	{
		IBT.writeInt(buffer, getAliveTime());
		IBT.writeByte(buffer, type);
		IBT.writeInt(buffer, tid);
		IBT.writeLong(buffer, uid);
		IBT.writeInt(buffer, mapId);
		IBT.writeInt(buffer, x);
		IBT.writeInt(buffer, y);
		IBT.writeByte(buffer, direction);
		IBT.writeString(buffer, look);
	}
	
	/** 写入IoBuffer[全部数据] */
	public void write(IoBuffer buffer)
	{
		IBT.writeLong(buffer, uid);
		IBT.writeInt(buffer, mapId);
		IBT.writeInt(buffer, x);
		IBT.writeInt(buffer, y);
		IBT.writeByte(buffer, direction);
		IBT.writeString(buffer, look);
	}
	
	/** 从IoBuffer读出[全部数据] */
	public void read(IoBuffer buffer)
	{
		uid = IBT.readLong(buffer);
		mapId = IBT.readInt(buffer);
		x = IBT.readInt(buffer);
		y = IBT.readInt(buffer);
		direction = IBT.readByte(buffer);
		look =  IBT.readString(buffer);
	}
	
	/** toSpriteString */
	protected String toSpriteString()
	{
		return " uid=" + uid
			+ " map=>" + mapId + "(" + x + "," + y + ")" + " direction=" + direction
			+ " look=" + look;
	}
	
	/** toString */
	public String toString()
	{
		return "[Sprite] " + toSpriteString();
	}
	
}
