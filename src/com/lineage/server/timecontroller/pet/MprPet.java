package com.lineage.server.timecontroller.pet;

import com.lineage.server.model.Instance.L1NpcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class MprPet
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(MprPet.class);
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
                int mprInterval = npc.getNpcTemplate().get_mprinterval();
                if (mprInterval <= 0) {
                    mprInterval = 20;
                }
                if (time % mprInterval == 0) {
                    int mpr = npc.getNpcTemplate().get_mpr();
                    if (mpr <= 0) {
                        mpr = 1;
                    }
                    mprInterval(npc, mpr);
                    return true;
                }
            }
        }
        catch (Exception e) {
            MprPet._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    private static void mprInterval(L1NpcInstance npc, int mpr) {
        try {
            if (npc.isMpRegenerationX()) {
                npc.setCurrentMp(npc.getCurrentMp() + mpr);
            }
        }
        catch (Exception e) {
            MprPet._log.error("PET 執行回復MP發生異常", e);
            npc.deleteMe();
        }
    }
}
