package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.config.ConfigRate;
import java.util.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Purification_Potion extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Purification_Potion();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemobj = data[0];
        L1ItemInstance item2 = pc.getInventory().getItem(itemobj);
        if (item2 == null) {
            return;
        }
        else {
            Random random = new Random();
            int earingId = item2.getItem().getItemId();
            if (earingId >= 40987 && 40989 >= earingId) {
                if (random.nextInt(100) < ConfigRate.CREATE_CHANCE_RECOLLECTION) {
                    CreateNewItem.createNewItem(pc, earingId + 186, 1L);
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
