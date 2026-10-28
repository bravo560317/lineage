package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class DropItem extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(DropItem.class);
        DropItem.START = false;
    }
    
    public static EventExecutor get() {
        return new DropItem();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            DropItem.START = true;
        }
        catch (Exception e) {
            DropItem._log.error(e.getLocalizedMessage(), e);
        }
    }
}
