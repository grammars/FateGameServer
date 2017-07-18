package game.core.sprite;

import java.util.Date;

import org.apache.mina.core.buffer.IoBuffer;

import message.common.CommonMsg;
import message.fight.FightMsg;
import framework.net.IBT;
import game.GameClientBundle;
import common.component.cd.CDUnit;
import common.component.goods.bag.BagContainer;
import common.component.goods.equip.EquipContainer;
import common.component.goods.warehouse.WarehouseContainer;
import common.component.practice.PracticeData;
import common.component.task.TaskList;
import common.define.SpriteType;

public class Player extends Creature
{
	private GameClientBundle bundle;
	public void setBundle(GameClientBundle value) { this.bundle = value; }
	public GameClientBundle getBundle() { return bundle; }
	
	/** 所属账号 */
	public String account;
	/** 武器外观 */
	public String weaponLook;
	/** PK模式 */
	public int pkMode;
	/** 经验 */
	public long exp;
	/** 上次登录时间 */
	public Date lastLoginTime;
	/** 创建时间 */
	public Date createTime;
	
	/** 包裹 */
	public BagContainer bag;
	/** 装备 */
	public EquipContainer equip;
	/** 仓库 */
	public WarehouseContainer warehouse;
	/** 修炼 */
	public PracticeData practice;
	/** 任务 */
	public TaskList task;
	/** CD */
	public CDUnit cd;
	
	/** 日志 */
	public String log;
	
	public Player()
	{
		super();
		this.type = SpriteType.PLAYER;
		bag = new BagContainer(this);
		equip = new EquipContainer(this);
		warehouse = new WarehouseContainer(this);
		practice = new PracticeData(this);
		task = new TaskList(this);
		cd = new CDUnit(this);
	}
	
	@Override
	protected void createParts()
	{
		super.createParts();
	}
	
	/** 设置PK模式 */
	public void setPkMode(int value)
	{
		boolean modeAvailable = true;
		if(modeAvailable)
		{
			this.pkMode = value;
			FightMsg.getInstance().sendPkModeInfo_G2C(value, bundle.getUid());
		}
	}
	
	/** 向玩家发送alert消息 */
	public void alert(String text)
	{
		CommonMsg.getInstance().sendAlert_G2C(text, this.getBundle().getUid());
	}
	
	@Override
	public void update()
	{
		super.update();
		cd.update();
		//System.err.println("update" + playerVO().getName());
	}
	
	/** 下线保存数据库之前的处理 */
	public void offline()
	{
		this.buffs.removeUnneedStore();
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
		IBT.writeString(buffer, weaponLook);
		IBT.writeInt(buffer, pkMode);
	}
	
	@Override
	public void write(IoBuffer buffer)
	{
		super.write(buffer);
		IBT.writeString(buffer, account);
		IBT.writeString(buffer, weaponLook);
		IBT.writeInt(buffer, pkMode);
		IBT.writeLong(buffer, exp);
		IBT.writeLong(buffer, lastLoginTime==null?0:lastLoginTime.getTime());
		IBT.writeLong(buffer, createTime==null?0:createTime.getTime());
	}
	
	@Override
	public void read(IoBuffer buffer)
	{
		super.read(buffer);
		account = IBT.readString(buffer);
		weaponLook = IBT.readString(buffer);
		pkMode = IBT.readInt(buffer);
		exp = IBT.readLong(buffer);
		lastLoginTime = new Date(IBT.readLong(buffer));
		createTime = new Date(IBT.readLong(buffer));
	}
	
	/** toPlayerString */
	protected String toPlayerString()
	{
		return " account=" + account + " weaponLook=" + weaponLook + " exp=" + exp + " lastLoginTime=" + lastLoginTime + " createTime=" + createTime;
	}
	
	/** toString */
	public String toString()
	{
		return "[Player] " + toPlayerString();
	}
}
