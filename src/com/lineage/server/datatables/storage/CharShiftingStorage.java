package com.lineage.server.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Item;
import com.lineage.server.model.Instance.L1PcInstance;

public interface CharShiftingStorage
{
    public abstract void newShifting(L1PcInstance p0, int p1, String p2, int p3, L1Item p4, L1ItemInstance p5, int p6);
}
