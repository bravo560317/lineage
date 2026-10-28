package com.lineage.data.event;

import com.lineage.server.datatables.Taketreasure;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class taketreasure extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(taketreasure.class);
        taketreasure.START = false;
    }
    
    public static EventExecutor get() {
        return new taketreasure();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            taketreasure.START = true;
            Taketreasure.getInstance().load();
        }
        catch (Exception e) {
            taketreasure._log.error(e.getLocalizedMessage(), e);
        }
    }
}
