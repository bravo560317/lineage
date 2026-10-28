package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ScrollShopSellList;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Anyshop extends ItemExecutor
{
    private int type1;
    
    public static ItemExecutor get() {
        return new Anyshop();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.set_temp_adena(40308);
        int npcId = this.type1;
        pc.sendPackets(new S_ScrollShopSellList(NpcTable.get().getTemplate(npcId)));
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this.type1 = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
    }
}
