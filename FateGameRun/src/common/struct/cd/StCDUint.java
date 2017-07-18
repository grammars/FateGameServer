package common.struct.cd;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import utils.JsonUtils;
import framework.net.IBT;

public class StCDUint
{
	/** 物品的冷却 */
	public Map<Integer, StCDInfo> goods = new HashMap<>();
	/** 技能的冷却 */
	public Map<Integer, StCDInfo> skill = new HashMap<>();
	
	/** 写入IoBuffer */
	public void write(IoBuffer buffer)
	{
		int goods_num = goods.size();
		IBT.writeInt(buffer, goods_num);
		Collection<StCDInfo> goodsArr = goods.values();
		for(StCDInfo info : goodsArr)
		{
			info.write(buffer);
		}
		
		int skill_num = skill.size();
		IBT.writeInt(buffer, skill_num);
		Collection<StCDInfo> skillArr = skill.values();
		for(StCDInfo info : skillArr)
		{
			info.write(buffer);
		}
	}
	
	/** 从IoBuffer读出 */
	public StCDUint read(IoBuffer buffer)
	{
		goods.clear();
		int goods_num = IBT.readInt(buffer);
		for(int i = 0; i < goods_num; i++)
		{
			StCDInfo info = new StCDInfo();
			info.read(buffer);
			goods.put(info.id, info);
		}
		
		skill.clear();
		int skill_num = IBT.readInt(buffer);
		for(int i = 0; i < skill_num; i++)
		{
			StCDInfo info = new StCDInfo();
			info.read(buffer);
			skill.put(info.id, info);
		}
		return this;
	}
	
	public JSONObject encode()
	{
		JSONObject jso = new JSONObject();
		
		JSONArray goods_arr = new JSONArray();
		for(StCDInfo info : goods.values())
		{
			JsonUtils.putJSONObject(goods_arr, info.encode());
		}
		
		JSONArray skill_arr = new JSONArray();
		for(StCDInfo info : skill.values())
		{
			JsonUtils.putJSONObject(skill_arr, info.encode());
		}
		
		JsonUtils.putJSONArray(jso, "goods", goods_arr);
		JsonUtils.putJSONArray(jso, "skill", skill_arr);
		return jso;
	}
	
	public void decode(JSONObject jso)
	{
		JSONArray goods_arr = JsonUtils.getJSONArray(jso, "goods");
		JSONArray skill_arr = JsonUtils.getJSONArray(jso, "skill");
		int goods_arr_size = goods_arr.length();
		int skill_arr_size = skill_arr.length();
		
		for(int i = 0; i < goods_arr_size; i++)
		{
			StCDInfo info = new StCDInfo();
			info.decode( JsonUtils.getJSONObject(goods_arr, i) );
			goods.put(info.id, info);
		}
		
		for(int i = 0; i < skill_arr_size; i++)
		{
			StCDInfo info = new StCDInfo();
			info.decode( JsonUtils.getJSONObject(skill_arr, i) );
			skill.put(info.id, info);
		}
	}
	
}
