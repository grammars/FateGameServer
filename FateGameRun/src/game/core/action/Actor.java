package game.core.action;

import java.util.ArrayList;
import java.util.List;

import flash.events.EventDispatcher;

public class Actor extends EventDispatcher
{
	protected List<Action> actions = new ArrayList<>();
	
	public Actor()
	{
		super();
	}
	
	/** 加入Action */
	public void pushAction(Action action)
	{
		actions.add(action);
	}
	
	/** 移除Action */
	public Action removeAction(int id)
	{
		for(int i = actions.size()-1; i >= 0; i--)
		{
			if(actions.get(i).getId() == id)
			{
				return actions.remove(i);
			}
		}
		return null;
	}
	
	/** 清空Actions */
	public void clearActions()
	{
		actions.clear();
	}
	
	/** 运行动作 */
	public void runAction()
	{
		for(int i = 0; i < actions.size(); i++)
		{
			Action action = actions.get(i);
			action.tryRun();
			if(action.isInvalid())
			{
				action.destroy();
				actions.remove(i);
				i--;
			}
		}
	}
}
