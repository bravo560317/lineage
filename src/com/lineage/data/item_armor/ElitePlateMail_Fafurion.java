package com.lineage.data.item_armor;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class ElitePlateMail_Fafurion extends ItemExecutor
{
    private static final Log _log;
    private int _r;
    private int _hp_min;
    private int _hp_max;
    
    static {
        _log = LogFactory.getLog(ElitePlateMail_Fafurion.class);
    }
    
    public ElitePlateMail_Fafurion() {
        this._r = 0;
        this._hp_min = 0;
        this._hp_max = 0;
    }
    
    public static ItemExecutor get() {
        return new ElitePlateMail_Fafurion();
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
                if (item.getEnchantLevel() >= 7 && item.getEnchantLevel() <= 9) {
                    this._hp_min += item.getEnchantLevel() - 6;
                    this._hp_max += item.getEnchantLevel() - 6;
                }
                switch (data[0]) {
                    case 0: {
                        pc.set_elitePlateMail_Fafurion(0, 0, 0);
                        break;
                    }
                    case 1: {
                        pc.set_elitePlateMail_Fafurion(this._r, this._hp_min, this._hp_max);
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            ElitePlateMail_Fafurion._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._r = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._hp_min = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._hp_max = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
    }
}
