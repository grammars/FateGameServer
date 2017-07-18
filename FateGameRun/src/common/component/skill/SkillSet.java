package common.component.skill;

import java.util.ArrayList;
import java.util.List;

import common.struct.skill.StSkillItem;
import common.struct.skill.StSkillSet;

public class SkillSet
{
	/** 最大技能数[数据库中BLOB分配了256字节 所以限制为 count(4) + 31*(id(4)+level(4))] */
	//private static final int MAX_COUNT = 31;
	
	public List<SkillItem> items = new ArrayList<>();
	
	public SkillSet()
	{
	}
	
	/** 获得SkillItem */
	public SkillItem getItem(int id)
	{
		for(int i = 0; i < items.size(); i++)
		{
			if(items.get(i).id == id) { return items.get(i); }
		}
		return null;
	}
	
	public void setup(int[] ids, int[] levels)
	{
		items.clear();
		int num = Math.min(ids.length, levels.length);
		for(int i = 0; i < num; i++)
		{
			SkillItem item = new SkillItem();
			item.setup(ids[i], levels[i]);
			items.add(item);
		}
	}
	
	/** 导入结构数据 */
	public void importData(StSkillSet data)
	{
		items.clear();
		int num = data.items.size();
		for(int i = 0; i < num; i++)
		{
			StSkillItem st = data.items.get(i);
			SkillItem item = new SkillItem();
			item.importData(st);
			items.add(item);
		}
	}
	
	/** 导出结构数据 */
	public StSkillSet exportData()
	{
		StSkillSet data = new StSkillSet();
		int num = items.size();
		for(int i = 0; i < num; i++)
		{
			SkillItem item = items.get(i);
			StSkillItem st = item.exportData();
			data.items.add(st);
		}
		return data;
	}
}
