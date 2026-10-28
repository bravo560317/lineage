package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1Account;

public interface AccountStorage
{
    public abstract void load();
    
    public abstract boolean isAccountUT(String p0);
    
    public abstract L1Account create(String p0, String p1, String p2, String p3, String p4);
    
    public abstract boolean isAccount(String p0);
    
    public abstract L1Account getAccount(String p0);
    
    public abstract void updateWarehouse(String p0, int p1);
    
    public abstract void updateLastActive(L1Account p0);
    
    public abstract void updateCharacterSlot(String p0, int p1);
    
    public abstract void updatePwd(String p0, String p1);
    
    public abstract void updateLan(String p0, boolean p1);
    
    public abstract void updateLan();
    
    public abstract void updatefp(String p0, int p1);
    
    public abstract void update_idcard(String p0, int p1);
    
    public abstract String getPalyer(String p0);
}
