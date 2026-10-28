package com.lineage.data.item_etcitem;

import java.util.Iterator;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Lamp_Oil extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Lamp_Oil();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        for (L1ItemInstance lightItem : pc.getInventory().getItems()) {
            if (lightItem.getItem().getItemId() == 40002) {
                lightItem.setRemainingTime(item.getItem().getLightFuel());
                pc.sendPackets(new S_ItemName(lightItem));
                pc.sendPackets(new S_ServerMessage(230));
                break;
            }
        }
        pc.getInventory().removeItem(item, 1L);
    }
}
