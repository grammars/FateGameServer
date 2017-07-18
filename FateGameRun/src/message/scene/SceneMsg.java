package message.scene;

import java.util.Iterator;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

import ds.geom.Point;
import framework.ClientBundleManager;
import framework.Log;
import framework.MsgBuffer;
import framework.SessHand;
import framework.define.SidType;
import framework.net.IBT;
import game.GameClientBundle;
import game.core.sprite.Player;
import game.core.sprite.Sprite;
import game.scene.Scene;
import game.scene.SceneManager;
import message.IMessage;
import message.MessageId;

public class SceneMsg implements IMessage
{
	private static SceneMsg instance;
	public static SceneMsg getInstance()
	{
		if(instance == null) { instance = new SceneMsg(); }
		return instance;
	}
	
	/** [场景]主消息号 */
	public static final int MID = MessageId.SCENE_MID;
	
	/** client向game请求场景信息 */
	private static final int SCENE_INFO_REQ_C2G = 1;
	/** game向client发送场景信息 */
	private static final int SCENE_INFO_RPL_G2C = 2;
	/** game向client发送添加sprite */
	private static final int SCENE_ADD_SPRITE_G2C = 3;
	/** game向client发送移除sprite */
	private static final int SCENE_REMOVE_SPRITE_G2C = 4;
	/** game向client发送添加多个sprite */
	private static final int SCENE_ADD_SPRITES_G2C = 5;
	/** game向client发送移除多个sprite */
	private static final int SCENE_REMOVE_SPRITES_G2C = 6;
	/** client向game发送移动路径 */
	private static final int PLAYER_MOVE_PATH_C2G = 7;
	/** client向game发送移动单步 */
	private static final int PLAYER_MOVE_STEP_C2G = 8;
	/** client向game发送移动止步 */
	private static final int PLAYER_MOVE_STOP_C2G = 12;
	/** game向client发送Sprite移动路径 */
	private static final int SPRITE_MOVE_PATH_G2C = 9;
	/** game向client发送Sprite移动到指定点 */
	private static final int SPRITE_MOVE_TO_POINT_G2C = 19;
	/** game向client发送玩家移动退回 */
	private static final int PLAYER_MOVE_BACK_G2C = 10;
	/** game向client发送Sprite移动止步 */
	private static final int SPRITE_MOVE_STOP_G2C = 13;
	/** game向client发送玩家场景切换 */
	private static final int SCENE_CHANGE_MAP_G2C = 11;
	/** game向client发送Sprite的selfLook改变 */
	private static final int SELF_LOOK_CHANGE_G2C = 20;
	/** game向client发送Sprite的weaponLook改变 */
	private static final int WEAPON_LOOK_CHANGE_G2C = 21;
	
	@Override
	public void recv(SessHand serverSH, int subMid, IoBuffer buffer, long clientBundleId)
	{
		GameClientBundle bundle = (GameClientBundle)ClientBundleManager.getInstance().getBundle(clientBundleId);
		if(bundle == null)
		{
			Log.game.error("recvSceneInfoReq_C2G:: bundle lost");
			return;
		}
		switch(subMid)
		{
		case SCENE_INFO_REQ_C2G:
			recvSceneInfoReq_C2G(buffer, bundle.player);
			break;
		case PLAYER_MOVE_PATH_C2G:
			recvPlayerMovePath_C2G(buffer, bundle.player);
			break;
		case PLAYER_MOVE_STEP_C2G:
			recvPlayerMoveStep_C2G(buffer, bundle.player);
			break;
		case PLAYER_MOVE_STOP_C2G:
			recvPlayerMoveStop_C2G(buffer, bundle.player);
			break;
		}
	}

	/** recv( client向game请求场景信息 ) */
	private void recvSceneInfoReq_C2G(IoBuffer buffer, Player player)
	{
		Log.game.debug("client向game请求场景信息");
		
		Scene scene = SceneManager.getScene(player.mapId);
		if(scene == null)
		{
			Log.game.error("recvSceneInfoReq_C2G:: scene == null");
			return;
		}
		
		sendSceneInfoRpl_G2C(scene, player, player.getBundle().getUid());
		player.getBundle().enter();
	}
	
