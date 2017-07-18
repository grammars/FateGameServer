package common.struct.buff;

import framework.net.IBT;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONObject;

import utils.JsonUtils;

public class StBuff
{
	/** buff配置id */
	public int cfgId;
	/** 唯一id */
	public int id;
	/** 来源对象tid */
	public int sourceTid;
	/** 目标对象tid */
	public int targetTid;
	
	/** 当前运行次数 */
	public int curRunTimes = 0;
	/** 下次待命时间 */
	public long readyTime = 0;
	/** 销毁时间 */
	public long destroyTime = 0;
	
	/** {累计改变}生命值 */
	public int deltHp = 0;
	/** {累计改变}魔法值 */
	public int deltMp = 0;
	/** {累计改变}移动速度 */
	public int deltMoveSpeed = 0;
	/** {累计改变}攻击力 */
	public int deltAttack = 0;
	/** {累计改变}攻击速度 */
	public float deltAttackSpeed = 0.0f;
	/** {累计改变}护甲值 */
	public int deltArmor = 0;
	/** {累计改变}护甲穿透 */
	public int deltArmorPene = 0;
	/** {累计改变}法术强度 */
	public int deltSpellPower = 0;
	/** {累计改变}法术免疫 */
	public int deltSpellImmun = 0;
	/** {累计改变}法术穿透 */
	public int deltSpellPene = 0;
	
	/** 写入IoBuffer */
	public void write(IoBuffer buffer)
	{
		IBT.writeInt(buffer, this.cfgId);
		IBT.writeInt(buffer, this.id);
		IBT.writeInt(buffer, this.sourceTid);
		IBT.writeInt(buffer, this.targetTid);
		IBT.writeLong(buffer, this.destroyTime);
		IBT.writeLong(buffer, this.readyTime);
		IBT.writeInt(buffer, this.curRunTimes);
		
		IBT.writeInt(buffer, deltHp);
		IBT.writeInt(buffer, deltMp);
		IBT.writeInt(buffer, deltMoveSpeed);
		IBT.writeInt(buffer, deltAttack);
		IBT.writeFloat(buffer, deltAttackSpeed);
		IBT.writeInt(buffer, deltArmor);
		IBT.writeInt(buffer, deltArmorPene);
		IBT.writeInt(buffer, deltSpellPower);
		IBT.writeInt(buffer, deltSpellImmun);
		IBT.writeInt(buffer, deltSpellPene);
	}
	
	/** 从IoBuffer读出 */
	public StBuff read(IoBuffer buffer)
	{
		this.cfgId = IBT.readInt(buffer);
		this.id = IBT.readInt(buffer);
		this.sourceTid = IBT.readInt(buffer);
		this.targetTid = IBT.readInt(buffer);
		this.destroyTime = IBT.readLong(buffer);
		this.readyTime = IBT.readLong(buffer);
		this.curRunTimes = IBT.readInt(buffer);
		
		this.deltHp = IBT.readInt(buffer);
		this.deltMp = IBT.readInt(buffer);
		this.deltMoveSpeed = IBT.readInt(buffer);
		this.deltAttack = IBT.readInt(buffer);
		this.deltAttackSpeed = IBT.readFloat(buffer);
		this.deltArmor = IBT.readInt(buffer);
		this.deltArmorPene = IBT.readInt(buffer);
		this.deltSpellPower = IBT.readInt(buffer);
		this.deltSpellImmun = IBT.readInt(buffer);
		this.deltSpellPene = IBT.readInt(buffer);
		
		return this;
	}
	
	public JSONObject encode()
	{
		JSONObject jso = new JSONObject();
		JsonUtils.putInt(jso, "cfgId", cfgId);
		JsonUtils.putInt(jso, "id", id);
		JsonUtils.putInt(jso, "sourceTid", sourceTid);
		JsonUtils.putInt(jso, "targetTid", targetTid);
		JsonUtils.putLong(jso, "destroyTime", destroyTime);
		JsonUtils.putLong(jso, "readyTime", readyTime);
		JsonUtils.putInt(jso, "curRunTimes", curRunTimes);
		
		JsonUtils.putInt(jso, "deltHp", deltHp);
		JsonUtils.putInt(jso, "deltMp", deltMp);
		JsonUtils.putInt(jso, "deltMoveSpeed", deltMoveSpeed);
		JsonUtils.putInt(jso, "deltAttack", deltAttack);
		JsonUtils.putFloat(jso, "deltAttackSpeed", deltAttackSpeed);
		JsonUtils.putInt(jso, "deltArmor", deltArmor);
		JsonUtils.putInt(jso, "deltArmorPene", deltArmorPene);
		JsonUtils.putInt(jso, "deltSpellPower", deltSpellPower);
		JsonUtils.putInt(jso, "deltSpellImmun", deltSpellImmun);
		JsonUtils.putInt(jso, "deltSpellPene", deltSpellPene);
		return jso;
	}
	
	public void decode(JSONObject jso)
	{
		cfgId = JsonUtils.getInt(jso, "cfgId");
		id = JsonUtils.getInt(jso, "id");
		sourceTid = JsonUtils.getInt(jso, "sourceTid");
		targetTid = JsonUtils.getInt(jso, "targetTid");
		destroyTime = JsonUtils.getLong(jso, "destroyTime");
		readyTime = JsonUtils.getLong(jso, "readyTime");
		curRunTimes = JsonUtils.getInt(jso, "curRunTimes");
		
		deltHp = JsonUtils.getInt(jso, "deltHp");
		deltMp = JsonUtils.getInt(jso, "deltMp");
		deltMoveSpeed = JsonUtils.getInt(jso, "deltMoveSpeed");
		deltAttack = JsonUtils.getInt(jso, "deltAttack");
		deltAttackSpeed = JsonUtils.getFloat(jso, "deltAttackSpeed");
		deltArmor = JsonUtils.getInt(jso, "deltArmor");
		deltArmorPene = JsonUtils.getInt(jso, "deltArmorPene");
		deltSpellPower = JsonUtils.getInt(jso, "deltSpellPower");
		deltSpellImmun = JsonUtils.getInt(jso, "deltSpellImmun");
		deltSpellPene = JsonUtils.getInt(jso, "deltSpellPene");
	}
	
}
