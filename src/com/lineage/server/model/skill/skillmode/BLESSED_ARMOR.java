package com.lineage.server.model.skill.skillmode;

import com.lineage.server.timecontroller.pc.ItemTimer;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class BLESSED_ARMOR extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        L1PcInstance pc = (L1PcInstance)cha;
        L1ItemInstance item = pc.getInventory().getItem(pc.getuseitemobjid());
        if (item != null && item.getItem().getType2() == 2 && item.getItem().getType() == 2) {
            pc.sendPackets(new S_ServerMessage(161, item.getLogName(), "$245", "$247"));
            this.setSkillArmorEnchant(pc, 21, integer, item);
        }
        else {
            pc.sendPackets(new S_ServerMessage(79));
        }
        return 0;
    }
    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        return 0;
    }
    
    @Override
    public void stop(L1Character cha) throws Exception {
    }
    
    @Override
    public void start(L1PcInstance pc, Object obj) throws Exception {
    }
    
    private void setSkillArmorEnchant(L1PcInstance pc, int skillId, int skillTime, L1ItemInstance item) {
        ItemTimer itemTimer = new ItemTimer();
        itemTimer.start(item, skillId, skillTime);
    }
}
