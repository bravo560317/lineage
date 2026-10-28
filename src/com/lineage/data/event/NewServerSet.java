package com.lineage.data.event;

import com.lineage.server.timecontroller.event.NewServerTime;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class NewServerSet extends EventExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(NewServerSet.class);
    }
    
    public static EventExecutor get() {
        return new NewServerSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            int time = Integer.parseInt(event.get_eventother());
            NewServerTime chatTime = new NewServerTime();
            chatTime.start(time);
        }
        catch (Exception e) {
            NewServerSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
