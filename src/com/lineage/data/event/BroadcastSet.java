package com.lineage.data.event;

import com.lineage.server.BroadcastController;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class BroadcastSet extends EventExecutor
{
    private static final Log _log;
    public static int ITEM_ID;
    
    static {
        _log = LogFactory.getLog(PowerItemSet.class);
    }
    
    private BroadcastSet() {
    }
    
    public static EventExecutor get() {
        return new BroadcastSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            String[] set = event.get_eventother().split(",");
            BroadcastSet.ITEM_ID = Integer.parseInt(set[0]);
            BroadcastController.getInstance().start();
        }
        catch (Exception e) {
            BroadcastSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
