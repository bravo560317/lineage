package com.lineage.data.item_etcitem.extra;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ProtectionScroll extends ItemExecutor
{
    public static ItemExecutor get() {
        return new ProtectionScroll();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem != null) {
            if (tgItem.getItem().get_safeenchant() <= -1) {
                pc.sendPackets(new S_ServerMessage(1309));
                return;
            }
            else if (tgItem.getproctect()) {
                pc.sendPackets(new S_ServerMessage(1300));
                return;
            }
            else if (tgItem.getItem().getType2() == 0) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else {
                int use_type = tgItem.getItem().getUseType();
                if (use_type == 23 || use_type == 24 || use_type == 37 || use_type == 40) {
                    pc.sendPackets(new S_ServerMessage(1309));
                    return;
                }
                else {
                    tgItem.setproctect(true);
                    pc.sendPackets(new S_ServerMessage(1308, tgItem.getLogName()));
                    pc.getInventory().removeItem(item, 1L);
                }
            }
        }
    }
}
