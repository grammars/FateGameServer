package data.oper;

import common.beans.PlayerBean;
import data.dao.PlayerDAO;

public class PlayerDataDbOper
{
	public static PlayerBean query(long playerUid)
	{
		PlayerBean pvo = (PlayerBean)PlayerDAO.getInstance().queryBean(playerUid);
		return pvo;
	}
	
	public static boolean save(PlayerBean pvo)
	{
		return PlayerDAO.getInstance().updateBean(pvo);
	}
	
}
