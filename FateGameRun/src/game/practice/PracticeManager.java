package game.practice;

import message.practice.PracticeMsg;
import common.component.practice.PracticeOperEnum;
import game.core.sprite.Player;
import game.player.PlayerManager;

/** 修炼管理 */
public class PracticeManager
{
	/** 尝试飞升,提升修炼等级 */
	public static void tryLevelUp(Player player)
	{
		PracticeOperEnum result = player.practice.levelUp();
		if(result == PracticeOperEnum.SUCC)
		{
			PracticeMsg.getInstance().sendInitData_G2C(player.practice.exportData(), player.getBundle().getUid());
			PlayerManager.character.updateNormalSelfLook(player);
		}
		PracticeMsg.getInstance().sendLevelUpRpl_G2C(result.errCode, player.getBundle().getUid());
	}
}
