package game.skill;

import cfg.SkillTreeCfg;
import message.fight.FightMsg;
import common.component.skill.SkillSet;
import common.define.SpriteType;
import framework.Log;
import game.core.sprite.Creature;

public class SkillManager
{
	
	/** 创建职业技能 */
	public static SkillSet createSkillForVoc(byte voc)
	{
		SkillSet skill = new SkillSet();
		SkillTreeCfg skillTreeCfg = SkillTreeCfg.get(voc);
		if(skillTreeCfg != null)
		{
			System.out.println("依据技能树:" + skillTreeCfg);
			skill.setup(skillTreeCfg.skillIds, skillTreeCfg.skillLevels);
		}
		return skill;
	}
	
	/** 向client通知技能不可用 */
	public static void sendUnavailableNotice(Creature user)
	{
		Log.game.info("该技能不可用");
		if(user.type == SpriteType.PLAYER)
		{
			FightMsg.getInstance().sendPlayerSkillUnavailable(user.toPlayer().getBundle().getUid());
		}
	}
}
