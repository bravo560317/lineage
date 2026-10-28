package com.lineage.server.datatables.storage;

import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.Timestamp;
import java.util.Map;

public interface VIPStorage
{
    public abstract void load();
    
    public abstract Map<Integer, Timestamp> map();
    
    public abstract Timestamp getOther(L1PcInstance p0);
    
    public abstract void storeOther(int p0, Timestamp p1);
    
    public abstract void delOther(int p0);
}