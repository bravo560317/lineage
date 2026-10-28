package com.lineage.server.templates;

import java.util.HashMap;
import com.lineage.server.command.GmHtml;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Map;

public class L1PcOther
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
    private int _usemap;
    private int _usemapTime;
    private int _addhp;
    private int _addmp;
    private int _score;
    private int _color;
    private int _clanskill;
    private int _killCount;
    private int _deathCount;
    private GmHtml _gmHtml;
    private int _login_time;
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
    private int _PvP_dmg;
    private int _PvP_hit;
    private int _PvP_bow_dmg;
    private int _PvP_bow_hit;
    private int _PvP_dmg_r;
    private int _PvP_magic_r;
    private int _Ran_PvP_dmg_b;
    private Double _PvP_dmg_b;
    private int _Ran_PvP_magic_b;
    private Double _PvP_magic_b;
    
    static {
        _titleList = new HashMap();
        L1PcOther._isStart = false;
        ADDNAME = new String[] { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
    }
    
    public L1PcOther() {
        this._item = null;
        this._shopSkill = false;
        this._objid = 0;
        this._page = 0;
        this._usemap = 0;
        this._usemapTime = 0;
        this._addhp = 0;
        this._addmp = 0;
        this._score = 0;
        this._color = 1;
        this._clanskill = 0;
        this._killCount = 0;
        this._deathCount = 0;
        this._gmHtml = null;
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
        this._PvP_dmg = 0;
        this._PvP_hit = 0;
        this._PvP_bow_dmg = 0;
        this._PvP_bow_hit = 0;
        this._PvP_dmg_r = 0;
        this._PvP_magic_r = 0;
        this._Ran_PvP_dmg_b = 0;
        this._PvP_dmg_b = 0.0;
        this._Ran_PvP_magic_b = 0;
        this._PvP_magic_b = 0.0;
    }
    
    public static void load() {
        if (!L1PcOther._isStart) {
            L1PcOther._titleList.put(1, new StringBuilder(""));
            L1PcOther._titleList.put(2, new StringBuilder("\\fD"));
            L1PcOther._titleList.put(4, new StringBuilder("\\f="));
            L1PcOther._titleList.put(8, new StringBuilder("\\fH"));
            L1PcOther._titleList.put(16, new StringBuilder("\\f_"));
            L1PcOther._titleList.put(32, new StringBuilder("\\f2"));
            L1PcOther._titleList.put(64, new StringBuilder("\\fF"));
            L1PcOther._titleList.put(128, new StringBuilder("\\fT"));
            L1PcOther._titleList.put(256, new StringBuilder("\\fE"));
            L1PcOther._titleList.put(512, new StringBuilder("\\f0"));
            L1PcOther._titleList.put(1024, new StringBuilder("\\f?"));
            L1PcOther._isStart = true;
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
    
    public void set_usemap(int usemap) {
        this._usemap = usemap;
    }
    
    public int get_usemap() {
        return this._usemap;
    }
    
    public void set_usemapTime(int usemapTime) {
        this._usemapTime = usemapTime;
    }
    
    public int get_usemapTime() {
        return this._usemapTime;
    }
    
    public void set_addhp(int addhp) {
        if (addhp < 0) {
            addhp = 0;
        }
        this._addhp = addhp;
    }
    
    public int get_addhp() {
        return this._addhp;
    }
    
    public void set_addmp(int addmp) {
        if (addmp < 0) {
            addmp = 0;
        }
        this._addmp = addmp;
    }
    
    public int get_addmp() {
        return this._addmp;
    }
    
    public void set_score(int score) {
        if (score < 0) {
            score = 0;
        }
        this._score = score;
    }
    
    public void add_score(int score) {
        if (score < 0) {
            score = 0;
        }
        this._score += score;
        if (this._score < 0) {
            this._score = 0;
        }
    }
    
    public int get_score() {
        return this._score;
    }
    
    public void set_color(int color) {
        this._color = color;
    }
    
    public boolean set_color(int color, int tg) {
        this.set_score(tg);
        this._color = color;
        return true;
    }
    
    public int get_color() {
        return this._color;
    }
    
    public String color() {
        StringBuilder stringBuilder = L1PcOther._titleList.get((Integer)this._color);
        if (stringBuilder != null) {
            return stringBuilder.toString();
        }
        else {
            return "";
        }
    }
    
    public void set_clanskill(int clanskill) {
        this._clanskill = clanskill;
    }
    
    public int get_clanskill() {
        return this._clanskill;
    }
    
    public void add_killCount(int i) {
        this._killCount += i;
    }
    
    public void set_killCount(int i) {
        this._killCount = i;
    }
    
    public int get_killCount() {
        return this._killCount;
    }
    
    public void add_deathCount(int i) {
        this._deathCount += i;
    }
    
    public void set_deathCount(int i) {
        this._deathCount = i;
    }
    
    public int get_deathCount() {
        return this._deathCount;
    }
    
    public GmHtml get_gmHtml() {
        return this._gmHtml;
    }
    
    public void set_gmHtml(GmHtml gmHtml) {
        this._gmHtml = gmHtml;
    }
    
    public void set_login_time(int login_time) {
        this._login_time = login_time;
    }
    
    public int get_login_time() {
        return this._login_time;
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
    
    public void set_PvP_dmg(int i) {
        this._PvP_dmg = i;
    }
    
    public int get_PvP_dmg() {
        return this._PvP_dmg;
    }
    
    public void add_PvP_dmg(int PvP_dmg) {
        this._PvP_dmg += PvP_dmg;
    }
    
    public void set_PvP_hit(int i) {
        this._PvP_hit = i;
    }
    
    public int get_PvP_hit() {
        return this._PvP_hit;
    }
    
    public void add_PvP_hit(int PvP_hit) {
        this._PvP_hit += PvP_hit;
    }
    
    public void set_PvP_bow_dmg(int i) {
        this._PvP_bow_dmg = i;
    }
    
    public int get_PvP_bow_dmg() {
        return this._PvP_bow_dmg;
    }
    
    public void add_PvP_bow_dmg(int PvP_bow_dmg) {
        this._PvP_bow_dmg += PvP_bow_dmg;
    }
    
    public void set_PvP_bow_hit(int i) {
        this._PvP_bow_hit = i;
    }
    
    public int get_PvP_bow_hit() {
        return this._PvP_bow_hit;
    }
    
    public void add_PvP_bow_hit(int PvP_bow_hit) {
        this._PvP_bow_hit += PvP_bow_hit;
    }
    
    public void set_PvP_dmg_r(int i) {
        this._PvP_dmg_r = i;
    }
    
    public int get_PvP_dmg_r() {
        return this._PvP_dmg_r;
    }
    
    public void add_PvP_dmg_r(int PvP_dmg_r) {
        this._PvP_dmg_r += PvP_dmg_r;
    }
    
    public void set_PvP_magic_r(int i) {
        this._PvP_magic_r = i;
    }
    
    public int get_PvP_magic_r() {
        return this._PvP_magic_r;
    }
    
    public void add_PvP_magic_r(int PvP_magic_r) {
        this._PvP_magic_r += PvP_magic_r;
    }
    
    public void set_Ran_PvP_dmg_b(int i) {
        this._Ran_PvP_dmg_b = i;
    }
    
    public int get_Ran_PvP_dmg_b() {
        return this._Ran_PvP_dmg_b;
    }
    
    public void add_Ran_PvP_dmg_b(int Ran_PvP_dmg_b) {
        this._Ran_PvP_dmg_b += Ran_PvP_dmg_b;
    }
    
    public void set_Ran_PvP_magic_b(int i) {
        this._Ran_PvP_magic_b = i;
    }
    
    public int get_Ran_PvP_magic_b() {
        return this._Ran_PvP_magic_b;
    }
    
    public void add_Ran_PvP_magic_b(int Ran_PvP_magic_b) {
        this._Ran_PvP_magic_b += Ran_PvP_magic_b;
    }
    
    public double getPvP_dmg_b() {
        return this._PvP_dmg_b;
    }
    
    public void setPvP_dmg_b(double d) {
        this._PvP_dmg_b = d;
    }
    
    public void add_PvP_dmg_b(int PvP_dmg_b) {
        this._PvP_dmg_b = (Double)(this._PvP_dmg_b + (double)PvP_dmg_b);
    }
    
    public double getPvP_magic_b() {
        return this._PvP_magic_b;
    }
    
    public void setPvP_magic_b(double d) {
        this._PvP_magic_b = d;
    }
    
    public void add_PvP_magic_b(int PvP_magic_b) {
        this._PvP_magic_b = (Double)(this._PvP_magic_b + (double)PvP_magic_b);
    }
}
