package utils;

public class GeomUtils
{
	/** 计算距离 */
	public static double distance(double fromX, double fromY, double toX, double toY)
	{
		double sq = Math.pow(toX-fromX, 2) + Math.pow(toY-fromY, 2);
		return Math.sqrt(sq);
	}
	
	/** 计算距离 */
	public static double distance(double deltX, double deltY)
	{
		double sq = Math.pow(deltX, 2) + Math.pow(deltY, 2);
		return Math.sqrt(sq);
	}
	
}
