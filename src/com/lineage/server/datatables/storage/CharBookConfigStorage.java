package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1BookConfig;

public interface CharBookConfigStorage
{
    public abstract void load();
    
    public abstract L1BookConfig get(int p0);
    
    public abstract L1BookConfig storeCharBookConfig(int p0, byte[] p1);
    
    public abstract void updateCharBookConfig(int p0, byte[] p1);
    
    public abstract void updateCharBookMaxSize(int p0, int p1);
}
