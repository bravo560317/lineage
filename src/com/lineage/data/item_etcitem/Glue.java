package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.config.ConfigRate;
import java.util.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Glue extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Glue();
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
            int diaryId = item2.getItem().getItemId();
            Random random = new Random();
            if (itemId == 41036) {
                if (diaryId >= 41038 && 41047 >= diaryId) {
                    pc.getInventory().removeItem(item2, 1L);
                    pc.getInventory().removeItem(item, 1L);
                    if (random.nextInt(99) + 1 <= ConfigRate.CREATE_CHANCE_DIARY) {
                        CreateNewItem.createNewItem(pc, diaryId + 10, 1L);
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(158, item2.getName()));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(79));
                }
            }
            return;
        }
    }
}