	/** send( game向client发送场景信息 ) */
	public void sendSceneInfoRpl_G2C(Scene scene, Player player, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, SCENE_INFO_RPL_G2C, SidType.CLIENT, clientBundleId);
		player.writeAvatar(msg.buffer);
		scene.writeSceneInfo(msg.buffer);
		msg.send();
	}
	
	/** game向client发送添加sprite */
	public void sendSceneAddSprite_G2C(long clientBundleId, Sprite sprite)
	{
		MsgBuffer msg = new MsgBuffer(MID, SCENE_ADD_SPRITE_G2C, SidType.CLIENT, clientBundleId);
		SceneMsgUtils.writeSprite(msg.buffer, sprite);
		msg.send();
	}

	/** game向client发送移除sprite */
	public void sendSceneRemoveSprite_G2C(long clientBundleId, int spriteId)
	{
		MsgBuffer msg = new MsgBuffer(MID, SCENE_REMOVE_SPRITE_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, spriteId);
		msg.send();
	}
	
	/** game向client发送添加多个sprite */
	public void sendSceneAddSprites_G2C(long clientBundleId, List<Sprite> sprites)
	{
		MsgBuffer msg = new MsgBuffer(MID, SCENE_ADD_SPRITES_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, sprites.size());
		Iterator<Sprite> iter = sprites.iterator();
		while(iter.hasNext())
		{
			Sprite sp = iter.next();
			SceneMsgUtils.writeSprite(msg.buffer, sp);
		}
		msg.send();
	}
	
	/** game向client发送移除多个sprite */
	public void sendSceneRemoveSprites_G2C(long clientBundleId, List<Integer> tids)
	{
		MsgBuffer msg = new MsgBuffer(MID, SCENE_REMOVE_SPRITES_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, tids.size());
		Iterator<Integer> iter = tids.iterator();
		while(iter.hasNext())
		{
			IBT.writeInt(msg, iter.next());
		}
		msg.send();
	}
	
	/** recv( client向game发送移动路径 ) */
	private void recvPlayerMovePath_C2G(IoBuffer buffer, Player player)
	{
		int pathSize = IBT.readInt(buffer);
		Point[] path = new Point[pathSize];
		//String debugStr = "[MovePath]";
		for(int i = 0; i < pathSize; i++)
		{
			path[i] = new Point(IBT.readInt(buffer), IBT.readInt(buffer));
			//debugStr += path[i] + ", ";
		}
		//System.out.println(debugStr);
		player.setMovePath(path);
	}
	
	/** recv( client向game发送移动单步 ) */
	private void recvPlayerMoveStep_C2G(IoBuffer buffer, Player player)
	{
		player.setMoveStep(IBT.readInt(buffer), IBT.readInt(buffer));
	}
	
	/** recv( client向game发送移动止步 ) */
	private void recvPlayerMoveStop_C2G(IoBuffer buffer, Player player)
	{
		player.stopMove(IBT.readInt(buffer), IBT.readInt(buffer));
	}
	
	/** send( game向client发送Sprite移动止步 ) */
	public void sendSpriteMoveStop_G2C(Sprite stoper, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, SPRITE_MOVE_STOP_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, stoper.tid);
		IBT.writeInt(msg, stoper.x);
		IBT.writeInt(msg, stoper.y);
		msg.send();
	}
	
	/** send( game向client发送Sprite移动路径 )
	 * @param path 移动路径
	 * @param mover 移动的对象
	 * @param clientBundleId 接收者的bundleId */
	public void sendSpriteMovePath_G2C(Point[] path, Sprite mover, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, SPRITE_MOVE_PATH_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, mover.tid);
		IBT.writeInt(msg, path.length);
		for(int i = 0; i < path.length; i++)
		{
			Point pt = path[i];
			IBT.writeInt(msg, pt.x);
			IBT.writeInt(msg, pt.y);
		}
		msg.send();
	}
	
	/** send( game向client发送Sprite移动到指定点 ) */
	public void sendSpriteMoveToPoint_G2C(int px, int py, Sprite mover, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, SPRITE_MOVE_TO_POINT_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, mover.tid);
		IBT.writeInt(msg, px);
		IBT.writeInt(msg, py);
		msg.send();
	}
	
	/** send( game向client发送玩家移动退回 ) */
	public void sendPlayerMoveBack_G2C(int px, int py, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, PLAYER_MOVE_BACK_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, px);
		IBT.writeInt(msg, py);
		msg.send();
	}
	
	/** send( game向client发送玩家场景切换 ) */
	public void sendSceneChangeMap_G2C(Scene scene, int toX, int toY, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, SCENE_CHANGE_MAP_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, toX);
		IBT.writeInt(msg, toY);
		scene.writeSceneInfo(msg.buffer);
		msg.send();
	}
	
	/** game向client发送Sprite的selfLook改变 */
	public void sendSelfLookChange_G2C(int changerTid, String lookId, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, SELF_LOOK_CHANGE_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, changerTid);
		IBT.writeString(msg, lookId);
		msg.send();
	}
	
	/** game向client发送Sprite的weaponLook改变 */
	public void sendWeaponLookChange_G2C(int changerTid, String lookId, long clientBundleId)
	{
		MsgBuffer msg = new MsgBuffer(MID, WEAPON_LOOK_CHANGE_G2C, SidType.CLIENT, clientBundleId);
		IBT.writeInt(msg, changerTid);
		IBT.writeString(msg, lookId);
		msg.send();
	}
}
