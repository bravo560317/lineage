package com.lineage.data.event;

import com.lineage.william.ClanOriginal;
import com.lineage.server.datatables.NpcClanContribution;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class ClanContribution extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(ClanContribution.class);
        ClanContribution.START = false;
    }
    
    public static EventExecutor get() {
        return new ClanContribution();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            ClanContribution.START = true;
            NpcClanContribution.get().load();
            ClanOriginal.getInstance();
        }
        catch (Exception e) {
            ClanContribution._log.error(e.getLocalizedMessage(), e);
        }
    }
}
