package com.lineage.server.datatables.storage;

import java.util.Map;

public interface EzpayStorage1
{
    public abstract Map<Integer, int[]> ezpayInfo(String p0);
    
    public abstract int[] ezpayInfo(String p0, int p1);
    
    public abstract boolean update(String p0, int p1, String p2, String p3);
}
