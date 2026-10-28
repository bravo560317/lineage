package com.lineage.server.model.skill.skillmode;

import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class DARK_BLIND extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.setSkillEffect(66, integer * 1000);
            pc.sendPackets(new S_Paralysis(3, true));
        }
        else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
            L1NpcInstance tgnpc = (L1NpcInstance)cha;
            tgnpc.setSkillEffect(66, integer * 1000);
            tgnpc.setSleeped(true);
        }
        return dmg;
    }
    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        return dmg;
    }
    
    @Override
    public void start(L1PcInstance srcpc, Object obj) throws Exception {
    }
    
    @Override
    public void stop(L1Character cha) throws Exception {
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.sendPackets(new S_Paralysis(3, false));
            pc.sendPackets(new S_OwnCharStatus(pc));
            pc.removeSkillEffect(66);
        }
        else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
            L1NpcInstance tgnpc = (L1NpcInstance)cha;
            tgnpc.removeSkillEffect(66);
            tgnpc.setSleeped(false);
        }
    }
}
