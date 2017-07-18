package game.core.sprite;

import org.apache.mina.core.buffer.IoBuffer;

import common.define.SpriteType;

public class PathSkill extends Sprite
{
	public PathSkill()
	{
		super();
		this.type = SpriteType.PATH_SKILL;
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
