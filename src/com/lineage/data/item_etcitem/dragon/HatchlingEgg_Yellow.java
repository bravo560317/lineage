package com.lineage.data.item_etcitem.dragon;

import com.lineage.server.model.L1PcInventory;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class HatchlingEgg_Yellow extends ItemExecutor
{
    private HatchlingEgg_Yellow() {
    }
    
    public static ItemExecutor get() {
        return new HatchlingEgg_Yellow();
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
            if (petList.length >= 4) {
                pc.sendPackets(new S_ServerMessage(489));
                return;
            }
            else {
                Object[] array;
                for (int length = (array = petList).length, i = 0; i < length; ++i) {
                    Object pet = array[i];
                    int nowpetcost = ((L1NpcInstance)pet).getPetcost();
                    petcost += nowpetcost;
                }
                int charisma = pc.getCha();
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
                charisma -= petcost;
                if (charisma <= 0) {
                    pc.sendPackets(new S_ServerMessage(489));
                    return;
                }
                else {
                    L1PcInventory inv = pc.getInventory();
                    if (inv.getSize() < 180) {
                        L1ItemInstance petamu = inv.storeItem(40314, 1L);
                        if (petamu != null) {
                            new L1PetInstance(93029, pc, petamu.getId());
                            pc.sendPackets(new S_ItemName(petamu));
                            pc.getInventory().removeItem(item, 1L);
                        }
                    }
                    return;
                }
            }
        }
    }
}
