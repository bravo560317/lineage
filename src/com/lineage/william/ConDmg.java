package com.lineage.william;

import com.lineage.server.model.Instance.L1PcInstance;

public class ConDmg
{
    private int _ConDmg;
    private int _AddDmgR;
    private int _AddHP;
    
    public ConDmg(int ConDmg, int AddDmgR, int AddHP) {
        this._ConDmg = ConDmg;
        this._AddDmgR = AddDmgR;
        this._AddHP = AddHP;
    }
    
    public int getConDmg() {
        return this._ConDmg;
    }
    
    public int getAddDmgR() {
        return this._AddDmgR;
    }
    
    public int getAddHP() {
        return this._AddHP;
    }
    
    public static int getConDmgSkill(L1PcInstance pc, int Con) {
        int dmg = 0;
        ConDmg ConDmgSkill = null;
        for (int i = Con; i >= 0; i--) {
            ConDmgSkill = PcCon.getInstance().getTemplate(i);
            if (ConDmgSkill != null) {
                break;
            }
        }
        if (ConDmgSkill != null && Con >= ConDmgSkill.getConDmg()) {
            dmg = ConDmgSkill.getAddDmgR();
        }
        return dmg;
    }
    
    public static int getConHitSkill(L1PcInstance pc, int Con) {
        int hit = 0;
        ConDmg ConDmgSkill = null;
        for (int i = Con; i >= 0; i--) {
            ConDmgSkill = PcCon.getInstance().getTemplate(i);
            if (ConDmgSkill != null) {
                break;
            }
        }
        if (ConDmgSkill != null && Con >= ConDmgSkill.getConDmg()) {
            hit = ConDmgSkill.getAddHP();
        }
        return hit;
    }
}
