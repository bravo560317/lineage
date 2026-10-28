package com.lineage.data.item_etcitem;

import com.lineage.server.templates.L1Item;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Scroll_CurseRemoval extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Scroll_CurseRemoval();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        for (L1ItemInstance tgItem : pc.getInventory().getItems()) {
            if (tgItem.getBless() == 2 && tgItem.getBless() < 128 && (tgItem.getItemId() != 240074 || item.getBless() == 0) && (tgItem.getItemId() != 240087 || item.getBless() == 0) && tgItem.getItemId() != 41216) {
                int id_normal = tgItem.getItemId() - 200000;
                L1Item template = ItemTable.get().getTemplate(id_normal);
                if (template != null) {
                    boolean isEun = false;
                    if (pc.getInventory().checkItem(id_normal)) {
                        if (template.isStackable()) {
                            pc.getInventory().removeItem(tgItem, tgItem.getCount());
                            pc.getInventory().storeItem(id_normal, tgItem.getCount());
                        }
                        else {
                            isEun = true;
                        }
                    }
                    else {
                        isEun = true;
                    }
                    if (isEun) {
                        tgItem.setBless(1);
                        tgItem.setItem(template);
                        pc.getInventory().updateItem(tgItem, 576);
                        pc.getInventory().saveItem(tgItem, 576);
                    }
                    else {
                        continue;
                    }
                }
                else {
                    continue;
                }
            }
        }
        pc.getInventory().removeItem(item, 1L);
        pc.sendPackets(new S_ServerMessage(155));
    }
}
