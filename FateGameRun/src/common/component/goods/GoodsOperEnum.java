package common.component.goods;

public enum GoodsOperEnum
{
	/** 成功 */
	SUCC( (byte)0 ),
	/** 默认失败 */
	FAIL( (byte)1 ),
	/** 已满 */
	FULL( (byte)2 ),
	/** 超过容量 */
	OVER_CAPACITY( (byte)3 ),
	/** 指定位置已存在物品,请选择空格位置放置 */
	POS_OCCUPY( (byte)4 ),
	/** 物品不存在 */
	ITEM_NULL( (byte)5 ),
	/** 这不是一件装备，请选择装备类物品进行穿戴 */
	NOT_EQUIP( (byte)6 ),
	/** 物品数量不足 */
	NUM_LESS( (byte)7 ),
	/** 物品CD中 */
	CD_ING( (byte)8 ),
	/** 玩家等级太低 */
	PLAYER_LEV_TOO_MIN( (byte)9 ),
	/** 玩家等级太高 */
	PLAYER_LEV_TOO_MAX( (byte)10 ),
	/** 玩家职业不符合 */
	PLAYER_VOC_DENY( (byte)11 ),
	;
	
	public byte errCode;
	private GoodsOperEnum(byte errCode)
	{
		this.errCode = errCode;
	}
}
