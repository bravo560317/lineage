package com.lineage.server.timecontroller.quest;

import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.TimerTask;

public class KIRTAS_Timer extends TimerTask
{
    private final L1NpcInstance _npc;
    
    public KIRTAS_Timer(L1NpcInstance npc) {
        this._npc = npc;
    }
    
    @Override
    public void run() {
        if (this._npc.getNpcId() == 81163 && (this._npc.hasSkillEffect(11057) || this._npc.hasSkillEffect(11058) || this._npc.hasSkillEffect(11059) || this._npc.hasSkillEffect(11060))) {
            this._npc.setbarrierTime(this._npc.getbarrierTime() + 1);
        }
    }
}
