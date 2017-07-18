package common.struct.skill;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import framework.net.IBT;

public class StSkillSet
{
	public List<StSkillItem> items = new ArrayList<>();
	
	/** 写入IoBuffer */
	public void write(IoBuffer buffer)
	{
		int num = items.size();
		IBT.writeInt(buffer, num);
		for(int i = 0; i < num; i++)
		{
			items.get(i).write(buffer);
		}
	}
	
	/** 从IoBuffer读出 */
	public StSkillSet read(IoBuffer buffer)
	{
		items.clear();
		int num = IBT.readInt(buffer);
		for(int i = 0; i < num; i++)
		{
			StSkillItem item = new StSkillItem();
			item.read(buffer);
			items.add(item);
		}
		return this;
	}
	
	/** 数据库存储编码 */
	public JSONArray encode()
	{
		JSONArray jsArr = new JSONArray();
		int num = items.size();
		for(int i = 0; i < num; i++)
		{
			JSONObject jsItem = items.get(i).encode();
			jsArr.put(jsItem);
		}
		return jsArr;
	}
	
	/** 数据库存储解码 */
	public void decode(JSONArray jsArr)
	{
		items.clear();
		int num = jsArr.length();
		for(int i = 0; i < num; i++)
		{
			StSkillItem item = new StSkillItem();
			item.decode(jsArr.getJSONObject(i));
			items.add(item);
		}
	}
}
