package com.lineage.server.templates;

public class DeShopItem
{
    private int _id;
    private int _item_id;
    private int _price;
    private int _sellcount;
    private int _buycount;
    private int _enchantlvl;
    
    public int get_id() {
        return this._id;
    }
    
    public void set_id(int id) {
        this._id = id;
    }
    
    public int get_item_id() {
        return this._item_id;
    }
    
    public void set_item_id(int item_id) {
        this._item_id = item_id;
    }
    
    public int get_price() {
        return this._price;
    }
    
    public void set_price(int price) {
        this._price = price;
    }
    
    public int get_sellcount() {
        return this._sellcount;
    }
    
    public void set_sellcount(int sellcount) {
        this._sellcount = sellcount;
    }
    
    public int get_buycount() {
        return this._buycount;
    }
    
    public void set_buycount(int buycount) {
        this._buycount = buycount;
    }
    
    public int get_enchantlvl() {
        return this._enchantlvl;
    }
    
    public void set_enchantlvl(int enchantlvl) {
        this._enchantlvl = enchantlvl;
    }
}
