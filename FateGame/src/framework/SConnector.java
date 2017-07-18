package framework;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import utils.Utils;
import framework.define.SidType;
import framework.net.FreeByteCodecFactory;

public class SConnector
{
	/** 自身的会话类型[SidType] */
	public byte srvType;
	/** 目标的会话类型[SidType] */
	public byte targetSrvType;
	
	protected NioSocketConnector connector;
	
	public SConnector(byte srvType, byte targetSrvType)
	{
		this.srvType = srvType;
		this.targetSrvType = targetSrvType;
	}
	
	public void start()
	{
		connector = new NioSocketConnector();
		SConnectorHandler handler = new SConnectorHandler(this);
		connector.setHandler(handler);
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new FreeByteCodecFactory()));
		connector.getSessionConfig().setWriteTimeout(1000 * 20);
		connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 240);
		connector.getSessionConfig().setTcpNoDelay(true);
		
		String targetIp = SetupCfg.getIpBySidType(this.targetSrvType);
		int targetPort = SetupCfg.getPortBySidType(this.targetSrvType);
		InetSocketAddress addr = new InetSocketAddress(targetIp, targetPort);
		boolean conncected = false;
		while(!conncected)
		{
			//Log.system.fatal("connector尝试连接目标服务器");
			ConnectFuture future = connector.connect(addr);
			future.awaitUninterruptibly();
			conncected = future.isConnected();
			if(!conncected)
			{
				Utils.delay(3000);
			}
		}
		Log.system.fatal("connector连接目标服务器成功");
	}
	
	public void stop()
	{
		
	}
}
