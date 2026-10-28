package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1GfxIdInPc implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1GfxIdInPc.class);
    }
    
    private L1GfxIdInPc() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1GfxIdInPc();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int gfxid = 0;
            String next = st.nextToken();
            try {
                gfxid = Integer.parseInt(next, 10);
            }
            catch (Exception e) {
                pc.sendPackets(new S_ServerMessage(261));
                return;
            }
            pc.sendPacketsX8(new S_SkillSound(pc.getId(), gfxid));
        }
        catch (Exception e2) {
            L1GfxIdInPc._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
