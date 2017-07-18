package message.login;

public class LoginEC
{
	//--- account(1-19)
	/** 账号验证成功 */
	public static final int ACCOUNT_VERIFY_SUCC = 1;
	/** 账号验证失败:sign错误 */
	public static final int ACCOUNT_SIGN_ERR = 2;
	
	//--- player(20-39)
	/** 创建角色成功 */
	public static final int PLAYER_CREATE_SUCC = 20;
	/** 创建角色失败:该角色名已存在 */
	public static final int PLAYER_NAME_EXIST = 21;
	/** 创建角色失败:角色数量已达到上限 */
	public static final int PLAYER_NUM_TOP = 22;
	/** 创建角色失败:对应的账户不存在 */
	public static final int PLAYER_NO_ACCOUNT = 23;
	/** 创建角色失败:插入数据库player表失败 */
	public static final int PLAYER_INS_T_PLAYER_FAIL = 24;
	/** 创建角色失败:更新数据库account表失败 */
	public static final int PLAYER_UPDATE_T_ACCOUNT_FAIL = 25;
	
}
