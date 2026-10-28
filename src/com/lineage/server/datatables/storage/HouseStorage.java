package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1House;
import java.util.Map;

public interface HouseStorage
{
    public abstract void load();
    
    public abstract Map<Integer, L1House> getHouseTableList();
    
    public abstract L1House getHouseTable(int p0);
    
    public abstract void updateHouse(L1House p0);
}
