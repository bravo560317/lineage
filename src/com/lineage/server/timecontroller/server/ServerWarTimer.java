package com.lineage.server.timecontroller.server;

import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class ServerWarTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(ServerWarTimer.class);
    }
    
    public void start() {
        int timeMillis = 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            ServerWarExecutor.get().checkWarTime();
        }
        catch (Exception e) {
            ServerWarTimer._log.error("城戰計時時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            ServerWarTimer warTimer = new ServerWarTimer();
            warTimer.start();
        }
    }
}
