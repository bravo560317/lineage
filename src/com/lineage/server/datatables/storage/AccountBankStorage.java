package com.lineage.server.datatables.storage;

import java.util.Map;
import com.lineage.server.templates.L1Bank;

public interface AccountBankStorage
{
    public abstract void load();
    
    public abstract L1Bank get(String p0);
    
    public abstract Map<String, L1Bank> map();
    
    public abstract void create(String p0, L1Bank p1);
    
    public abstract void updatePass(String p0, String p1);
    
    public abstract void updateAdena(String p0, long p1);
}
