package com.lineage.data.item_etcitem;

import java.util.Iterator;
import java.util.List;
import com.lineage.server.templates.L1ItemVIP;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.ItemVIPTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class VIP extends ItemExecutor
{
    public static ItemExecutor get() {
        return new VIP();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int item_id = item.getItemId();
        L1ItemVIP vip = ItemVIPTable.get().getVIP(item_id);
        if (vip == null) {
            return;
        }
        else {
            if (vip.get_classid() > 0) {
                byte class_id = 0;
                String msg = "";
                if (pc.isCrown()) {
                    class_id = 1;
                }
                else if (pc.isKnight()) {
                    class_id = 2;
                }
                else if (pc.isWizard()) {
                    class_id = 3;
                }
                else if (pc.isElf()) {
                    class_id = 4;
                }
                else if (pc.isDarkelf()) {
                    class_id = 5;
                }
                else if (pc.isDragonKnight()) {
                    class_id = 6;
                }
                else if (pc.isIllusionist()) {
                    class_id = 7;
                }
                switch (vip.get_classid()) {
                    case 1: {
                        msg = "王族";
                        break;
                    }
                    case 2: {
                        msg = "騎士";
                        break;
                    }
                    case 3: {
                        msg = "法師";
                        break;
                    }
                    case 4: {
                        msg = "妖精";
                        break;
                    }
                    case 5: {
                        msg = "黑暗妖精";
                        break;
                    }
                    case 6: {
                        msg = "龍騎士";
                        break;
                    }
                    case 7: {
                        msg = "幻術士";
                        break;
                    }
                }
                if (vip.get_classid() != class_id) {
                    pc.sendPackets(new S_SystemMessage("該物品限制於[" + msg + "]職業使用。"));
                    return;
                }
            }
            if (item.isEquipped()) {
                item.setEquipped(false);
                ItemVIPTable.get().deleItemVIP(pc, item_id);
                pc.getInventory().updateItem(item, 8);
                pc.getInventory().saveItem(item, 8);
            }
            else {
                boolean isvip = false;
                List<L1ItemInstance> items = pc.getInventory().getItems();
                for (L1ItemInstance tgitem : items) {
                    if (!tgitem.isEquipped()) {
                        continue;
                    }
                    else {
                        int tgitem_id = tgitem.getItemId();
                        L1ItemVIP tgvip = ItemVIPTable.get().getVIP(tgitem_id);
                        if (tgvip == null) {
                            continue;
                        }
                        else if (tgvip.get_type() == vip.get_type()) {
                            isvip = true;
                            break;
                        }
                        else {
                            continue;
                        }
                    }
                }
                if (isvip) {
                    pc.sendPackets(new S_ServerMessage(66));
                }
                else {
                    item.setEquipped(true);
                    ItemVIPTable.get().addItemVIP(pc, item_id);
                    pc.getInventory().updateItem(item, 8);
                    pc.getInventory().saveItem(item, 8);
                }
            }
            return;
        }
    }
}
