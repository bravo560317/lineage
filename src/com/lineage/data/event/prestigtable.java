package com.lineage.data.event;

import com.lineage.server.datatables.RewardPrestigeTable;
import com.lineage.server.datatables.NpcPrestigeTable;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class prestigtable extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(prestigtable.class);
        prestigtable.START = false;
    }
    
    public static EventExecutor get() {
        return new prestigtable();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            prestigtable.START = true;
            NpcPrestigeTable.get().load();
            RewardPrestigeTable.get();
        }
        catch (Exception e) {
            prestigtable._log.error(e.getLocalizedMessage(), e);
        }
    }
}
