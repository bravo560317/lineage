package com.lineage.server.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public interface LogEnchantStorage
{
    public abstract void failureEnchant(L1PcInstance p0, L1ItemInstance p1);
}
