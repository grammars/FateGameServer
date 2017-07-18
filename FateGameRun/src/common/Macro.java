package common;

/** 全局宏 */
public class Macro
{
	
	/** 游戏逻辑帧数 { FRAME_TIME * FRAME_TIME = 1000ms } */
	public static final int FRAME_RATE = 30;
	/** 游戏逻辑帧耗时 { FRAME_TIME * FRAME_TIME = 1000ms } */
	public static final int FRAME_TIME = 33;
	
	/** 地图格子宽度 */
	public static final int GridW = 40;
	/** 地图格子宽度 */
	public static final int GridH = 30;
	
	/** 判断字符串是否属于空的情况 */
	public static boolean isNull(String str)
	{
		if(str == null) { return true; }
		if(str == "") { return true; }
		if(str == "null" || str == "NULL" || str == "Null") { return true; }
		return false;
	}
}
