package message.gm;

import message.gm.params.GmFightPC;
import message.gm.params.GmGoodsPC;
import message.gm.params.GmOthersPC;
import message.gm.params.GmPlayerPC;
import message.gm.params.GmTaskPC;

import org.apache.mina.core.buffer.IoBuffer;

import framework.net.IBT;
import game.GameClientBundle;

public class GmParamCmd
{
	private static final int TYPE_OTHERS = 0;
	private static final int TYPE_PLAYER = 1;
	private static final int TYPE_FIGHT = 2;
	private static final int TYPE_GOODS = 3;
	private static final int TYPE_TASK = 4;
	
	/** _cmdId_byte_byte_int_int_int_float_double_long_long_str_str_ */
	public static void handle(IoBuffer buffer, GameClientBundle bundle)// throws Exception
	{
		int mainCmdId = IBT.readInt(buffer);
		int subCmdId = IBT.readInt(buffer);
		byte byte0 = IBT.readByte(buffer);
		byte byte1 = IBT.readByte(buffer);
		int int0 = IBT.readInt(buffer);
		int int1 = IBT.readInt(buffer);
		int int2 = IBT.readInt(buffer);
		float float0 = IBT.readFloat(buffer);
		double double0 = IBT.readDouble(buffer);
		long long0 = IBT.readLong(buffer);
		long long1 = IBT.readLong(buffer);
		String str0 = IBT.readString(buffer);
		String str1 = IBT.readString(buffer);
		
		switch(mainCmdId)
		{
		case TYPE_OTHERS:
			GmOthersPC.cmdHandler(subCmdId, bundle, byte0, byte1, int0, int1, int2, float0, double0, long0, long1, str0, str1);
			break;
		case TYPE_PLAYER:
			GmPlayerPC.cmdHandler(subCmdId, bundle, byte0, byte1, int0, int1, int2, float0, double0, long0, long1, str0, str1);
			break;
		case TYPE_FIGHT:
			GmFightPC.cmdHandler(subCmdId, bundle, byte0, byte1, int0, int1, int2, float0, double0, long0, long1, str0, str1);
			break;
		case TYPE_GOODS:
			GmGoodsPC.cmdHandler(subCmdId, bundle, byte0, byte1, int0, int1, int2, float0, double0, long0, long1, str0, str1);
			break;
		case TYPE_TASK:
			GmTaskPC.cmdHandler(subCmdId, bundle, byte0, byte1, int0, int1, int2, float0, double0, long0, long1, str0, str1);
			break;
		}
	}
	
	
	
}
