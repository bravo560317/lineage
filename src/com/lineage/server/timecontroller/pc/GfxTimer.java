package com.lineage.server.timecontroller.pc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.model.L1Trade;
import com.lineage.server.Shutdown;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.PcOtherThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class GfxTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(GfxTimer.class);
    }
    
    public void start() {
        int timeMillis = 60000;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 60000L, 60000L);
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
                    if (ConfigOther.newcharpra && tgpc.getnewcharpra() && tgpc.getLevel() >= ConfigOther.newcharpralv) {
                        tgpc.setnewcharpra(false);
                        tgpc.sendPackets(new S_ServerMessage("您的等級已達新手保護機制。"));
                    }
                    if (Shutdown.isSHUTDOWN) {
                        L1Trade trade = new L1Trade();
                        trade.tradeCancel(tgpc);
                        tgpc.sendPackets(new S_CloseList(tgpc.getId()));
                    }
                }
            }
        }
        catch (Exception localException) {
            GfxTimer._log.error("裝備特效處理時間軸異常重啟", localException);
            PcOtherThreadPool.get().cancel(this._timer, false);
            Object localObject = new GfxTimer();
            ((GfxTimer)localObject).start();
        }
    }
}
