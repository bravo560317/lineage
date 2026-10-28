package com.lineage.server.timecontroller.event;

import com.lineage.server.serverpackets.S_SkillSound;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class ClanShowTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(ClanShowTimer.class);
    }
    
    public void start() {
        int timeMillis = 15000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 15000L, 15000L);
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
                        if (ServerWarExecutor.get().checkCastleWar() <= 0 && checkC(tgpc)) {
                            showClan(tgpc);
                        }
                        Thread.sleep(10L);
                    }
                }
            }
        }
        catch (Exception e) {
            ClanShowTimer._log.error("血盟技能光環 顯示處理時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            ClanShowTimer clanShowTimer = new ClanShowTimer();
            clanShowTimer.start();
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
            else if (tgpc.isTeleport()) {
                return false;
            }
            else if (tgpc.isDead()) {
                return false;
            }
            else if (tgpc.getCurrentHp() <= 0) {
                return false;
            }
            else {
                switch (tgpc.getMapId()) {
                    case 340:
                    case 350:
                    case 360:
                    case 370:
                    case 800: {
                        return false;
                    }
                }
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    private static boolean checkC(L1PcInstance tgpc) {
        try {
            if (tgpc.getClan() == null) {
                return false;
            }
            else if (!tgpc.getClan().isClanskill()) {
                return false;
            }
            else {
                int count = tgpc.getClan().getOnlineClanMemberSize();
                if (count < 16) {
                    return false;
                }
                else if (tgpc.get_other().get_clanskill() == 0) {
                    return false;
                }
            }
        }
        catch (Exception e) {
            ClanShowTimer._log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }
    
    private static void showClan(L1PcInstance tgpc) {
        try {
            int count = tgpc.getClan().getOnlineClanMemberSize();
            if (count >= 30) {
                tgpc.sendPacketsX8(new S_SkillSound(tgpc.getId(), 5201));
            }
            else {
                tgpc.sendPacketsX8(new S_SkillSound(tgpc.getId(), 5263));
            }
        }
        catch (Exception e) {
            ClanShowTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
}
