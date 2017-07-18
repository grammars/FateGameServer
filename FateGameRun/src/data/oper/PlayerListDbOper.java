package data.oper;

import message.login.LoginMsg;
import message.login.PlayerListPack;
import common.struct.login.StAccountInfo;
import common.beans.PlayerBean;
import data.dao.PlayerDAO;

public class PlayerListDbOper
{
	public static void query(StAccountInfo avo, long clientBundleId)
	{
		PlayerListPack pack = new PlayerListPack();
		if(avo.existPlayer0())
		{
			pack.makeP0( (PlayerBean) PlayerDAO.getInstance().queryBean(avo.getPlayeruid0()) );
		}
		if(avo.existPlayer1())
		{
			pack.makeP1( (PlayerBean) PlayerDAO.getInstance().queryBean(avo.getPlayeruid1()) );
		}
		if(avo.existPlayer2())
		{
			pack.makeP2( (PlayerBean) PlayerDAO.getInstance().queryBean(avo.getPlayeruid2()) );
		}
		LoginMsg.getInstance().sendPlayerList_D2L(pack, clientBundleId);
	}
}
