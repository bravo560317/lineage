package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_PacketBoxCooking;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1CookingIcon implements L1CommandExecutor
{
    public static L1CommandExecutor getInstance() {
        return new L1CookingIcon();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int iconid = 0;
            int number = 10;
            if (st.hasMoreTokens()) {
                iconid = Integer.parseInt(st.nextToken());
            }
            if (st.hasMoreTokens()) {
                number = Integer.parseInt(st.nextToken());
            }
            pc.sendPackets(new S_PacketBoxCooking(pc, iconid, number));
        }
        catch (Exception e) {
            pc.sendPackets(new S_SystemMessage(cmdName + " skillicon 請輸入 id 編碼。"));
        }
    }
}
