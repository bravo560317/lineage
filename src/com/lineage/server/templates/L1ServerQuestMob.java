package com.lineage.server.templates;

public class L1ServerQuestMob
{
    private int _quest_id;
    private boolean _can_party;
    private int _quest_step;
    private String _note;
    private int _lv;
    private int[] _mob_id;
    private int[] _mob_count;
    private int[] _item_id;
    private int[] _item_lv;
    private int[] _item_count;
    private int _save_quest_order;
    private int _tele_x;
    private int _addexp;
    private int _tele_y;
    private int _tele_m;
    private int _tele_delay;
    
    public int get_quest_id() {
        return this._quest_id;
    }
    
    public void set_quest_id(int _quest_id) {
        this._quest_id = _quest_id;
    }
    
    public boolean is_can_party() {
        return this._can_party;
    }
    
    public void set_can_party(boolean i) {
        this._can_party = i;
    }
    
    public int get_quest_step() {
        return this._quest_step;
    }
    
    public void set_quest_step(int i) {
        this._quest_step = i;
    }
    
    public String get_note() {
        return this._note;
    }
    
    public void set_note(String _note) {
        this._note = _note;
    }
    
    public int get_lv() {
        return this._lv;
    }
    
    public void set_lv(int _lv) {
        this._lv = _lv;
    }
    
    public int[] get_mob_id() {
        return this._mob_id;
    }
    
    public void set_mob_id(int[] i) {
        this._mob_id = i;
    }
    
    public int[] get_mob_count() {
        return this._mob_count;
    }
    
    public void set_mob_count(int[] i) {
        this._mob_count = i;
    }
    
    public int[] get_item_id() {
        return this._item_id;
    }
    
    public void set_item_id(int[] i) {
        this._item_id = i;
    }
    
    public int[] get_item_lv() {
        return this._item_lv;
    }
    
    public void set_item_lv(int[] i) {
        this._item_lv = i;
    }
    
    public int[] get_item_count() {
        return this._item_count;
    }
    
    public void set_item_count(int[] i) {
        this._item_count = i;
    }
    
    public int get_save_quest_order() {
        return this._save_quest_order;
    }
    
    public void set_save_quest_order(int i) {
        this._save_quest_order = i;
    }
    
    public int get_tele_x() {
        return this._tele_x;
    }
    
    public void set_tele_x(int i) {
        this._tele_x = i;
    }
    
    public int get_tele_y() {
        return this._tele_y;
    }
    
    public void set_tele_y(int i) {
        this._tele_y = i;
    }
    
    public int get_tele_m() {
        return this._tele_m;
    }
    
    public void set_tele_m(int i) {
        this._tele_m = i;
    }
    
    public int get_tele_delay() {
        return this._tele_delay;
    }
    
    public void set_tele_delay(int i) {
        this._tele_delay = i;
    }
    
    public int getaddexp() {
        return this._addexp;
    }
    
    public void setaddexp(int i) {
        this._addexp = i;
    }
}
