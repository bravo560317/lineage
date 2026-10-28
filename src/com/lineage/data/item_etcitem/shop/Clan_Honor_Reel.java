package com.lineage.data.item_etcitem.shop;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Clan_Honor_Reel extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Clan_Honor_Reel.class);
    }
    
    public static ItemExecutor get() {
        return new Clan_Honor_Reel();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else if (pc.getLevel() >= 70) {
            return;
        }
        else {
            pc.addExp(813177894L);
            pc.getInventory().removeItem(item, 1L);
            pc.sendPacketsX8(new S_SkillSound(pc.getId(), 9714));
            try {
                pc.save();
            }
            catch (Exception e) {
                Clan_Honor_Reel._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
}
