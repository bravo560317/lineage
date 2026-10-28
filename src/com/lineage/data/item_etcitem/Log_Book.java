package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Log_Book extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Log_Book();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemobj = data[0];
        int itemId = item.getItemId();
        L1ItemInstance tgItem = pc.getInventory().getItem(itemobj);
        if (tgItem == null) {
            return;
        }
        else {
            int logbookId = tgItem.getItem().getItemId();
            if (logbookId == itemId + 8034) {
                CreateNewItem.createNewItem(pc, logbookId + 2, 1L);
                pc.getInventory().removeItem(tgItem, 1L);
                pc.getInventory().removeItem(item, 1L);
            }
            else {
                pc.sendPackets(new S_ServerMessage(79));
            }
            return;
        }
    }
}
