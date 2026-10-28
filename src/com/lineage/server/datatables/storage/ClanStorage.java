package com.lineage.server.datatables.storage;

import java.util.Map;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Clan;

public interface ClanStorage
{
    public abstract void load();
    
    public abstract void addDeClan(Integer p0, L1Clan p1);
    
    public abstract L1Clan createClan(L1PcInstance p0, String p1);
    
    public abstract void updateClan(L1Clan p0);
    
    public abstract void deleteClan(String p0);
    
    public abstract L1Clan getTemplate(int p0);
    
    public abstract Map<Integer, L1Clan> get_clans();
    
    public abstract void updateClanSkill(L1Clan p0);
}
