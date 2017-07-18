package common.component.buff;

import java.util.ArrayList;
import java.util.List;

import message.buff.BuffMsg;
import common.struct.buff.StBuffSet;
import game.core.sprite.Creature;
import game.core.sprite.Player;

public class BuffSet
{
	private Creature target;
	
	private ArrayList<Buff> list = new ArrayList<>();
	
	public BuffSet()
	{
	}
	
	public BuffSet(Creature target)
	{
		this.target = target;
	}
	
	/** 获取Buff */
	public Buff get(int id)
	{
		for(int i = 0; i < list.size(); i++)
		{
			if(id == list.get(i).id)
			{
				return list.get(i);
			}
		}
		return null;
	}
	
	/** 添加Buff */
	public Buff add(Buff buff)
	{
		//根据最大叠加数销毁过量的同类型buff
		int sameOldNum = 0;
		for(int i = list.size()-1; i >= 0; i--)
		{
			Buff old = list.get(i);
			if(old.config.id == buff.config.id)
			{
				sameOldNum++;
				if(sameOldNum > buff.config.maxStack)
				{
					tellBuffRemove(old);
					
					old.destroy();
					list.remove(i);
				}
			}
		}
		//添加
		list.add(buff);
		
		tellBuffAdd(buff);
		
		return buff;
	}
	
	/** 移除Buff */
	public Buff remove(int id)
	{
		for(int i = 0; i < list.size(); i++)
		{
			Buff item = list.get(i);
			if(id == item.id)
			{
				tellBuffRemove(item);
				
				return list.remove(i);
			}
		}
		return null;
	}
	
	/** 被Creature调用的update */
	public void update()
	{
		for(int i = 0; i < list.size(); i++)
		{
			Buff buff = list.get(i);
			buff.tryRun();
			if(buff.isInvalid())
			{
				tellBuffRemove(buff);
				buff.destroy();
				list.remove(i);
				i--;
			}
		}
	}
	
	/** 在下线保存数据库之前，清除所有不需要存数据库的buff */
	public void removeUnneedStore()
	{
		for(int i = list.size()-1; i >= 0; i--)
		{
			Buff item = list.get(i);
			if(item.config.needStore==false)
			{
				item.destroy();
				list.remove(i);
			}
		}
	}
	
	/** 被Creature调用的dispose */
	public void dispose()
	{
		if(list != null)
		{
			for(int i = 0; i < list.size(); i++)
			{
				Buff buff = list.get(i);
				buff.destroy();
			}
			this.list.clear();
			this.list = null;
		}
		this.target = null;
	}
	
	/** 告诉客户端Buff添加 */
	private void tellBuffAdd(Buff buff)
	{
		if(target == null) { return; }
		List<Player> players = target.getNear(true);
		for(Player player : players)
		{
			BuffMsg.getInstance().sendAddBuff_G2C(buff, player.getBundle().getUid());
		}
	}
	
	/** 告诉客户端Buff移除 */
	private void tellBuffRemove(Buff buff)
	{
		if(target == null) { return; }
		List<Player> players = target.getNear(true);
		for(Player player : players)
		{
			BuffMsg.getInstance().sendRemoveBuff_G2C(buff, player.getBundle().getUid());
		}
	}
	
	private void build()
	{
		
	}
	
	/** 导出结构数据 */
	public StBuffSet exportData()
	{
		StBuffSet data = new StBuffSet();
		for(int i = 0; i < list.size(); i++)
		{
			Buff buff = list.get(i);
			data.items.add(buff.exportData());
		}
		return data;
	}
	
	/** 导入结构数据 */
	public void importData(StBuffSet data)
	{
		list.clear();
		for(int i = 0; i < data.items.size(); i++)
		{
			Buff buff = new Buff();
			buff.target = target;
			buff.importData(data.items.get(i));
			list.add(buff);
		}
		build();
	}
	
}
