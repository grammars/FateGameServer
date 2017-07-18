package utils;

public class Str
{
	/** 判断两个值是否相同 */
	public static boolean same(String a, String b)
	{
		if(a==null && b==null) return true;
		if(a != null && b != null)
		{
			if(a.equals(b)) { return true; }
		}
		return false;
	}
}
