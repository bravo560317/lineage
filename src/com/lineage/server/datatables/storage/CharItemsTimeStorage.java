package com.lineage.server.datatables.storage;

import java.sql.Timestamp;

public interface CharItemsTimeStorage
{
    public abstract void load();
    
    public abstract void addTime(int p0, Timestamp p1);
    
    public abstract void updateTime(int p0, Timestamp p1);
    
    public abstract boolean isExistTimeData(int p0);
}
