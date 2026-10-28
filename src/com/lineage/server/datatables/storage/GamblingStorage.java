package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1Gambling;

public interface GamblingStorage
{
    public abstract void load();
    
    public abstract L1Gambling getGambling(String p0);
    
    public abstract L1Gambling getGambling(int p0);
    
    public abstract void add(L1Gambling p0);
    
    public abstract void updateGambling(int p0, int p1);
    
    public abstract int[] winCount(int p0);
    
    public abstract int maxId();
}
