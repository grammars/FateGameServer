package framework.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;

import framework.MsgBuffer;

/** IoBufferTool */
public class IBT
{
	public static final String CHARSET = "UTF-8";
	
	public static Charset cs;
	public static CharsetDecoder dec;
	public static CharsetEncoder enc;
	
	static
	{
		setCharset(CHARSET);
		System.out.println("IBT initialize");
	}
	
	/** 获取IBT的版本号 */
	public static String getVersion()
	{
		return "1.0.0";
	}
	
	/** 设置字符编码 */
	public static void setCharset(String cname)
	{
		cs = Charset.forName(cname);
		dec = cs.newDecoder();
		enc = cs.newEncoder();
	}
	
	public static String readString(IoBuffer src)
	{
		return readString(src, -1);
	}
	
	public static String readString(IoBuffer src, int bytesLen)
	{
		int len = src.getInt();
		String str = "";
		try
		{
			if(bytesLen > 0)
			{
				len = bytesLen;
			}
			str = src.getString(len, dec);
		}
		catch (CharacterCodingException e)
		{
			e.printStackTrace();
		}
		return str;
	}
	

	public static void writeString(IoBuffer dest, String value)
	{
		writeString(dest, value, -1);
	}
	
	public static void writeString(IoBuffer dest, String value, int bytesLen)
	{
		if(value == null)
		{
			value = "";
		}
		int len = bytesLen;
		if(bytesLen <= 0)
		{
			try { len = value.getBytes(CHARSET).length; }
			catch (UnsupportedEncodingException e) { e.printStackTrace(); }
		}
		try
		{
			dest.putInt(len);
			dest.putString(value, len, enc);
		}
		catch (CharacterCodingException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void writeString(MsgBuffer dest, String value)
	{
		writeString(dest.buffer, value);
	}
	
	public static void writeString(MsgBuffer dest, String value, int bytesLen)
	{
		writeString(dest.buffer, value, bytesLen);
	}
	
	//======================================================================
	
	public static byte[] readBytes(IoBuffer src)
	{
		int size = src.getInt();
		byte[] bytes = new byte[size];
		src.get(bytes, 0, size);
		return bytes;
	}
	
	public static void writeBytes(IoBuffer dest, byte[] value)
	{
		dest.putInt(value.length);
		dest.put(value);
	}
	
	public static void writeBytes(MsgBuffer dest, byte[] value)
	{
		writeBytes(dest.buffer, value);
	}
	
	//======================================================================
	
	public static boolean readBoolean(IoBuffer src)
	{
		byte bt = src.get();
		return bt != 0;
	}
	
	public static void writeBoolean(IoBuffer dest, boolean value)
	{
		byte bt = value==true ? (byte)1 : 0;
		dest.put(bt);
	}
	
	public static void writeBoolean(MsgBuffer dest, boolean value)
	{
		writeBoolean(dest.buffer, value);
	}
	
	//======================================================================
	
	public static byte readByte(IoBuffer src)
	{
		return src.get();
	}
	
	public static void writeByte(IoBuffer dest, byte value)
	{
		dest.put(value);
	}
	
	public static void writeByte(MsgBuffer dest, byte value)
	{
		writeByte(dest.buffer, value);
	}
	
	//======================================================================
	
	public static char readChar(IoBuffer src)
	{
		return src.getChar();
	}
	
	public static void writeChar(IoBuffer dest, char value)
	{
		dest.putChar(value);
	}
	
	public static void writeChar(MsgBuffer dest, char value)
	{
		writeChar(dest.buffer, value);
	}
	
	//======================================================================
	
	public static short readShort(IoBuffer src)
	{
		return src.getShort();
	}
	
	public static void writeShort(IoBuffer dest, short value)
	{
		dest.putShort(value);
	}
	
	public static void writeShort(MsgBuffer dest, short value)
	{
		writeShort(dest.buffer, value);
	}
	
	//======================================================================

	public static int readInt(IoBuffer src)
	{
		return src.getInt();
	}
	
	public static void writeInt(IoBuffer dest, int value)
	{
		dest.putInt(value);
	}
	
	public static void writeInt(MsgBuffer dest, int value)
	{
		writeInt(dest.buffer, value);
	}
	
	//======================================================================
	
	public static long readLong(IoBuffer src)
	{
		return src.getLong();
	}
	
	public static void writeLong(IoBuffer dest, long value)
	{
		dest.putLong(value);
	}
	
	public static void writeLong(MsgBuffer dest, long value)
	{
		writeLong(dest.buffer, value);
	}
	
	//======================================================================
	
	public static float readFloat(IoBuffer src)
	{
		return src.getFloat();
	}
	
	public static void writeFloat(IoBuffer dest, float value)
	{
		dest.putFloat(value);
	}
	
	public static void writeFloat(MsgBuffer dest, float value)
	{
		writeFloat(dest.buffer, value);
	}
	
	//======================================================================
	
	public static double readDouble(IoBuffer src)
	{
		return src.getDouble();
	}
	
	public static void writeDouble(IoBuffer dest, double value)
	{
		dest.putDouble(value);
	}
	
	public static void writeDouble(MsgBuffer dest, double value)
	{
		writeDouble(dest.buffer, value);
	}
	
	//======================================================================
	
	public static void clear(IoBuffer buffer)
	{
		buffer.clear();
	}
	
}
