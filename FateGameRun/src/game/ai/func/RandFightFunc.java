package game.ai.func;

import java.util.ArrayList;

import game.core.sprite.Creature;
import game.core.sprite.Sprite;
import game.fight.FightManager;
import game.scene.AStarEngine;
import game.scene.MapData.Grid;
import game.utils.SceneUtils;
import time.Timework;
import utils.GeomUtils;

/** 随意战斗功能 */
public class RandFightFunc extends Timework
{
	private Creature owner;
	private Creature target;
	
	public RandFightFunc(Creature owner)
	{
		this.owner = owner;
	}
	
	/** 开始 */
	public void start(Creature target)
	{
		stop();
		this.target = target;
		owner.status.setBusy("RandFightFunc", true);
		run();
	}
	
	/** 结束 */
	public void stop()
	{
		clearTimeout();
		owner.status.setBusy("RandFightFunc", false);
	}
	
	/** 销毁释放 */
	public void dispose()
	{
		stop();
	}
	
	private boolean available()
	{
		return ( target != null && owner != null && owner.getScene() != null
				&& owner.alive && false == owner.isDeleted()
				&& target.alive && false == target.isDeleted() );
	}
	
	/** 是否朝向目标 */
	private boolean isFaceTar()
	{
		double dist = GeomUtils.distance(owner.x, owner.y, target.x, target.y);
		return (dist <= Sprite.TOWARD_DISTANCE);
	}
	
	@Override
	public void run()
	{
		if( false == available() )
		{ 
			//System.err.println("AI故障::AttackPlayerAction::run()  [false == available()]");
			stop();
			return;
		}
		long delay = 0;
		if(isFaceTar())
		{
			delay = normalHit();
		}
		else
		{
			delay = follow();
		}
		setTimeout(delay);
	}
	
	/** 返回本次跟踪step需要的时间 */
	private long follow()
	{
		AStarEngine.getInstance().setMapData(owner.getScene().getMapData());
		ArrayList<Grid> path = AStarEngine.getInstance().findPath(owner.x, owner.y, target.x, target.y);
		//System.out.println("" + monster.x + "," + monster.y + "-->" + target.x + "," + target.y);
		//dump(path);
		if(path.size() >= 1)
		{
			Grid nextPt = path.remove(0);
			//System.err.println("怪物准备走到" + nextPt);
			int pxDisW = Grid.PIXEL_W * (nextPt.x - owner.x);
			int pxDisH = Grid.PIXEL_H * (nextPt.y - owner.y);
			double pxDis = GeomUtils.distance(pxDisW, pxDisH);
			owner.moveTo(nextPt.x, nextPt.y);
			return (long)(double)( 1000 * pxDis / owner.attris.moveSpeed() );
		}
		return 0;
	}
	
	/** 返回普通攻击时间 */
	public long normalHit()
	{
		byte face = SceneUtils.getTowardsFace(owner.x, owner.y, target.x, target.y);
		owner.direction = face;
		FightManager.hit.prepare(owner, target);
		
		Timework fightExe = new Timework()
		{
			
			@Override
			public void run()
			{
				FightManager.hit.execute(owner, target);
			}
		};
		int interval = (int)(double)( 1000 / owner.attris.attackSpeed() );
		int actionTime = interval;//动作时间 
		if(actionTime > SceneUtils.ACTION_TIME_HIT)
		{
			actionTime = SceneUtils.ACTION_TIME_HIT;
		}
		fightExe.setTimeout(actionTime);
		return (long)interval;
	}
	
	
	@SuppressWarnings("unused")
	private static void dump(ArrayList<Grid> list)
	{
		String debug = "[dump] ";
		for(int i = 0; i < list.size(); i++)
		{
			debug += list.get(i) + "_";
		}
		System.out.println(debug);
	}

}
