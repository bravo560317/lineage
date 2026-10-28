package com.lineage.data.npc.event;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_GQuest extends NpcExecutor {
    private static final Log _log;

    static {
        _log = LogFactory.getLog(Npc_GQuest.class);
    }

    // 將不連續的 Quest ID 建立成陣列對應表 (依序對應原本的 340000 ~ 340121)
    private static final int[] QUEST_IDS = {
        45008, 45009, 45016, 45019, 45033, 45083, 45041, 45065, 45082, 45079, // 340000 - 340009
        45092, 45125, 45089, 45121, 45122, 45107, 45098, 45127, 45925, 45144, // 340010 - 340019
        45140, 45115, 45131, 45130, 45551, 45146, 45136, 45024, 45109, 45143, // 340020 - 340029
        45157, 45241, 45173, 45161, 45149, 45155, 45126, 45190, 45171, 45213, // 340030 - 340039
        45184, 45264, 45215, 45223, 45147, 45280, 45804, 45346, 45276, 45196, // 340040 - 340049
        45329, 45324, 45265, 45289, 45809, 45806, 45810, 45298, 45393, 45270, // 340050 - 340059
        45817, 45387, 45222, 45451, 45278, 45361, 45373, 45269, 45370, 45818, // 340060 - 340069
        45189, 45816, 45206, 45263, 45286, 45203, 45420, 45381, 45822, 45819, // 340070 - 340079
        45349, 45823, 45257, 45284, 45291, 45341, 45359, 45812, 45365, 45088, // 340080 - 340089
        45141, 45162, 46131, 46132, 45221, 45322, 46125, 45216, 45224, 45248, // 340090 - 340099
        46128, 46129, 46130, 46133, 46134, 45497, 45473, 45488, 45456, 45464, // 340100 - 340109
        45529, 45338, 45545, 45516, 45573, 45600, 45601, 45546, 45614, 45617, // 340110 - 340119
        45609, 45649                                                          // 340120 - 340121
    };

    public static NpcExecutor get() {
        return new Npc_GQuest();
    }

    @Override
    public int type() {
        return 3;
    }

            // 自動對應 questId=340000 ~ 340599
        // for (int i = 0; i < 600; i++) {
        //    int questId = 340000 + i;
        //    int step = pc.getQuest().get_step(questId);
        //    if (step == 1) {
        //        types[i] = "[進行中]";
        //    } else if (step == 2) {
        //        types[i] = "[已完成]";
        //    }
    //    }

    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        // 用陣列取代，支援 600 個成就 (全部先預設為未解)
        String[] types = new String[600]; 
        for (int i = 0; i < 600; i++) {
            types[i] = "[未解]";
        }

        // 讀取新的 QUEST_IDS 陣列來判斷任務狀態
        for (int i = 0; i < QUEST_IDS.length; i++) {
            int questId = QUEST_IDS[i];
            int step = pc.getQuest().get_step(questId);
            
            if (step == 1) {
                types[i] = "[進行中]";
            } else if (step == 2) {
                types[i] = "[已完成]";
            }
        }

        // 保持原本結構，傳給 collect 對話檔
        pc.setpag(0);
        pc.sendPackets(new S_NPCTalkReturn(
            npc.getId(),
            "collect",
            types
        ));
    }
}