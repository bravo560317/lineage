package com.lineage.server.timecontroller.event;

import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import java.util.Iterator;
import java.util.Map;
import java.sql.Timestamp;
import com.lineage.server.datatables.lock.VIPReading;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class VIPTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(VIPTimer.class);
    }
    
    public void start() {
        int timeMillis = 60000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            Map<Integer, Timestamp> map = VIPReading.get().map();
            if (map.isEmpty()) {
                return;
            }
            else {
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                for (Integer objid : map.keySet()) {
                    Timestamp time = map.get(objid);
                    if (time.before(ts)) {
                        VIPReading.get().delOther(objid);
                        checkVIP(objid);
                    }
                    Thread.sleep(5L);
                }
            }
        }
        catch (Exception e) {
            VIPTimer._log.error("VIP計時時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            VIPTimer timer = new VIPTimer();
            timer.start();
        }
    }
    
    private static void checkVIP(Integer objid) {
        try {
            L1Object target = World.get().findObject(objid);
            if (target != null) {
                boolean isOut = false;
                if (!isOut) {
                    return;
                }
                else if (target instanceof L1PcInstance) {
                    L1PcInstance tgpc = (L1PcInstance)target;
                    L1Teleport.teleport(tgpc, 33080, 33392, (short)4, 5, true);
                    tgpc.sendPackets(new S_ServerMessage("VIP時間終止"));
                }
            }
        }
        catch (Exception e) {
            VIPTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
}
