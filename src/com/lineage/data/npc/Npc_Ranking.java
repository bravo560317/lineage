package com.lineage.data.npc;

import java.sql.SQLException;
import com.add.L1Ranking;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Ranking extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_Ranking();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_h_3"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            String[] userName = null;
            if (cmd.equalsIgnoreCase("All")) {
                userName = L1Ranking.All();
            }
            if (cmd.equalsIgnoreCase("Crown")) {
                userName = L1Ranking.Crown();
            }
            if (cmd.equalsIgnoreCase("Knight")) {
                userName = L1Ranking.Knight();
            }
            if (cmd.equalsIgnoreCase("Elf")) {
                userName = L1Ranking.Elf();
            }
            if (cmd.equalsIgnoreCase("Wizard")) {
                userName = L1Ranking.Wizard();
            }
            if (cmd.equalsIgnoreCase("Darkelf")) {
                userName = L1Ranking.Darkelf();
            }
            if (cmd.equalsIgnoreCase("Dragon")) {
                userName = L1Ranking.Dragon();
            }
            if (cmd.equalsIgnoreCase("Illusionist")) {
                userName = L1Ranking.Illusionist();
            }
            if (userName != null) {
                String htmlid = "y_h_4";
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), htmlid, userName));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
