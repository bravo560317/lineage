package com.lineage.server.datatables.storage;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1User_Power;

public interface CharacterC1Storage
{
    public abstract void load();
    
    public abstract L1User_Power get(int p0);
    
    public abstract void storeCharacterC1(L1PcInstance p0);
    
    public abstract void updateCharacterC1(int p0, int p1, String p2);
}
