package com.lineage.data.item_etcitem;

import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class FishingPoleRoller extends ItemExecutor
{
    private static final Log _log;
    private int _fishpoleid;
    
    static {
        _log = LogFactory.getLog(FishingPoleRoller.class);
    }
    
    public static ItemExecutor get() {
        return new FishingPoleRoller();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemobj = data[0];
        L1ItemInstance tgitem = pc.getInventory().getItem(itemobj);
        if (tgitem == null) {
            return;
        }
        else {
            this.useRoller(pc, tgitem, item);
            return;
        }
    }
    
    private void useRoller(L1PcInstance pc, L1ItemInstance tgitem, L1ItemInstance item) {
        if (tgitem == null || item == null) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if (tgitem.getItem().getUseType() != 42) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if (tgitem.getItemId() == 83014 && item.getItemId() != 83004) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if (tgitem.getItemId() == 83024 && item.getItemId() != 83023) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            if (tgitem.getItemId() == 83014 || tgitem.getItemId() == 83024) {
                int maxchargecount = tgitem.getItem().getMaxChargeCount();
                if (tgitem.getChargeCount() + 100 >= maxchargecount) {
                    tgitem.setChargeCount(maxchargecount);
                }
                else {
                    tgitem.setChargeCount(tgitem.getChargeCount() + 100);
                }
                pc.getInventory().updateItem(tgitem, 128);
                pc.getInventory().removeItem(item, 1L);
            }
            if (tgitem.getItemId() == 83001) {
                L1ItemInstance newitem = ItemTable.get().createItem(this._fishpoleid);
                newitem.setChargeCount(100);
                newitem.setIdentified(true);
                pc.getInventory().storeItem(newitem);
                pc.getInventory().removeItem(tgitem, 1L);
                pc.getInventory().removeItem(item, 1L);
            }
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._fishpoleid = Integer.parseInt(set[1]);
            if (this._fishpoleid <= 0) {
                this._fishpoleid = 83014;
                FishingPoleRoller._log.error("FishingPoleRoller 設置錯誤:釣竿編號錯誤! 使用預設83014");
            }
        }
        catch (Exception ex) {}
    }
}
