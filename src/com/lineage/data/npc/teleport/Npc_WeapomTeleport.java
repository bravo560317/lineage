package com.lineage.data.npc.teleport;

import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.NpcExecutor;

public class Npc_WeapomTeleport extends NpcExecutor
{
    public static final Random _random;
    
    static {
        _random = new Random();
    }
    
    public static NpcExecutor get() {
        return new Npc_WeapomTeleport();
    }
    
    @Override
    public int type() {
        return 1;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        int r = Npc_WeapomTeleport._random.nextInt(3);
        if (r == 0) {
            L1Teleport.teleport(pc, 32557, 32863, (short)5167, 6, true);
        }
        else if (r == 1) {
            L1Teleport.teleport(pc, 32723, 32799, (short)5167, 0, true);
        }
        else if (r == 2) {
            L1Teleport.teleport(pc, 32606, 32745, (short)5167, 4, true);
        }
    }
}
