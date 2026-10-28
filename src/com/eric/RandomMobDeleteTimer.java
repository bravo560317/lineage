package com.eric;

import java.util.Timer;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.TimerTask;

public class RandomMobDeleteTimer extends TimerTask
{
    private int _randomMobId;
    private L1NpcInstance[] _npc;
    
    public RandomMobDeleteTimer(int randomMobId, L1NpcInstance[] npc) {
        this._randomMobId = randomMobId;
        this._npc = npc;
    }
    
    @Override
    public void run() {
        L1SpawnUtil.spawn(this._randomMobId);
        L1NpcInstance[] npc2;
        for (int length = (npc2 = this._npc).length, i = 0; i < length; ++i) {
            L1NpcInstance npc = npc2[i];
            npc.deleteMe();
        }
        this.cancel();
    }
    
    public void begin() {
        Timer timer = new Timer();
        if (RandomMobTable.getInstance().getTimeSecondToDelete(this._randomMobId) > 0) {
            timer.schedule(this, (long)(RandomMobTable.getInstance().getTimeSecondToDelete(this._randomMobId) * 1000));
        }
    }
}
