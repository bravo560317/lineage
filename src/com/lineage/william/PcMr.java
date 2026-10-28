package com.lineage.william;

public class PcMr
{
    private int _IntSp;
    private int _Sp;
    private int _Mgs;
    
    public PcMr(int IntSp, int Sp, int Mgs) {
        this._IntSp = IntSp;
        this._Sp = Sp;
        this._Mgs = Mgs;
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
    
    public static int getIntSpSkill(int pcint) {
        int dmg = 0;
        PcMr IntSpSkill = null;
        for (int i = pcint; i >= 0; i--) {
            IntSpSkill = PcMrDmg.getInstance().getTemplate(i);
            if (IntSpSkill != null) {
                break;
            }
        }
        if (IntSpSkill != null && pcint >= IntSpSkill.getIntSp()) {
            dmg = IntSpSkill.getSp();
        }
        return dmg;
    }
    
    public static int getIntSpSkillMgs(int pcint) {
        int dmg = 0;
        PcMr IntSpSkill = null;
        for (int i = pcint; i >= 0; i--) {
            IntSpSkill = PcMrDmg.getInstance().getTemplate(i);
            if (IntSpSkill != null) {
                break;
            }
        }
        if (IntSpSkill != null && pcint >= IntSpSkill.getIntSp()) {
            dmg = IntSpSkill.getMgs();
        }
        return dmg;
    }
}
