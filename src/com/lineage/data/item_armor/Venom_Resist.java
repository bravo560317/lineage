package com.lineage.data.item_armor;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Venom_Resist extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Venom_Resist.class);
    }
    
    public static ItemExecutor get() {
        return new Venom_Resist();
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
                        pc.set_venom_resist(-1);
                        break;
                    }
                    case 1: {
                        pc.set_venom_resist(1);
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            Venom_Resist._log.error(e.getLocalizedMessage(), e);
        }
    }
}
