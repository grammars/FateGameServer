package common.struct.goods;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import utils.JsonUtils;
import common.component.goods.GoodsFactory;
import common.component.goods.GoodsInfo;
import framework.net.IBT;

public class StGoodsContainer
{
	/** 当前容量 */
	public int capacity = 18;
	
	/** 物品们 */
	public List<GoodsInfo> items = new ArrayList<>();

	public void write(IoBuffer buffer)
	{
		int num = items.size();
		IBT.writeInt(buffer, num);
		for(int i = 0; i < num; i++)
		{
			GoodsInfo item = items.get(i);
			GoodsFactory.writeInfo(item, buffer);
		}
	}

	public void read(IoBuffer buffer)
	{
		items.clear();
		int num = IBT.readInt(buffer);
		for(int i = 0; i < num; i++)
		{
			GoodsInfo item = GoodsFactory.readInfo(buffer);
			items.add(item);
		}
	}

	public JSONArray encode()
	{
		JSONArray jarr = new JSONArray();
		int num = items.size();
		for(int i = 0; i < num; i++)
		{
			GoodsInfo item = items.get(i);
			JsonUtils.putJSONObject(jarr, GoodsFactory.encodeInfo(item) );
		}
		return jarr;
	}

	public void decode(JSONArray jarr)
	{
		items.clear();
		int num = jarr.length();
		for(int i = 0; i < num; i++)
		{
			JSONObject jso = JsonUtils.getJSONObject(jarr, i);
			GoodsInfo item = GoodsFactory.decodeInfo(jso);
			items.add(item);
		}
	}
}
