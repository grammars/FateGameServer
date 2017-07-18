package message.gm.params;

import game.GameClientBundle;
import game.core.sprite.Player;

public class GmFightPC
{
	/** fight测试 */
	private static final int TEST = 0;
	
	public static void cmdHandler(int subCmdId, GameClientBundle bundle, byte byte0, byte byte1, 
			int int0, int int1, int int2, float float0, double double0, 
			long long0, long long1, String str0, String str1)
	{
		Player player = bundle.player;
		switch(subCmdId)
		{
		case TEST:
			H_TEST(bundle, player);
			break;
		}
	}
		
	private static void H_TEST(GameClientBundle bundle, Player player)
	{
		
	}
}
