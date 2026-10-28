package com.lineage.server.command.executor;

import com.lineage.server.EchoServerTimer;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Echo implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Echo.class);
    }
    
    private L1Echo() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Echo();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            if (arg.equalsIgnoreCase("stop")) {
                if (pc == null) {
                    L1Echo._log.warn("系統命令執行: 全部端口關閉監聽!");
                }
                else {
                    pc.sendPackets(new S_ServerMessage(166, "全部端口關閉監聽!"));
                }
                EchoServerTimer.get().stopEcho();
                return;
            }
            else {
                String info = "重新啟動服務器端口監聽!";
                L1Echo._log.info(info);
                EchoServerTimer.get().reStart();
                if (pc != null) {
                    pc.sendPackets(new S_ServerMessage(166, info));
                }
            }
        }
        catch (Exception e) {
            if (pc == null) {
                L1Echo._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1Echo._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
        finally {
            L1Echo._log.info("監聽端口設置作業完成!!");
        }
    }
}
