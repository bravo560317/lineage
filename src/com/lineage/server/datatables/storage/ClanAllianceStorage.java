package com.lineage.server.datatables.storage;

import com.lineage.server.model.L1Clan;
import java.util.ArrayList;
import com.lineage.server.model.L1Alliance;

public interface ClanAllianceStorage
{
    public abstract void load();
    
    public abstract void insertAlliance(L1Alliance p0);
    
    public abstract void updateAlliance(int p0, ArrayList<L1Clan> p1);
    
    public abstract void deleteAlliance(int p0);
    
    public abstract L1Alliance getAlliance(int p0);
}
