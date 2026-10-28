package com.lineage.data.item_etcitem;

import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class onlygm extends ItemExecutor
{
    private onlygm() {
    }
    
    public static ItemExecutor get() {
        return new onlygm();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int spellsc_objid = data[0];
        int itemId = item.getItemId();
        L1Object target = World.get().findObject(spellsc_objid);
        L1PcInstance target_pc = (L1PcInstance)target;
        if (itemId == 240309) {
            target_pc.setSkillEffect(9990, 21600000);
            L1Teleport.teleport(target_pc, 32768, 32773, (short)7787, 5, true);
            target_pc.sendPackets(new S_SystemMessage("因為您未通過線上GM驗證，即刻起將入監6個小時。"));
            target_pc.sendPackets(new S_ServerMessage("\\fX請保持角色在線以扣除監禁時間。"));
        }
        else if (itemId == 240310) {
            target_pc.setSkillEffect(9990, 43200000);
            L1Teleport.teleport(target_pc, 32768, 32773, (short)7787, 5, true);
            target_pc.sendPackets(new S_SystemMessage("因為您未通過線上GM驗證，即刻起將入監12個小時。"));
            target_pc.sendPackets(new S_ServerMessage("\\fX請保持角色在線以扣除監禁時間。"));
        }
        else if (itemId == 240311) {
            target_pc.setSkillEffect(9990, 86400000);
            L1Teleport.teleport(target_pc, 32768, 32773, (short)7787, 5, true);
            target_pc.sendPackets(new S_SystemMessage("因為您未通過線上GM驗證，即刻起將入監24個小時。"));
            target_pc.sendPackets(new S_ServerMessage("\\fX請保持角色在線以扣除監禁時間。"));
        }
        else if (itemId == 240312 && target_pc.hasSkillEffect(9990)) {
            target_pc.killSkillEffectTimer(9990);
            target_pc.sendPackets(new S_ServerMessage("\\fX您的監禁時間已解除。"));
        }
    }
}
