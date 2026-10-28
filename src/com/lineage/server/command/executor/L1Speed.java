package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Speed implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Speed.class);
    }
    
    private L1Speed() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Speed();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            L1BuffUtil.haste(pc, 3600000);
            L1BuffUtil.brave(pc, 3600000);
        }
        catch (Exception e) {
            L1Speed._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
