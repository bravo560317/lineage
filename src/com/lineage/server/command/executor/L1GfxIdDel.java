package com.lineage.server.command.executor;

import java.util.Iterator;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1GfxIdDel implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1GfxIdDel.class);
    }
    
    private L1GfxIdDel() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1GfxIdDel();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            for (L1Object object : World.get().getObject()) {
                if (object instanceof L1NpcInstance) {
                    L1NpcInstance npc = (L1NpcInstance)object;
                    if (npc.getNpcId() == 50000) {
                        npc.deleteMe();
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        catch (Exception e) {
            L1GfxIdDel._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
