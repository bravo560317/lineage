package com.lineage.server.model.doll;

import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class Doll_DmgR extends L1DollExecutor
{
    private static final Log _log;
    private int _int1;
    private int _int2;
    private String _note;
    
    static {
        _log = LogFactory.getLog(Doll_DmgR.class);
    }
    
    public static L1DollExecutor get() {
        return new Doll_DmgR();
    }
    
    @Override
    public void set_power(int int1, int int2, int int3, double int4) {
        try {
            this._int1 = int1;
            this._int2 = int2;
        }
        catch (Exception e) {
            Doll_DmgR._log.error(e.getLocalizedMessage(), e);
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
            pc.set_dmgAdd(this._int1, this._int2);
        }
        catch (Exception e) {
            Doll_DmgR._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void removeDoll(L1PcInstance pc) {
        try {
            pc.set_dmgAdd(-this._int1, -this._int2);
        }
        catch (Exception e) {
            Doll_DmgR._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public boolean is_reset() {
        return false;
    }
}
