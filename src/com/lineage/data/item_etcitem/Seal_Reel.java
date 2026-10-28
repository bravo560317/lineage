package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Seal_Reel extends ItemExecutor
{
    private Seal_Reel() {
    }
    
    public static ItemExecutor get() {
        return new Seal_Reel();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemobj = data[0];
        L1ItemInstance item2 = pc.getInventory().getItem(itemobj);
        if (item2 == null) {
            return;
        }
        else {
            int lockItemId = item2.getItem().getItemId();
            if ((item2 != null && item2.getItem().getType2() == 1) || item2.getItem().getType2() == 2 || (item2.getItem().getType2() == 0 && (lockItemId == 40314 || lockItemId == 40316))) {
                if (item2.getBless() >= 0 && item2.getBless() <= 3) {
                    int bless = 1;
                    switch (item2.getBless()) {
                        case 0: {
                            bless = 128;
                            break;
                        }
                        case 1: {
                            bless = 129;
                            break;
                        }
                        case 2: {
                            bless = 130;
                            break;
                        }
                        case 3: {
                            bless = 131;
                            break;
                        }
                    }
                    item2.setBless(bless);
                    pc.getInventory().updateItem(item2, 512);
                    pc.getInventory().saveItem(item2, 512);
                    pc.getInventory().removeItem(item, 1L);
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
