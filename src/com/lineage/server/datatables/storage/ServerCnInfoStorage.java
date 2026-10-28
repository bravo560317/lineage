package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1Item;
import com.lineage.server.model.Instance.L1PcInstance;

public interface ServerCnInfoStorage
{
    public abstract void create(L1PcInstance p0, L1Item p1, long p2, boolean p3, int p4);
}
