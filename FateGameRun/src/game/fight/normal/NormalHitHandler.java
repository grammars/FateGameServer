package game.fight.normal;

import java.util.List;

import message.fight.FightMsg;
import framework.Log;
import game.core.sprite.Creature;
import game.core.sprite.Player;
import game.fight.action.NormalHitAction;
import game.fight.law.FightLaw;

public class NormalHitHandler
{
	public NormalHitHandler()
	{
		
	}
	
	/** 普通攻击预备动作 */
	public void prepare(Creature attacker, Creature target)
	{
		if(attacker == null || target == null) { return; }
		
		if( ! FightLaw.hitRelationAvailable(attacker, target) ) { return; }
		
		List<Player> players = attacker.getNear(false);
		for(Player player : players)
		{
			FightMsg.getInstance().sendCretHitPrepare_G2C(attacker.tid, attacker.direction, player.getBundle().getUid());
		}
	}
	
	/** 正式执行普通攻击 */
	public void execute(Creature attacker, Creature target)
	{
		if(attacker == null || target == null) { return; }
		
		if( ! FightLaw.hitRelationAvailable(attacker, target) ) { return; }
		
		if(!attacker.hit.isHitFree())
		{
			Log.game.error("FightHandler::hit 普攻冷却未完成");
			return;
		}
		
		Log.game.debug("进行了一次普通攻击,atkSpd=>" + attacker.attris.attackSpeed());
		
		NormalHitAction action = new NormalHitAction();
		action.init(attacker, target);
		action.setup(0, 0, 1);
		target.pushAction(action);
	}
	
}
