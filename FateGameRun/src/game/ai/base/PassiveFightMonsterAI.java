package game.ai.base;

import game.ai.CreatureAI;
import game.ai.func.RandFightFunc;
import game.ai.func.WalkAroundFunc;
import game.core.sprite.Creature;
import game.events.creature.CreatureStatusEvent;
import game.scene.Scene;

public class PassiveFightMonsterAI extends CreatureAI
{
	protected WalkAroundFunc walkFunc;
	protected RandFightFunc fightFunc;
	
	public PassiveFightMonsterAI (Creature owner)
	{
		super(owner);
		walkFunc = new WalkAroundFunc(owner);
		fightFunc = new RandFightFunc(owner);
		owner.status.addEventListener(CreatureStatusEvent.BUSY_CHANGED, this.getClass(), "__statusBusyChanged", CreatureStatusEvent.class, this);
	}
	
	public void __statusBusyChanged(CreatureStatusEvent event)
	{
		if( event.status.isBusy() )
		{
			walkFunc.stop();
		}
		else
		{
			walkFunc.start();
		}
	}
	
	@Override
	public void addScene(Scene scene)
	{
		walkFunc.start();
	}
	
	@Override
	public void removeScene(Scene scene)
	{
		walkFunc.stop();
	}
	
	@Override
	public void normalHitFrom(Creature attacker)
	{
		fightFunc.start(attacker);
	}
	
	@Override
	public void update()
	{
		//
	}
	
	@Override
	public void dead()
	{
		walkFunc.stop();
		fightFunc.stop();
	}
	
	@Override
	public void dispose()
	{
		walkFunc.dispose();
		walkFunc = null;
		fightFunc.dispose();
		fightFunc = null;
	}
}
