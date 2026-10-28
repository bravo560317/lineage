package com.lineage.server.templates;

public class L1PrivateShopSellList
{
    private int _itemObjectId;
    private int _sellTotalCount;
    private int _sellPrice;
    private int _sellCount;
    
    public void setItemObjectId(int i) {
        this._itemObjectId = i;
    }
    
    public int getItemObjectId() {
        return this._itemObjectId;
    }
    
    public void setSellTotalCount(int i) {
        this._sellTotalCount = i;
    }
    
    public int getSellTotalCount() {
        return this._sellTotalCount;
    }
    
    public void setSellPrice(int i) {
        this._sellPrice = i;
    }
    
    public int getSellPrice() {
        return this._sellPrice;
    }
    
    public void setSellCount(int i) {
        this._sellCount = i;
    }
    
    public int getSellCount() {
        return this._sellCount;
    }
}
