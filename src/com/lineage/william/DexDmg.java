package com.lineage.william;

import com.lineage.server.model.Instance.L1PcInstance;

public class DexDmg
{
    private int _DexDmg;
    private int _AddDmg;
    private int _AddHit;
    
    public DexDmg(int DexDmg, int AddDmg, int AddHit) {
        this._DexDmg = DexDmg;
        this._AddDmg = AddDmg;
        this._AddHit = AddHit;
    }
    
    public int getDexDmg() {
        return this._DexDmg;
    }
    
    public int getAddDmg() {
        return this._AddDmg;
    }
    
    public int getAddHit() {
        return this._AddHit;
    }
    
    public static int getDexDmgSkill(L1PcInstance pc, int dex) {
        int dmg = 0;
        DexDmg DexDmgSkill = null;
        for (int i = dex; i >= 0; i--) {
            DexDmgSkill = PcDexDmg.getInstance().getTemplate(i);
            if (DexDmgSkill != null) {
                break;
            }
        }
        if (DexDmgSkill != null && dex >= DexDmgSkill.getDexDmg()) {
            dmg = DexDmgSkill.getAddDmg();
        }
        return dmg;
    }
    
    public static int getDexHitSkill(L1PcInstance pc, int dex) {
        int hit = 0;
        DexDmg DexDmgSkill = null;
        for (int i = dex; i >= 0; i--) {
            DexDmgSkill = PcDexDmg.getInstance().getTemplate(i);
            if (DexDmgSkill != null) {
                break;
            }
        }
        if (DexDmgSkill != null && dex >= DexDmgSkill.getDexDmg()) {
            hit = DexDmgSkill.getAddHit();
        }
        return hit;
    }
}
