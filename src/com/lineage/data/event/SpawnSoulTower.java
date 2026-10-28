package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import com.lineage.data.executor.EventExecutor;

public class SpawnSoulTower extends EventExecutor
{
    private SpawnSoulTower() {
    }
    
    public static EventExecutor get() {
        return new SpawnSoulTower();
    }
    
    @Override
    public void execute(L1Event event) {
    }
}
