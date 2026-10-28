package com.lineage.data.item_armor;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Imperius_Tshirt extends ItemExecutor
{
    private static final Log _log;
    private int _r;
    private int _drainingHP_min;
    private int _drainingHP_max;
    
    static {
        _log = LogFactory.getLog(Imperius_Tshirt.class);
    }
    
    public static ItemExecutor get() {
        return new Imperius_Tshirt();
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
                        pc.set_Imperius_Tshirt(0, 0, 0);
                        break;
                    }
                    case 1: {
                        pc.set_Imperius_Tshirt(this._r, this._drainingHP_min, this._drainingHP_max);
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            Imperius_Tshirt._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._r = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._drainingHP_min = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._drainingHP_max = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
    }
}
