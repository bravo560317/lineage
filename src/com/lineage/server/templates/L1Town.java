package com.lineage.server.templates;

public class L1Town
{
    private int _townid;
    private String _name;
    private int _leader_id;
    private String _leader_name;
    private int _tax_rate;
    private int _tax_rate_reserved;
    private int _sales_money;
    private int _sales_money_yesterday;
    private int _town_tax;
    private int _town_fix_tax;
    
    public int get_townid() {
        return this._townid;
    }
    
    public void set_townid(int i) {
        this._townid = i;
    }
    
    public String get_name() {
        return this._name;
    }
    
    public void set_name(String s) {
        this._name = s;
    }
    
    public int get_leader_id() {
        return this._leader_id;
    }
    
    public void set_leader_id(int i) {
        this._leader_id = i;
    }
    
    public String get_leader_name() {
        return this._leader_name;
    }
    
    public void set_leader_name(String s) {
        this._leader_name = s;
    }
    
    public int get_tax_rate() {
        return this._tax_rate;
    }
    
    public void set_tax_rate(int i) {
        this._tax_rate = i;
    }
    
    public int get_tax_rate_reserved() {
        return this._tax_rate_reserved;
    }
    
    public void set_tax_rate_reserved(int i) {
        this._tax_rate_reserved = i;
    }
    
    public int get_sales_money() {
        return this._sales_money;
    }
    
    public void set_sales_money(int i) {
        this._sales_money = i;
    }
    
    public int get_sales_money_yesterday() {
        return this._sales_money_yesterday;
    }
    
    public void set_sales_money_yesterday(int i) {
        this._sales_money_yesterday = i;
    }
    
    public int get_town_tax() {
        return this._town_tax;
    }
    
    public void set_town_tax(int i) {
        this._town_tax = i;
    }
    
    public int get_town_fix_tax() {
        return this._town_fix_tax;
    }
    
    public void set_town_fix_tax(int i) {
        this._town_fix_tax = i;
    }
}
