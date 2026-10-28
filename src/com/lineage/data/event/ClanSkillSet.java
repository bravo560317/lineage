package com.lineage.data.event;

import com.lineage.server.timecontroller.event.ClanShowTimer;
import com.lineage.server.timecontroller.event.ClanSkillTimer;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class ClanSkillSet extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    
    static {
        _log = LogFactory.getLog(ClanSkillSet.class);
        ClanSkillSet.START = false;
    }
    
    public static EventExecutor get() {
        return new ClanSkillSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            if (ClanSkillDBSet.START) {
                ClanSkillSet._log.info("警告!活動設置:111項血盟技能已啟動狀態下無法同時啟動伊薇版血盟技能");
            }
            else {
                ClanSkillSet.START = true;
                ClanSkillTimer useTimer = new ClanSkillTimer();
                useTimer.start();
            }
            ClanShowTimer clanShowTimer = new ClanShowTimer();
            clanShowTimer.start();
        }
        catch (Exception e) {
            ClanSkillSet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
