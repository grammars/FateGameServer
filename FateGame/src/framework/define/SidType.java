package framework.define;

/** 会话身份类型 */
public class SidType
{
	/** 客户端 */
	public static final byte CLIENT = 0;
	
	/** 登陆服 */
	public static final byte LOGIN = 1;
	
	/** 网关服 */
	public static final byte GATE = 2;
	
	/** 游戏服 */
	public static final byte GAME = 4;
	
	/** 数据服 */
	public static final byte DATA = 8;
	
	/** 不知道具体的服务器 */
	public static final byte SERVER = 16;
}
