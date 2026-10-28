package com.lineage.data.item_etcitem.shop;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class TimeUpdate1 extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(TimeUpdate1.class);
    }
    
    public static ItemExecutor get() {
        return new TimeUpdate1();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else {
            try {
                int targObjId = data[0];
                L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
                if (tgItem == null) {
                    return;
                }
                else if (tgItem.getItem().getType2() == 0) {
                    pc.sendPackets(new S_ServerMessage(79));
                    return;
                }
                else if (tgItem.getItem().getMaxUseTime() <= 0) {
                    pc.sendPackets(new S_ServerMessage(79));
                    return;
                }
                else {
                    pc.getInventory().removeItem(item, 1L);
                    int time = tgItem.getItem().getMaxUseTime();
                    tgItem.setRemainingTime(time);
                    pc.getInventory().updateItem(tgItem, 256);
                }
            }
            catch (Exception e) {
                TimeUpdate1._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
}
