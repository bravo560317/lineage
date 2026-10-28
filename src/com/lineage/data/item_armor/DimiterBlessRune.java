package com.lineage.data.item_armor;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class DimiterBlessRune extends ItemExecutor
{
    private static final Log _log;
    private int _r;
    private int _mp_min;
    private int _mp_max;
    private int _r2;
    private int _time;
    
    static {
        _log = LogFactory.getLog(DimiterBlessRune.class);
    }
    
    public static ItemExecutor get() {
        return new DimiterBlessRune();
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
                        pc.set_DimiterBless(0, 0, 0, 0, 0);
                        break;
                    }
                    case 1: {
                        pc.set_DimiterBless(this._r, this._mp_min, this._mp_max, this._r2, this._time);
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            DimiterBlessRune._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._r = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._mp_min = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._mp_max = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
        try {
            this._r2 = Integer.parseInt(set[4]);
        }
        catch (Exception ex4) {}
        try {
            this._time = Integer.parseInt(set[5]);
        }
        catch (Exception ex5) {}
    }
}
