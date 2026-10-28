package com.lineage.server.model.doll;

import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class Doll_DefenseWind extends L1DollExecutor
{
    private static final Log _log;
    private int _int1;
    private String _note;
    
    static {
        _log = LogFactory.getLog(Doll_DefenseWind.class);
    }
    
    public static L1DollExecutor get() {
        return new Doll_DefenseWind();
    }
    
    @Override
    public void set_power(int int1, int int2, int int3, double int4) {
        try {
            this._int1 = int1;
        }
        catch (Exception e) {
            Doll_DefenseWind._log.error(e.getLocalizedMessage(), e);
        }
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
            pc.addWind(this._int1);
            pc.sendPackets(new S_OwnCharAttrDef(pc));
        }
        catch (Exception e) {
            Doll_DefenseWind._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void removeDoll(L1PcInstance pc) {
        try {
            pc.addWind(-this._int1);
            pc.sendPackets(new S_OwnCharAttrDef(pc));
        }
        catch (Exception e) {
            Doll_DefenseWind._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public boolean is_reset() {
        return false;
    }
}
