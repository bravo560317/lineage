package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class Npc_Dead extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(Npc_Dead.class);
        Npc_Dead.START = false;
    }
    
    public static EventExecutor get() {
        return new Npc_Dead();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            Npc_Dead.START = true;
        }
        catch (Exception e) {
            Npc_Dead._log.error(e.getLocalizedMessage(), e);
        }
    }
}
