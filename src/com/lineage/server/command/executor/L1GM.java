package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1GM implements L1CommandExecutor
{
    private L1GM() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1GM();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        pc.setGm(!pc.isGm());
        pc.sendPackets(new S_SystemMessage("setGm = " + pc.isGm()));
    }
}
