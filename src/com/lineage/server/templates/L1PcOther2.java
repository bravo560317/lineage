package com.lineage.server.templates;

import java.util.HashMap;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Map;

public class L1PcOther2
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
    private int _TfCount;
    private int _TfCount1;
    private int _Attack;
    private int _BowAttack;
    private int _Hit;
    private int _BowHit;
    private int _Sp;
    private int _Str;
    private int _Dex;
    private int _Int;
    private int _Con;
    private int _Cha;
    private int _Wis;
    private int _Hp;
    private int _Mp;
    private int _Mr;
    private int _ReductionDmg;
    private int _Hpr;
    private int _Mpr;
    private int _hppotion;
    private int _exp;
    private int _ac;
    private int _weight;
    private int _regist_stun;
    private int _regist_stone;
    private int _regist_sleep;
    private int _regist_freeze;
    private int _regist_sustain;
    private int _regist_blind;
    private int _pvp;
    private int _bowpvp;
    
    static {
        _titleList = new HashMap();
        L1PcOther2._isStart = false;
        ADDNAME = new String[] { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
    }
    
    public L1PcOther2() {
        this._item = null;
        this._shopSkill = false;
        this._objid = 0;
        this._page = 0;
        this._TfCount = 0;
        this._TfCount1 = 0;
        this._Attack = 0;
        this._BowAttack = 0;
        this._Hit = 0;
        this._BowHit = 0;
        this._Sp = 0;
        this._Str = 0;
        this._Dex = 0;
        this._Int = 0;
        this._Con = 0;
        this._Cha = 0;
        this._Wis = 0;
        this._Hp = 0;
        this._Mp = 0;
        this._Mr = 0;
        this._ReductionDmg = 0;
        this._Hpr = 0;
        this._Mpr = 0;
        this._hppotion = 0;
        this._exp = 0;
        this._ac = 0;
        this._weight = 0;
        this._regist_stun = 0;
        this._regist_stone = 0;
        this._regist_sleep = 0;
        this._regist_freeze = 0;
        this._regist_sustain = 0;
        this._regist_blind = 0;
        this._pvp = 0;
        this._bowpvp = 0;
    }
    
    public static void load() {
        if (!L1PcOther2._isStart) {
            L1PcOther2._titleList.put(1, new StringBuilder(""));
            L1PcOther2._titleList.put(2, new StringBuilder("\\fD"));
            L1PcOther2._titleList.put(4, new StringBuilder("\\f="));
            L1PcOther2._titleList.put(8, new StringBuilder("\\fH"));
            L1PcOther2._titleList.put(16, new StringBuilder("\\f_"));
            L1PcOther2._titleList.put(32, new StringBuilder("\\f2"));
            L1PcOther2._titleList.put(64, new StringBuilder("\\fF"));
            L1PcOther2._titleList.put(128, new StringBuilder("\\fT"));
            L1PcOther2._titleList.put(256, new StringBuilder("\\fE"));
            L1PcOther2._titleList.put(512, new StringBuilder("\\f0"));
            L1PcOther2._titleList.put(1024, new StringBuilder("\\f?"));
            L1PcOther2._isStart = true;
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
    
    public void set_TfCount(int i) {
        this._TfCount = i;
    }
    
    public int get_TfCount() {
        return this._TfCount;
    }
    
    public void set_TfCount1(int i) {
        this._TfCount1 = i;
    }
    
    public int get_TfCount1() {
        return this._TfCount1;
    }
    
    public void set_Attack(int i) {
        this._Attack = i;
    }
    
    public int get_Attack() {
        return this._Attack;
    }
    
    public void add_Attack(int Attack) {
        this._Attack += Attack;
    }
    
    public void set_BowAttack(int i) {
        this._BowAttack = i;
    }
    
    public int get_BowAttack() {
        return this._BowAttack;
    }
    
    public void add_BowAttack(int BowAttack) {
        this._BowAttack += BowAttack;
    }
    
    public void set_Hit(int i) {
        this._Hit = i;
    }
    
    public int get_Hit() {
        return this._Hit;
    }
    
    public void add_Hit(int Hit) {
        this._Hit += Hit;
    }
    
    public void set_BowHit(int i) {
        this._BowHit = i;
    }
    
    public int get_BowHit() {
        return this._BowHit;
    }
    
    public void add_BowHit(int BowHit) {
        this._BowHit += BowHit;
    }
    
    public void set_Sp(int i) {
        this._Sp = i;
    }
    
    public int get_Sp() {
        return this._Sp;
    }
    
    public void add_Sp(int Sp) {
        this._Sp += Sp;
    }
    
    public void set_Str(int i) {
        this._Str = i;
    }
    
    public int get_Str() {
        return this._Str;
    }
    
    public void add_Str(int Str) {
        this._Str += Str;
    }
    
    public void set_Dex(int i) {
        this._Dex = i;
    }
    
    public int get_Dex() {
        return this._Dex;
    }
    
    public void add_Dex(int Dex) {
        this._Dex += Dex;
    }
    
    public void set_Int(int i) {
        this._Int = i;
    }
    
    public int get_Int() {
        return this._Int;
    }
    
    public void add_Int(int Int) {
        this._Int += Int;
    }
    
    public void set_Con(int i) {
        this._Con = i;
    }
    
    public int get_Con() {
        return this._Con;
    }
    
    public void add_Con(int Con) {
        this._Con += Con;
    }
    
    public void set_Cha(int i) {
        this._Cha = i;
    }
    
    public int get_Cha() {
        return this._Cha;
    }
    
    public void add_Cha(int Cha) {
        this._Cha += Cha;
    }
    
    public void set_Wis(int i) {
        this._Wis = i;
    }
    
    public int get_Wis() {
        return this._Wis;
    }
    
    public void add_Wis(int Wis) {
        this._Wis += Wis;
    }
    
    public void set_Hp(int i) {
        this._Hp = i;
    }
    
    public int get_Hp() {
        return this._Hp;
    }
    
    public void add_Hp(int Hp) {
        this._Hp += Hp;
    }
    
    public void set_Mp(int i) {
        this._Mp = i;
    }
    
    public int get_Mp() {
        return this._Mp;
    }
    
    public void add_Mp(int Mp) {
        this._Mp += Mp;
    }
    
    public void set_Mr(int i) {
        this._Mr = i;
    }
    
    public int get_Mr() {
        return this._Mr;
    }
    
    public void add_Mr(int Mr) {
        this._Mr += Mr;
    }
    
    public void set_ReductionDmg(int i) {
        this._ReductionDmg = i;
    }
    
    public int get_ReductionDmg() {
        return this._ReductionDmg;
    }
    
    public void add_ReductionDmg(int ReductionDmg) {
        this._ReductionDmg += ReductionDmg;
    }
    
    public void set_Hpr(int i) {
        this._Hpr = i;
    }
    
    public int get_Hpr() {
        return this._Hpr;
    }
    
    public void add_Hpr(int Hpr) {
        this._Hpr += Hpr;
    }
    
    public void set_Mpr(int i) {
        this._Mpr = i;
    }
    
    public int get_Mpr() {
        return this._Mpr;
    }
    
    public void add_Mpr(int Mpr) {
        this._Mpr += Mpr;
    }
    
    public void set_hppotion(int i) {
        this._hppotion = i;
    }
    
    public int get_hppotion() {
        return this._hppotion;
    }
    
    public void add_hppotion(int hppotion) {
        this._hppotion += hppotion;
    }
    
    public void set_exp(int i) {
        this._exp = i;
    }
    
    public int get_exp() {
        return this._exp;
    }
    
    public void add_exp(int exp) {
        this._exp += exp;
    }
    
    public void set_ac(int i) {
        this._ac = i;
    }
    
    public int get_ac() {
        return this._ac;
    }
    
    public void add_ac(int ac) {
        this._ac += ac;
    }
    
    public void set_weight(int i) {
        this._weight = i;
    }
    
    public int get_weight() {
        return this._weight;
    }
    
    public void add_weight(int weight) {
        this._weight += weight;
    }
    
    public void set_regist_stun(int i) {
        this._regist_stun = i;
    }
    
    public int get_regist_stun() {
        return this._regist_stun;
    }
    
    public void add_regist_stun(int regist_stun) {
        this._regist_stun += regist_stun;
    }
    
    public void set_regist_stone(int i) {
        this._regist_stone = i;
    }
    
    public int get_regist_stone() {
        return this._regist_stone;
    }
    
    public void add_regist_stone(int regist_stone) {
        this._regist_stone += regist_stone;
    }
    
    public void set_regist_sleep(int i) {
        this._regist_sleep = i;
    }
    
    public int get_regist_sleep() {
        return this._regist_sleep;
    }
    
    public void add_regist_sleep(int regist_sleep) {
        this._regist_sleep += regist_sleep;
    }
    
    public void set_regist_freeze(int i) {
        this._regist_freeze = i;
    }
    
    public int get_regist_freeze() {
        return this._regist_freeze;
    }
    
    public void add_regist_freeze(int regist_freeze) {
        this._regist_freeze += regist_freeze;
    }
    
    public void set_regist_sustain(int i) {
        this._regist_sustain = i;
    }
    
    public int get_regist_sustain() {
        return this._regist_sustain;
    }
    
    public void add_regist_sustain(int regist_sustain) {
        this._regist_sustain += regist_sustain;
    }
    
    public void set_regist_blind(int i) {
        this._regist_blind = i;
    }
    
    public int get_regist_blind() {
        return this._regist_blind;
    }
    
    public void add_regist_blind(int regist_blind) {
        this._regist_blind += regist_blind;
    }
    
    public void set_pvp(int i) {
        this._pvp = i;
    }
    
    public int get_pvp() {
        return this._pvp;
    }
    
    public void add_pvp(int pvp) {
        this._pvp += pvp;
    }
    
    public void set_bowpvp(int i) {
        this._bowpvp = i;
    }
    
    public int get_bowpvp() {
        return this._bowpvp;
    }
    
    public void add_bowpvp(int bowpvp) {
        this._bowpvp += bowpvp;
    }
}
