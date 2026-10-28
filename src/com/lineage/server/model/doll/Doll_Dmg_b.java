package com.lineage.server.model.doll;

import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class Doll_Dmg_b extends L1DollExecutor
{
    private static final Log _log;
    private int _int1;
    private int _int2;
    private double _int4;
    private String _note;
    
    static {
        _log = LogFactory.getLog(Doll_Dmg_b.class);
    }
    
    public static L1DollExecutor get() {
        return new Doll_Dmg_b();
    }
    
    @Override
    public void set_power(int int1, int int2, int int3, double int4) {
        try {
            this._int4 = int4;
            this._int2 = int2;
        }
        catch (Exception e) {
            Doll_Dmg_b._log.error(e.getLocalizedMessage(), e);
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
            pc.setDmgup_b_ran(this._int2);
            pc.setDmgup_b(this._int4);
        }
        catch (Exception e) {
            Doll_Dmg_b._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void removeDoll(L1PcInstance pc) {
        try {
            pc.setDmgup_b_ran(-this._int2);
            pc.setDmgup_b(-this._int4);
        }
        catch (Exception e) {
            Doll_Dmg_b._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public boolean is_reset() {
        return false;
    }
}
