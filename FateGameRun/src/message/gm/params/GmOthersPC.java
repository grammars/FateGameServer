package message.gm.params;

import framework.Log;
import game.GameClientBundle;

public class GmOthersPC
{
	/** 测试收到值 */
	private static final int DEBUG = 0;
	
	public static void cmdHandler(int subCmdId, GameClientBundle bundle, byte byte0, byte byte1, 
			int int0, int int1, int int2, float float0, double double0, 
			long long0, long long1, String str0, String str1)
	{
		switch(subCmdId)
		{
		case DEBUG:
			H_DEBUG(bundle, byte0, byte1, int0, int1, int2, float0, double0, long0, long1, str0, str1);
			break;
		}
	}
		
	private static void H_DEBUG(GameClientBundle bundle, byte byte0, byte byte1, 
			int int0, int int1, int int2, float float0, double double0, 
			long long0, long long1, String str0, String str1)
	{
		Log.system.debug("处理调试 来自玩家：" + bundle.player.name + 
			" byte0=" + byte0 + " byte1=" + byte1 + " int0=" + int0 + " int1=" + int1 + 
			" int2=" + int2 + " float0=" + float0 + " double0=" + double0 + 
			" long0=" + long0 + " long1=" + long1 +
			" str0=" + str0 + " str1=" + str1);
		bundle.player.log = str1;
	}
		
}
