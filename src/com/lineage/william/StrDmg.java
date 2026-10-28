package com.lineage.william;

import com.lineage.server.model.Instance.L1PcInstance;

public class StrDmg
{
    private int _StrDmg;
    private int _AddDmg;
    private int _AddHit;
    
    public StrDmg(int StrDmg, int AddDmg, int AddHit) {
        this._StrDmg = StrDmg;
        this._AddDmg = AddDmg;
        this._AddHit = AddHit;
    }
    
    public int getStrDmg() {
        return this._StrDmg;
    }
    
    public int getAddDmg() {
        return this._AddDmg;
    }
    
    public int getAddHit() {
        return this._AddHit;
    }
    
    public static int getStrDmgSkill(L1PcInstance pc, int str) {
        int dmg = 0;
        StrDmg StrDmgSkill = null;
        for (int i = str; i >= 0; i--) {
            StrDmgSkill = PcStrDmg.getInstance().getTemplate(i);
            if (StrDmgSkill != null) {
                break;
            }
        }
        if (StrDmgSkill != null && str >= StrDmgSkill.getStrDmg()) {
            dmg = StrDmgSkill.getAddDmg();
        }
        return dmg;
    }
    
    public static int getStrHitSkill(L1PcInstance pc, int str) {
        int hit = 0;
        StrDmg StrDmgSkill = null;
        for (int i = str; i >= 0; i--) {
            StrDmgSkill = PcStrDmg.getInstance().getTemplate(i);
            if (StrDmgSkill != null) {
                break;
            }
        }
        if (StrDmgSkill != null && str >= StrDmgSkill.getStrDmg()) {
            hit = StrDmgSkill.getAddHit();
        }
        return hit;
    }
}
