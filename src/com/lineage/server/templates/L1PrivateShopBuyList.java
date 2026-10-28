package com.lineage.server.templates;

public class L1PrivateShopBuyList
{
    private int _itemObjectId;
    private int _buyTotalCount;
    private int _buyPrice;
    private int _buyCount;
    
    public void setItemObjectId(int i) {
        this._itemObjectId = i;
    }
    
    public int getItemObjectId() {
        return this._itemObjectId;
    }
    
    public void setBuyTotalCount(int i) {
        this._buyTotalCount = i;
    }
    
    public int getBuyTotalCount() {
        return this._buyTotalCount;
    }
    
    public void setBuyPrice(int i) {
        this._buyPrice = i;
    }
    
    public int getBuyPrice() {
        return this._buyPrice;
    }
    
    public void setBuyCount(int i) {
        this._buyCount = i;
    }
    
    public int getBuyCount() {
        return this._buyCount;
    }
}
