package game.events;

import flash.events.Event;

public class MonsterEvent extends Event
{
	/** NULL */
	public static final String NULL = "MonsterEvent.NULL";
	
	public MonsterEvent(String type)
	{
		super(type);
	}
}
