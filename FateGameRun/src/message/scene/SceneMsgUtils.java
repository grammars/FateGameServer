package message.scene;

import framework.net.IBT;
import game.core.sprite.Sprite;

import org.apache.mina.core.buffer.IoBuffer;

public class SceneMsgUtils
{
	/** 向IoBuffer写入Sprite */
	public static void writeSprite(IoBuffer buffer, Sprite sp)
	{
		IBT.writeByte(buffer, sp.type);
		sp.writeAvatar(buffer);
	}
	
}
