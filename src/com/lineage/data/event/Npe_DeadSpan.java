package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class Npe_DeadSpan extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(Npe_DeadSpan.class);
        Npe_DeadSpan.START = false;
    }
    
    public static EventExecutor get() {
        return new Npe_DeadSpan();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            Npe_DeadSpan.START = true;
        }
        catch (Exception e) {
            Npe_DeadSpan._log.error(e.getLocalizedMessage(), e);
        }
    }
}
