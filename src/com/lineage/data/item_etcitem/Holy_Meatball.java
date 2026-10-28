package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Holy_Meatball extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Holy_Meatball();
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
            int time = 300;
            if (item.getItemId() == 85003) {
                time = 1800;
            }
            if (pc.hasSkillEffect(1027)) {
                pc.killSkillEffectTimer(1027);
                pc.removeSkillEffect(1027);
            }
            pc.sendPackets(new S_SkillSound(pc.getId(), 7612));
            pc.sendPackets(new S_PacketBox(154, time, 20));
            pc.setSkillEffect(1027, time * 1000);
            pc.getInventory().removeItem(item, 1L);
            pc.sendPackets(new S_ServerMessage(3673));
            return;
        }
    }
}
