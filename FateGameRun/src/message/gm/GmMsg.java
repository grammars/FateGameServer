package message.gm;

import org.apache.mina.core.buffer.IoBuffer;

import framework.ClientBundleManager;
import framework.Log;
import framework.MsgBuffer;
import framework.SessHand;
import framework.net.IBT;
import game.GameClientBundle;
import message.IMessage;
import message.MessageId;

public class GmMsg implements IMessage
{
	private static GmMsg instance;
	public static GmMsg getInstance()
	{
		if(instance == null) { instance = new GmMsg(); }
		return instance;
	}
	
	/** [角色数据]主消息号 */
	public static final int MID = MessageId.GM_MID;
	
	/** client向game请求执行参数化的GM命令 */
	private static final int PARAMS_CMD_C2G = 0;
	
	@Override
	public void recv(SessHand sess, int subMid, IoBuffer buffer, long clientBundleId)
	{
		switch(subMid)
		{
		case PARAMS_CMD_C2G:
			recvParamsCMD_C2G(sess, buffer, clientBundleId);
			break;
		}
	}
	
	/** recv( client向game请求执行参数化的GM命令 ) */
	private void recvParamsCMD_C2G(SessHand sess, IoBuffer buffer, long clientBundleId)
	{
		//try
		{
			GameClientBundle bundle = (GameClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
			if(bundle == null)
			{
				return;
			}
			GmParamCmd.handle(buffer, bundle);
		}
		//catch(Exception ex)
		//{
		//	Log.system.error("收到错误的请求执行参数化的GM命令");
		//}
	}

}
