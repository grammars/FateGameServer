package game.core.sprite.parts;

import game.core.sprite.Creature;
import utils.Utils;

/** 普通攻击控制单元 */
public class NormalHitUnit
{
	private Creature owner;
	
	public NormalHitUnit(Creature owner)
	{
		this.owner = owner;
	}
	
	/** 普攻冷却时间 */
	protected long hitFreeTime = 0;
	/** 过多少时间之后可以再攻击 */
	public void hitCD(int afterTime)
	{
		hitFreeTime = Utils.now() + afterTime;
	}
	/** 增加默认普攻CD */
	public void hitCD()
	{
		int afterTime = (int)( (float)1000/owner.attris.attackSpeed() );
		hitCD(afterTime);
	}
	/** 允许连续提前CD的最大累计值 */
	protected static final int AllowHitFastMax = 100;
	/** 允许连续提前CD的累计值 */
	protected int allowHitFastCache = 0;
	/** 是否可以进行普通，即完成普攻冷却 */
	public boolean isHitFree()
	{
		long now = Utils.now();
		if(now > hitFreeTime)//完全合法
		{
			allowHitFastCache = 0;
			return true;
		}
		else//本次不合法
		{
			allowHitFastCache += (hitFreeTime-now);
			if(allowHitFastCache > AllowHitFastMax)
			{
				allowHitFastCache = 0;
				return false;
			}
			else
			{
				return true;
			}
		}
	}
}
