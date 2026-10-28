package com.lineage.data.item_etcitem.quest;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.IllusionistLv45_1;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ThoughtFragment1 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new ThoughtFragment1();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemobj = data[0];
        L1ItemInstance item2 = pc.getInventory().getItem(itemobj);
        if (item2 != null) {
            if (item2.getItemId() == 49198) {
                pc.getInventory().removeItem(item, 1L);
                pc.getInventory().removeItem(item2, 1L);
                if (pc.getQuest().isStart(IllusionistLv45_1.QUEST.get_id())) {
                    CreateNewItem.createNewItem(pc, 49200, 1L);
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(79));
            }
        }
        else {
            pc.sendPackets(new S_ServerMessage(79));
        }
    }
}
