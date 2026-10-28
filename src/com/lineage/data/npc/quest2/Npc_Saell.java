package com.lineage.data.npc.quest2;

import com.lineage.server.model.skill.skillmode.SkillMode;
import com.lineage.server.model.skill.L1SkillMode;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Saell extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Saell.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Saell();
    }
    
    @Override
    public int type() {
        return 1;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.hasSkillEffect(4009)) {
                pc.removeSkillEffect(4009);
            }
            if (pc.hasSkillEffect(4018)) {
                pc.removeSkillEffect(4018);
            }
            if (pc.hasSkillEffect(4010)) {
                return;
            }
            else {
                pc.sendPacketsX8(new S_SkillSound(pc.getId(), 7680));
                SkillMode mode = L1SkillMode.get().getSkill(4010);
                if (mode != null) {
                    try {
                        mode.start(pc, null, null, 2400);
                    }
                    catch (Exception e) {
                        Npc_Saell._log.error(e.getLocalizedMessage(), e);
                    }
                }
            }
        }
        catch (Exception e2) {
            Npc_Saell._log.error(e2.getLocalizedMessage(), e2);
        }
    }
}
