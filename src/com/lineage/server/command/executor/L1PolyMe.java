package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1PolyMe implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1PolyMe.class);
    }
    
    private L1PolyMe() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1PolyMe();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            int polyid = Integer.parseInt(arg);
            try {
                L1PolyMorph.doPoly(pc, polyid, 7200, 2);
            }
            catch (Exception exception) {
                pc.sendPackets(new S_SystemMessage(".polyme [外型代號]"));
            }
        }
        catch (Exception e) {
            L1PolyMe._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
