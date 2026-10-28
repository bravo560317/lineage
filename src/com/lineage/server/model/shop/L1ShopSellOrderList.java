package com.lineage.server.model.shop;

import java.util.ArrayList;
import java.util.List;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1ShopSellOrderList
{
    private final L1Shop _shop;
    private final L1PcInstance _pc;
    private final List<L1ShopSellOrder> _list;
    
    L1ShopSellOrderList(L1Shop shop, L1PcInstance pc) {
        this._list = new ArrayList();
        this._shop = shop;
        this._pc = pc;
    }
    
    public void add(int itemObjectId, int count) {
        L1AssessedItem assessedItem = this._shop.assessItem(this._pc.getInventory().getItem(itemObjectId));
        if (assessedItem == null) {
            throw new IllegalArgumentException();
        }
        else {
            this._list.add(new L1ShopSellOrder(assessedItem, count));
            return;
        }
    }
    
    L1PcInstance getPc() {
        return this._pc;
    }
    
    List<L1ShopSellOrder> getList() {
        return this._list;
    }
}
