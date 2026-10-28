package com.lineage.server.timecontroller.event;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.config.ConfigDescs;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class NewServerTime extends TimerTask
{
    private static final Log _log;
    private int _count;
    private int _time;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(NewServerTime.class);
    }
    
    public NewServerTime() {
        this._count = 1;
        this._time = 0;
    }
    
    public void start(int time) {
        this._time = time;
        int timeMillis = time * 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            World.get().broadcastPacketToAll(new S_SystemMessage(ConfigDescs.getShow(this._count)));
            ++this._count;
            if (this._count >= ConfigDescs.get_show_size()) {
                this._count = 1;
            }
        }
        catch (Exception e) {
            NewServerTime._log.error("服務器介紹與教學時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            NewServerTime timerTask = new NewServerTime();
            timerTask.start(this._time);
        }
    }
}
