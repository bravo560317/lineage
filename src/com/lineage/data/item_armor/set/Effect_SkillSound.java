package com.lineage.data.item_armor.set;

import com.lineage.server.model.Instance.L1PcInstance;

public class Effect_SkillSound implements ArmorSetEffect
{
    int _ctril;
    int _ctrilchance;
    
    public Effect_SkillSound(int ctril, int ctrilchance) {
        this._ctril = ctril;
        this._ctrilchance = ctrilchance;
    }
    
    @Override
    public void giveEffect(L1PcInstance pc) {
        pc.startSustainEffect2(pc, this._ctril, this._ctrilchance);
    }
    
    @Override
    public void cancelEffect(L1PcInstance pc) {
        pc.stopSustainEffect2();
    }
    
    @Override
    public int get_mode() {
        return this._ctril;
    }
}
