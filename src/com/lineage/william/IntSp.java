package com.lineage.william;

import com.lineage.server.model.Instance.L1PcInstance;

public class IntSp
{
    private int _IntSp;
    private int _Sp;
    private int _Mgs;
    private int _Mgs2;
    
    public IntSp(int IntSp, int Sp, int Mgs, int Mgs2) {
        this._IntSp = IntSp;
        this._Sp = Sp;
        this._Mgs = Mgs;
        this._Mgs2 = Mgs2;
    }
    
    public int getIntSp() {
        return this._IntSp;
    }
    
    public int getSp() {
        return this._Sp;
    }
    
    public int getMgs() {
        return this._Mgs;
    }
    
    public int getMgs2() {
        return this._Mgs2;
    }
    
    public static int getIntSpSkill(L1PcInstance pc, int pcint) {
        int dmg = 0;
        IntSp IntSpSkill = null;
        for (int i = pcint; i >= 0; i--) {
            IntSpSkill = PcIntSp.getInstance().getTemplate(i);
            if (IntSpSkill != null) {
                break;
            }
        }
        if (IntSpSkill != null && pcint >= IntSpSkill.getIntSp()) {
            dmg = IntSpSkill.getSp();
        }
        return dmg;
    }
    
    public static int getIntSpSkillMgs(L1PcInstance pc, int pcint) {
        int dmg = 0;
        IntSp IntSpSkill = null;
        for (int i = pcint; i >= 0; i--) {
            IntSpSkill = PcIntSp.getInstance().getTemplate(i);
            if (IntSpSkill != null) {
                break;
            }
        }
        if (IntSpSkill != null && pcint >= IntSpSkill.getIntSp()) {
            dmg = IntSpSkill.getMgs();
        }
        return dmg;
    }
    
    public static int getIntSpSkillMgs2(L1PcInstance pc, int pcint) {
        int dmg = 0;
        IntSp IntSpSkill = null;
        for (int i = pcint; i >= 0; i--) {
            IntSpSkill = PcIntSp.getInstance().getTemplate(i);
            if (IntSpSkill != null) {
                break;
            }
        }
        if (IntSpSkill != null && pcint >= IntSpSkill.getIntSp()) {
            dmg = IntSpSkill.getMgs2();
        }
        return dmg;
    }
}
