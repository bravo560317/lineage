package com.lineage.server.serverpackets;

import com.lineage.server.model.shop.L1Shop;
import java.util.ArrayList;
import com.lineage.server.datatables.ShopTable;
import com.lineage.server.datatables.ItemPowerUpdateTable;
import com.lineage.server.datatables.ShopCnTable;
import java.util.HashMap;
import com.lineage.server.model.L1PcInventory;
import java.util.Iterator;
import java.util.Map;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_ShopBuyListAll extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ShopBuyListAll(L1PcInstance pc, L1NpcInstance npc) {
        this._byte = null;
        Map<L1ItemInstance, Integer> assessedItems = this.assessItems(pc.getInventory());
        if (assessedItems.isEmpty()) {
            pc.sendPackets(new S_NoSell(npc));
            return;
        }
        else if (assessedItems.size() <= 0) {
            pc.sendPackets(new S_NoSell(npc));
            return;
        }
        else {
            this.writeC(65);
            this.writeD(npc.getId());
            this.writeH(assessedItems.size());
            for (L1ItemInstance key : assessedItems.keySet()) {
                this.writeD(key.getId());
                this.writeD(assessedItems.get(key));
            }
            this.writeH(7);
            return;
        }
    }
    
    private Map<L1ItemInstance, Integer> assessItems(L1PcInventory inv) {
        Map<L1ItemInstance, Integer> result = new HashMap();
        for (L1ItemInstance item : inv.getItems()) {
            switch (item.getItem().getItemId()) {
                case 40308:
                case 40314:
                case 40316:
                case 41246:
                case 80033:
                case 83000:
                case 83022: {
                    continue;
                }
                default: {
                    if (!item.isEquipped()) {
                        int itemid = item.getItemId();
                        ArrayList<Integer> cnlist = ShopCnTable.get().get_cnitemidlist();
                        if (cnlist.contains((Integer)itemid)) {
                            continue;
                        }
                        else {
                            ArrayList<Integer> uplist = ItemPowerUpdateTable.get().get_updeatitemidlist();
                            if (uplist.contains((Integer)itemid)) {
                                continue;
                            }
                            else {
                                boolean contains = ShopCnTable.get().isSelling(110811, itemid);
                                if (contains) {
                                    continue;
                                }
                                else {
                                    L1Shop shop = ShopTable.get().get(110641);
                                    if (shop != null) {
                                        if (shop.isSelling(itemid)) {
                                            continue;
                                        }
                                        else if (shop.isPurchasing(itemid)) {
                                            continue;
                                        }
                                    }
                                    L1Shop shop2 = ShopTable.get().get(200206);
                                    if (shop2 != null) {
                                        if (shop2.isSelling(itemid)) {
                                            continue;
                                        }
                                        else if (shop2.isPurchasing(itemid)) {
                                            continue;
                                        }
                                    }
                                    if (item.getBless() >= 128) {
                                        continue;
                                    }
                                    else {
                                        double price = (double)ShopTable.get().getPrice(itemid);
                                        double maxchargeCount = (double)item.getItem().getMaxChargeCount();
                                        double chargecount = (double)item.getChargeCount();
                                        if (maxchargeCount > 0.0) {
                                            price *= chargecount / maxchargeCount;
                                        }
                                        if (price > 0.0) {
                                            result.put(item, new Integer((int)price));
                                            continue;
                                        }
                                        else {
                                            continue;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        return result;
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
