package com.lineage.server.command.executor;

import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1BanUser implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1BanUser.class);
    }
    
    private L1BanUser() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1BanUser();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
    }
}
