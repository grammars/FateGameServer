package game.ai.base;

import game.ai.CreatureAI;
import game.ai.func.WalkAroundFunc;
import game.core.sprite.Creature;
import game.scene.Scene;

public class WalkerAI extends CreatureAI
{
	protected WalkAroundFunc walkFunc;
	
	public WalkerAI (Creature owner)
	{
		super(owner);
		
		walkFunc = new WalkAroundFunc(owner);
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
	public void dead()
	{
		walkFunc.stop();
	}
	
	@Override
	public void dispose()
	{
		walkFunc.stop();
		walkFunc = null;
	}
	
}
