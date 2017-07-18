package common.struct.practice;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONObject;

import utils.JsonUtils;
import framework.net.IBT;

/** 修炼信息 */
public class StPracticeData
{
	public static final int TOP_LEV = 24;
	
	/** 修炼经验 */
	public int exp = 0;
	/** 修炼境界等级 */
	public int lev = 0;
	
	/** 写入IoBuffer */
	public void write(IoBuffer buffer)
	{
		IBT.writeInt(buffer, exp);
		IBT.writeInt(buffer, lev);
	}
	
	/** 从IoBuffer读出 */
	public StPracticeData read(IoBuffer buffer)
	{
		exp = IBT.readInt(buffer);
		lev = IBT.readInt(buffer);
		return this;
	}
	
	/** 数据库存储编码 */
	public JSONObject encode()
	{
		JSONObject jso = new JSONObject();
		JsonUtils.putInt(jso, "exp", exp);
		JsonUtils.putInt(jso, "lev", lev);
		return jso;
	}
	
	/** 数据库存储解码 */
	public void decode(JSONObject jso)
	{
		exp = JsonUtils.getInt(jso, "exp");
		lev = JsonUtils.getInt(jso, "lev");
	}
	
}
