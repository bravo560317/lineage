package com.lineage.data.npc.quest2;

import java.util.List;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.templates.L1Rank;
import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.datatables.lock.BoardOrimReading;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_OrimBoard extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_OrimBoard.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_OrimBoard();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "id_s"));
        }
        catch (Exception e) {
            Npc_OrimBoard._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equalsIgnoreCase("query")) {
            List<L1Rank> totalList = BoardOrimReading.get().getTotalList();
            List<L1Rank> tempList = new CopyOnWriteArrayList();
            int totalSize = 0;
            for (int i = 0, r = 5, n = totalList.size(); i < r && i < n; ++i) {
                L1Rank rank = totalList.get(i);
                if (rank != null) {
                    tempList.add(rank);
                    totalSize += rank.getMemberSize();
                }
            }
            pc.sendPackets(new S_PacketBoxGree(tempList, totalSize, 0, 0));
        }
    }
}
