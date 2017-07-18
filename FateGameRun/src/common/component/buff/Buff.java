package common.component.buff;

import java.util.List;

import cfg.BuffCfg;
import message.buff.BuffMsg;
import common.component.player.AttriSet;
import common.struct.buff.StBuff;
import game.core.sprite.Creature;
import game.core.sprite.Player;
import game.fight.FightManager;
import game.player.PlayerManager;
import game.scene.SceneManager;
import utils.Utils;

public class Buff extends StBuff
{
	/** 是否是PlanObject,如果是的话,仅仅存储数据,而不执行任何逻辑  */
	protected boolean isPO = true;
	
	/** 来源对象 */
	public Creature source;
	/** 目标对象 */
	public Creature target;
	/** Buff的配置 */
	public BuffCfg config;
	/** 所属Buff组 */
	public BuffSet group;
	
	public Buff()
	{
	}
	
	/** 标准设置Buff */
	public void setup(BuffCfg config, Creature source, Creature target, BuffSet group)
	{
		this.isPO = false;
		this.id = Utils.createTidInt();
		this.config = config;
		this.source = source;
		this.target = target;
		this.group = group;
		
		this.cfgId = config.id;
		if(source!=null)this.sourceTid = source.tid;
		this.targetTid = target.tid;
		this.destroyTime = Utils.now() + config.duration;
		this.readyTime = Utils.now() + config.delay;
	}
	
	/** 标准设置Buff */
	public void setup(int cfgId, int sourceTid, int targetTid, BuffSet group)
	{
		this.isPO = false;
		this.id = Utils.createTidInt();
		this.cfgId = cfgId;
		this.sourceTid = sourceTid;
		this.targetTid = targetTid;
		this.group = group;
		
		this.config = BuffCfg.get(cfgId);
		this.source = SceneManager.getCreature(sourceTid);
		this.target = SceneManager.getCreature(targetTid);
		this.destroyTime = Utils.now() + config.duration;
		this.readyTime = Utils.now() + config.delay;
	}
	
	/** 标准设置Buff */
	public void setup(int cfgId, Creature self)
	{
		this.isPO = false;
		this.id = Utils.createTidInt();
		this.cfgId = cfgId;
		this.group = self.buffs;
		this.sourceTid = this.targetTid = self.tid;
		this.source = this.target = self;
		this.config = BuffCfg.get(cfgId);
		this.destroyTime = Utils.now() + config.duration;
		this.readyTime = Utils.now() + config.delay;
	}
	
	/** 是否能被作用(use前判断,false就不能use) */
	public boolean isAvailable()
	{
		if(null == source) { return false; }
		if(null == target) { return false; }
		if(config.type == BuffCfg.TYPE_IMM)
		{
			return (curRunTimes <= 0);
		}
		else if(config.type == BuffCfg.TYPE_GRAD)
		{
			if(Utils.now() < readyTime) { return false; }
			return true;
		}
		return false;
	}
	
	/** 尝试运行
	 * @return 是否运行了 */
	public boolean tryRun()
	{
		if(!isAvailable())
		{
			return false;
		}
		run();
		runned();
		return true;
	}
	
