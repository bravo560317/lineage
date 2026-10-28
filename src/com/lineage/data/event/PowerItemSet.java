package com.lineage.data.event;

import com.lineage.server.datatables.lock.CharItemPowerReading;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class PowerItemSet extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    public static int HOLER;
    public static int ARMORHOLE;
    public static int WEAPONHOLE;
    
    static {
        _log = LogFactory.getLog(PowerItemSet.class);
        PowerItemSet.START = false;
        PowerItemSet.HOLER = 0;
        PowerItemSet.ARMORHOLE = 0;
        PowerItemSet.WEAPONHOLE = 0;
    }
    
    private PowerItemSet() {
    }
    
    public static EventExecutor get() {
        return new PowerItemSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            PowerItemSet.START = true;
            String[] set = event.get_eventother().split(",");
            PowerItemSet.HOLER = Integer.parseInt(set[0]);
            PowerItemSet.ARMORHOLE = Integer.parseInt(set[1]);
            if (PowerItemSet.ARMORHOLE > 5) {
                PowerItemSet.ARMORHOLE = 5;
            }
            PowerItemSet.WEAPONHOLE = Integer.parseInt(set[2]);
            if (PowerItemSet.WEAPONHOLE > 5) {
                PowerItemSet.WEAPONHOLE = 5;
            }
            CharItemPowerReading.get().load();
        }
        catch (Exception e) {
            PowerItemSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
