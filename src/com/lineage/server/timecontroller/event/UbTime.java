package com.lineage.server.timecontroller.event;

import java.util.Iterator;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.L1UltimateBattle;
import com.lineage.server.datatables.UBTable;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class UbTime extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(UbTime.class);
    }
    
    public void start() {
        int timeMillis = 16500;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 16500L, 16500L);
    }
    
    @Override
    public void run() {
        try {
            checkUbTime();
        }
        catch (Exception e) {
            UbTime._log.error("無線大賽時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            UbTime timer = new UbTime();
            timer.start();
        }
    }
    
    private static void checkUbTime() {
        for (L1UltimateBattle ub : UBTable.getInstance().getAllUb()) {
            if (ub.checkUbTime() && !ub.isActive()) {
                ub.start();
                World.get().broadcastPacketToAll(new S_SystemMessage("【" + ub.getName() + "】５分鐘後比賽將開始，想參賽者請趕快入場。"));
            }
        }
    }
}
