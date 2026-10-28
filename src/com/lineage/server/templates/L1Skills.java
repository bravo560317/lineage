package com.lineage.server.templates;

public class L1Skills
{
    public static final int ATTR_NONE = 0;
    public static final int ATTR_EARTH = 1;
    public static final int ATTR_FIRE = 2;
    public static final int ATTR_WATER = 4;
    public static final int ATTR_WIND = 8;
    public static final int ATTR_RAY = 16;
    public static final int TYPE_PROBABILITY = 1;
    public static final int TYPE_CHANGE = 2;
    public static final int TYPE_CURSE = 4;
    public static final int TYPE_DEATH = 8;
    public static final int TYPE_HEAL = 16;
    public static final int TYPE_RESTORE = 32;
    public static final int TYPE_ATTACK = 64;
    public static final int TYPE_OTHER = 128;
    public static final int TARGET_TO_ME = 0;
    public static final int TARGET_TO_PC = 1;
    public static final int TARGET_TO_NPC = 2;
    public static final int TARGET_TO_CLAN = 4;
    public static final int TARGET_TO_PARTY = 8;
    public static final int TARGET_TO_PET = 16;
    public static final int TARGET_TO_PLACE = 32;
    private int _skillId;
    private String _name;
    private int _skillLevel;
    private int _skillNumber;
    private int _mpConsume;
    private int _hpConsume;
    private int _itmeConsumeId;
    private int _itmeConsumeCount;
    private int _reuseDelay;
    private int _buffDuration;
    private String _target;
    private int _targetTo;
    private int _damageValue;
    private int _damageDice;
    private int _damageDiceCount;
    private int _probabilityValue;
    private int _probabilityDice;
    private int _attr;
    private int _type;
    private int _lawful;
    private int _ranged;
    private int _area;
    boolean _isThrough;
    private int _id;
    private String _nameId;
    private int _actionId;
    private int _castGfx;
    private int _castGfx2;
    private int _sysmsgIdHappen;
    private int _sysmsgIdStop;
    private int _sysmsgIdFail;
    private int type;
    private int typestun;
    private int type1;
    private int type2;
    private int type3;
    private int type4;
    private int type5;
    private int type6;
    private int type7;
    private int type8;
    private int type9;
    private int type10;
    private int type11;
    private int type12;
    private int type13;
    private int type14;
    private int type15;
    private int type16;
    private int type17;
    private int type18;
    private int type19;
    private int type20;
    private int type21;
    private int type22;
    private int type23;
    private int type24;
    private int type25;
    private int type26;
    private int type27;
    private int type28;
    private int detect_no_delay;
    
    public int getSkillId() {
        return this._skillId;
    }
    
    public void setSkillId(int i) {
        this._skillId = i;
    }
    
    public String getName() {
        return this._name;
    }
    
    public void setName(String s) {
        this._name = s;
    }
    
    public int getSkillLevel() {
        return this._skillLevel;
    }
    
    public void setSkillLevel(int i) {
        this._skillLevel = i;
    }
    
    public int getSkillNumber() {
        return this._skillNumber;
    }
    
    public void setSkillNumber(int i) {
        this._skillNumber = i;
    }
    
    public int getMpConsume() {
        return this._mpConsume;
    }
    
    public void setMpConsume(int i) {
        this._mpConsume = i;
    }
    
    public int getHpConsume() {
        return this._hpConsume;
    }
    
    public void setHpConsume(int i) {
        this._hpConsume = i;
    }
    
    public int getItemConsumeId() {
        return this._itmeConsumeId;
    }
    
    public void setItemConsumeId(int i) {
        this._itmeConsumeId = i;
    }
    
    public int getItemConsumeCount() {
        return this._itmeConsumeCount;
    }
    
    public void setItemConsumeCount(int i) {
        this._itmeConsumeCount = i;
    }
    
    public int getReuseDelay() {
        return this._reuseDelay;
    }
    
    public void setReuseDelay(int i) {
        this._reuseDelay = i;
    }
    
    public int getBuffDuration() {
        return this._buffDuration;
    }
    
    public void setBuffDuration(int i) {
        this._buffDuration = i;
    }
    
    public String getTarget() {
        return this._target;
    }
    
    public void setTarget(String s) {
        this._target = s;
    }
    
    public int getTargetTo() {
        return this._targetTo;
    }
    
    public void setTargetTo(int i) {
        this._targetTo = i;
    }
    
    public int getDamageValue() {
        return this._damageValue;
    }
    
    public void setDamageValue(int i) {
        this._damageValue = i;
    }
    
    public int getDamageDice() {
        return this._damageDice;
    }
    
    public void setDamageDice(int i) {
        this._damageDice = i;
    }
    
    public int getDamageDiceCount() {
        return this._damageDiceCount;
    }
    
    public void setDamageDiceCount(int i) {
        this._damageDiceCount = i;
    }
    
    public int getProbabilityValue() {
        return this._probabilityValue;
    }
    
    public void setProbabilityValue(int i) {
        this._probabilityValue = i;
    }
    
    public int getProbabilityDice() {
        return this._probabilityDice;
    }
    
    public void setProbabilityDice(int i) {
        this._probabilityDice = i;
    }
    
    public int getAttr() {
        return this._attr;
    }
    
    public void setAttr(int i) {
        this._attr = i;
    }
    
    public int getType() {
        return this._type;
    }
    
    public void setType(int i) {
        this._type = i;
    }
    
