package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Disintoxicat_Potion extends ItemExecutor
{
    private Disintoxicat_Potion() {
    }
    
    public static ItemExecutor get() {
        return new Disintoxicat_Potion();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.hasSkillEffect(71)) {
            pc.sendPackets(new S_ServerMessage(698));
        }
        else {
            L1BuffUtil.cancelAbsoluteBarrier(pc);
            pc.sendPacketsX8(new S_SkillSound(pc.getId(), 192));
            pc.getInventory().removeItem(item, 1L);
            pc.curePoison();
        }
    }
}
