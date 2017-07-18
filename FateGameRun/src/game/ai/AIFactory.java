package game.ai;

import game.ai.base.ActiveFightMonsterAI;
import game.ai.base.PassiveFightMonsterAI;
import game.ai.base.WalkerAI;
import game.core.sprite.Creature;

public class AIFactory
{
	/** 仅散步，挨打也不还手 */
	public static final int T_WALKER = 1;
	/** 被动攻击怪 */
	public static final int T_PASSIVE_FIGHT_MONSTER = 2;
	/** 主动攻击怪 */
	public static final int T_ACTIVE_FIGHT_MONSTER = 3;
	
	public static CreatureAI create(int type, Creature owner)
	{
		CreatureAI ai = null;
		switch(type)
		{
		case T_WALKER:
			ai = new WalkerAI(owner);
			break;
		case T_PASSIVE_FIGHT_MONSTER:
			ai = new PassiveFightMonsterAI(owner);
			break;
		case T_ACTIVE_FIGHT_MONSTER:
			ai = new ActiveFightMonsterAI(owner);
			break;
		default:
			ai = new CreatureAI(owner);
			break;
		}
		return ai;
	}
}
