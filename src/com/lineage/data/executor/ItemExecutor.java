package com.lineage.data.executor;

import com.lineage.server.utils.BinaryOutputStream;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public abstract class ItemExecutor
{
    private String[] as;
    
    public abstract void execute(int[] p0, L1PcInstance p1, L1ItemInstance p2);
    
    public String[] get_set() {
        return this.as;
    }
    
    public void set_set(String[] set) {
        this.as = set;
    }
    
    public BinaryOutputStream itemStatus(L1ItemInstance item) {
        return null;
    }
}
