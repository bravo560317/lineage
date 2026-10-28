package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class tradeokone extends ItemExecutor
{
    private tradeokone() {
    }
    
    public static ItemExecutor get() {
        return new tradeokone();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else if (tgItem.isEquipped()) {
            pc.sendPackets(new S_ServerMessage("\\fR你必須先解除物品裝備!"));
            return;
        }
        else if (tgItem.getItem().isTradable()) {
            pc.sendPackets(new S_ServerMessage("\\fR沒發生什麼事情!"));
            return;
        }
        else if (tgItem.getproctect10()) {
            pc.sendPackets(new S_ServerMessage("目前該裝備已經可以交易"));
            return;
        }
        else {
            tgItem.setproctect10(true);
            pc.sendPackets(new S_ItemStatus(tgItem));
            pc.getInventory().saveItem(tgItem, 4);
            pc.sendPackets(new S_ServerMessage("該裝備已賦予可交易限制次數1次。"));
            pc.getInventory().removeItem(item, 1L);
            return;
        }
    }
}
