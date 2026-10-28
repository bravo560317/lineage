package com.lineage.server.templates;

import java.sql.Timestamp;

public class L1Account
{
    private String _login;
    private String _password;
    private Timestamp _lastactive;
    private int _access_level;
    private String _ip;
    private String _mac;
    private int _character_slot;
    private String _spw;
    private int _warehouse;
    private int _countCharacters;
    private boolean _isLoad;
    private int _server_no;
    private int _first_pay;
    private int _logincheck;
    private int _id_card;
    
    public String get_login() {
        return this._login;
    }
    
    public void set_login(String login) {
        this._login = login;
    }
    
    public String get_password() {
        return this._password;
    }
    
    public void set_password(String password) {
        this._password = password;
    }
    
    public Timestamp get_lastactive() {
        return this._lastactive;
    }
    
    public void set_lastactive(Timestamp lastactive) {
        this._lastactive = lastactive;
    }
    
    public int get_access_level() {
        return this._access_level;
    }
    
    public void set_access_level(int access_level) {
        this._access_level = access_level;
    }
    
    public String get_ip() {
        return this._ip;
    }
    
    public void set_ip(String ip) {
        this._ip = ip;
    }
    
    public String get_mac() {
        return this._mac;
    }
    
    public void set_mac(String mac) {
        this._mac = mac;
    }
    
    public int get_character_slot() {
        return this._character_slot;
    }
    
    public void set_character_slot(int character_slot) {
        this._character_slot = character_slot;
    }
    
    public String get_spw() {
        return this._spw;
    }
    
    public void set_spw(String spw) {
        this._spw = spw;
    }
    
    public int get_warehouse() {
        return this._warehouse;
    }
    
    public void set_warehouse(int warehouse) {
        this._warehouse = warehouse;
    }
    
    public int get_countCharacters() {
        return this._countCharacters;
    }
    
    public void set_countCharacters(int characters) {
        this._countCharacters = characters;
    }
    
    public boolean is_isLoad() {
        return this._isLoad;
    }
    
    public void set_isLoad(boolean load) {
        this._isLoad = load;
    }
    
    public void set_server_no(int server_no) {
        this._server_no = server_no;
    }
    
    public int get_server_no() {
        return this._server_no;
    }
    
    public void set_first_pay(int first_pay) {
        this._first_pay = first_pay;
    }
    
    public void set_logincheck(int logincheck) {
        this._logincheck = logincheck;
    }
    
    public int get_first_pay() {
        return this._first_pay;
    }
    
    public int get_logincheck() {
        return this._logincheck;
    }
    
    public void set_id_card(int id_card) {
        this._id_card = id_card;
    }
    
    public int get_id_card() {
        return this._id_card;
    }
}