	/** 作用 */
	public void run()
	{
		AttriSet tao = this.target.attris;
		
		int iv = 0;
		float fv = 0;
		
		//curHp
		if(config.ratCurHp != 0)
		{
			iv = (int)( tao.curHp()*config.ratCurHp );
			tao.changeCurHp(iv);
			FightManager.handleHurt(target, source);
		}
		if(config.absCurHp != 0)
		{
			iv = (int)( config.absCurHp );
			tao.changeCurHp(iv);
			FightManager.handleHurt(target, source);
		}
		//curMp
		if(config.ratCurMp != 0)
		{
			iv = (int)( tao.curMp()*config.ratCurMp );
			tao.changeCurMp(iv);
		}
		if(config.absCurMp != 0)
		{
			iv = (int)( config.absCurMp );
			tao.changeCurMp(iv);
		}
		//hp
		if(config.ratHp != 0)
		{
			iv = (int)( tao.hp()*config.ratHp );
			tao.changeHp(iv, AttriSet.SRC_BUFF);
			deltHp += iv;
		}
		if(config.absHp != 0)
		{
			iv = (int)( config.absHp );
			tao.changeHp(iv, AttriSet.SRC_BUFF);
			deltHp += iv;
		}
		//mp
		if(config.ratMp != 0)
		{
			iv = (int)( tao.mp()*config.ratMp );
			tao.changeMp(iv, AttriSet.SRC_BUFF);
			deltMp += iv;
		}
		if(config.absMp != 0)
		{
			iv = (int)( config.absMp );
			tao.changeMp(iv, AttriSet.SRC_BUFF);
			deltMp += iv;
		}
		//moveSpeed
		if(config.ratMoveSpeed != 0)
		{
			iv = (int)( tao.moveSpeed()*config.ratMoveSpeed );
			tao.changeMoveSpeed(iv, AttriSet.SRC_BUFF);
			deltMoveSpeed += iv;
		}
		if(config.absMoveSpeed != 0)
		{
			iv = (int)( config.absMoveSpeed );
			tao.changeMoveSpeed(iv, AttriSet.SRC_BUFF);
			deltMoveSpeed += iv;
		}
		//attack
		if(config.ratAttack != 0)
		{
			iv = (int)( tao.attack()*config.ratAttack );
			tao.changeAttack(iv, AttriSet.SRC_BUFF);
			deltAttack += iv;
		}
		if(config.absAttack != 0)
		{
			iv = (int)( config.absAttack );
			tao.changeAttack(iv, AttriSet.SRC_BUFF);
			deltAttack += iv;
		}
		//attackSpeed
		if(config.ratAttackSpeed != 0)
		{
			fv = (float)( tao.attackSpeed()*config.ratAttackSpeed );
			tao.changeAttackSpeed(fv, AttriSet.SRC_BUFF);
			deltAttackSpeed += fv;
		}
		if(config.absAttackSpeed != 0)
		{
			fv = (float)( config.absAttackSpeed );
			tao.changeAttackSpeed(fv, AttriSet.SRC_BUFF);
			deltAttackSpeed += fv;
		}
		//armor
		if(config.ratArmor != 0)
		{
			iv = (int)( tao.armor()*config.ratArmor );
			tao.changeArmor(iv, AttriSet.SRC_BUFF);
			deltArmor += iv;
		}
		if(config.absArmor != 0)
		{
			iv = (int)( config.absArmor );
			tao.changeArmor(iv, AttriSet.SRC_BUFF);
			deltArmor += iv;
		}
		//armorPene
		if(config.ratArmorPene != 0)
		{
			iv = (int)( tao.armorPene()*config.ratArmorPene );
			tao.changeArmorPene(iv, AttriSet.SRC_BUFF);
			deltArmorPene += iv;
		}
		if(config.absArmorPene != 0)
		{
			iv = (int)( config.absArmorPene );
			tao.changeArmorPene(iv, AttriSet.SRC_BUFF);
			deltArmorPene += iv;
		}
		//spellPower
		if(config.ratSpellPower != 0)
		{
			iv = (int)( tao.spellPower()*config.ratSpellPower );
			tao.changeSpellPower(iv, AttriSet.SRC_BUFF);
			deltSpellPower += iv;
		}
		if(config.absSpellPower != 0)
		{
			iv = (int)( config.absSpellPower );
			tao.changeSpellPower(iv, AttriSet.SRC_BUFF);
			deltSpellPower += iv;
		}
		//spellImmun
		if(config.ratSpellImmun != 0)
		{
			iv = (int)( tao.spellImmun()*config.ratSpellImmun );
			tao.changeSpellImmun(iv, AttriSet.SRC_BUFF);
			deltSpellImmun += iv;
		}
		if(config.absSpellImmun != 0)
		{
			iv = (int)( config.absSpellImmun );
			tao.changeSpellImmun(iv, AttriSet.SRC_BUFF);
			deltSpellImmun += iv;
		}
		//spellPene
		if(config.ratSpellPene != 0)
		{
			iv = (int)( tao.spellPene()*config.ratSpellPene );
			tao.changeSpellPene(iv, AttriSet.SRC_BUFF);
			deltSpellPene += iv;
		}
		if(config.absSpellPene != 0)
		{
			iv = (int)( config.absSpellPene );
			tao.changeSpellPene(iv, AttriSet.SRC_BUFF);
			deltSpellPene += iv;
		}
		
		broadcastBuffUse();
	}
	
