package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.timecontroller.server.ServerHomeTownTime;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1HomeTown implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1HomeTown.class);
    }
    
    private L1HomeTown() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1HomeTown();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            if (pc == null) {
                L1HomeTown._log.warn("系統命令執行: " + cmdName + " " + arg + " 啟用貢獻度系統。");
            }
            StringTokenizer st = new StringTokenizer(arg);
            String para1 = st.nextToken();
            if (para1.equalsIgnoreCase("daily")) {
                ServerHomeTownTime.getInstance().dailyProc();
            }
            else if (para1.equalsIgnoreCase("monthly")) {
                ServerHomeTownTime.getInstance().monthlyProc();
            }
            else {
                throw new Exception();
            }
        }
        catch (Exception e) {
            if (pc == null) {
                L1HomeTown._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1HomeTown._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
}
