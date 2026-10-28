package com.lineage.data.executor;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1Quest;

public abstract class QuestExecutor
{
    public abstract void execute(L1Quest p0);
    
    public abstract void startQuest(L1PcInstance p0);
    
    public abstract void endQuest(L1PcInstance p0);
    
    public abstract void showQuest(L1PcInstance p0);
    
    public abstract void stopQuest(L1PcInstance p0);
}
