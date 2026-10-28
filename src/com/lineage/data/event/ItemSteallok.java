package com.lineage.data.event;

import com.lineage.server.datatables.Taketreasure1;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class ItemSteallok extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(ItemSteallok.class);
        ItemSteallok.START = false;
    }
    
    public static EventExecutor get() {
        return new ItemSteallok();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            ItemSteallok.START = true;
            Taketreasure1.getInstance().load();
        }
        catch (Exception e) {
            ItemSteallok._log.error(e.getLocalizedMessage(), e);
        }
    }
}
