package message.login;

import login.LoginClientBundle;
import message.IMessage;
import message.MessageId;
import message.player.PlayerDataMsg;

import org.apache.mina.core.buffer.IoBuffer;

import common.struct.login.StAccountInfo;
import data.oper.AccountVerifyDbOper;
import data.oper.PlayerCreateDbOper;
import data.oper.PlayerDeleteDbOper;
import framework.ClientBundleManager;
import framework.Log;
import framework.MsgBuffer;
import framework.Security;
import framework.SessHand;
import framework.define.SidType;
import framework.net.IBT;

public class LoginMsg implements IMessage
{
	private static LoginMsg instance;
	public static LoginMsg getInstance()
	{
		if(instance == null) { instance = new LoginMsg(); }
		return instance;
	}
	
	/** [登录]主消息号 */
	public static final int MID = MessageId.LOGIN_MID;
	
	/** client向login请求验证账号 */
	private static final int ACCOUNT_VERIFY_REQ_C2L = 0;
	/** login向data请求验证账号 */
	private static final int ACCOUNT_VERIFY_REQ_L2D = 1;
	/** login向client回复验证账号的结果 */
	private static final int ACCOUNT_VERIFY_RPL_L2C = 2;
	/** data向login发送的账号信息 */
	private static final int ACCOUNT_INFO_D2L = 3;
	/** login向client发送的账号信息 */
	private static final int ACCOUNT_INFO_L2C = 4;
	/** data向login发送的账号信息 */
	private static final int PLAYER_LIST_D2L = 5;
	/** login向client发送的账号信息 */
	private static final int PLAYER_LIST_L2C = 6;
	/** client向login请求新建角色 */
	private static final int PLAYER_CREATE_REQ_C2L = 7;
	/** login向data请求新建角色 */
	private static final int PLAYER_CREATE_REQ_L2D = 8;
	/** data向login发送的新建角色的结果 */
	private static final int PLAYER_CREATE_RPL_D2L = 9;
	/** login向client发送的新建角色的结果 */
	private static final int PLAYER_CREATE_RPL_L2C = 10;
	/** client向login请求选择角色进入 */
	private static final int PLAYER_SELECT_REQ_C2L = 11;
	/** login向game请求角色进入游戏 */
	private static final int PLAYER_ENTER_GAME_REQ_L2G = 12;
	/** client向login请求删除角色 */
	private static final int PLAYER_DELETE_REQ_C2L = 13;
	/** login向data请求删除角色 */
	private static final int PLAYER_DELETE_REQ_L2D = 14;
	
	@Override
	public void recv(SessHand sess, int subMid, IoBuffer buffer, long clientBundleId)
	{
		switch(subMid)
		{
		case ACCOUNT_VERIFY_REQ_C2L:
			recvAccountVerifyReq_C2L(sess, buffer);
			break;
		case ACCOUNT_VERIFY_REQ_L2D:
			recvAccountVerifyReq_L2D(sess, buffer, clientBundleId);
			break;
		case ACCOUNT_INFO_D2L:
			recvAccountInfo_D2L(sess, buffer, clientBundleId);
			break;
		case PLAYER_LIST_D2L:
			recvPlayerList_D2L(sess, buffer, clientBundleId);
			break;
		case PLAYER_CREATE_REQ_C2L:
			recvPlayerCreateReq_C2L(sess, buffer, clientBundleId);
			break;
		case PLAYER_CREATE_REQ_L2D:
			recvPlayerCreateReq_L2D(sess, buffer, clientBundleId);
			break;
		case PLAYER_CREATE_RPL_D2L:
			recvPlayerCreatePpl_D2L(sess, buffer, clientBundleId);
			break;
		case PLAYER_SELECT_REQ_C2L:
			recvPlayerSelectReq_C2L(sess, buffer, clientBundleId);
			break;
		case PLAYER_ENTER_GAME_REQ_L2G:
			recvPlayerEnterGameReq_L2G(sess, buffer, clientBundleId);
			break;
		case PLAYER_DELETE_REQ_C2L:
			recvPlayerDeleteReq_C2L(sess, buffer, clientBundleId);
			break;
		case PLAYER_DELETE_REQ_L2D:
			recvPlayerDeleteReq_L2D(sess, buffer, clientBundleId);
			break;
		}
	}
	
