package com.lineage.william;

import com.lineage.server.model.Instance.L1NpcInstance;

public class MapNpcDmg
{
    private short _StrDmg;
    private double _AddDuble;
    
    public MapNpcDmg(short StrDmg, double addDuble) {
        this._StrDmg = StrDmg;
        this._AddDuble = addDuble;
    }
    
    public short getStrDmg() {
        return this._StrDmg;
    }
    
    public double getAddDuble() {
        return this._AddDuble;
    }
    
    public static double getStrDmgSkill(L1NpcInstance npc, short map) {
        double dmg = 0.0;
        MapNpcDmg StrDmgSkill = PcMapNpcmdg.getInstance().getTemplate((int)map);
        if (StrDmgSkill != null && map == StrDmgSkill.getStrDmg()) {
            dmg = StrDmgSkill.getAddDuble();
        }
        return dmg;
    }
}
