package game.fight;

import message.fight.FightMsg;
import common.component.goods.GoodsFactory;
import common.component.goods.GoodsInfo;
import common.define.DeathCause;
import common.define.SpriteType;
import game.core.sprite.Creature;
import game.core.sprite.Monster;
import game.core.sprite.Player;
import game.fight.aim.AimSkillHandler;
import game.fight.aoe.AoeSkillHandler;
import game.fight.normal.NormalHitHandler;
import game.task.TaskManager;

public class FightManager
{
	public static NormalHitHandler hit = new NormalHitHandler();
	public static AimSkillHandler aim = new AimSkillHandler();
	public static AoeSkillHandler aoe = new AoeSkillHandler();
	
	/** 处理伤害
	 * @param target 受害目标
	 * @param source 施害者 */
	public static void handleHurt(Creature target, Creature source)
	{
		//System.out.println("FightManager::handleHurt");
		noticeRebirthIfPlayerDead(target, source);
		checkPlayerKillMonsterTask(target, source);
	}
	
	/** 检查是否是Player，是否已经死亡，死亡的话，通知客户端打开复活面板 */
	private static void noticeRebirthIfPlayerDead(Creature target, Creature killer)
	{
		if(SpriteType.PLAYER == target.type
				&& false == target.alive)
		{
			Player player = target.toPlayer();
			FightMsg.getInstance().sendPlayerDeadInfo_G2C(DeathCause.DEFAULT, killer, player.getBundle().getUid());
		}
	}
	
	/** 检查是否是Player杀了Monster,进行任务更新 */
	private static void checkPlayerKillMonsterTask(Creature target, Creature source)
	{
		//System.out.println("FightManager::checkPlayerKillMonsterTask");
		if(target.type == SpriteType.MONSTER && source.type == SpriteType.PLAYER
				&& false == target.alive)
		{
			Monster monster = target.toMonster();
			Player player = source.toPlayer();
			
			if(monster.deathDropped==false && monster.getConfig().willDrop())
			{
				GoodsInfo gi = GoodsFactory.createInfo(monster.getConfig().dropId);
				gi.num = 1;
				player.bag.addItem(gi);
				monster.deathDropped = true;
			}
			
			TaskManager.handleKillMonster(player, monster);
		}
	}
	
}
