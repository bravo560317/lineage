package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_OtherCharPacks;
import com.lineage.server.serverpackets.S_Invis;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Invisible implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Invisible.class);
    }
    
    private L1Invisible() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Invisible();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            if (pc.isGmInvis()) {
                pc.setGmInvis(false);
                pc.sendPackets(new S_Invis(pc.getId(), 0));
                pc.broadcastPacketAll(new S_OtherCharPacks(pc));
                pc.sendPackets(new S_SystemMessage("取消GM隱身!"));
            }
            else {
                pc.setGmInvis(true);
                pc.sendPackets(new S_Invis(pc.getId(), 1));
                pc.broadcastPacketAll(new S_RemoveObject(pc));
                pc.sendPackets(new S_SystemMessage("啟用GM隱身!"));
            }
        }
        catch (Exception e) {
            L1Invisible._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
