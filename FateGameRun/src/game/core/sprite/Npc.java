package game.core.sprite;

import java.util.ArrayList;
import java.util.List;

import framework.net.IBT;
import game.scene.Scene;

import org.apache.mina.core.buffer.IoBuffer;

import cfg.NpcInfoCfg;
import cfg.TaskCfg;
import common.define.SpriteType;

public class Npc extends Creature
{
	protected NpcInfoCfg config;
	public void setConfig(NpcInfoCfg value)
	{
		this.config = value;
		this.name = config.name;
		this.look = config.look;
		this.mapId = config.mapId;
		this.x = config.mapX;
		this.y = config.mapY;
		this.direction = config.direction;
	}
	public NpcInfoCfg getConfig() { return this.config; }
	
	/** 负责玩家接取的任务 */
	public List<TaskCfg> begTasks = new ArrayList<>();
	/** 负责玩家交掉的任务 */
	public List<TaskCfg> endTasks = new ArrayList<>();
	
	public Npc()
	{
		super();
		this.type = SpriteType.NPC;
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
		
		ai.addScene(scene);
	}
	
	@Override
	public void removeScene(Scene scene)
	{
		super.removeScene(scene);
		
		ai.removeScene(scene);
	}
	
	@Override
	public void update()
	{
		super.update();
		
		ai.update();
	}
	
	@Override
	public void dead()
	{
		super.dead();
		
		ai.dead();
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		
		ai.dispose();
	}
	
	@Override
	public void writeAvatar(IoBuffer buffer)
	{
		super.writeAvatar(buffer);
		IBT.writeInt(buffer, config.id);
	}
}
