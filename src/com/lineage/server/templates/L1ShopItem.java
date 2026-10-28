package com.lineage.server.templates;

import com.lineage.server.datatables.ItemTable;

public class L1ShopItem
{
    private final int _id;
    private final int _itemId;
    private final L1Item _item;
    private final int _price;
    private final int _packCount;
    private final int _EnchantLevel;
    private final int _DailyBuyingCount;
    private final int _DailyBuyinglevel;
    
    public L1ShopItem(int id, int itemId, int price, int packCount, int enchantlevel, int dailybuyingCount, int dailybuyinglevel) {
        this._id = id;
        this._itemId = itemId;
        this._item = ItemTable.get().getTemplate(itemId);
        this._price = price;
        this._packCount = packCount;
        this._EnchantLevel = enchantlevel;
        this._DailyBuyingCount = dailybuyingCount;
        this._DailyBuyinglevel = dailybuyinglevel;
    }
    
    public L1ShopItem(int itemId, int price, int packCount, int enchantlevel, int dailybuyingCount, int dailybuyinglevel) {
        this._id = -1;
        this._itemId = itemId;
        this._item = ItemTable.get().getTemplate(itemId);
        this._price = price;
        this._packCount = packCount;
        this._EnchantLevel = enchantlevel;
        this._DailyBuyingCount = dailybuyingCount;
        this._DailyBuyinglevel = dailybuyinglevel;
    }
    
    public int getId() {
        return this._id;
    }
    
    public int getItemId() {
        return this._itemId;
    }
    
    public L1Item getItem() {
        return this._item;
    }
    
    public int getPrice() {
        return this._price;
    }
    
    public int getPackCount() {
        return this._packCount;
    }
    
    public int getEnchantLevel() {
        return this._EnchantLevel;
    }
    
    public int getDailyBuyingCount() {
        return this._DailyBuyingCount;
    }
    
    public int getDailyBuyinglevel() {
        return this._DailyBuyinglevel;
    }
}
