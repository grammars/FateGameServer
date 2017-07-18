package game.player.charac;

import java.util.Iterator;

import message.player.PlayerDataMsg;
import message.scene.SceneMsg;
import cfg.SelfLookCfg;
import game.core.sprite.Player;
import game.player.PlayerManager;

public class CharacterHandler
{
	/** 改变Player的职业
	 * @param player 被操作的玩家对象
	 * @param voc 职业类型
	 * @param changeLook 是否需要改变外观(因为当玩家拥有特殊外观的时候就不应用常规外观了) */
	public void changeVoc(Player player, byte voc, boolean changeLook)
	{
		player.voc = voc;
		player.updateSkillForVoc();
		PlayerDataMsg.getInstance().sendPlayerVocChange_G2C(voc, player.getBundle().getUid());
		if(changeLook)
		{
			updateNormalSelfLook(player);
		}
		PlayerManager.tellSkillSetInit(player);
	}
	
	/** 根据Player的职业性别修炼等级来更新Player的常规外观 */
	public void updateNormalSelfLook(Player player)
	{
		String oldLookId = player.look;
		String lookId = SelfLookCfg.getLook(player.voc, player.sex, player.practice.lev);
		if(!lookId.equals(oldLookId))
		{
			changeSelfLook(player, lookId);
		}
	}
	
	/** 改变玩家自身外观，并通知周围玩家 */
	public void changeSelfLook(Player player, String lookId)
	{
		player.look = lookId;
		Iterator<Player> iter = player.getNear(true).iterator();
		while(iter.hasNext())
		{
			Player near = iter.next();
			SceneMsg.getInstance().sendSelfLookChange_G2C(player.tid, lookId, near.getBundle().getUid());
		}
	}
	
	/** 改变player的等级 */
	public void changeLevel(Player player, int level)
	{
		player.level = level;
		Iterator<Player> iter = player.getNear(true).iterator();
		while(iter.hasNext())
		{
			Player near = iter.next();
			PlayerDataMsg.getInstance().sendPlayerLevelChange_G2C(player.tid, level, near.getBundle().getUid());
		}
	}
}
