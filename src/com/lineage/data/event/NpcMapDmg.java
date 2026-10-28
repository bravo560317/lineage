package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class NpcMapDmg extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(NpcMapDmg.class);
        NpcMapDmg.START = false;
    }
    
    public static EventExecutor get() {
        return new NpcMapDmg();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            NpcMapDmg.START = true;
        }
        catch (Exception e) {
            NpcMapDmg._log.error(e.getLocalizedMessage(), e);
        }
    }
}
