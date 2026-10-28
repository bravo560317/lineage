package com.lineage.data.item_etcitem.add;

import com.lineage.william.ExcavateTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class Excavate extends ItemExecutor
{
    private static Random _random;
    
    static {
        Excavate._random = new Random();
    }
    
    private Excavate() {
    }
    
    public static ItemExecutor get() {
        return new Excavate();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        ExcavateTable.forExcavate(pc);
    }
}
