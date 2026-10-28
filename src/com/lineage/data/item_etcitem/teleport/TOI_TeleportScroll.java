package com.lineage.data.item_etcitem.teleport;

import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class TOI_TeleportScroll extends ItemExecutor
{
    public static ItemExecutor get() {
        return new TOI_TeleportScroll();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.hasSkillEffect(4017)) {
            pc.sendPackets(new S_ServerMessage(1413));
            return;
        }
        else {
            int x = 0;
            int y = 0;
            short map = 0;
            String nameId = item.getItem().getNameId();
            if (nameId.equalsIgnoreCase("$2169")) {
                x = 32630;
                y = 32935;
                map = 111;
            }
            else if (nameId.equalsIgnoreCase("$2168")) {
                x = 32630;
                y = 32935;
                map = 121;
            }
            else if (nameId.equalsIgnoreCase("$2404")) {
                x = 32630;
                y = 32935;
                map = 131;
            }
            else if (nameId.equalsIgnoreCase("$2405")) {
                x = 32630;
                y = 32935;
                map = 141;
            }
            else if (nameId.equalsIgnoreCase("$2673")) {
                x = 32630;
                y = 32935;
                map = 151;
            }
            else if (nameId.equalsIgnoreCase("$2674")) {
                x = 32630;
                y = 32935;
                map = 161;
            }
            else if (nameId.equalsIgnoreCase("$2675")) {
                x = 32630;
                y = 32935;
                map = 171;
            }
            else if (nameId.equalsIgnoreCase("$2676")) {
                x = 32630;
                y = 32935;
                map = 181;
            }
            else if (nameId.equalsIgnoreCase("$2677")) {
                x = 32630;
                y = 32935;
                map = 191;
            }
            else if (nameId.equalsIgnoreCase("$2862")) {
                x = 32693;
                y = 32876;
                map = 200;
            }
            if (pc.getMap().isEscapable()) {
                pc.getInventory().removeItem(item, 1L);
                L1Teleport.teleport(pc, x, y, map, 5, true);
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
            else {
                pc.sendPackets(new S_ServerMessage(647));
                pc.sendPackets(new S_Paralysis(7, false));
            }
            return;
        }
    }
}
