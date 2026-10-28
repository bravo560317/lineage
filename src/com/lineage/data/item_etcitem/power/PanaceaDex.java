package com.lineage.data.item_etcitem.power;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.config.ConfigAlt;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class PanaceaDex extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(PanaceaDex.class);
    }
    
    public static ItemExecutor get() {
        return new PanaceaDex();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getBaseDex() < ConfigAlt.POWERMEDICINE) {
            if (pc.getElixirStats() < ConfigAlt.MEDICINE) {
                pc.addBaseDex(1);
                pc.resetBaseAc();
                pc.setElixirStats(pc.getElixirStats() + 1);
                pc.getInventory().removeItem(item, 1L);
                pc.sendPackets(new S_OwnCharStatus2(pc));
                try {
                    pc.save();
                }
                catch (Exception e) {
                    PanaceaDex._log.error(e.getLocalizedMessage(), e);
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(79));
            }
        }
        else {
            pc.sendPackets(new S_ServerMessage(481));
        }
    }
}
