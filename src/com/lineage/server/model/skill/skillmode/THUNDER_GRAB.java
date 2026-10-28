package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SystemMessage;
import java.util.Random;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class THUNDER_GRAB extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        Random random = new Random();
        int dmg = magic.calcMagicDamage(192);
        int ran = random.nextInt(100) + 1;
        int bindtime = random.nextInt(4) + 1;
        if (cha instanceof L1PcInstance && cha.hasSkillEffect(192)) {
            bindtime += cha.getSkillEffectTimeSec(192);
        }
        if (bindtime > 3) {
            bindtime = 3;
        }
        int level = (srcpc.getLevel() - cha.getLevel()) * 3;
        int level2 = (cha.getLevel() - srcpc.getLevel()) * 3;
        int isProbability = 0;
        if (srcpc.getLevel() > cha.getLevel()) {
            isProbability = 60 + level;
        }
        else if (srcpc.getLevel() == cha.getLevel()) {
            isProbability = 45;
        }
        else if (srcpc.getLevel() < cha.getLevel()) {
            isProbability = 30 - level2;
        }
        if (random.nextInt(100) + 1 <= isProbability) {
            if (srcpc.isGm()) {
                srcpc.sendPackets(new S_SystemMessage("GM顯示發動機率:" + isProbability));
            }
            if (!cha.hasSkillEffect(4017)) {
                cha.setSkillEffect(192, bindtime * 1000);
                cha.setSkillEffect(4017, bindtime * 1000);
                if (cha instanceof L1PcInstance) {
                    L1PcInstance pc = (L1PcInstance)cha;
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 4184));
                    L1SpawnUtil.spawnEffect(81182, bindtime, pc.getX(), pc.getY(), srcpc.getMapId(), srcpc, 0);
                    pc.sendPackets(new S_Paralysis(6, true));
                }
                else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
                    L1NpcInstance tgnpc = (L1NpcInstance)cha;
                    tgnpc.broadcastPacketX8(new S_SkillSound(tgnpc.getId(), 4184));
                    L1SpawnUtil.spawnEffect(81182, bindtime, tgnpc.getX(), tgnpc.getY(), srcpc.getMapId(), srcpc, 0);
                    tgnpc.setPassispeed(0);
                }
            }
        }
        return dmg;
    }
    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = magic.calcMagicDamage(192);
        Random random = new Random();
        int bindtime = random.nextInt(4) + 1;
        if (cha instanceof L1PcInstance && cha.hasSkillEffect(192)) {
            bindtime += cha.getSkillEffectTimeSec(192);
        }
        if (bindtime > 4) {
            bindtime = 4;
        }
        boolean isProbability = magic.calcProbabilityMagic(192);
        if (isProbability && !cha.hasSkillEffect(4000)) {
            cha.setSkillEffect(192, bindtime * 1000);
            if (cha instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)cha;
                pc.sendPacketsAll(new S_SkillSound(pc.getId(), 4184));
                L1SpawnUtil.spawnEffect(81182, bindtime, pc.getX(), pc.getY(), npc.getMapId(), npc, 0);
                pc.sendPackets(new S_Paralysis(6, true));
            }
            else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
                L1NpcInstance tgnpc = (L1NpcInstance)cha;
                tgnpc.broadcastPacketAll(new S_SkillSound(tgnpc.getId(), 4184));
                L1SpawnUtil.spawnEffect(81182, bindtime, tgnpc.getX(), tgnpc.getY(), npc.getMapId(), npc, 0);
                tgnpc.setPassispeed(0);
            }
        }
        return dmg;
    }
    
    @Override
    public void start(L1PcInstance srcpc, Object obj) throws Exception {
    }
    
    @Override
    public void stop(L1Character cha) throws Exception {
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.sendPackets(new S_Paralysis(6, false));
        }
        else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
            L1NpcInstance npc = (L1NpcInstance)cha;
            npc.setPassispeed(npc.getNpcTemplate().get_passispeed());
        }
    }
}
