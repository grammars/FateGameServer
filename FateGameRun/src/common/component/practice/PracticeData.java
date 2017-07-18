package common.component.practice;

import game.core.sprite.Player;
import common.struct.practice.StPracticeData;

public class PracticeData extends StPracticeData
{
	private Player owner;
	
	public PracticeData(Player owner)
	{
		this.owner = owner;
	}
	
	/** 进行升级 */
	public PracticeOperEnum levelUp()
	{
		if(lev >= TOP_LEV) { return PracticeOperEnum.REACH_TOP; }
		lev++;
		return PracticeOperEnum.SUCC;
	}
	
	private void build()
	{
		
	}
	
	/** 导入结构数据 */
	public void importData(StPracticeData data)
	{
		this.exp = data.exp;
		this.lev = data.lev;
		build();
	}
	
	/** 导出结构数据 */
	public StPracticeData exportData()
	{
		StPracticeData data = new StPracticeData();
		data.exp = this.exp;
		data.lev = this.lev;
		return data;
	}
	
}
