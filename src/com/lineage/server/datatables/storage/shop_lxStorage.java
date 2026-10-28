package com.lineage.server.datatables.storage;

import java.util.Map;

public interface shop_lxStorage
{
    public abstract Map<Integer, int[]> ezpayInfo(String p0);
    
    public abstract int[] ezpayInfo(String p0, int p1);
    
    public abstract boolean update();
    
    public abstract void updatemejs(int p0, String p1);
    
    public abstract int count(String p0);
    
    public abstract void remove(String p0);
}
