package com.lineage.server.timecontroller.server;

import com.add.L1Ranking;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.world.World;
import com.lineage.server.timecontroller.event.ranking.RankingKillTimer;

public class RankingEffectTimer implements Runnable {

    private static final int INTERVAL = 30 * 1000; // 30 秒
    private static final int DELAY = 2500;        // 2.5 秒

    @Override
    public void run() {
        while (true) {
            try {
                // 第一職業（立刻播）
                playJob(L1Ranking.Crown(), 19031);
                playJob(L1Ranking.Knight(), 19032);
                playJob(L1Ranking.Wizard(), 19033);
                playJob(L1Ranking.Elf(), 19034);
                playJob(L1Ranking.Darkelf(), 19035);
                playJob(L1Ranking.Dragon(), 19036);
                playJob(L1Ranking.Illusionist(), 19037);

                // 🔪 殺人魔（晚 2.5 秒）
                playKillDelay(RankingKillTimer.killName(), 19045, DELAY);

                // 💀 吃吐王（晚 2.5 秒）
                playKillDelay(RankingKillTimer.deathName(), 19048, DELAY);

                Thread.sleep(INTERVAL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /** 第一職業特效 */
    private void playJob(String[] data, int gfxId) {
        if (data == null || data.length < 2) {
            return;
        }

        String name = data[1];
        if (name == null || name.trim().isEmpty() || name.equals("空缺")) {
            return;
        }

        L1PcInstance pc = World.get().getPlayer(name);
        if (pc == null) {
            return;
        }

        S_SkillSound packet = new S_SkillSound(pc.getId(), gfxId);
        pc.sendPackets(packet);
        pc.broadcastPacket(packet);
    }

    /** 殺人 / 被殺排行榜（格式：name,count）延遲播放 */
    private void playKillDelay(String[] data, int gfxId, int delayMs) {
        if (data == null || data.length < 1) {
            return;
        }

        String raw = data[0];
        if (raw == null || raw.trim().isEmpty() || !raw.contains(",")) {
            return;
        }

        String name = raw.split(",")[0];

        L1PcInstance pc = World.get().getPlayer(name);
        if (pc == null) {
            return;
        }

        GeneralThreadPool.get().schedule(() -> {
            S_SkillSound packet = new S_SkillSound(pc.getId(), gfxId);
            pc.sendPackets(packet);
            pc.broadcastPacket(packet);
        }, delayMs);
    }
}