    public int getLawful() {
        return this._lawful;
    }
    
    public void setLawful(int i) {
        this._lawful = i;
    }
    
    public int getRanged() {
        return this._ranged;
    }
    
    public void setRanged(int i) {
        this._ranged = i;
    }
    
    public int getArea() {
        return this._area;
    }
    
    public void setArea(int i) {
        this._area = i;
    }
    
    public boolean isThrough() {
        return this._isThrough;
    }
    
    public void setThrough(boolean flag) {
        this._isThrough = flag;
    }
    
    public int getId() {
        return this._id;
    }
    
    public void setId(int i) {
        this._id = i;
    }
    
    public String getNameId() {
        return this._nameId;
    }
    
    public void setNameId(String s) {
        this._nameId = s;
    }
    
    public int getActionId() {
        return this._actionId;
    }
    
    public void setActionId(int i) {
        this._actionId = i;
    }
    
    public int getCastGfx() {
        return this._castGfx;
    }
    
    public void setCastGfx(int i) {
        this._castGfx = i;
    }
    
    public int getCastGfx2() {
        return this._castGfx2;
    }
    
    public void setCastGfx2(int i) {
        this._castGfx2 = i;
    }
    
    public int getSysmsgIdHappen() {
        return this._sysmsgIdHappen;
    }
    
    public void setSysmsgIdHappen(int i) {
        this._sysmsgIdHappen = i;
    }
    
    public int getSysmsgIdStop() {
        return this._sysmsgIdStop;
    }
    
    public void setSysmsgIdStop(int i) {
        this._sysmsgIdStop = i;
    }
    
    public int getSysmsgIdFail() {
        return this._sysmsgIdFail;
    }
    
    public void setSysmsgIdFail(int i) {
        this._sysmsgIdFail = i;
    }
    
    public int gettype1() {
        return this.type1;
    }
    
    public void settype1(int i) {
        this.type1 = i;
    }
    
    public int gettype2() {
        return this.type2;
    }
    
    public void settype2(int i) {
        this.type2 = i;
    }
    
    public int gettype3() {
        return this.type3;
    }
    
    public void settype3(int i) {
        this.type3 = i;
    }
    
    public int gettype4() {
        return this.type4;
    }
    
    public void settype4(int i) {
        this.type4 = i;
    }
    
    public int gettype5() {
        return this.type5;
    }
    
    public void settype5(int i) {
        this.type5 = i;
    }
    
    public int gettype6() {
        return this.type6;
    }
    
    public void settype6(int i) {
        this.type6 = i;
    }
    
    public int gettype7() {
        return this.type7;
    }
    
    public void settype7(int i) {
        this.type7 = i;
    }
    
    public int gettype8() {
        return this.type8;
    }
    
    public void settype8(int i) {
        this.type8 = i;
    }
    
    public int gettype9() {
        return this.type9;
    }
    
    public void settype9(int i) {
        this.type9 = i;
    }
    
    public int gettype10() {
        return this.type10;
    }
    
    public void settype10(int i) {
        this.type10 = i;
    }
    
    public int gettype11() {
        return this.type11;
    }
    
    public void settype11(int i) {
        this.type11 = i;
    }
    
    public int gettype12() {
        return this.type12;
    }
    
    public void settype12(int i) {
        this.type12 = i;
    }
    
    public int gettype13() {
        return this.type13;
    }
    
    public void settype13(int i) {
        this.type13 = i;
    }
    
    public int gettype14() {
        return this.type14;
    }
    
    public void settype14(int i) {
        this.type14 = i;
    }
    
    public int gettype15() {
        return this.type15;
    }
    
    public void settype15(int i) {
        this.type15 = i;
    }
    
    public int gettype16() {
        return this.type16;
    }
    
    public void settype16(int i) {
        this.type16 = i;
    }
    
    public int gettype17() {
        return this.type17;
    }
    
    public void settype17(int i) {
        this.type17 = i;
    }
    
    public int gettype18() {
        return this.type18;
    }
    
    public void settype18(int i) {
        this.type18 = i;
    }
    
    public int gettype19() {
        return this.type19;
    }
    
    public void settype19(int i) {
        this.type19 = i;
    }
    
    public int gettype20() {
        return this.type20;
    }
    
    public void settype20(int i) {
        this.type20 = i;
    }
    
    public int gettype21() {
        return this.type21;
    }
    
    public void settype21(int i) {
        this.type21 = i;
    }
    
    public int gettype22() {
        return this.type22;
    }
    
    public void settype22(int i) {
        this.type22 = i;
    }
    
    public int gettype23() {
        return this.type23;
    }
    
    public void settype23(int i) {
        this.type23 = i;
    }
    
    public int gettype24() {
        return this.type24;
    }
    
    public void settype24(int i) {
        this.type24 = i;
    }
    
    public int gettype25() {
        return this.type25;
    }
    
    public void settype25(int i) {
        this.type25 = i;
    }
    
    public int gettype26() {
        return this.type26;
    }
    
    public void settype26(int i) {
        this.type26 = i;
    }
    
    public int gettype27() {
        return this.type27;
    }
    
    public void settype27(int i) {
        this.type27 = i;
    }
    
    public int gettype28() {
        return this.type28;
    }
    
    public void settype28(int i) {
        this.type28 = i;
    }
    
    public int getdetect_no_delay() {
        return this.detect_no_delay;
    }
    
    public void setdetect_no_delay(int i) {
        this.detect_no_delay = i;
    }
}
