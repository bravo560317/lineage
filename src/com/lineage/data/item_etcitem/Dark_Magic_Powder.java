package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.config.ConfigRate;
import java.util.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Dark_Magic_Powder extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Dark_Magic_Powder();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemobj = data[0];
        L1ItemInstance item2 = pc.getInventory().getItem(itemobj);
        if (item2 == null) {
            return;
        }
        else {
            Random _random = new Random();
            int historybookId = item2.getItem().getItemId();
            if (historybookId >= 41011 && 41018 >= historybookId) {
                if (_random.nextInt(99) + 1 <= ConfigRate.CREATE_CHANCE_HISTORY_BOOK) {
                    CreateNewItem.createNewItem(pc, historybookId + 8, 1L);
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
            return;
        }
    }
}
