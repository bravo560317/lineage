package com.lineage.server.templates;

public class L1Beginner {

    private String _activate;
    private int _itemid;
    private int _count;
    private int _enchantlvl;
    private int _charge_count;
    private int _time;

    // ★ 新增：穿戴設定 0=不穿戴 1=穿戴
    private int _wearing;

    public String get_activate() {
        return this._activate;
    }

    public void set_activate(String activate) {
        this._activate = activate;
    }

    public int get_itemid() {
        return this._itemid;
    }

    public void set_itemid(int itemid) {
        this._itemid = itemid;
    }

    public int get_count() {
        return this._count;
    }

    public void set_count(int count) {
        this._count = count;
    }

    public int get_enchantlvl() {
        return this._enchantlvl;
    }

    public void set_enchantlvl(int enchantlvl) {
        this._enchantlvl = enchantlvl;
    }

    public int get_charge_count() {
        return this._charge_count;
    }

    public void set_charge_count(int charge_count) {
        this._charge_count = charge_count;
    }

    public int get_time() {
        return this._time;
    }

    public void set_time(int time) {
        this._time = time;
    }

    // ★ 新增：穿戴讀寫方法
    public int get_wearing() {
        return this._wearing;
    }

    public void set_wearing(int wearing) {
        this._wearing = wearing;
    }
}
