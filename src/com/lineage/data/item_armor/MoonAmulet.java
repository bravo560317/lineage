package com.lineage.data.item_armor;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class MoonAmulet extends ItemExecutor
{
    private static final Log _log;
    private int _MoonAmulet_rnd;
    private int _MoonAmulet_dmg_min;
    private int _MoonAmulet_dmg_max;
    private int _MoonAmulet_gfxid;
    
    static {
        _log = LogFactory.getLog(MoonAmulet.class);
    }
    
    public static ItemExecutor get() {
        return new MoonAmulet();
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
                        pc.set_MoonAmulet(0, 0, 0, 0);
                        break;
                    }
                    case 1: {
                        pc.set_MoonAmulet(this._MoonAmulet_rnd, this._MoonAmulet_dmg_min, this._MoonAmulet_dmg_max, this._MoonAmulet_gfxid);
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            MoonAmulet._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._MoonAmulet_rnd = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._MoonAmulet_dmg_min = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._MoonAmulet_dmg_max = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
        try {
            this._MoonAmulet_gfxid = Integer.parseInt(set[4]);
        }
        catch (Exception ex4) {}
    }
}
