package game.fight.formula;

import common.component.player.AttriSet;
import game.core.sprite.Creature;

public class HitFormula
{
	//物理伤害公式： 攻击 / ( (Math.max(0,护甲-护甲穿透)/100) + 1 )
	public static int getHurt(Creature attacker, Creature target)
	{
		AttriSet A = attacker.attris;
		AttriSet T = target.attris;
		double defense = Math.max(0, (T.armor()-A.armorPene()));
		int hurt = (int)( A.attack() / (defense/100 + 1) );
		return hurt;
	}
}
