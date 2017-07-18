package common.component.practice;

public enum PracticeOperEnum
{
	/** 成功 */
	SUCC( (byte)0 ),
	/** 默认失败 */
	FAIL( (byte)1 ),
	/** 已到最高修炼等级 */
	REACH_TOP( (byte)2 ),
	/** 升级经验不够 */
	EXP_NOT_ENOUGH( (byte)3 ),
	;
	
	public byte errCode;
	private PracticeOperEnum(byte errCode)
	{
		this.errCode = errCode;
	}
}
