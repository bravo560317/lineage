package com.lineage.data.item_etcitem.hp;

import java.util.Random;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;

public class PetAddHp extends ItemExecutor {

    private int _type;   // 治癒量基準
    private int _type1;  // 特效ID

    public static ItemExecutor get() {
        return new PetAddHp();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null || pc == null) {
            return;
        }

        int targetObjId = 0;
        // 如果有傳入對象 ID，代表這是指定目標的藥水
        if (data != null && data.length > 0) {
            targetObjId = data[0];
        }

        if (targetObjId != 0) {
            // 指定目標使用 (use_type = 30)
            L1Object target = World.get().findObject(targetObjId);
            if (target instanceof L1NpcInstance) {
                L1NpcInstance npc = (L1NpcInstance) target;
                // 判斷目標是否為自己的寵物或召喚獸
                if (npc.getMaster() != null && npc.getMaster().getId() == pc.getId() 
                        && (npc instanceof L1PetInstance || npc instanceof L1SummonInstance)) {
                    if (pc.getInventory().removeItem(item, 1) == 1) {
                        this.doHeal(npc);
                    }
                } else {
                    pc.sendPackets(new S_SystemMessage("只能對自己的寵物或召喚獸使用。"));
                }
            } else {
                pc.sendPackets(new S_SystemMessage("這個道具只能對寵物使用。"));
            }
        } else {
            // 雙擊直接使用 (use_type = 0)
            if (pc.getPetList().isEmpty()) {
                pc.sendPackets(new S_SystemMessage("目前沒有攜帶寵物或召喚獸。"));
                return;
            }
            // 扣除1個道具後，將在場的所有寵物與召喚獸都補血
            if (pc.getInventory().removeItem(item, 1) == 1) {
                for (L1NpcInstance petNpc : pc.getPetList().values()) {
                    if (petNpc instanceof L1PetInstance || petNpc instanceof L1SummonInstance) {
                        this.doHeal(petNpc);
                    }
                }
            }
        }
    }

    private void doHeal(L1NpcInstance pet) {
        if (pet.isDead()) {
            return;
        }
        Random random = new Random();
        // 模擬原本補血浮動公式
        int healHp = (int) ((double) this._type * (random.nextGaussian() / 5.0 + 1.0));
        if (healHp < 0) {
            healHp = 0;
        }
        
        // 播放特效
        pet.broadcastPacketX8(new S_SkillSound(pet.getId(), this._type1));
        
        // 恢復血量
        pet.setCurrentHp(pet.getCurrentHp() + healHp);
    }

    @Override
    public void set_set(String[] set) {
        try {
            // 抓取 classname 後面的第一個參數 (治癒量)
            this._type = Integer.parseInt(set[1]);
        } catch (Exception ex) {}
        try {
            // 抓取 classname 後面的第二個參數 (特效ID)
            this._type1 = Integer.parseInt(set[2]);
        } catch (Exception ex2) {}
    }
}