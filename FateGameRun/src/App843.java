import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


public class App843 extends IoHandlerAdapter
{

	protected final static String xml = "<cross-domain-policy> "
			+ "<allow-access-from domain=\"*\" to-ports=\"1025-9999\"/>"
			+ "</cross-domain-policy>";

	private Charset charset = Charset.forName("utf-8");

	private CharsetDecoder decoder = charset.newDecoder();

	private CharsetEncoder encoder = charset.newEncoder();

	public void messageReceived(IoSession session, Object message)
	{
		IoBuffer buffer = (IoBuffer) message;
		try
		{
			String str = buffer.getString(decoder);
			if (str.indexOf("<policy-file-request/>") >= 0)
			{
				System.out.println("安全沙箱消息发送!!");
				IoBuffer buffer2 = IoBuffer.allocate(100).setAutoExpand(true);
				buffer2.putString(xml + "\0", encoder);
				buffer2.flip();
				session.write(buffer2);
			}
		}
		catch (CharacterCodingException e)
		{
			e.printStackTrace();
		}
	}

	public void start()
	{
		NioSocketAcceptor acceptor = null;
		try
		{
			acceptor = new NioSocketAcceptor();
			// handler
			acceptor.setHandler(this);
			// 监听地址a
			acceptor.setDefaultLocalAddress(new InetSocketAddress("0.0.0.0", 843));
			// 解码，编码器
			acceptor.getSessionConfig().setTcpNoDelay(true);
			acceptor.getSessionConfig().setReceiveBufferSize(1024 * 8);
			acceptor.getSessionConfig().setSendBufferSize(1024 * 32);
			acceptor.getSessionConfig().setWriteTimeout(1000 * 20);
			acceptor.getSessionConfig().setKeepAlive(true);
			acceptor.getSessionConfig().setSoLinger(0);
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 240);
			acceptor.bind();
			System.out.println("flash  843  port start !!!");
		}
		catch (IOException e)
		{
			if (acceptor != null)
			{
				acceptor.unbind();
			}

			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		new App843().start();
	}

}
