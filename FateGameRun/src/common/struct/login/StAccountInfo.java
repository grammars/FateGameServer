package common.struct.login;

import org.apache.mina.core.buffer.IoBuffer;

import framework.net.IBT;

public class StAccountInfo
{
	//--------------------不在DB中的字段----------------------
	/** 验证账号时候的时间戳 */
	public String timestamp;
	/** 验证账号时候的sign */
	public String sign;
	
	//---------------------DB中存在的字段---------------------
	
	/** 账号id */
	private String uid;
	public String getUid() { return uid; }
	public void setUid(String value) { this.uid = value; }

	/** 角色0 id */
	private long playeruid0;
	public long getPlayeruid0() { return playeruid0; }
	public void setPlayeruid0(long value) { this.playeruid0 = value; }

	/** 角色1 id */
	private long playeruid1;
	public long getPlayeruid1() { return playeruid1; }
	public void setPlayeruid1(long value) { this.playeruid1 = value; }
	
	/** 角色2 id */
	private long playeruid2;
	public long getPlayeruid2() { return playeruid2; }
	public void setPlayeruid2(long value) { this.playeruid2 = value; }
	
	/** 在数据库中是否存在 */
	public boolean exist()
	{
		return uid != null;
	}
	
	/** 角色0是否存在 */
	public boolean existPlayer0() { return playeruid0 != 0; }
	/** 角色1是否存在 */
	public boolean existPlayer1() { return playeruid1 != 0; }
	/** 角色2是否存在 */
	public boolean existPlayer2() { return playeruid2 != 0; }
	
	/** 是否可以再创建角色，是否有空余角色位 */
	public boolean canAddPlayer()
	{
		return (!existPlayer0()) ||  (!existPlayer1()) || (!existPlayer2());
	}
	/** 添加新的角色 */
	public boolean addPlayer(long uid)
	{
		if(!existPlayer0()) { setPlayeruid0(uid); return true; }
		else if(!existPlayer1()) { setPlayeruid1(uid); return true; }
		else if(!existPlayer2()) { setPlayeruid2(uid); return true; }
		else { return false; }
	}
	
	/** 写入到消息 */
	public void writeMsg(IoBuffer buffer)
	{
		IBT.writeString(buffer, uid);
		IBT.writeLong(buffer, playeruid0);
		IBT.writeLong(buffer, playeruid1);
		IBT.writeLong(buffer, playeruid2);
	}
	
	/** 从消息读出 */
	public void readMsg(IoBuffer buffer)
	{
		uid = IBT.readString(buffer);
		playeruid0 = IBT.readLong(buffer);
		playeruid1 = IBT.readLong(buffer);
		playeruid2 = IBT.readLong(buffer);
	}
	
	@Override
	public String toString()
	{
		return "uid=" + uid + " playeruid0=" + playeruid0 + " playeruid1=" + playeruid1
				+ " playeruid2=" + playeruid2;
	}
	
}
