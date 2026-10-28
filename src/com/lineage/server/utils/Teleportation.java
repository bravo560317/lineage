package com.lineage.server.utils;

import java.util.Map;
import java.util.ArrayList;
import com.lineage.server.templates.L1MapsLimitTime;
import com.lineage.server.model.L1Location;
import java.util.Iterator;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_PacketBoxWindShackle;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.timecontroller.server.ServerUseMapTimer;
import com.lineage.server.serverpackets.S_NPCPack_Hierarch;
import com.lineage.server.serverpackets.S_NPCPack_Skin;
import com.lineage.server.model.Instance.L1SkinInstance;
import com.lineage.server.serverpackets.S_NPCPack_Eff;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.datatables.MapsGroupTable;
import com.lineage.server.serverpackets.S_NPCPack_Doll;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.serverpackets.S_NPCPack_Pet;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.serverpackets.S_NPCPack_Summon;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.HashSet;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_OwnCharPack;
import com.lineage.server.serverpackets.S_OtherCharPacks;
import com.lineage.server.serverpackets.S_MapID;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.world.WorldClan;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class Teleportation
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(Teleportation.class);
        Teleportation._random = new Random();
    }
    
    public static void teleportation(L1PcInstance pc) {
        try {
            if (pc == null) {
                return;
            }
            else if (pc.getOnlineStatus() == 0) {
                return;
            }
            else if (pc.getNetConnection() == null) {
                return;
            }
            else if (pc.isDead()) {
                return;
            }
else if (pc.isTeleport()) {
                return;
            }
            else {
                pc.getMap().setPassable(pc.getLocation(), true);
                short mapId = pc.getTeleportMapId();
                if (pc.isDead() && mapId != 9101) {
                    return;
                }
                else {
                    // 1. 先把 x, y, pc_mapId 這些變數宣告出來
                    int x = pc.getTeleportX();
                    int y = pc.getTeleportY();
                    int head = pc.getTeleportHeading();
                    short pc_mapId = pc.getMapId();

                    // =========================================
                    // [終極修正] 攔截高寵跟進旅館 (同步解除雙方連線版)
                    // =========================================
                    if (pc.get_followmaster() != null) {
                        if (mapId >= 16384 && mapId <= 25088) {
                            pc.sendPackets(new S_SystemMessage("無法跟隨主人進入旅館，已自動解除跟隨！"));
                            
                            // 同步解除主人的被跟隨狀態
                            L1PcInstance master = pc.get_followmaster();
                            if (master != null) {
                                master.setfollow(false);
                                master.sendPackets(new S_SystemMessage("您的高寵無法進入旅館，已自動斷開連線！"));
                            }
                            
                            // 解除高寵相關設定
                            pc.set_followmaster(null);
                            pc.setfollow(false);
                            pc.setfollowcheck(false);
                            pc.setActived(false);
                            
                            try {
                                com.add.L1PcUnlock.Pc_Unlock(pc); 
                            } catch (Exception e) {}
                            
                            // 強制改回現在的座標，讓系統原地重整畫面
                            x = pc.getX();
                            y = pc.getY();
                            mapId = pc_mapId; 
                        }
                    }
                    // =========================================

                    L1Map map = L1WorldMap.get().getMap(mapId);
                    if (!map.isInMap(x, y) && !pc.isGm()) {
                        x = pc.getX();
                        y = pc.getY();
                        mapId = pc.getMapId();
                    }
                    L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                    if (clan != null && clan.getWarehouseUsingChar() == pc.getId()) {
                        clan.setWarehouseUsingChar(0);
                        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_who"));
                        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_who"));
                    }
                    World.get().moveVisibleObject(pc, (int)mapId);
                    pc.setLocation(x, y, (int)mapId);
                    pc.setHeading(head);
                    pc.setOleLocX(x);
                    pc.setOleLocY(y);
                    boolean isUnderwater = pc.getMap().isUnderwater();
                    pc.sendPackets(new S_MapID(pc, (int)pc.getMapId(), isUnderwater));
                    if (!pc.isGhost() && !pc.isGmInvis() && !pc.isInvisble()) {
                        pc.broadcastPacketAll(new S_OtherCharPacks(pc));
                    }
                    if (pc.isReserveGhost()) {
                        pc.endGhost();
                    }
                    pc.sendPackets(new S_OwnCharPack(pc));
                    pc.removeAllKnownObjects();
                    pc.updateObject();
                    pc.sendVisualEffectAtTeleport();
                    pc.sendPackets(new S_CharVisualUpdate(pc));
                    pc.killSkillEffectTimer(32);
                    pc.setCallClanId(0);
                    HashSet<L1PcInstance> subjects = new HashSet();
                    subjects.add(pc);
                    if (!pc.isGhost()) {
                        if (pc.getMap().isTakePets()) {
                            for (L1NpcInstance petNpc : pc.getPetList().values()) {
                                int nx = pc.getX();
                                int ny = pc.getY();
                                petNpc.set_showId(pc.get_showId());
                                teleport(petNpc, nx, ny, mapId, head);
                                if (petNpc instanceof L1SummonInstance) {
                                    L1SummonInstance summon = (L1SummonInstance)petNpc;
                                    pc.sendPackets(new S_NPCPack_Summon(summon, pc));
                                }
                                else if (petNpc instanceof L1PetInstance) {
                                    L1PetInstance pet = (L1PetInstance)petNpc;
                                    pc.sendPackets(new S_NPCPack_Pet(pet, pc));
                                }
                                for (L1PcInstance visiblePc : World.get().getVisiblePlayer(petNpc)) {
                                    visiblePc.removeKnownObject(petNpc);
                                    if (visiblePc.get_showId() == petNpc.get_showId()) {
                                        subjects.add(visiblePc);
                                    }
                                }
                            }
                        }
                        if (!pc.getDolls().isEmpty()) {
                            L1Location loc = pc.getLocation().randomLocation(3, false);
                            int nx2 = loc.getX();
                            int ny2 = loc.getY();
                            Object[] dolls = pc.getDolls().values().toArray();
                            Object[] array;
                            for (int length = (array = dolls).length, i = 0; i < length; ++i) {
                                Object obj = array[i];
                                L1DollInstance doll = (L1DollInstance)obj;
                                teleport(doll, nx2, ny2, mapId, head);
                                pc.sendPackets(new S_NPCPack_Doll(doll, pc));
                                doll.set_showId(pc.get_showId());
                                for (L1PcInstance visiblePc2 : World.get().getVisiblePlayer(doll)) {
                                    visiblePc2.removeKnownObject(doll);
                                    if (visiblePc2.get_showId() == doll.get_showId()) {
                                        subjects.add(visiblePc2);
                                    }
                                }
                            }
                        }
                        if (pc_mapId != mapId) {
                            L1MapsLimitTime mapsLimitTime = MapsGroupTable.get().findGroupMap((int)mapId);
                            if (mapsLimitTime != null) {
                                int order_id = mapsLimitTime.getOrderId();
                                int used_time = pc.getMapsTime(order_id);
                                int limit_time = mapsLimitTime.getLimitTime();
                                if (used_time < limit_time) {
                                    pc.sendPackets(new S_PacketBox(153, limit_time - used_time));
                                }
                            }
                        }
                        ArrayList<L1EffectInstance> effectlist = pc.get_TrueTargetEffectList();
                        for (L1EffectInstance effect : effectlist) {
                            L1Location loc2 = pc.getLocation();
                            int nx3 = loc2.getX();
                            int ny3 = loc2.getY();
                            teleport(effect, nx3, ny3, mapId, head);
                            pc.sendPackets(new S_NPCPack_Eff(effect));
                            effect.set_showId(pc.get_showId());
                            for (L1PcInstance knownPc : effect.getKnownPlayers()) {
                                knownPc.removeKnownObject(effect);
                                if (knownPc.get_showId() == effect.get_showId()) {
                                    knownPc.addKnownObject(effect);
                                    knownPc.sendPackets(new S_NPCPack_Eff(effect));
                                }
                            }
                        }
                        if (pc.getSkins() != null) {
                            Map<Integer, L1SkinInstance> skinList = pc.getSkins();
                            for (Integer gfxid : skinList.keySet()) {
                                L1SkinInstance skin = skinList.get(gfxid);
                                teleport(skin, pc.getX(), pc.getY(), pc.getMapId(), pc.getHeading());
                                pc.sendPackets(new S_NPCPack_Skin(skin));
                                skin.set_showId(pc.get_showId());
                                for (L1PcInstance visiblePc3 : World.get().getVisiblePlayer(skin)) {
                                    visiblePc3.removeKnownObject(skin);
                                    if (visiblePc3.get_showId() == skin.get_showId()) {
                                        subjects.add(visiblePc3);
                                    }
                                }
                            }
                        }
                        if (pc.getHierarchs() != null) {
                            int nx2 = pc.getX();
                            int ny2 = pc.getY();
                            teleport(pc.getHierarchs(), nx2, ny2, mapId, head);
                            pc.sendPackets(new S_NPCPack_Hierarch(pc.getHierarchs()));
                            pc.getHierarchs().set_showId(pc.get_showId());
                            for (L1PcInstance visiblePc4 : World.get().getVisiblePlayer(pc.getHierarchs())) {
                                visiblePc4.removeKnownObject(pc.getHierarchs());
                                if (visiblePc4.get_showId() == pc.getHierarchs().get_showId()) {
                                    subjects.add(visiblePc4);
                                }
                            }
                        }
                    }
                    for (L1PcInstance updatePc : subjects) {
                        updatePc.updateObject();
                    }
                    Integer time = ServerUseMapTimer.MAP.get(pc);
                    if (time != null) {
                        ServerUseMapTimer.put(pc, time);
                    }
                    if (pc.getoldMapId() != pc.getMapId()) {
                        if (pc.getnpcdmg() > 0.0) {
                            pc.sendPackets(new S_SystemMessage("\\fU目前攻擊累積傷害:" + pc.getnpcdmg()));
                            pc.sendPackets(new S_SystemMessage("\\fU因您更新地圖,攻擊傷害累積重新計算"));
                            pc.setnpcdmg(0.0);
                        }
                        pc.setoldMapId(pc.getMapId());
                    }
                    pc.setTeleport(false);
                    if (pc.hasSkillEffect(167)) {
                        pc.sendPackets(new S_PacketBoxWindShackle(pc.getId(), pc.getSkillEffectTimeSec(167)));
                    }
                    pc.sendPackets(new S_Paralysis(7, false));
                    if (!pc.isGmInvis()) {
                        pc.getMap().setPassable(pc.getLocation(), false);
                    }
                    pc.getPetModel();
                }
            }
        }
        catch (Exception e) {
            Teleportation._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void teleport(L1NpcInstance npc, int x, int y, short map, int head) {
        try {
            World.get().moveVisibleObject(npc, (int)map);
            L1WorldMap.get().getMap(npc.getMapId()).setPassable(npc.getX(), npc.getY(), true, 2);
            npc.setX(x);
            npc.setY(y);
            npc.setMap(map);
            npc.setHeading(head);
            L1WorldMap.get().getMap(npc.getMapId()).setPassable(npc.getX(), npc.getY(), false, 2);
        }
        catch (Exception e) {
            Teleportation._log.error(e.getLocalizedMessage(), e);
        }
    }
}
