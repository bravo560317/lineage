package com.lineage.server.timecontroller.event;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.serverpackets.S_PacketBoxExp;
import com.lineage.data.event.LeavesSet;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class LeavesTime extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(LeavesTime.class);
    }
    
    public void start() {
        int timeMillis = 60000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 60000L, 60000L);
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
                    if (check(tgpc)) {
                        int time = tgpc.getlogintime1();
                        tgpc.setlogintime1(time + 1);
                        if (tgpc.getlogintime1() >= LeavesSet.TIME) {
                            tgpc.setlogintime1(0);
                            int exp = tgpc.getlogintime();
                            int addexp = exp + LeavesSet.EXP;
                            tgpc.setlogintime(addexp);
                            if (tgpc.getlogintime() > 960000) {
                                tgpc.setlogintime(960000);
                            }
                            tgpc.sendPackets(new S_PacketBoxExp(tgpc.getlogintime() / LeavesSet.EXP));
                        }
                        Thread.sleep(100L);
                    }
                }
            }
        }
        catch (Exception e) {
            LeavesTime._log.error("殷海薩的祝福-休息系統時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            LeavesTime leavesTime = new LeavesTime();
            leavesTime.start();
        }
    }
    
    private static boolean check(L1PcInstance tgpc) {
        try {
            if (tgpc == null) {
                return false;
            }
            else if (tgpc.getOnlineStatus() == 0) {
                return false;
            }
            else if (tgpc.getNetConnection() == null) {
                return false;
            }
            else if (tgpc.isDead()) {
                return false;
            }
            else if (!tgpc.isSafetyZone()) {
                return false;
            }
            else if (tgpc.isTeleport()) {
                return false;
            }
            else if (tgpc.getlogintime() >= LeavesSet.MAXEXP) {
                return false;
            }
            else if (tgpc.getHpRegenState() == 1) {
                return false;
            }
        }
        catch (Exception e) {
            LeavesTime._log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }
}
