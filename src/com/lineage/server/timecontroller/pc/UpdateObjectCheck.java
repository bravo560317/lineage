package com.lineage.server.timecontroller.pc;

import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class UpdateObjectCheck
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(UpdateObjectCheck.class);
    }
    
    public static boolean check(L1PcInstance tgpc) {
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
        }
        catch (Exception e) {
            UpdateObjectCheck._log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }
}
