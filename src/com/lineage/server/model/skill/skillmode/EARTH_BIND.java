package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_Poison;
import com.lineage.server.model.L1CastleLocation;
import java.util.Random;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class EARTH_BIND extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        Random rad = new Random();
        int i = rad.nextInt(12) + 1;
        boolean castle_area = L1CastleLocation.checkInAllWarArea(srcpc.getX(), srcpc.getY(), srcpc.getMapId());
        if (castle_area) {
            return dmg;
        }
        else {
            if (!srcpc.castleWarResult()) {
                cha.setSkillEffect(157, integer * 1000);
                if (cha instanceof L1PcInstance) {
                    L1PcInstance pc = (L1PcInstance)cha;
                    pc.sendPacketsAll(new S_Poison(pc.getId(), 2));
                    pc.sendPackets(new S_Paralysis(4, true));
                }
                else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
                    L1NpcInstance npc = (L1NpcInstance)cha;
                    npc.broadcastPacketAll(new S_Poison(npc.getId(), 2));
                    npc.setParalyzed(true);
                }
            }
            return dmg;
        }
    }
    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        Random rad = new Random();
        int i = rad.nextInt(12) + 1;
        cha.setSkillEffect(157, i * 1000);
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.sendPacketsAll(new S_Poison(pc.getId(), 2));
            pc.sendPackets(new S_Paralysis(4, true));
        }
        else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
            L1NpcInstance tgnpc = (L1NpcInstance)cha;
            tgnpc.broadcastPacketAll(new S_Poison(tgnpc.getId(), 2));
            tgnpc.setParalyzed(true);
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
            pc.sendPacketsAll(new S_Poison(pc.getId(), 0));
            pc.sendPackets(new S_Paralysis(4, false));
        }
        else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
            L1NpcInstance npc = (L1NpcInstance)cha;
            npc.broadcastPacketAll(new S_Poison(npc.getId(), 0));
            npc.setParalyzed(false);
        }
    }
}
