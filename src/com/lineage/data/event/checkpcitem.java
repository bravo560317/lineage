package com.lineage.data.event;

import com.lineage.server.datatables.CheckItemPowerTable;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class checkpcitem extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(checkpcitem.class);
        checkpcitem.START = false;
    }
    
    public static EventExecutor get() {
        return new checkpcitem();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            checkpcitem.START = true;
            CheckItemPowerTable.get();
        }
        catch (Exception e) {
            checkpcitem._log.error(e.getLocalizedMessage(), e);
        }
    }
}
