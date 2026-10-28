package com.lineage.data.item_etcitem.dragon;

import com.lineage.server.model.L1PcInventory;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class HatchlingEgg_Green extends ItemExecutor
{
    private HatchlingEgg_Green() {
    }
    
    public static ItemExecutor get() {
        return new HatchlingEgg_Green();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else {
            int petcost = 0;
            Object[] petList = pc.getPetList().values().toArray();
            // 判斷玩家目前帶領的寵物數量，如果大於等於 4 隻則無法再孵化
            if (petList.length >= 4) {
                pc.sendPackets(new S_ServerMessage(489));
                return;
            }
            else {
                Object[] array;
                // 計算目前帶領寵物所花費的魅力值總和
                for (int length = (array = petList).length, i = 0; i < length; ++i) {
                    Object pet = array[i];
                    int nowpetcost = ((L1NpcInstance)pet).getPetcost();
                    petcost += nowpetcost;
                }
                int charisma = pc.getCha();
                // 根據職業給予額外的魅力值加成
                if (pc.isCrown()) {
                    charisma += 6;
                }
                else if (!pc.isKnight()) {
                    if (pc.isElf()) {
                        charisma += 12;
                    }
                    else if (pc.isWizard()) {
                        charisma += 6;
                    }
                    else if (pc.isDarkelf()) {
                        charisma += 6;
                    }
                    else if (pc.isDragonKnight()) {
                        charisma += 6;
                    }
                    else if (pc.isIllusionist()) {
                        charisma += 6;
                    }
                }
                // 判斷剩餘魅力值是否足夠孵化新的寵物
                charisma -= petcost;
                if (charisma <= 0) {
                    pc.sendPackets(new S_ServerMessage(489));
                    return;
                }
                else {
                    L1PcInventory inv = pc.getInventory();
                    // 檢查玩家背包空間是否小於 180 (避免滿包無法取得寵物項圈)
                    if (inv.getSize() < 180) {
                        // 給予寵物項圈 (Item ID: 40314)
                        L1ItemInstance petamu = inv.storeItem(40314, 1L);
                        if (petamu != null) {
                            // 召喚淘氣幼龍 (NPC ID: 93028)，綁定剛剛取得的寵物項圈
                            new L1PetInstance(93028, pc, petamu.getId());
                            pc.sendPackets(new S_ItemName(petamu));
                            // 消耗孵化用的龍蛋道具 1 個
                            pc.getInventory().removeItem(item, 1L);
                        }
                    }
                    return;
                }
            }
        }
    }
}
