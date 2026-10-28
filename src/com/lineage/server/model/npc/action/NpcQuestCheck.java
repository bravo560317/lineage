package com.lineage.server.model.npc.action;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_SystemMessage;

public class NpcQuestCheck {

    public static void action(L1PcInstance pc, L1NpcInstance npc, String cmd) {

        if (cmd.startsWith("quest")) {

            String[] data = cmd.split(" ");
            int questId = Integer.parseInt(data[1]);

            int step = pc.getQuest().get_step(questId);

            if (step == 0) {
                pc.sendPackets(new S_SystemMessage("任務" + questId + "：尚未接取"));
            }

            else if (step == 1) {
                pc.sendPackets(new S_SystemMessage("任務" + questId + "：進行中"));
            }

            else if (step == 255) {
                pc.sendPackets(new S_SystemMessage("任務" + questId + "：已完成"));
            }

            else {
                pc.sendPackets(new S_SystemMessage("任務" + questId + "：狀態=" + step));
            }

            return;
        }
    }
}