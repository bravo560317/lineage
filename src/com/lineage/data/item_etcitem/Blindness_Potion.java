package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_CurseBlind;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Blindness_Potion extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Blindness_Potion();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        this.useBlindPotion(pc);
        pc.getInventory().removeItem(item, 1L);
    }
    
    private void useBlindPotion(L1PcInstance pc) {
        L1BuffUtil.cancelAbsoluteBarrier(pc);
        int time = 16;
        if (pc.hasSkillEffect(20)) {
            pc.killSkillEffectTimer(20);
        }
        else if (pc.hasSkillEffect(40)) {
            pc.killSkillEffectTimer(40);
        }
        if (pc.hasSkillEffect(1012)) {
            pc.sendPackets(new S_CurseBlind(2));
        }
        else {
            pc.sendPackets(new S_CurseBlind(1));
        }
        pc.setSkillEffect(20, 16000);
    }
}
