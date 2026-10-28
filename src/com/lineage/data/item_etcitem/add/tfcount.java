package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class tfcount extends ItemExecutor
{
    private static final Log _log;
    private int _count;
    
    static {
        _log = LogFactory.getLog(tfcount.class);
    }
    
    private tfcount() {
        this._count = 0;
    }
    
    public static ItemExecutor get() {
        return new tfcount();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (this._count == 0) {
            return;
        }
        else if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else {
            pc.getInventory().removeItem(item, 1L);
            pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() + this._count);
            pc.sendPackets(new S_ServerMessage("\\aD目前總天賦:" + pc.get_other2().get_TfCount()));
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._count = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
    }
}
