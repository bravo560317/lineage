package com.lineage.data.item_etcitem.add;

import com.lineage.william.resolvent11;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Dissolution_other extends ItemExecutor
{
    private Dissolution_other() {
    }
    
    public static ItemExecutor get() {
        return new Dissolution_other();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        if (targObjId <= 0) {
            return;
        }
        else {
            L1ItemInstance item2 = pc.getInventory().getItem(targObjId);
            if (item2 == null) {
                return;
            }
            else if (item2.isEquipped()) {
                pc.sendPackets(new S_SystemMessage("請先把裝備脫掉在分解。"));
                return;
            }
            else {
                resolvent11.forresolvent(pc, item, data[0]);
                return;
            }
        }
    }
}
