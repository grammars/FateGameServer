package common.component.goods.equip;

import java.util.List;

import utils.Str;
import game.core.sprite.Player;
import message.goods.GoodsMsg;
import message.scene.SceneMsg;
import common.component.goods.GoodsContainer;
import common.component.goods.GoodsInfo;
import common.component.goods.info.EquipGI;
import common.define.EquipType;
import common.struct.player.StAttriSet;

public class EquipContainer extends GoodsContainer
{	
	public EquipContainer(Player owner)
	{
		super(owner);
		this.type = TYPE_EQUIP;
		capacity = 6;
	}
	
	/** 应用装备属性到玩家身上 */
	public void applyAttris()
	{
		int hp = 0;
		int mp = 0;
		int moveSpeed = 0;
		int attack = 0;
		float attackSpeed = 0.0f;
		int armor = 0;
		int armorPene = 0;
		int spellPower = 0;
		int spellImmun = 0;
		int spellPene = 0;
		
		for(GoodsInfo item : items)
		{
			if(!item.available()) { continue; }
			EquipGI equipment = item.toEquip();
			if(!equipment.available()) { continue; }
			hp += equipment.hp();
			mp += equipment.mp();
			moveSpeed += equipment.moveSpeed();
			attack += equipment.attack();
			attackSpeed += equipment.attackSpeed();
			armor += equipment.armor();
			armorPene += equipment.armorPene();
			spellPower += equipment.spellPower();
			spellImmun += equipment.spellImmun();
			spellPene += equipment.spellPene();
		}
		
		int d_hp = hp - owner.attris.equip.hp;
		int d_mp = mp - owner.attris.equip.mp;
		int d_moveSpeed = moveSpeed - owner.attris.equip.moveSpeed;
		int d_attack = attack - owner.attris.equip.attack;
		float d_attackSpeed = attackSpeed - owner.attris.equip.attackSpeed;
		int d_armor = armor - owner.attris.equip.armor;
		int d_armorPene = armorPene - owner.attris.equip.armorPene;
		int d_spellPower = spellPower - owner.attris.equip.spellPower;
		int d_spellImmun = spellImmun - owner.attris.equip.spellImmun;
		int d_spellPene = spellPene - owner.attris.equip.spellPene;
		
		owner.attris.changeHp(d_hp, StAttriSet.SRC_EQUIP);
		owner.attris.changeMp(d_mp, StAttriSet.SRC_EQUIP);
		owner.attris.changeMoveSpeed(d_moveSpeed, StAttriSet.SRC_EQUIP);
		owner.attris.changeAttack(d_attack, StAttriSet.SRC_EQUIP);
		owner.attris.changeAttackSpeed(d_attackSpeed, StAttriSet.SRC_EQUIP);
		owner.attris.changeArmor(d_armor, StAttriSet.SRC_EQUIP);
		owner.attris.changeArmorPene(d_armorPene, StAttriSet.SRC_EQUIP);
		owner.attris.changeSpellPower(d_spellPower, StAttriSet.SRC_EQUIP);
		owner.attris.changeSpellImmun(d_spellImmun, StAttriSet.SRC_EQUIP);
		owner.attris.changeSpellPene(d_spellPene, StAttriSet.SRC_EQUIP);
	}
	
	/** 更新武器外观 */
	private void updateWeaponLook()
	{
		String wl = null;
		for(GoodsInfo item : items)
		{
			if(item.toEquip().equipCfg.type == EquipType.WEAPON)
			{
				wl = item.toEquip().equipCfg.look;
			}
		}
		if( !Str.same(owner.weaponLook, wl) )
		{
			owner.weaponLook = wl;
			List<Player> players = owner.getNear(true);
			for(Player p : players)
			{
				SceneMsg.getInstance().sendWeaponLookChange_G2C(owner.tid, wl, p.getBundle().getUid());
			}
		}
	}
	
	@Override
	public void clearItemsHandler()
	{
		GoodsMsg.getInstance().sendClearItemsInEquip_G2C(owner.getBundle().getUid());
	}
	
	@Override
	public void initItemsHandler()
	{
		GoodsMsg.getInstance().sendInitItemsToEquip_G2C(this, owner.getBundle().getUid());
	}
	
	@Override
	protected void addItemHandler(GoodsInfo item)
	{
		GoodsMsg.getInstance().sendAddItemToEquip_G2C(item, owner.getBundle().getUid());
		updateWeaponLook();
	}
	
	@Override
	protected void removeItemHandler(GoodsInfo item)
	{
		GoodsMsg.getInstance().sendRemoveItemFromEquip_G2C(item.uid, owner.getBundle().getUid());
		updateWeaponLook();
	}
	
	@Override
	protected void updateItemHandler(GoodsInfo item)
	{
		GoodsMsg.getInstance().sendUpdateItemInEquip_G2C(item, owner.getBundle().getUid());
	}
	
}
