package game.fight.action;

import java.util.List;

import message.fight.FightMsg;
import common.component.skill.SkillItem;
import game.core.action.Action;
import game.core.sprite.Creature;
import game.core.sprite.Player;
import game.fight.formula.SkillEffForm;

public class SkillEffAction extends Action
{
	private SkillItem si;
	private Creature user;
	private Creature tar;
	
	public void init(SkillItem si, Creature user, Creature tar)
	{
		this.si = si;
		this.user = user;
		this.tar = tar;
	}
	
	@Override
	public void run()
	{
		//System.out.println("SkillEffAction::run");
		SkillEffForm.execute(si, user, tar);
		broadcastSkillEffect();
	}
	
	/** 通知客户端 技能效果完成了一次作用 */
	protected void broadcastSkillEffect()
	{
		List<Player> players = this.tar.getNear(true);
		for(Player player : players)
		{
			FightMsg.getInstance().sendSkillEffect_G2C(tar.tid, si.id, si.level, player.getBundle().getUid());
		}
	}
}
