package com.lineage.server.model;

import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.utils.Teleportation;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ChangeName;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class L1Teleport
{
	private static final Log _log = LogFactory.getLog(L1Teleport.class);

	public static final int TELEPORT = 0;

	public static final int CHANGE_POSITION = 1;

	public static final int ADVANCED_MASS_TELEPORT = 2;

	public static final int CALL_CLAN = 3;

	public static final int[] EFFECT_SPR = { 169, 2235, 2236, 2281 };

	public static final int[] EFFECT_TIME = { 280, 440, 440, 1120 };

	private static final String _random = null;

	/**
	 * 傳送控制項
	 * 
	 * @param pc
	 *            執行的人物
	 * @param loc
	 *            座標
	 * @param head
	 *            面向
	 * @param effectable
	 *            是否產生動畫
	 */
	public static void teleport(L1PcInstance pc, L1Location loc, int head, boolean effectable) {
		teleport(pc, loc.getX(), loc.getY(), (short) loc.getMapId(), head, effectable, 0);
	}

	/**
	 * 傳送控制項
	 * 
	 * @param pc
	 *            執行的人物
	 * @param loc
	 *            座標
	 * @param head
	 *            面向
	 * @param effectable
	 *            是否產生動畫
	 * @param skillType
	 *            動畫的光 (0:白),(1:青),(2:赤),(3:緑)
	 */
	public static void teleport(L1PcInstance pc, L1Location loc, int head, boolean effectable, int skillType) {
		teleport(pc, loc.getX(), loc.getY(), (short) loc.getMapId(), head, effectable, skillType);
	}

	/**
	 * 傳送控制項
	 * 
	 * @param pc
	 *            執行的人物
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 * @param mapId
	 *            地圖編號
	 * @param head
	 *            面向
	 * @param effectable
	 *            是否產生動畫
	 */
	public static void teleport(L1PcInstance pc, int x, int y, short mapid, int head, boolean effectable) {
		teleport(pc, x, y, mapid, head, effectable, 0);
	}

	/**
	 * 傳送控制項
	 * 
	 * @param pc
	 *            執行的人物
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 * @param mapId
	 *            地圖編號
	 * @param head
	 *            面向
	 * @param effectable
	 *            是否產生動畫
	 * @param skillType
	 *            動畫的光 (0:白),(1:青),(2:赤),(3:緑)
	 */
	public static void teleport(L1PcInstance pc, int x, int y, short mapId, int head, boolean effectable,
			int skillType) {

		if ((effectable) && (skillType >= 0) && (skillType <= EFFECT_SPR.length)) {
			pc.sendPackets(new S_ChangeName(pc, false));
			S_SkillSound packet = new S_SkillSound(pc.getId(), EFFECT_SPR[skillType]);
			pc.sendPackets(packet);
			pc.broadcastPacketAll(packet);
			try {
				Thread.sleep((int) (EFFECT_TIME[skillType] * 0.7D));

			} catch (Exception localException) {
			}
		}

		pc.setTeleportX(x);
		pc.setTeleportY(y);
		pc.setTeleportMapId(mapId);
		pc.setTeleportHeading(head);

		Teleportation.teleportation(pc);
	}

	/**
	 * 召喚傳送術
	 * 
	 * @param cha
	 *            被召喚的目標
	 * @param target
	 *            召喚執行者
	 * @param distance
	 *            召喚至面前距離幾格位置
	 */
	public static void teleportToTargetFront(L1Character cha, L1Character target, int distance) {
		int locX = target.getX();
		int locY = target.getY();
		int heading = target.getHeading();
		L1Map map = target.getMap();
		short mapId = target.getMapId();

		switch (heading) {
		case 1:
			locX += distance;
			locY -= distance;
			break;
		case 2:
			locX += distance;
			break;
		case 3:
			locX += distance;
			locY += distance;
			break;
		case 4:
			locY += distance;
			break;
		case 5:
			locX -= distance;
			locY += distance;
			break;
		case 6:
			locX -= distance;
			break;
		case 7:
			locX -= distance;
			locY -= distance;
			break;
		case 0:
			locY -= distance;
			break;
		}

		if (map.isPassable(locX, locY, null)) {// 座標可通行
			if (cha instanceof L1PcInstance) {// 被召喚的是PC
				teleport((L1PcInstance) cha, locX, locY, mapId, cha.getHeading(), true, 1);

			} else if (cha instanceof L1NpcInstance) {// 被召喚的是NPC
				((L1NpcInstance) cha).teleport(locX, locY, cha.getHeading());
			}
		} else {// 座標不可通行
			L1Location newloc = target.getLocation().randomLocation(distance, false);// 周圍隨機座標
			if (cha instanceof L1PcInstance) {// 被召喚的是PC
				teleport((L1PcInstance) cha, newloc, cha.getHeading(), true, 1);

			} else if (cha instanceof L1NpcInstance) {// 被召喚的是NPC
				((L1NpcInstance) cha).teleport(newloc.getX(), newloc.getY(), cha.getHeading());
			}
		}
	}

	/**
	 * 隨機執行移動
	 * 
	 * @param pc
	 * @param effectable
	 */
	public static void randomTeleport(final L1PcInstance pc, final boolean effectable) {
		try {
			// 本處理違結構???
			final L1Location newLocation = pc.getLocation().randomLocation(200, true);
			final int newX = newLocation.getX();
			final int newY = newLocation.getY();
			final short mapId = (short) newLocation.getMapId();

			L1Teleport.teleport(pc, newX, newY, mapId, 5, effectable);

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
	   public static void randomTeleportai(L1PcInstance pc) {
	        try {
	            L1Location newLocation = pc.getLocation().randomLocation(200, true);
	            int newX = newLocation.getX();
	            int newY = newLocation.getY();
	            int mapid = pc.getMapId();
	            pc.setTeleportX(newX);
	            pc.setTeleportY(newY);
	            pc.setTeleportMapId((short)mapid);
	            pc.setTeleportHeading(5);
	            Teleportation.teleportation(pc);
	            pc.setTeleport(false);
	            pc.sendPackets(new S_Paralysis(7, false));
	            if (pc.hasSkillEffect(78)) {
	                pc.killSkillEffectTimer(78);
	                pc.startHpRegeneration();
	                pc.startMpRegeneration();
	            }
	            if (pc.isPathfinding()) {
	                pc.setrandomMoveDirection((7));
	            }
	        }
	        catch (Exception e) {
	            L1Teleport._log.error(e.getLocalizedMessage(), e);
}
}
}

