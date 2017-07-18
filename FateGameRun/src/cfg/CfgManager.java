package cfg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import framework.FilePath;

public class CfgManager
{

	public static void load()
	{
		loadSub(SampleCfg.TYPE);
		loadSub(MapInfoCfg.TYPE);
		loadSub(MapDoorCfg.TYPE);
		loadSub(VocCfg.TYPE);
		loadSub(NpcInfoCfg.TYPE);
		loadSub(MonsterInfoCfg.TYPE);
		loadSub(MonsterPoolCfg.TYPE);
		loadSub(BuffCfg.TYPE);
		loadSub(SkillCfg.TYPE);
		loadSub(SkillEffCfg.TYPE);
		loadSub(SkillExtAimCfg.TYPE);
		loadSub(SkillExtAoeCfg.TYPE);
		loadSub(SkillExtPathCfg.TYPE);
		loadSub(SkillTreeCfg.TYPE);
		loadSub(SelfLookCfg.TYPE);
		loadSub(GoodsBaseCfg.TYPE);
		loadSub(GoodsDrugCfg.TYPE);
		loadSub(GoodsEquipCfg.TYPE);
		loadSub(TaskCfg.TYPE);
		process();
	}
	
	
	private static void loadSub(String type)
	{
		File f = new File(FilePath.getCfgDir()+"tables/buf/"+type+".cfgbuf");
		try
		{
			@SuppressWarnings("resource")
			FileInputStream in = new FileInputStream(f);
			byte[] buff;
			try
			{
				buff = new byte[in.available()];
				in.read(buff);
				String content = new String(buff, "UTF-8");
				//System.out.println("该cfgbuf的内容\r"+content+"\r");
				parse(type, content);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	/** 解析 */
	private static void parse(String type, String content)
	{
		final String LINE_SEP = "\n";
		final String WORD_SEP = "┼";
		
		HashMap<String, String[]> dataDic = new HashMap<>();//key:keyname value:valuesArr
		
		String[] lineArr = content.split(LINE_SEP);
		int itemCount = lineArr.length - 1;//-1是因为首行是字段名，并非可用数据
		String keynamesStr = lineArr[0];
		String[] keynamesArr = keynamesStr.split(WORD_SEP);
		int i = 0;
		String keyname;
		String[] valuesArr;
		String[][] index2valuesArr = new String[keynamesArr.length][];
		for(i = 0; i < keynamesArr.length; i++)
		{
			keyname = keynamesArr[i];
			//System.out.println("keyname=" + keyname);
			valuesArr = new String[itemCount];
			dataDic.put(keyname, valuesArr);
			index2valuesArr[i] = valuesArr;
		}
		for(i = 1; i <= itemCount; i++)
		{
			String lineStr = lineArr[i];
			if(lineStr.equals("")) { itemCount--; continue; }
			String[] valuesLine = lineStr.split(WORD_SEP);
			for(int ind = 0; ind < valuesLine.length; ind++)
			{
				String value = valuesLine[ind];
				valuesArr = index2valuesArr[ind];
				valuesArr[i-1] = value;
			}
		}
		
		try
		{
			switch(type)
			{
			case SampleCfg.TYPE:
				SampleCfg.parse(dataDic, itemCount);
				break;
			case MapInfoCfg.TYPE:
				MapInfoCfg.parse(dataDic, itemCount);
				break;
			case MapDoorCfg.TYPE:
				MapDoorCfg.parse(dataDic, itemCount);
				break;
			case VocCfg.TYPE:
				VocCfg.parse(dataDic, itemCount);
				break;
			case NpcInfoCfg.TYPE:
				NpcInfoCfg.parse(dataDic, itemCount);
				break;
			case MonsterInfoCfg.TYPE:
				MonsterInfoCfg.parse(dataDic, itemCount);
				break;
			case MonsterPoolCfg.TYPE:
				MonsterPoolCfg.parse(dataDic, itemCount);
				break;
			case BuffCfg.TYPE:
				BuffCfg.parse(dataDic, itemCount);
				break;
			case SkillCfg.TYPE:
				SkillCfg.parse(dataDic, itemCount);
				break;
			case SkillEffCfg.TYPE:
				SkillEffCfg.parse(dataDic, itemCount);
				break;
			case SkillExtAimCfg.TYPE:
				SkillExtAimCfg.parse(dataDic, itemCount);
				break;
			case SkillExtAoeCfg.TYPE:
				SkillExtAoeCfg.parse(dataDic, itemCount);
				break;
			case SkillExtPathCfg.TYPE:
				SkillExtPathCfg.parse(dataDic, itemCount);
				break;
			case SkillTreeCfg.TYPE:
				SkillTreeCfg.parse(dataDic, itemCount);
				break;
			case SelfLookCfg.TYPE:
				SelfLookCfg.parse(dataDic, itemCount);
				break;
			case GoodsBaseCfg.TYPE:
				GoodsBaseCfg.parse(dataDic, itemCount);
				break;
			case GoodsDrugCfg.TYPE:
				GoodsDrugCfg.parse(dataDic, itemCount);
				break;
			case GoodsEquipCfg.TYPE:
				GoodsEquipCfg.parse(dataDic, itemCount);
				break;
			case TaskCfg.TYPE:
				TaskCfg.parse(dataDic, itemCount);
				break;
			}
		}
		catch(NullPointerException e)
		{
			System.err.println("配置表"+type+"解析发生错误，可能字段名与配置表不符");
		}
	}
	
	/** 加工模版表<br>
	 * 部分模版表之间会需要互相引用关联<br>
	 * 比如skill 引用 skill_ext_xxxx表 */
	private static void process()
	{
		SkillCfg.process();
	}
}
