package message.gm.params;

import java.util.Collection;

import cfg.GoodsBaseCfg;
import common.component.goods.GoodsFactory;
import common.component.goods.GoodsInfo;
import common.define.GoodsType;
import game.GameClientBundle;
import game.core.sprite.Player;

public class GmGoodsPC
{
	/** 制造物品 */
	private static final int MAKE_GOODS = 1;
	/** 清空包裹 */
	private static final int CLEAR_BAG = 2;
	/** 领取一套装备 */
	private static final int GET_EQUIPS = 3;
	
	public static void cmdHandler(int subCmdId, GameClientBundle bundle, byte byte0, byte byte1, 
			int int0, int int1, int int2, float float0, double double0, 
			long long0, long long1, String str0, String str1)
	{
		Player player = bundle.player;
		switch(subCmdId)
		{
		case MAKE_GOODS:
			H_MAKE_GOODS(player, int0, int1);
			break;
		case CLEAR_BAG:
			H_CLEAR_BAG(player);
			break;
		case GET_EQUIPS:
			H_GET_EQUIPS(player, byte0, int0);
			break;
		}
	}
	
	private static void H_MAKE_GOODS(Player player, int cfgId, int num)
	{
		System.out.println(player.name + "制造物品" + cfgId + " " + num + "个");
		GoodsInfo item = GoodsFactory.createInfo(cfgId);
		item.num = num;
		player.bag.addItem(item);
	}
	
	private static void H_CLEAR_BAG(Player player)
	{
		player.bag.clearItems();
	}
	
	private static void H_GET_EQUIPS(Player player, byte voc, int level)
	{
		Collection<GoodsBaseCfg> gbcs = GoodsBaseCfg.getAll();
		for(GoodsBaseCfg gbc : gbcs)
		{
			if(gbc.type != GoodsType.EQUIP) { continue; }
			if(gbc.reqLevMin != level) { continue; }
			if(gbc.reqVoc != 0 && gbc.reqVoc != voc) { continue; }
			GoodsInfo item = GoodsFactory.createInfo(gbc.id);
			item.num = 1;
			player.bag.addItem(item);
		}
	}
}
