package game.scene;

import game.core.sprite.Creature;
import game.core.sprite.Player;
import game.core.sprite.Sprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import utils.Utils;
import ds.geom.Point;

/** 扫描区域 */
public class District
{
	/** 区域的水平宽度 */
	public static final int SIZE_HORIZONTAL = 16;//16
	/** 区域的垂直高度 */
	public static final int SIZE_VERTICAL = 9;//9
	
	/** 区域唯一id */
	private int id;
	/** 获取区域唯一id */
	public int getId() { return id; }
	
	/** 从属场景 */
	private Scene parent;
	/** 序号X */
	private int iX;
	public int getIX() { return iX; }
	/** 序号Y */
	private int iY;
	public int getIY() { return iY; }
	
	/** 扫描范围Point,包含本区域和周边区域 */
	private List<Point> scopePts;
	/** 扫描范围,包含本区域和周边区域 */
	private List<District> scope;
	/** 获取扫描区域范围 */
	public List<District> getScope() { return scope; }
	
	/** 区域中所有[abs]Sprite */
	protected Map<Integer, Sprite> spriteMap;
	public Map<Integer, Sprite> getSpriteMap() { return spriteMap; }
	/** 区域中所有[abs]Creature */
	protected Map<Integer, Creature> creatureMap;
	public Map<Integer, Creature> getCreatureMap() { return creatureMap; }
	/** 区域中所有Player */
	protected Map<Integer, Player> playerMap;
	public Map<Integer, Player> getPlayerMap() { return playerMap; }
	
	public District(int iX, int iY, int districtCX, int districtCY, Scene parent)
	{
		this.id = Utils.createTidInt();
		this.iX = iX;
		this.iY = iY;
		this.parent = parent;
		spriteMap = new ConcurrentHashMap<>();
		creatureMap = new ConcurrentHashMap<>();
		playerMap = new ConcurrentHashMap<>();
		
		scopePts = new ArrayList<>();
		for(int y = -1; y <= 1; y++)
		{
			for(int x = -1; x <= 1; x++)
			{
				int tx = iX + x;
				int ty = iY + y;
				if (tx < 0 || ty < 0 || tx >= districtCX || ty >= districtCY)
				{
					continue;
				}
				scopePts.add(new Point(tx, ty));
			}
		}
	}
	
	/** 启动区域服务 */
	public void start()
	{
		initScope();
	}
	
	/** 停止区域服务 */
	public void stop()
	{
		
	}
	
	/** 初始化扫描范围 */
	private void initScope()
	{
		scope = new ArrayList<>();
		for(int i = 0; i < scopePts.size(); i++)
		{
			Point pt = scopePts.get(i);
			District dis = this.parent.getDistrictByIndex(pt.x, pt.y);
			scope.add(dis);
		}
	}
	
	public void addSprite(Sprite sp)
	{
		sp.addDistrict(this);
		spriteMap.put(sp.tid, sp);
		if(sp instanceof Creature)
		{
			creatureMap.put(sp.tid, (Creature)sp);
		}
		if(sp instanceof Player)
		{
			playerMap.put(sp.tid, (Player)sp);
		}
	}
	
	public Sprite removeSprite(Sprite sp)
	{
		return removeSprite(sp.tid);
	}
	public Sprite removeSprite(Integer tid)
	{	
		creatureMap.remove(tid);
		playerMap.remove(tid);
		Sprite sp = spriteMap.remove(tid);
		if(sp != null) { sp.removeDistrict(this); }
		return sp;
	}
	
	/** 获得指定点上的Sprite */
	public List<Sprite> getSpritesOnPoint(int x, int y)
	{
		List<Sprite> them = new ArrayList<>();
		Iterator<Entry<Integer, Sprite>> iter = spriteMap.entrySet().iterator();
		while(iter.hasNext())
		{
			Sprite sp = iter.next().getValue();
			if( sp.x==x && sp.y==y )
			{
				them.add(sp);
			}
		}
		return them;
	}
	
	public boolean equals(District d)
	{
		if(d == null) { return false; }
		if(this.getId() == d.getId()) { return true; }
		return false;
	}
	
	public String toString()
	{
		return "[District] id=" + id + "(" + iX + "," + iY + ")";
	}
	
}
