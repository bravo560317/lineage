package com.lineage.data.item_etcitem.poweritem;

import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.datatables.lock.CharItemPowerReading;
import com.lineage.server.templates.L1ItemPower_name;
import com.lineage.data.event.PowerItemSet;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Reset_Hole5 extends ItemExecutor
{
    private Reset_Hole5() {
    }
    
    public static ItemExecutor get() {
        return new Reset_Hole5();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else if (tgItem.isEquipped()) {
            pc.sendPackets(new S_ServerMessage("\\fR你必須先解除物品裝備!"));
            return;
        }
        else {
            pc.getInventory().removeItem(item, 1L);
            L1ItemPower_name power = null;
            boolean update = false;
            int count = 0;
            switch (tgItem.getItem().getUseType()) {
                case 1: {
                    count = PowerItemSet.WEAPONHOLE;
                    if (tgItem.get_power_name() != null) {
                        power = tgItem.get_power_name();
                        update = true;
                        break;
                    }
                    else {
                        power = new L1ItemPower_name();
                        break;
                    }
                }
                case 2:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 25: {
                    count = PowerItemSet.ARMORHOLE;
                    if (tgItem.get_power_name() != null) {
                        power = tgItem.get_power_name();
                        update = true;
                        break;
                    }
                    else {
                        power = new L1ItemPower_name();
                        break;
                    }
                }
            }
            if (power != null) {
                power.set_item_obj_id(tgItem.getId());
                power.set_hole_count(count);
                power.set_hole_1(0);
                power.set_hole_2(0);
                power.set_hole_3(0);
                power.set_hole_4(0);
                power.set_hole_5(0);
                tgItem.set_power_name(power);
                if (update) {
                    CharItemPowerReading.get().updateItem(tgItem.getId(), tgItem.get_power_name());
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    pc.sendPackets(new S_ItemName(tgItem));
                }
                else {
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    pc.sendPackets(new S_ItemName(tgItem));
                    CharItemPowerReading.get().storeItem(tgItem.getId(), tgItem.get_power_name());
                }
                pc.sendPackets(new S_ItemName(tgItem));
            }
            return;
        }
    }
}
