package common.struct.skill;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONObject;

import utils.JsonUtils;
import framework.net.IBT;

public class StSkillItem
{
	/** 技能id */
	public int id;
	/** 技能等级 */
	public int level;
	
	/** 写入IoBuffer */
	public void write(IoBuffer buffer)
	{
		IBT.writeInt(buffer, id);
		IBT.writeInt(buffer, level);
	}
	
	/** 从IoBuffer读出 */
	public void read(IoBuffer buffer)
	{
		id = IBT.readInt(buffer);
		level = IBT.readInt(buffer);
	}
	
	public JSONObject encode()
	{
		JSONObject jso = new JSONObject();
		JsonUtils.putInt(jso, "id", id);
		JsonUtils.putInt(jso, "level", level);
		return jso;
	}
	
	public void decode(JSONObject jso)
	{
		id = JsonUtils.getInt(jso, "id");
		level = JsonUtils.getInt(jso, "level");
	}
}
