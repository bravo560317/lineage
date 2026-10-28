package com.lineage.server.model.doll;

import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class Doll_Speed extends L1DollExecutor
{
    private static final Log _log;
    private String _note;
    
    static {
        _log = LogFactory.getLog(Doll_Speed.class);
    }
    
    public static L1DollExecutor get() {
        return new Doll_Speed();
    }
    
    @Override
    public void set_power(int int1, int int2, int int3, double int4) {
    }
    
    @Override
    public void set_note(String note) {
        this._note = note;
    }
    
    @Override
    public String get_note() {
        return this._note;
    }
    
    @Override
    public void setDoll(L1PcInstance pc) {
        try {
            pc.addHasteItemEquipped(1);
            pc.removeHasteSkillEffect();
            pc.sendPackets(new S_SkillHaste(pc.getId(), 1, -1));
            if (pc.getMoveSpeed() != 1) {
                pc.setMoveSpeed(1);
                pc.broadcastPacketAll(new S_SkillHaste(pc.getId(), 1, 0));
            }
        }
        catch (Exception e) {
            Doll_Speed._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void removeDoll(L1PcInstance pc) {
        try {
            pc.addHasteItemEquipped(-1);
            if (pc.getHasteItemEquipped() == 0) {
                pc.setMoveSpeed(0);
                pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
            }
        }
        catch (Exception e) {
            Doll_Speed._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public boolean is_reset() {
        return false;
    }
}
