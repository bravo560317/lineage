package com.lineage.server.templates;

import com.lineage.server.model.doll.L1DollExecutor;
import java.util.ArrayList;

public class L1Doll
{
    private int _itemid;
    private ArrayList<L1DollExecutor> powerList;
    private int[] _need;
    private int[] _counts;
    private int _time;
    private int _gfxid;
    private String _nameid;
    private int _mode;
    private int _ArrowGifx;
    
    public L1Doll() {
        this.powerList = null;
    }
    
    public int get_itemid() {
        return this._itemid;
    }
    
    public void set_itemid(int _itemid) {
        this._itemid = _itemid;
    }
    
    public ArrayList<L1DollExecutor> getPowerList() {
        return this.powerList;
    }
    
    public void setPowerList(ArrayList<L1DollExecutor> powerList) {
        this.powerList = powerList;
    }
    
    public int[] get_need() {
        return this._need;
    }
    
    public void set_need(int[] _need) {
        this._need = _need;
    }
    
    public int[] get_counts() {
        return this._counts;
    }
    
    public void set_counts(int[] _counts) {
        this._counts = _counts;
    }
    
    public int get_gfxid() {
        return this._gfxid;
    }
    
    public void set_gfxid(int _gfxid) {
        this._gfxid = _gfxid;
    }
    
    public String get_nameid() {
        return this._nameid;
    }
    
    public void set_nameid(String _nameid) {
        this._nameid = _nameid;
    }
    
    public int get_time() {
        return this._time;
    }
    
    public void set_time(int _time) {
        this._time = _time;
    }
    
    public int get_mode() {
        return this._mode;
    }
    
    public void set_mode(int mode) {
        this._mode = mode;
    }
    
    public int get_ArrowGifx() {
        return this._ArrowGifx;
    }
    
    public void set_ArrowGifx(int ArrowGifx) {
        this._ArrowGifx = ArrowGifx;
    }
}
