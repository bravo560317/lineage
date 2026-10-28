package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class CastleOriginal extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(CastleOriginal.class);
        CastleOriginal.START = false;
    }
    
    public static EventExecutor get() {
        return new CastleOriginal();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            CastleOriginal.START = true;
            com.lineage.william.CastleOriginal.getInstance();
        }
        catch (Exception e) {
            CastleOriginal._log.error(e.getLocalizedMessage(), e);
        }
    }
}
