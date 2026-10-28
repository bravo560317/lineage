package com.add.system;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.map.L1Map;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import com.lineage.server.model.L1Party;
import com.lineage.server.templates.L1Item;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class ItemActionTele1
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean NO_GET_DATA;
    private static boolean tele;
    public static final String TOKEN = ",";
    
    static {
        ItemActionTele1.aData = new ArrayList();
        ItemActionTele1.NO_GET_DATA = false;
        ItemActionTele1.tele = true;
    }
    
    public static boolean forNpcQuest(String s, L1PcInstance pc, L1NpcInstance npc, int npcid, int oid) {
        ArrayList aTempData = null;
        if (!ItemActionTele1.NO_GET_DATA) {
            ItemActionTele1.NO_GET_DATA = true;
            getData();
        }
        for (int i = 0; i < ItemActionTele1.aData.size(); ++i) {
            aTempData = ItemActionTele1.aData.get(i);
            if (aTempData.get(0) != null && (Integer)aTempData.get(0) == npcid && ((String)aTempData.get(1)).equals(s)) {
                if ((Integer)aTempData.get(2) > 0 && !pc.getInventory().checkItem((Integer)aTempData.get(2), (long)(int)(Integer)aTempData.get(3))) {
                    L1Item temp = ItemTable.get().getTemplate((Integer)aTempData.get(2));
                    pc.sendPackets(new S_SystemMessage(temp.getNameId() + "不足( " + (int)(Integer)aTempData.get(3) + ")，無法使用。"));
                    return false;
                }
                else {
                    boolean isTeleport = pc.getMap().isTeleportable();
                    if (!isTeleport) {
                        pc.sendPackets(new S_ServerMessage("該地圖無法使用此功能"));
                        return false;
                    }
                    else if (!pc.isInParty()) {
                        pc.sendPackets(new S_ServerMessage("您尚未有組隊"));
                        return false;
                    }
                    else if (!pc.getParty().isLeader(pc)) {
                        pc.sendPackets(new S_ServerMessage("你必須是隊伍的領導者"));
                        return false;
                    }
                    else if (pc.getQuest().get_step((Integer)aTempData.get(10)) == (Integer)aTempData.get(11)) {
                        pc.sendPackets(new S_ServerMessage("您當日執行已達上限"));
                        return false;
                    }
                    else if (pc.getLevel() < (Integer)aTempData.get(8)) {
                        pc.sendPackets(new S_ServerMessage("等級不足" + (int)(Integer)aTempData.get(8)));
                        return false;
                    }
                    else {
                        if ((Integer)aTempData.get(9) != 0) {
                            byte class_id = 0;
                            if (pc.isCrown()) {
                                class_id = 1;
                            }
                            else if (pc.isKnight()) {
                                class_id = 2;
                            }
                            else if (pc.isWizard()) {
                                class_id = 3;
                            }
                            else if (pc.isElf()) {
                                class_id = 4;
                            }
                            else if (pc.isDarkelf()) {
                                class_id = 5;
                            }
                            else if (pc.isDragonKnight()) {
                                class_id = 6;
                            }
                            else if (pc.isIllusionist()) {
                                class_id = 7;
                            }
                            if ((int)(Integer)aTempData.get(9) != class_id) {
                                pc.sendPackets(new S_SystemMessage("你的職業無法傳送。"));
                                return false;
                            }
                        }
                        if (pc.getParty().isLeader(pc)) {
                            L1Party party = pc.getParty();
                            ConcurrentHashMap<Integer, L1PcInstance> pcs = party.partyUsers();
                            for (L1PcInstance pc2 : pcs.values()) {
                                if ((Integer)aTempData.get(9) != 0) {
                                    byte class_id2 = 0;
                                    if (pc2.isCrown()) {
                                        class_id2 = 1;
                                    }
                                    else if (pc2.isKnight()) {
                                        class_id2 = 2;
                                    }
                                    else if (pc2.isWizard()) {
                                        class_id2 = 3;
                                    }
                                    else if (pc2.isElf()) {
                                        class_id2 = 4;
                                    }
                                    else if (pc2.isDarkelf()) {
                                        class_id2 = 5;
                                    }
                                    else if (pc2.isDragonKnight()) {
                                        class_id2 = 6;
                                    }
                                    else if (pc2.isIllusionist()) {
                                        class_id2 = 7;
                                    }
                                    if ((int)(Integer)aTempData.get(9) != class_id2) {
                                        pc2.sendPackets(new S_SystemMessage("你的職業無法傳送。"));
                                        return false;
                                    }
                                }
                                if (pc2.getLevel() < (Integer)aTempData.get(8)) {
                                    pc2.sendPackets(new S_ServerMessage("等級不足" + (int)(Integer)aTempData.get(8)));
                                    return false;
                                }
                                else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == (Integer)aTempData.get(11)) {
                                    pc2.sendPackets(new S_ServerMessage("您當日執行已達上限"));
                                    return false;
                                }
                                else if (pc.getParty().isMember(pc2) && (Integer)aTempData.get(4) > 0) {
                                    int locX = (Integer)aTempData.get(4);
                                    int locY = (Integer)aTempData.get(5);
                                    int mapId = (Integer)aTempData.get(6);
                                    L1Map map = L1WorldMap.get().getMap((short)mapId);
                                    int r = 10;
                                    int tryCount = 0;
                                    int newX = locX;
                                    int newY = locY;
                                    do {
                                        ++tryCount;
                                        newX = locX + (int)(Math.random() * (double)r) - (int)(Math.random() * (double)r);
                                        newY = locY + (int)(Math.random() * (double)r) - (int)(Math.random() * (double)r);
                                        if (map.isPassable(newX, newY, pc)) {
                                            break;
                                        }
                                    } while (tryCount < 5);
                                    if (tryCount >= 5) {
                                        L1Teleport.teleport(pc2, locX, locY, (short)mapId, pc2.getHeading(), true);
                                    }
                                    else {
                                        L1Teleport.teleport(pc2, newX, newY, (short)mapId, pc2.getHeading(), true);
                                    }
                                    pc.getInventory().consumeItem((Integer)aTempData.get(2), (long)(int)(Integer)aTempData.get(3));
                                    if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 0) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 1);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 1) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 2);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 2) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 3);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 3) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 4);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 4) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 5);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 5) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 6);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 6) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 7);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 7) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 8);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 8) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 9);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 9) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 10);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 10) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 11);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 11) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 12);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 12) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 13);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 13) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 14);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 14) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 15);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 15) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 16);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 16) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 17);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 17) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 18);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 18) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 19);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 19) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 20);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 20) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 21);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 21) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 22);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 22) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 23);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 23) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 24);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 24) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 25);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 25) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 26);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 26) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 27);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 27) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 28);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 28) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 29);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 29) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 30);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 30) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 31);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 31) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 32);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 32) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 33);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 33) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 34);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 34) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 35);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 35) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 36);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 36) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 37);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 37) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 38);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 38) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 39);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 39) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 40);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 40) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 41);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 41) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 42);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 42) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 43);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 43) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 44);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 44) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 45);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 45) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 46);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 46) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 47);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 47) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 48);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 48) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 49);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 49) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 50);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 50) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 51);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 51) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 52);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 52) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 53);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 53) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 54);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 54) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 55);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 55) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 56);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 56) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 57);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 57) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 58);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 58) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 59);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 59) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 60);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 60) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 61);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 61) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 62);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 62) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 63);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 63) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 64);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 64) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 65);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 65) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 66);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 66) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 67);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 67) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 68);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 68) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 69);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 69) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 70);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 70) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 71);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 71) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 72);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 72) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 73);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 73) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 74);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 74) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 75);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 75) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 76);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 76) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 77);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 77) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 78);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 78) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 79);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 79) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 80);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 80) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 81);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 81) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 82);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 82) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 83);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 83) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 84);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 84) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 85);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 85) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 86);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 86) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 87);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 87) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 88);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 88) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 89);
                                    }
                                    else if (pc2.getQuest().get_step((Integer)aTempData.get(10)) == 89) {
                                        pc2.getQuest().set_step((Integer)aTempData.get(10), 90);
                                    }
                                    if ((String)aTempData.get(7) != null) {
                                        pc2.sendPackets(new S_SystemMessage((String)aTempData.get(7)));
                                    }
                                    else {
                                        continue;
                                    }
                                }
                                else {
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private static void getData() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_npc隊伍傳送");
            ArrayList<Object> aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("npcid")));
                    sTemp = rset.getString("action");
                    aReturn.add(1, sTemp);
                    aReturn.add(2, new Integer(rset.getInt("消耗道具編號")));
                    aReturn.add(3, new Integer(rset.getInt("消耗道具數量")));
                    aReturn.add(4, new Integer(rset.getInt("傳送座標X")));
                    aReturn.add(5, new Integer(rset.getInt("傳送座標Y")));
                    aReturn.add(6, new Integer(rset.getInt("傳送座標M")));
                    aReturn.add(7, rset.getString("傳送顯示文字"));
                    aReturn.add(8, new Integer(rset.getInt("等級限制")));
                    aReturn.add(9, new Integer(rset.getInt("職業限制")));
                    aReturn.add(10, new Integer(rset.getInt("每日次數編號")));
                    aReturn.add(11, new Integer(rset.getInt("每日次數限制")));
                    ItemActionTele1.aData.add(aReturn);
                }
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
        catch (Exception ex) {}
    }
    
    private static Object getArray(String s, String sToken, int iType) {
        StringTokenizer st = new StringTokenizer(s, sToken);
        int iSize = st.countTokens();
        String sTemp = null;
        if (iType == 1) {
            int[] iReturn = new int[iSize];
            for (int i = 0; i < iSize; ++i) {
                sTemp = st.nextToken();
                iReturn[i] = Integer.parseInt(sTemp);
            }
            return iReturn;
        }
        else if (iType == 2) {
            String[] sReturn = new String[iSize];
            for (int i = 0; i < iSize; ++i) {
                sTemp = st.nextToken();
                sReturn[i] = sTemp;
            }
            return sReturn;
        }
        else if (iType == 3) {
            String sReturn2 = null;
            for (int i = 0; i < iSize; ++i) {
                sTemp = (sReturn2 = st.nextToken());
            }
            return sReturn2;
        }
        else {
            return null;
        }
    }
}
