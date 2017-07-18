package common.component.goods;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONObject;

import utils.JsonUtils;
import common.component.goods.info.DrugGI;
import common.component.goods.info.EquipGI;
import common.component.goods.info.GemGI;
import common.component.goods.info.GiftGI;
import common.component.goods.info.SystemGI;
import common.component.goods.info.TaskGI;
import common.define.GoodsType;
import framework.Log;
import framework.net.IBT;
import cfg.GoodsBaseCfg;

public class GoodsFactory
{
	/** 创建一个物品信息
	 * @param mid 物品的模版表id */
	public static GoodsInfo createInfo(int baseCfgId)
	{
		GoodsBaseCfg baseCfg = GoodsBaseCfg.get(baseCfgId);
		if(baseCfg == null)
		{
			Log.game.error("找不到baseCfgId=" + baseCfgId + "的物品");
			return null;
		}
		GoodsInfo info = create(baseCfg.type);
		info.create(baseCfgId);
		return info;
	}
	
	public static GoodsInfo create(int type)
	{
		switch(type)
		{
		case GoodsType.SYSTEM: return new SystemGI();
		case GoodsType.DRUG: return new DrugGI();
		case GoodsType.TASK: return new TaskGI();
		case GoodsType.EQUIP: return new EquipGI();
		case GoodsType.GEM: return new GemGI();
		case GoodsType.GIFT: return new GiftGI();
		default: return null;
		}
	}
	
	/** 从IoBuffer中读取 */
	public static GoodsInfo readInfo(IoBuffer buffer)
	{
		int baseCfgId = IBT.readInt(buffer);
		GoodsInfo info = createInfo(baseCfgId);
		info.read(buffer);
		return info;
	}
	
	/** 写入到IoBuffer */
	public static void writeInfo(GoodsInfo info, IoBuffer buffer)
	{
		IBT.writeInt(buffer, info.baseCfgId);
		info.write(buffer);
	}
	
	/** 克隆一个GoodsInfo */
	public static GoodsInfo cloneInfo(GoodsInfo source)
	{
		int type = source.baseCfg.type;
		GoodsInfo info = create(type);
		info.copy(source);
		return info;
	}
	
	public static JSONObject encodeInfo(GoodsInfo info)
	{
		JSONObject jso = new JSONObject();
		JsonUtils.putInt(jso, "baseCfgId", info.baseCfgId);
		JsonUtils.putJSONObject(jso, "info", info.encode());
		return jso;
	}
	
	public static GoodsInfo decodeInfo(JSONObject jso)
	{
		int baseCfgId = JsonUtils.getInt(jso, "baseCfgId");
		GoodsInfo info = createInfo(baseCfgId);
		info.decode( JsonUtils.getJSONObject(jso, "info") );
		return info;
	}
	
	
}
