package com.lineage.server.model.monitor;

import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;

public abstract class L1PcMonitor implements Runnable
{
    protected int _id;
    
    public L1PcMonitor(int oId) {
        this._id = oId;
    }
    
    @Override
    public final void run() {
        L1PcInstance pc = (L1PcInstance)World.get().findObject(this._id);
        if (pc == null || pc.getNetConnection() == null) {
            return;
        }
        else {
            this.execTask(pc);
            return;
        }
    }
    
    public abstract void execTask(L1PcInstance p0);
}
