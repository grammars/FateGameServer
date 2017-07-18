package game.core.sprite;

import org.apache.mina.core.buffer.IoBuffer;

import common.define.SpriteType;

public class Effect extends Sprite
{
	public Effect()
	{
		super();
		this.type = SpriteType.EFFECT;
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
	
}
