package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public abstract class SkillMode
{
    public abstract int start(L1PcInstance p0, L1Character p1, L1Magic p2, int p3) throws Exception;
    
    public abstract int start(L1NpcInstance p0, L1Character p1, L1Magic p2, int p3) throws Exception;
    
    public abstract void stop(L1Character p0) throws Exception;
    
    public abstract void start(L1PcInstance p0, Object p1) throws Exception;
}
