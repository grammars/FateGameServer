package common.component.skill;

import common.struct.skill.StSkillItem;
import cfg.SkillCfg;

public class SkillItem extends StSkillItem
{
	public SkillCfg config;
	
	public SkillItem()
	{
	}
	
	public void setup(int id, int level)
	{
		this.id = id;
		this.level = level;
		build();
	}
	
	private void build()
	{
		config = SkillCfg.get(this.id);
	}
	
	/** 导入结构数据 */
	public void importData(StSkillItem data)
	{
		this.id = data.id;
		this.level = data.level;
		build();
	}
	
	/** 导出结构数据 */
	public StSkillItem exportData()
	{
		StSkillItem data = new StSkillItem();
		data.id = this.id;
		data.level = this.level;
		return data;
	}
}
