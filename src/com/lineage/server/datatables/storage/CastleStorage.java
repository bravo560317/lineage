package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1Castle;
import java.util.Map;

public interface CastleStorage
{
    public abstract void load();
    
    public abstract Map<Integer, L1Castle> getCastleMap();
    
    public abstract L1Castle[] getCastleTableList();
    
    public abstract L1Castle getCastleTable(int p0);
    
    public abstract void updateCastle(L1Castle p0);
}
