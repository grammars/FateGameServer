package common.struct.task;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONObject;

import utils.JsonUtils;
import framework.net.IBT;

public class StTaskItem
{
	/** 未接到这个任务 */
	public static final byte ST_NULL = 0;
	/** 已接到这个任务，正在进行中 */
	public static final byte ST_ING = 1;
	/** 已满足任务条件，但还没有交掉这个任务 */
	public static final byte ST_DONE = 2;
	/** 任务交掉，已完成 */
	public static final byte ST_OVER = 3;
	
	/** 任务id */
	public int taskId = 0;
	/** 任务状态 */
	public byte state = 0;
	
	/** 是否跟npc对话过 */
	public boolean talkedNpc = false;
	/** 已杀怪物数量 */
	public int killMonsterNum = 0;
	/** 获取物品数量 */
	public int gotGoodsNum = 0;
	/** 是否完成目标副本 */
	public boolean passZone = false;
	
	/** 写入IoBuffer */
	public void write(IoBuffer buffer)
	{
		IBT.writeInt(buffer, taskId);
		IBT.writeByte(buffer, state);
		IBT.writeBoolean(buffer, talkedNpc);
		IBT.writeInt(buffer, killMonsterNum);
		IBT.writeInt(buffer, gotGoodsNum);
		IBT.writeBoolean(buffer, passZone);
	}
	
	/** 从IoBuffer读出 */
	public void read(IoBuffer buffer)
	{
		taskId = IBT.readInt(buffer);
		state = IBT.readByte(buffer);
		talkedNpc = IBT.readBoolean(buffer);
		killMonsterNum = IBT.readInt(buffer);
		gotGoodsNum = IBT.readInt(buffer);
		passZone = IBT.readBoolean(buffer);
	}
	
	public JSONObject encode()
	{
		JSONObject jso = new JSONObject();
		JsonUtils.putInt(jso, "taskId", taskId);
		JsonUtils.putByte(jso, "state", state);
		JsonUtils.putBoolean(jso, "talkedNpc", talkedNpc);
		JsonUtils.putInt(jso, "killMonsterNum", killMonsterNum);
		JsonUtils.putInt(jso, "gotGoodsNum", gotGoodsNum);
		JsonUtils.putBoolean(jso, "passZone", passZone);
		return jso;
	}
	
	public void decode(JSONObject jso)
	{
		taskId = JsonUtils.getInt(jso, "taskId");
		state = JsonUtils.getByte(jso, "state");
		talkedNpc = JsonUtils.getBoolean(jso, "talkedNpc");
		killMonsterNum = JsonUtils.getInt(jso, "killMonsterNum");
		gotGoodsNum = JsonUtils.getInt(jso, "gotGoodsNum");
		passZone = JsonUtils.getBoolean(jso, "passZone");
	}
}
