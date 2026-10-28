package com.lineage.server.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;

public interface DwarfForElfStorage
{
    public abstract void load();
    
    public abstract CopyOnWriteArrayList<L1ItemInstance> loadItems(String p0);
    
    public abstract void delUserItems(String p0);
    
    public abstract boolean getUserItems(String p0, int p1, int p2);
    
    public abstract void insertItem(String p0, L1ItemInstance p1);
    
    public abstract void updateItem(L1ItemInstance p0);
    
    public abstract void deleteItem(String p0, L1ItemInstance p1);
}
