package message.goods;

import org.apache.mina.core.buffer.IoBuffer;

import common.component.goods.GoodsFactory;
import common.component.goods.GoodsInfo;
import common.struct.goods.StGoodsContainer;
import framework.ClientBundleManager;
import framework.MsgBuffer;
import framework.SessHand;
import framework.define.SidType;
import framework.net.IBT;
import game.GameClientBundle;
import game.core.sprite.Player;
import game.goods.GoodsManager;
import message.IMessage;
import message.MessageId;

public class GoodsMsg implements IMessage
{
	private static GoodsMsg instance;
	public static GoodsMsg getInstance()
	{
		if(instance == null) { instance = new GoodsMsg(); }
		return instance;
	}
	/** [物品]主消息号 */
	public static final int MID = MessageId.GOODS_MID;

	/** game向client通知物品操作异常 */
	private static final int OPER_EXCEPTION_G2C = 1;
	
	/** game向client通知背包已清空 */
	private static final int CLEAR_ITEMS_IN_BAG_G2C = 10;
	/** game向client发送初始化背包物品 */
	private static final int INIT_ITEMS_TO_BAG_G2C = 11;
	/** game向client通知添加物品到背包 */
	private static final int ADD_ITEM_TO_BAG_G2C = 12;
	/** game向client通知移除物品从背包 */
	private static final int REMOVE_ITEM_FROM_BAG_G2C = 13;
	/** game向client通知更新背包物品 */
	private static final int UPDATE_ITEM_IN_BAG_G2C = 14;
	
	
	/** game向client通知装备已清空 */
	private static final int CLEAR_ITEMS_IN_EQUIP_G2C = 20;
	/** game向client发送初始化装备 */
	private static final int INIT_ITEMS_TO_EQUIP_G2C = 21;
	/** game向client通知穿上装备 */
	private static final int ADD_ITEM_TO_EQUIP_G2C = 22;
	/** game向client通知脱下装备 */
	private static final int REMOVE_ITEM_FROM_EQUIP_G2C = 23;
	/** game向client通知更新装备物品 */
	private static final int UPDATE_ITEM_IN_EQUIP_G2C = 24;
	
	/** game向client通知仓库已清空 */
	private static final int CLEAR_ITEMS_IN_WAREHOUSE_G2C = 30;
	/** game向client发送初始化仓库物品 */
	private static final int INIT_ITEMS_TO_WAREHOUSE_G2C = 31;
	/** game向client通知添加物品到仓库 */
	private static final int ADD_ITEM_TO_WAREHOUSE_G2C = 32;
	/** game向client通知移除物品从仓库 */
	private static final int REMOVE_ITEM_FROM_WAREHOUSE_G2C = 33;
	/** game向client通知更新仓库物品 */
	private static final int UPDATE_ITEM_IN_WAREHOUSE_G2C = 34;
	
	/** client请求移动物品：从背包到背包 */
	private static final int MOVE_ITEM_BAG_TO_BAG_REQ_C2G = 41;
	/** client请求移动物品：从背包到仓库 */
	private static final int MOVE_ITEM_BAG_TO_WAREHOUSE_REQ_C2G = 42;
	/** client请求移动物品：从仓库到仓库 */
	private static final int MOVE_ITEM_WAREHOUSE_TO_WAREHOUSE_REQ_C2G = 43;
	/** client请求移动物品：从仓库到背包 */
	private static final int MOVE_ITEM_WAREHOUSE_TO_BAG_REQ_C2G = 44;
	
	/** client请求丢弃背包物品 */
	private static final int DROP_ITEM_FROM_BAG_REQ_C2G = 51;
	/** client请求拆分背包物品 */
	private static final int SPLIT_ITEM_IN_BAG_REQ_C2G = 52;
	
	/** client请求穿上装备 */
	private static final int PUT_ON_EQUIP_REQ_C2G = 61;
	/** client请求脱下装备 */
	private static final int TAKE_OFF_EQUIP_REQ_C2G = 62;
	
	/** client请求使用物品 */
	private static final int USE_ITEM_REQ_C2G = 71;
	
