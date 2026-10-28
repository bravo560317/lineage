package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;

public class BONE_BREAK extends SkillMode
{
    private static Random _random;
    
    static {
        BONE_BREAK._random = new Random();
    }
    
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = magic.calcMagicDamage(208);
        if (cha.hasSkillEffect(208)) {
            return dmg;
        }
        else {
            int time = BONE_BREAK._random.nextInt(2) + 1;
            cha.setSkillEffect(208, time * 1000);
            L1SpawnUtil.spawnEffect(86123, time, cha.getX(), cha.getY(), cha.getMapId(), cha, 0);
            if (cha instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)cha;
                pc.sendPackets(new S_Paralysis(5, true));
            }
            else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
                L1NpcInstance npc = (L1NpcInstance)cha;
                npc.setParalyzed(true);
            }
            return dmg;
        }
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
            pc.sendPackets(new S_Paralysis(5, false));
        }
        else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
            L1NpcInstance npc = (L1NpcInstance)cha;
            npc.setParalyzed(false);
        }
    }
}
