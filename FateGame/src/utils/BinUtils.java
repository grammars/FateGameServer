package utils;

public class BinUtils
{
	/** 将byte转换为一个长度为8的byte数组，数组每个值代表bit */
	public static byte[] byte2bitArr(byte b)
	{
		byte[] array = new byte[8];
		for (int i = 7; i >= 0; i--)
		{
			array[i] = (byte) (b & 0x1);
			b = (byte) (b >> 1);
		}
		return array;
	}

	/** 把byte转为字符串的bit */
	public static String byte2bitStr(byte b)
	{
		return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
				+ (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
				+ (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
				+ (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
	}
	
	/** 把bit数组变成byte */
	public static byte bitArr2byte(byte[] bits)
	{
		byte result = 0;
		int offset = 0;
		for(int i = bits.length-1; i >= 0; i--)
		{
			if(bits[i] != 0) { result += ( 1 << offset ) ; }
			offset++;
		}
		return result;
	}
	
	/** 把bit字符串变成byte */
	public static byte bitStr2byte(String str)
	{
		byte result = 0;
		int offset = 0;
		for(int i = str.length()-1; i >= 0; i--)
		{
			char ch = str.charAt(i);
			int iv = Character.getNumericValue(ch);
			if(iv != 0) { result += ( 1 << offset ) ; }
			offset++;
		}
		return result;
	}

}
