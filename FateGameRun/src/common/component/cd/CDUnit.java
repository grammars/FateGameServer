package common.component.cd;

import message.cd.CDMsg;
import utils.Utils;

import java.util.Collection;

import common.struct.cd.StCDInfo;
import common.struct.cd.StCDUint;
import game.core.sprite.Player;

public class CDUnit extends StCDUint
{
	/** CD类型{物品} */
	public static final byte T_GOODS = 0;
	/** CD类型{技能} */
	public static final byte T_SKILL = 1;
	
	/** 拥有者 */
	protected Player owner;
	
	public CDUnit(Player owner)
	{
		this.owner = owner;
	}
	
	/** 设置CD时间 */
	public void setCD(byte type, int id, int msCDTime)
	{
		StCDInfo cdi = new StCDInfo();
		cdi.setup(type, id, msCDTime);
		if(type == T_GOODS)
		{
			goods.put(id, cdi);
		}
		else if(type == T_SKILL)
		{
			skill.put(id, cdi);
		}
		
		CDMsg.getInstance().sendAddCD_G2C(cdi, owner.getBundle().getUid());
	}
	
	/** 获取CD剩余时间
	 * 如果已完成CD，则顺便充CD池中移除 */
	public int getCD(byte type, int id)
	{
		StCDInfo cdi = getCDInfo(type, id);
		if(cdi == null) { return 0; }
		long now = Utils.now();
		if(now > cdi.finishTime)
		{
			removeCD(type, id);
		}
		int leftCDTime = (int) (cdi.finishTime-now);
		return leftCDTime;
	}
	
	/** 获取CD信息 */
	public StCDInfo getCDInfo(int type, int id)
	{
		StCDInfo cdi = null;
		if(type == T_GOODS)
		{
			cdi = goods.get(id);
		}
		else if(type == T_SKILL)
		{
			cdi = skill.get(id);
		}
		return cdi;
	}
	
	/** 是否可用，即cd时间已经完成 */
	public boolean available(byte type, int id)
	{
		int cdt = getCD(type, id);
		return cdt <= 0;
	}
	
	/** 移除指定CD */
	private void removeCD(byte type, int id)
	{
		if(type == T_GOODS)
		{
			goods.remove(id);
		}
		else if(type == T_SKILL)
		{
			skill.remove(id);
		}
		
		CDMsg.getInstance().sendRemoveCD_G2C(type, id, owner.getBundle().getUid());
	}
	
	/** 被Player的update调用 */
	public void update()
	{
		long now = Utils.now();
		int len = 0;
		
		len = goods.size();
		StCDInfo[] goodsInfos = new StCDInfo[len];
		goods.values().toArray(goodsInfos);
		for(int i = len-1; i >= 0; i--)
		{
			StCDInfo cdi = goodsInfos[i];
			if(now > cdi.finishTime)
			{
				removeCD(T_GOODS, cdi.id);
			}
		}
		
		len = skill.size();
		StCDInfo[] skillInfos = new StCDInfo[len];
		skill.values().toArray(skillInfos);
		for(int i = len-1; i >= 0; i--)
		{
			StCDInfo cdi = skillInfos[i];
			if(now > cdi.finishTime)
			{
				removeCD(T_SKILL, cdi.id);
			}
		}
		
	}
	
	/** build */
	protected void build()
	{
		//
	}
	
	/** 导入结构数据 */
	public void importData(StCDUint data)
	{
		this.goods = data.goods;
		this.skill = data.skill;
		build();
	}
	
	/** 导出结构数据 */
	public StCDUint exportData()
	{
		StCDUint data = new StCDUint();
		data.goods = this.goods;
		data.skill = this.skill;
		return data;
	}
	
}
