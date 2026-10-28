package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.config.ConfigRate;
import java.util.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Summoning_Center extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Summoning_Center();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        int itemobj = data[0];
        L1ItemInstance item2 = pc.getInventory().getItem(itemobj);
        if (item2 == null) {
            return;
        }
        else {
            Random _random = new Random();
            if (itemId == 41029) {
                int dantesId = item2.getItem().getItemId();
                if (dantesId >= 41030 && 41034 >= dantesId) {
                    if (_random.nextInt(99) + 1 < ConfigRate.CREATE_CHANCE_DANTES) {
                        CreateNewItem.createNewItem(pc, dantesId + 1, 1L);
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(158, item2.getName()));
                    }
                    pc.getInventory().removeItem(item2, 1L);
                    pc.getInventory().removeItem(item, 1L);
                }
                else {
                    pc.sendPackets(new S_ServerMessage(79));
                }
            }
            return;
        }
    }
}
