package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class NowTimeSpawn extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(NowTimeSpawn.class);
        NowTimeSpawn.START = false;
    }
    
    public static EventExecutor get() {
        return new NowTimeSpawn();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            NowTimeSpawn.START = true;
        }
        catch (Exception e) {
            NowTimeSpawn._log.error(e.getLocalizedMessage(), e);
        }
    }
}
