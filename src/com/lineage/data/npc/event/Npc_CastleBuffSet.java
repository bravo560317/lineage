package com.lineage.data.npc.event;

import com.lineage.server.templates.L1Skills;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_CastleBuffSet extends NpcExecutor
{
    private static final Log _log;
    private static final int[] allBuffSkill;
    
    static {
        _log = LogFactory.getLog(Npc_CastleBuffSet.class);
        allBuffSkill = new int[] { 26, 42, 48 };
    }
    
    public static NpcExecutor get() {
        return new Npc_CastleBuffSet();
    }
    
    @Override
    public int type() {
        return 1;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.getInventory().consumeItem(40308, 1000L)) {
                for (int i = 0; i < Npc_CastleBuffSet.allBuffSkill.length; ++i) {
                    L1Skills skill = SkillsTable.get().getTemplate(Npc_CastleBuffSet.allBuffSkill[i]);
                    L1SkillUse skillUse = new L1SkillUse();
                    skillUse.handleCommands(pc, Npc_CastleBuffSet.allBuffSkill[i], pc.getId(), pc.getX(), pc.getY(), skill.getBuffDuration(), 4);
                }
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_done"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_adena"));
            }
        }
        catch (Exception e) {
            Npc_CastleBuffSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
