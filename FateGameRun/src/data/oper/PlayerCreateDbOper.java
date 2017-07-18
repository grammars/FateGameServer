package data.oper;

import message.login.LoginEC;
import message.login.LoginMsg;
import common.struct.login.StAccountInfo;
import common.beans.PlayerBean;
import data.dao.AccountDAO;
import data.dao.PlayerDAO;
import framework.Log;

public class PlayerCreateDbOper
{
	public static void create(String pAccount, String pPlayerName, byte pSex, long clientBundleId)
	{
		int errorCode = LoginEC.PLAYER_CREATE_SUCC;
		StAccountInfo avo = null;
		
		if(CheckNameDbOper.exist(pPlayerName))
		{
			Log.db.error("创建角色失败:该角色名已存在");
			errorCode = LoginEC.PLAYER_NAME_EXIST;
		}
		else
		{
			avo = (StAccountInfo) AccountDAO.getInstance().queryBean(pAccount);
			if(avo.exist())
			{
				PlayerBean pvo = new PlayerBean();
				pvo.create(pAccount, pPlayerName, pSex);
				boolean playerInsertOK = false;
				boolean accountUpdateOK = false;
				if(avo.canAddPlayer())
				{
					playerInsertOK = PlayerDAO.getInstance().insertBean(pvo);
					if(playerInsertOK)
					{
						avo.addPlayer(pvo.getUid());
						accountUpdateOK = AccountDAO.getInstance().updateBean(avo);
						if(accountUpdateOK)
						{
							Log.system.info("角色[" + pPlayerName + "]创建成功");
							errorCode = LoginEC.PLAYER_CREATE_SUCC;
						}
						else
						{
							errorCode = LoginEC.PLAYER_UPDATE_T_ACCOUNT_FAIL;
						}
						CheckNameDbOper.add(pPlayerName);
					}
					else
					{
						Log.system.error("创建角色失败:插入数据库player表失败");
						errorCode = LoginEC.PLAYER_INS_T_PLAYER_FAIL;
					}
				}
				else
				{
					Log.system.error("创建角色失败:角色数量已达到上限");
					errorCode = LoginEC.PLAYER_NUM_TOP;
				}
			}
			else
			{
				Log.system.error("创建角色失败:对应的账户不存在");
				errorCode = LoginEC.PLAYER_NO_ACCOUNT;
			}
		}
		//
		LoginMsg.getInstance().sendPlayerCreateRpl_D2L(errorCode, clientBundleId);
		//
		if(errorCode == LoginEC.PLAYER_CREATE_SUCC)
		{
			PlayerListDbOper.query(avo, clientBundleId);
		}
	}
}
