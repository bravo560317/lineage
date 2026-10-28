package com.lineage.data.event;

import com.lineage.server.timecontroller.event.ranking.RankingClanTimer;
import com.lineage.server.timecontroller.event.ranking.RankingHeroTimer;
import com.lineage.server.timecontroller.event.ranking.RankingHeroTimerlv;
import com.lineage.config.ConfigOther;
import com.lineage.server.timecontroller.event.ranking.RankingWealthTimer;
import com.lineage.server.timecontroller.event.ranking.RankingKillTimer1;
import com.lineage.server.timecontroller.event.ranking.RankingKillTimer;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class Ranking4 extends EventExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Ranking4.class);
    }
    
    private Ranking4() {
    }
    
    public static EventExecutor get() {
        return new Ranking4();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            RankingKillTimer killTimer = new RankingKillTimer();
            killTimer.start();
            RankingKillTimer1 killTimer2 = new RankingKillTimer1();
            killTimer2.start();
            Thread.sleep(500L);
            RankingWealthTimer wealthTimer = new RankingWealthTimer();
            wealthTimer.start();
            Thread.sleep(500L);
            if (ConfigOther.RankLevel) {
                RankingHeroTimerlv heroTimer = new RankingHeroTimerlv();
                heroTimer.start();
            }
            else {
                RankingHeroTimer heroTimer2 = new RankingHeroTimer();
                heroTimer2.start();
            }
            Thread.sleep(500L);
            RankingClanTimer clanRTimer = new RankingClanTimer();
            clanRTimer.start();
        }
        catch (Exception e) {
            Ranking4._log.error(e.getLocalizedMessage(), e);
        }
    }
}
