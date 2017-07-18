package game.events.creature;

import flash.events.Event;
import game.core.sprite.parts.CreatureStatus;

public class CreatureStatusEvent extends Event
{
	public static final String BUSY_CHANGED = "CreatureStatusEvent.BUSY_CHANGED";

	public CreatureStatus status;
	
	public CreatureStatusEvent(String type, CreatureStatus status)
	{
		super(type);
		this.status = status;
	}

}
