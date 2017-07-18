package game.fight.action;

import java.util.List;

import message.fight.FightMsg;
import game.core.action.Action;
import game.core.sprite.Creature;
import game.core.sprite.Monster;
import game.core.sprite.Player;
import game.fight.FightManager;
import game.fight.formula.HitFormula;
import game.player.PlayerManager;

public class NormalHitAction extends Action
{
	private Creature attacker;
	private Creature target;
	
	public void init(Creature attacker, Creature target)
	{
		this.attacker = attacker;
		this.target = target;
	}
	
	@Override
	public void run()
	{
		int hpDelt = - HitFormula.getHurt(attacker, target);
		target.attris.changeCurHp(hpDelt);
		
		int costTime = (int)( (float)1000/attacker.attris.attackSpeed() );
		attacker.hit.hitCD(costTime);
		
//		if(target instanceof Monster)
//		{
//			if( null == target.toMonster().getFightTar() )
//			{
//				target.toMonster().setFightTar(attacker);
//			}
//		}
		target.ai.normalHitFrom(attacker);
		
		List<Player> players = attacker.getNear(true);
		for(Player player : players)
		{
			FightMsg.getInstance().sendCretHitExecute_G2C(attacker.tid, target.tid, player.getBundle().getUid());
		}
		
		FightManager.handleHurt(target, attacker);
	}
	
}
