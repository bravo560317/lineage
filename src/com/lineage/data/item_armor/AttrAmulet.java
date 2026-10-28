package com.lineage.data.item_armor;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class AttrAmulet extends ItemExecutor
{
    private static final Log _log;
    private int _AttrAmulet_rnd;
    private int _AttrAmulet_dmg;
    private int _AttrAmulet_gfxid;
    
    static {
        _log = LogFactory.getLog(AttrAmulet.class);
    }
    
    public static ItemExecutor get() {
        return new AttrAmulet();
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
                        pc.set_AttrAmulet(0, 0, 0);
                        break;
                    }
                    case 1: {
                        pc.set_AttrAmulet(this._AttrAmulet_rnd, this._AttrAmulet_dmg, this._AttrAmulet_gfxid);
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            AttrAmulet._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._AttrAmulet_rnd = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._AttrAmulet_dmg = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._AttrAmulet_gfxid = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
    }
}
