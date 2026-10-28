package com.lineage.data.item_etcitem.dragon;

import com.lineage.server.model.L1PcInventory;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class HatchlingEgg extends ItemExecutor {

    private int _npcId = 0; // 從資料庫設定讀取的寵物 NPC ID

    private HatchlingEgg() {
    }

    public static ItemExecutor get() {
        return new HatchlingEgg();
    }

    /**
     * 解析資料庫中的 classname 參數
     * 範例: dragon.HatchlingEgg 45601
     * set[0] = "dragon.HatchlingEgg"
     * set[1] = "45601"
     */
    @Override
    public void set_set(String[] set) {
        try {
            if (set.length > 1) {
                this._npcId = Integer.parseInt(set[1]);
            }
        } catch (Exception e) {
            // 忽略解析錯誤
        }
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null || pc == null || this._npcId == 0) {
            return;
        }

        int petcost = 0;
        Object[] petList = pc.getPetList().values().toArray();
        
        // 判斷玩家目前帶領的寵物數量，如果大於等於 4 隻則無法再孵化
        if (petList.length >= 4) {
            pc.sendPackets(new S_ServerMessage(489));
            return;
        }

        // 計算目前帶領寵物所花費的魅力值總和
        for (int length = petList.length, i = 0; i < length; ++i) {
            Object pet = petList[i];
            int nowpetcost = ((L1NpcInstance) pet).getPetcost();
            petcost += nowpetcost;
        }
        


                    // 1. 計算總魅力 (基礎魅力 + 職業隱藏魅力)
            int charisma = pc.getCha();
            if (pc.isElf()) {
                charisma += 12;
            } else if (pc.isCrown() || pc.isWizard() || pc.isDarkelf()
                    || pc.isDragonKnight() || pc.isIllusionist() || pc.isKnight()) {
                charisma += 6;
         }

        
        // 判斷剩餘魅力值是否足夠孵化新的寵物
        charisma -= petcost;
        if (charisma <= 0) {
            pc.sendPackets(new S_ServerMessage(489));
            return;
        }

        L1PcInventory inv = pc.getInventory();
        // 檢查玩家背包空間是否小於 180 (避免滿包無法取得寵物項圈)
        if (inv.getSize() < 180) {
            // 給予寵物項圈 (Item ID: 40314)
            L1ItemInstance petamu = inv.storeItem(40314, 1L);
            if (petamu != null) {
                // 召喚寵物，綁定剛剛取得的寵物項圈，並代入自定義的 _npcId
                new L1PetInstance(this._npcId, pc, petamu.getId());
                pc.sendPackets(new S_ItemName(petamu));
                // 消耗孵化用的龍蛋道具 1 個
                pc.getInventory().removeItem(item, 1L);
            }
        }
    }
}