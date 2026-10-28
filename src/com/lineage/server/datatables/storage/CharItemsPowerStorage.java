package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1ItemPower_name;

public interface CharItemsPowerStorage
{
    public abstract void load();
    
    public abstract void storeItem(int p0, L1ItemPower_name p1) throws Exception;
    
    public abstract void updateItem(int p0, L1ItemPower_name p1);
}
