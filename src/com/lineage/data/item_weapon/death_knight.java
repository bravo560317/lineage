package com.lineage.data.item_weapon;

import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class death_knight extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(death_knight.class);
    }
    
    public static ItemExecutor get() {
        return new death_knight();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            switch (data[0]) {
                case 0: {
                    L1PolyMorph.undoPoly(pc);
                    break;
                }
                case 1: {
                    L1PolyMorph.doPoly(pc, 12232, 0, 1);
                    break;
                }
            }
        }
        catch (Exception e) {
            death_knight._log.error(e.getLocalizedMessage(), e);
        }
    }
}
