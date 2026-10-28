package com.lineage.server.timecontroller.server;

import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class ServerResetMapTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(ServerResetMapTimer.class);
    }
    
    public void start() {
        int timeMillis = 300000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            System.out.println("檢查重置限時地監時間軸執行");
        }
        catch (Exception e) {
            ServerResetMapTimer._log.error("檢查重置限時地監時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            ServerResetMapTimer resetMapTimer = new ServerResetMapTimer();
            resetMapTimer.start();
        }
    }
}
