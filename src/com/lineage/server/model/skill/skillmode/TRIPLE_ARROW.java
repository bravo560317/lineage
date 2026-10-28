package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.datatables.SprTable;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class TRIPLE_ARROW extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        int playerGFX = srcpc.getTempCharGfx();
        int attack21 = SprTable.get().getAttackSpeed(playerGFX, 21);
        if (attack21 == 0) {
            return 0;
        }
        else {
            srcpc.setTripleArrow(true);
            for (int i = 0; i < 3; ++i) {
                cha.onAction(srcpc);
            }
            srcpc.sendPacketsAll(new S_SkillSound(srcpc.getId(), 11764));
            return dmg;
        }
    }
    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        cha.setTripleArrow(true);
        for (int i = 3; i > 0; --i) {
            npc.attackTarget(cha);
        }
        npc.broadcastPacketAll(new S_SkillSound(npc.getId(), 11764));
        return dmg;
    }
    
    @Override
    public void start(L1PcInstance srcpc, Object obj) throws Exception {
    }
    
    @Override
    public void stop(L1Character cha) throws Exception {
    }
}
