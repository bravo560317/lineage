package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;

public class Eavesdropping implements L1CommandExecutor
{
    public static L1CommandExecutor getInstance() {
        return new Eavesdropping();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        if (pc.getcheckgm()) {
            pc.setcheckgm(false);
            pc.sendPackets(new S_SystemMessage("GM監聽:關閉"));
            return;
        }
        else if (!pc.getcheckgm()) {
            pc.setcheckgm(true);
            pc.sendPackets(new S_SystemMessage("GM監聽:開啟"));
            return;
        }
        else {
            return;
        }
    }
}
