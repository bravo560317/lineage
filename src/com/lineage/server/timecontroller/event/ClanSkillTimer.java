package com.lineage.server.timecontroller.event;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.datatables.lock.ClanReading;
import java.sql.Timestamp;
import com.lineage.server.model.L1Clan;
import com.lineage.server.world.WorldClan;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class ClanSkillTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(ClanSkillTimer.class);
    }
    
    public void start() {
        int timeMillis = 3600000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1Clan> allClan = WorldClan.get().getAllClans();
            if (allClan.isEmpty()) {
                return;
            }
            else {
                for (L1Clan clan : allClan) {
                    if (clan.isClanskill()) {
                        Timestamp skilltime = clan.get_skilltime();
                        if (skilltime == null) {
                            clan.set_clanskill(false);
                        }
                        else {
                            Timestamp ts = new Timestamp(System.currentTimeMillis());
                            if (skilltime.before(ts)) {
                                clan.set_clanskill(false);
                                clan.set_skilltime(null);
                                ClanReading.get().updateClan(clan);
                            }
                            Thread.sleep(1L);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            ClanSkillTimer._log.error("血盟技能計時時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            ClanSkillTimer skillTimer = new ClanSkillTimer();
            skillTimer.start();
        }
    }
}
