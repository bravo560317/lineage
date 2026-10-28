package com.lineage.server.templates;

public class DeClan
{
    private String _clanname;
    private int _clan_id;
    private String _leader_name;
    private int _emblemid;
    
    public DeClan(String clanname, int clan_id, String leader_name, int emblemid) {
        this._clanname = clanname;
        this._clan_id = clan_id;
        this._leader_name = leader_name;
        this._emblemid = emblemid;
    }
    
    public void set_clanname(String _clanname) {
        this._clanname = _clanname;
    }
    
    public void set_clan_id(int _clan_id) {
        this._clan_id = _clan_id;
    }
    
    public void set_leader_name(String _leader_name) {
        this._leader_name = _leader_name;
    }
    
    public String get_clanname() {
        return this._clanname;
    }
    
    public int get_clan_id() {
        return this._clan_id;
    }
    
    public String get_leader_name() {
        return this._leader_name;
    }
    
    public int get_emblemid() {
        return this._emblemid;
    }
    
    public void set_emblemid(int _emblemid) {
        this._emblemid = _emblemid;
    }
}
