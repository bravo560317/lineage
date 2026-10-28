package com.lineage.data.item_etcitem.add;

import com.lineage.server.templates.L1ItemPowerUpdate;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemPowerUpdateTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class saveweaponH extends ItemExecutor
{
    private saveweaponH() {
    }
    
    public static ItemExecutor get() {
        return new saveweaponH();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        int itemobj = data[0];
        L1ItemInstance item2 = pc.getInventory().getItem(itemobj);
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else {
            L1ItemPowerUpdate info = ItemPowerUpdateTable.get().get(item2.getItemId());
            if (info == null) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else if (info.get_mode() == 4) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else {
                if (tgItem != null) {
                    if (tgItem.getItem().getItemId() <= -1) {
                        pc.sendPackets(new S_ServerMessage(79));
                        return;
                    }
                    else if (tgItem.getproctect7()) {
                        pc.sendPackets(new S_ServerMessage("目前該裝備已經受到保護中"));
                        return;
                    }
                    else {
                        tgItem.setproctect7(true);
                        pc.sendPackets(new S_ItemStatus(tgItem));
                        pc.getInventory().saveItem(tgItem, 4);
                        pc.sendPackets(new S_ServerMessage("保護卷軸的力量附著的物品中。"));
                        pc.getInventory().removeItem(item, 1L);
                        RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "賦予升接保護", pc.getIp());
                    }
                }
                return;
            }
        }
    }
}
