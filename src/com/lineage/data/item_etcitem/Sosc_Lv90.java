package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ShowPolyList;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Sosc_Lv90 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Sosc_Lv90();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.sendPackets(new S_ShowPolyList(pc.getId(), "lv90poly"));
        if (!pc.isItemPoly()) {
            pc.setSummonMonster(false);
            pc.setItemPoly(true);
            pc.setPolyScroll(item);
        }
    }
}
