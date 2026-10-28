package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1MoveXY implements L1CommandExecutor
{
    private static final Log _log;
    public static boolean SHUTDOWN1;
    
    static {
        _log = LogFactory.getLog(L1MoveXY.class);
        L1MoveXY.SHUTDOWN1 = false;
    }
    
    private L1MoveXY() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1MoveXY();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            if (!L1MoveXY.SHUTDOWN1) {
                L1MoveXY.SHUTDOWN1 = true;
                pc.sendPackets(new S_ServerMessage("開啟玩家採點記憶座標"));
            }
            else if (L1MoveXY.SHUTDOWN1) {
                L1MoveXY.SHUTDOWN1 = false;
                pc.sendPackets(new S_ServerMessage("關閉玩家採點記憶座標"));
            }
        }
        catch (Exception e) {
            L1MoveXY._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
