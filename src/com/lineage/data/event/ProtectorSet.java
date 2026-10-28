package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class ProtectorSet extends EventExecutor
{
    private static final Log _log;
    public static int ITEM_ID;
    public static int CHANCE;
    public static int DROP_LIMIT;
    public static int HP_UP;
    public static int MP_UP;
    public static int DMG_UP;
    public static int DMG_DOWN;
    public static int SP_UP;
    public static boolean DEATH_VALUE_EXP;
    public static boolean DEATH_VALUE_ITEM;
    
    static {
        _log = LogFactory.getLog(ProtectorSet.class);
    }
    
    public static EventExecutor get() {
        return new ProtectorSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            String[] set = event.get_eventother().split(",");
            ProtectorSet.ITEM_ID = Integer.parseInt(set[0]);
            ProtectorSet.CHANCE = Integer.parseInt(set[1]);
            ProtectorSet.DROP_LIMIT = Integer.parseInt(set[2]);
            ProtectorSet.HP_UP = Integer.parseInt(set[3]);
            ProtectorSet.MP_UP = Integer.parseInt(set[4]);
            ProtectorSet.DMG_UP = Integer.parseInt(set[5]);
            ProtectorSet.DMG_DOWN = Integer.parseInt(set[6]);
            ProtectorSet.SP_UP = Integer.parseInt(set[7]);
            ProtectorSet.DEATH_VALUE_EXP = Boolean.parseBoolean(set[8]);
            ProtectorSet.DEATH_VALUE_ITEM = Boolean.parseBoolean(set[9]);
        }
        catch (Exception e) {
            ProtectorSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
