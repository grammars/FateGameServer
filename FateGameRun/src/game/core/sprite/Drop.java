package game.core.sprite;

import org.apache.mina.core.buffer.IoBuffer;

import common.define.SpriteType;

public class Drop extends Sprite
{
	public Drop()
	{
		super();
		this.type = SpriteType.DROP;
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
	}
	
	@Override
	public void write(IoBuffer buffer)
	{
		super.write(buffer);
	}
	
	@Override
	public void read(IoBuffer buffer)
	{
		super.read(buffer);
	}
	
}
