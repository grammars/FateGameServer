package common.component.goods.info;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONObject;

import cfg.GoodsEquipCfg;
import common.component.goods.GoodsInfo;

public class EquipGI extends GoodsInfo
{
	@Override
	public boolean available()
	{
		if(super.available() == false) { return false; }
		if(equipCfg == null) { return false; }
		return true;
	}
	
	public GoodsEquipCfg equipCfg;
	
	/** {该装备提供的}生命值 */
	public int hp() { return ( equipCfg.baseHp ); }
	/** {该装备提供的}魔法值 */
	public int mp() { return ( equipCfg.baseMp ); }
	
	/** {该装备提供的}移动速度 */
	public int moveSpeed() { return ( equipCfg.baseMoveSpeed ); }
	
	/** {该装备提供的}攻击力 */
	public int attack() { return ( equipCfg.baseAttack ); }
	/** {该装备提供的}攻击速度 */
	public float attackSpeed() { return ( equipCfg.baseAttackSpeed ); }
	/** {该装备提供的}护甲值 */
	public int armor() { return ( equipCfg.baseArmor ); }
	/** {该装备提供的}护甲穿透 */
	public int armorPene() { return ( equipCfg.baseArmorPene ); }
	
	/** {该装备提供的}法术强度 */
	public int spellPower() { return ( equipCfg.baseSpellPower ); }
	/** {该装备提供的}法术免疫 */
	public int spellImmun() { return ( equipCfg.baseSpellImmun ); }
	/** {该装备提供的}法术穿透 */
	public int spellPene() { return ( equipCfg.baseSpellPene ); }
	
	@Override
	protected void build()
	{
		super.build();
		equipCfg = GoodsEquipCfg.get(baseCfgId);
	}
	
	@Override
	protected void writeHandler(IoBuffer buffer)
	{
		
	}
	
	@Override
	protected void readHandler(IoBuffer buffer)
	{
		
	}
	
	@Override
	protected void copyHandler(GoodsInfo source)
	{
		
	}
	
	@Override
	protected void encodeHandler(JSONObject jso)
	{
		
	}
	
	@Override
	protected void decodeHandler(JSONObject jso)
	{
		
	}
}
