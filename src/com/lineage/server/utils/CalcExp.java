package com.lineage.server.utils;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Pet;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.world.WorldItem;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_NPCPack_Pet;
import com.lineage.server.datatables.lock.PetReading;
import java.util.Iterator;
import com.lineage.server.model.L1Party;
import com.lineage.config.ConfigGuaji;
import com.lineage.config.ConfigOther;
import com.lineage.server.datatables.Explogpcpower;
import com.lineage.server.datatables.MapsTable;
import com.lineage.server.serverpackets.S_PacketBoxExp;
import com.lineage.data.event.LeavesSet;
import com.lineage.server.datatables.ExpTable;
import com.lineage.config.ConfigRate;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.model.Instance.L1IllusoryInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class CalcExp
{
    private static final Log _log;
    protected static final Random _random;
    
    static {
        _log = LogFactory.getLog(CalcExp.class);
        _random = new Random();
    }
    
    public static void calcExp(L1PcInstance srcpc, int targetid, ArrayList<?> acquisitorList, ArrayList<Integer> hateList, long exp) {
        try {
            int i = 0;
            double party_level = 0.0;
            double dist = 0.0;
            long member_exp = 0L;
            int member_lawful = 0;
            int pet_lawful = 0;
            L1Object object = World.get().findObject(targetid);
            L1NpcInstance npc = null;
            if (object instanceof L1NpcInstance) {
                npc = (L1NpcInstance)object;
                int hate = 0;
                long acquire_exp = 0L;
                int acquire_lawful = 0;
                long party_exp = 0L;
                int party_lawful = 0;
                long totalHateExp = 0L;
                int totalHateLawful = 0;
                long partyHateExp = 0L;
                int partyHateLawful = 0;
                long ownHateExp = 0L;
                if (acquisitorList.size() != hateList.size()) {
                    return;
                }
                else {
                    for (i = hateList.size() - 1; i >= 0; --i) {
                        L1Character acquisitor = (L1Character)acquisitorList.get(i);
                        hate = hateList.get(i);
                        boolean isRemove = false;
                        if (acquisitor instanceof L1IllusoryInstance) {
                            isRemove = true;
                        }
                        if (acquisitor instanceof L1EffectInstance) {
                            isRemove = true;
                        }
                        if (isRemove) {
                            if (acquisitor != null) {
                                acquisitorList.remove(i);
                                hateList.remove(i);
                            }
                        }
                        else if (acquisitor != null && !acquisitor.isDead()) {
                            totalHateExp += (long)hate;
                            if (acquisitor instanceof L1PcInstance) {
                                totalHateLawful += hate;
                            }
                        }
                        else {
                            acquisitorList.remove(i);
                            hateList.remove(i);
                        }
                    }
                    if (totalHateExp == 0L) {
                        return;
                    }
                    else if (object != null && !(npc instanceof L1PetInstance) && !(npc instanceof L1SummonInstance)) {
                        if (!World.get().isProcessingContributionTotal() && srcpc.getHomeTownId() > 0) {
                            int contribution = npc.getLevel() / 10;
                            srcpc.addContribution(contribution);
                        }
                        int lawful = npc.getLawful();
                        if (srcpc.isInParty()) {
                            partyHateExp = 0L;
                            partyHateLawful = 0;
                            for (i = hateList.size() - 1; i >= 0; --i) {
                                L1Character acquisitor2 = (L1Character)acquisitorList.get(i);
                                hate = hateList.get(i);
                                if (acquisitor2 instanceof L1PcInstance) {
                                    L1PcInstance pc = (L1PcInstance)acquisitor2;
                                    if (pc == srcpc) {
                                        partyHateExp += (long)hate;
                                        partyHateLawful += hate;
                                    }
                                    else if (srcpc.getParty().isMember(pc)) {
                                        partyHateExp += (long)hate;
                                        partyHateLawful += hate;
                                    }
                                    else {
                                        if (totalHateExp > 0L) {
                                            acquire_exp = exp * (long)hate / totalHateExp;
                                        }
                                        if (totalHateLawful > 0) {
                                            acquire_lawful = lawful * hate / totalHateLawful;
                                        }
                                        addExp(pc, acquire_exp, acquire_lawful);
                                    }
                                }
                                else if (acquisitor2 instanceof L1PetInstance) {
                                    L1PetInstance pet = (L1PetInstance)acquisitor2;
                                    L1PcInstance master = (L1PcInstance)pet.getMaster();
                                    if (master == srcpc) {
                                        partyHateExp += (long)hate;
                                    }
                                    else if (srcpc.getParty().isMember(master)) {
                                        partyHateExp += (long)hate;
                                    }
                                    else {
                                        if (totalHateExp > 0L) {
                                            acquire_exp = exp * (long)hate / totalHateExp;
                                        }
                                        addExpPet(pet, acquire_exp);
                                    }
                                }
                                else if (acquisitor2 instanceof L1SummonInstance) {
                                    L1SummonInstance summon = (L1SummonInstance)acquisitor2;
                                    L1PcInstance master = (L1PcInstance)summon.getMaster();
                                    if (master == srcpc) {
                                        partyHateExp += (long)hate;
                                    }
                                    else if (srcpc.getParty().isMember(master)) {
                                        partyHateExp += (long)hate;
                                    }
                                }
                            }
                            if (totalHateExp > 0L) {
                                party_exp = exp * partyHateExp / totalHateExp;
                            }
                            if (totalHateLawful > 0) {
                                party_lawful = lawful * partyHateLawful / totalHateLawful;
                            }
                            double pri_bonus = 0.0;
                            L1PcInstance leader = srcpc.getParty().getLeader();
                            if (leader.isCrown() && (srcpc.knownsObject(leader) || srcpc.equals(leader))) {
                                pri_bonus = 0.059;
                            }
                            Object[] pcs = srcpc.getParty().partyUsers().values().toArray();
                            double pt_bonus = 0.0;
                            Object[] array;
                            for (int length = (array = pcs).length, j = 0; j < length; ++j) {
                                Object obj = array[j];
                                if (obj instanceof L1PcInstance) {
                                    L1PcInstance each = (L1PcInstance)obj;
                                    if (!each.isDead()) {
                                        if (srcpc.knownsObject(each) || srcpc.equals(each)) {
                                            party_level += (double)(each.getLevel() * each.getLevel());
                                        }
                                        if (srcpc.knownsObject(each)) {
                                            pt_bonus += 0.04;
                                        }
                                    }
                                }
                            }
                            party_exp = (long)((double)party_exp * (1.0 + pt_bonus + pri_bonus));
                            if (party_level > 0.0) {
                                dist = (double)(srcpc.getLevel() * srcpc.getLevel()) / party_level;
                            }
                            member_exp = (long)((double)party_exp * dist);
                            member_lawful = (int)((double)party_lawful * dist);
                            ownHateExp = 0L;
                            for (i = hateList.size() - 1; i >= 0; --i) {
                                L1Character acquisitor3 = (L1Character)acquisitorList.get(i);
                                hate = hateList.get(i);
                                if (acquisitor3 instanceof L1PcInstance) {
                                    L1PcInstance pc2 = (L1PcInstance)acquisitor3;
                                    if (pc2 == srcpc) {
                                        ownHateExp += (long)hate;
                                    }
                                }
                                else if (acquisitor3 instanceof L1PetInstance) {
                                    L1PetInstance pet2 = (L1PetInstance)acquisitor3;
                                    L1PcInstance master2 = (L1PcInstance)pet2.getMaster();
                                    if (master2 == srcpc) {
                                        ownHateExp += (long)hate;
                                    }
                                }
                                else if (acquisitor3 instanceof L1SummonInstance) {
                                    L1SummonInstance summon2 = (L1SummonInstance)acquisitor3;
                                    L1PcInstance master2 = (L1PcInstance)summon2.getMaster();
                                    if (master2 == srcpc) {
                                        ownHateExp += (long)hate;
                                    }
                                }
                            }
                            if (ownHateExp != 0L) {
                                for (i = hateList.size() - 1; i >= 0; --i) {
                                    L1Character acquisitor3 = (L1Character)acquisitorList.get(i);
                                    hate = hateList.get(i);
                                    if (acquisitor3 instanceof L1PcInstance) {
                                        L1PcInstance pc2 = (L1PcInstance)acquisitor3;
                                        if (pc2 == srcpc) {
                                            if (ownHateExp > 0L) {
                                                acquire_exp = member_exp * (long)hate / ownHateExp;
                                            }
                                            addExp(pc2, acquire_exp, member_lawful);
                                        }
                                    }
                                    else if (acquisitor3 instanceof L1PetInstance) {
                                        L1PetInstance pet2 = (L1PetInstance)acquisitor3;
                                        L1PcInstance master2 = (L1PcInstance)pet2.getMaster();
                                        if (master2 == srcpc) {
                                            if (ownHateExp > 0L) {
                                                acquire_exp = member_exp * (long)hate / ownHateExp;
                                            }
                                            addExpPet(pet2, acquire_exp);
                                        }
                                    }
                                    else if (acquisitor3 instanceof L1SummonInstance) {}
                                }
                            }
                            else {
                                addExp(srcpc, member_exp, member_lawful);
                            }
                            Object[] array2;
                            for (int length2 = (array2 = pcs).length, k = 0; k < length2; ++k) {
                                Object obj = array2[k];
                                if (obj instanceof L1PcInstance) {
                                    L1PcInstance tgpc = (L1PcInstance)obj;
                                    if (!tgpc.isDead() && srcpc.knownsObject(tgpc)) {
                                        if (party_level > 0.0) {
                                            dist = (double)(tgpc.getLevel() * tgpc.getLevel()) / party_level;
                                        }
                                        member_exp = (long)(int)((double)party_exp * dist);
                                        member_lawful = (int)((double)party_lawful * dist);
                                        ownHateExp = 0L;
                                        for (i = hateList.size() - 1; i >= 0; --i) {
                                            L1Character acquisitor4 = (L1Character)acquisitorList.get(i);
                                            hate = hateList.get(i);
                                            if (acquisitor4 instanceof L1PcInstance) {
                                                L1PcInstance pc3 = (L1PcInstance)acquisitor4;
                                                if (pc3 == tgpc) {
                                                    ownHateExp += (long)hate;
                                                }
                                            }
                                            else if (acquisitor4 instanceof L1PetInstance) {
                                                L1PetInstance pet3 = (L1PetInstance)acquisitor4;
                                                L1PcInstance master3 = (L1PcInstance)pet3.getMaster();
                                                if (master3 == tgpc) {
                                                    ownHateExp += (long)hate;
                                                }
                                            }
                                            else if (acquisitor4 instanceof L1SummonInstance) {
                                                L1SummonInstance summon3 = (L1SummonInstance)acquisitor4;
                                                L1PcInstance master3 = (L1PcInstance)summon3.getMaster();
                                                if (master3 == tgpc) {
                                                    ownHateExp += (long)hate;
                                                }
                                            }
                                        }
                                        if (ownHateExp != 0L) {
                                            for (i = hateList.size() - 1; i >= 0; --i) {
                                                L1Character acquisitor4 = (L1Character)acquisitorList.get(i);
                                                hate = hateList.get(i);
                                                if (acquisitor4 instanceof L1PcInstance) {
                                                    L1PcInstance pc3 = (L1PcInstance)acquisitor4;
                                                    if (pc3 == tgpc) {
                                                        if (ownHateExp > 0L) {
                                                            acquire_exp = member_exp * (long)hate / ownHateExp;
                                                        }
                                                        addExp(pc3, acquire_exp, member_lawful);
                                                    }
                                                }
                                                else if (acquisitor4 instanceof L1PetInstance) {
                                                    L1PetInstance pet3 = (L1PetInstance)acquisitor4;
                                                    L1PcInstance master3 = (L1PcInstance)pet3.getMaster();
                                                    if (master3 == tgpc) {
                                                        if (ownHateExp > 0L) {
                                                            acquire_exp = member_exp * (long)hate / ownHateExp;
                                                        }
                                                        addExpPet(pet3, acquire_exp);
                                                    }
                                                }
                                                else if (acquisitor4 instanceof L1SummonInstance) {}
                                            }
                                        }
                                        else {
                                            addExp(tgpc, member_exp, member_lawful);
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            for (i = hateList.size() - 1; i >= 0; --i) {
                                L1Character acquisitor2 = (L1Character)acquisitorList.get(i);
                                hate = hateList.get(i);
                                acquire_exp = exp * (long)hate / totalHateExp;
                                if (acquisitor2 instanceof L1PcInstance && totalHateLawful > 0) {
                                    acquire_lawful = lawful * hate / totalHateLawful;
                                }
                                if (acquisitor2 instanceof L1PcInstance) {
                                    L1PcInstance pc = (L1PcInstance)acquisitor2;
                                    addExp(pc, acquire_exp, acquire_lawful);
                                }
                                else if (acquisitor2 instanceof L1PetInstance) {
                                    L1PetInstance pet = (L1PetInstance)acquisitor2;
                                    addExpPet(pet, acquire_exp);
                                }
                                else if (acquisitor2 instanceof L1SummonInstance) {}
                            }
                        }
                    }
                }
            }
            else {
                return;
            }
        }
        catch (Exception e) {
            CalcExp._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void addExp(L1PcInstance pc, long exp, int lawful) {
        try {
            double addExp = (double)exp;
            int add_lawful = (int)((double)lawful * ConfigRate.RATE_LA) * -1;
            pc.addLawful(add_lawful);
            if (pc.getLevel() >= ExpTable.MAX_LEVEL) {
                return;
            }
            else {
                if (LeavesSet.START && pc.getlogintime() > 0) {
                    int add = (int)(addExp * 0.77);
                    int add2 = (int)addExp + add;
                    int add3 = pc.getlogintime() - add2;
                    if (add3 > 0) {
                        pc.setlogintime(add3);
                        pc.sendPackets(new S_PacketBoxExp(pc.getlogintime() / LeavesSet.EXP));
                    }
                    else {
                        add = pc.getlogintime();
                        pc.setlogintime(0);
                        pc.sendPackets(new S_PacketBoxExp());
                    }
                    addExp += (double)add;
                }
                double exppenalty = ExpTable.getPenaltyRate(pc.getLevel(), pc.getMeteLevel());
                if (exppenalty < 1.0) {
                    addExp *= exppenalty;
                }
                if (ConfigRate.RATE_XP > 1.0) {
                    addExp *= ConfigRate.RATE_XP;
                }
                if ((double)MapsTable.get().getEXP((int)pc.getMapId()) > 100.0) {
                    addExp *= (double)MapsTable.get().getEXP((int)pc.getMapId()) / 100.0;
                }
                addExp *= add(pc);
                addExp *= Explogpcpower.get().getRate(pc.getMeteLevel());
                if (addExp < 0.0) {
                    addExp = 0.0;
                }
                else if (addExp > 3.6065092E7) {
                    addExp = 3.6065092E7;
                }
                pc.addExp((long)addExp);
            }
        }
        catch (Exception e) {
            CalcExp._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static double add(L1PcInstance pc) {
        try {
            double add_exp = 0.0;
            if (pc.hasSkillEffect(8591)) {
                add_exp += 0.5;
            }
            if (pc.hasSkillEffect(3007) || pc.hasSkillEffect(3015)) {
                add_exp += 0.01;
            }
            if (pc.hasSkillEffect(3023) || pc.hasSkillEffect(3031)) {
                add_exp += 0.02;
            }
            if (pc.hasSkillEffect(3039) || pc.hasSkillEffect(3047)) {
                add_exp += 0.03;
            }
            if (pc.hasSkillEffect(3048) || pc.hasSkillEffect(3049) || pc.hasSkillEffect(3050)) {
                add_exp += 0.02;
            }
            if (pc.hasSkillEffect(3051)) {
                add_exp += 0.04;
            }
            if (pc.hasSkillEffect(6666)) {
                add_exp += 1.3;
            }
            if (pc.hasSkillEffect(6667)) {
                add_exp += 1.5;
            }
            if (pc.hasSkillEffect(6668)) {
                add_exp += 1.7;
            }
            if (pc.hasSkillEffect(6669)) {
                add_exp += 2.0;
            }
            if (pc.hasSkillEffect(6670)) {
                add_exp += 2.5;
            }
            if (pc.hasSkillEffect(6671)) {
                add_exp += 3.0;
            }
            if (pc.hasSkillEffect(6672)) {
                add_exp += 3.5;
            }
            if (pc.hasSkillEffect(6673)) {
                add_exp += 4.0;
            }
            if (pc.hasSkillEffect(6674)) {
                add_exp += 4.5;
            }
            if (pc.hasSkillEffect(6675)) {
                add_exp += 5.0;
            }
            if (pc.hasSkillEffect(6676)) {
                add_exp += 5.5;
            }
            if (pc.hasSkillEffect(6677)) {
                add_exp += 6.0;
            }
            if (pc.hasSkillEffect(6678)) {
                add_exp += 6.5;
            }
            if (pc.hasSkillEffect(6679)) {
                add_exp += 7.0;
            }
            if (pc.hasSkillEffect(6680)) {
                add_exp += 7.5;
            }
            if (pc.hasSkillEffect(6681)) {
                add_exp += 8.0;
            }
            if (pc.getExpAdd() > 0.0) {
                if (pc.getExpAdd() >= 1.0) {
                    ++add_exp;
                }
                add_exp += pc.getExpAdd();
            }
            if (pc.getExpPoint() > 0) {
                if (pc.getExpPoint() >= 1) {
                    ++add_exp;
                }
                add_exp += (double)pc.getExpPoint();
            }
            L1Party party = pc.getParty();
            if (ConfigOther.partyexp && party != null) {
                for (L1PcInstance player : World.get().getVisiblePlayer(pc, 8)) {
                    if (pc.getParty().isMember(player)) {
                        int count = player.getParty().getNumOfMembers();
                        if (count > 1) {
                            player.setPartyExp(ConfigOther.partyexp1 * (double)count);
                        }
                    }
                    add_exp += pc.getPartyExp();
                }
            }
            if (pc.getExpByArmor() > 0.0) {
                add_exp += pc.getExpByArmor() / 100.0;
            }
            double foodBonus = 0.0;
            if (pc.hasSkillEffect(5000)) {
                foodBonus += 1.5;
            }
            if (pc.hasSkillEffect(5001)) {
                foodBonus += 1.75;
            }
            if (pc.hasSkillEffect(5002)) {
                foodBonus += 2.0;
            }
            if (pc.hasSkillEffect(5003)) {
                foodBonus += 2.25;
            }
            if (pc.hasSkillEffect(5004)) {
                foodBonus += 2.5;
            }
            if (pc.hasSkillEffect(5005)) {
                foodBonus += 2.75;
            }
            if (pc.hasSkillEffect(5006)) {
                foodBonus += 3.0;
            }
            if (pc.hasSkillEffect(5007)) {
                foodBonus += 3.25;
            }
            if (pc.hasSkillEffect(5008)) {
                foodBonus += 3.5;
            }
            if (pc.hasSkillEffect(5009)) {
                foodBonus += 3.75;
            }
            if (pc.hasSkillEffect(5010)) {
                foodBonus += 4.0;
            }
            if (pc.hasSkillEffect(5011)) {
                foodBonus += 4.5;
            }
            if (pc.hasSkillEffect(5012)) {
                foodBonus += 5.0;
            }
            if (pc.hasSkillEffect(5013)) {
                foodBonus += 5.5;
            }
            if (pc.hasSkillEffect(5014)) {
                foodBonus += 6.0;
            }
            if (foodBonus > 0.0) {
                add_exp *= foodBonus;
            }
            if (add_exp < 1.0) {
                ++add_exp;
            }
            if (pc.isActived() || pc.get_followmaster() != null) {
                if (ConfigGuaji.guajiitem > 0 && pc.getInventory().checkItem(ConfigGuaji.guajiitem)) {
                    // 持有掛機消耗媒介，不衰減經驗 (消耗動作統一於DropShare中執行)
                } else {
                    add_exp *= ConfigGuaji.guajiexp2;
                }
            }
            return add_exp;
        }
        catch (Exception e) {
            CalcExp._log.error(e.getLocalizedMessage(), e);
            return 1.0;
        }
    }
    
    private static void addExpPet(L1PetInstance pet, long exp) {
        try {
            if (pet == null) {
                return;
            }
            else if (pet.getPetType() == null) {
                return;
            }
            else {
                L1PcInstance pc = (L1PcInstance)pet.getMaster();
                if (pc == null) {
                    return;
                }
                else {
                    int petItemObjId = pet.getItemObjId();
                    int levelBefore = pet.getLevel();
                    long totalExp = exp * (long)ConfigOther.petexp + pet.getExp();
                    int maxLevel = ConfigOther.petlevel;
                    pet.addLawful(1 + Random.nextInt(15));
                    L1Pet petTemplate1 = PetReading.get().getTemplate(petItemObjId);
                    petTemplate1.set_lawful(pet.getLawful());
                    PetReading.get().storePet(petTemplate1);
                    if (totalExp >= ExpTable.getExpByLevel(maxLevel)) {
                        totalExp = ExpTable.getExpByLevel(maxLevel) - 1L;
                    }
                    pet.setExp(totalExp);
                    pet.setLevel(ExpTable.getLevelByExp(totalExp));
                    int expPercentage = ExpTable.getExpPercentage(pet.getLevel(), totalExp);
                    int gap = pet.getLevel() - levelBefore;
                    for (int i = 1; i <= gap; ++i) {
                        RangeInt hpUpRange = pet.getPetType().getHpUpRange();
                        RangeInt mpUpRange = pet.getPetType().getMpUpRange();
                        pet.addMaxHp(hpUpRange.randomValue());
                        pet.addMaxMp(mpUpRange.randomValue());
                    }
                    pet.setExpPercent(expPercentage);
                    pc.sendPackets(new S_NPCPack_Pet(pet, pc));
                    if (gap != 0) {
                        L1Pet petTemplate2 = PetReading.get().getTemplate(petItemObjId);
                        if (petTemplate2 == null) {
                            return;
                        }
                        else {
                            petTemplate2.set_exp((int)pet.getExp());
                            petTemplate2.set_level(pet.getLevel());
                            petTemplate2.set_hp(pet.getMaxHp());
                            petTemplate2.set_mp(pet.getMaxMp());
                            petTemplate2.set_lawful(pet.getLawful());
                            PetReading.get().storePet(petTemplate2);
                            pc.sendPackets(new S_ServerMessage(320, pet.getName()));
                            L1ItemInstance item = WorldItem.get().getItem(pet.getItemObjId());
                            pc.sendPackets(new S_ItemName(item));
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            CalcExp._log.error(e.getLocalizedMessage(), e);
        }
    }
}
