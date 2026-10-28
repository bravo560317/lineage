package com.lineage.data.event;

import com.lineage.server.datatables.ServerCrockTable;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class servercrock extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(servercrock.class);
        servercrock.START = false;
    }
    
    public static EventExecutor get() {
        return new servercrock();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            servercrock.START = true;
            ServerCrockTable.get();
        }
        catch (Exception e) {
            servercrock._log.error(e.getLocalizedMessage(), e);
        }
    }
}
