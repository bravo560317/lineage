package com.lineage.data.item_etcitem.quest;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Holy_WaterEva extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Holy_WaterEva();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.hasSkillEffect(1013) || pc.hasSkillEffect(1014)) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            if (pc.hasSkillEffect(1014)) {
                pc.removeSkillEffect(1014);
            }
            pc.setSkillEffect(1015, 900000);
            pc.sendPacketsX8(new S_SkillSound(pc.getId(), 190));
            pc.sendPackets(new S_ServerMessage(1140));
            pc.getInventory().removeItem(item, 1L);
            return;
        }
    }
}
