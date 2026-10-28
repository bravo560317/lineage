package com.lineage.data.npc.event;

import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.data.event.MagicianSet;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_BuffSet extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_BuffSet.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_BuffSet();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_01"));
        }
        catch (Exception e) {
            Npc_BuffSet._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            if (cmd.equalsIgnoreCase("a")) {
                if (pc.getInventory().consumeItem(MagicianSet.ITEM_ID, (long)MagicianSet.ITEM_COUNT)) {
                    L1SkillUse skillUse = new L1SkillUse();
                    skillUse.handleCommands(pc, 26, pc.getId(), pc.getX(), pc.getY(), 0, 4);
                    skillUse.handleCommands(pc, 42, pc.getId(), pc.getX(), pc.getY(), 0, 4);
                    skillUse.handleCommands(pc, 48, pc.getId(), pc.getX(), pc.getY(), 0, 4);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_done"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_adena"));
                }
            }
            else if (cmd.equalsIgnoreCase("0")) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_01"));
            }
        }
        catch (Exception e) {
            Npc_BuffSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
