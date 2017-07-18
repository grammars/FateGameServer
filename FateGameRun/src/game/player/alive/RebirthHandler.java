package game.player.alive;

import java.util.List;

import message.fight.FightMsg;
import framework.Log;
import game.core.sprite.Player;

public class RebirthHandler
{
	/** 复活方式：回城复活 */
	private static final byte REBIRTH_WAY_HOME = 0;
	/** 复活方式：原地复活 */
	private static final byte REBIRTH_WAY_HERE = 1;
	
	/** 复活结果枚举：成功 */
	private static final byte EC_SUCC = 0;
	/** 复活结果枚举：失败,未知原因 */
	@SuppressWarnings("unused")
	private static final byte EC_FAIL_UNDEFINED = 1;
	
	/** 使Player复活 */
	public void rebirth(byte rebirthWay, Player player)
	{
		if(false == player.alive)
		{
			player.alive = true;
			player.attris.recover(true);
			byte errCode = EC_SUCC;
			switch(rebirthWay)
			{
			case REBIRTH_WAY_HOME:
				break;
			case REBIRTH_WAY_HERE:
				break;
			}
			List<Player> players = player.getNear(true);
			for(Player p : players)
			{
				FightMsg.getInstance().sendPlayerRebirthRpl_G2C(player.tid, errCode, p.getBundle().getUid());
			}
		}
		else
		{
			Log.game.error("角色[" + player.name + "]无需复活");
		}
	}
}
