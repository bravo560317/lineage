package com.lineage.data.event;

import com.lineage.server.datatables.lock.MaryReading;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class MarySet extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(MarySet.class);
        MarySet.START = false;
    }
    
    public static EventExecutor get() {
        return new MarySet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            MarySet.START = true;
            MaryReading.get().load();
        }
        catch (Exception e) {
            MarySet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
