package framework;

import java.util.HashMap;

import org.apache.mina.core.buffer.IoBuffer;

import message.IMessage;
import framework.define.SidType;

public class AppContext
{
	private static byte sidType;
	/** set(自身的会话身份类型) */
	public static void setSidType(byte value) { sidType = value; }
	/** get(自身的会话身份类型) */
	public static byte getSidType() { return sidType; }
	
	/** 与登录服的会话 */
	private static SessHand loginSess;
	/** 与网关服的会话 */
	private static SessHand gateSess;
	/** 与游戏服的会话 */
	private static SessHand gameSess;
	/** 与数据服的会话 */
	private static SessHand dataSess;
	/** 注册服务器会话 */
	public static void regSrvSessHand(byte sidType, SessHand sh)
	{
		switch(sidType)
		{
		case SidType.LOGIN: loginSess = sh; break;
		case SidType.GATE: gateSess = sh; break;
		case SidType.GAME: gameSess = sh; break;
		case SidType.DATA: dataSess = sh; break;
		default: break;
		}
	}
	
	/** 向指定服务器发送消息 */
	public static void sendMsg(MsgBuffer msg)
	{
		sendMsg(msg.sendTarget, msg.createMsg());
	}
	
	/** 向指定服务器发送消息 */
	public static void sendMsg(byte sidType, IoBuffer buffer)
	{
		switch(sidType)
		{
		case SidType.LOGIN: loginSess.send(buffer); break;
		case SidType.GATE: gateSess.send(buffer); break;
		case SidType.GAME: gameSess.send(buffer); break;
		case SidType.DATA: dataSess.send(buffer); break;
		case SidType.CLIENT: gateSess.send(buffer); break;
		default: break;
		}
	}
	
	private static HashMap<Integer, IMessage> msgMap = new HashMap<>();
	/** 添加消息处理
	 * @param mid 消息基础主id
	 * @param msgHandler 消息处理器 */
	public static void addMsgHandler(int mid, IMessage msgHandler)
	{
		msgMap.put(mid, msgHandler);
	}
	/** 移除消息处理
	 * @param mid 消息基础主id */
	public static void removeMsgHandler(int mid)
	{
		msgMap.remove(mid);
	}
	/** 获得消息处理
	 * @param mid 消息基础主id */
	public static IMessage getMsgHandler(int mid)
	{
		return msgMap.get(mid);
	}
	
	/** 会话管理器 */
	private static SessManager sessManager;
	/** set会话管理器 */
	public static void setSessManager(SessManager value)
	{
		sessManager = value;
	}
	/** get会话管理器 */
	public static SessManager getSessManager() { return sessManager; }
	
}
