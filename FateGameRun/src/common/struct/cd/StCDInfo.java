package common.struct.cd;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONObject;

import common.struct.buff.StBuff;
import framework.net.IBT;
import utils.JsonUtils;
import utils.Utils;

public class StCDInfo
{
	/** 技能还是物品，取自CDUnit.T_Xxxx的类型 */
	public byte type;
	/** 对应技能或者物品的模版id */
	public int id;
	/** cd总耗时 */
	public int cdTime;
	/** cd完成时间 */
	public long finishTime;
	
	public void setup(byte type, int id, int msCDTime)
	{
		this.type = type;
		this.id = id;
		this.cdTime = msCDTime;
		this.finishTime = Utils.now()+msCDTime;
	}
	
	/** 写入IoBuffer */
	public void write(IoBuffer buffer)
	{
		IBT.writeByte(buffer, type);
		IBT.writeInt(buffer, id);
		IBT.writeInt(buffer, cdTime);
		IBT.writeLong(buffer, finishTime);
		
		int leftCDTime = (int)(finishTime-Utils.now());
		IBT.writeInt(buffer, leftCDTime);
	}
	
	/** 从IoBuffer读出 */
	public StCDInfo read(IoBuffer buffer)
	{
		type = IBT.readByte(buffer);
		id = IBT.readInt(buffer);
		cdTime =IBT.readInt(buffer); 
		finishTime = IBT.readLong(buffer);
		
		int leftCDTime = IBT.readInt(buffer);
		return this;
	}
	
	public JSONObject encode()
	{
		JSONObject jso = new JSONObject();
		JsonUtils.putByte(jso, "type", type);
		JsonUtils.putInt(jso, "id", id);
		JsonUtils.putInt(jso, "cdTime", cdTime);
		JsonUtils.putLong(jso, "finishTime", finishTime);
		return jso;
	}
	
	public void decode(JSONObject jso)
	{
		type = JsonUtils.getByte(jso, "type");
		id = JsonUtils.getInt(jso, "id");
		cdTime = JsonUtils.getInt(jso, "cdTime");
		finishTime = JsonUtils.getLong(jso, "finishTime");
	}
}
