package com.lineage.data.item_etcitem.wand;

import com.lineage.server.serverpackets.S_Teleport;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.L1Trade;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Storm_Walk extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Storm_Walk();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int spellsc_x = data[1];
        int spellsc_y = data[2];
        if (pc.getTradeID() != 0) {
            L1Trade trade = new L1Trade();
            trade.tradeCancel(pc);
        }
        pc.setTeleportX(spellsc_x);
        pc.setTeleportY(spellsc_y);
        pc.setTeleportMapId(pc.getMapId());
        pc.setTeleportHeading(5);
        pc.sendPacketsAll(new S_SkillSound(pc.getId(), 2235));
        pc.sendPackets(new S_Teleport(pc));
    }
}
