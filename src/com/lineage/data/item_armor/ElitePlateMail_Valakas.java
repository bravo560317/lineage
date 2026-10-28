package com.lineage.data.item_armor;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class ElitePlateMail_Valakas extends ItemExecutor
{
    private static final Log _log;
    private int _r;
    private int _dmg_min;
    private int _dmg_max;
    
    static {
        _log = LogFactory.getLog(ElitePlateMail_Valakas.class);
    }
    
    public static ItemExecutor get() {
        return new ElitePlateMail_Valakas();
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
                        pc.set_elitePlateMail_Valakas(0, 0, 0);
                        break;
                    }
                    case 1: {
                        pc.set_elitePlateMail_Valakas(this._r, this._dmg_min, this._dmg_max);
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            ElitePlateMail_Valakas._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._r = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._dmg_min = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._dmg_max = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
    }
}
