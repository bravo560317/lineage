package com.add;

import java.util.Collection;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.timecontroller.event.ranking.RankingKillTimer;

public class RankingLoginAnnounce {

    /* =========================
     * 入口：登入時呼叫
     * ========================= */
    public static void checkAndAnnounce(L1PcInstance pc) {
        try {
            // 限制 40 級(含)以上才觸發登入排行稱號廣播
            if (pc.getLevel() < 40) {
                return;
            }

            // 職業第一（彼此不衝突）
            announceJob(pc, L1Ranking.Crown(), "王族");
            announceJob(pc, L1Ranking.Knight(), "騎士");
            announceJob(pc, L1Ranking.Wizard(), "法師");
            announceJob(pc, L1Ranking.Elf(), "妖精");
            announceJob(pc, L1Ranking.Darkelf(), "黑妖");
            announceJob(pc, L1Ranking.Dragon(), "龍騎");
            announceJob(pc, L1Ranking.Illusionist(), "幻術");

            // 殺人 / 被殺（殺人優先）
            announceKillDeath(pc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* =========================
     * 職業第一公告
     * ========================= */
    private static void announceJob(L1PcInstance pc, String[] data, String jobName) {
        if (data == null || data.length < 2) {
            return;
        }

        String firstName = data[1];
        if (firstName == null || firstName.equals("空缺")) {
            return;
        }

        if (!pc.getName().equals(firstName)) {
            return; // 只跳過本職業，不影響後面
        }

        broadcast("【第一" + jobName + "】 " + pc.getName() + " 已重返回歸！");
    }

    /* =========================
     * 殺人 / 被殺公告（殺人優先）
     * ========================= */
    private static void announceKillDeath(L1PcInstance pc) {

        boolean isKillFirst = isKillFirst(pc);
        boolean isDeathFirst = isDeathFirst(pc);

        // 殺人優先
        if (isKillFirst) {
            broadcast("【殺人魔】 " + pc.getName() + " 上線了！請各位加倍小心！");
            return;
        }

        if (isDeathFirst) {
            broadcast("【吃吐王】 " + pc.getName() + " 上線了！懇請別再殺他了！");
        }
    }

    /* =========================
     * 是否殺人第一
     * ========================= */
    private static boolean isKillFirst(L1PcInstance pc) {
        String[] data = RankingKillTimer.killName();
        if (data == null || data.length == 0) {
            return false;
        }
        return matchFirst(pc, data);
    }

    /* =========================
     * 是否被殺第一
     * ========================= */
    private static boolean isDeathFirst(L1PcInstance pc) {
        String[] data = RankingKillTimer.deathName();
        if (data == null || data.length == 0) {
            return false;
        }
        return matchFirst(pc, data);
    }

    /* =========================
     * 判斷是否為排行榜第一
     * ========================= */
    private static boolean matchFirst(L1PcInstance pc, String[] data) {
        if (data[0] == null || data[0].trim().isEmpty()) {
            return false;
        }

        // 格式：name,count
        String[] set = data[0].split(",");
        if (set.length < 1) {
            return false;
        }

        return pc.getName().equals(set[0]);
    }

    /* =========================
     * 全服廣播
     * ========================= */
    private static void broadcast(String msg) {
        Collection<L1PcInstance> players = World.get().getAllPlayers();
        for (L1PcInstance target : players) {
            target.sendPackets(new S_SystemMessage(msg));
        }
    }
}
