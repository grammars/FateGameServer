package game.buff;

import game.core.sprite.Creature;
import cfg.BuffCfg;
import common.component.buff.Buff;
import common.component.skill.SkillItem;

public class BuffManager
{	
	/** 应用buff */
	public static boolean applyBuff(SkillItem si, Creature user, Creature tar)
	{
		BuffCfg cfg = BuffCfg.get(si.config.buffId);
		if(cfg == null) { return false; }
		Buff buff = new Buff();
		buff.setup(cfg, user, tar, tar.buffs);
		tar.addBuff(buff);
		return true;
	}
}
