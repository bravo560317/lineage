package com.lineage.data.item_etcitem;

import com.lineage.config.ConfigOther;
import com.lineage.william.Addsetvip;
import com.lineage.william.CastleOriginal;
import com.lineage.william.ClanOriginal;
import com.lineage.server.model.L1PcQuest;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.templates.L1Pet;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.datatables.lock.PetReading;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Pet_Collar extends ItemExecutor
{
    private int type1;
    private int type2;
    
    public static ItemExecutor get() {
        return new Pet_Collar();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            L1PcQuest quest = pc.getQuest();
            Addsetvip.forArmorSkillSound(pc);
            ClanOriginal.forIntensifyArmor(pc);
            CastleOriginal.forCastleOriginal(pc);
            
            if (this.type1 > 0 && !pc.getInventory().checkItem(this.type1, (long)this.type2)) {
                pc.sendPackets(new S_ServerMessage(79)); 
                return;
            }

            if (this.withdrawPet(pc, item.getId())) {
                if (this.type1 > 0) {
                    pc.getInventory().consumeItem(this.type1, (long)this.type2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean withdrawPet(L1PcInstance pc, int itemObjectId) {
        try {
            if (!pc.getMap().isTakePets()) {
                pc.sendPackets(new S_ServerMessage(563)); 
                return false;
            }

            for (Object petObject : pc.getPetList().values()) {
                if (petObject instanceof L1PetInstance && ((L1PetInstance)petObject).getItemObjId() == itemObjectId) {
                    pc.sendPackets(new S_ServerMessage(79)); 
                    return false;
                }
            }

            // 1. 計算總魅力 (基礎魅力 + 職業隱藏魅力)
            int charisma = pc.getCha();
            if (pc.isElf()) {
                charisma += 12;
            } else if (pc.isCrown() || pc.isWizard() || pc.isDarkelf()
                    || pc.isDragonKnight() || pc.isIllusionist() || pc.isKnight()) {
                charisma += 6;
            }

            // 2. 根據 Config 設定計算可攜帶的寵物總數
            int petCount = 0;
            if (ConfigOther.petcountcha) { 
                petCount = charisma / 2;
            } else { 
                petCount = charisma / 6;
            }

            int limit = ConfigOther.petcountcha ? ConfigOther.petcountchatype1 : ConfigOther.petcountchatype;
            petCount = Math.min(petCount, limit);

            System.out.println("【寵物召喚除錯】角色: " + pc.getName() + " | 總魅力: " + charisma + " | 可攜帶數: " + petCount + " | 當前已召喚數: " + pc.getPetList().size());

            // 3. 嚴格防護判斷：當前已召喚數若已經達到或超過計算數量，直接擋下
            // 這裡我們不改動條件，而是確認當前已召喚數的計算方式是否正確
            if (pc.getPetList().size() >= petCount) {
                pc.sendPackets(new S_ServerMessage(489)); 
                return false;
            }

            // 4. 執行召喚
            L1Pet l1pet = PetReading.get().getTemplate(itemObjectId);
            if (l1pet != null) {
                L1Npc npcTemp = NpcTable.get().getTemplate(l1pet.get_npcid());
                if (npcTemp != null) {
                    L1PetInstance pet = new L1PetInstance(npcTemp, pc, l1pet);
                    pet.setPetcost(6); 
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void set_set(String[] set) {
        try {
            this.type1 = Integer.parseInt(set[1]);
            this.type2 = Integer.parseInt(set[2]);
        } catch (Exception e) {
            this.type1 = 0;
            this.type2 = 0;
        }
    }
}