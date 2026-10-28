package com.lineage.data.cmd;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class Skill_Check
{
    public static void check(L1PcInstance pc, L1ItemInstance item, int skillid, int magicLv, int attribute) {
        if (CharSkillReading.get().spellCheck(pc.getId(), skillid)) {
            S_ServerMessage msg = new S_ServerMessage(79);
            pc.sendPackets(msg);
        }
        else if (skillid != 0) {
            Skill_StudyingExecutor addSkill = new Skill_Studying();
            addSkill.magic(pc, skillid, magicLv, attribute, item.getId());
        }
    }
}
