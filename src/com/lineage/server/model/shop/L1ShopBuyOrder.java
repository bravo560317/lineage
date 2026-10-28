package com.lineage.server.model.shop;

import com.lineage.server.templates.L1ShopItem;

class L1ShopBuyOrder
{
    private final L1ShopItem _item;
    private final int _count;
    
    public L1ShopBuyOrder(L1ShopItem item, int count) {
        this._item = item;
        this._count = Math.max(0, count);
    }
    
    public L1ShopItem getItem() {
        return this._item;
    }
    
    public int getCount() {
        return this._count;
    }
}