	/** 成功运行一次之后的处理 */
	protected void runned()
	{
		readyTime = Utils.now() + config.interval;
		curRunTimes++;
	}
	
	/** buff作用通知处理 */
	protected void broadcastBuffUse()
	{
		List<Player> players = this.target.getNear(true);
		for(Player player : players)
		{
			BuffMsg.getInstance().sendBuffUse_G2C(this, player.getBundle().getUid());
		}
	}
	
	/** 是否已废弃(run后判断,true就该移除) */
	public boolean isInvalid()
	{
		return Utils.now() > destroyTime;
	}
	
	/** 被销毁 */
	public void destroy()
	{
		AttriSet tao = this.target.attris;
		tao.changeHp(-deltHp, AttriSet.SRC_BUFF);
		tao.changeMp(-deltMp, AttriSet.SRC_BUFF);
		tao.changeMoveSpeed(-deltMoveSpeed, AttriSet.SRC_BUFF);
		tao.changeAttack(-deltAttack, AttriSet.SRC_BUFF);
		tao.changeAttackSpeed(-deltAttackSpeed, AttriSet.SRC_BUFF);
		tao.changeArmor(-deltArmor, AttriSet.SRC_BUFF);
		tao.changeArmorPene(-deltArmorPene, AttriSet.SRC_BUFF);
		tao.changeSpellPower(-deltSpellPower, AttriSet.SRC_BUFF);
		tao.changeSpellImmun(-deltSpellImmun, AttriSet.SRC_BUFF);
		tao.changeSpellPene(-deltSpellPene, AttriSet.SRC_BUFF);
	}
	

	private void build()
	{
		this.config = BuffCfg.get(cfgId);
		this.source = SceneManager.getCreature(sourceTid);
	}
	
	/** 导入结构数据 */
	public void importData(StBuff data)
	{
		this.id = data.id;
		this.sourceTid = data.sourceTid;
		this.targetTid = data.targetTid;
		this.cfgId = data.cfgId;
		this.curRunTimes = data.curRunTimes;
		this.readyTime = data.readyTime;
		this.destroyTime = data.destroyTime;
		
		this.deltHp = data.deltHp;
		this.deltMp = data.deltMp;
		this.deltMoveSpeed = data.deltMoveSpeed;
		this.deltAttack = data.deltAttack;
		this.deltAttackSpeed = data.deltAttackSpeed;
		this.deltArmor = data.deltArmor;
		this.deltArmorPene = data.deltArmorPene;
		this.deltSpellPower = data.deltSpellPower;
		this.deltSpellImmun = data.deltSpellImmun;
		this.deltSpellPene = data.deltSpellPene;
		
		build();
	}
	
	/** 导出结构数据 */
	public StBuff exportData()
	{
		StBuff data = new StBuff();
		data.id = this.id;
		data.sourceTid = this.sourceTid;
		data.targetTid = this.targetTid;
		data.cfgId = this.cfgId;
		data.curRunTimes = this.curRunTimes;
		data.readyTime = this.readyTime;
		data.destroyTime = this.destroyTime;
		
		data.deltHp = this.deltHp;
		data.deltMp = this.deltMp;
		data.deltMoveSpeed = this.deltMoveSpeed;
		data.deltAttack = this.deltAttack;
		data.deltAttackSpeed = this.deltAttackSpeed;
		data.deltArmor = this.deltArmor;
		data.deltArmorPene = this.deltArmorPene;
		data.deltSpellPower = this.deltSpellPower;
		data.deltSpellImmun = this.deltSpellImmun;
		data.deltSpellPene = this.deltSpellPene;
		return data;
	}
	
}
