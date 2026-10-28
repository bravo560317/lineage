package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Seal_Reel_Relieving extends ItemExecutor
{
    private Seal_Reel_Relieving() {
    }
    
    public static ItemExecutor get() {
        return new Seal_Reel_Relieving();
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
                if (item2.getBless() >= 128 && item2.getBless() <= 131) {
                    int bless = 1;
                    switch (item2.getBless()) {
                        case 128: {
                            bless = 0;
                            break;
                        }
                        case 129: {
                            bless = 1;
                            break;
                        }
                        case 130: {
                            bless = 2;
                            break;
                        }
                        case 131: {
                            bless = 3;
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
