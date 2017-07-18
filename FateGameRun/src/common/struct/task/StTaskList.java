package common.struct.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import utils.JsonUtils;
import framework.net.IBT;

public class StTaskList
{
	/** 已完成的任务id们 */
	public List<Integer> finishIds = new ArrayList<>();
	
	public List<StTaskItem> items = new ArrayList<>();
	
	/** 写入IoBuffer */
	public void write(IoBuffer buffer)
	{
		int finishIdsNum = finishIds.size();
		IBT.writeInt(buffer, finishIdsNum);
		for(int i = 0; i < finishIdsNum; i++)
		{
			IBT.writeInt(buffer, finishIds.get(i));
		}
		
		int num = items.size();
		IBT.writeInt(buffer, num);
		for(int i = 0; i < num; i++)
		{
			items.get(i).write(buffer);
		}
	}
	
	/** 从IoBuffer读出 */
	public StTaskList read(IoBuffer buffer)
	{
		int finishIdsNum = IBT.readInt(buffer);
		finishIds.clear();
		for(int i = 0; i < finishIdsNum; i++)
		{
			finishIds.add( IBT.readInt(buffer) );
		}
		
		items.clear();
		int num = IBT.readInt(buffer);
		for(int i = 0; i < num; i++)
		{
			StTaskItem item = new StTaskItem();
			item.read(buffer);
			items.add(item);
		}
		return this;
	}
	
	/** 数据库存储编码 */
	public JSONObject encode()
	{
		JSONObject jso = new JSONObject();
		JsonUtils.putCollection(jso, "finishIds", finishIds);
		JSONArray jsArr = new JSONArray();
		int num = items.size();
		for(int i = 0; i < num; i++)
		{
			JSONObject jsItem = items.get(i).encode();
			jsArr.put(jsItem);
		}
		JsonUtils.putJSONArray(jso, "items", jsArr);
		return jso;
	}
	
	/** 数据库存储解码 */
	public void decode(JSONObject jso)
	{
		JSONArray fija = JsonUtils.getCollection(jso, "finishIds");
		finishIds.clear();
		int finishIdsNum = fija.length();
		for(int i = 0; i < finishIdsNum; i++)
		{
			finishIds.add( JsonUtils.getInt(fija, i) );
		}
		
		JSONArray jsArr = JsonUtils.getJSONArray(jso, "items");
		items.clear();
		int num = jsArr.length();
		for(int i = 0; i < num; i++)
		{
			StTaskItem item = new StTaskItem();
			item.decode(jsArr.getJSONObject(i));
			items.add(item);
		}
	}
}
