package game.player;

import game.core.sprite.Player;
import game.player.alive.RebirthHandler;
import game.player.charac.CharacterHandler;
import message.cd.CDMsg;
import message.player.PlayerDataMsg;
import message.practice.PracticeMsg;
import message.skill.SkillMsg;
import message.task.TaskMsg;

public class PlayerManager
{
	/** 复活处理 */
	public static RebirthHandler rebirth = new RebirthHandler();
	/** 角色特征处理 */
	public static CharacterHandler character = new CharacterHandler();
	
	/** 向client通知上线需要初始化的消息 */
	public static void tellInit(Player player)
	{
		long clientBundleId = player.getBundle().getUid();
		PlayerDataMsg.getInstance().sendPlayerInitData_G2C(player, clientBundleId);
		player.bag.initItemsHandler();
		player.equip.initItemsHandler();
		player.warehouse.initItemsHandler();
		PracticeMsg.getInstance().sendInitData_G2C(player.practice.exportData(), clientBundleId);
		TaskMsg.getInstance().sendInitTaskList_G2C(player.task.exportData(), clientBundleId);
		CDMsg.getInstance().sendInitCD_G2C(player.cd.exportData(), clientBundleId);
	}
	
	/** 向client通知技能初始化
	 * [初始化的时候已经整合到整个player的完整数据中去]
	 * [此方法是为了技能集变更之后通知使用的，比如转职之后的职业发生变化] */
	public static void tellSkillSetInit(Player player)
	{
		SkillMsg.getInstance().sendInitSkillSet_G2C(player.skills.exportData(), player.getBundle().getUid());
	}
	
}
