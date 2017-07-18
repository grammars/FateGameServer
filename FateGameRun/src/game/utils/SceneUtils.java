package game.utils;

public class SceneUtils
{
	/** 通过 目标点-当前点 的 delt X/Y 来计算应该面向的方向 */
	public static byte countFaceDir(int deltX, int deltY)
	{
		if(deltX != 0 && deltY != 0)
		{
			double rad = Math.atan2(-deltY, deltX);
			double degree = rad / Math.PI * 180;
			//trace("计算的角度是,", degree, " 弧度是", rad / Math.PI, "PI");
			if(degree >= 67.5 && degree < 112.5) { return 0; }
			else if(degree >= 22.5 && degree < 67.5) { return 1; }
			else if(degree >= -22.5 && degree < 22.5) { return 2; }
			else if(degree >= -67.5 && degree < -22.5) { return 3; }
			else if(degree >= -112.5 && degree < -67.5) { return 4; }
			else if(degree >= -157.5 && degree < -112.5) { return 5; }
			else if(degree >= -180 && degree < -157.5) { return 6; }
			else if(degree >= 157.5 && degree <= 180) { return 6; }
			else if(degree >= 112.5 && degree < 157.5) { return 7; }
			else { return -1; }
		}
		else if(deltX != 0 && deltY == 0)
		{
			//trace("deltX != 0 && deltY == 0");
			if(deltX > 0) { return 2; }
			else { return 6; }
		}
		else if(deltX == 0 && deltY != 0)
		{
			//trace("deltX == 0 && deltY != 0");
			if(deltY > 0) { return 4; }
			else { return 0; }
		}
		else
		{
			return -1;
		}
	}
	
	
	/** 获得朝向对象的朝向 */
	public static byte getTowardsFace(int fromX, int fromY, int tarX, int tarY)
	{
		byte face = -1;
		int deltX = 0;
		int deltY = 0;
		
		if(tarX-fromX > 0) { deltX = 1; }
		else if(tarX-fromX < 0) { deltX = -1; }
		
		if(tarY-fromY > 0) { deltY = 1; }
		else if(tarY-fromY < 0) { deltY = -1; }
		
		face = countFaceDir(deltX, deltY);
		
		return face;
	}
	
	/** 动作{HIT}标准播放时长[毫秒] */
	public static final int ACTION_TIME_HIT = 600;// =1000/BASE_HIT_SPEED(1.667)
	/** 动作{BANG}标准播放时长[毫秒] */
	public static final int ACTION_TIME_BANG = 800;
	/** 动作{JUMP_CUT}标准播放时长[毫秒] */
	public static final int ACTION_TIME_JUMP_CUT = 800;
	
}
