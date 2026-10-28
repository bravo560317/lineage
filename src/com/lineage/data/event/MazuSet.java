package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class MazuSet extends EventExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(MazuSet.class);
    }
    
    public static EventExecutor get() {
        return new MazuSet();
    }
    
    @Override
    public void execute(L1Event event) {
    }
}
