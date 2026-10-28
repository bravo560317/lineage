package com.lineage.server.model;

public class L1UbSupplie
{
    private int ub_id;
    private String ub_name;
    private int ub_item_id;
    private int ub_round;
    private int ub_item_stackcont;
    private int ub_item_cont;
    private int ub_item_bless;
    
    public int getUbId() {
        return this.ub_id;
    }
    
    public void setUbId(int id) {
        this.ub_id = id;
    }
    
    public String getUbName() {
        return this.ub_name;
    }
    
    public void setUbName(String name) {
        this.ub_name = name;
    }
    
    public int getUbItemId() {
        return this.ub_item_id;
    }
    
    public void setUbItemId(int id) {
        this.ub_item_id = id;
    }
    
    public int getUbRound() {
        return this.ub_round;
    }
    
    public void setUbRound(int round) {
        this.ub_round = round;
    }
    
    public int getUbItemStackCont() {
        return this.ub_item_stackcont;
    }
    
    public void setUbItemStackCont(int stackcont) {
        this.ub_item_stackcont = stackcont;
    }
    
    public int getUbItemCont() {
        return this.ub_item_cont;
    }
    
    public void setUbItemCont(int cont) {
        this.ub_item_cont = cont;
    }
    
    public int getUbItemBless() {
        return this.ub_item_bless;
    }
    
    public void setUbItemBless(int bless) {
        this.ub_item_bless = bless;
    }
}
