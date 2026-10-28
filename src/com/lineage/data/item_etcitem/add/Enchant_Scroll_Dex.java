package com.lineage.data.item_etcitem.add;

import com.lineage.server.datatables.sql.CharItemsTable;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Enchant_Scroll_Dex extends ItemExecutor
{
    private int type1;
    private int type2;
    
    private Enchant_Scroll_Dex() {
    }
    
    public static ItemExecutor get() {
        return new Enchant_Scroll_Dex();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance l1iteminstance = pc.getInventory().getItem(targObjId);
        if (l1iteminstance == null) {
            return;
        }
        else if (!pc.getInventory().checkItem(l1iteminstance.getItem().getItemId(), 1L)) {
            return;
        }
        else if (l1iteminstance.isEquipped()) {
            pc.sendPackets(new S_SystemMessage("該裝備尚未脫掉。"));
            return;
        }
        else if (l1iteminstance.getItemDex() == this.type1) {
            pc.sendPackets(new S_SystemMessage("該武器能力已達上限"));
            return;
        }
        else {
            if (l1iteminstance.getItem().getType2() == 1) {
                if (RandomArrayList.getInc(100, 1) <= this.type2) {
                    l1iteminstance.setItemDex(l1iteminstance.getItemDex() + 1);
                    pc.getInventory().removeItem(item, 1L);
                    pc.sendPackets(new S_SystemMessage("強化成功"));
                }
                else if (l1iteminstance.getItemDex() == 0) {
                    pc.sendPackets(new S_SystemMessage("沒發生什麼事情"));
                    return;
                }
                else {
                    l1iteminstance.setItemDex(l1iteminstance.getItemDex() - 1);
                    pc.sendPackets(new S_SystemMessage("強化敏捷失敗"));
                }
            }
            pc.sendPackets(new S_ItemStatus(l1iteminstance));
            CharItemsTable cit = new CharItemsTable();
            try {
                cit.updateItemDex(l1iteminstance);
                pc.save();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this.type1 = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this.type2 = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
    }
}
