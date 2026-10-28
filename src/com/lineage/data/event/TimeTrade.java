package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class TimeTrade extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(TimeTrade.class);
        TimeTrade.START = false;
    }
    
    public static EventExecutor get() {
        return new TimeTrade();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            TimeTrade.START = true;
        }
        catch (Exception e) {
            TimeTrade._log.error(e.getLocalizedMessage(), e);
        }
    }
}
