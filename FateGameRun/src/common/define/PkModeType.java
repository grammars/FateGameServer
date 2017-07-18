package common.define;

public class PkModeType
{
	/** 和平模式
	 * 不会被其他玩家攻击，也不能攻击其他任何玩家 */
	public static final int PEACE = 0;
	/** 自由模式
	 * 可以攻击那些也处于自由模式下的（非好友）玩家 */
	public static final int FREE = 1;
	/** 家族模式
	 * 类似自由模式，但同一家族内的即使非好友也不会被攻击 */
	public static final int FAMILY = 2;
}
