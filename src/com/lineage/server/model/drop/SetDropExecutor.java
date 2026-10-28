package com.lineage.server.model.drop;

import com.lineage.server.templates.L1DropMob;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.templates.L1DropMap;
import com.lineage.server.templates.L1Drop;
import java.util.ArrayList;
import java.util.Map;

public interface SetDropExecutor
{
    public abstract void addDropMap(Map<Integer, ArrayList<L1Drop>> p0);
    
    public abstract void addDropMapX(Map<Integer, ArrayList<L1DropMap>> p0);
    
    public abstract void setDrop(L1NpcInstance p0, L1Inventory p1);
    
    public abstract void setDrop(L1NpcInstance p0, L1Inventory p1, double p2);
    
    public abstract void addDropMob(Map<Integer, L1DropMob> p0);
}
