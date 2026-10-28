package com.lineage.data.item_armor;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class MaiteSi_Earring extends ItemExecutor
{
    private static final Log _log;
    private int _uhp_number;
    
    static {
        _log = LogFactory.getLog(MaiteSi_Earring.class);
    }
    
    public MaiteSi_Earring() {
        this._uhp_number = 0;
    }
    
    public static ItemExecutor get() {
        return new MaiteSi_Earring();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            if (item == null) {
                return;
            }
            else if (pc == null) {
                return;
            }
            else {
                switch (data[0]) {
                    case 0: {
                        pc.add_uhp_number(-this._uhp_number);
                        break;
                    }
                    case 1: {
                        pc.add_uhp_number(this._uhp_number);
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            MaiteSi_Earring._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._uhp_number = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
    }
}
