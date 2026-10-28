package com.lineage.data.item_etcitem;

import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skeleton_Change_Reel extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skeleton_Change_Reel();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        this.usePolyPotion(pc, itemId);
        pc.getInventory().removeItem(item, 1L);
    }
    
    private void usePolyPotion(L1PcInstance pc, int itemId) {
        int awakeSkillId = pc.getAwakeSkillId();
        if (awakeSkillId == 185 || awakeSkillId == 190 || awakeSkillId == 195) {
            pc.sendPackets(new S_ServerMessage(1384));
            return;
        }
        else {
            int polyId = 0;
            if (itemId == 41143) {
                polyId = 6086;
            }
            else if (itemId == 41144) {
                polyId = 6087;
            }
            else if (itemId == 41145) {
                polyId = 6088;
            }
            L1PolyMorph.doPoly(pc, polyId, 1800, 1);
            return;
        }
    }
}
