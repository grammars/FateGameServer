package robot;

import java.net.InetSocketAddress;

import message.player.PlayerEventMsg;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import framework.MsgBuffer;
import framework.SessHand;
import framework.SetupCfg;
import framework.define.SidType;
import framework.net.FreeByteCodecFactory;

public class RobotClient
{
	public long clientUid;
	public SessHand sh;
	
	private int index;
	
	private static int indexCount = 0;
	
	public RobotClient()
	{
		this.index = ++indexCount;
		clientUid = (long)((double)770000000 + (double)10000000*Math.random());
	}
	
	/** 启动机器人 */
	public void start()
	{
		NioSocketConnector conn = new NioSocketConnector();
		RobotMsgHandler handler = new RobotMsgHandler(this);
		conn.setHandler(handler);
		conn.getFilterChain().addLast("codec", new ProtocolCodecFilter(new FreeByteCodecFactory()));
		conn.getSessionConfig().setWriteTimeout(1000 * 20);
		conn.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 240);
		conn.getSessionConfig().setTcpNoDelay(true);
		
		String targetIp = SetupCfg.getIpBySidType(SidType.GATE);
		int targetPort = SetupCfg.getPortBySidType(SidType.GATE);
		InetSocketAddress addr = new InetSocketAddress(targetIp, targetPort);
		ConnectFuture future = conn.connect(addr);
		future.awaitUninterruptibly();
		boolean conncected = future.isConnected();
		if(conncected)
		{
			System.out.println("与GateSrv成功连接");
			IoSession session = future.getSession(); 
			sh = new SessHand(session);
			sh.attachSession();
			
			MsgBuffer msg = new MsgBuffer(PlayerEventMsg.MID, PlayerEventMsg.PLAYER_LOGIN_GATE_REQ_C2G, SidType.GATE, clientUid);
			//MsgBuffer msg = new MsgBuffer(GmMsg.MID, GmMsg.ROBOT_CMD_R2G, SidType.GAME, clientUid);
			sh.send(msg);
		}
		else
		{
			System.err.println("无法与GateSrv取得连接");
		}
		
		System.err.println("no." + index + "机器人已启动");
	}
}
