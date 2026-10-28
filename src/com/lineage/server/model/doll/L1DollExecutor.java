package com.lineage.server.model.doll;

import com.lineage.server.model.Instance.L1PcInstance;

public abstract class L1DollExecutor
{
    public abstract void set_power(int p0, int p1, int p2, double p3);
    
    public abstract String get_note();
    
    public abstract void set_note(String p0);
    
    public abstract void setDoll(L1PcInstance p0);
    
    public abstract void removeDoll(L1PcInstance p0);
    
    public abstract boolean is_reset();
}
