package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_PacketBoxTest;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1PacketIcon implements L1CommandExecutor
{
    public static L1CommandExecutor getInstance() {
        return new L1PacketIcon();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int type = 0;
            int time = 10;
            if (st.hasMoreTokens()) {
                type = Integer.parseInt(st.nextToken());
            }
            if (st.hasMoreTokens()) {
                time = Integer.parseInt(st.nextToken());
            }
            pc.sendPackets(new S_PacketBoxTest(type, time));
        }
        catch (Exception e) {
            pc.sendPackets(new S_SystemMessage(cmdName + " PacketIcon 請輸入 id 編碼。"));
        }
    }
}
