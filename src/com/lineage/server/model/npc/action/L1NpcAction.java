package com.lineage.server.model.npc.action;

import com.lineage.server.model.npc.L1NpcHtml;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;

public interface L1NpcAction
{
    public abstract boolean acceptsRequest(String p0, L1PcInstance p1, L1Object p2);
    
    public abstract L1NpcHtml execute(String p0, L1PcInstance p1, L1Object p2, byte[] p3);
    
    public abstract void execute(String p0, String p1);
    
    public abstract L1NpcHtml executeWithAmount(String p0, L1PcInstance p1, L1Object p2, long p3);
}
