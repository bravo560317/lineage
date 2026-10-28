package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1PcOther3;
import com.lineage.server.model.Instance.L1PcInstance;

public interface CharOtherStorage3
{
    public abstract void load();
    
    public abstract L1PcOther3 getOther(L1PcInstance p0);
    
    public abstract void storeOther(int p0, L1PcOther3 p1);
    
    public abstract void tam();
}
