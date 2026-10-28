package com.lineage.data.npc.mob;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class E_Event1030 extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(E_Event1030.class);
        E_Event1030._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new E_Event1030();
    }
    
    @Override
    public int type() {
        return 4;
    }
    
    @Override
    public void attack(L1PcInstance pc, L1NpcInstance npc) {
        try {
            int i = E_Event1030._random.nextInt(100);
            if (i >= 0 && i <= 1) {
                if (pc.hasSkillEffect(4011)) {
                    return;
                }
                else if (pc.hasSkillEffect(4012)) {
                    return;
                }
                else {
                    pc.sendPacketsX8(new S_SkillSound(pc.getId(), 7782));
                    pc.setSkillEffect(4012, 12000);
                }
            }
            else if (i >= 2 && i <= 5) {
                if (npc.isremovearmor()) {
                    return;
                }
                else if (pc.hasSkillEffect(4011)) {
                    return;
                }
                else if (pc.hasSkillEffect(4012)) {
                    return;
                }
                else {
                    pc.sendPacketsX8(new S_SkillSound(pc.getId(), 7781));
                    pc.setSkillEffect(4011, 12000);
                    npc.set_removearmor(true);
                }
            }
            else if (i >= 94 && i <= 99) {
                if (npc.isremovearmor()) {
                    return;
                }
                else if (pc.hasItemDelay(500)) {
                    return;
                }
                else {
                    pc.getInventory().takeoffEquip(945);
                    pc.sendPackets(new S_ServerMessage(1356));
                    pc.sendPackets(new S_ServerMessage(1027));
                    npc.set_removearmor(true);
                }
            }
        }
        catch (Exception e) {
            E_Event1030._log.error(e.getLocalizedMessage(), e);
        }
    }
}
