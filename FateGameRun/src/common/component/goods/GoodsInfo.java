package common.component.goods;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONObject;

import common.component.goods.info.*;
import common.define.GoodsType;
import utils.JsonUtils;
import utils.Utils;
import cfg.GoodsBaseCfg;
import framework.Log;
import framework.net.IBT;

public abstract class GoodsInfo
{
	/** 唯一id */
	public long uid = 0;
	/** 物品位置 */
	public int index = 0;
	/** 物品基础配置id */
	public int baseCfgId = 0;
	/** 物品数量 */
	public int num = 0;
	
	/** 物品基础配置 */
	public GoodsBaseCfg baseCfg;
	
	/** 可供再增加的数目 */
	public int numRoom() { return this.baseCfg.maxHeap - this.num; }
	
	public boolean isSytem() { return baseCfg.type == GoodsType.SYSTEM; }
	public boolean isDrug() { return baseCfg.type == GoodsType.DRUG; }
	public boolean isTask() { return baseCfg.type == GoodsType.TASK; }
	public boolean isEquip() { return baseCfg.type == GoodsType.EQUIP; }
	public boolean isGem() { return baseCfg.type == GoodsType.GEM; }
	public boolean isGift() { return baseCfg.type == GoodsType.GIFT; }
	
	public SystemGI toSystem() { return (SystemGI)this; }
	public DrugGI toDrug() { return (DrugGI)this; }
	public TaskGI toTask() { return (TaskGI)this; }
	public EquipGI toEquip() { return (EquipGI)this; }
	public GemGI toGem() { return (GemGI)this; }
	public GiftGI toGift() { return (GiftGI)this; }
	
	/** 数据是否合法可用 */
	public boolean available()
	{
		return baseCfg != null;
	}
	
	public GoodsInfo()
	{
		//
	}
	
	/** 创建新的uid */
	public void createUid()
	{
		this.uid = Utils.createUidLong();
	}
	
	public void create(int baseCfgId)
	{
		this.baseCfgId = baseCfgId;
		createUid();
		build();
	}
	
	protected void build()
	{
		this.baseCfg = GoodsBaseCfg.get(baseCfgId);
		if(baseCfg == null)
		{
			Log.game.error("找不到mid=" + baseCfgId + "的物品");
		}
	}
	
	public void write(IoBuffer buffer)
	{
		IBT.writeLong(buffer, uid);
		IBT.writeInt(buffer, index);
		IBT.writeInt(buffer, baseCfgId);
		IBT.writeInt(buffer, num);
		writeHandler(buffer);
	}
	
	abstract protected void writeHandler(IoBuffer buffer);
	
	public void read(IoBuffer buffer)
	{
		uid = IBT.readLong(buffer);
		index = IBT.readInt(buffer);
		baseCfgId = IBT.readInt(buffer);
		num = IBT.readInt(buffer);
		readHandler(buffer);
		build();
	}
	
	abstract protected void readHandler(IoBuffer buffer);
	
	/** 拷贝 */
	public void copy(GoodsInfo source)
	{
		this.uid = source.uid;
		this.index = source.index;
		this.baseCfgId = source.baseCfgId;
		this.num = source.num;
		copyHandler(source);
		build();
	}
	
	abstract protected void copyHandler(GoodsInfo source);
	
	/** 数据库存储编码 */
	public JSONObject encode()
	{
		JSONObject jso = new JSONObject();
		JsonUtils.putLong(jso, "uid", uid);
		JsonUtils.putInt(jso, "index", index);
		JsonUtils.putInt(jso, "baseCfgId", baseCfgId);
		JsonUtils.putInt(jso, "num", num);
		
		encodeHandler(jso);
		return jso;
	}
	
	abstract protected void encodeHandler(JSONObject jso);
	
	/** 数据库存储解码 */
	public void decode(JSONObject jso)
	{
		uid = JsonUtils.getLong(jso, "uid");
		index = JsonUtils.getInt(jso, "index");
		baseCfgId = JsonUtils.getInt(jso, "baseCfgId");
		num = JsonUtils.getInt(jso, "num");
		
		decodeHandler(jso);
	}
	
	abstract protected void decodeHandler(JSONObject jso);
	
}
