package common;

public class Res
{
	/** 判断资源字符串是否属于空的情况 */
	public static boolean isNull(String str)
	{
		if(str == null) { return true; }
		if(str == "") { return true; }
		if(str == "null" || str == "NULL" || str == "Null") { return true; }
		return false;
	}
}
