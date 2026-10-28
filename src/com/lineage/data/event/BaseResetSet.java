package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class BaseResetSet extends EventExecutor
{
    private static final Log _log;
    public static int RETAIN;
    
    static {
        _log = LogFactory.getLog(BaseResetSet.class);
        BaseResetSet.RETAIN = 0;
    }
    
    public static EventExecutor get() {
        return new BaseResetSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            String[] set = event.get_eventother().split(",");
            try {
                BaseResetSet.RETAIN = Integer.parseInt(set[0]);
            }
            catch (Exception ex) {}
        }
        catch (Exception e) {
            BaseResetSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
