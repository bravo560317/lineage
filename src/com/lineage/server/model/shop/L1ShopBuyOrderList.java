package com.lineage.server.model.shop;

import com.lineage.config.ConfigRate;
import com.lineage.server.templates.L1ShopItem;
import java.util.ArrayList;
import com.lineage.server.model.L1TaxCalculator;
import java.util.List;

public class L1ShopBuyOrderList
{
    private final L1Shop _shop;
    private final List<L1ShopBuyOrder> _list;
    private final L1TaxCalculator _taxCalc;
    private int _totalWeight;
    private int _totalPrice;
    private int _totalPriceTaxIncluded;
    
    public L1ShopBuyOrderList(L1Shop shop) {
        this._list = new ArrayList();
        this._totalWeight = 0;
        this._totalPrice = 0;
        this._totalPriceTaxIncluded = 0;
        this._shop = shop;
        this._taxCalc = new L1TaxCalculator(shop.getNpcId());
    }
    
    public boolean isEmpty() {
        return this._list.isEmpty();
    }
    
    public void add(int orderNumber, int count) {
        if (this._shop.getSellingItems().size() < orderNumber) {
            return;
        }
        else {
            int ch_count = Math.max(0, count);
            if (ch_count <= 0) {
                return;
            }
            else {
                L1ShopItem shopItem = this._shop.getSellingItems().get(orderNumber);
                int price = (int)((double)shopItem.getPrice() * ConfigRate.RATE_SHOP_SELLING_PRICE);
                int j = 0;
                while (j < ch_count) {
                    if (price * j < 0) {
                        return;
                    }
                    else {
                        ++j;
                    }
                }
                if (this._totalPrice < 0) {
                    return;
                }
                else {
                    this._totalPrice += price * ch_count;
                    this._totalPriceTaxIncluded += this._taxCalc.layTax(price) * ch_count;
                    this._totalWeight += shopItem.getItem().getWeight() * ch_count * shopItem.getPackCount();
                    if (shopItem.getItem().isStackable()) {
                        this._list.add(new L1ShopBuyOrder(shopItem, ch_count * shopItem.getPackCount()));
                        return;
                    }
                    else {
                        for (int i = 0; i < ch_count * shopItem.getPackCount(); ++i) {
                            this._list.add(new L1ShopBuyOrder(shopItem, 1));
                        }
                        return;
                    }
                }
            }
        }
    }
    
    List<L1ShopBuyOrder> getList() {
        return this._list;
    }
    
    public int getTotalWeight() {
        return this._totalWeight;
    }
    
    public int getTotalPrice() {
        return this._totalPrice;
    }
    
    public int getTotalPriceTaxIncluded() {
        return this._totalPriceTaxIncluded;
    }
    
    L1TaxCalculator getTaxCalculator() {
        return this._taxCalc;
    }
}
