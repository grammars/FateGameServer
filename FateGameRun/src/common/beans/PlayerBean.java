package common.beans;

import java.util.Date;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import common.struct.buff.StBuffSet;
import common.struct.cd.StCDUint;
import common.struct.goods.StGoodsContainer;
import common.struct.player.StAttriSet;
import common.struct.practice.StPracticeData;
import common.struct.skill.StSkillSet;
import common.struct.task.StTaskItem;
import common.struct.task.StTaskList;
import utils.Utils;
import cfg.SelfLookCfg;
import framework.SetupCfg;
import framework.net.IBT;
import game.core.sprite.Player;
import game.skill.SkillManager;

public class PlayerBean
{
	//--------sprite-----------
	/** 唯一id */
	private long uid;
	public long getUid() { return this.uid; }
	public void setUid(long value) { this.uid = value; }
	
	/** 地图id */
	private int mapId;
	public int getMapId() { return this.mapId; }
	public void setMapId(int value) { this.mapId = value; }
	
	/** 地图x */
	private int mapX;
	public int getMapX() { return this.mapX; }
	public void setMapX(int value) { this.mapX = value; }
	
	/** 地图y */
	private int mapY;
	public int getMapY() { return this.mapY; }
	public void setMapY(int value) { this.mapY = value; }
	
	/** 朝向 */
	private byte direction;
	public byte getDirection() { return this.direction; }
	public void setDirection(byte value) { this.direction = value; }
	
	/** 外观 */
	private String look;
	public String getLook() { return this.look; }
	public void setLook(String value) { this.look = value; }
	
	//--------creature-----------
	/** 是否存活 */
	private boolean alive;
	public boolean getAlive() { return this.alive; }
	public void setAlive(boolean value) { this.alive = value; }
	
	/** 名字 */
	private String name;
	public String getName() { return this.name; }
	public void setName(String value) { this.name = value; }
	
	/** 性别 */
	private byte sex;
	public byte getSex() { return this.sex; }
	public void setSex(byte value) { this.sex = value; }
	
	/** 职业 */
	protected byte voc;
	public byte getVoc() { return this.voc; }
	public void setVoc(byte value) { this.voc = value; }
	
	/** 等级 */
	private int level;
	public int getLevel() { return this.level; }
	public void setLevel(int value) { this.level = value; }
	
	private StAttriSet attri = new StAttriSet();
	/** 基础属性数据 */
	public String getAttriData()
	{
		return attri.encode().toString();
	}
	public void setAttriData(String value)
	{
		JSONObject jso = new JSONObject(value);
		attri.decode(jso);
	}
	
	private StSkillSet skill = new StSkillSet();
	/** 技能信息数据 */
	public String getSkillData()
	{
		return skill.encode().toString();
	}
	public void setSkillData(String value)
	{
		JSONArray jarr = new JSONArray(value);
		skill.decode(jarr);
	}
	
	private StBuffSet buff = new StBuffSet();
	/** Buff组信息数据 */
	public String getBuffData()
	{
		return buff.encode().toString();
	}
	public void setBuffData(String value)
	{
		JSONArray jarr = new JSONArray(value);
		buff.decode(jarr);
	}
	
	//--------player-----------
	/** 所属账号 */
	private String account;
	public String getAccount() { return this.account; }
	public void setAccount(String value) { this.account = value; }
	
	/** 武器外观 */
	private String weaponLook;
	public String getWeaponLook() { return this.weaponLook; }
	public void setWeaponLook(String value) { this.weaponLook = value; }
	
	/** PK模式 */
	private int pkMode;
	public int getPkMode() { return this.pkMode; }
	public void setPkMode(int value) { this.pkMode = value; }
	
	/** 经验 */
	private long exp;
	public long getExp() { return this.exp; }
	public void setExp(long value) { this.exp = value; }
	