	@Override
	public void recv(SessHand sess, int subMid, IoBuffer buffer, long clientBundleId)
	{
		GameClientBundle bundle = (GameClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle == null) { return; }
		switch(subMid)
		{
		case MOVE_ITEM_BAG_TO_BAG_REQ_C2G:
			recvMoveItemBagToBagReq_C2G(buffer, bundle.player);
			break;
		case MOVE_ITEM_BAG_TO_WAREHOUSE_REQ_C2G:
			recvMoveItemBagToWarehouseReq_C2G(buffer, bundle.player);
			break;
		case MOVE_ITEM_WAREHOUSE_TO_WAREHOUSE_REQ_C2G:
			recvMoveItemWarehouseToWarehouseReq_C2G(buffer, bundle.player);
			break;
		case MOVE_ITEM_WAREHOUSE_TO_BAG_REQ_C2G:
			recvMoveItemWarehouseToBagReq_C2G(buffer, bundle.player);
			break;
			
		case DROP_ITEM_FROM_BAG_REQ_C2G:
			recvDropItemFromBagReq_C2G(buffer, bundle.player);
			break;
		case SPLIT_ITEM_IN_BAG_REQ_C2G:
			recvSplitItemInBagReq_C2G(buffer, bundle.player);
			break;
			
		case PUT_ON_EQUIP_REQ_C2G:
			recvPutOnEquipReq_C2G(buffer, bundle.player);
			break;
		case TAKE_OFF_EQUIP_REQ_C2G:
			recvTakeOffEquipReq_C2G(buffer, bundle.player);
			break;
			
		case USE_ITEM_REQ_C2G:
			recvUseItemReq_C2G(buffer, bundle.player);
			break;
		}
	}
	
	/** send( game向client通知物品操作异常 ) */
	public void sendOperException_G2C(byte errCode, byte containerType, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, OPER_EXCEPTION_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeByte(msg, errCode);
		IBT.writeByte(msg, containerType);
		msg.send();
	}
	
	//=========================Bag=====↓=====================================
	
