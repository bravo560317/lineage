package com.lineage.data.event;

import com.lineage.server.timecontroller.event.LeavesTime;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class LeavesSet extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    public static int TIME;
    public static int EXP;
    public static int MAXEXP;
    
    static {
        _log = LogFactory.getLog(LeavesSet.class);
        LeavesSet.START = false;
        LeavesSet.TIME = 0;
        LeavesSet.EXP = 0;
        LeavesSet.MAXEXP = 400000;
    }
    
    private LeavesSet() {
    }
    
    public static EventExecutor get() {
        return new LeavesSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            LeavesSet.START = true;
            String[] set = event.get_eventother().split(",");
            try {
                LeavesSet.TIME = Integer.parseInt(set[0]);
            }
            catch (Exception e) {
                LeavesSet.TIME = 15;
                LeavesSet._log.error("未設定時間(使用預設15分鐘)");
            }
            try {
                LeavesSet.EXP = Integer.parseInt(set[1]);
            }
            catch (Exception e) {
                LeavesSet.EXP = 4000;
                LeavesSet._log.error("未設定增加的經驗質(使用預設4000)");
            }
            LeavesSet.MAXEXP = LeavesSet.EXP * 200;
            LeavesTime leavesTime = new LeavesTime();
            leavesTime.start();
        }
        catch (Exception e2) {
            LeavesSet._log.error(e2.getLocalizedMessage(), e2);
        }
    }
}
