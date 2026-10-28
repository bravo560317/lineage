package com.lineage.server.datatables.storage;

import com.lineage.server.model.Instance.L1PcInstance;

public interface CharBuffStorage
{
    public abstract void load();
    
    public abstract void saveBuff(L1PcInstance p0);
    
    public abstract void buff(L1PcInstance p0);
    
    public abstract void deleteBuff(L1PcInstance p0);
    
    public abstract void deleteBuff(int p0);
}
