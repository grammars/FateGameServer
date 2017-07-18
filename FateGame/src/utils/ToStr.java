package utils;

public class ToStr
{
	public static String t(int[] arr)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < arr.length; i++)
		{
			sb.append(arr[i]);
			if(i < arr.length-1) { sb.append(","); }
		}
		return sb.toString();
	}
	
	public static String t(float[] arr)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < arr.length; i++)
		{
			sb.append(arr[i]);
			if(i < arr.length-1) { sb.append(","); }
		}
		return sb.toString();
	}
	
	public static String t(double[] arr)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < arr.length; i++)
		{
			sb.append(arr[i]);
			if(i < arr.length-1) { sb.append(","); }
		}
		return sb.toString();
	}
	
	public static String t(boolean[] arr)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < arr.length; i++)
		{
			sb.append(arr[i]);
			if(i < arr.length-1) { sb.append(","); }
		}
		return sb.toString();
	}
	
	public static String t(char[] arr)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < arr.length; i++)
		{
			sb.append(arr[i]);
			if(i < arr.length-1) { sb.append(","); }
		}
		return sb.toString();
	}
	
	public static String t(byte[] arr)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < arr.length; i++)
		{
			sb.append(arr[i]);
			if(i < arr.length-1) { sb.append(","); }
		}
		return sb.toString();
	}
	
	public static String t(Object[] arr)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < arr.length; i++)
		{
			sb.append(arr[i]);
			if(i < arr.length-1) { sb.append(","); }
		}
		return sb.toString();
	}
	
}
