package com.lineage.server.model.skill.skillmode;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1TowerInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class CALL_OF_NATURE extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            if (srcpc.getId() != pc.getId()) {
                if (World.get().getVisiblePlayer(pc, 0).size() > 0) {
                    for (L1PcInstance visiblePc : World.get().getVisiblePlayer(pc, 0)) {
                        if (!visiblePc.isDead()) {
                            srcpc.sendPackets(new S_ServerMessage(592));
                            return 0;
                        }
                    }
                }
                if (pc.isDead()) {
                    pc.setTempID(srcpc.getId());
                    pc.sendPackets(new S_Message_YN(322));
                }
            }
        }
        if (cha instanceof L1NpcInstance && !(cha instanceof L1TowerInstance)) {
            L1NpcInstance npc = (L1NpcInstance)cha;
            if (npc.getNpcTemplate().isCantResurrect()) {
                return 0;
            }
            else {
                if (npc instanceof L1PetInstance && World.get().getVisiblePlayer(npc, 0).size() > 0) {
                    for (L1PcInstance visiblePc : World.get().getVisiblePlayer(npc, 0)) {
                        if (!visiblePc.isDead()) {
                            srcpc.sendPackets(new S_ServerMessage(592));
                            return 0;
                        }
                    }
                }
                if (npc.isDead()) {
                    npc.resurrect(cha.getMaxHp());
                    npc.setResurrect(true);
                }
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
    }
}
