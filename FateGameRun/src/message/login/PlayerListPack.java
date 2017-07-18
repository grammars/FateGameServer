package message.login;

import org.apache.mina.core.buffer.IoBuffer;

import common.beans.PlayerBean;
import framework.net.IBT;

public class PlayerListPack
{
	public PlayerPreviewVO p0;
	public PlayerPreviewVO p1;
	public PlayerPreviewVO p2;
	
	public PlayerListPack()
	{
	}
	
	public void makeP0(PlayerBean ppo)
	{
		p0 = new PlayerPreviewVO();
		p0.copy(ppo);
	}
	
	public void makeP1(PlayerBean ppo)
	{
		p1 = new PlayerPreviewVO();
		p1.copy(ppo);
	}
	
	public void makeP2(PlayerBean ppo)
	{
		p2 = new PlayerPreviewVO();
		p2.copy(ppo);
	}
	
	public void write(IoBuffer buffer)
	{
		byte size = 0;
		if(p0 != null && p0.exist()) { size++; }
		if(p1 != null && p1.exist()) { size++; }
		if(p2 != null && p2.exist()) { size++; }
		IBT.writeByte(buffer, size);
		
		if(p0 != null && p0.exist())
		{
			p0.write(buffer);
		}
		if(p1 != null && p1.exist())
		{
			p1.write(buffer);
		}
		if(p2 != null && p2.exist())
		{
			p2.write(buffer);
		}
	}
	
	public void read(IoBuffer buffer)
	{
		byte size = buffer.get();
		if(size >= 1)
		{
			p0 = new PlayerPreviewVO();
			p0.read(buffer);
		}
		if(size >= 2)
		{
			p1 = new PlayerPreviewVO();
			p1.read(buffer);
		}
		if(size >= 3)
		{
			p2 = new PlayerPreviewVO();
			p2.read(buffer);
		}
	}
	
	public String toString()
	{
		String ret = "[PlayerListPack]";
		if(p0 != null) { ret += " p0=>" + p0.toString(); }
		if(p1 != null) { ret += " p1=>" + p1.toString(); }
		if(p2 != null) { ret += " p2=>" + p2.toString(); }
		return ret;
	}
}
