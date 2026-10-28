package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Food1 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Food1();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        pc.getInventory().removeItem(item, 1L);
        short foodvolume1 = (short)(item.getItem().getFoodVolume() / 10);
        short foodvolume2 = 0;
        if (foodvolume1 <= 0) {
            foodvolume1 = 5;
        }
        if (pc.get_food() < 225) {
            foodvolume2 = (short)(pc.get_food() + foodvolume1);
            if (foodvolume2 > 255) {
                foodvolume2 = 255;
            }
            pc.set_food((int)foodvolume2);
            pc.sendPackets(new S_PacketBox(11, (int)(short)pc.get_food()));
        }
        if (itemId == 40057) {
            pc.setSkillEffect(1012, 0);
        }
        pc.sendPackets(new S_ServerMessage(76, item.getItem().getNameId()));
    }
}
