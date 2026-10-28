package com.lineage.server.datatables.storage;

import java.sql.Timestamp;

public interface IpStorage
{
    public abstract void load();
    
    public abstract void add(String p0, String p1);
    
    public abstract void remove(String p0);
    
    public abstract void setUnbanTime(Timestamp p0);
    
    public abstract void checktime(String p0);
}
