package com.lineage.data.item_etcitem.teleport;

import com.lineage.server.utils.Random;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class NewOM_TeleportRune_Mutant extends ItemExecutor
{
    public static ItemExecutor get() {
        return new NewOM_TeleportRune_Mutant();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.hasSkillEffect(4017)) {
            pc.sendPackets(new S_ServerMessage(1413));
            return;
        }
        else {
            int itemId = item.getItemId();
            pc.getInventory().removeItem(item, 1L);
            L1ItemInstance item2;
            if (Random.nextInt(100) + 1 <= 80) {
                item2 = pc.getInventory().storeItem(itemId - 30, 1L);
            }
            else {
                item2 = pc.getInventory().storeItem(itemId - 20, 1L);
            }
            if (item2 != null) {
                pc.sendPackets(new S_ServerMessage(403, item2.getLogName()));
            }
            return;
        }
    }
}
