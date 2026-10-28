package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ShopSellListCn;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Anyshop_cn extends ItemExecutor
{
    private int type1;
    
    public static ItemExecutor get() {
        return new Anyshop_cn();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.set_temp_adena(44070);
        int npcId = this.type1;
        pc.sendPackets(new S_ShopSellListCn(pc, npcId));
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this.type1 = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
    }
}
