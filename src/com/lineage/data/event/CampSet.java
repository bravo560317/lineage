package com.lineage.data.event;

import com.lineage.server.datatables.lock.CharacterC1Reading;
import com.lineage.server.datatables.C1_Name_Type_Table;
import com.lineage.server.datatables.C1_Name_Table;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class CampSet extends EventExecutor
{
    private static final Log _log;
    public static boolean CAMPSTART;
    
    static {
        _log = LogFactory.getLog(CampSet.class);
        CampSet.CAMPSTART = false;
    }
    
    public static EventExecutor get() {
        return new CampSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            CampSet.CAMPSTART = true;
            C1_Name_Table.get().load();
            C1_Name_Type_Table.get().load();
            CharacterC1Reading.get().load();
        }
        catch (Exception e) {
            CampSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
