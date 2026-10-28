package com.add.system;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_NPCPack;
import com.lineage.server.utils.Random;
import com.lineage.server.IdFactory;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import java.util.Iterator;
import java.util.logging.Level;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_GreenMessage;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.echo.ClientExecutor;
import java.util.ArrayList;
import java.util.logging.Logger;

public class L1ItemNpc
{
    private static Logger _log;
    private static boolean NO_MORE_GET_DATA5;
    private static ArrayList<int[]> aData5;
    
    static {
        L1ItemNpc._log = Logger.getLogger(L1ItemNpc.class.getName());
        L1ItemNpc.NO_MORE_GET_DATA5 = false;
        L1ItemNpc.aData5 = new ArrayList();
    }
    
    public static void forRequestItemUSe(ClientExecutor client, L1ItemInstance itemInstance) {
        L1PcInstance user = client.getActiveChar();
        int[] aTempData = null;
        int iX = user.getX();
        int iY = user.getY();
        int iMap = user.getMapId();
        int iX2 = 0;
        int iY2 = 0;
        int iX3 = 0;
        int iY3 = 0;
        short iMap2 = 0;
        boolean found = false;
        int itemid = itemInstance.getItemId();
        if (!L1ItemNpc.NO_MORE_GET_DATA5) {
            L1ItemNpc.NO_MORE_GET_DATA5 = true;
            getData5();
        }
        for (int i = 0; i < L1ItemNpc.aData5.size(); ++i) {
            aTempData = L1ItemNpc.aData5.get(i);
            iX2 = aTempData[5];
            iY2 = aTempData[6];
            iX3 = aTempData[7];
            iY3 = aTempData[8];
            iMap2 = (short)aTempData[10];
            if (aTempData[1] == itemid && user.getInventory().checkItem(aTempData[1], (long)aTempData[2])) {
                try {
                    if (iMap2 != -1 && iMap != iMap2) {
                        user.sendPackets(new S_SystemMessage("無法此在地圖使用。"));
                        return;
                    }
                    else if (iX2 != -1 && iX3 != -1 && iY2 != -1 && iY3 != -1 && (iX < iX2 || iX > iX3 || iY < iY2 || iY > iY3)) {
                        user.sendPackets(new S_SystemMessage("無法此在座標使用。"));
                        return;
                    }
                    else {
                        if (aTempData[0] != 0) {
                            byte class_id = 0;
                            String msg = "";
                            if (user.isCrown()) {
                                class_id = 1;
                            }
                            else if (user.isKnight()) {
                                class_id = 2;
                            }
                            else if (user.isWizard()) {
                                class_id = 3;
                            }
                            else if (user.isElf()) {
                                class_id = 4;
                            }
                            else if (user.isDarkelf()) {
                                class_id = 5;
                            }
                            else if (user.isDragonKnight()) {
                                class_id = 6;
                            }
                            else if (user.isIllusionist()) {
                                class_id = 7;
                            }
                            switch (aTempData[0]) {
                                case 1: {
                                    msg = "王族";
                                    break;
                                }
                                case 2: {
                                    msg = "騎士";
                                    break;
                                }
                                case 3: {
                                    msg = "法師";
                                    break;
                                }
                                case 4: {
                                    msg = "妖精";
                                    break;
                                }
                                case 5: {
                                    msg = "黑暗妖精";
                                    break;
                                }
                                case 6: {
                                    msg = "龍騎士";
                                    break;
                                }
                                case 7: {
                                    msg = "幻術師";
                                    break;
                                }
                            }
                            if (aTempData[0] != class_id) {
                                user.sendPackets(new S_ServerMessage(166, "職業必須是" + msg + "才能使用此道具"));
                                return;
                            }
                        }
                        for (L1Object obj : World.get().getObject()) {
                            if (obj instanceof L1MonsterInstance) {
                                L1MonsterInstance mob = (L1MonsterInstance)obj;
                                if (mob != null && aTempData[12] != 0 && mob.getNpcTemplate().get_npcId() == aTempData[4]) {
                                    found = true;
                                    break;
                                }
                                else {
                                    continue;
                                }
                            }
                        }
                        if (found) {
                            user.sendPackets(new S_ServerMessage(79));
                        }
                        else {
                            mobspawn(user, aTempData[4], aTempData[9], aTempData[11], aTempData[13], aTempData[14]);
                            String npcname = NpcTable.get().getNpcName(aTempData[4]);
                            user.sendPackets(new S_GreenMessage(npcname + "出現了。"));
                            for (L1PcInstance listner : World.get().getVisiblePlayer(user, 50)) {
                                if (user.get_showId() == listner.get_showId()) {
                                    listner.sendPackets(new S_GreenMessage(npcname + "出現了。"));
                                }
                            }
                        }
                        if (aTempData[3] == 1 && !found) {
                            for (int j = 0; j < aTempData[2]; ++j) {
                                L1ItemInstance item = user.getInventory().findItemId(aTempData[1]);
                                user.getInventory().removeItem(item.getId(), 1L);
                            }
                        }
                    }
                }
                catch (Exception e) {
                    L1ItemNpc._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
                }
            }
        }
    }
    
