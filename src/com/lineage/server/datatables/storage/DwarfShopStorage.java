package com.lineage.server.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Map;
import com.lineage.server.templates.L1ShopS;
import java.util.HashMap;

public interface DwarfShopStorage
{
    public abstract void load();
    
    public abstract int get_id();
    
    public abstract void set_id(int p0);
    
    public abstract HashMap<Integer, L1ShopS> allShopS();
    
    public abstract Map<Integer, L1ItemInstance> allItems();
    
    public abstract L1ShopS getShopS(int p0);
    
    public abstract HashMap<Integer, L1ShopS> getShopSMap(int p0);
    
    public abstract void insertItem(int p0, L1ItemInstance p1, L1ShopS p2);
    
    public abstract void updateShopS(L1ShopS p0);
    
    public abstract void deleteItem(int p0);
}
