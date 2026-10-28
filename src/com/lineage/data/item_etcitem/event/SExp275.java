package com.lineage.data.item_etcitem.event;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class SExp275 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new SExp275();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else {
            if (L1BuffUtil.cancelExpSkill_2(pc)) {
                int time = 600;
                if (pc.getInventory().removeItem(item, 1L) == 1L) {
                    pc.setSkillEffect(5005, time * 1000);
                    pc.sendPackets(new S_ServerMessage("第二段經驗值提升275%(600秒)"));
                    pc.sendPackets(new S_SkillSound(pc.getId(), 750));
                }
            }
            return;
        }
    }
}
