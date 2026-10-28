package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class FeatureItemSet extends EventExecutor
{
    private static final Log _log;
    public static boolean POWER_START;
    
    static {
        _log = LogFactory.getLog(FeatureItemSet.class);
        FeatureItemSet.POWER_START = false;
    }
    
    public static EventExecutor get() {
        return new FeatureItemSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            String[] set = event.get_eventother().split(",");
            FeatureItemSet.POWER_START = Boolean.parseBoolean(set[0]);
        }
        catch (Exception e) {
            FeatureItemSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
