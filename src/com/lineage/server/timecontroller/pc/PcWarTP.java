package com.lineage.server.timecontroller.pc;

import com.lineage.server.model.L1Teleport;
import com.lineage.server.thread.PcOtherThreadPool;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class PcWarTP extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private L1PcInstance _pc;
    private int _x;
    private int _y;
    private int _mapid;
    
    static {
        _log = LogFactory.getLog(PcWarTP.class);
    }
    
    public void start(L1PcInstance pc, int x, int y, int mapid) {
        this._pc = pc;
        this._x = x;
        this._y = y;
        this._mapid = mapid;
        this._timer = PcOtherThreadPool.get().schedule(this, 1L);
    }
    
    @Override
    public void run() {
        try {
            L1Teleport.teleport(this._pc, this._x, this._y, (short)this._mapid, 5, true);
        }
        catch (Exception e) {
            PcWarTP._log.error("攻城用傳送執行緒異常", e);
        }
    }
}
