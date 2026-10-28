package com.lineage.server.command.executor;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Adena implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Adena.class);
    }
    
    private L1Adena() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Adena();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer stringtokenizer = new StringTokenizer(arg);
            long count = Long.parseLong(stringtokenizer.nextToken());
            L1ItemInstance adena = pc.getInventory().storeItem(40308, count);
            if (adena != null) {
                pc.sendPackets(new S_ServerMessage(403, "$4: " + count));
            }
        }
        catch (Exception e) {
            L1Adena._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
