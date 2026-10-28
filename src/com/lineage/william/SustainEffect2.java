package com.lineage.william;

import java.util.logging.Level;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.logging.Logger;
import java.util.TimerTask;

public class SustainEffect2 extends TimerTask
{
    private static Logger _log;
    private final L1PcInstance Active_pc;
    private final int effect_id;
    
    static {
        SustainEffect2._log = Logger.getLogger(SustainEffect2.class.getName());
    }
    
    public SustainEffect2(L1PcInstance pc, int effect) {
        this.Active_pc = pc;
        this.effect_id = effect;
    }
    
    @Override
    public void run() {
        try {
            if (this.Active_pc.isDead()) {
                return;
            }
            else {
                this.Active_pc.sendPackets(new S_SkillSound(this.Active_pc.getId(), this.effect_id));
            }
        }
        catch (Throwable e) {
            SustainEffect2._log.log(Level.WARNING, e.getLocalizedMessage(), e);
        }
    }
}
