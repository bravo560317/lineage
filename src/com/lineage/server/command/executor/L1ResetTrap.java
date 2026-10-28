package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.WorldTrap;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1ResetTrap implements L1CommandExecutor
{
    private L1ResetTrap() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1ResetTrap();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        WorldTrap.get().resetAllTraps();
        pc.sendPackets(new S_SystemMessage("\\aH重新配置陷阱!"));
    }
}
