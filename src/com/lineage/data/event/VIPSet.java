package com.lineage.data.event;

import com.lineage.server.timecontroller.event.VIPTimer;
import com.lineage.server.datatables.lock.VIPReading;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class VIPSet extends EventExecutor
{
    private static final Log _log;
    public static int ADENA;
    public static int DATETIME;
    public static int ITEMID;
    
    static {
        _log = LogFactory.getLog(VIPSet.class);
    }
    
    public static EventExecutor get() {
        return new VIPSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            String[] set = event.get_eventother().split(",");
            VIPSet.ADENA = Integer.parseInt(set[0]);
            VIPSet.DATETIME = Integer.parseInt(set[1]);
            VIPSet.ITEMID = Integer.parseInt(set[2]);
            VIPReading.get().load();
            VIPTimer exp11Timer = new VIPTimer();
            exp11Timer.start();
        }
        catch (Exception e) {
            VIPSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
