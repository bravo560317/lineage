package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class MIND_BREAK extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int reMp = 5;
        int reMp2 = 0;
        int sp = srcpc.getSp();
        double dmg = (double)sp * 3.8;
        srcpc.sendPacketsAll(new S_SkillSound(cha.getId(), 6553));
        if (srcpc.getlogpcpower_SkillFor2() > 0) {
            reMp2 = srcpc.getlogpcpower_SkillFor2() * 20;
        }
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            int newMp = pc.getCurrentMp() - reMp - reMp2;
            if (newMp < 0) {
                newMp = 0;
            }
            pc.setCurrentMp(newMp);
            pc.receiveDamage(srcpc, dmg + (double)reMp2, false, false);
        }
        else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
            L1NpcInstance npc = (L1NpcInstance)cha;
            int newMp = npc.getCurrentMp() - reMp;
            if (newMp < 0) {
                newMp = 0;
            }
            npc.setCurrentMp(newMp);
            npc.receiveDamage(srcpc, (int)dmg + reMp2);
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
    }
}
