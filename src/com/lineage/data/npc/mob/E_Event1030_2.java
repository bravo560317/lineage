package com.lineage.data.npc.mob;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class E_Event1030_2 extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(E_Event1030_2.class);
        E_Event1030_2._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new E_Event1030_2();
    }
    
    @Override
    public int type() {
        return 4;
    }
    
    @Override
    public void attack(L1PcInstance pc, L1NpcInstance npc) {
        try {
            int i = E_Event1030_2._random.nextInt(100);
            if (i >= 0 && i <= 1) {
                if (npc.isremovearmor()) {
                    return;
                }
                else if (pc.hasSkillEffect(4012)) {
                    return;
                }
                else {
                    pc.sendPacketsX8(new S_SkillSound(pc.getId(), 7782));
                    pc.setSkillEffect(4012, 12000);
                    npc.set_removearmor(true);
                }
            }
        }
        catch (Exception e) {
            E_Event1030_2._log.error(e.getLocalizedMessage(), e);
        }
    }
}
