package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Hone extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Hone();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemobj = data[0];
        L1ItemInstance item2 = pc.getInventory().getItem(itemobj);
        if (item2 == null) {
            return;
        }
        else {
            if (item2.getItem().getType2() != 0 && item2.get_durability() > 0) {
                pc.getInventory().recoveryDamage(item2);
                String msg0 = item2.getLogName();
                if (item2.get_durability() == 0) {
                    pc.sendPackets(new S_ServerMessage(464, msg0));
                }
                else {
                    pc.sendPackets(new S_ServerMessage(463, msg0));
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(79));
            }
            pc.getInventory().removeItem(item, 1L);
            return;
        }
    }
}
