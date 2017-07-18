package message.player;

import login.LoginClientBundle;
import message.IMessage;
import message.MessageId;

import org.apache.mina.core.buffer.IoBuffer;

import common.beans.PlayerBean;
import data.oper.PlayerDataDbOper;
import framework.ClientBundleManager;
import framework.Log;
import framework.MsgBuffer;
import framework.SessHand;
import framework.define.SidType;
import framework.net.IBT;
import game.GameClientBundle;
import game.core.sprite.Player;

public class PlayerDataMsg implements IMessage
{
	private static PlayerDataMsg instance;
	public static PlayerDataMsg getInstance()
	{
		if(instance == null) { instance = new PlayerDataMsg(); }
		return instance;
	}
	
	/** [角色数据]主消息号 */
	public static final int MID = MessageId.PLAYER_DATA_MID;
	
	/** game向data请求完整角色数据 */
	private static final int PLAYER_DATA_REQ_G2D = 0;
	/** data向game返回完整角色数据 */
	private static final int PLAYER_DATA_RPL_D2G = 1;
	/** game向login通知 game已准备好角色数据 */
	private static final int PLAYER_DATA_RPL_G2L = 2;
	/** login向client通知 game已准备好角色数据,已表示登录成功,让client重连到GateServer */
	private static final int PLAYER_DATA_RPL_L2C = 3;
	/** game向data请求保存完整角色数据 */
	private static final int PLAYER_DATA_SAVE_REQ_G2D = 4;
	
	/** game向client发送玩家初始化数据 */
	private static final int PLAYER_INIT_DATA_G2C = 10;
	/** game向client发送玩家职业改变 */
	private static final int PLAYER_VOC_CHANGE_G2C = 11;
	/** game相client发送玩家等级改变 */
	private static final int PLAYER_LEVEL_CHANGE_G2C = 12;


	@Override
	public void recv(SessHand serverSH, int subMid, IoBuffer buffer, long clientBundleId)
	{
		switch(subMid)
		{
		case PLAYER_DATA_REQ_G2D:
			recvPlayerDataReq_G2D(buffer, clientBundleId);
			break;
		case PLAYER_DATA_RPL_D2G:
			recvPlayerDataRpl_D2G(buffer, clientBundleId);
			break;
		case PLAYER_DATA_RPL_G2L:
			recvPlayerDataRpl_G2L(buffer, clientBundleId);
			break;
		case PLAYER_DATA_SAVE_REQ_G2D:
			recvPlayerDataSaveReq_G2D(buffer, clientBundleId);
			break;
		}
	}

	/** send( game向data请求完整角色数据 ) */
	public void sendPlayerDataReq_G2D(long playerUid, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_DATA_REQ_G2D, SidType.DATA, clientBundleId);
		IBT.writeLong(msg, playerUid);
		msg.send();
	}
	/** recv( game向data请求完整角色数据 ) */
	public void recvPlayerDataReq_G2D(IoBuffer buffer, long clientBundleId)
	{
		long playerUid = IBT.readLong(buffer);
		Log.db.debug("game向data请求完整角色数据playerUid=" + playerUid);
		PlayerBean bean = PlayerDataDbOper.query(playerUid);
		if(bean != null)
		{
			sendPlayerDataRpl_D2G(bean, clientBundleId);
		}
		else
		{
			Log.db.error("playerUid[" + playerUid + "]不存在");
		}
	}
	
	/** send( data向game返回完整角色数据 ) */
	public void sendPlayerDataRpl_D2G(PlayerBean bean, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_DATA_RPL_D2G, SidType.GAME, clientBundleId);
		bean.writeFull(msg.buffer);
		msg.send();
	}
	/** recv( data向game返回完整角色数据 ) */
	private void recvPlayerDataRpl_D2G(IoBuffer buffer, long clientBundleId)
	{
		GameClientBundle bundle = new GameClientBundle(clientBundleId);
		PlayerBean bean = new PlayerBean();
		bean.readFull(buffer);
		bean.apply(bundle.player);
		bundle.initialize();
		System.out.println("GameSrv收到：" + bundle.player.toString() + " clientBundleId=" + clientBundleId);
		String notice = "GameSrv已准备好角色数据";
		sendPlayerDataRpl_G2L(notice, clientBundleId);
	}
	
	/** send( game向login通知 game已准备好角色数据 ) */
	public void sendPlayerDataRpl_G2L(String notice, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_DATA_RPL_G2L, SidType.LOGIN, clientBundleId);
		IBT.writeString(msg, notice);
		msg.send();
	}
	/** recv( game向login通知 game已准备好角色数据  ) */
	private void recvPlayerDataRpl_G2L(IoBuffer buffer, long clientBundleId)
	{
		LoginClientBundle bundle = (LoginClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle == null)
		{
			Log.system.error("recvPlayerDataRpl_G2L::LoginClientBundle丢失");
			return;
		}
		String notice = IBT.readString(buffer);
		sendPlayerDataRpl_L2C(bundle.getClientSess(), notice);
	}
	
	/** send( login向client通知 game已准备好角色数据 ,已表示登录成功,让client重连到GateServer ) */
	public void sendPlayerDataRpl_L2C(SessHand clientSH, String notice)
	{
		System.out.println("login向client通知 game已准备好角色数据 ,已表示登录成功：");
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_DATA_RPL_L2C);
		IBT.writeString(msg, notice);
		clientSH.send(msg);
	}
	
	/** send( game向data请求保存完整角色数据 ) */
	public void sendPlayerDataSaveReq_G2D(PlayerBean pvo, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_DATA_SAVE_REQ_G2D, SidType.DATA, clientBundleId);
		pvo.writeFull(msg.buffer);
		msg.send();
	}
	
	/** recv( game向data请求保存完整角色数据 ) */
	private void recvPlayerDataSaveReq_G2D(IoBuffer buffer, long clientBundleId)
	{
		PlayerBean pvo = new PlayerBean();
		pvo.readFull(buffer);
		boolean result = PlayerDataDbOper.save(pvo);
		if(result==true)
		{
			Log.db.fatal("成功保存了玩家[" + pvo.getName() + "]");
		}
	}
	
	/** send( game向client发送玩家初始化数据 ) */
	public void sendPlayerInitData_G2C(Player player, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_INIT_DATA_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, player.tid);
		player.write(msg.buffer);
		msg.send();
	}
	
	/** send( game向client发送玩家职业改变 ) */
	public void sendPlayerVocChange_G2C(byte voc, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_VOC_CHANGE_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeByte(msg, voc);
		msg.send();
	}
	
	/** send( game向client发送玩家等级改变 ) */
	public void sendPlayerLevelChange_G2C(int playerTid, int level, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_LEVEL_CHANGE_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, playerTid);
		IBT.writeInt(msg, level);
		msg.send();
	}
	
}
