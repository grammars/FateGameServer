package framework.net;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import framework.Log;

public class FreeByteCodecEncoder implements ProtocolEncoder
{
	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception
	{
		byte[] bytes = (byte[])message;
		System.out.println("消息组装bytes.length=" + bytes.length);
        IoBuffer buf = IoBuffer.allocate(bytes.length+4);
        buf.putInt(bytes.length);
        buf.put(bytes);
        buf.flip();
        out.write(buf);
	}

	@Override
	public void dispose(IoSession session) throws Exception
	{
		// TODO Auto-generated method stub

	}

}
