package com.lineage.server.timecontroller;

import java.util.Iterator;
import com.lineage.william.ArmorSkillSound;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.TimerTask;

public class ArmorEffect extends TimerTask
{
    public ArmorEffect() {
        GeneralThreadPool.get().scheduleAtFixedRate(this, 0L, 15000L);
    }
    
    @Override
    public void run() {
        for (L1PcInstance pc : World.get().getAllPlayers()) {
            ArmorSkillSound.forArmorSkillSound(pc);
        }
    }
}
