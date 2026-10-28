package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class Chatvip extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(Chatvip.class);
        Chatvip.START = false;
    }
    
    public static EventExecutor get() {
        return new Chatvip();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            Chatvip.START = true;
        }
        catch (Exception e) {
            Chatvip._log.error(e.getLocalizedMessage(), e);
        }
    }
}
