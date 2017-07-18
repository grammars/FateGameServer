package game.fight.law;

import cfg.SkillCfg;
import common.component.skill.SkillItem;
import common.define.PkModeType;
import common.define.SpriteType;
import game.core.sprite.Creature;
import game.family.FamilyManager;
import game.friend.FriendManager;

public class FightLaw
{
	/** 判定:关系 是否可以进行此次普通攻击 */
	public static boolean hitRelationAvailable(Creature attacker, Creature target)
	{
		if(target.type == SpriteType.NPC) { return false; }
		if(attacker.type == SpriteType.PLAYER && target.type == SpriteType.PLAYER)
		{
			int atkPkMode = attacker.toPlayer().pkMode;
			int tarPkMode = target.toPlayer().pkMode;
			long atkUid = attacker.toPlayer().uid;
			long tarUid = target.toPlayer().uid;
			
			if(atkPkMode == PkModeType.PEACE || tarPkMode == PkModeType.PEACE) { return false; }
			
			if(atkPkMode == PkModeType.FREE)
			{
				if( FriendManager.isFriend(atkUid, tarUid) ) { return false; }
			}
			else if(atkPkMode == PkModeType.FAMILY)
			{
				if( FriendManager.isFriend(atkUid, tarUid) ) { return false; }
				if( FamilyManager.isInSameFamily(atkUid, tarUid) ) { return false; }
			}
		}
		return true;
	}
	
	/** 判定:关系 是否可以进行此次技能使用 */
	public static boolean skillRelationAvailable(Creature user, Creature target, SkillItem si, boolean needNotice)
	{
		if(user == null || target == null) { return false; }
		
		if(user.type == SpriteType.PLAYER)
		{
			if(si.config.effType == SkillCfg.EFF_HARM)
			{
				if(user == target)
				{
					if(needNotice) { user.toPlayer().alert("不能伤害自己！"); }
					return false; 
				}
				if(target.type == SpriteType.MONSTER)
				{
					return true;
				}
				else if(target.type == SpriteType.PLAYER)
				{
					int userPkMode = user.toPlayer().pkMode;
					int tarPkMode = target.toPlayer().pkMode;
					if(userPkMode == PkModeType.PEACE || tarPkMode == PkModeType.PEACE)
					{
						if(needNotice)
						{
							if(userPkMode == PkModeType.PEACE) { user.toPlayer().alert("你处于和平模式，不能主动发起攻击！"); }
							else if(tarPkMode == PkModeType.PEACE) { user.toPlayer().alert("对方处于和平模式，不能对其攻击！"); }
						}
						return false;
					}
					if(userPkMode == PkModeType.FREE)
					{
						if( FriendManager.isFriend(user.toPlayer().uid, target.toPlayer().uid) )
						{
							if(needNotice) { user.toPlayer().alert("不能伤害自己的朋友！"); }
							return false;
						}
						return true;
					}
					else if(userPkMode == PkModeType.FAMILY)
					{
						if( FriendManager.isFriend(user.toPlayer().uid, target.toPlayer().uid) )
						{
							if(needNotice) { user.toPlayer().alert("不能伤害自己的朋友！"); }
							return false;
						}
						if( FamilyManager.isInSameFamily(user.toPlayer().uid, target.toPlayer().uid) )
						{
							if(needNotice) { user.toPlayer().alert("不能伤害同一家族的玩家！"); }
							return false;
						}
						return true;
					}
				}
				else if(target.type == SpriteType.NPC)
				{
					if(needNotice) { user.toPlayer().alert("以老夫这么多年的江湖经验，NPC这种生物最好别去惹！"); }
					return false;
				}
				else
				{
					return false;
				}
			}
			else if(si.config.effType == SkillCfg.EFF_CURE)
			{
				if(user == target) { return true; }
				if(target.type == SpriteType.PLAYER)
				{
					int userPkMode = user.toPlayer().pkMode;
					int tarPkMode = target.toPlayer().pkMode;
					if( FriendManager.isFriend(user.toPlayer().uid, target.toPlayer().uid) ) 
					{
						return true;
					}
					else
					{
						if(userPkMode == PkModeType.FAMILY)
						{
							if( FamilyManager.isInSameFamily(user.toPlayer().uid, target.toPlayer().uid) )
							{
								return true;
							}
						}
						else
						{
							if(needNotice) { user.toPlayer().alert("这位玩家你和他又不熟，何必治疗他！"); }
							return false;
						}
					}
				}
				else
				{
					if(needNotice) { user.toPlayer().alert("Ta不需要你的治疗，请别自作多情！"); }
					return false;
				}
			}
		}
		else if(user.type == SpriteType.MONSTER)
		{
			if(si.config.effType == SkillCfg.EFF_HARM)
			{
				if(user == target) { return false; }
				if(target.type == SpriteType.PLAYER)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else if(si.config.effType == SkillCfg.EFF_CURE)
			{
				if(user == target) { return true; }
				if(target.type == SpriteType.MONSTER)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		
		return false;
	}
}
