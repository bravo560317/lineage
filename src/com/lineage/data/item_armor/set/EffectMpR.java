package com.lineage.data.item_armor.set;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;

public class EffectMpR implements ArmorSetEffect
{
    private final int _add;
    
    public EffectMpR(int add) {
        this._add = add;
    }
    
    @Override
    public void giveEffect(L1PcInstance pc) {
        pc.addMpr(this._add);
        if (pc.getarmor_setgive()) {
            String type = "";
            if (this._add > 0) {
                type = "+";
            }
            pc.sendPackets(new S_SystemMessage("套裝效果[回魔]:" + type + this._add));
        }
    }
    
    @Override
    public void cancelEffect(L1PcInstance pc) {
        pc.addMpr(-this._add);
        if (!pc.getarmor_setgive()) {
            if (this._add < 0) {
                pc.sendPackets(new S_SystemMessage("移除套裝效果[回魔]:+" + -this._add));
            }
            else {
                pc.sendPackets(new S_SystemMessage("移除套裝效果[回魔]:+" + this._add));
            }
        }
    }
    
    @Override
    public int get_mode() {
        return this._add;
    }
}
