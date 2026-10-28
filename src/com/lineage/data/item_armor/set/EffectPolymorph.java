package com.lineage.data.item_armor.set;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;

public class EffectPolymorph implements ArmorSetEffect
{
    private int _gfxId;
    
    public EffectPolymorph(int gfxId) {
        this._gfxId = gfxId;
    }
    
    @Override
    public void giveEffect(L1PcInstance pc) {
        int awakeSkillId = pc.getAwakeSkillId();
        if (awakeSkillId == 185 || awakeSkillId == 190 || awakeSkillId == 195) {
            pc.sendPackets(new S_ServerMessage(1384));
            return;
        }
        else {
            if (this._gfxId == 6080 || this._gfxId == 6094) {
                if (pc.get_sex() == 0) {
                    this._gfxId = 6094;
                }
                else {
                    this._gfxId = 6080;
                }
                if (!this.isRemainderOfCharge(pc)) {
                    L1ItemInstance item = pc.getInventory().findItemId(20383);
                    if (item != null && item.getChargeCount() == 0) {
                        return;
                    }
                }
            }
            L1PolyMorph.doPoly(pc, this._gfxId, 0, 1);
            return;
        }
    }
    
    @Override
    public void cancelEffect(L1PcInstance pc) {
        int awakeSkillId = pc.getAwakeSkillId();
        if (awakeSkillId == 185 || awakeSkillId == 190 || awakeSkillId == 195) {
            pc.sendPackets(new S_ServerMessage(1384));
            return;
        }
        else {
            if (this._gfxId == 6080 || this._gfxId == 6094) {
                if (pc.get_sex() == 0) {
                    this._gfxId = 6094;
                }
                else {
                    this._gfxId = 6080;
                }
            }
            if (pc.getTempCharGfx() != this._gfxId) {
                return;
            }
            else {
                L1PolyMorph.undoPoly(pc);
                return;
            }
        }
    }
    
    private boolean isRemainderOfCharge(L1PcInstance pc) {
        if (pc.getInventory().checkItem(20383, 1L)) {
            L1ItemInstance item = pc.getInventory().findItemId(20383);
            if (item != null && item.getChargeCount() != 0) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int get_mode() {
        return this._gfxId;
    }
}
