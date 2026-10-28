package com.lineage.server.model.skillUse;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public abstract class L1SkillMode
{
    public abstract int start(L1PcInstance p0, L1Character p1, int p2, int p3, int p4, String p5) throws Exception;
    
    public abstract int start(L1NpcInstance p0, L1Character p1, int p2, int p3, int p4, String p5) throws Exception;
    
    public void useMode(L1Character user, int skillid) {
    }
    
    public void useLoc(L1Character user, L1Character targ, int skillid) {
    }
    
    public abstract void stop(L1Character p0) throws Exception;
}
