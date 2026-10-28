package com.lineage.server.timecontroller.pc;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.serverpackets.S_PacketBoxSelect;
import com.lineage.server.serverpackets.S_ChangeName;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.PcOtherThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class PcDeleteTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(PcDeleteTimer.class);
    }
    
    public void start() {
        int timeMillis = 1100;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 1100L, 1100L);
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
                    if (!tgpc.isDead()) {
                        continue;
                    }
                    else if (tgpc.get_delete_time() <= 0) {
                        continue;
                    }
                    else {
                        int newtime = tgpc.get_delete_time() - 1;
                        tgpc.set_delete_time(newtime);
                        if (tgpc.get_delete_time() <= 0) {
                            tgpc.sendPackets(new S_Disconnect());
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            PcDeleteTimer._log.error("PC 死亡刪除處理時間軸異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            PcDeleteTimer pcHprTimer = new PcDeleteTimer();
            pcHprTimer.start();
        }
    }
    
    private static void outPc(L1PcInstance tgpc) {
        try {
            if (tgpc.isDead()) {
                ClientExecutor client = tgpc.getNetConnection();
                tgpc.sendPackets(new S_ChangeName(tgpc, false));
                client.quitGame();
                PcDeleteTimer._log.info("角色死亡登出: " + tgpc.getName());
                client.out().encrypt(new S_PacketBoxSelect());
            }
        }
        catch (Exception e) {
            PcDeleteTimer._log.error("登出死亡角色時發生異常!", e);
        }
    }
}
