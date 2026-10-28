package com.lineage.server.datatables.storage;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1Board;
import java.util.Map;

public interface BoardStorage
{
    public abstract void load();
    
    public abstract Map<Integer, L1Board> getBoardMap();
    
    public abstract L1Board[] getBoardTableList();
    
    public abstract L1Board getBoardTable(int p0);
    
    public abstract int getMaxId();
    
    public abstract void writeTopic(L1PcInstance p0, String p1, String p2, String p3);
    
    public abstract void deleteTopic(int p0);
}
