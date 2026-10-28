package com.lineage.server.model.skill.skillmode;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class SUMMON_MONSTER extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        int level = srcpc.getLevel();
        if (!srcpc.getMap().isRecallPets()) {
            srcpc.sendPackets(new S_ServerMessage(353));
            return 0;
        }
        else if (!srcpc.getInventory().checkItem(40318, 3L)) {
            srcpc.sendPackets(new S_ServerMessage("魔法寶石不足,無法召喚"));
            return 0;
        }
        else {
            if (srcpc.getInventory().checkEquipped(20284) || srcpc.getInventory().checkEquipped(120284)) {
                if (!srcpc.isSummonMonster()) {
                    srcpc.setShapeChange(false);
                    srcpc.setSummonMonster(true);
                }
                String SummonString = String.valueOf(srcpc.getSummonId());
                if (!srcpc.isSimpleAutoHunt() && srcpc.getAu_AutoSkill(8) > 0 && srcpc.getSummon_npcid() != null && srcpc.isActived()) {
                    SummonString = srcpc.getSummon_npcid();
                }
                summonMonster(srcpc, SummonString);
            }
            else {
                int[] summons = { 81210, 81213, 81216, 81219, 81222, 81225, 81246, 81246, 81228 };
                int summonid = 0;
                int summoncost = 6;
                int levelRange = 32;
                if (ConfigOther.summoncountcha) {
                    int i = 0;
                    while (i < summons.length) {
                        if (level < levelRange || i == summons.length - 1) {
                            summonid = summons[i];
                            break;
                        }
                        else {
                            levelRange += 4;
                            ++i;
                        }
                    }
                    int petcost = 0;
                    Object[] petlist = srcpc.getPetList().values().toArray();
                    Object[] array;
                    for (int length = (array = petlist).length, k = 0; k < length; ++k) {
                        Object pet = array[k];
                        petcost += ((L1NpcInstance)pet).getPetcost();
                    }
                    int charisma = srcpc.getCha() - petcost;
                    int summoncount = charisma / 2;
                    if (summoncount >= ConfigOther.summmonstercount) {
                        summoncount = ConfigOther.summmonstercount - petlist.length;
                    }
                    L1Npc npcTemp = NpcTable.get().getTemplate(summonid);
                    for (int j = 0; j < summoncount; ++j) {
                        L1SummonInstance summon = new L1SummonInstance(npcTemp, srcpc);
                        summon.setPetcost(2);
                        summon.set_currentPetStatus(1);
                    }
                }
                else {
                    int i = 0;
                    while (i < summons.length) {
                        if (level < levelRange || i == summons.length - 1) {
                            summonid = summons[i];
                            break;
                        }
                        else {
                            levelRange += 4;
                            ++i;
                        }
                    }
                    int petcost = 0;
                    Object[] petlist = srcpc.getPetList().values().toArray();
                    Object[] array2;
                    for (int length2 = (array2 = petlist).length, l = 0; l < length2; ++l) {
                        Object pet = array2[l];
                        petcost += ((L1NpcInstance)pet).getPetcost();
                    }
                    int charisma = srcpc.getCha() + 6 - petcost;
                    int summoncount = charisma / 6;
                    if (summoncount >= ConfigOther.summmonstercount) {
                        summoncount = ConfigOther.summmonstercount - petlist.length;
                    }
                    L1Npc npcTemp = NpcTable.get().getTemplate(summonid);
                    for (int j = 0; j < summoncount; ++j) {
                        L1SummonInstance summon = new L1SummonInstance(npcTemp, srcpc);
                        summon.setPetcost(6);
                        summon.set_currentPetStatus(1);
                    }
                }
            }
            return 0;
        }
    }
    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        return 0;
    }
    
    @Override
    public void start(L1PcInstance srcpc, Object obj) throws Exception {
        String s = (String)obj;
        int summonid = 0;
        int levelrange = 0;
        int summoncost = 0;
        String[] summonstr_list = { "7", "263", "519", "8", "264", "520", "9", "265", "521", "10", "266", "522", "11", "267", "523", "12", "268", "524", "13", "269", "525", "14", "270", "526", "15", "271", "527", "16", "17", "18", "274" };
        int[] summonid_list = { 81210, 81211, 81212, 81213, 81214, 81215, 81216, 81217, 81218, 81219, 81220, 81221, 81222, 81223, 81224, 81225, 81226, 81227, 81228, 81229, 81230, 81231, 81232, 81233, 81234, 81235, 81236, 81237, 81238, 81239, 81240 };
        int[] summonlvl_list = { 28, 28, 28, 32, 32, 32, 36, 36, 36, 40, 40, 40, 44, 44, 44, 48, 48, 48, 52, 52, 52, 56, 56, 56, 60, 60, 60, 64, 68, 72, 72 };
        int[] summoncha_list = { 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 14, 36, 36, 44 };
        int loop = 0;
        while (loop < summonstr_list.length) {
            if (s.equalsIgnoreCase(summonstr_list[loop])) {
                summonid = summonid_list[loop];
                levelrange = summonlvl_list[loop];
                summoncost = summoncha_list[loop];
                break;
            }
            else {
                ++loop;
            }
        }
        if (srcpc.getLevel() < levelrange) {
            srcpc.sendPackets(new S_ServerMessage(743));
            return;
        }
        else if (!srcpc.getInventory().checkItem(40318, 3L)) {
            srcpc.sendPackets(new S_ServerMessage("魔法寶石不足,無法召喚"));
            return;
        }
        else {
            int petcost = 0;
            Object[] petlist = srcpc.getPetList().values().toArray();
            Object[] array;
            for (int length = (array = petlist).length, j = 0; j < length; ++j) {
                Object pet = array[j];
                petcost += ((L1NpcInstance)pet).getPetcost();
            }
            int charisma = srcpc.getCha() - petcost;
            int summoncount = charisma / 2;
            if (summoncount >= ConfigOther.summmonstercount) {
                summoncount = ConfigOther.summmonstercount - petlist.length;
            }
            if ((summonid == 81238 || summonid == 81239 || summonid == 81240) && petcost == 0) {
                if (srcpc.getCha() < summoncost) {
                    srcpc.sendPackets(new S_ServerMessage(3039));
                    srcpc.sendPackets(new S_CloseList(srcpc.getId()));
                    return;
                }
                else {
                    summoncount = 1;
                    srcpc.sendPackets(new S_CloseList(srcpc.getId()));
                }
            }
            L1Npc npcTemp = NpcTable.get().getTemplate(summonid);
            for (int i = 0; i < summoncount; ++i) {
                L1SummonInstance summon = new L1SummonInstance(npcTemp, srcpc);
                if ((summonid == 81238 || summonid == 81239 || summonid == 81240) && petcost == 0) {
                    summon.setPetcost(summoncost);
                    summon.set_currentPetStatus(1);
                }
                else {
                    summon.setPetcost(2);
                    summon.set_currentPetStatus(1);
                }
            }
            srcpc.sendPackets(new S_CloseList(srcpc.getId()));
            return;
        }
    }
    
    @Override
    public void stop(L1Character cha) throws Exception {
    }
    
    private static void summonMonster(L1PcInstance pc, String s) {
        int summonid = 0;
        int levelrange = 0;
        int summoncost = 0;
        String[] summonstr_list = { "7", "263", "519", "8", "264", "520", "9", "265", "521", "10", "266", "522", "11", "267", "523", "12", "268", "524", "13", "269", "525", "14", "270", "526", "15", "271", "527", "16", "17", "18", "274" };
        int[] summonid_list = { 81210, 81211, 81212, 81213, 81214, 81215, 81216, 81217, 81218, 81219, 81220, 81221, 81222, 81223, 81224, 81225, 81226, 81227, 81228, 81229, 81230, 81231, 81232, 81233, 81234, 81235, 81236, 81237, 81238, 81239, 81240 };
        int[] summonlvl_list = { 28, 28, 28, 32, 32, 32, 36, 36, 36, 40, 40, 40, 44, 44, 44, 48, 48, 48, 52, 52, 52, 56, 56, 56, 60, 60, 60, 64, 68, 72, 72 };
        int[] summoncha_list = { 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 14, 34, 36, 40 };
        int loop = 0;
        while (loop < summonstr_list.length) {
            if (s.equalsIgnoreCase(summonstr_list[loop])) {
                summonid = summonid_list[loop];
                levelrange = summonlvl_list[loop];
                summoncost = summoncha_list[loop];
                break;
            }
            else {
                ++loop;
            }
        }
        if (pc.isWizard() && pc.getlogpcpower_SkillFor2() != 0 && pc.getlogpcpower_SkillFor2() >= 1 && summonid == 81240) {
            summonid = 93142 + pc.getlogpcpower_SkillFor2();
        }
        if (!pc.getInventory().checkItem(40318, 3L)) {
            pc.sendPackets(new S_ServerMessage("魔法寶石不足,無法召喚"));
            return;
        }
        else if (pc.getLevel() < levelrange) {
            pc.sendPackets(new S_ServerMessage(743));
            return;
        }
        else if (pc.getCha() + 6 < summoncost) {
            pc.sendPackets(new S_ServerMessage(3039));
            return;
        }
        else {
            int petcost = 0;
            Object[] petlist = pc.getPetList().values().toArray();
            Object[] array;
            for (int length = (array = petlist).length, i = 0; i < length; ++i) {
                Object pet = array[i];
                petcost += ((L1NpcInstance)pet).getPetcost();
            }
            int pcCha = pc.getCha();
            int charisma = 0;
            int summoncount = 0;
            if (levelrange <= 56 || levelrange == 64) {
                if (pcCha > 34) {
                    pcCha = 34;
                }
            }
            else if (levelrange == 60) {
                if (pcCha > 30) {
                    pcCha = 30;
                }
            }
            else if (levelrange > 64 && pcCha > 44) {
                pcCha = 44;
            }
            charisma = pc.getCha() + 6 - petcost;
            summoncount = charisma / summoncost;
            if (summoncount >= ConfigOther.summmonstercount) {
                summoncount = ConfigOther.summmonstercount - petlist.length;
            }
            L1Npc npcTemp = NpcTable.get().getTemplate(summonid);
            for (int cnt = 0; cnt < summoncount; ++cnt) {
                L1SummonInstance summon = new L1SummonInstance(npcTemp, pc);
                summon.setPetcost(summoncost);
                summon.set_currentPetStatus(1);
            }
            return;
        }
    }
}
