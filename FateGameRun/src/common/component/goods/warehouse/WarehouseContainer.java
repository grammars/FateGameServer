package common.component.goods.warehouse;

import message.goods.GoodsMsg;
import common.component.goods.GoodsContainer;
import common.component.goods.GoodsInfo;
import common.component.goods.GoodsOperEnum;
import game.core.sprite.Player;

public class WarehouseContainer extends GoodsContainer
{
	private static final int CAPACITY_MIN = 96;
	private static final int CAPACITY_MAX = 126;
	
	public WarehouseContainer(Player owner)
	{
		super(owner);
		this.type = TYPE_WAREHOUSE;
		capacity = CAPACITY_MIN;
	}
	
	@Override
	public void clearItemsHandler()
	{
		GoodsMsg.getInstance().sendClearItemsInWarehouse_G2C(owner.getBundle().getUid());
	}
	
	@Override
	public void initItemsHandler()
	{
		GoodsMsg.getInstance().sendInitItemsToWarehouse_G2C(this, owner.getBundle().getUid());
	}
	
	@Override
	protected void addItemHandler(GoodsInfo item)
	{
		GoodsMsg.getInstance().sendAddItemToWarehouse_G2C(item, owner.getBundle().getUid());
	}
	
	@Override
	protected void removeItemHandler(GoodsInfo item)
	{
		GoodsMsg.getInstance().sendRemoveItemFromWarehouse_G2C(item.uid, owner.getBundle().getUid());
	}
	
	@Override
	protected void updateItemHandler(GoodsInfo item)
	{
		GoodsMsg.getInstance().sendUpdateItemInWarehouse_G2C(item, owner.getBundle().getUid());
	}
}
