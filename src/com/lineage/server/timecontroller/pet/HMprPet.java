package com.lineage.server.timecontroller.pet;

import com.lineage.server.model.Instance.L1NpcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class HMprPet
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(HMprPet.class);
    }
    
    public static boolean mpUpdate(L1NpcInstance npc, int time) {
        try {
            if (npc.getMaxHp() <= 0) {
                return false;
            }
            else if (npc.getMaxMp() <= 0) {
                return false;
            }
            else if (npc.isDead()) {
                return false;
            }
            else if (npc.destroyed()) {
                return false;
            }
            else if (npc.getCurrentHp() <= 0) {
                return false;
            }
            else if (npc.getCurrentMp() >= npc.getMaxMp()) {
                return false;
            }
            else {
                npc.setCurrentMp(npc.getCurrentMp() + 10);
            }
        }
        catch (Exception e) {
            HMprPet._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
}
