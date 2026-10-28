package com.lineage.data.event;

import com.lineage.server.datatables.CastleWarGiftTable;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class CastleOriginal_item extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(CastleOriginal_item.class);
        CastleOriginal_item.START = false;
    }
    
    public static EventExecutor get() {
        return new CastleOriginal_item();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            CastleOriginal_item.START = true;
            CastleWarGiftTable.get().load();
        }
        catch (Exception e) {
            CastleOriginal_item._log.error(e.getLocalizedMessage(), e);
        }
    }
}
