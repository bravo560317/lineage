package com.lineage.data.item_etcitem.shop;

import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Up_hm2 extends ItemExecutor
{
    private static final Log _log;
    private int _value;
    private int _limit_count;
    
    static {
        _log = LogFactory.getLog(Up_hm2.class);
    }
    
    public static ItemExecutor get() {
        return new Up_hm2();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.get_other().get_addmp() >= this._value * this._limit_count) {
            pc.sendPackets(new S_SystemMessage("已超過可用數量上限: " + this._limit_count + "個。"));
            return;
        }
        else {
            pc.getInventory().removeItem(item, 1L);
            pc.get_other().set_addmp(pc.get_other().get_addmp() + this._value);
            pc.addMaxMp(this._value);
            pc.sendPackets(new S_MPUpdate(pc));
            try {
                pc.save();
            }
            catch (Exception e) {
                Up_hm2._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._value = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._limit_count = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
    }
}
