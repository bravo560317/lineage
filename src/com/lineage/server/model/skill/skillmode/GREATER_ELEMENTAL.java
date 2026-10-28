package com.lineage.server.model.skill.skillmode;

import com.lineage.server.templates.L1Npc;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class GREATER_ELEMENTAL extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        L1PcInstance pc = (L1PcInstance)cha;
        int attr = pc.getElfAttr();
        if (attr != 0) {
            if (!pc.getMap().isRecallPets()) {
                pc.sendPackets(new S_ServerMessage(353));
                return 0;
            }
            else {
                int petcost = 0;
                Object[] petlist = pc.getPetList().values().toArray();
                Object[] array;
                for (int length = (array = petlist).length, i = 0; i < length; ++i) {
                    Object pet = array[i];
                    petcost += ((L1NpcInstance)pet).getPetcost();
                }
                if (petcost == 0) {
                    int summonid = 0;
                    switch (attr) {
                        case 1: {
                            summonid = 81053;
                            break;
                        }
                        case 2: {
                            summonid = 81050;
                            break;
                        }
                        case 4: {
                            summonid = 81051;
                            break;
                        }
                        case 8: {
                            summonid = 81052;
                            break;
                        }
                    }
                    if (summonid != 0) {
                        L1Npc npcTemp = NpcTable.get().getTemplate(summonid);
                        L1SummonInstance summon = new L1SummonInstance(npcTemp, pc);
                        summon.setPetcost(pc.getCha() + 7);
                        summon.set_currentPetStatus(1);
                    }
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
