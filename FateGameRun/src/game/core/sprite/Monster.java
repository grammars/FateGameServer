package game.core.sprite;

import org.apache.mina.core.buffer.IoBuffer;

import utils.Utils;
import game.ai.AIFactory;
import game.scene.Scene;
import game.scene.monster.MonsterPool;
import cfg.MonsterInfoCfg;
import common.define.SpriteType;

public class Monster extends Creature
{
	/** 所属怪物池 */
	protected MonsterPool parent;
	public void setParent(MonsterPool value)
	{
		this.parent = value;
	}
	public MonsterPool getParent() { return this.parent; }
	
	protected MonsterInfoCfg config;
	public void setConfig(MonsterInfoCfg value)
	{
		this.config = value;
		this.name = config.name;
		this.sex = config.sex;
		this.voc = config.voc;
		this.level = config.level;
		this.look = config.look;
		this.skills.setup(config.skillIds, config.skillLevels);
		this.attris.base.copy(config.attri);
		this.attris.recover(false);
		
		this.ai = AIFactory.create(AIFactory.T_WALKER, this);//T_PASSIVE_FIGHT_MONSTER
	}
	public MonsterInfoCfg getConfig() { return this.config; }
	
	/** 出生点坐标x */
	protected int bornX = 0;
	public void setBornX(int value)
	{
		this.bornX = value;
		this.x = bornX;
	}
	public int getBornX() { return this.bornX; }
	
	/** 出生点坐标y */
	protected int bornY = 0;
	public void setBornY(int value)
	{
		this.bornY = value;
		this.y = bornY;
	}
	public int getBornY() { return this.bornY; }
	
	/** 移除日期 */
	private long removeTime = -1;
	
	protected Creature fightTar;
	/** 设置战斗目标 */
	public void setFightTar(Creature value)
	{
		this.fightTar = value;
	}
	/** 获取战斗目标 */
	public Creature getFightTar() { return this.fightTar; }
	
	/** 是否已做了死亡掉落 */
	public boolean deathDropped = false;
	
	public Monster()
	{
		super();
		this.type = SpriteType.MONSTER;
	}
	
	@Override
	protected void createParts()
	{
		super.createParts();
	}
	
	@Override
	public void addScene(Scene scene)
	{
		super.addScene(scene);
		
		//walkAroundAction.start();
		ai.addScene(scene);
	}
	
	@Override
	public void removeScene(Scene scene)
	{
		super.removeScene(scene);
		
		//walkAroundAction.stop();
		ai.removeScene(scene);
	}
	
	@Override
	public void update()
	{
		super.update();
		
		if(removeTime > 0 && Utils.now() > removeTime)
		{
			if(null != this.scene) { this.scene.removeSprite(this); }
			dispose();
		}
		ai.update();
	}
	
	@Override
	public void dead()
	{
		super.dead();
		
		removeTime = Utils.now() + 3000;
		ai.dead();
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		
		this.parent.decreaseActive();
		ai.dispose();
	}
	
	@Override
	public void writeAvatar(IoBuffer buffer)
	{
		super.writeAvatar(buffer);
	}
	
}
