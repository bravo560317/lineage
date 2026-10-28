package com.lineage.data.event;

import com.lineage.server.timecontroller.quest.ChapterQuestTimer2;
import com.lineage.server.timecontroller.quest.QuestTimer;
import com.lineage.server.datatables.QuesttSpawnTable;
import com.lineage.server.datatables.QuestTable;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class QuestSet extends EventExecutor
{
    private static final Log _log;
    public static boolean ISQUEST;
    
    static {
        _log = LogFactory.getLog(QuestSet.class);
        QuestSet.ISQUEST = false;
    }
    
    public static EventExecutor get() {
        return new QuestSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            QuestSet.ISQUEST = true;
            QuestTable.get().load();
            if (QuestTable.get().size() > 0) {
                QuesttSpawnTable.get().load();
            }
            QuestTimer questTimer = new QuestTimer();
            questTimer.start();
            ChapterQuestTimer2 questTimer2 = new ChapterQuestTimer2();
            questTimer2.start();
        }
        catch (Exception e) {
            QuestSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
