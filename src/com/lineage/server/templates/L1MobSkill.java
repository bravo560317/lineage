package com.lineage.server.templates;

public class L1MobSkill implements Cloneable
{
    public static final int TYPE_NONE = 0;
    public static final int TYPE_PHYSICAL_ATTACK = 1;
    public static final int TYPE_MAGIC_ATTACK = 2;
    public static final int TYPE_SUMMON = 3;
    public static final int TYPE_POLY = 4;
    public static final int AHTHARAS_1 = 5;
    public static final int AHTHARAS_2 = 6;
    public static final int AHTHARAS_3 = 7;
    public static final int CHANGE_TARGET_NO = 0;
    public static final int CHANGE_TARGET_ME = 2;
    public static final int CHANGE_TARGET_RANDOM = 3;
    private final int skillSize;
    private boolean[] isSkillDelayIdx;
    private int mobid;
    private String mobName;
    private int[] type;
    private int[] triRnd;
    int[] triHp;
    int[] triCompanionHp;
    int[] triRange;
    int[] triCount;
    int[] changeTarget;
    int[] range;
    int[] areaWidth;
    int[] areaHeight;
    int[] leverage;
    int[] skillId;
    int[] gfxid;
    int[] actid;
    int[] summon;
    int[] summonMin;
    int[] summonMax;
    int[] polyId;
    int[] reuseDelay;
    
    public L1MobSkill clone() {
        try {
            return (L1MobSkill)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
    }
    
    public int getSkillSize() {
        return this.skillSize;
    }
    
    public L1MobSkill(int sSize) {
        this.skillSize = sSize;
        this.type = new int[this.skillSize];
        this.triRnd = new int[this.skillSize];
        this.triHp = new int[this.skillSize];
        this.triCompanionHp = new int[this.skillSize];
        this.triRange = new int[this.skillSize];
        this.triCount = new int[this.skillSize];
        this.changeTarget = new int[this.skillSize];
        this.range = new int[this.skillSize];
        this.areaWidth = new int[this.skillSize];
        this.areaHeight = new int[this.skillSize];
        this.leverage = new int[this.skillSize];
        this.skillId = new int[this.skillSize];
        this.gfxid = new int[this.skillSize];
        this.actid = new int[this.skillSize];
        this.summon = new int[this.skillSize];
        this.summonMin = new int[this.skillSize];
        this.summonMax = new int[this.skillSize];
        this.polyId = new int[this.skillSize];
        this.reuseDelay = new int[this.skillSize];
        this.isSkillDelayIdx = new boolean[this.skillSize];
    }
    
    public void setSkillDelayIdx(int idx, boolean flag) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.isSkillDelayIdx[idx] = flag;
            return;
        }
    }
    
    public boolean isSkillDelayIdx(int idx) {
        return idx >= 0 && idx < this.getSkillSize() && this.isSkillDelayIdx[idx];
    }
    
    public int get_mobid() {
        return this.mobid;
    }
    
    public void set_mobid(int i) {
        this.mobid = i;
    }
    
    public String getMobName() {
        return this.mobName;
    }
    
    public void setMobName(String s) {
        this.mobName = s;
    }
    
    public int getType(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.type[idx];
        }
    }
    
    public void setType(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.type[idx] = i;
            return;
        }
    }
    
    public int getTriggerRandom(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.triRnd[idx];
        }
    }
    
    public void setTriggerRandom(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.triRnd[idx] = i;
            return;
        }
    }
    
    public int getTriggerHp(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.triHp[idx];
        }
    }
    
    public void setTriggerHp(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.triHp[idx] = i;
            return;
        }
    }
    
    public int getTriggerCompanionHp(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.triCompanionHp[idx];
        }
    }
    
    public void setTriggerCompanionHp(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.triCompanionHp[idx] = i;
            return;
        }
    }
    
    public int getTriggerRange(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            Math.abs(idx);
            return this.triRange[idx];
        }
    }
    
    public void setTriggerRange(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.triRange[idx] = i;
            return;
        }
    }
    
    public boolean isTriggerDistance(int idx, int distance) {
        int triggerRange = this.getTriggerRange(idx);
        return (triggerRange < 0 && distance <= Math.abs(triggerRange)) || (triggerRange > 0 && distance >= triggerRange);
    }
    
    public int getTriggerCount(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.triCount[idx];
        }
    }
    
    public void setTriggerCount(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.triCount[idx] = i;
            return;
        }
    }
    
    public int getChangeTarget(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.changeTarget[idx];
        }
    }
    
    public void setChangeTarget(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.changeTarget[idx] = i;
            return;
        }
    }
    
    public int getRange(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.range[idx];
        }
    }
    
    public void setRange(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.range[idx] = i;
            return;
        }
    }
    
    public int getAreaWidth(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.areaWidth[idx];
        }
    }
    
    public void setAreaWidth(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.areaWidth[idx] = i;
            return;
        }
    }
    
    public int getAreaHeight(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.areaHeight[idx];
        }
    }
    
    public void setAreaHeight(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.areaHeight[idx] = i;
            return;
        }
    }
    
    public int getLeverage(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.leverage[idx];
        }
    }
    
    public void setLeverage(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.leverage[idx] = i;
            return;
        }
    }
    
    public int getSkillId(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.skillId[idx];
        }
    }
    
    public void setSkillId(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.skillId[idx] = i;
            return;
        }
    }
    
    public int getGfxid(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.gfxid[idx];
        }
    }
    
    public void setGfxid(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.gfxid[idx] = i;
            return;
        }
    }
    
    public int getActid(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.actid[idx];
        }
    }
    
    public void setActid(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.actid[idx] = i;
            return;
        }
    }
    
    public int getSummon(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.summon[idx];
        }
    }
    
    public void setSummon(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.summon[idx] = i;
            return;
        }
    }
    
    public int getSummonMin(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.summonMin[idx];
        }
    }
    
    public void setSummonMin(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.summonMin[idx] = i;
            return;
        }
    }
    
    public int getSummonMax(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.summonMax[idx];
        }
    }
    
    public void setSummonMax(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.summonMax[idx] = i;
            return;
        }
    }
    
    public int getPolyId(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.polyId[idx];
        }
    }
    
    public void setPolyId(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.polyId[idx] = i;
            return;
        }
    }
    
    public int getReuseDelay(int idx) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return 0;
        }
        else {
            return this.reuseDelay[idx];
        }
    }
    
    public void setReuseDelay(int idx, int i) {
        if (idx < 0 || idx >= this.getSkillSize()) {
            return;
        }
        else {
            this.reuseDelay[idx] = i;
            return;
        }
    }
}
