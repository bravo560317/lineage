package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class RandomGiftSet extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(RandomGiftSet.class);
        RandomGiftSet.START = false;
    }
    
    public static EventExecutor get() {
        return new RandomGiftSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            RandomGiftSet.START = true;
        }
        catch (Exception e) {
            RandomGiftSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
