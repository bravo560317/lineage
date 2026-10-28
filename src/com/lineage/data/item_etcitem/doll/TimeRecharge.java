package com.lineage.data.item_etcitem.doll;

import com.lineage.server.templates.L1Doll;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.DollPowerTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class TimeRecharge extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(TimeRecharge.class);
    }
    
    public static ItemExecutor get() {
        return new TimeRecharge();
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
                else {
                    L1Doll doll = DollPowerTable.get().get_type(tgItem.getItemId());
                    if (doll == null) {
                        pc.sendPackets(new S_ServerMessage(2477));
                        return;
                    }
                    else if (tgItem.getRemainingTime() > 1800) {
                        pc.sendPackets(new S_ServerMessage(3331));
                        return;
                    }
                    else if (tgItem.getItem().getMaxUseTime() <= 0) {
                        pc.sendPackets(new S_ServerMessage(3329));
                        return;
                    }
                    else {
                        pc.getInventory().removeItem(item, 1L);
                        int time = 0;
                        switch (item.getItemId()) {
                            case 92381: {
                                time = 18000;
                                break;
                            }
                            case 92382: {
                                time = 36000;
                                break;
                            }
                            case 92383: {
                                time = 86400;
                                break;
                            }
                            case 92384: {
                                time = 360000;
                                break;
                            }
                        }
                        tgItem.setRemainingTime(time);
                        pc.getInventory().updateItem(tgItem, 256);
                    }
                }
            }
            catch (Exception e) {
                TimeRecharge._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
}
