package common.component.goods;

import java.util.ArrayList;
import java.util.List;

import message.goods.GoodsMsg;
import common.struct.goods.StGoodsContainer;
import game.core.sprite.Player;

public abstract class GoodsContainer extends StGoodsContainer
{
	/** 容器类型：背包 */
	public static final byte TYPE_BAG = 1;
	/** 容器类型：装备 */
	public static final byte TYPE_EQUIP = 2;
	/** 容器类型：仓库 */
	public static final byte TYPE_WAREHOUSE = 3;
	
	/** 拥有者 */
	protected Player owner;
	/** 类型 */
	protected byte type;
	
	public GoodsContainer(Player owner)
	{
		this.owner = owner;
	}
	
	/** 是否已满 */
	public boolean isFull()
	{
		return items.size() >= capacity;
	}
	
	/** 寻找可放置位置
	 * -1表示无可放置位置 */
	protected int availableIndex()
	{
		List<Integer> existIndices = new ArrayList<>();
		for(GoodsInfo item : items)
		{
			existIndices.add(item.index);
		}
		for(int i = 0; i < capacity; i++)
		{
			boolean existed = false;
			for(Integer ei : existIndices)
			{
				if(ei == i)
				{
					existed = true;
					break;
				}
			}
			if(!existed) { return i; }
		}
		return -1;
	}
	
	/** 根据uid获得GoodsInfo */
	public GoodsInfo getItemByUid(long uid)
	{
		for(GoodsInfo item : items)
		{
			if(item.uid == uid) { return item; }
		}
		return null;
	}
	
	/** 根据index获得GoodsInfo */
	public GoodsInfo getItemByIndex(int index)
	{
		for(GoodsInfo item : items)
		{
			if(item.index == index) { return item; }
		}
		return null;
	}
	
	/** 获取某种物品的总数量 */
	public int getTotalNum(int baseCfgId)
	{
		int total = 0;
		for(GoodsInfo item : items)
		{
			if(item.baseCfgId == baseCfgId) { total += item.num; }
		}
		return total;
	}
	
	/** 寻找同种物品 */
	public List<GoodsInfo> getSameItems(int baseCfgId)
	{
		List<GoodsInfo> sames = new ArrayList<>();
		for(GoodsInfo item : items)
		{
			if(item.baseCfgId == baseCfgId)
			{
				sames.add(item);
			}
		}
		return sames;
	}
	
	/** 将指定物品往现有物品上堆叠
	 * @param item 指定物品 */
	public void heapItem(GoodsInfo item)
	{
		List<GoodsInfo> sames = getSameItems(item.baseCfgId);
		for(GoodsInfo si : sames)
		{
			int numRoom = si.numRoom();
			if(item.num <= 0)
			{
				break;
			}
			if(numRoom > 0)
			{
				int giveNum = Math.min(item.num, numRoom);
				si.num += giveNum;
				updateItemHandler(si);
				item.num -= giveNum;
			}
		}
	}
	
	/** 添加一项物品到指定位置[会通知client] */
	public GoodsOperEnum addItemAt(GoodsInfo item, int index)
	{
		if(item == null)
		{
			return operExceptionHandler(GoodsOperEnum.ITEM_NULL);
		}
		if(items.size() >= capacity)
		{
			return operExceptionHandler(GoodsOperEnum.FULL);
		}
		if(index >= capacity)
		{
			return operExceptionHandler(GoodsOperEnum.OVER_CAPACITY);
		}
		if(index < 0)
		{
			index = availableIndex();
		}
		if(index < 0)
		{
			return operExceptionHandler(GoodsOperEnum.FAIL);
		}
		GoodsInfo tmp = getItemByIndex(index);
		if(tmp != null)
		{
			return operExceptionHandler(GoodsOperEnum.POS_OCCUPY);
		}
		item.index = index;
		items.add(item);
		addItemHandler(item);
		return GoodsOperEnum.SUCC;
	}
	
