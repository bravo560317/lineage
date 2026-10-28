package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.L1PinkName;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.serverpackets.S_ShowPolyList;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.Random;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class SHAPE_CHANGE extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        Random _random = new Random();
        int[] polyArray = { 29, 945, 947, 979, 1037, 1039, 3860, 3861, 3862, 3863, 3864, 3865, 3904, 3906, 95, 146, 2374, 2376, 2377, 2378, 3866, 3867, 3868, 3869, 3870, 3871, 3872, 3873, 3874, 3875, 3876 };
        int pid = _random.nextInt(polyArray.length);
        int polyId = polyArray[pid];
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            int awakeSkillId = pc.getAwakeSkillId();
            if (awakeSkillId == 185 || awakeSkillId == 190 || awakeSkillId == 195) {
                pc.sendPackets(new S_ServerMessage(1384));
                return 0;
            }
            else {
                if (pc.getInventory().checkEquipped(20281) || pc.getInventory().checkEquipped(120281)) {
                    pc.sendPackets(new S_ShowPolyList(pc.getId()));
                    if (!pc.isShapeChange()) {
                        pc.setSummonMonster(false);
                        pc.setShapeChange(true);
                    }
                    pc.sendPackets(new S_ServerMessage(966));
                }
                else if (srcpc.getId() == pc.getId()) {
                    L1PolyMorph.doPoly(pc, polyId, integer, 1);
                }
                else {
                    pc.sendPackets(new S_ServerMessage(241, srcpc.getName()));
                    L1PolyMorph.doPoly(pc, polyId, integer, 1);
                }
                L1PinkName.onAction(pc, srcpc);
            }
        }
        else if (cha instanceof L1MonsterInstance) {
            L1MonsterInstance mob = (L1MonsterInstance)cha;
            if (mob.getLevel() < 50) {
                int npcId = mob.getNpcTemplate().get_npcId();
                if (npcId != 45464 && npcId != 45473 && npcId != 45488 && npcId != 45497 && npcId != 45458 && npcId != 45752 && npcId != 45492 && npcId != 46035 && npcId != 99006) {
                    L1PolyMorph.doPoly(mob, polyId, integer, 1);
                }
            }
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
        L1PolyMorph.undoPoly(cha);
    }
}