	/** recv( client向login请求验证账号 ) */
	private void recvAccountVerifyReq_C2L(SessHand clientSH, IoBuffer buffer)
	{
		LoginClientBundle bundle = (LoginClientBundle)ClientBundleManager.getInstance().getBundle(clientSH);
		if(bundle == null)
		{
			Log.login.error("recvAccountVerifyReq_C2L::bundle lost");
			return;
		}
		String account = IBT.readString(buffer);
		String timestamp = IBT.readString(buffer);
		String sign = IBT.readString(buffer);
		Log.system.fatal("recv( client向login请求验证账号 ) account=" + account + " timestamp=" + timestamp + " sign=" + sign);
		if(Security.checkAccount(account, timestamp, sign))
		{
			bundle.account.setUid(account);
			bundle.account.timestamp = timestamp;
			bundle.account.sign = sign;
				
			Security.addAccountSign(account, sign);
			sendAccountVerifyRpl_L2C(clientSH, LoginEC.ACCOUNT_VERIFY_SUCC, bundle.getUid());
			sendAccountVerifyReq_L2D(bundle);
		}
		else
		{
			//sign验证错误
			sendAccountVerifyRpl_L2C(clientSH, LoginEC.ACCOUNT_SIGN_ERR, 0);
		}
	}
	
	/** send(login向data请求验证账号) */
	public void sendAccountVerifyReq_L2D(LoginClientBundle bundle)
	{
		MsgBuffer msg = new MsgBuffer(MID, ACCOUNT_VERIFY_REQ_L2D, SidType.DATA, bundle.getUid());
		IBT.writeString(msg, bundle.account.getUid());
		msg.send();
	}
	/** recv( login向data请求验证账号 ) */
	private void recvAccountVerifyReq_L2D(SessHand serverSH, IoBuffer buffer, long clientBundleId)
	{
		String account = IBT.readString(buffer);
		Log.login.debug("recv( login向data请求验证账号 ) account=" + account);
		AccountVerifyDbOper.verify(account, clientBundleId);
	}

