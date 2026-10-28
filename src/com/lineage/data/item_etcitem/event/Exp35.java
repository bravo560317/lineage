package com.lineage.data.item_etcitem.event;

import com.lineage.server.serverpackets.S_PacketBoxCooking;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Exp35 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Exp35();
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
            if (L1BuffUtil.cancelExpSkill(pc)) {
                int time = 600;
                pc.setSkillEffect(6672, time * 1000);
                pc.getInventory().removeItem(item, 1L);
                pc.sendPackets(new S_ServerMessage("第一段經驗值提升350%(600秒)"));
                pc.sendPackets(new S_SkillSound(pc.getId(), 750));
                pc.sendPackets(new S_PacketBoxCooking(pc, 32, time));
            }
            return;
        }
    }
}
