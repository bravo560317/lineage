package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_DoActionGFX;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1Action implements L1CommandExecutor
{
    public static L1CommandExecutor getInstance() {
        return new L1Action();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int actId = Integer.parseInt(st.nextToken(), 10);
            pc.sendPackets(new S_DoActionGFX(pc.getId(), actId));
        }
        catch (Exception exception) {
            pc.sendPackets(new S_SystemMessage("請輸入 " + cmdName + " actid。"));
        }
    }
}
