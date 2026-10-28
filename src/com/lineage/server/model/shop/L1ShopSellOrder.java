package com.lineage.server.model.shop;

class L1ShopSellOrder
{
    private final L1AssessedItem _item;
    private final int _count;
    
    public L1ShopSellOrder(L1AssessedItem item, int count) {
        this._item = item;
        this._count = Math.max(0, count);
    }
    
    public L1AssessedItem getItem() {
        return this._item;
    }
    
    public int getCount() {
        return this._count;
    }
}
