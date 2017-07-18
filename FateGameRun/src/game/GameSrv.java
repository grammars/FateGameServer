package game;

import cfg.CfgManager;
import message.buff.BuffMsg;
import message.common.CommonMsg;
import message.creature.CreatureMsg;
import message.fight.FightMsg;
import message.gm.GmMsg;
import message.goods.GoodsMsg;
import message.login.LoginMsg;
import message.npc.NpcMsg;
import message.player.PlayerDataMsg;
import message.player.PlayerEventMsg;
import message.practice.PracticeMsg;
import message.robot.RobotMsg;
import message.scene.SceneMsg;
import message.skill.SkillMsg;
import message.task.TaskMsg;
import framework.AbsSrv;
import framework.AppContext;
import framework.SConnector;
import framework.define.SidType;
import game.npc.NpcManager;
import game.scene.SceneManager;

public class GameSrv extends AbsSrv
{
	private static GameSrv instance;
	public static GameSrv getInstance()
	{
		if(instance == null) { instance = new GameSrv(); }
		return instance;
	}
	
	private SConnector connData;
	
	public GameSrv()
	{
		super(SidType.GAME);
	}
	
	@Override
	public void start()
	{
		AppContext.setSessManager(GameSessManager.getInstance());
		super.start();
		CfgManager.load();
		SceneManager.start();
		NpcManager.start();
		connData = new SConnector(SidType.GAME, SidType.DATA);
		connData.start();
	}
	
	@Override
	protected void initMsgHandler()
	{
		super.initMsgHandler();
		AppContext.addMsgHandler(LoginMsg.MID, LoginMsg.getInstance());
		AppContext.addMsgHandler(PlayerDataMsg.MID, PlayerDataMsg.getInstance());
		AppContext.addMsgHandler(SceneMsg.MID, SceneMsg.getInstance());
		AppContext.addMsgHandler(PlayerEventMsg.MID, PlayerEventMsg.getInstance());
		AppContext.addMsgHandler(FightMsg.MID, FightMsg.getInstance());
		AppContext.addMsgHandler(CreatureMsg.MID, CreatureMsg.getInstance());
		AppContext.addMsgHandler(BuffMsg.MID, BuffMsg.getInstance());
		AppContext.addMsgHandler(GoodsMsg.MID, GoodsMsg.getInstance());
		AppContext.addMsgHandler(SkillMsg.MID, SkillMsg.getInstance());
		AppContext.addMsgHandler(PracticeMsg.MID, PracticeMsg.getInstance());
		AppContext.addMsgHandler(TaskMsg.MID, TaskMsg.getInstance());
		AppContext.addMsgHandler(NpcMsg.MID, NpcMsg.getInstance());
		AppContext.addMsgHandler(CommonMsg.MID, CommonMsg.getInstance());
		AppContext.addMsgHandler(GmMsg.MID, GmMsg.getInstance());
		AppContext.addMsgHandler(RobotMsg.MID, RobotMsg.getInstance());
	}
	
	@Override
	public void stop()
	{
		super.stop();
		connData.stop();
		SceneManager.stop();
	}

}
