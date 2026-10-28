package com.lineage.data.event;

import com.lineage.server.datatables.ServerQuestMobTable;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class QuestMobSet extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(QuestMobSet.class);
        QuestMobSet.START = false;
    }
    
    public static EventExecutor get() {
        return new QuestMobSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            QuestMobSet.START = true;
            ServerQuestMobTable.get();
        }
        catch (Exception e) {
            QuestMobSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
