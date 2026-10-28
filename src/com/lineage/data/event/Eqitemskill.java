package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class Eqitemskill extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(Eqitemskill.class);
        Eqitemskill.START = false;
    }
    
    public static EventExecutor get() {
        return new Eqitemskill();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            Eqitemskill.START = true;
        }
        catch (Exception e) {
            Eqitemskill._log.error(e.getLocalizedMessage(), e);
        }
    }
}
