package game.fight.aim;

import framework.Log;
import game.buff.BuffManager;
import game.core.sprite.Creature;
import game.core.sprite.Player;
import game.family.FamilyManager;
import game.fight.action.SkillEffAction;
import game.fight.formula.SkillCostForm;
import game.fight.law.FightLaw;
import game.friend.FriendManager;
import game.scene.District;
import game.skill.SkillManager;

import java.util.ArrayList;
import java.util.List;

import cfg.SkillCfg;
import message.fight.FightMsg;
import utils.GeomUtils;
import common.component.cd.CDUnit;
import common.component.skill.SkillItem;
import common.define.PkModeType;
import common.define.SpriteType;

public class AimSkillHandler
{
	public AimSkillHandler()
	{
		//
	}
	
	private boolean available(Creature user, int skillId)
	{
		if(user.type == SpriteType.PLAYER)
		{
			Player player = user.toPlayer();
			boolean ok = player.cd.available(CDUnit.T_SKILL, skillId);
			if(!ok)
			{
				player.alert("服务器拒绝技能释放，CD未到");
			}
			return ok;
		}
		return true;
	}
	
	public void prepare(Creature user, int skillId)
	{
		if( user == null ) { return; }
		SkillItem si = user.skills.getItem(skillId);
		if(! SkillCostForm.cost(false, user, si, 1))
		{
			SkillManager.sendUnavailableNotice(user);
			return;
		}
		List<Player> players = user.getNear(false);
		for(Player player : players)
		{
			FightMsg.getInstance().sendCretSkillAimPrepare_G2C(user.tid, user.direction, player.getBundle().getUid());
		}
	}
	
	/** 找个适合的作用目标 */
	private List<Creature> findTargets(Creature user, int tarTid, SkillItem si)
	{
		List<Creature> arr = new ArrayList<Creature>();
		
		Creature target = null;
		if(si.config.extAim.aimSelf)
		{
			target = user;
		}
		else
		{
			target = user.getScene().getCreature(tarTid);
		}
		
		final int R = si.config.extAim.aimRange;
		List<Creature> range = user.getScene().getCreaturesInRect(target.x-R, target.y-R, R*2+1, R*2+1);
		
		for(int i = 0; i < range.size(); i++)
		{
			Creature cret = range.get(i);
			if( FightLaw.skillRelationAvailable(user, cret, si, false) )
			{
				arr.add(cret);
			}
		}
		
		List<Creature> fits = new ArrayList<>();
		fits.add(target);
		int leftNum = Math.min(arr.size()-1, si.config.extAim.aimMaxCount-1);
		for(int i = 0; i < leftNum; i++)
		{
			Creature candidate = arr.get(i);
			if(candidate != target)
			{
				fits.add(candidate);
			}
		}
		
		return fits;
	}
	
	public void execute(Creature user, int tarTid, int skillId)
	{
		if( user == null ) { return; }
		if( !available(user, skillId) ) { return; }
		
		SkillItem si = user.skills.getItem(skillId);
		
		if( !FightLaw.skillRelationAvailable(user, user.getScene().getCreature(tarTid), si, true) ) { return; }
		
		List<Creature> targets = findTargets(user, tarTid, si);
		
		//技能消耗
		if(!SkillCostForm.cost(true, user, si, targets.size()))
		{
			SkillManager.sendUnavailableNotice(user);
			return;
		}
		//技能CD
		if(user.type == SpriteType.PLAYER)
		{
			user.toPlayer().cd.setCD(CDUnit.T_SKILL, skillId, si.config.cdTime);
		}
		
		final int NUM = targets.size();
		int[] tarTids = new int[NUM];
		for(int i = 0; i < NUM; i++)
		{
			Creature target = targets.get(i);
			if(target == null) { continue; }
			tarTids[i] = target.tid;
			Log.game.debug(user.name + "使用技能" + si.config.name + " 去攻击" + target.tid);
			
			//给技能目标添加技效用
			double gridDist = GeomUtils.distance(user.x, user.y, target.x, target.y);
			int flyTime = (int)( gridDist*si.config.extAim.aimFlyGridTime );
			
			SkillEffAction action = new SkillEffAction();
			action.init(si, user, target);
			action.setup(flyTime, 0, 1);
			target.pushAction(action);
			
			BuffManager.applyBuff(si, user, target);
		}
		
		//通知客户端开始播放攻击动画
		List<Player> players = user.getNear(true);
		for(Player player : players)
		{
			FightMsg.getInstance().sendCretSkillAimExecute_G2C(user.tid, tarTids, skillId, player.getBundle().getUid());
		}
	}
	
}
