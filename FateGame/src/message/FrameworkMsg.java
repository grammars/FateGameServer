package message;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import framework.AppContext;
import framework.Log;
import framework.MsgBuffer;
import framework.SessHand;
import framework.define.SidType;
import framework.net.IBT;

public class FrameworkMsg implements IMessage
{
	private static FrameworkMsg instance;
	public static FrameworkMsg getInstance()
	{
		if(instance == null) { instance = new FrameworkMsg(); }
		return instance;
	}
	
	/** [框架核心]主消息号 */
	public static final int MID = FRAMEWORK_MID;
	
	/** 服务器互相登录请求 */
	private static final int SRV_LOGIN_REQ = 0;
	/** 服务器互相登录的应答 */
	private static final int SRV_LOGIN_RPL = 1;
	
	@Override
	public void recv(SessHand serverSH, int subMid, IoBuffer buffer, long clientBundleId)
	{
		switch(subMid)
		{
		case SRV_LOGIN_REQ:
			recvSrvLoginReq(serverSH, buffer);
			break;
		case SRV_LOGIN_RPL:
			recvSrvLoginRpl(serverSH, buffer);
			break;
		}
	}
	
	/** send(服务器互相登录请求) */
	public void sendSrvLoginReq(IoSession session, byte myType)
	{
		MsgBuffer msg = new MsgBuffer(MID, SRV_LOGIN_REQ);
		IBT.writeByte(msg, myType);
		session.write(msg.createMsg());
	}
	/** recv(服务器互相登录请求) */
	private void recvSrvLoginReq(SessHand serverSH, IoBuffer buffer)
	{
		byte connectorSidType = IBT.readByte(buffer);
		Log.system.fatal("recv(服务器互相登录请求)" + connectorSidType);
		AppContext.regSrvSessHand(connectorSidType, serverSH);
		sendSrvLoginRpl(serverSH);
	}
	
	/** send(服务器互相登录的应答) */
	public void sendSrvLoginRpl(SessHand serverSH)
	{
		MsgBuffer msg = new MsgBuffer(MID, SRV_LOGIN_RPL, SidType.SERVER, 0);
		IBT.writeByte(msg, AppContext.getSidType());
		serverSH.send(msg.createMsg());
	}
	/** recv(服务器互相登录的应答) */
	private void recvSrvLoginRpl(SessHand serverSH, IoBuffer buffer)
	{
		byte acceptorSidType = IBT.readByte(buffer);
		Log.system.fatal("recv(服务器互相登录的应答)" + acceptorSidType);
		AppContext.regSrvSessHand(acceptorSidType, serverSH);
	}
	
}
