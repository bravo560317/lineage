package com.lineage.server.model.doll;

import com.lineage.server.serverpackets.S_SkillIconBlessOfEva;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class Doll_Water extends L1DollExecutor
{
    private static final Log _log;
    private String _note;
    
    static {
        _log = LogFactory.getLog(Doll_Water.class);
    }
    
    public static L1DollExecutor get() {
        return new Doll_Water();
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
            if (pc.hasSkillEffect(1003)) {
                pc.killSkillEffectTimer(1003);
            }
            pc.sendPackets(new S_SkillIconBlessOfEva(pc.getId(), -1));
            pc.setSkillEffect(1003, 0);
        }
        catch (Exception e) {
            Doll_Water._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void removeDoll(L1PcInstance pc) {
        try {
            pc.killSkillEffectTimer(1003);
            pc.sendPackets(new S_SkillIconBlessOfEva(pc.getId(), 0));
        }
        catch (Exception e) {
            Doll_Water._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public boolean is_reset() {
        return false;
    }
}
