package com.lineage.data.item_etcitem.shop;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class UserColorD extends ItemExecutor
{
    private static final Log _log;
    private int _count;
    
    static {
        _log = LogFactory.getLog(UserColorD.class);
    }
    
    public static ItemExecutor get() {
        return new UserColorD();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.getInventory().removeItem(item, 1L);
        pc.addLawful(-this._count);
        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 198));
        pc.onChangeLawful();
        try {
            pc.save();
        }
        catch (Exception e) {
            UserColorD._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._count = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
    }
}
