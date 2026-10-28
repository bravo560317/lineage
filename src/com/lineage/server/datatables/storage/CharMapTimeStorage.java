package com.lineage.server.datatables.storage;

import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Map;

public interface CharMapTimeStorage
{
    public abstract void load();
    
    public abstract Map<Integer, Integer> addTime(int p0, int p1, int p2);
    
    public abstract void getTime(L1PcInstance p0);
    
    public abstract void deleteTime(int p0);
    
    public abstract void saveAllTime();
    
    public abstract void clearAllTime();
    
    public abstract void update(int p0, int p1, int p2);
}
