package com.lineage.server.timecontroller.pc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.thread.PcOtherThreadPool;
import com.lineage.server.serverpackets.S_Poison;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class BreakdownTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private int _timeMillis;
    private Random _random;
    
    static {
        _log = LogFactory.getLog(BreakdownTimer.class);
    }
    
    public void start(int timeMillis, Random random) {
        this._timeMillis = timeMillis;
        this._random = random;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1PcInstance> all = World.get().getAllPlayers();
            if (all.isEmpty()) {
                return;
            }
            else {
                for (L1PcInstance tgpc : all) {
                    if (this._random != null) {
                        int gfxid = this._random.nextInt(3);
                        tgpc.sendPackets(new S_Poison(tgpc.getId(), gfxid));
                        tgpc.broadcastPacketAll(new S_Poison(tgpc.getId(), gfxid));
                    }
                    else {
                        tgpc.sendPackets(new S_Poison(tgpc.getId(), 0));
                        tgpc.broadcastPacketAll(new S_Poison(tgpc.getId(), 0));
                    }
                }
            }
        }
        catch (Exception e) {
            BreakdownTimer._log.error("時間軸異常重啟.時間軸異常重啟.時間軸異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            BreakdownTimer breakdownTimer = new BreakdownTimer();
            breakdownTimer.start(this._timeMillis, this._random);
        }
    }
}
