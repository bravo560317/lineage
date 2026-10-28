package com.lineage.data.item_armor.set;

import com.lineage.server.model.Instance.L1PcInstance;

public class EffectRegist_Stun implements ArmorSetEffect
{
    private final int _add;
    
    public EffectRegist_Stun(int add) {
        this._add = add;
    }
    
    @Override
    public void giveEffect(L1PcInstance pc) {
        pc.addRegistStun(this._add);
    }
    
    @Override
    public void cancelEffect(L1PcInstance pc) {
        pc.addRegistStun(-this._add);
    }
    
    @Override
    public int get_mode() {
        return this._add;
    }
}
