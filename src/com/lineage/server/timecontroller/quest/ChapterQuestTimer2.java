package com.lineage.server.timecontroller.quest;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class ChapterQuestTimer2 extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(ChapterQuestTimer2.class);
    }
    
    public void start() {
        int timeMillis = 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1QuestUser> allQuest = WorldQuest.get().all();
            if (allQuest.isEmpty()) {
                return;
            }
            else {
                for (L1QuestUser quest : allQuest) {
                    if (quest.get_orimR() != null) {
                        if (quest.get_orimR().party == null) {
                            continue;
                        }
                        L1PcInstance leader = quest.get_orimR().party.getLeader();
                        if (leader == null) continue;
                        if (leader.getX() == 32799 && leader.getY() == 32808) {
                            if (leader.get_actionId() == 66) {
                                quest.get_orimR().attack();
                                leader.set_actionId(0);
                            }
                            else if (leader.get_actionId() == 69) {
                                quest.get_orimR().defense();
                                leader.set_actionId(0);
                            }
                        }
                        else if (leader.get_actionId() == 66 || leader.get_actionId() == 69) {
                            leader.set_actionId(0);
                        }
                        if (quest.get_orimR().portal != null) {
                            for (L1PcInstance member : quest.pcList()) {
                                quest.get_orimR().teleport(member, quest.get_orimR().getCabinLocation());
                            }
                        }
                        quest.get_orimR().calcScore();
                        Thread.sleep(1L);
                    }
                }
            }
        }
        catch (Exception e) {
            ChapterQuestTimer2._log.error("副本任務檢查時間軸<海戰副本>異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            ChapterQuestTimer2 questTimer = new ChapterQuestTimer2();
            questTimer.start();
        }
    }
}
