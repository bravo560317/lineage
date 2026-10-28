package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import java.util.Map;
import com.lineage.data.executor.EventExecutor;

public class NetBarSet extends EventExecutor
{
    public static final Map<String, Integer> EXIPLIST;
    private static final Log _log;
    
    static {
        EXIPLIST = new HashMap();
        _log = LogFactory.getLog(NetBarSet.class);
    }
    
    public static EventExecutor get() {
        return new NetBarSet();
    }
    
    @Override
    public void execute(L1Event event) {
        String[] set = event.get_eventother().split(",");
        String[] set2 = event.get_eventother2().split("#");
        if (set.length == set2.length) {
            for (int i = 0; i < set.length; ++i) {
                String ipstring = set[i];
                NetBarSet.EXIPLIST.put(ipstring, Integer.valueOf(set2[i]));
            }
        }
        else {
            NetBarSet._log.warn("網咖IP登入設定錯誤");
        }
    }
}