	/** 上次登录时间 */
	private Date lastLoginTime;
	public Date getLastLoginTime() { return this.lastLoginTime; }
	public void setLastLoginTime(Date value) { this.lastLoginTime = value; }
	
	/** 创建时间 */
	private Date createTime;
	public Date getCreateTime() { return this.createTime; }
	public void setCreateTime(Date value) { this.createTime = value; }
	
	/** 玩家背包 */
	private StGoodsContainer bag = new StGoodsContainer();
	public String getBagData()
	{
		return bag.encode().toString();
	}
	public void setBagData(String value)
	{
		JSONArray jarr = new JSONArray(value);
		bag.decode(jarr);
	}
	
	/** 玩家仓库 */
	private StGoodsContainer warehouse = new StGoodsContainer();
	public String getWarehouseData()
	{
		return warehouse.encode().toString();
	}
	public void setWarehouseData(String value)
	{
		JSONArray jarr = new JSONArray(value);
		warehouse.decode(jarr);
	}
	
	/** 玩家装备 */
	private StGoodsContainer equip = new StGoodsContainer();
	public String getEquipData()
	{
		return equip.encode().toString();
	}
	public void setEquipData(String value)
	{
		JSONArray jarr = new JSONArray(value);
		equip.decode(jarr);
	}
	
	/** 修炼数据 */
	private StPracticeData practice = new StPracticeData();
	public String getPracticeData()
	{
		return practice.encode().toString();
	}
	public void setPracticeData(String value)
	{
		JSONObject jso = new JSONObject(value);
		practice.decode(jso);
	}
	
	/** 玩家任务 */
	private StTaskList task = new StTaskList();
	public String getTaskData()
	{
		return task.encode().toString();
	}
	public void setTaskData(String value)
	{
		JSONObject jarr = new JSONObject(value);
		task.decode(jarr);
	}
	
	/** 玩家CD */
	private StCDUint cd = new StCDUint();
	public String getCdData()
	{
		return cd.encode().toString();
	}
	public void setCdData(String value)
	{
		JSONObject jso = new JSONObject(value);
		cd.decode(jso);
	}
	
	/** 日志 */
	private String log;
	public String getLog() { return this.log; }
	public void setLog(String value) { this.log = value; }
	
	/** 存在于数据库中 */
	public boolean exist()
	{
		if(getUid() != 0) { return true; }
		return false;
	}
	
	/** 新建一个角色 */
	public void create(String account, String name, byte sex)
	{
		this.uid = Utils.createUidLong();
		this.alive = true;
		this.account = account;
		this.name = name;
		this.sex = sex;
		this.look = SelfLookCfg.getLook(voc, sex, 0);
		this.mapId = SetupCfg.INIT_MAP_ID;
		this.mapX = SetupCfg.INIT_MAP_X;
		this.mapY = SetupCfg.INIT_MAP_Y;
		this.lastLoginTime = new Date();
		this.createTime = new Date();
		
		this.skill = SkillManager.createSkillForVoc(voc).exportData();
		StTaskItem task = new StTaskItem();
		task.state = StTaskItem.ST_ING;
		task.taskId = 1;
		this.task.items.add(task);
	}
	
	/** 写入完整的角色信息 */
	public void writeFull(IoBuffer buffer)
	{
		//sprite::writeBase
		IBT.writeLong(buffer, uid);
		IBT.writeInt(buffer, mapId);
		IBT.writeInt(buffer, mapX);
		IBT.writeInt(buffer, mapY);
		IBT.writeByte(buffer, direction);
		IBT.writeString(buffer, look);
		//creature::writeBase
		IBT.writeBoolean(buffer, alive);
		IBT.writeString(buffer, name);
		IBT.writeByte(buffer, sex);
		IBT.writeByte(buffer, voc);
		IBT.writeInt(buffer, level);
		//
		attri.write(buffer);
		skill.write(buffer);
		buff.write(buffer);
		//player::writeBase
		IBT.writeString(buffer, account);
		IBT.writeString(buffer, weaponLook);
		IBT.writeInt(buffer, pkMode);
		IBT.writeLong(buffer, exp);
		IBT.writeLong(buffer, lastLoginTime==null?0:lastLoginTime.getTime());
		IBT.writeLong(buffer, createTime==null?0:createTime.getTime());
		//
		bag.write(buffer);
		equip.write(buffer);
		warehouse.write(buffer);
		practice.write(buffer);
		task.write(buffer);
		cd.write(buffer);
		//
		IBT.writeString(buffer, log);
	}
	
