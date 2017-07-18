package game.core.sprite;

import org.apache.mina.core.buffer.IoBuffer;

import cfg.MapDoorCfg;
import common.define.SpriteType;
import framework.net.IBT;

public class Door extends Sprite
{	
	protected MapDoorCfg config;
	public void setConfig(MapDoorCfg value)
	{
		this.config = value;
		this.x = config.fromMapX;
		this.y = config.fromMapY;
	}
	public MapDoorCfg getConfig() { return this.config; }
	
	public Door()
	{
		super();
		this.type = SpriteType.DOOR;
	}
	
	@Override
	protected void createParts()
	{
		super.createParts();
	}
	
	@Override
	public void update()
	{
		super.update();
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
	}
	
	@Override
	public void writeAvatar(IoBuffer buffer)
	{
		super.writeAvatar(buffer);
		IBT.writeInt(buffer, config.toMapId);
		IBT.writeInt(buffer, config.toMapX);
		IBT.writeInt(buffer, config.toMapY);
	}
	
}
