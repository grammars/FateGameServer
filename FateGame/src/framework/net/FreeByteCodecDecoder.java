package framework.net;

import java.nio.ByteOrder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class FreeByteCodecDecoder extends CumulativeProtocolDecoder
{
	private static Logger LOGGER = Logger.getLogger(FreeByteCodecDecoder.class);

	/** 消息体最大字节长(单位:byte) */
	private static final int MSG_BODY_MAX_LEN = 128 * 1024;
	
	private final AttributeKey CONTEXT = new AttributeKey(getClass(),
			"FreeByteCodecDecoder.context");

	private FreeByteContext getContext(IoSession session)
	{
		FreeByteContext ctx = (FreeByteContext) session.getAttribute(CONTEXT);
		if (ctx == null)
		{
			ctx = new FreeByteContext();
			session.setAttribute(CONTEXT, ctx);
		}
		return ctx;
	}

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception
	{
		in.order(ByteOrder.BIG_ENDIAN); // 字节序

		// 考虑以下几种情况：
		// 1. 一个ip包中只包含一个完整消息
		// 2. 一个ip包中包含一个完整消息和另一个消息的一部分
		// 3. 一个ip包中包含一个消息的一部分
		// 4. 一个ip包中包含两个完整的数据消息或更多（循环处理在父类的decode中）
		if (in.remaining() >= 4)//4即int的长度
		{
			int length = in.getInt();
			if (length <= 0 || length > MSG_BODY_MAX_LEN)
			{
				LOGGER.error("收到异常消息，本次socket数据全部被废弃 length=" + length);
				byte[] eat = new byte[in.remaining()];
				in.get(eat);
				return true;
			}
			if (in.remaining() < length)
			{
				in.skip(-4);
				return false;
			}
			// 复制一个完整消息
			byte[] bytes = new byte[length];
			in.get(bytes);
			// 消息buf
			IoBuffer buf = IoBuffer.allocate(length); //最大消息字节数
			buf.order(ByteOrder.BIG_ENDIAN);
			buf.put(bytes);
			buf.flip();
			out.write(buf);
			return true;
		}
		else
		{
			return false;
		}
	}

}
