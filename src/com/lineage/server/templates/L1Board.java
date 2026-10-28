package com.lineage.server.templates;

public class L1Board
{
    private int _id;
    private String _name;
    private String _date;
    private String _title;
    private String _content;
    
    public int get_id() {
        return this._id;
    }
    
    public void set_id(int id) {
        this._id = id;
    }
    
    public String get_name() {
        return this._name;
    }
    
    public void set_name(String name) {
        this._name = name;
    }
    
    public String get_date() {
        return this._date;
    }
    
    public void set_date(String date) {
        this._date = date;
    }
    
    public String get_title() {
        return this._title;
    }
    
    public void set_title(String title) {
        this._title = title;
    }
    
    public String get_content() {
        return this._content;
    }
    
    public void set_content(String content) {
        this._content = content;
    }
}
