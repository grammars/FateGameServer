package framework;

import java.util.HashMap;
import java.util.Map;

import utils.MD5;

public class Security
{
	private static Map<String, String> accountSignMap = new HashMap<>();
	/** 添加账户和密钥 */
	public static void addAccountSign(String account, String sign)
	{
		accountSignMap.put(account, sign);
	}
	/** 移除账户和密钥 */
	public static void removeAccountSign(String account)
	{
		accountSignMap.remove(account);
	}
	/** 验证账户和密钥 */
	public static boolean verifyAccountSign(String account, String sign)
	{
		String rightSign = accountSignMap.get(account);
		if(rightSign != null && rightSign.equals(sign)) { return true; }
		return false;
	}
	
	/** 验证账户和密钥的合法性 */
	public static boolean checkAccount(String account, String timestamp, String sign)
	{
		String source = SetupCfg.APP_SKEY + "_" + account + "_" + timestamp;
		String calSign = MD5.createPassword(source).toUpperCase();
		if(calSign.equals(sign))
		{
			return true;
		}
		return false;
	}
}
