package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import com.lineage.data.executor.EventExecutor;

public class SpawnOtherSet extends EventExecutor
{
    public static EventExecutor get() {
        return new SpawnOtherSet();
    }
    
    @Override
    public void execute(L1Event event) {
    }
}
