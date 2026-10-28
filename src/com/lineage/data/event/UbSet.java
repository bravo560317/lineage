package com.lineage.data.event;

import com.lineage.server.timecontroller.event.UbTime;
import com.lineage.server.datatables.UBSpawnTable;
import com.lineage.server.datatables.UBTable;
import com.lineage.server.templates.L1Event;
import com.lineage.data.executor.EventExecutor;

public class UbSet extends EventExecutor
{
    public static EventExecutor get() {
        return new UbSet();
    }
    
    @Override
    public void execute(L1Event event) {
        UBTable.getInstance().load();
        UBSpawnTable.getInstance().load();
        UbTime ubTimeContoroller = new UbTime();
        ubTimeContoroller.start();
    }
}
