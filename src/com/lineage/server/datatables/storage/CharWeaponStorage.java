package com.lineage.server.datatables.storage;

import java.sql.Timestamp;

public interface CharWeaponStorage
{
    public abstract void load();
    
    public abstract void addTime(int p0, Timestamp p1, int p2);
    
    public abstract void updateTime(int p0, Timestamp p1, int p2, int p3, int p4);
}
