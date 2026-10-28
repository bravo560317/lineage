package com.lineage.data.item_etcitem.magicreel;

import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ImmuntToHarm extends ItemExecutor
{
    public static ItemExecutor get() {
        return new ImmuntToHarm();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc == null) {
            return;
        }
        else if (item == null) {
            return;
        }
        else {
            int useCount = 1;
            if (pc.getInventory().removeItem(item, 1L) >= 1L) {
                L1BuffUtil.cancelAbsoluteBarrier(pc);
                int skillid = 68;
                L1SkillUse l1skilluse = new L1SkillUse();
                l1skilluse.handleCommands(pc, 68, pc.getId(), 0, 0, 0, 2);
            }
            return;
        }
    }
}
