package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.Shutdown;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Shutdown implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Shutdown.class);
    }
    
    private L1Shutdown() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Shutdown();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            if (pc == null) {
                L1Shutdown._log.warn("系統命令執行: " + cmdName + " " + arg + " 秒後關閉伺服器。");
            }
            if (arg.equalsIgnoreCase("now")) {
                Shutdown.getInstance().startShutdown(pc, 5, true);
                return;
            }
            else if (arg.equalsIgnoreCase("abort")) {
                Shutdown.getInstance().abort(pc);
                return;
            }
            else {
                int sec = Math.max(5, Integer.parseInt(arg));
                Shutdown.getInstance().startShutdown(pc, sec, true);
            }
        }
        catch (Exception e) {
            if (pc == null) {
                L1Shutdown._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1Shutdown._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
}
