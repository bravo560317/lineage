package com.lineage.server.templates;

import java.util.HashMap;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Map;

public class L1PcOther3
{
    private static final Map<Integer, StringBuilder> _titleList;
    private static boolean _isStart;
    public static final int CLEVLE0 = 1;
    public static final int CLEVLE1 = 2;
    public static final int CLEVLE2 = 4;
    public static final int CLEVLE3 = 8;
    public static final int CLEVLE4 = 16;
    public static final int CLEVLE5 = 32;
    public static final int CLEVLE6 = 64;
    public static final int CLEVLE7 = 128;
    public static final int CLEVLE8 = 256;
    public static final int CLEVLE9 = 512;
    public static final int CLEVLE10 = 1024;
    public static final String[] ADDNAME;
    private L1ItemInstance _item;
    private boolean _shopSkill;
    private int _objid;
    private int _page;
    private int _type1;
    private int _type2;
    private int _type3;
    private int _type4;
    private int _type5;
    private int _type6;
    
    static {
        _titleList = new HashMap();
        L1PcOther3._isStart = false;
        ADDNAME = new String[] { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
    }
    
    public L1PcOther3() {
        this._item = null;
        this._shopSkill = false;
        this._objid = 0;
        this._page = 0;
        this._type1 = 0;
        this._type2 = 0;
        this._type3 = 0;
        this._type4 = 0;
        this._type5 = 0;
        this._type6 = 0;
    }
    
    public static void load() {
        if (!L1PcOther3._isStart) {
            L1PcOther3._titleList.put(1, new StringBuilder(""));
            L1PcOther3._titleList.put(2, new StringBuilder("\\fD"));
            L1PcOther3._titleList.put(4, new StringBuilder("\\f="));
            L1PcOther3._titleList.put(8, new StringBuilder("\\fH"));
            L1PcOther3._titleList.put(16, new StringBuilder("\\f_"));
            L1PcOther3._titleList.put(32, new StringBuilder("\\f2"));
            L1PcOther3._titleList.put(64, new StringBuilder("\\fF"));
            L1PcOther3._titleList.put(128, new StringBuilder("\\fT"));
            L1PcOther3._titleList.put(256, new StringBuilder("\\fE"));
            L1PcOther3._titleList.put(512, new StringBuilder("\\f0"));
            L1PcOther3._titleList.put(1024, new StringBuilder("\\f?"));
            L1PcOther3._isStart = true;
        }
    }
    
    public void set_item(L1ItemInstance item) {
        this._item = item;
    }
    
    public L1ItemInstance get_item() {
        return this._item;
    }
    
    public void set_shopSkill(boolean shopSkill) {
        this._shopSkill = shopSkill;
    }
    
    public boolean get_shopSkill() {
        return this._shopSkill;
    }
    
    public void set_objid(int objid) {
        this._objid = objid;
    }
    
    public int get_objid() {
        return this._objid;
    }
    
    public void set_page(int page) {
        this._page = page;
    }
    
    public int get_page() {
        return this._page;
    }
    
    public void set_type1(int i) {
        this._type1 = i;
    }
    
    public int get_type1() {
        return this._type1;
    }
    
    public void addtype1(int i) {
        this._type1 -= i;
    }
    
    public void set_type2(int i) {
        this._type2 = i;
    }
    
    public int get_type2() {
        return this._type2;
    }
    
    public void set_type3(int i) {
        this._type3 = i;
    }
    
    public int get_type3() {
        return this._type3;
    }
    
    public void set_type4(int i) {
        this._type4 = i;
    }
    
    public int get_type4() {
        return this._type4;
    }
    
    public void set_type5(int i) {
        this._type5 = i;
    }
    
    public int get_type5() {
        return this._type5;
    }
    
    public void set_type6(int i) {
        this._type6 = i;
    }
    
    public int get_type6() {
        return this._type6;
    }
}
