package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class weaponlvdmg extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(weaponlvdmg.class);
        weaponlvdmg.START = false;
    }
    
    public static EventExecutor get() {
        return new weaponlvdmg();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            weaponlvdmg.START = true;
        }
        catch (Exception e) {
            weaponlvdmg._log.error(e.getLocalizedMessage(), e);
        }
    }
}
