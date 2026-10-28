package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class Addskill extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(Addskill.class);
        Addskill.START = false;
    }
    
    public static EventExecutor get() {
        return new Addskill();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            Addskill.START = true;
        }
        catch (Exception e) {
            Addskill._log.error(e.getLocalizedMessage(), e);
        }
    }
}
