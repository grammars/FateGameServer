package common.component.goods.info;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONObject;

import common.component.goods.GoodsInfo;

public class TaskGI extends GoodsInfo
{
	@Override
	public boolean available()
	{
		if(super.available() == false) { return false; }
		return true;
	}
	
	@Override
	protected void build()
	{
		super.build();
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
