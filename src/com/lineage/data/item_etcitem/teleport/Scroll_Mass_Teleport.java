package com.lineage.data.item_etcitem.teleport;

import com.lineage.server.model.L1Location;
import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.templates.L1BookMark;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.datatables.lock.CharBookReading;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Scroll_Mass_Teleport extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Scroll_Mass_Teleport();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.hasSkillEffect(4017)) {
            pc.sendPackets(new S_ServerMessage(1413));
            return;
        }
        else {
            int mapID = data[0];
            int btele = data[1];
            boolean isTeleport = L1WorldMap.get().getMap((short)mapID).isEscapable();
            if (!isTeleport) {
                pc.sendPackets(new S_ServerMessage(647));
                pc.sendPackets(new S_Paralysis(7, false));
            }
            else {
                L1BookMark bookm = CharBookReading.get().getBookMark(pc, btele);
                if (bookm != null) {
                    pc.getInventory().removeItem(item, 1L);
                    ArrayList<L1Object> objList = World.get().getVisiblePoint(pc.getLocation(), 3, pc.get_showId());
                    for (L1Object tgObj : objList) {
                        if (tgObj instanceof L1PcInstance) {
                            L1PcInstance tgPc = (L1PcInstance)tgObj;
                            if (!tgPc.isDead() && tgPc.getClanid() != 0 && tgPc.getClanid() == pc.getClanid() && !tgPc.isPrivateShop()) {
                                L1BuffUtil.cancelAbsoluteBarrier(tgPc);
                                tgPc.setTeleportX(bookm.getLocX());
                                tgPc.setTeleportY(bookm.getLocY());
                                tgPc.setTeleportMapId(bookm.getMapId());
                                tgPc.sendPackets(new S_Message_YN(748));
                            }
                            else {
                                continue;
                            }
                        }
                    }
                    L1Teleport.teleport(pc, bookm.getLocX(), bookm.getLocY(), bookm.getMapId(), 5, true);
                }
                else {
                    pc.getInventory().removeItem(item, 1L);
                    L1Location newLocation = pc.getLocation().randomLocation(200, true);
                    int newX = newLocation.getX();
                    int newY = newLocation.getY();
                    short newMapId = (short)newLocation.getMapId();
                    ArrayList<L1Object> objList2 = World.get().getVisiblePoint(pc.getLocation(), 3, pc.get_showId());
                    for (L1Object tgObj2 : objList2) {
                        if (tgObj2 instanceof L1PcInstance) {
                            L1PcInstance tgPc2 = (L1PcInstance)tgObj2;
                            if (!tgPc2.isDead() && tgPc2.getClanid() != 0 && tgPc2.getClanid() == pc.getClanid() && !tgPc2.isPrivateShop()) {
                                L1BuffUtil.cancelAbsoluteBarrier(tgPc2);
                                tgPc2.setTeleportX(newX);
                                tgPc2.setTeleportY(newY);
                                tgPc2.setTeleportMapId(newMapId);
                                pc.sendPackets(new S_Message_YN(748));
                            }
                            else {
                                continue;
                            }
                        }
                    }
                    L1Teleport.teleport(pc, newX, newY, newMapId, 5, true);
                    if (pc.isActived()) {
                        pc.setActived(false);
                        pc.killSkillEffectTimer(8853);
                        pc.sendPackets(new S_ServerMessage("掛機中請勿使用手動卷軸。"));
                        pc.sendPackets(new S_ServerMessage("自動狩獵已停止。"));
                        if (pc.getAutoX() > 0) {
                            pc.setAutoX(0);
                            pc.setAutoY(0);
                            pc.setAutoMap(0);
                        }
                    }
                }
                L1BuffUtil.cancelAbsoluteBarrier(pc);
            }
            return;
        }
    }
}
