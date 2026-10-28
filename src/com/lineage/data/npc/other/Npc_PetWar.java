package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.model.L1PetMatch;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_PetWar extends NpcExecutor
{
    private static final Log _log;

    static {
        _log = LogFactory.getLog(Npc_PetWar.class);
    }

    public static NpcExecutor get() {
        return new Npc_PetWar();
    }

    @Override
    public int type() {
        return 3;
    }

    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "petmatcher"));
        }
        catch (Exception e) {
            Npc_PetWar._log.error(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            boolean isCloseList = false;

            if (!cmd.equalsIgnoreCase("ent") && !cmd.toLowerCase().startsWith("ent,")) {
                return;
            }

            if (!pc.getPetList().isEmpty()) {
                pc.sendPackets(new S_ServerMessage(1187)); // 你已經有寵物了
                return;
            }

            int amuletId = 0;
            try {
                String[] temp = cmd.split(",");
                if (temp.length >= 3) {
                    amuletId = Integer.parseInt(temp[2]);
                } else if (temp.length >= 2) {
                    amuletId = Integer.parseInt(temp[1]);
                }
            } catch (Exception ignore) {}

            if (amuletId <= 0) {
                return; // 等玩家真正選完項圈再呼叫
            }

            // 交由核心處理（核心會自行送出「不足/扣除/不可參加」等訊息）
            if (!L1PetMatch.getInstance().enterPetMatch(pc, amuletId)) {
                return;
            }

            if (isCloseList) {
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        catch (Exception e) {
            Npc_PetWar._log.error(e.getLocalizedMessage(), e);
        }
    }
}
