package com.lineage.data.item_armor.set;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;

public class EffectDefenseWater implements ArmorSetEffect
{
    private final int _add;
    
    public EffectDefenseWater(int add) {
        this._add = add;
    }
    
    @Override
    public void giveEffect(L1PcInstance pc) {
        pc.addWater(this._add);
        if (pc.getarmor_setgive()) {
            pc.sendPackets(new S_SystemMessage("套裝效果[水屬性]:+" + this._add));
        }
    }
    
    @Override
    public void cancelEffect(L1PcInstance pc) {
        pc.addWater(-this._add);
        if (pc.getarmor_setgive()) {
            pc.sendPackets(new S_SystemMessage("移除套裝效果[水屬性]:-" + this._add));
        }
    }
    
    @Override
    public int get_mode() {
        return this._add;
    }
}
