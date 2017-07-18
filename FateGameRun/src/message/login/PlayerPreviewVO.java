package message.login;

import org.apache.mina.core.buffer.IoBuffer;

import common.beans.PlayerBean;
import framework.net.IBT;

public class PlayerPreviewVO
{
	/** 唯一id */
	public long uid;
	/** 地图id */
	public int mapId;
	/** 地图x */
	public int mapX;
	/** 地图y */
	public int mapY;
	/** 外观 */
	public String look;
	
	/** 是否存活 */
	public boolean alive = true;
	/** 名字 */
	public String name;
	/** 性别 */
	public byte sex;
	/** 职业 */
	public byte voc;
	/** 等级 */
	public int level;
	
	/** 所属账号 */
	public String account;
	/** 上次登录时间 */
	public long lastLoginTime;
	/** 创建时间 */
	public long createTime;
	
	/** 存在于数据库中 */
	public boolean exist()
	{
		if(uid != 0) { return true; }
		return false;
	}
	
	/** 写入IoBuffer */
	public void write(IoBuffer buffer)
	{
		IBT.writeLong(buffer, uid);
		IBT.writeInt(buffer, mapId);
		IBT.writeInt(buffer, mapX);
		IBT.writeInt(buffer, mapY);
		IBT.writeString(buffer, look);
		IBT.writeBoolean(buffer, alive);
		IBT.writeString(buffer, name);
		IBT.writeByte(buffer, sex);
		IBT.writeByte(buffer, voc);
		IBT.writeInt(buffer, level);
		IBT.writeString(buffer, account);
		IBT.writeLong(buffer, lastLoginTime);
		IBT.writeLong(buffer, createTime);
	}
	
	/** 从IoBuffer读出 */
	public void read(IoBuffer buffer)
	{
		uid = IBT.readLong(buffer);
		mapId = IBT.readInt(buffer);
		mapX = IBT.readInt(buffer);
		mapY = IBT.readInt(buffer);
		look =  IBT.readString(buffer);
		alive = IBT.readBoolean(buffer);
		name = IBT.readString(buffer);
		sex = IBT.readByte(buffer);
		voc = IBT.readByte(buffer);
		level = IBT.readInt(buffer);
		account = IBT.readString(buffer);
		lastLoginTime = IBT.readLong(buffer);
		createTime = IBT.readLong(buffer); 
	}
	
	/** 拷贝PlayerPO */
	public void copy(PlayerBean ppo)
	{
		uid = ppo.getUid();
		mapId = ppo.getMapId();
		mapX = ppo.getMapX();
		mapY = ppo.getMapY();
		look =  ppo.getLook();
		alive = ppo.getAlive();
		name = ppo.getName();
		sex = ppo.getSex();
		voc = ppo.getVoc();
		level = ppo.getLevel();
		account = ppo.getAccount();
		lastLoginTime = ppo.getLastLoginTime().getTime();
		createTime = ppo.getCreateTime().getTime(); 
	}
}
