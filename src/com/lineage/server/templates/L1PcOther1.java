package com.lineage.server.templates;

import java.util.HashMap;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Map;

public class L1PcOther1
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
    private int _page;
    private int _type1;
    private int _type2;
    private int _type3;
    private int _type4;
    private int _type5;
    private int _type6;
    private int _type7;
    private int _type8;
    private int _type9;
    private int _type10;
    private int _type11;
    private int _type12;
    private int _type13;
    private int _type14;
    private int _type15;
    private int _type16;
    private int _type17;
    private int _type18;
    private int _type19;
    private int _type20;
    private int _type21;
    private int _type22;
    private int _type23;
    private int _type24;
    private int _type25;
    private int _type26;
    private int _type27;
    private int _type28;
    private int _type29;
    private String _type30;
    private String _type31;
    private String _type32;
    private String _type33;
    private String _type34;
    private String _type35;
    private String _type36;
    private String _type37;
    private String _type38;
    private String _type39;
    private String _type40;
    private int _type41;
    private int _type42;
    private int _type43;
    private int _type44;
    private int _type45;
    private int _type46;
    private int _type47;
    private int _type48;
    private int _type49;
    private int _type50;
    private int _objid;
    
    static {
        _titleList = new HashMap();
        L1PcOther1._isStart = false;
        ADDNAME = new String[] { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
    }
    
    public L1PcOther1() {
        this._item = null;
        this._shopSkill = false;
        this._page = 0;
        this._type1 = 0;
        this._type2 = 0;
        this._type3 = 0;
        this._type4 = 0;
        this._type5 = 0;
        this._type6 = 0;
        this._type7 = 0;
        this._type8 = 0;
        this._type9 = 0;
        this._type10 = 0;
        this._type11 = 0;
        this._type12 = 0;
        this._type13 = 0;
        this._type14 = 0;
        this._type15 = 0;
        this._type16 = 0;
        this._type17 = 0;
        this._type18 = 0;
        this._type19 = 0;
        this._type20 = 0;
        this._type21 = 0;
        this._type22 = 0;
        this._type23 = 0;
        this._type24 = 0;
        this._type25 = 0;
        this._type26 = 0;
        this._type27 = 0;
        this._type28 = 0;
        this._type29 = 0;
        this._type41 = 0;
        this._type42 = 0;
        this._type43 = 0;
        this._type44 = 0;
        this._type45 = 0;
        this._type46 = 0;
        this._type47 = 0;
        this._type48 = 0;
        this._type49 = 0;
        this._type50 = 0;
        this._objid = 0;
    }
    
    public static void load() {
        if (!L1PcOther1._isStart) {
            L1PcOther1._titleList.put(1, new StringBuilder(""));
            L1PcOther1._titleList.put(2, new StringBuilder("\\fD"));
            L1PcOther1._titleList.put(4, new StringBuilder("\\f="));
            L1PcOther1._titleList.put(8, new StringBuilder("\\fH"));
            L1PcOther1._titleList.put(16, new StringBuilder("\\f_"));
            L1PcOther1._titleList.put(32, new StringBuilder("\\f2"));
            L1PcOther1._titleList.put(64, new StringBuilder("\\fF"));
            L1PcOther1._titleList.put(128, new StringBuilder("\\fT"));
            L1PcOther1._titleList.put(256, new StringBuilder("\\fE"));
            L1PcOther1._titleList.put(512, new StringBuilder("\\f0"));
            L1PcOther1._titleList.put(1024, new StringBuilder("\\f?"));
            L1PcOther1._isStart = true;
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
    
    public void set_type7(int i) {
        this._type7 = i;
    }
    
    public int get_type7() {
        return this._type7;
    }
    
    public void set_type8(int i) {
        this._type8 = i;
    }
    
    public int get_type8() {
        return this._type8;
    }
    
    public void set_type9(int i) {
        this._type9 = i;
    }
    
    public int get_type9() {
        return this._type9;
    }
    
    public void set_type10(int i) {
        this._type10 = i;
    }
    
    public int get_type10() {
        return this._type10;
    }
    
    public void set_type11(int i) {
        this._type11 = i;
    }
    
    public int get_type11() {
        return this._type11;
    }
    
    public void set_type12(int i) {
        this._type12 = i;
    }
    
    public int get_type12() {
        return this._type12;
    }
    
    public void set_type13(int i) {
        this._type13 = i;
    }
    
    public int get_type13() {
        return this._type13;
    }
    
    public void set_type14(int i) {
        this._type14 = i;
    }
    
    public int get_type14() {
        return this._type14;
    }
    
    public void set_type15(int i) {
        this._type15 = i;
    }
    
    public int get_type15() {
        return this._type15;
    }
    
    public void set_type16(int i) {
        this._type16 = i;
    }
    
    public int get_type16() {
        return this._type16;
    }
    
    public void set_type17(int i) {
        this._type17 = i;
    }
    
    public int get_type17() {
        return this._type17;
    }
    
    public void set_type18(int i) {
        this._type18 = i;
    }
    
    public int get_type18() {
        return this._type18;
    }
    
    public void set_type19(int i) {
        this._type19 = i;
    }
    
    public int get_type19() {
        return this._type19;
    }
    
    public void set_type20(int i) {
        this._type20 = i;
    }
    
    public int get_type20() {
        return this._type20;
    }
    
    public void set_type21(int i) {
        this._type21 = i;
    }
    
    public int get_type21() {
        return this._type21;
    }
    
    public void set_type22(int i) {
        this._type22 = i;
    }
    
    public int get_type22() {
        return this._type22;
    }
    
    public void set_type23(int i) {
        this._type23 = i;
    }
    
    public int get_type23() {
        return this._type23;
    }
    
    public void set_type24(int i) {
        this._type24 = i;
    }
    
    public int get_type24() {
        return this._type24;
    }
    
    public void set_type25(int i) {
        this._type25 = i;
    }
    
    public int get_type25() {
        return this._type25;
    }
    
    public void set_type26(int i) {
        this._type26 = i;
    }
    
    public int get_type26() {
        return this._type26;
    }
    
    public void set_type27(int i) {
        this._type27 = i;
    }
    
    public int get_type27() {
        return this._type27;
    }
    
    public void set_type28(int i) {
        this._type28 = i;
    }
    
    public int get_type28() {
        return this._type28;
    }
    
    public void set_type29(int i) {
        this._type29 = i;
    }
    
    public int get_type29() {
        return this._type29;
    }
    
    public String get_type30() {
        return this._type30;
    }
    
    public void set_type30(String s) {
        this._type30 = s;
    }
    
    public String get_type31() {
        return this._type31;
    }
    
    public void set_type31(String s) {
        this._type31 = s;
    }
    
    public String get_type32() {
        return this._type32;
    }
    
    public void set_type32(String s) {
        this._type32 = s;
    }
    
    public String get_type33() {
        return this._type33;
    }
    
    public void set_type33(String s) {
        this._type33 = s;
    }
    
    public String get_type34() {
        return this._type34;
    }
    
    public void set_type34(String s) {
        this._type34 = s;
    }
    
    public String get_type35() {
        return this._type35;
    }
    
    public void set_type35(String s) {
        this._type35 = s;
    }
    
    public String get_type36() {
        return this._type36;
    }
    
    public void set_type36(String s) {
        this._type36 = s;
    }
    
    public String get_type37() {
        return this._type37;
    }
    
    public void set_type37(String s) {
        this._type37 = s;
    }
    
    public String get_type38() {
        return this._type38;
    }
    
    public void set_type38(String s) {
        this._type38 = s;
    }
    
    public String get_type39() {
        return this._type39;
    }
    
    public void set_type39(String s) {
        this._type39 = s;
    }
    
    public String get_type40() {
        return this._type40;
    }
    
    public void set_type40(String s) {
        this._type40 = s;
    }
    
    public void set_type41(int i) {
        this._type41 = i;
    }
    
    public int get_type41() {
        return this._type41;
    }
    
    public void set_type42(int i) {
        this._type42 = i;
    }
    
    public int get_type42() {
        return this._type42;
    }
    
    public void set_type43(int i) {
        this._type43 = i;
    }
    
    public int get_type43() {
        return this._type43;
    }
    
    public void set_type44(int i) {
        this._type44 = i;
    }
    
    public int get_type44() {
        return this._type44;
    }
    
    public void set_type45(int i) {
        this._type45 = i;
    }
    
    public int get_type45() {
        return this._type45;
    }
    
    public void set_type46(int i) {
        this._type46 = i;
    }
    
    public int get_type46() {
        return this._type46;
    }
    
    public void set_type47(int i) {
        this._type47 = i;
    }
    
    public int get_type47() {
        return this._type47;
    }
    
    public void set_type48(int i) {
        this._type48 = i;
    }
    
    public int get_type48() {
        return this._type48;
    }
    
    public void set_type49(int i) {
        this._type49 = i;
    }
    
    public int get_type49() {
        return this._type49;
    }
    
    public void set_type50(int i) {
        this._type50 = i;
    }
    
    public int get_type50() {
        return this._type50;
    }
    
    public void set_objid(int objid) {
        this._objid = objid;
    }
    
    public int get_objid() {
        return this._objid;
    }
}
