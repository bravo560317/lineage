package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ItemAction extends ItemExecutor
{
    private String _htmlid;
    
    public static ItemExecutor get() {
        return new ItemAction();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), this._htmlid));
        pc.setPolyScrol2(item);
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._htmlid = set[1];
        }
        catch (Exception ex) {}
    }
}
