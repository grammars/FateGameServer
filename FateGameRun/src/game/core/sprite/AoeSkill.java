package game.core.sprite;

import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

import framework.net.IBT;
import game.buff.BuffManager;
import game.fight.action.SkillEffAction;
import utils.Utils;
import common.component.skill.SkillItem;
import common.define.SpriteType;
import ds.geom.Point;

public class AoeSkill extends Sprite
{
	/** 关联的技能信息 */
	public SkillItem skillItem = new SkillItem();
	
	/** 施放者 */
	private Creature user;
	/** 剩余作用次数 */
	private int leftUseTimes = 0;
	/** 下一次作用时间 */
	private long nextUseTime = 0;
	/** 从场景中移除的时间 */
	private long removeTime = 0;
	
	public AoeSkill()
	{
		super();
		this.type = SpriteType.AOE_SKILL;
	}
	
	@Override
	protected void createParts()
	{
		super.createParts();
	}
	
	public void setup(Creature user, SkillItem si)
	{
		this.user = user;
		this.skillItem = si;
		this.leftUseTimes = si.config.extAoe.aoeUseTimes;
		this.nextUseTime = Utils.now() + si.config.extAoe.aoeUseDelay;
		this.removeTime = Utils.now() + si.config.extAoe.aoeLifeTime;
	}
	
	@Override
	public void update()
	{
		super.update();
		
		if(leftUseTimes > 0 && Utils.now() > nextUseTime)
		{
			use();
			leftUseTimes--;
			nextUseTime = Utils.now() + skillItem.config.extAoe.aoeUseInterval;
		}
		
		if(Utils.now() > removeTime)
		{
			this.scene.removeSprite(this);
			dispose();
		}
	}
	
	private void use()
	{
		System.out.println("AoeSkill::use");
		SkillItem si = this.skillItem;
		Point[] range = si.config.extAoe.range;
		for(int i = 0; i < range.length; i++)
		{
			Point pt = range[i];
			int usePtX = this.x + pt.x;
			int usePtY = this.y + pt.y;
			List<Sprite> sprites = this.scene.getSpritesOnPoint(usePtX, usePtY);
			for(Sprite sp : sprites)
			{
				if( sp == user ) { continue; }
				if( sp.type==SpriteType.PLAYER || sp.type==SpriteType.MONSTER )
				{	
					SkillEffAction action = new SkillEffAction();
					action.init(si, user, sp.toCreature());
					action.setup(0, 0, 1);
					sp.pushAction(action);
					
					BuffManager.applyBuff(si, user, sp.toCreature());
				}
			}
		}
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
	}
	
	@Override
	public void writeAvatar(IoBuffer buffer)
	{
		super.writeAvatar(buffer);
		IBT.writeInt(buffer, skillItem.id);
	}
	
}
