package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_PacketBoxIconAura;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1SkillIcon5 implements L1CommandExecutor
{
    public static L1CommandExecutor getInstance() {
        return new L1SkillIcon5();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int type = 0;
            if (st.hasMoreTokens()) {
                type = Integer.parseInt(st.nextToken());
            }
            switch (type) {
                case 1: {
                    for (int i = 0; i < 1000; ++i) {
                        pc.sendPackets(new S_PacketBoxIconAura(0 + i, 10, 10, 10));
                    }
                    break;
                }
                case 2: {
                    for (int i = 0; i < 1000; ++i) {
                        pc.sendPackets(new S_PacketBoxIconAura(10, 0 + i, 10, 10));
                    }
                    break;
                }
                case 3: {
                    for (int i = 0; i < 1000; ++i) {
                        pc.sendPackets(new S_PacketBoxIconAura(10, 10, 0 + i, 10));
                    }
                    break;
                }
                case 4: {
                    for (int i = 0; i < 1000; ++i) {
                        pc.sendPackets(new S_PacketBoxIconAura(10, 10, 10, 0 + i));
                    }
                    break;
                }
            }
        }
        catch (Exception e) {
            pc.sendPackets(new S_SystemMessage(cmdName + " Skillicon 請輸入 id 編碼。"));
        }
    }
}
