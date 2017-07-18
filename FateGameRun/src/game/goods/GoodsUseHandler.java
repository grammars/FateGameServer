package game.goods;

import cfg.GoodsDrugCfg;
import common.component.buff.Buff;
import common.component.cd.CDUnit;
import common.component.goods.GoodsInfo;
import common.component.goods.GoodsOperEnum;
import common.component.goods.info.*;
import common.define.GoodsType;
import game.buff.BuffManager;
import game.core.sprite.Player;

public class GoodsUseHandler
{
	/** 使用物品(但不作扣除数量处理) */
	public static GoodsOperEnum use(Player player, GoodsInfo item, int useNum)
	{
		if(!item.available()) { return GoodsOperEnum.FAIL; }
		if(item.num < useNum) { return GoodsOperEnum.NUM_LESS; }
		GoodsOperEnum oper =  GoodsOperEnum.FAIL;
		switch(item.baseCfg.type)
		{
		case GoodsType.SYSTEM:
			player.alert("暂不支持使用SYSTEM物品");
			oper = useSyetemItem(player, item.toSystem(), useNum);
			break;
		case GoodsType.DRUG:
			//player.alert("暂不支持使用DRUG物品");
			oper = useDrugItem(player, item.toDrug(), useNum);
			break;
		case GoodsType.TASK:
			player.alert("暂不支持使用TASK物品");
			oper = useTaskItem(player, item.toTask(), useNum);
			break;
		case GoodsType.EQUIP:
			//player.alert("暂不支持使用EQUIP物品");
			oper = useEquipItem(player, item.toEquip(), useNum);
			break;
		case GoodsType.GEM:
			player.alert("暂不支持使用GEM物品");
			oper = useGemItem(player, item.toGem(), useNum);
			break;
		case GoodsType.GIFT:
			player.alert("暂不支持使用GIFT物品");
			oper = useGiftItem(player, item.toGift(), useNum);
			break;
		}
		if(oper != GoodsOperEnum.SUCC)
		{
			player.bag.operExceptionHandler(oper);
		}
		return oper;
	}
	
	/** useSyetemItem */
	private static GoodsOperEnum useSyetemItem(Player player, SystemGI gi, int useNum)
	{
		return GoodsOperEnum.SUCC;
	}
	
	/** useDrugItem */
	private static GoodsOperEnum useDrugItem(Player player, DrugGI gi, int useNum)
	{
		if( !player.cd.available(CDUnit.T_GOODS, gi.baseCfgId) )
		{
			return GoodsOperEnum.CD_ING;
		}
		GoodsDrugCfg dc = gi.drugCfg;
		if(dc.absHp != 0)
		{
			player.attris.changeCurHp(dc.absHp);
		}
		if(dc.ratHp != 0)
		{
			player.attris.changeCurHp( player.attris.rateCurHp(dc.ratHp) );
		}
		if(dc.absMp != 0)
		{
			player.attris.changeCurMp(dc.absMp);
		}
		if(dc.ratMp != 0)
		{
			player.attris.changeCurMp( player.attris.rateCurMp(dc.ratMp) );
		}
		if(dc.addBuff > 0)
		{
			Buff buff = new Buff();
			buff.setup(dc.addBuff, player);
			player.addBuff(buff);
		}
		if(dc.cdTime > 0)
		{
			player.cd.setCD(CDUnit.T_GOODS, dc.id, dc.cdTime);
		}
		return GoodsOperEnum.SUCC;
	}
	
	/** useTaskItem */
	private static GoodsOperEnum useTaskItem(Player player, TaskGI gi, int useNum)
	{
		return GoodsOperEnum.SUCC;
	}
	
	/** useEquipItem */
	private static GoodsOperEnum useEquipItem(Player player, EquipGI gi, int useNum)
	{
		GoodsOperEnum oper = GoodsManager.putOnEquip(player, gi.uid);
		return oper;
	}
	
	/** useGemItem */
	private static GoodsOperEnum useGemItem(Player player, GemGI gi, int useNum)
	{
		return GoodsOperEnum.SUCC;
	}
	
	/** useGiftItem */
	private static GoodsOperEnum useGiftItem(Player player, GiftGI gi, int useNum)
	{
		return GoodsOperEnum.SUCC;
	}
}
