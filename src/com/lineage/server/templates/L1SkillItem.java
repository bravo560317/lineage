package com.lineage.server.templates;

public class L1SkillItem
{
    private int _skill_id;
    private String _name;
    private int[] _items;
    private int[] _counts;
    
    public int get_skill_id() {
        return this._skill_id;
    }
    
    public void set_skill_id(int i) {
        this._skill_id = i;
    }
    
    public String get_name() {
        return this._name;
    }
    
    public void set_name(String s) {
        this._name = s;
    }
    
    public int[] get_items() {
        return this._items;
    }
    
    public void set_items(int[] is) {
        this._items = is;
    }
    
    public int[] get_counts() {
        return this._counts;
    }
    
    public void set_counts(int[] is) {
        this._counts = is;
    }
}
