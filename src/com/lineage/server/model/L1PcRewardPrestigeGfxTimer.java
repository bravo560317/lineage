package com.lineage.server.model;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.TimerTask;

public class L1PcRewardPrestigeGfxTimer extends TimerTask
{
    private final L1PcInstance _pc;
    private final int _gfx;
    private final int _time;
    
    public L1PcRewardPrestigeGfxTimer(L1PcInstance pc, int gfx, int time) {
        this._pc = pc;
        this._gfx = gfx;
        this._time = time * 1000;
    }
    
    public void start() {
        GeneralThreadPool.get().scheduleAtFixedRate(this, (long)this._time, (long)this._time);
    }
    
    @Override
    public void run() {
        if (this._pc == null) {
            this.cancel();
        }
        if (!this._pc.isDead() && !this._pc.isTeleport()) {
            this._pc.sendPacketsX8(new S_SkillSound(this._pc.getId(), this._gfx));
        }
    }
}
