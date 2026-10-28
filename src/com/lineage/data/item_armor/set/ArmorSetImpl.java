package com.lineage.data.item_armor.set;

import com.lineage.server.model.L1PcInventory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1SkinInstance;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class ArmorSetImpl extends ArmorSet
{
    private static final Log _log;
    private final int _id;
    private final int[] _ids;
    private final ArrayList<ArmorSetEffect> _effects;
    private final int _addEffectId;
    
    static {
        _log = LogFactory.getLog(ArmorSetImpl.class);
    }
    
    protected ArmorSetImpl(int id, int[] ids, int addEffectId) {
        this._id = id;
        this._ids = ids;
        this._addEffectId = addEffectId;
        this._effects = new ArrayList();
    }
    
    public int get_id() {
        return this._id;
    }
    
    @Override
    public int[] get_ids() {
        return this._ids;
    }
    
    public void addEffect(ArmorSetEffect effect) {
        this._effects.add(effect);
    }
    
    public void removeEffect(ArmorSetEffect effect) {
        this._effects.remove(effect);
    }
    
    @Override
    public int[] get_mode() {
        int[] mode = new int[33];
        for (ArmorSetEffect effect : this._effects) {
            if (effect instanceof EffectStat_Str) {
                mode[0] = effect.get_mode();
            }
            if (effect instanceof EffectStat_Dex) {
                mode[1] = effect.get_mode();
            }
            if (effect instanceof EffectStat_Con) {
                mode[2] = effect.get_mode();
            }
            if (effect instanceof EffectStat_Wis) {
                mode[3] = effect.get_mode();
            }
            if (effect instanceof EffectStat_Int) {
                mode[4] = effect.get_mode();
            }
            if (effect instanceof EffectStat_Cha) {
                mode[5] = effect.get_mode();
            }
            if (effect instanceof EffectHp) {
                mode[6] = effect.get_mode();
            }
            if (effect instanceof EffectMp) {
                mode[7] = effect.get_mode();
            }
            if (effect instanceof EffectMr) {
                mode[8] = effect.get_mode();
            }
            if (effect instanceof EffectSp) {
                mode[9] = effect.get_mode();
            }
            if (effect instanceof EffectHaste) {
                mode[10] = effect.get_mode();
            }
            if (effect instanceof EffectDefenseFire) {
                mode[11] = effect.get_mode();
            }
            if (effect instanceof EffectDefenseWater) {
                mode[12] = effect.get_mode();
            }
            if (effect instanceof EffectDefenseWind) {
                mode[13] = effect.get_mode();
            }
            if (effect instanceof EffectDefenseEarth) {
                mode[14] = effect.get_mode();
            }
            if (effect instanceof EffectRegist_Freeze) {
                mode[15] = effect.get_mode();
            }
            if (effect instanceof EffectRegist_Stone) {
                mode[16] = effect.get_mode();
            }
            if (effect instanceof EffectRegist_Sleep) {
                mode[17] = effect.get_mode();
            }
            if (effect instanceof EffectRegist_Blind) {
                mode[18] = effect.get_mode();
            }
            if (effect instanceof EffectRegist_Stun) {
                mode[19] = effect.get_mode();
            }
            if (effect instanceof EffectRegist_Sustain) {
                mode[20] = effect.get_mode();
            }
            if (effect instanceof EffectHpR) {
                mode[21] = effect.get_mode();
            }
            if (effect instanceof EffectMpR) {
                mode[22] = effect.get_mode();
            }
            if (effect instanceof Effect_Modifier_dmg) {
                mode[23] = effect.get_mode();
            }
            if (effect instanceof Effect_Reduction_dmg) {
                mode[24] = effect.get_mode();
            }
            if (effect instanceof Effect_Magic_modifier_dmg) {
                mode[25] = effect.get_mode();
            }
            if (effect instanceof Effect_Magic_reduction_dmg) {
                mode[26] = effect.get_mode();
            }
            if (effect instanceof Effect_Bow_modifier_dmg) {
                mode[27] = effect.get_mode();
            }
            if (effect instanceof Effect_Hit_modifier) {
                mode[28] = effect.get_mode();
            }
            if (effect instanceof Effect_Bow_Hit_modifier) {
                mode[29] = effect.get_mode();
            }
            if (effect instanceof Effect_MagicCritical_chance) {
                mode[30] = effect.get_mode();
            }
            if (effect instanceof Effect_getweaponskillpro) {
                mode[31] = effect.get_mode();
            }
            if (effect instanceof Effect_getweaponskilldmg) {
                mode[32] = effect.get_mode();
            }
        }
        return mode;
    }
    
    @Override
    public void giveEffect(L1PcInstance pc) {
        try {
            for (ArmorSetEffect effect : this._effects) {
                effect.giveEffect(pc);
            }
            if (this._addEffectId != -1) {
                L1SkinInstance skin = L1SpawnUtil.spawnSkin(pc, this._addEffectId);
                if (skin != null) {
                    skin.setMoveType(1);
                    pc.addSkin(skin, this._addEffectId);
                }
            }
        }
        catch (Exception ex) {
            ArmorSetImpl._log.error(ex.getLocalizedMessage(), ex);
        }
    }
    
    @Override
    public void cancelEffect(L1PcInstance pc) {
        try {
            for (ArmorSetEffect effect : this._effects) {
                effect.cancelEffect(pc);
                if (this._addEffectId != -1 && pc.getSkin(this._addEffectId) != null) {
                    pc.getSkin(this._addEffectId).deleteMe();
                    pc.removeSkin(this._addEffectId);
                }
            }
        }
        catch (Exception ex) {
            ArmorSetImpl._log.error(ex.getLocalizedMessage(), ex);
        }
    }
    
    @Override
    public final boolean isValid(L1PcInstance pc) {
        return pc.getInventory().checkEquipped(this._ids);
    }
    
    @Override
    public boolean isPartOfSet(int id) {
        int[] ids;
        int length = (ids = this._ids).length;
        int j = 0;
        while (j < length) {
            int i = ids[j];
            if (id == i) {
                return true;
            }
            else {
                ++j;
            }
        }
        return false;
    }
    
    @Override
    public boolean isEquippedRingOfArmorSet(L1PcInstance pc) {
        L1PcInventory pcInventory = pc.getInventory();
        L1ItemInstance armor = null;
        boolean isSetContainRing = false;
        int[] ids;
        int length = (ids = this._ids).length;
        int i = 0;
        while (i < length) {
            int id = ids[i];
            armor = pcInventory.findItemId(id);
            if (armor.getItem().getUseType() == 23) {
                isSetContainRing = true;
                break;
            }
            else {
                ++i;
            }
        }
        if (armor != null && isSetContainRing) {
            int itemId = armor.getItem().getItemId();
            if (pcInventory.getTypeEquipped(2, 9) == 2) {
                L1ItemInstance[] ring = new L1ItemInstance[2];
                ring = pcInventory.getRingEquipped();
                if (ring[0].getItem().getItemId() == itemId && ring[1].getItem().getItemId() == itemId) {
                    return true;
                }
            }
            if (pcInventory.getTypeEquipped(2, 9) == 3) {
                L1ItemInstance[] ring = new L1ItemInstance[3];
                ring = pcInventory.getRingEquipped();
                if ((ring[0].getItem().getItemId() == itemId && ring[1].getItem().getItemId() == itemId) || (ring[0].getItem().getItemId() == itemId && ring[2].getItem().getItemId() == itemId) || (ring[1].getItem().getItemId() == itemId && ring[2].getItem().getItemId() == itemId)) {
                    return true;
                }
            }
            if (pcInventory.getTypeEquipped(2, 9) == 4) {
                L1ItemInstance[] ring = new L1ItemInstance[4];
                ring = pcInventory.getRingEquipped();
                if ((ring[0].getItem().getItemId() == itemId && ring[1].getItem().getItemId() == itemId) || (ring[0].getItem().getItemId() == itemId && ring[2].getItem().getItemId() == itemId) || (ring[0].getItem().getItemId() == itemId && ring[3].getItem().getItemId() == itemId) || (ring[1].getItem().getItemId() == itemId && ring[2].getItem().getItemId() == itemId) || (ring[1].getItem().getItemId() == itemId && ring[3].getItem().getItemId() == itemId) || (ring[2].getItem().getItemId() == itemId && ring[3].getItem().getItemId() == itemId)) {
                    return true;
                }
            }
        }
        return false;
    }
}
