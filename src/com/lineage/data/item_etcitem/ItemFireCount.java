package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ItemFireCount extends ItemExecutor
{
    private int _type;
    private int _type1;
    
    private ItemFireCount() {
    }
    
    public static ItemExecutor get() {
        return new ItemFireCount();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else if (pc.getQuburcount() >= this._type1) {
            pc.sendPackets(new S_ServerMessage("\\fU爆氣值已飽和狀態"));
            return;
        }
        else {
            pc.setQuburcount(pc.getQuburcount() + this._type);
            pc.sendPackets(new S_ServerMessage("目前爆氣值:" + pc.getQuburcount()));
            pc.getInventory().removeItem(item, 1L);
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._type = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._type1 = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
    }
}
