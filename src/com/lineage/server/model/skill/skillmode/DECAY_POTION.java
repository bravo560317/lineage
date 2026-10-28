package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class DECAY_POTION extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (cha.hasSkillEffect(71)) {
            return 0;
        }
        else {
            cha.set_decay_potion(true);
            cha.setSkillEffect(71, integer * 1000);
            return dmg;
        }
    }
    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (cha.hasSkillEffect(71)) {
            return 0;
        }
        else {
            cha.set_decay_potion(true);
            cha.setSkillEffect(71, integer * 1000);
            return dmg;
        }
    }
    
    @Override
    public void start(L1PcInstance srcpc, Object obj) throws Exception {
    }
    
    @Override
    public void stop(L1Character cha) throws Exception {
        cha.set_decay_potion(false);
    }
}
