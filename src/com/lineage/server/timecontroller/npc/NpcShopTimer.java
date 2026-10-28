package com.lineage.server.timecontroller.npc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.world.WorldDe;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class NpcShopTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(NpcShopTimer.class);
    }
    
    public void start() {
        int timeMillis = 8000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1DeInstance> allDe = WorldDe.get().all();
            if (allDe.isEmpty()) {
                return;
            }
            else {
                for (L1DeInstance de : allDe) {
                    if ((!de.destroyed() || !de.isDead()) && de.getCurrentHp() > 0) {
                        if (de.isShop()) {
                            de.shopChat();
                        }
                        if (de.get_chat() != null) {
                            de.globalChat();
                        }
                        Thread.sleep(50L);
                    }
                }
            }
        }
        catch (Exception e) {
            NpcShopTimer._log.error("Npc 虛擬商店時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            NpcShopTimer shopTimer = new NpcShopTimer();
            shopTimer.start();
        }
    }
}
