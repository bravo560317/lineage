package com.lineage.server.model;

import com.lineage.server.model.Instance.L1PcInstance;

public class AutoNextQuest {

    public static void check(L1PcInstance pc, int questId) {

        if (questId < 9000 || questId > 9200) {
            return;
        }

        int nextQuest = questId + 1;

        if (nextQuest > 9060) {
            return;
        }

        if (pc.getQuest().get_step(nextQuest) == 0) {
            pc.getQuest().set_step(nextQuest, 1);
        }
    }
}