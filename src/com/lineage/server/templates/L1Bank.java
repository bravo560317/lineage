package com.lineage.server.templates;

import com.lineage.list.OnlineUser;

public class L1Bank
{
    private String _account_name;
    private long _adena_count;
    private String _pass;
    
    public L1Bank() {
        this._account_name = null;
        this._adena_count = 0L;
        this._pass = null;
    }
    
    public String get_account_name() {
        return this._account_name;
    }
    
    public void set_account_name(String _account_name) {
        this._account_name = _account_name;
    }
    
    public long get_adena_count() {
        return this._adena_count;
    }
    
    public void set_adena_count(long _adena_count) {
        this._adena_count = _adena_count;
    }
    
    public String get_pass() {
        return this._pass;
    }
    
    public void set_pass(String _pass) {
        this._pass = _pass;
    }
    
    public boolean isLan() {
        return OnlineUser.get().isLan(this._account_name);
    }
    
    public boolean isEmpty() {
        return this._adena_count <= 0L;
    }
}