    private static void getData5() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM system_item_npc");
            int[] aReturn = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new int[] { rset.getInt("class"), rset.getInt("materials"), rset.getInt("counts"), rset.getInt("destroy"), rset.getInt("monster_id"), rset.getInt("location_minx"), rset.getInt("location_miny"), rset.getInt("location_maxx"), rset.getInt("location_maxy"), rset.getInt("location_area"), rset.getInt("map_id"), rset.getInt("delete_time"), rset.getInt("found"), rset.getInt("spawn_x"), rset.getInt("spawn_y") };
                    L1ItemNpc.aData5.add(aReturn);
                }
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
        catch (Exception ex) {}
    }
    
    private static void mobspawn(L1PcInstance pc, int npcId, int randomRange, int timeMillisToDelete, int spawnx, int spawny) {
        try {
            L1NpcInstance npc = NpcTable.get().newNpcInstance(npcId);
            npc.setId(IdFactory.get().nextId());
            npc.setMap(pc.getMapId());
            npc.set_showId(pc.get_showId());
            if (randomRange == 0) {
                if (spawnx > 0 && spawny > 0) {
                    npc.setX(spawnx);
                    npc.setY(spawny);
                }
                else {
                    npc.getLocation().set(pc.getLocation());
                    npc.getLocation().forward(pc.getHeading());
                }
            }
            else {
                int tryCount = 0;
                do {
                    ++tryCount;
                    npc.setX(pc.getX() + Random.nextInt(randomRange) - Random.nextInt(randomRange));
                    npc.setY(pc.getY() + Random.nextInt(randomRange) - Random.nextInt(randomRange));
                    if (npc.getMap().isInMap(npc.getLocation()) && npc.getMap().isPassable(npc.getLocation(), npc)) {
                        break;
                    }
                    else {
                        Thread.sleep(1L);
                    }
                } while (tryCount < 50);
                if (tryCount >= 50) {
                    npc.getLocation().set(pc.getLocation());
                    npc.getLocation().forward(pc.getHeading());
                }
            }
            npc.setHomeX(npc.getX());
            npc.setHomeY(npc.getY());
            npc.setHeading(5);
            npc.setMovementDistance(50);
            World.get().storeObject(npc);
            World.get().addVisibleObject(npc);
            int gfx = npc.getTempCharGfx();
            switch (gfx) {
                case 30: {
                    npc.broadcastPacketAll(new S_NPCPack(npc));
                    npc.broadcastPacketAll(new S_DoActionGFX(npc.getId(), 30));
                    break;
                }
                case 145:
                case 2158:
                case 3547:
                case 3566:
                case 3957:
                case 3969:
                case 3984:
                case 3989:
                case 7719:
                case 10071:
                case 11465:
                case 11467: {
                    npc.broadcastPacketAll(new S_NPCPack(npc));
                    npc.broadcastPacketAll(new S_DoActionGFX(npc.getId(), 4));
                    break;
                }
                case 7539:
                case 7557:
                case 7558:
                case 7864:
                case 7869:
                case 7870:
                case 8036:
                case 8054:
                case 8055: {
                    for (L1PcInstance _pc : World.get().getVisiblePlayer(npc, 50)) {
                        if (npc.getTempCharGfx() == 7539) {
                            _pc.sendPackets(new S_ServerMessage(1570));
                        }
                        else if (npc.getTempCharGfx() == 7864) {
                            _pc.sendPackets(new S_ServerMessage(1657));
                        }
                        else if (npc.getTempCharGfx() == 8036) {
                            _pc.sendPackets(new S_ServerMessage(1755));
                        }
                        else {
                            continue;
                        }
                    }
                    npc.broadcastPacketAll(new S_NPCPack(npc));
                    npc.broadcastPacketAll(new S_DoActionGFX(npc.getId(), 11));
                    break;
                }
                case 7548:
                case 7550:
                case 7552:
                case 7554:
                case 7585:
                case 7591:
                case 7840:
                case 8096: {
                    npc.broadcastPacketAll(new S_NPCPack(npc));
                    npc.broadcastPacketAll(new S_DoActionGFX(npc.getId(), 11));
                    break;
                }
            }
            npc.turnOnOffLight();
            npc.startChat(0);
            if (timeMillisToDelete > 0) {
                npc.set_spawnTime(timeMillisToDelete);
            }
        }
        catch (Exception e) {
            L1ItemNpc._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }
}
