package com.lineage.server.templates;

import java.sql.Timestamp;

public class CharItemTime
{
    private Timestamp usertime;
    private String pc_name;
    private String item_name;
    
    public Timestamp get_usertime() {
        return this.usertime;
    }
    
    public void set_usertime(Timestamp i) {
        this.usertime = i;
    }
    
    public String get_pc_name() {
        return this.pc_name;
    }
    
    public void set_pc_name(String i) {
        this.pc_name = i;
    }
    
    public String get_item_name() {
        return this.item_name;
    }
    
    public void set_item_name(String i) {
        this.item_name = i;
    }
}
