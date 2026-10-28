package com.lineage.server.model.Instance;

/**
 * 怪物強化屬性樣板
 */
public class L1MonsterEnhanceInstance {

    private int _npcId;
    private int _currentDc;
    private int _dcEnhance;
    private int _dcEnhance1;
    private int _dcEnhance2;

    private int _level; // 每次強化增加等級(%)
    private int _hp; // 每次強化增加血量(%)
    private int _mp; // 每次強化增加魔力(%)
    private int _ac; // 每次強化增加防禦(%)
    private int _str; // 每次強化增加力量(%)
    private int _dex; // 每次強化增加敏捷(%)
    private int _con; // 每次強化增加體質(%)
    private int _wis; // 每次強化增加精神(%)
    private int _int; // 每次強化增加智力(%)
    private int _mr; // 每次強化增加魔防(%)
    private int _hpr; // 每次強化增加回血(%)
    private int _damageReduction; // 每次強化增加傷害減免(%)

    // 儲存原始數值 (用於百分比計算的基礎)
    private int _originalLevel;
    private int _originalHp;
    private int _originalMp;
    private int _originalAc;
    private int _originalStr;
    private int _originalDex;
    private int _originalCon;
    private int _originalWis;
    private int _originalInt;
    private int _originalMr;
    private int _originalHpr;
    private int _originalDamageReduction;

    public int getNpcId() {
        return _npcId;
    }

    public void setNpcId(int npcId) {
        _npcId = npcId;
    }

    public int getCurrentDc() {
        return _currentDc;
    }

    public void setCurrentDc(int currentDc) {
        _currentDc = currentDc;
    }

    public int getDcEnhance() {
        return _dcEnhance;
    }

    public void setDcEnhance(int dcEnhance) {
        _dcEnhance = dcEnhance;
    }

    public int getDcEnhance1() {
        return _dcEnhance1;
    }

    public void setDcEnhance1(int dcEnhance1) {
        _dcEnhance1 = dcEnhance1;
    }

    public int getDcEnhance2() {
        return _dcEnhance2;
    }

    public void setDcEnhance2(int dcEnhance2) {
        _dcEnhance2 = dcEnhance2;
    }

    public int getLevel() {
        return _level;
    }

    public void setLevel(int level) {
        _level = level;
    }

    public int getHp() {
        return _hp;
    }

    public void setHp(int hp) {
        _hp = hp;
    }

    public int getMp() {
        return _mp;
    }

    public void setMp(int mp) {
        _mp = mp;
    }

    public int getAc() {
        return _ac;
    }

    public void setAc(int ac) {
        _ac = ac;
    }

    public int getStr() {
        return _str;
    }

    public void setStr(int str) {
        _str = str;
    }

    public int getDex() {
        return _dex;
    }

    public void setDex(int dex) {
        _dex = dex;
    }

    public int getCon() {
        return _con;
    }

    public void setCon(int con) {
        _con = con;
    }

    public int getWis() {
        return _wis;
    }

    public void setWis(int wis) {
        _wis = wis;
    }

    public int getInt() {
        return _int;
    }

    public void setInt(int i) {
        _int = i;
    }

    public int getMr() {
        return _mr;
    }

    public void setMr(int mr) {
        _mr = mr;
    }

    public int getHpr() {
        return _hpr;
    }

    public void setHpr(int hpr) {
        _hpr = hpr;
    }

    public int getDamageReduction() {
        return _damageReduction;
    }

    public void setDamageReduction(int damageReduction) {
        _damageReduction = damageReduction;
    }

    // --- 原始數值 Getters/Setters (Original Stats for Percentage Calculation) ---
    public int getOriginalLevel() { return _originalLevel; }
    public void setOriginalLevel(int i) { _originalLevel = i; }
    public int getOriginalHp() { return _originalHp; }
    public void setOriginalHp(int i) { _originalHp = i; }
    public int getOriginalMp() { return _originalMp; }
    public void setOriginalMp(int i) { _originalMp = i; }
    public int getOriginalAc() { return _originalAc; }
    public void setOriginalAc(int i) { _originalAc = i; }
    public int getOriginalStr() { return _originalStr; }
    public void setOriginalStr(int i) { _originalStr = i; }
    public int getOriginalDex() { return _originalDex; }
    public void setOriginalDex(int i) { _originalDex = i; }
    public int getOriginalCon() { return _originalCon; }
    public void setOriginalCon(int i) { _originalCon = i; }
    public int getOriginalWis() { return _originalWis; }
    public void setOriginalWis(int i) { _originalWis = i; }
    public int getOriginalInt() { return _originalInt; }
    public void setOriginalInt(int i) { _originalInt = i; }
    public int getOriginalMr() { return _originalMr; }
    public void setOriginalMr(int i) { _originalMr = i; }
    public int getOriginalHpr() { return _originalHpr; }
    public void setOriginalHpr(int i) { _originalHpr = i; }
    public int getOriginalDamageReduction() { return _originalDamageReduction; }
    public void setOriginalDamageReduction(int i) { _originalDamageReduction = i; }
}