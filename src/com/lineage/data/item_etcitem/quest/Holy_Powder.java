package com.lineage.data.item_etcitem.quest;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Holy_Powder extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Holy_Powder();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.hasSkillEffect(1015)) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            if (pc.hasSkillEffect(1013)) {
                pc.removeSkillEffect(1013);
            }
            pc.setSkillEffect(1014, 900000);
            pc.sendPacketsX8(new S_SkillSound(pc.getId(), 190));
            pc.sendPackets(new S_ServerMessage(1142));
            pc.getInventory().removeItem(item, 1L);
            return;
        }
    }
}
