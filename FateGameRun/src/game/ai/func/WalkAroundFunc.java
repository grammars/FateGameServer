package game.ai.func;

import cfg.MonsterInfoCfg;
import common.Macro;
import game.core.sprite.Creature;
import game.core.sprite.Monster;
import time.Timework;

public class WalkAroundFunc extends Timework
{
	private Creature target;
	
	public WalkAroundFunc(Creature target)
	{
		this.target = target;
	}
	
	/** 开始 */
	public void start()
	{
		run();
	}
	
	/** 结束 */
	public void stop()
	{
		clearTimeout();
	}
	
	/** 销毁释放 */
	public void dispose()
	{
		clearTimeout();
	}
	
	/** 获得随机的-1,0,1 */
	private int createOffsetOne()
	{
		double rand = Math.random();
		if(rand < 0.4)
		{
			return -1;
		}
		else if(rand > 0.6)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	@Override
	public void run()
	{
		//行走
		if(!target.alive) { return; }
		int speed = target.attris.moveSpeed();
		if(speed <= 0) { return; }
		int deltX = createOffsetOne();
		int deltY = createOffsetOne();
		
		if(deltX != 0 && deltY != 0)
		{
			int tx = this.target.x + deltX;
			int ty = this.target.y + deltY;
			
			if(this.target instanceof Monster)
			{
				//不可超过巡视半径
				MonsterInfoCfg mcfg = this.target.toMonster().getConfig();
				int mbx = this.target.toMonster().getBornX();
				int mby = this.target.toMonster().getBornY();
				if(Math.abs(tx-mbx) > mcfg.walkRX)
				{
					tx = this.target.x;
				}
				if(Math.abs(ty-mby) > mcfg.walkRY)
				{
					ty = this.target.y;
				}
			}
			
			this.target.moveTo(tx, ty);
			
			int dpX = Macro.GridW * deltX;
			int dpY = Macro.GridH * deltY;
			double distSqr = dpX*dpX + dpY*dpY;
			long costTime = (long) ( 1000f * Math.sqrt(distSqr)/speed );
			setTimeout(costTime);
		}
		else
		{
			setTimeout(1000);
		}
	}

}
