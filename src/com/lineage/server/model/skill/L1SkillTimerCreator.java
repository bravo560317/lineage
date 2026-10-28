package com.lineage.server.model.skill;

import com.lineage.server.model.L1Character;

public class L1SkillTimerCreator
{
    public static L1SkillTimer create(L1Character cha, int skillId, int timeMillis) {
        return new L1SkillTimerTimerImpl(cha, skillId, timeMillis);
    }
}
