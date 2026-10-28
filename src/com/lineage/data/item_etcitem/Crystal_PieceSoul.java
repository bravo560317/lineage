package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Crystal_PieceSoul extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Crystal_PieceSoul();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        boolean notUse = false;
        switch (itemId) {
            case 40576: {
                if (!pc.isElf()) {
                    notUse = true;
                    break;
                }
                else {
                    break;
                }
            }
            case 40577: {
                if (!pc.isWizard()) {
                    notUse = true;
                    break;
                }
                else {
                    break;
                }
            }
            case 40578: {
                if (!pc.isKnight()) {
                    notUse = true;
                    break;
                }
                else {
                    break;
                }
            }
        }
        if (notUse) {
            pc.sendPackets(new S_ServerMessage(264));
        }
        else {
            String itenName = item.getLogName();
            if (pc.castleWarResult()) {
                pc.sendPackets(new S_ServerMessage(403, itenName));
            }
            else if (pc.getMapId() == 303) {
                pc.sendPackets(new S_ServerMessage(403, itenName));
            }
            else {
                pc.death(null);
                pc.getInventory().removeItem(item, 1L);
                int newItemId = item.getItemId() - 3;
                CreateNewItem.createNewItem(pc, newItemId, 1L);
            }
        }
    }
}
