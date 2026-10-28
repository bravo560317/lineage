package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Chap_Center extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Chap_Center();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemobj = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(itemobj);
        int targetItemId = tgItem.getItem().getItemId();
        switch (targetItemId) {
            case 49095: {
                if (!pc.getInventory().consumeItem(49092, 1L)) {
                    return;
                }
                else if (!pc.getInventory().consumeItem(targetItemId, 1L)) {
                    return;
                }
                else {
                    CreateNewItem.createNewItem(pc, 49096, 1L);
                    break;
                }
            }
            case 49099: {
                if (!pc.getInventory().consumeItem(49092, 1L)) {
                    return;
                }
                else if (!pc.getInventory().consumeItem(targetItemId, 1L)) {
                    return;
                }
                else {
                    CreateNewItem.createNewItem(pc, 49100, 1L);
                    break;
                }
            }
            case 49274: {
                if (!pc.getInventory().consumeItem(49092, 1L)) {
                    return;
                }
                else if (!pc.getInventory().consumeItem(targetItemId, 1L)) {
                    return;
                }
                else {
                    CreateNewItem.createNewItem(pc, 49284, 1L);
                    break;
                }
            }
            case 49275: {
                if (!pc.getInventory().consumeItem(49092, 1L)) {
                    return;
                }
                else if (!pc.getInventory().consumeItem(targetItemId, 1L)) {
                    return;
                }
                else {
                    CreateNewItem.createNewItem(pc, 49285, 1L);
                    break;
                }
            }
            default: {
                pc.sendPackets(new S_ServerMessage(79));
                break;
            }
        }
    }
}
