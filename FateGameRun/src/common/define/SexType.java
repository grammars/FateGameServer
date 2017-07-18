package common.define;

public class SexType
{
	/** 无性别 */
	public static final byte NULL = 0;
	/** 男 */
	public static final byte BOY = 1;
	/** 女 */
	public static final byte GIRL = 2;
	
	/** Str */
	public static String Str(byte type)
	{
		switch(type)
		{
		case NULL: return "无性别";
		case BOY: return "男";
		case GIRL: return "女";
		default: return "未定义";
		}
	}
	
}
