package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1Rank;
import java.util.List;

public interface BoardOrimStorage
{
    public abstract void load();
    
    public abstract List<L1Rank> getTotalList();
    
    public abstract int writeTopic(int p0, String p1, List<String> p2);
    
    public abstract void renewPcName(String p0, String p1);
}
