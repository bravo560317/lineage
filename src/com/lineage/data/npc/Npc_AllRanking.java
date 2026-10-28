package com.lineage.data.npc;

import com.add.L1RankingManager;
import com.lineage.data.executor.NpcExecutor;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;

/**
 * 綜合排行榜 NPC
 * 透過 allranking.htm 顯示選單，再把玩家點的選項交給 L1RankingManager 分流
 */
public class Npc_AllRanking extends NpcExecutor {

    public static NpcExecutor get() {
        return new Npc_AllRanking();
    }

    @Override
    public int type() {
        return 3; // 和其他排行榜 NPC 一致
    }

    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        // 顯示對話檔：text/allranking.htm
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "allranking"));
    }

    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        // 直接把 cmd 丟給 L1RankingManager 判斷
        L1RankingManager.showRanking(pc, npc, cmd);
    }
}
