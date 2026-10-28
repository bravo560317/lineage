package com.lineage.data.item_etcitem.brave;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Potion_6 extends ItemExecutor
{
    private Potion_6() {
    }
    
    public static ItemExecutor get() {
        return new Potion_6();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else if (pc.hasSkillEffect(1017)) {
            pc.sendPackets(new S_ServerMessage(1413));
            return;
        }
        else {
            if (L1BuffUtil.stopPotion(pc)) {
                this.useBravePotion(pc);
                pc.getInventory().removeItem(item, 1L);
            }
            return;
        }
    }
    
    private void useBravePotion(L1PcInstance pc) {
        L1BuffUtil.cancelAbsoluteBarrier(pc);
        int time = 600;
        L1BuffUtil.braveStart(pc);
        int mode = 1;
        int skillMode = 1000;
        pc.sendPackets(new S_SkillBrave(pc.getId(), mode, 600));
        pc.broadcastPacketAll(new S_SkillBrave(pc.getId(), mode, 0));
        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 751));
        pc.setSkillEffect(skillMode, 600000);
        pc.setBraveSpeed(1);
    }
}