	/** 读出完整的接收的角色信息 */
	public void readFull(IoBuffer buffer)
	{
		//sprite::readBase
		uid = IBT.readLong(buffer);
		mapId = IBT.readInt(buffer);
		mapX = IBT.readInt(buffer);
		mapY = IBT.readInt(buffer);
		direction = IBT.readByte(buffer);
		look =  IBT.readString(buffer);
		//creature::readBase
		alive = IBT.readBoolean(buffer);
		name = IBT.readString(buffer);
		sex = IBT.readByte(buffer);
		voc = IBT.readByte(buffer);
		level = IBT.readInt(buffer);
		//
		attri.read(buffer);
		skill.read(buffer);
		buff.read(buffer);
		//player::readBase
		account = IBT.readString(buffer);
		weaponLook = IBT.readString(buffer);
		pkMode = IBT.readInt(buffer);
		exp = IBT.readLong(buffer);
		lastLoginTime = new Date(IBT.readLong(buffer));
		createTime = new Date(IBT.readLong(buffer)); 
		//
		bag.read(buffer);
		equip.read(buffer);
		warehouse.read(buffer);
		practice.read(buffer);
		task.read(buffer);
		cd.read(buffer);
		//
		log = IBT.readString(buffer);
	}
	
	/** 从Player中汲取数据 */
	public void learn(Player player)
	{
		this.uid = player.uid;
		this.alive = player.alive;
		this.mapId = player.mapId;
		this.mapX = player.x;
		this.mapY = player.y;
		this.direction = player.direction;
		this.look = player.look;
		
		this.name = player.name;
		this.sex = player.sex;
		this.voc = player.voc;
		this.level = player.level;
		this.attri = player.attris.exportData();
		this.skill = player.skills.exportData();
		this.buff = player.buffs.exportData();
		
		this.account = player.account;
		this.weaponLook = player.weaponLook;
		this.pkMode = player.pkMode;
		this.exp = player.exp;
		this.lastLoginTime = player.lastLoginTime;
		this.createTime = player.createTime;
		
		this.bag = player.bag.exportData();;
		this.equip = player.equip.exportData();
		this.warehouse = player.warehouse.exportData();
		this.practice = player.practice.exportData();
		this.task = player.task.exportData();
		this.cd = player.cd.exportData();
		
		this.log = player.log;
	}
	
	/** 应用到Player */
	public void apply(Player player)
	{
		player.uid = this.uid;
		player.alive = this.alive;
		player.mapId = this.mapId;
		player.x = this.mapX;
		player.y = this.mapY;
		player.direction = this.direction;
		player.look = this.look;
		
		player.name = this.name;
		player.sex = this.sex;
		player.voc = this.voc;
		player.level = this.level;
		player.attris.importData(this.attri);
		player.skills.importData(this.skill);
		player.buffs.importData(this.buff);
		
		player.account = this.account;
		player.weaponLook = this.weaponLook;
		player.pkMode = this.pkMode;
		player.exp = this.exp;
		player.lastLoginTime = this.lastLoginTime;
		player.createTime = this.createTime;
		
		player.bag.importData(this.bag);
		player.equip.importData(this.equip);
		player.warehouse.importData(this.warehouse);
		player.practice.importData(this.practice);
		player.task.importData(this.task);
		player.cd.importData(this.cd);
		
		player.log = this.log;
	}
	
}
