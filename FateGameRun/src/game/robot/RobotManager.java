package game.robot;

import cfg.MonsterInfoCfg;
import game.core.sprite.Monster;
import game.scene.Scene;
import game.scene.SceneManager;

public class RobotManager
{
	/** 创建一个机器人并放入场景 */
	public static void createRobot(long clientBundleId)
	{
		Scene scene = SceneManager.getScene(1001);
		MonsterInfoCfg monsterCfg = MonsterInfoCfg.get(8008777);
		Monster monster = new Monster();
		monster.setParent(null);
		monster.setConfig(monsterCfg);
		monster.mapId = 8008777;
		int offsetX = (int)(Math.random() * 20);
		int offsetY = (int)(Math.random() * 20);
		int bornX = 34 + offsetX;
		int bornY = 46 + offsetY;
		monster.setBornX(bornX);
		monster.setBornY(bornY);
		byte dir = (byte)Math.floor((Math.random() * 8));
		monster.direction = dir;
		scene.addSprite(monster);
	}
}
