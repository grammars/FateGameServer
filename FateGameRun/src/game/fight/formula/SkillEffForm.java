package game.fight.formula;

import cfg.SkillEffCfg;
import game.core.sprite.Creature;
import game.fight.FightManager;
import game.player.PlayerManager;
import common.component.player.AttriSet;
import common.component.skill.SkillItem;
import common.struct.player.StAttributes;

public class SkillEffForm
{
	public static boolean execute(SkillItem si, Creature user, Creature tar)
	{
		for(SkillEffCfg effCfg : si.config.effs)
		{
			exeItem(effCfg, si, user, tar);
		}
		return true;
	}
	
	private static boolean exeItem(SkillEffCfg cfg, SkillItem si, Creature user, Creature tar)
	{
		//System.out.println("SkillEffForm::exeItem");
		AttriSet userAtt = user.attris;
		AttriSet tarAtt = tar.attris;
		
		double VA = (double)cfg.baseValue;
		VA += cfg.fUserLevel * user.level;
		VA += cfg.fTarLevel * tar.level;
		VA += cfg.fSkillLevel * si.level;
		VA += cfg.fUserCurHp * userAtt.curHp();
		VA += cfg.fTarCurHp * tarAtt.curHp();
		VA += cfg.fUserHp * userAtt.hp();
		VA += cfg.fTarHp * tarAtt.hp();
		VA += cfg.fUserCurMp * userAtt.curMp();
		VA += cfg.fTarCurMp * tarAtt.curMp();
		VA += cfg.fUserMp * userAtt.mp();
		VA += cfg.fTarMp * tarAtt.mp();
		VA += cfg.fUserAttack * userAtt.attack();
		VA += cfg.fTarAttack * tarAtt.attack();
		VA += cfg.fUserAttackSpeed * userAtt.attackSpeed();
		VA += cfg.fTarAttackSpeed * tarAtt.attackSpeed();
		VA += cfg.fUserArmor * userAtt.armor();
		VA += cfg.fTarArmor * tarAtt.armor();
		VA += cfg.fUserArmorPene * userAtt.armorPene();
		VA += cfg.fTarArmorPene * tarAtt.armorPene();
		VA += cfg.fUserSpellPower * userAtt.spellPower();
		VA += cfg.fTarSpellPower * tarAtt.spellPower();
		VA += cfg.fUserSpellImmun * userAtt.spellImmun();
		VA += cfg.fTarSpellImmun * tarAtt.spellImmun();
		VA += cfg.fUserSpellPene * userAtt.spellPene();
		VA += cfg.fTarSpellPene * tarAtt.spellPene();
		
		switch(cfg.calType)
		{
		case SkillEffCfg.CAL_REAL:
			//VA = VA;
			break;
		case SkillEffCfg.CAL_PHYSIC:
			VA = calculatePhysic(VA, userAtt, tarAtt);
			break;
		case SkillEffCfg.CAL_MAGIC:
			VA = calculateMagic(VA, userAtt, tarAtt);
			break;
		}
		
		double oper = 0;
		switch(cfg.effType)
		{
		case SkillEffCfg.EFF_HARM:
			if(VA <= 0) { VA = 1; }
			oper = -1;
			break;
		case SkillEffCfg.EFF_CURE:
			if(VA <= 0) { VA = 1; }
			oper = 1;
			break;
		}
		
		if( StAttributes.TYPE_HP.equals(cfg.attriType) )
		{
			tarAtt.changeCurHp((int)(oper*VA));	
		}
		else if( StAttributes.TYPE_MP.equals(cfg.attriType) )
		{
			tarAtt.changeCurMp((int)(oper*VA));	
		}
		
		if(cfg.effType == SkillEffCfg.EFF_HARM)
		{
			FightManager.handleHurt(tar, user);
		}
		
		return true;
	}
	
	private static double calculatePhysic(double srcHurt, AttriSet attacker, AttriSet target)
	{
		double defense = Math.max(0, (target.armor()-attacker.armorPene()));
		double hurt = ( srcHurt / (defense/100 + 1) );
		return hurt;
	}
	
	private static double calculateMagic(double srcHurt, AttriSet attacker, AttriSet target)
	{
		double defense = Math.max(0, (target.spellImmun()-attacker.spellPene()));
		double hurt = ( srcHurt / (defense/100 + 1) );
		return hurt;
	}
	
}
