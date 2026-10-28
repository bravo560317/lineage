package com.lineage.data.item_armor.set;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.model.Instance.L1PcInstance;

public class EffectHaste implements ArmorSetEffect
{
    private final int _add;
    
    public EffectHaste(int add) {
        this._add = add;
    }
    
    @Override
    public void giveEffect(L1PcInstance pc) {
        pc.addHasteItemEquipped(this._add);
        pc.removeHasteSkillEffect();
        pc.sendPackets(new S_SkillHaste(pc.getId(), 1, -1));
        if (pc.getMoveSpeed() != 1) {
            pc.setMoveSpeed(1);
            pc.broadcastPacketAll(new S_SkillHaste(pc.getId(), 1, 0));
        }
        if (pc.getarmor_setgive()) {
            pc.sendPackets(new S_SystemMessage("套裝效果[綠水狀態]"));
        }
    }
    
    @Override
    public void cancelEffect(L1PcInstance pc) {
        pc.addHasteItemEquipped(-this._add);
        if (pc.getHasteItemEquipped() == 0) {
            pc.setMoveSpeed(0);
            pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
        }
        if (pc.getarmor_setgive()) {
            pc.sendPackets(new S_SystemMessage("移除套裝效果[綠水狀態]"));
        }
    }
    
    @Override
    public int get_mode() {
        return this._add;
    }
}
