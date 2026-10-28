package com.lineage.server.templates;

public class DeName
{
    private int _deobjid;
    private String _name;
    private int _type;
    private int _sex;
    private int _clanid;
    
    public DeName(int deobjid, String name, int type, int sex, int clanid) {
        this._deobjid = deobjid;
        this._name = name;
        this._type = type;
        this._sex = sex;
        this._clanid = clanid;
    }
    
    public int get_deobjid() {
        return this._deobjid;
    }
    
    public void set_deobjid(int deobjid) {
        this._deobjid = deobjid;
    }
    
    public String get_name() {
        return this._name;
    }
    
    public void set_name(String name) {
        this._name = name;
    }
    
    public int get_type() {
        return this._type;
    }
    
    public void set_type(int type) {
        this._type = type;
    }
    
    public int get_sex() {
        return this._sex;
    }
    
    public void set_sex(int sex) {
        this._sex = sex;
    }
    
    public int get_clanid() {
        return this._clanid;
    }
    
    public void set_clanid(int clanid) {
        this._clanid = clanid;
    }
}
