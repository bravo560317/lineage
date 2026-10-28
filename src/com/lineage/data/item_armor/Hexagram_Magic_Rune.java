package com.lineage.data.item_armor;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Hexagram_Magic_Rune extends ItemExecutor
{
    private static final Log _log;
    private int _r;
    private int _hp_min;
    private int _hp_max;
    private int _gfx;
    
    static {
        _log = LogFactory.getLog(Hexagram_Magic_Rune.class);
    }
    
    public Hexagram_Magic_Rune() {
        this._r = 0;
        this._hp_min = 0;
        this._hp_max = 0;
        this._gfx = 0;
    }
    
    public static ItemExecutor get() {
        return new Hexagram_Magic_Rune();
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
                        pc.set_Hexagram_Magic_Rune(0, 0, 0, 0);
                        break;
                    }
                    case 1: {
                        pc.set_Hexagram_Magic_Rune(this._r, this._hp_min, this._hp_max, this._gfx);
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            Hexagram_Magic_Rune._log.error(e.getLocalizedMessage(), e);
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
        try {
            this._gfx = Integer.parseInt(set[4]);
        }
        catch (Exception ex4) {}
    }
}
