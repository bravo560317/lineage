package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.config.ConfigRate;
import java.util.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Mystical_Ption extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Mystical_Ption();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        int itemobj = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(itemobj);
        if (tgItem == null) {
            return;
        }
        else {
            Random random = new Random();
            int earing2Id = tgItem.getItem().getItemId();
            int potion1 = 0;
            int potion2 = 0;
            if (earing2Id >= 41173 && 41184 >= earing2Id) {
                if (itemId == 40926) {
                    potion1 = 247;
                    potion2 = 249;
                }
                else if (itemId == 40927) {
                    potion1 = 249;
                    potion2 = 251;
                }
                else if (itemId == 40928) {
                    potion1 = 251;
                    potion2 = 253;
                }
                else if (itemId == 40929) {
                    potion1 = 253;
                    potion2 = 255;
                }
                if (earing2Id >= itemId + potion1 && itemId + potion2 >= earing2Id) {
                    if (random.nextInt(99) + 1 < ConfigRate.CREATE_CHANCE_MYSTERIOUS) {
                        CreateNewItem.createNewItem(pc, earing2Id - 12, 1L);
                        pc.getInventory().removeItem(tgItem, 1L);
                        pc.getInventory().removeItem(item, 1L);
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(160, tgItem.getName()));
                        pc.getInventory().removeItem(item, 1L);
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(79));
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(79));
            }
            return;
        }
    }
}
