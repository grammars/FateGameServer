package game.fight.formula;

import common.component.skill.SkillItem;
import game.core.sprite.Creature;

public class SkillCostForm
{
	/** 默认消耗 */
	private static final String T_NULL = "null";
	/** 固定消耗 hp */
	private static final String T_FIXED_4_HP = "fixed4hp";
	/** 固定消耗 mp */
	private static final String T_FIXED_4_MP = "fixed4mp";
	/** 根据 人物等级 消耗 hp */
	private static final String T_LEVEL_4_HP = "level4hp";
	/** 根据 人物等级 消耗 mp */
	private static final String T_LEVEL_4_MP = "level4mp";
	/** 根据 人物等级 消耗 hp,mp */
	private static final String T_LEVEL_4_HPMP = "level4hpmp";
	/** 根据 技能等级 消耗 hp */
	private static final String T_SKLV_4_HP = "sklv4hp";
	/** 根据 技能等级 消耗 mp */
	private static final String T_SKLV_4_MP = "sklv4mp";
	/** 根据 技能等级 消耗 hp,mp */
	private static final String T_SKLV_4_HPMP = "sklv4hpmp";
	
	public static boolean cost(boolean doCost, Creature user, SkillItem si)
	{
		return cost(doCost, user, si, 1);
	}
	
	/** 扣除技能消耗
	 * @param doCost 是否实施扣除，false的话就是只是预估一下够不够，并不实际扣除消耗
	 * @param user 施放者
	 * @param si 施放技能项
	 * @param tarNum 作用目标数量
	 * @return 是否够消耗 */
	public static boolean cost(boolean doCost, Creature user, SkillItem si, int tarNum)
	{
		String costType = si.config.costType;
		double[] costParams = si.config.costParams;
		if( T_NULL.equals(costType) )
		{
			return cost_null(doCost, user, tarNum);
		}
		else if( T_FIXED_4_HP.equals(costType) )
		{
			return cost_fixed4hp(doCost, user, costParams[0], tarNum);
		}
		else if( T_FIXED_4_MP.equals(costType) )
		{
			return cost_fixed4mp(doCost, user, costParams[0], tarNum);
		}
		else if( T_LEVEL_4_HP.equals(costType) )
		{
			return cost_level4hp(doCost, user, costParams[0], costParams[1], tarNum);
		}
		else if( T_LEVEL_4_MP.equals(costType) )
		{
			return cost_level4mp(doCost, user, costParams[0], costParams[1], tarNum);
		}
		else if( T_LEVEL_4_HPMP.equals(costType) )
		{
			return cost_level4hpmp(doCost, user, costParams[0], costParams[1], costParams[2], costParams[3], tarNum);
		}
		else if( T_SKLV_4_HP.equals(costType) )
		{
			return cost_sklv4hp(doCost, user, si, costParams[0], costParams[1], tarNum);
		}
		else if( T_SKLV_4_MP.equals(costType) )
		{
			return cost_sklv4mp(doCost, user, si, costParams[0], costParams[1], tarNum);
		}
		else if( T_SKLV_4_HPMP.equals(costType) )
		{
			return cost_sklv4hpmp(doCost, user, si, costParams[0], costParams[1], costParams[2], costParams[3], tarNum);
		}
		else
		{
			return false;
		}
	}
	
	/** null */
	private static boolean cost_null(boolean doCost, Creature user, int tarNum)
	{
		int cost = 1 * tarNum;
		if(user.attris.curHp() < cost)
		{
			return false;
		}
		if(doCost)
		{
			user.attris.changeCurHp(-cost);
		}
		return true;
	}
	
	/** fixed4hp */
	private static boolean cost_fixed4hp(boolean doCost, Creature user, double single, int tarNum)
	{
		int cost = (int) ( single * tarNum ) ;
		if(user.attris.curHp() < cost)
		{
			return false;
		}
		if(doCost)
		{
			user.attris.changeCurHp(-cost);
		}
		return true;
	}
	
	/** fixed4mp */
	private static boolean cost_fixed4mp(boolean doCost, Creature user, double single, int tarNum)
	{
		int cost = (int) ( single * tarNum ) ;
		if(user.attris.curMp() < cost)
		{
			return false;
		}
		if(doCost)
		{
			user.attris.changeCurMp(-cost);
		}
		return true;
	}
	
	/** level4hp */
	private static boolean cost_level4hp(boolean doCost, Creature user, double slope, double offset, int tarNum)
	{
		int cost = (int) ( slope * user.level + offset ) * tarNum;
		if(user.attris.curHp() < cost)
		{
			return false;
		}
		if(doCost)
		{
			user.attris.changeCurHp(-cost);
		}
		return true;
	}
	
	/** level4mp */
	private static boolean cost_level4mp(boolean doCost, Creature user, double slope, double offset, int tarNum)
	{
		int cost = (int) ( slope * user.level + offset ) * tarNum;
		if(user.attris.curMp() < cost)
		{
			return false;
		}
		if(doCost)
		{
			user.attris.changeCurMp(-cost);
		}
		return true;
	}
	
	/** level4hpmp */
	private static boolean cost_level4hpmp(boolean doCost, Creature user, double slopeHp, double offsetHp, double slopeMp, double offsetMp, int tarNum)
	{
		int costHp = (int) ( slopeHp * user.level + offsetHp ) * tarNum;
		int costMp = (int) ( slopeMp * user.level + offsetMp ) * tarNum;
		if(user.attris.curHp() < costHp)
		{
			return false;
		}
		if(user.attris.curMp() < costMp)
		{
			return false;
		}
		if(doCost)
		{
			user.attris.changeCurHp(-costHp);
			user.attris.changeCurMp(-costMp);
		}
		return true;
	}
	
	/** sklv4hp */
	private static boolean cost_sklv4hp(boolean doCost, Creature user, SkillItem si, double slope, double offset, int tarNum)
	{
		int cost = (int) ( slope * si.level + offset ) * tarNum;
		if(user.attris.curHp() < cost)
		{
			return false;
		}
		if(doCost)
		{
			user.attris.changeCurHp(-cost);
		}
		return true;
	}
	
	/** sklv4mp */
	private static boolean cost_sklv4mp(boolean doCost, Creature user, SkillItem si, double slope, double offset, int tarNum)
	{
		int cost = (int) ( slope * si.level + offset ) * tarNum;
		if(user.attris.curMp() < cost)
		{
			return false;
		}
		if(doCost)
		{
			user.attris.changeCurMp(-cost);
		}
		return true;
	}
	
	/** sklv4hpmp */
	private static boolean cost_sklv4hpmp(boolean doCost, Creature user, SkillItem si, double slopeHp, double offsetHp, double slopeMp, double offsetMp, int tarNum)
	{
		int costHp = (int) ( slopeHp * si.level + offsetHp ) * tarNum;
		int costMp = (int) ( slopeMp * si.level + offsetMp ) * tarNum;
		if(user.attris.curHp() < costHp)
		{
			return false;
		}
		if(user.attris.curMp() < costMp)
		{
			return false;
		}
		if(doCost)
		{
			user.attris.changeCurHp(-costHp);
			user.attris.changeCurMp(-costMp);
		}
		return true;
	}
}
