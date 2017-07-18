package game.fight.aoe;

import common.component.skill.SkillItem;
import game.core.sprite.AoeSkill;
import game.core.sprite.Creature;
import game.fight.formula.SkillCostForm;
import game.skill.SkillManager;

public class AoeSkillHandler
{
	public void fire(Creature user, int skillId, int fireGX, int fireGY)
	{
		if( user == null ) { return; }
		SkillItem si = user.skills.getItem(skillId);
		if( si == null ) { return; }
		
		if(!SkillCostForm.cost(true, user, si))
		{
			SkillManager.sendUnavailableNotice(user);
			return;
		}
		
		AoeSkill sp = new AoeSkill();
		sp.x = (fireGX);
		sp.y = (fireGY);
		sp.setup(user, si);
		user.getScene().addSprite(sp);
	}
}
