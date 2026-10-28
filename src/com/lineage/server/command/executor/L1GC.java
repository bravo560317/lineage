package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1GC implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1GC.class);
    }
    
    private L1GC() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1GC();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            System.gc();
        }
        catch (Exception e) {
            if (pc == null) {
                L1GC._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1GC._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
}
