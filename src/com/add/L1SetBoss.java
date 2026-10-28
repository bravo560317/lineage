package com.add;

import java.util.Calendar;

public class L1SetBoss
{
    private int _id;
    private String _message;
    private Calendar _resettime;
    
    public int get_id() {
        return this._id;
    }
    
    public void set_id(int _id) {
        this._id = _id;
    }
    
    public String get_message() {
        return this._message;
    }
    
    public void set_message(String _message) {
        this._message = _message;
    }
    
    public Calendar get_resettime() {
        return this._resettime;
    }
    
    public void set_resettime(Calendar _resettime) {
        this._resettime = _resettime;
    }
}
