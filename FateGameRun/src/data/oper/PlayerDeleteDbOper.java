package data.oper;

import common.struct.login.StAccountInfo;
import common.beans.PlayerBean;
import data.dao.AccountDAO;
import data.dao.PlayerDAO;
import framework.Log;

public class PlayerDeleteDbOper
{
	public static void delete(long playerUid, long clientBundleId)
	{
		PlayerBean pvo = (PlayerBean)PlayerDAO.getInstance().queryBean(playerUid);
		if(pvo != null)
		{
			StAccountInfo avo = (StAccountInfo)AccountDAO.getInstance().queryBean(pvo.getAccount());
			if(avo != null)
			{
				if(avo.getPlayeruid0() == playerUid)
				{
					avo.setPlayeruid0(0);
				}
				else if(avo.getPlayeruid1() == playerUid)
				{
					avo.setPlayeruid1(0);
				}
				else if(avo.getPlayeruid2() == playerUid)
				{
					avo.setPlayeruid2(0);
				}
			}
			AccountDAO.getInstance().updateBean(avo);
			boolean result = PlayerDAO.getInstance().deleteBean(playerUid);
			Log.db.info("删除角色" + playerUid + "结果:" + result);
			
			PlayerListDbOper.query(avo, clientBundleId);
		}
	}
}
