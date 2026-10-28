package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class PANIC extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!cha.hasSkillEffect(217)) {
            if (cha instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)cha;
                pc.addStr(-1);
                pc.addCon(-1);
                pc.addDex(-1);
                pc.addWis(-1);
                pc.addInt(-1);
                pc.setSkillEffect(217, integer * 1000);
                pc.sendPackets(new S_OwnCharStatus2(pc));
            }
            else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
                L1NpcInstance tgnpc = (L1NpcInstance)cha;
                tgnpc.addStr(-1);
                tgnpc.addCon(-1);
                tgnpc.addDex(-1);
                tgnpc.addWis(-1);
                tgnpc.addInt(-1);
                tgnpc.setSkillEffect(217, integer * 1000);
            }
        }
        return 0;
    }
    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        return 0;
    }
    
    @Override
    public void start(L1PcInstance srcpc, Object obj) throws Exception {
    }
    
    @Override
    public void stop(L1Character cha) throws Exception {
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.addStr(1);
            pc.addCon(1);
            pc.addDex(1);
            pc.addWis(1);
            pc.addInt(1);
            pc.sendPackets(new S_OwnCharStatus2(pc));
        }
        else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
            L1NpcInstance tgnpc = (L1NpcInstance)cha;
            tgnpc.addStr(1);
            tgnpc.addCon(1);
            tgnpc.addDex(1);
            tgnpc.addWis(1);
            tgnpc.addInt(1);
        }
    }
}