	/** send( game向client通知背包已清空 ) */
	public void sendClearItemsInBag_G2C(long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CLEAR_ITEMS_IN_BAG_G2C, SidType.CLIENT, clientBundleId);
		msg.send();
	}
	
	/** send( game向client发送初始化背包物品 ) */
	public void sendInitItemsToBag_G2C(StGoodsContainer bag, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, INIT_ITEMS_TO_BAG_G2C, SidType.CLIENT, clientBundleId);
		bag.write(msg.buffer);
		msg.send();
	}
	
	/** send( game向client通知添加物品到背包 ) */
	public void sendAddItemToBag_G2C(GoodsInfo item, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, ADD_ITEM_TO_BAG_G2C, SidType.CLIENT, clientBundleId);
		GoodsFactory.writeInfo(item, msg.buffer);
		msg.send();
	}
	
	/** send( game向client通知移除物品从背包 ) */
	public void sendRemoveItemFromBag_G2C(long itemUid, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, REMOVE_ITEM_FROM_BAG_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeLong(msg, itemUid);
		msg.send();
	}
	
	/** send( game向client通知更新背包物品 ) */
	public void sendUpdateItemInBag_G2C(GoodsInfo item, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, UPDATE_ITEM_IN_BAG_G2C, SidType.CLIENT, clientBundleId);
		GoodsFactory.writeInfo(item, msg.buffer);
		msg.send();
	}

	//=========================Equip=====↓=====================================
	
	/** send( game向client通知装备已清空 ) */
	public void sendClearItemsInEquip_G2C(long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CLEAR_ITEMS_IN_EQUIP_G2C, SidType.CLIENT, clientBundleId);
		msg.send();
	}
	
	/** send( game向client发送初始化装备 ) */
	public void sendInitItemsToEquip_G2C(StGoodsContainer equip, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, INIT_ITEMS_TO_EQUIP_G2C, SidType.CLIENT, clientBundleId);
		equip.write(msg.buffer);
		msg.send();
	}
	
	/** send( game向client通知穿上装备 ) */
	public void sendAddItemToEquip_G2C(GoodsInfo item, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, ADD_ITEM_TO_EQUIP_G2C, SidType.CLIENT, clientBundleId);
		GoodsFactory.writeInfo(item, msg.buffer);
		msg.send();
	}
	
	/** send( game向client通知脱下装备 ) */
	public void sendRemoveItemFromEquip_G2C(long itemUid, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, REMOVE_ITEM_FROM_EQUIP_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeLong(msg, itemUid);
		msg.send();
	}
	
	/** send( game向client通知更新装备物品 ) */
	public void sendUpdateItemInEquip_G2C(GoodsInfo item, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, UPDATE_ITEM_IN_EQUIP_G2C, SidType.CLIENT, clientBundleId);
		GoodsFactory.writeInfo(item, msg.buffer);
		msg.send();
	}
	
	//=========================Warehouse=====↓=====================================
	
	/** send( game向client通知仓库已清空 ) */
	public void sendClearItemsInWarehouse_G2C(long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, CLEAR_ITEMS_IN_WAREHOUSE_G2C, SidType.CLIENT, clientBundleId);
		msg.send();
	}
	
	/** send( game向client发送初始化仓库物品 ) */
	public void sendInitItemsToWarehouse_G2C(StGoodsContainer warehouse, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, INIT_ITEMS_TO_WAREHOUSE_G2C, SidType.CLIENT, clientBundleId);
		warehouse.write(msg.buffer);
		msg.send();
	}
	
	/** send( game向client通知添加物品到仓库 ) */
	public void sendAddItemToWarehouse_G2C(GoodsInfo item, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, ADD_ITEM_TO_WAREHOUSE_G2C, SidType.CLIENT, clientBundleId);
		GoodsFactory.writeInfo(item, msg.buffer);
		msg.send();
	}
	
	/** send( game向client通知移除物品从仓库 ) */
	public void sendRemoveItemFromWarehouse_G2C(long itemUid, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, REMOVE_ITEM_FROM_WAREHOUSE_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeLong(msg, itemUid);
		msg.send();
	}
	
	/** send( game向client通知更新仓库物品 ) */
	public void sendUpdateItemInWarehouse_G2C(GoodsInfo item, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, UPDATE_ITEM_IN_WAREHOUSE_G2C, SidType.CLIENT, clientBundleId);
		GoodsFactory.writeInfo(item, msg.buffer);
		msg.send();
	}
	
	//=========================Xxxx=====↓=====================================
	
	/** recv( client请求移动物品：从背包到背包 ) */
	private void recvMoveItemBagToBagReq_C2G(IoBuffer buffer, Player player)
	{
		
		long srcUid = IBT.readLong(buffer);
		int tarInd = IBT.readInt(buffer);
		GoodsManager.moveBagItemToBag(player, srcUid, tarInd);
	}
	
	/** recv( client请求移动物品：从背包到仓库 ) */
	private void recvMoveItemBagToWarehouseReq_C2G(IoBuffer buffer, Player player)
	{
		long srcUid = IBT.readLong(buffer);
		int tarInd = IBT.readInt(buffer);
		GoodsManager.moveBagItemToWarehouse(player, srcUid, tarInd);
	}
	
	/** recv( client请求移动物品：从仓库到仓库 ) */
	private void recvMoveItemWarehouseToWarehouseReq_C2G(IoBuffer buffer, Player player)
	{
		long srcUid = IBT.readLong(buffer);
		int tarInd = IBT.readInt(buffer);
		GoodsManager.moveWarehouseItemToWarehouse(player, srcUid, tarInd);
	}
	
	/** recv( client请求移动物品：从仓库到背包 ) */
	private void recvMoveItemWarehouseToBagReq_C2G(IoBuffer buffer, Player player)
	{
		long srcUid = IBT.readLong(buffer);
		int tarInd = IBT.readInt(buffer);
		GoodsManager.moveWarehouseItemToBag(player, srcUid, tarInd);
	}
	
	/** recv( client请求丢弃背包物品 ) */
	private void recvDropItemFromBagReq_C2G(IoBuffer buffer, Player player)
	{
		long itemUid = IBT.readLong(buffer);
		GoodsManager.dropItemFromBag(player, itemUid);
	}
	
	/** recv( client请求拆分背包物品 ) */
	private void recvSplitItemInBagReq_C2G(IoBuffer buffer, Player player)
	{
		long itemUid = IBT.readLong(buffer);
		int spNum = IBT.readInt(buffer);
		GoodsManager.splitItemInBag(player, itemUid, spNum);
	}
	
	/** recv( client请求穿上装备 ) */
	private void recvPutOnEquipReq_C2G(IoBuffer buffer, Player player)
	{
		long srcUid = IBT.readLong(buffer);
		GoodsManager.putOnEquip(player, srcUid);
	}
	
	/** recv( client请求脱下装备 ) */
	private void recvTakeOffEquipReq_C2G(IoBuffer buffer, Player player)
	{
		long srcUid = IBT.readLong(buffer);
		int index = IBT.readInt(buffer);
		GoodsManager.takeOffEquip(player, srcUid, index);
	}
	
	/** recv( client请求使用物品 ) */
	private void recvUseItemReq_C2G(IoBuffer buffer, Player player)
	{
		long itemUid = IBT.readLong(buffer);
		int useNum = IBT.readInt(buffer);
		player.bag.useItem(itemUid, useNum);
	}
	
}
