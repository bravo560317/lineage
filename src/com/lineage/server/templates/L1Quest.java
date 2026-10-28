package com.lineage.server.templates;

import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Quest
{
    private static final Log _log;
    private int _id;
    private String _questname;
    private String _questclass;
    private boolean _queststart;
    private boolean _del;
    private int _questlevel;
    private int _difficulty;
    private String _note;
    private boolean _isCrown;
    private boolean _isKnight;
    private boolean _isElf;
    private boolean _isWizard;
    private boolean _isDarkelf;
    private boolean _isDragonKnight;
    private boolean _isIllusionist;
    private static final int _int7 = 64;
    private static final int _int6 = 32;
    private static final int _int5 = 16;
    private static final int _int4 = 8;
    private static final int _int3 = 4;
    private static final int _int2 = 2;
    private static final int _int1 = 1;
    
    static {
        _log = LogFactory.getLog(L1Quest.class);
    }
    
    public int get_id() {
        return this._id;
    }
    
    public void set_id(int _id) {
        this._id = _id;
    }
    
    public String get_questname() {
        return this._questname;
    }
    
    public void set_questname(String _questname) {
        this._questname = _questname;
    }
    
    public String get_questclass() {
        return this._questclass;
    }
    
    public void set_questclass(String _questclass) {
        this._questclass = _questclass;
    }
    
    public boolean is_queststart() {
        return this._queststart;
    }
    
    public void set_queststart(boolean _queststart) {
        this._queststart = _queststart;
    }
    
    public int get_questlevel() {
        return this._questlevel;
    }
    
    public void set_questlevel(int _questlevel) {
        this._questlevel = _questlevel;
    }
    
    public void set_difficulty(int _difficulty) {
        this._difficulty = _difficulty;
    }
    
    public int get_difficulty() {
        return this._difficulty;
    }
    
    public void set_note(String _note) {
        this._note = _note;
    }
    
    public String get_note() {
        return this._note;
    }
    
    public void set_questuser(int questuser) {
        try {
            if (questuser >= 64) {
                questuser -= 64;
                this._isIllusionist = true;
            }
            if (questuser >= 32) {
                questuser -= 32;
                this._isDragonKnight = true;
            }
            if (questuser >= 16) {
                questuser -= 16;
                this._isDarkelf = true;
            }
            if (questuser >= 8) {
                questuser -= 8;
                this._isWizard = true;
            }
            if (questuser >= 4) {
                questuser -= 4;
                this._isElf = true;
            }
            if (questuser >= 2) {
                questuser -= 2;
                this._isKnight = true;
            }
            if (questuser >= 1) {
                --questuser;
                this._isCrown = true;
            }
            if (questuser > 0) {
                L1Quest._log.error("任務可執行職業設定錯誤:餘數大於0 編號:" + this._id);
            }
        }
        catch (Exception e) {
            L1Quest._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public boolean check(L1PcInstance pc) {
        try {
            if (pc.isCrown() && this._isCrown) {
                return true;
            }
            else if (pc.isKnight() && this._isKnight) {
                return true;
            }
            else if (pc.isElf() && this._isElf) {
                return true;
            }
            else if (pc.isWizard() && this._isWizard) {
                return true;
            }
            else if (pc.isDarkelf() && this._isDarkelf) {
                return true;
            }
            else if (pc.isDragonKnight() && this._isDragonKnight) {
                return true;
            }
            else if (pc.isIllusionist() && this._isIllusionist) {
                return true;
            }
        }
        catch (Exception e) {
            L1Quest._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    public void set_del(boolean del) {
        this._del = del;
    }
    
    public boolean is_del() {
        return this._del;
    }
}
