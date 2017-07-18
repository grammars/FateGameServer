package common.struct.buff;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONArray;

import framework.net.IBT;

public class StBuffSet
{
	public List<StBuff> items = new ArrayList<>();
	
	public void write(IoBuffer buffer)
	{
		int num = items.size();
		IBT.writeInt(buffer, num);
		for(int i = 0; i < num; i++)
		{
			items.get(i).write(buffer);
		}
	}
	
	public StBuffSet read(IoBuffer buffer)
	{
		items.clear();
		int num = IBT.readInt(buffer);
		for(int i = 0; i < num; i++)
		{
			StBuff item = new StBuff();
			item.read(buffer);
			items.add(item);
		}
		return this;
	}
	
	/** encode */
	public JSONArray encode()
	{
		JSONArray jsArr = new JSONArray();
		int num = items.size();
		for(int i = 0; i < num; i++)
		{
			jsArr.put( items.get(i).encode() );
		}
		return jsArr;
	}
	
	/** decode */
	public void decode(JSONArray jsArr)
	{
		items.clear();
		int num = jsArr.length();
		for(int i = 0; i < num; i++)
		{
			StBuff item = new StBuff();
			item.decode(jsArr.getJSONObject(i));
			items.add(item);
		}
	}
}
