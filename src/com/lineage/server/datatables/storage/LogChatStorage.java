package com.lineage.server.datatables.storage;

import com.lineage.server.model.Instance.L1PcInstance;

public interface LogChatStorage
{
    public abstract void isTarget(L1PcInstance p0, L1PcInstance p1, String p2, int p3);
    
    public abstract void noTarget(L1PcInstance p0, String p1, int p2);
}
