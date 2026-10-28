package com.lineage.server.command.executor;

import com.lineage.server.datatables.DropTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1ReloadDrop implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1ReloadDrop.class);
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1ReloadDrop();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        if (pc == null) {
            L1ReloadDrop._log.warn("系統命令執行: " + cmdName + "重新載入掉寶資料數量。");
        }
        else {
            pc.sendPackets(new S_SystemMessage("\\aH重新載入掉寶資料數量"));
        }
        DropTable.get().load();
    }
}
