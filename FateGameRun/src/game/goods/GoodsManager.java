package game.goods;

import common.component.goods.GoodsInfo;
import common.component.goods.GoodsOperEnum;
import common.component.goods.info.EquipGI;
import common.define.EquipType;
import game.core.sprite.Player;

public class GoodsManager
{
	/** 移动物品：从背包到背包 */
	public static void moveBagItemToBag(Player player, long srcUid, int tarInd)
	{
		player.bag.moveItem(srcUid, tarInd);
	}
	
	/** 移动物品：从背包到仓库 */
	public static void moveBagItemToWarehouse(Player player, long srcUid, int tarInd)
	{
		GoodsInfo srcItem = player.bag.getItemByUid(srcUid);
		GoodsOperEnum addResult = player.warehouse.addItemAt(srcItem, tarInd);
		if(addResult == GoodsOperEnum.SUCC)
		{
			player.bag.removeItem(srcUid);
		}
	}
	
	/** 移动物品：从仓库到仓库 */
	public static void moveWarehouseItemToWarehouse(Player player, long srcUid, int tarInd)
	{
		player.warehouse.moveItem(srcUid, tarInd);
	}
	
	/** 移动物品：从仓库到背包 */
	public static void moveWarehouseItemToBag(Player player, long srcUid, int tarInd)
	{
		GoodsInfo srcItem = player.warehouse.getItemByUid(srcUid);
		GoodsOperEnum addResult = player.bag.addItemAt(srcItem, tarInd);
		if(addResult == GoodsOperEnum.SUCC)
		{
			player.warehouse.removeItem(srcUid);
		}
	}
	
	/** 丢弃背包物品 */
	public static void dropItemFromBag(Player player, long itemUid)
	{
		player.bag.removeItem(itemUid);
		//player.alert("丢弃背包物品请求已受理");
	}
	
	/** 拆分背包物品 */
	public static void splitItemInBag(Player player, long itemUid, int spNum)
	{
		player.bag.splitItem(itemUid, spNum);
		//player.alert("拆分背包物品请求已受理");
	}
	
	/** 穿上装备 */
	public static GoodsOperEnum putOnEquip(Player player, long srcUid)
	{
		GoodsInfo item = player.bag.getItemByUid(srcUid);
		if(item == null)
		{
			player.bag.operExceptionHandler(GoodsOperEnum.ITEM_NULL);
		}
		if(item.isEquip() == false)
		{
			player.bag.operExceptionHandler(GoodsOperEnum.NOT_EQUIP);
		}
		EquipGI equipment = item.toEquip();
		if(!equipment.available())
		{
			player.alert("装备数据不合法");
			return GoodsOperEnum.FAIL;
		}
		if(equipment.baseCfg.reqLevMin > 0 && player.level < equipment.baseCfg.reqLevMin)
		{
			player.alert("需要"+equipment.baseCfg.reqLevMin+"级以上才能穿戴");
			return GoodsOperEnum.PLAYER_LEV_TOO_MIN;
		}
		if(equipment.baseCfg.reqLevMax > 0 && player.level > equipment.baseCfg.reqLevMax)
		{
			player.alert("您的等级已经超过穿戴等级上限");
			return GoodsOperEnum.PLAYER_LEV_TOO_MAX;
		}
		if(equipment.baseCfg.reqVoc > 0 && player.voc != equipment.baseCfg.reqVoc)
		{
			player.alert("您的职业与装备需求不符合");
			return GoodsOperEnum.PLAYER_VOC_DENY;
		}
		GoodsInfo oldItem = player.equip.getItemByIndex(equipment.equipCfg.type);
		int indexInBag = equipment.index;
		GoodsOperEnum result;
		if(oldItem == null)
		{
			result = player.equip.addItemAt(equipment, equipment.equipCfg.type);
			if(result == GoodsOperEnum.SUCC)
			{
				player.bag.removeItem(equipment.uid);
			}
		}
		else
		{
			player.equip.removeItem(oldItem.uid);
			player.equip.addItemAt(equipment, equipment.equipCfg.type);
			player.bag.removeItem(equipment.uid);
			player.bag.addItemAt(oldItem, indexInBag);
		}
		player.equip.applyAttris();
		return GoodsOperEnum.SUCC;
	}
	
	/** 脱下装备 */
	public static void takeOffEquip(Player player, long srcUid, int index)
	{
		if(player.bag.isFull())
		{
			player.alert("背包已满，无法脱下装备");
			return;
		}
		GoodsInfo item = player.equip.getItemByUid(srcUid);
		if(item == null)
		{
			player.alert("所请求脱下的装备并不存在");
			return;
		}
		if(index < 0)
		{
			player.equip.removeItem(item.uid);
			player.bag.addItem(item);
		}
		else
		{
			if( null != player.bag.getItemByIndex(index) )
			{
				player.alert("指定位置已有物品，请挑一个空格脱下装备");
				return;
			}
			else
			{
				player.equip.removeItem(item.uid);
				player.bag.addItemAt(item, index);
			}
		}
		player.equip.applyAttris();
	}
}
