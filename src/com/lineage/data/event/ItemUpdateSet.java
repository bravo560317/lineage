package com.lineage.data.event;

import com.lineage.server.datatables.ItemUpdateTable;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class ItemUpdateSet extends EventExecutor
{
    private static final Log _log;
    public static boolean MODE;
    
    static {
        _log = LogFactory.getLog(ItemUpdateSet.class);
        ItemUpdateSet.MODE = false;
    }
    
    public static EventExecutor get() {
        return new ItemUpdateSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            String[] set = event.get_eventother().split(",");
            ItemUpdateSet.MODE = Boolean.parseBoolean(set[0]);
            ItemUpdateTable.get().load();
        }
        catch (Exception e) {
            ItemUpdateSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
