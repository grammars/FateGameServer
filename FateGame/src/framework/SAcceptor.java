package framework;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import framework.define.SidType;
import framework.net.FreeByteCodecFactory;

public class SAcceptor
{	
	/** 自身的会话身份类型[SidType] */
	protected byte srvType;
	
	protected NioSocketAcceptor acceptor;
	
	public SAcceptor(byte srvType)
	{
		this.srvType = srvType;
	}
	
	public void start()
	{
		String bindIp = SetupCfg.getIpBySidType(this.srvType);
		int bindPort = SetupCfg.getPortBySidType(this.srvType);
		if(bindIp != null && bindPort > 0)
		{
			acceptor = new NioSocketAcceptor();
			SAcceptorHandler handler = new SAcceptorHandler();
			acceptor.setHandler(handler);
			acceptor.setDefaultLocalAddress(new InetSocketAddress(bindIp, bindPort));
			acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new FreeByteCodecFactory()));
			acceptor.getSessionConfig().setMinReadBufferSize(64);
			acceptor.getSessionConfig().setReceiveBufferSize(1024 * 8);
			acceptor.getSessionConfig().setMaxReadBufferSize(65536);
			acceptor.getSessionConfig().setSendBufferSize(1024 * 32);
			acceptor.getSessionConfig().setTcpNoDelay(true);
			acceptor.getSessionConfig().setWriteTimeout(1000 * 20);
			//acceptor.getSessionConfig().setKeepAlive(true);
			acceptor.getSessionConfig().setSoLinger(0);
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 180);
			
			try
			{
				acceptor.bind();
				Log.system.fatal("服务启动成功，开始监听----->" + acceptor.getDefaultLocalAddress());
			}
			catch (IOException e)
			{
				acceptor.unbind();
				e.printStackTrace();
				Log.system.fatal("服务监听异常，程序退出!!!!!");
				System.exit(0);
			}
		}
	}
	
	public void stop()
	{
		if(acceptor != null)
		{
			acceptor.unbind();
		}
	}
	
}
