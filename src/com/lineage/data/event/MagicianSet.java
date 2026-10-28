package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class MagicianSet extends EventExecutor
{
    private static final Log _log;
    public static int ITEM_ID;
    public static int ITEM_COUNT;
    
    static {
        _log = LogFactory.getLog(MagicianSet.class);
    }
    
    public static EventExecutor get() {
        return new MagicianSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            String[] set = event.get_eventother().split(",");
            MagicianSet.ITEM_ID = Integer.parseInt(set[0]);
            MagicianSet.ITEM_COUNT = Integer.parseInt(set[1]);
        }
        catch (Exception e) {
            MagicianSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
