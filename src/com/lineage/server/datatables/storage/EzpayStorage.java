package com.lineage.server.datatables.storage;

import java.util.Map;

public interface EzpayStorage
{
    public abstract Map<Integer, int[]> ezpayInfo(String p0);
    
    public abstract boolean update(String p0, int p1, String p2, String p3);
}
