package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.pr_type_name;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Prestige extends ItemExecutor
{
    private static final Log _log;
    private int _count;
    private int _gfxid;
    
    static {
        _log = LogFactory.getLog(Prestige.class);
    }
    
    private Prestige() {
        this._count = 0;
        this._gfxid = 0;
    }
    
    public static ItemExecutor get() {
        return new Prestige();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (this._count == 0) {
            return;
        }
        else if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else {
            pc.getInventory().removeItem(item, 1L);
            pc.addPrestige(this._count);
            if (this._gfxid != 0) {
                pc.sendPacketsX8(new S_SkillSound(pc.getId(), this._gfxid));
            }
            pc.sendPackets(new S_ServerMessage("\\aD" + pr_type_name._1 + "增加+" + this._count + "\\aE目前總" + pr_type_name._1 + ":" + pc.getPrestige()));
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._count = Integer.parseInt(set[1]);
            if (set[2] != null) {
                this._gfxid = Integer.parseInt(set[2]);
            }
        }
        catch (Exception ex) {}
    }
}
