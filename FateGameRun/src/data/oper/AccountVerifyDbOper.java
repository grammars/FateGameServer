package data.oper;

import message.login.LoginMsg;
import common.struct.login.StAccountInfo;
import data.dao.AccountDAO;
import framework.Log;

public class AccountVerifyDbOper
{
	/** 验证玩家账号,并发送角色列表 */
	public static void verify(String account, long clientBundleId)
	{
		StAccountInfo avo = (StAccountInfo) AccountDAO.getInstance().queryBean(account);
		if(avo.exist())
		{
			Log.system.debug("[老用户] " + avo);
		}
		else
		{
			avo.setUid(account);
			AccountDAO.getInstance().insertBean(avo);
			Log.system.debug("[新用户] db_uid=" + account);
		}
		LoginMsg.getInstance().sendAccountInfo_D2L(avo, clientBundleId);
		//
		PlayerListDbOper.query(avo, clientBundleId);
	}
	
}
