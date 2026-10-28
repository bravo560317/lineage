package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_PacketBox;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1SkillIcon2 implements L1CommandExecutor
{
    public static L1CommandExecutor getInstance() {
        return new L1SkillIcon2();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int type = 0;
            if (st.hasMoreTokens()) {
                type = Integer.parseInt(st.nextToken());
            }
            for (int i = 0; i < 1000; ++i) {
                pc.sendPackets(new S_PacketBox(147, 1, type + i));
            }
        }
        catch (Exception e) {
            pc.sendPackets(new S_SystemMessage(cmdName + " Skillicon 請輸入 id 編碼。"));
        }
    }
}
