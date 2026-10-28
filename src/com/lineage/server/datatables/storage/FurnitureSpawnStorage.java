package com.lineage.server.datatables.storage;

import com.lineage.server.model.Instance.L1FurnitureInstance;

public interface FurnitureSpawnStorage
{
    public abstract void load();
    
    public abstract void insertFurniture(L1FurnitureInstance p0);
    
    public abstract void deleteFurniture(L1FurnitureInstance p0);
}