	/** send(login向client回复验证账号的结果) */
	public void sendAccountVerifyRpl_L2C(SessHand clientSH, int ec, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, ACCOUNT_VERIFY_RPL_L2C);
		IBT.writeInt(msg, ec);
		IBT.writeLong(msg, clientBundleId);
		clientSH.send(msg);
	}
	
	/** send(data向login发送的账号信息) */
	public void sendAccountInfo_D2L(StAccountInfo avo, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, ACCOUNT_INFO_D2L, SidType.LOGIN,  clientBundleId);
		avo.writeMsg(msg.buffer);
		msg.send();
	}
	/** recv(data向login发送的账号信息) */
	private void recvAccountInfo_D2L(SessHand serverSH, IoBuffer buffer, long clientBundleId)
	{
		LoginClientBundle bundle = (LoginClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle != null)
		{
			bundle.account.readMsg(buffer);
			sendAccountInfo_L2C(bundle.getClientSess(), bundle.account);
		}
		else
		{
			Log.login.error("recvAccountInfo_D2L::LoginClientBundle丢失");
		}
	}
	
	/** send( login向client发送的账号信息 ) */
	public void sendAccountInfo_L2C(SessHand clientSH, StAccountInfo avo)
	{
		MsgBuffer msg = new MsgBuffer(MID, ACCOUNT_INFO_L2C);
		avo.writeMsg(msg.buffer);
		clientSH.send(msg);
	}
	
	/** send( data向login发送角色列表  ) */
	public void sendPlayerList_D2L(PlayerListPack pack, long clientBundleId)
	{
		Log.login.debug("sendPlayerList_D2L::" + pack);
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_LIST_D2L, SidType.LOGIN, clientBundleId);
		pack.write(msg.buffer);
		msg.send();
	}
	/** recv( data向login发送角色列表  ) */
	private void recvPlayerList_D2L(SessHand serverSH, IoBuffer buffer, long clientBundleId)
	{
		LoginClientBundle bundle = (LoginClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle == null)
		{
			Log.login.error("recvPlayerList_D2L::LoginClientBundle丢失");
			return;
		}
		PlayerListPack pack = new PlayerListPack();
		pack.read(buffer);
		Log.login.debug("recvPlayerList_D2L::" + pack);
		sendPlayerList_L2C(bundle.getClientSess(), pack);
	}
	
	/** send( login向client发送的账号信息 ) */
	public void sendPlayerList_L2C(SessHand clientSH, PlayerListPack pack)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_LIST_L2C);
		pack.write(msg.buffer);
		clientSH.send(msg);
	}
	
	/** recv( client向login请求新建角色 ) */
	private void recvPlayerCreateReq_C2L(SessHand clientSH, IoBuffer buffer, long clientBundleId)
	{
		LoginClientBundle bundle = (LoginClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle == null) { return; }
		
		String account = IBT.readString(buffer);
		String playerName = IBT.readString(buffer);
		byte sex = IBT.readByte(buffer);
		
		Log.login.debug("recvPlayerCreateReq_C2L account= " + account + " playerName=" + playerName + " sex=" + sex);
		sendPlayerCreateReq_L2D(account, playerName, sex, clientBundleId);
	}
	
	/** send( login向data请求新建角色 ) */
	public void sendPlayerCreateReq_L2D(String account, String playerName, byte sex, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_CREATE_REQ_L2D, SidType.DATA, clientBundleId);
		IBT.writeString(msg, account);
		IBT.writeString(msg, playerName);
		IBT.writeByte(msg, sex);
		msg.send();
	}
	/** recv( login向data请求新建角色 ) */
	private void recvPlayerCreateReq_L2D(SessHand serverSH, IoBuffer buffer, long clientBundleId)
	{
		String account = IBT.readString(buffer);
		String playerName = IBT.readString(buffer);
		byte sex = IBT.readByte(buffer);
		Log.db.debug("数据服收到account=" + account + " playerName=" + playerName + " sex=" + sex);
		PlayerCreateDbOper.create(account, playerName, sex, clientBundleId);
	}
	
	/** send( data向login发送的新建角色的结果 ) */
	public void sendPlayerCreateRpl_D2L(int errorCode, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_CREATE_RPL_D2L, SidType.LOGIN, clientBundleId);
		IBT.writeInt(msg, errorCode);
		msg.send();
	}
	/** recv( data向login发送的新建角色的结果 ) */
	private void recvPlayerCreatePpl_D2L(SessHand serverSH, IoBuffer buffer, long clientBundleId)
	{
		LoginClientBundle bundle = (LoginClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle == null)
		{
			Log.login.error("recvPlayerCreatePpl_D2L::LoginClientBundle丢失");
			return;
		}
		
		int errorCode = IBT.readInt(buffer);
		sendPlayerCreatePpl_L2C(bundle.getClientSess(), errorCode);
	}
	
	/** send( login向client发送的新建角色的结果 ) */
	public void sendPlayerCreatePpl_L2C(SessHand clientSH, int errorCode)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_CREATE_RPL_L2C);
		IBT.writeInt(msg, errorCode);
		clientSH.send(msg);
	}
	
	/** recv( client向login请求选择角色进入 ) */
	private void recvPlayerSelectReq_C2L(SessHand clientSH, IoBuffer buffer, long clientBundleId)
	{
		LoginClientBundle bundle = (LoginClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle == null) { return; }
		
		long playerUid = IBT.readLong(buffer);
		Log.login.debug("选择的角色uid=" + playerUid);
		sendPlayerEnterGameReq_L2G(playerUid, clientBundleId);
	}
	
	/** send( login向game请求角色进入游戏 ) */
	public void sendPlayerEnterGameReq_L2G(long playerUid, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_ENTER_GAME_REQ_L2G, SidType.GAME, clientBundleId);
		IBT.writeLong(msg, playerUid);
		msg.send();
	}
	/** recv( login向game请求角色进入游戏 ) */
	private void recvPlayerEnterGameReq_L2G(SessHand serverSH, IoBuffer buffer, long clientBundleId)
	{
		long playerUid = IBT.readLong(buffer);
		Log.game.debug("recvPlayerEnterGameReq_L2G::playerUid=" + playerUid);
		PlayerDataMsg.getInstance().sendPlayerDataReq_G2D(playerUid, clientBundleId);
	}
	
	/** recv( client向login请求删除角色 ) */
	private void recvPlayerDeleteReq_C2L(SessHand clientSH, IoBuffer buffer, long clientBundleId)
	{
		long playerUid = IBT.readLong(buffer);
		sendPlayerDeleteReq_L2D(playerUid, clientBundleId);
	}
	
	/** send( login向data请求删除角色 ) */
	private void sendPlayerDeleteReq_L2D(long playerUid, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_DELETE_REQ_L2D, SidType.DATA, clientBundleId);
		IBT.writeLong(msg, playerUid);
		msg.send();
	}
	
	/** recv( login向data请求删除角色 ) */
	private void recvPlayerDeleteReq_L2D(SessHand serverSH, IoBuffer buffer, long clientBundleId)
	{
		long playerUid = IBT.readLong(buffer);
		PlayerDeleteDbOper.delete(playerUid, clientBundleId);
	}
	
}
