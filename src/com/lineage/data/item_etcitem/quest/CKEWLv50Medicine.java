package com.lineage.data.item_etcitem.quest;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class CKEWLv50Medicine extends ItemExecutor
{
    private CKEWLv50Medicine() {
    }
    
    public static ItemExecutor get() {
        return new CKEWLv50Medicine();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getMapId() != 2000) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            if (pc.hasSkillEffect(4003)) {
                pc.removeSkillEffect(4003);
            }
            if (pc.hasSkillEffect(4006)) {
                pc.removeSkillEffect(4006);
            }
            if (pc.hasSkillEffect(4005)) {
                pc.removeSkillEffect(4005);
            }
            if (pc.hasSkillEffect(4007)) {
                pc.removeSkillEffect(4007);
            }
            if (pc.getWeapon() != null && !pc.isCrown()) {
                pc.getInventory().setEquipped(pc.getWeapon(), false, false, false);
                pc.sendPackets(new S_ServerMessage(1027));
            }
            pc.setSkillEffect(4007, 1500000);
            pc.sendPacketsX8(new S_SkillSound(pc.getId(), 7248));
            pc.sendPackets(new S_ServerMessage(1300));
            pc.getInventory().removeItem(item, 1L);
            return;
        }
    }
}
