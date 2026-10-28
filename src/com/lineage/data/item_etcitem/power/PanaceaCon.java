package com.lineage.data.item_etcitem.power;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.william.ConDmg;
import com.lineage.config.ConfigAlt;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class PanaceaCon extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(PanaceaCon.class);
    }
    
    public static ItemExecutor get() {
        return new PanaceaCon();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getBaseCon() < ConfigAlt.POWERMEDICINE) {
            if (pc.getElixirStats() < ConfigAlt.MEDICINE) {
                pc.addBaseCon(1);
                pc.setElixirStats(pc.getElixirStats() + 1);
                int condmg = ConDmg.getConHitSkill(pc, (int)pc.getCon());
                if (condmg != 0) {
                    pc.addMaxHp(condmg);
                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                }
                pc.getInventory().removeItem(item, 1L);
                pc.sendPackets(new S_OwnCharStatus2(pc));
                try {
                    pc.save();
                }
                catch (Exception e) {
                    PanaceaCon._log.error(e.getLocalizedMessage(), e);
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
