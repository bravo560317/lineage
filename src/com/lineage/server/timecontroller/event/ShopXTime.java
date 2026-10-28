package com.lineage.server.timecontroller.event;

import java.util.Iterator;
import java.util.HashMap;
import com.lineage.server.templates.L1ShopS;
import com.lineage.server.datatables.lock.DwarfShopReading;
import java.sql.Timestamp;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class ShopXTime extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(ShopXTime.class);
    }
    
    public void start() {
        int timeMillis = 3600000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 3600000L, 3600000L);
    }
    
    @Override
    public void run() {
        try {
            Timestamp overTime = new Timestamp(System.currentTimeMillis());
            HashMap<Integer, L1ShopS> allShopS = DwarfShopReading.get().allShopS();
            for (L1ShopS shopS : allShopS.values()) {
                if (shopS.get_end() == 0 && overTime.after(shopS.get_overtime())) {
                    shopS.set_end(3);
                    shopS.set_item(null);
                    DwarfShopReading.get().updateShopS(shopS);
                }
                Thread.sleep(1L);
            }
            allShopS.clear();
        }
        catch (Exception e) {
            ShopXTime._log.error("託售物件時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            ShopXTime timerTask = new ShopXTime();
            timerTask.start();
        }
    }
}
