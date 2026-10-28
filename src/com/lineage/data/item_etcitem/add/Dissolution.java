package com.lineage.data.item_etcitem.add;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.datatables.ResolventTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Dissolution extends ItemExecutor
{
    private Dissolution() {
    }
    
    public static ItemExecutor get() {
        return new Dissolution();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemobj = data[0];
        L1ItemInstance item2 = pc.getInventory().getItem(itemobj);
        if (item2 == null) {
            return;
        }
        else {
            this.useResolvent(pc, item2, item);
            return;
        }
    }
    
    private void useResolvent(L1PcInstance pc, L1ItemInstance item, L1ItemInstance resolvent) {
        Random _random = new Random();
        if (item == null || resolvent == null) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            if (item.getItem().getType2() == 1 || item.getItem().getType2() == 2) {
                if (item.getEnchantLevel() != 0) {
                    pc.sendPackets(new S_ServerMessage(1161));
                    return;
                }
                else if (item.isEquipped()) {
                    pc.sendPackets(new S_ServerMessage(1161));
                    return;
                }
            }
            long crystalCount = (long)ResolventTable.get().getCrystalCount(item.getItem().getItemId());
            if (crystalCount == 0L) {
                pc.sendPackets(new S_ServerMessage(1161));
                return;
            }
            else {
                int rnd = _random.nextInt(100) + 1;
                if (rnd >= 1 && rnd <= 50) {
                    crystalCount *= 1L;
                }
                else if (rnd >= 51 && rnd <= 90) {
                    crystalCount = (long)((double)crystalCount * 1.5);
                }
                else if (rnd >= 91 && rnd <= 100) {
                    crystalCount *= 2L;
                }
                if (crystalCount != 0L) {
                    CreateNewItem.createNewItem(pc, 41246, crystalCount);
                }
                pc.getInventory().removeItem(item, 1L);
                pc.getInventory().removeItem(resolvent, 1L);
                return;
            }
        }
    }
}
