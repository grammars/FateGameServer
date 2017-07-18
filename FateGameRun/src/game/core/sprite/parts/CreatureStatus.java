package game.core.sprite.parts;

import flash.events.EventDispatcher;
import game.events.creature.CreatureStatusEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** Creature's status */
public class CreatureStatus extends EventDispatcher
{
	public CreatureStatus()
	{
		//
	}
	
	//=======忙碌状态======
	private Map<String, Boolean> busyStatus = new HashMap<>();
	private boolean busy = false;
	/** 是否忙碌 */
	public boolean isBusy() { return busy; }
	
	/** 获取细节忙碌 */
	public Boolean getBusy(String src)
	{
		return busyStatus.get(src);
	}
	
	/** 设置细节忙碌
	 * @param src 细节源
	 * @param value true:忙碌 false:空闲 */
	public void setBusy(String src, Boolean value)
	{
		if(value == true)
		{
			busyStatus.put(src, value);
		}
		else
		{
			busyStatus.remove(src);
		}
		updateBusy();
	}
	
	private void updateBusy()
	{
		Iterator<Boolean> iter = busyStatus.values().iterator();
		boolean curBusy = false;
		while(iter.hasNext())
		{
			Boolean val = iter.hasNext();
			if(val==true)
			{
				curBusy = true;
				break;
			}
		}
		changeBusy(curBusy);
	}
	
	private void changeBusy(boolean value)
	{
		if(busy != value)
		{
			busy = value;
			CreatureStatusEvent event = new CreatureStatusEvent(CreatureStatusEvent.BUSY_CHANGED, this);
			this.dispatchEvent(event);
		}
	}
	
	/** 强制取消所有忙碌状态 */
	public void idle()
	{
		busyStatus.clear();
		busy = false;
		changeBusy(false);
	}
	
	//=======XX状态======
	
}
