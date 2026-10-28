package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigAlt;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1AtkMsg implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1AtkMsg.class);
    }
    
    private L1AtkMsg() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1AtkMsg();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            if (ConfigAlt.ALT_ATKMSG) {
                ConfigAlt.ALT_ATKMSG = false;
            }
            else {
                ConfigAlt.ALT_ATKMSG = true;
            }
        }
        catch (Exception e) {
            L1AtkMsg._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
