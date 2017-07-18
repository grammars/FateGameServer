package message.gm.params;

import common.component.player.AttriSet;
import game.GameClientBundle;
import game.core.sprite.Player;
import game.player.PlayerManager;

public class GmPlayerPC
{
	/** 改变玩家职业 */
	private static final int CHANGE_VOC = 1;
	/** 改变玩家等级 */
	private static final int CHANGE_LEVEL = 2;
	/** 改变当前生命值 */
	private static final int CHANGE_CUR_HP = 3;
	/** 改变当前魔法值 */
	private static final int CHANGE_CUR_MP = 4;
	/** 改变生命值 */
	private static final int CHANGE_HP = 5;
	/** 改变魔法值 */
	private static final int CHANGE_MP = 6;
	/** 改变移动速度 */
	private static final int CHANGE_MOVE_SPEED = 7;
	/** 改变攻击力 */
	private static final int CHANGE_ATTACK = 8;
	/** 改变攻击速度 */
	private static final int CHANGE_ATTACK_SPEED = 9;
	/** 改变护甲值 */
	private static final int CHANGE_ARMOR = 10;
	/** 改变护甲穿透 */
	private static final int CHANGE_ARMOR_PENETRATION = 11;
	/** 改变法术强度 */
	private static final int CHANGE_SPELL_POWER = 12;
	/** 改变法术免疫 */
	private static final int CHANGE_SPELL_IMMUNITY = 13;
	/** 改变法术穿透 */
	private static final int CHANGE_SPELL_PENETRATION = 14;
	
	public static void cmdHandler(int subCmdId, GameClientBundle bundle, byte byte0, byte byte1, 
			int int0, int int1, int int2, float float0, double double0, 
			long long0, long long1, String str0, String str1)
	{
		Player player = bundle.player;
		switch(subCmdId)
		{
		case CHANGE_VOC:
			H_CHANGE_VOC(player, byte0);
			break;
		case CHANGE_LEVEL:
			H_CHANGE_LEVEL(player, int0);
			break;
		case CHANGE_CUR_HP:
			H_CHANGE_CUR_HP(player, int0);
			break;
		case CHANGE_CUR_MP:
			H_CHANGE_CUR_MP(player, int0);
			break;
		case CHANGE_HP:
			H_CHANGE_HP(player, int0);
			break;
		case CHANGE_MP:
			H_CHANGE_MP(player, int0);
			break;
		case CHANGE_MOVE_SPEED:
			H_CHANGE_MOVE_SPEED(player, int0);
			break;
		case CHANGE_ATTACK:
			H_CHANGE_ATTACK(player, int0);
			break;
		case CHANGE_ATTACK_SPEED:
			H_CHANGE_ATTACK_SPEED(player, float0);
			break;
		case CHANGE_ARMOR:
			H_CHANGE_ARMOR(player, int0);
			break;
		case CHANGE_ARMOR_PENETRATION:
			H_CHANGE_ARMOR_PENETRATION(player, int0);
			break;
		case CHANGE_SPELL_POWER:
			H_CHANGE_SPELL_POWER(player, int0);
			break;
		case CHANGE_SPELL_IMMUNITY:
			H_CHANGE_SPELL_IMMUNITY(player, int0);
			break;
		case CHANGE_SPELL_PENETRATION:
			H_CHANGE_SPELL_PENETRATION(player, int0);
			break;
		}
	}
	
	private static void H_CHANGE_VOC(Player player, byte voc)
	{
		PlayerManager.character.changeVoc(player, voc, true);
	}
	
	private static void H_CHANGE_LEVEL(Player player, int level)
	{
		PlayerManager.character.changeLevel(player, level);
	}
	
	private static void H_CHANGE_CUR_HP(Player player, int value)
	{
		player.attris.changeCurHpTo(value);
	}
	
	private static void H_CHANGE_CUR_MP(Player player, int value)
	{
		player.attris.changeCurMpTo(value);
	}
	
	private static void H_CHANGE_HP(Player player, int value)
	{
		player.attris.changeHpTo(value, AttriSet.SRC_BASE);
	}
	
	private static void H_CHANGE_MP(Player player, int value)
	{
		player.attris.changeMpTo(value, AttriSet.SRC_BASE);
	}
	
	private static void H_CHANGE_MOVE_SPEED(Player player, int value)
	{
		player.attris.changeMoveSpeedTo(value, AttriSet.SRC_BASE);
	}
	
	private static void H_CHANGE_ATTACK(Player player, int value)
	{
		player.attris.changeAttackTo(value, AttriSet.SRC_BASE);
	}
	
	private static void H_CHANGE_ATTACK_SPEED(Player player, float value)
	{
		player.attris.changeAttackSpeedTo(value, AttriSet.SRC_BASE);
	}
	
	private static void H_CHANGE_ARMOR(Player player, int value)
	{
		player.attris.changeArmorTo(value, AttriSet.SRC_BASE);
	}
	
	private static void H_CHANGE_ARMOR_PENETRATION(Player player, int value)
	{
		player.attris.changeArmorPeneTo(value, AttriSet.SRC_BASE);
	}
	
	private static void H_CHANGE_SPELL_POWER(Player player, int value)
	{
		player.attris.changeSpellPowerTo(value, AttriSet.SRC_BASE);
	}
	
	private static void H_CHANGE_SPELL_IMMUNITY(Player player, int value)
	{
		player.attris.changeSpellImmunTo(value, AttriSet.SRC_BASE);
	}
	
	private static void H_CHANGE_SPELL_PENETRATION(Player player, int value)
	{
		player.attris.changeSpellPeneTo(value, AttriSet.SRC_BASE);
	}
	
}
