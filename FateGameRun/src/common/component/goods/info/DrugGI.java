package common.component.goods.info;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONObject;

import cfg.GoodsDrugCfg;
import common.component.goods.GoodsInfo;

public class DrugGI extends GoodsInfo
{
	@Override
	public boolean available()
	{
		if(super.available() == false) { return false; }
		if(drugCfg == null) { return false; }
		return true;
	}
	
	public GoodsDrugCfg drugCfg;
	
	@Override
	protected void build()
	{
		super.build();
		drugCfg = GoodsDrugCfg.get(baseCfgId);
	}
	
	@Override
	protected void writeHandler(IoBuffer buffer)
	{
		
	}
	
	@Override
	protected void readHandler(IoBuffer buffer)
	{
		
	}
	
	@Override
	protected void copyHandler(GoodsInfo source)
	{
		
	}
	
	@Override
	protected void encodeHandler(JSONObject jso)
	{
		
	}
	
	@Override
	protected void decodeHandler(JSONObject jso)
	{
		
	}
}
