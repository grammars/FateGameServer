package game;

import common.beans.PlayerBean;
import message.player.PlayerDataMsg;
import message.player.PlayerEventMsg;
import framework.ClientBundle;
import game.core.sprite.Player;
import game.player.PlayerManager;
import game.scene.Scene;
import game.scene.SceneManager;

public class GameClientBundle extends ClientBundle
{
	public Player player = new Player();
	
	public GameClientBundle(Long uid)
	{
		super(uid);
		player.setBundle(this);
	}
	
	/** 初始化 */
	public void initialize()
	{
		Global.addBundle(player.uid, this);
	}
	
	/** 进入游戏场景 */
	public void enter()
	{
		Scene scene = SceneManager.getScene(player.mapId);
		scene.addSprite(player);
		
		PlayerManager.tellInit(player);
	}
	
	@Override
	/** 释放，并做下线处理 */
	public void dispose()
	{
		super.dispose();
		Global.removeBundle(player.uid);
		Scene scene = SceneManager.getScene(player.mapId);
		scene.removeSprite(player);
		player.offline();
		PlayerBean bean = new PlayerBean();
		bean.learn(player);
		PlayerDataMsg.getInstance().sendPlayerDataSaveReq_G2D(bean, this.uid);
		PlayerEventMsg.getInstance().sendPlayerKicklineReq_G2G(this.uid);
		player.dispose();
		player = null;
	}
}
