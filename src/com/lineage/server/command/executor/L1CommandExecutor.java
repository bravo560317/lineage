package com.lineage.server.command.executor;

import com.lineage.server.model.Instance.L1PcInstance;

public interface L1CommandExecutor
{
    public abstract void execute(L1PcInstance p0, String p1, String p2);
}
