package com.lineage.server.model.c1;

import com.lineage.server.model.Instance.L1PcInstance;

public abstract class C1Executor
{
    public abstract void set_power(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9);
    
    public abstract void set_c1(L1PcInstance p0);
    
    public abstract void remove_c1(L1PcInstance p0);
}
