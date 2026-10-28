package com.lineage.server.datatables.storage;

public interface ServerStorage
{
    public abstract void load();
    
    public abstract int minId();
    
    public abstract int maxId();
    
    public abstract void isStop();
}
