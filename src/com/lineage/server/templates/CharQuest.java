package com.lineage.server.templates;

public class CharQuest
{
    private int _quest_step;
    private int[] _mob_count;
    
    public int get_quest_step() {
        return this._quest_step;
    }
    
    public void set_quest_step(int i) {
        this._quest_step = i;
    }
    
    public int[] get_mob_count() {
        return this._mob_count;
    }
    
    public void set_mob_count(int[] i) {
        this._mob_count = i;
    }
    
    public void add_mob_count(int n) {
        int[] mob_count = this._mob_count;
        ++mob_count[n];
    }
}
