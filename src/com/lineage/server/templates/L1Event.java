package com.lineage.server.templates;

import java.sql.Timestamp;

public class L1Event
{
    private int _eventid;
    private String _eventname;
    private String _eventclass;
    private boolean _eventstart;
    private String _eventother;
    private String _eventother2;
    private Timestamp _next_time;
    
    public int get_eventid() {
        return this._eventid;
    }
    
    public void set_eventid(int eventid) {
        this._eventid = eventid;
    }
    
    public String get_eventname() {
        return this._eventname;
    }
    
    public void set_eventname(String eventname) {
        this._eventname = eventname;
    }
    
    public String get_eventclass() {
        return this._eventclass;
    }
    
    public void set_eventclass(String eventclass) {
        this._eventclass = eventclass;
    }
    
    public boolean is_eventstart() {
        return this._eventstart;
    }
    
    public void set_eventstart(boolean eventstart) {
        this._eventstart = eventstart;
    }
    
    public String get_eventother() {
        return this._eventother;
    }
    
    public void set_eventother(String eventother2) {
        this._eventother = eventother2;
    }
    
    public String get_eventother2() {
        return this._eventother2;
    }
    
    public void set_eventother2(String eventother2) {
        this._eventother2 = eventother2;
    }
    
    public Timestamp get_next_time() {
        return this._next_time;
    }
    
    public void set_next_time(Timestamp next_time) {
        this._next_time = next_time;
    }
}
