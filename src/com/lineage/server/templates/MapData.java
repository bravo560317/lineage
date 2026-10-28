package com.lineage.server.templates;

public class MapData
{
    public int mapId;
    public int startX;
    public int endX;
    public int startY;
    public int endY;
    public double monster_amount;
    public double dropRate;
    public boolean isUnderwater;
    public boolean markable;
    public boolean teleportable;
    public boolean escapable;
    public boolean isUseResurrection;
    public boolean isUsePainwand;
    public boolean isEnabledDeathPenalty;
    public boolean isTakePets;
    public boolean isRecallPets;
    public boolean isUsableItem;
    public boolean isUsableSkill;
    public boolean isGuaji;
    public boolean isClanPc;
    public boolean isPartyPc;
    public boolean isdropitem;
    public int isUsableShop;
    public int isStart_time;
    public int isEnd_time;
    public int isStart_time2;
    public int isEnd_time2;
    public int isweek;
    public int maptime;
    public String location;
    public int CopyMapId;
    public int EXP;
    
    public MapData() {
        this.mapId = 0;
        this.startX = 0;
        this.endX = 0;
        this.startY = 0;
        this.endY = 0;
        this.monster_amount = 1.0;
        this.dropRate = 1.0;
        this.isUnderwater = false;
        this.markable = false;
        this.teleportable = false;
        this.escapable = false;
        this.isUseResurrection = false;
        this.isUsePainwand = false;
        this.isEnabledDeathPenalty = false;
        this.isTakePets = false;
        this.isRecallPets = false;
        this.isUsableItem = false;
        this.isUsableSkill = false;
        this.isGuaji = false;
        this.isClanPc = false;
        this.isPartyPc = false;
        this.isdropitem = false;
        this.maptime = 0;
        this.CopyMapId = -1;
        this.EXP = 0;
    }
}
