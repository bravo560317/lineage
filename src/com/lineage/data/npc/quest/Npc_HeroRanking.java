package com.lineage.data.npc.quest;

import com.lineage.server.timecontroller.event.ranking.RankingHeroTimer;
import com.lineage.server.timecontroller.event.ranking.RankingHeroTimerlv;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_HeroRanking extends NpcExecutor {

    private Npc_HeroRanking() {
    }

    public static NpcExecutor get() {
        return new Npc_HeroRanking();
    }

    @Override
    public int type() {
        return 3;
    }

    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_h_1"));
    }

    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        String[] userName = new String[11];

        if (ConfigOther.RankLevel) {
            if (cmd.equals("c")) {
                fillNames(userName, RankingHeroTimerlv.userNameC(), "王族");
            } else if (cmd.equals("k")) {
                fillNames(userName, RankingHeroTimerlv.userNameK(), "騎士");
            } else if (cmd.equals("e")) {
                fillNames(userName, RankingHeroTimerlv.userNameE(), "精靈");
            } else if (cmd.equals("w")) {
                fillNames(userName, RankingHeroTimerlv.userNameW(), "法師");
            } else if (cmd.equals("d")) {
                fillNames(userName, RankingHeroTimerlv.userNameD(), "黑暗精靈");
            } else if (cmd.equals("g")) {
                fillNames(userName, RankingHeroTimerlv.userNameG(), "龍騎士");
            } else if (cmd.equals("i")) {
                fillNames(userName, RankingHeroTimerlv.userNameI(), "幻術師");
            } else if (cmd.equals("a")) {
                fillNames(userName, RankingHeroTimerlv.userNameAll(), "全職業");
            }
        } else {
            if (cmd.equals("c")) {
                fillNames(userName, RankingHeroTimer.userNameC(), "王族");
            } else if (cmd.equals("k")) {
                fillNames(userName, RankingHeroTimer.userNameK(), "騎士");
            } else if (cmd.equals("e")) {
                fillNames(userName, RankingHeroTimer.userNameE(), "精靈");
            } else if (cmd.equals("w")) {
                fillNames(userName, RankingHeroTimer.userNameW(), "法師");
            } else if (cmd.equals("d")) {
                fillNames(userName, RankingHeroTimer.userNameD(), "黑暗精靈");
            } else if (cmd.equals("g")) {
                fillNames(userName, RankingHeroTimer.userNameG(), "龍騎士");
            } else if (cmd.equals("i")) {
                fillNames(userName, RankingHeroTimer.userNameI(), "幻術師");
            } else if (cmd.equals("a")) {
                fillNames(userName, RankingHeroTimer.userNameAll(), "全職業");
            }
        }

        if (userName != null) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_h_2", userName));
        }
    }

    /**
     * 把 names 填入 userName，並移除名字中的等級資訊 (括號後的字)
     */
    private void fillNames(String[] userName, String[] names, String label) {
        for (int i = 0; i < names.length; ++i) {
            if (names[i] != null) {
                int idx = names[i].indexOf("(");
                if (idx > 0) {
                    userName[i] = names[i].substring(0, idx); // 去掉 "(等級)"
                } else {
                    userName[i] = names[i];
                }
            }
        }
        userName[10] = label;
    }
}
