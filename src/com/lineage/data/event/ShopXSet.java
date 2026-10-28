package com.lineage.data.event;

import com.lineage.server.timecontroller.event.ShopXTime;
import com.lineage.server.datatables.ShopXTable;
import com.lineage.server.datatables.lock.DwarfShopReading;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class ShopXSet extends EventExecutor
{
    private static final Log _log;
    public static int ADENA;
    public static int DATE;
    public static int MIN;
    public static int MAX;
    
    static {
        _log = LogFactory.getLog(ShopXSet.class);
    }
    
    public static EventExecutor get() {
        return new ShopXSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            String[] set = event.get_eventother().split(",");
            ShopXSet.ADENA = Integer.parseInt(set[0]);
            ShopXSet.DATE = Integer.parseInt(set[1]);
            ShopXSet.MIN = Integer.parseInt(set[2]);
            ShopXSet.MAX = Integer.parseInt(set[3]);
            DwarfShopReading.get().load();
            ShopXTable.get().load();
            ShopXTime timer = new ShopXTime();
            timer.start();
        }
        catch (Exception e) {
            ShopXSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