	/** 添加一项物品[会通知client] */
	public GoodsOperEnum addItem(GoodsInfo item)
	{
		return addItemAt(item, -1);
	}
	
	/** 移除一项物品[会通知client] */
	public GoodsInfo removeItem(long uid)
	{
		for(int i = items.size()-1; i >= 0; i--)
		{
			if(items.get(i).uid == uid)
			{
				GoodsInfo ri = items.remove(i);
				removeItemHandler(ri);
				return ri;
			}
		}
		return null;
	}
	
	/** 移动物品到指定位置 */
	public GoodsOperEnum moveItem(long srcUid, int index)
	{
		GoodsInfo target = getItemByUid(srcUid);
		if(target == null)
		{
			return operExceptionHandler(GoodsOperEnum.ITEM_NULL);
		}
		if(target.index == index)
		{
			return GoodsOperEnum.SUCC;
		}
		GoodsInfo old = getItemByIndex(index);
		if(old == null)
		{
			//原有位置是空的
			target.index = index;
			updateItemHandler(target);
		}
		else
		{
			//原来位置有物品
			if(target.baseCfgId != old.baseCfgId)
			{
				//是不同种类的物品
				old.index = target.index;
				target.index = index;
				updateItemHandler(old);
				updateItemHandler(target);
			}
			else
			{
				//是同种类的物品
				int maxHeap = old.baseCfg.maxHeap;
				int totalNum = old.num + target.num;
				if(totalNum <= maxHeap)
				{
					old.num = totalNum;
					updateItemHandler(old);
					items.remove(target);
					removeItemHandler(target);
				}
				else
				{
					old.num = maxHeap;
					target.num = totalNum-maxHeap;
					updateItemHandler(old);
					updateItemHandler(target);
				}
			}
		}
		return GoodsOperEnum.SUCC;
	}
	
	/** 拆分背包物品 */
	public GoodsOperEnum splitItem(long itemUid, int spNum)
	{
		GoodsInfo oldItem = getItemByUid(itemUid);
		if(spNum <= 0 || oldItem.num <= spNum)
		{
			return operExceptionHandler(GoodsOperEnum.FAIL);
		}
		if(items.size() >= capacity)
		{
			return operExceptionHandler(GoodsOperEnum.FULL);
		}
		else
		{
			oldItem.num = oldItem.num-spNum;
			updateItemHandler(oldItem);
			GoodsInfo newItem = GoodsFactory.cloneInfo(oldItem);
			newItem.createUid();
			newItem.num = spNum;
			newItem.index = availableIndex();
			items.add(newItem);
			addItemHandler(newItem);
			return GoodsOperEnum.SUCC;
		}
	}
	
	/** 清除所有物品 */
	public void clearItems()
	{
		items.clear();
		clearItemsHandler();
	}
	
	/** 清空物品handler{增加对客户端的通知} */
	abstract public void clearItemsHandler();
	
	/** 初始化物品handler{增加对客户端的通知} */
	abstract public void initItemsHandler();
	
	/** 添加物品handler{增加对客户端的通知}  */
	abstract protected void addItemHandler(GoodsInfo item);
	
	/** 移除物品handler{增加对客户端的通知} */
	abstract protected void removeItemHandler(GoodsInfo item);
	
	/** 更新物品信息handler{增加对客户端的通知} */
	abstract protected void updateItemHandler(GoodsInfo item);
	
	/** 物品操作异常结果handler{增加对客户端的通知} */
	public GoodsOperEnum operExceptionHandler(GoodsOperEnum result)
	{
		GoodsMsg.getInstance().sendOperException_G2C(result.errCode, type, owner.getBundle().getUid());
		return result;
	}
	
	
	/** build */
	protected void build()
	{
		//
	}
	
	/** 导入结构数据 */
	public void importData(StGoodsContainer data)
	{
		this.items = data.items;
		build();
	}
	
	/** 导出结构数据 */
	public StGoodsContainer exportData()
	{
		StGoodsContainer data = new StGoodsContainer();
		data.items = this.items;
		return data;
	}
}
