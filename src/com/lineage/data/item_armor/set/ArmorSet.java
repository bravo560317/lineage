package com.lineage.data.item_armor.set;

import com.lineage.server.templates.L1ArmorSets;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.ArmorSetTable;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import org.apache.commons.logging.Log;

public abstract class ArmorSet
{
    private static final Log _log;
    private static final HashMap<Integer, ArmorSet> _allSet;
    
    static {
        _log = LogFactory.getLog(ArmorSet.class);
        _allSet = new HashMap();
    }
    
    public abstract int[] get_ids();
    
    public abstract int[] get_mode();
    
    public abstract void giveEffect(L1PcInstance p0);
    
    public abstract void cancelEffect(L1PcInstance p0);
    
    public abstract boolean isValid(L1PcInstance p0);
    
    public abstract boolean isPartOfSet(int p0);
    
    public abstract boolean isEquippedRingOfArmorSet(L1PcInstance p0);
    
    public static HashMap<Integer, ArmorSet> getAllSet() {
        return ArmorSet._allSet;
    }
    
    public static void load() {
        try {
            L1ArmorSets[] allList;
            for (int length = (allList = ArmorSetTable.get().getAllList()).length, i = 0; i < length; ++i) {
                L1ArmorSets armorSets = allList[i];
                int id = armorSets.getId();
                ArmorSetImpl value = new ArmorSetImpl(id, getArray(id, armorSets.getSets()), armorSets.getEffectId());
                if (armorSets.getPolyId() != -1) {
                    value.addEffect(new EffectPolymorph(armorSets.getPolyId()));
                }
                if (armorSets.getAc() != 0) {
                    value.addEffect(new EffectAc(armorSets.getAc()));
                }
                if (armorSets.getMr() != 0) {
                    value.addEffect(new EffectMr(armorSets.getMr()));
                }
                if (armorSets.getHp() != 0) {
                    value.addEffect(new EffectHp(armorSets.getHp()));
                }
                if (armorSets.getHpr() != 0) {
                    value.addEffect(new EffectHpR(armorSets.getHpr()));
                }
                if (armorSets.getMp() != 0) {
                    value.addEffect(new EffectMp(armorSets.getMp()));
                }
                if (armorSets.getMpr() != 0) {
                    value.addEffect(new EffectMpR(armorSets.getMpr()));
                }
                if (armorSets.getDefenseWater() != 0) {
                    value.addEffect(new EffectDefenseWater(armorSets.getDefenseWater()));
                }
                if (armorSets.getDefenseWind() != 0) {
                    value.addEffect(new EffectDefenseWind(armorSets.getDefenseWind()));
                }
                if (armorSets.getDefenseFire() != 0) {
                    value.addEffect(new EffectDefenseFire(armorSets.getDefenseFire()));
                }
                if (armorSets.getDefenseEarth() != 0) {
                    value.addEffect(new EffectDefenseEarth(armorSets.getDefenseEarth()));
                }
                if (armorSets.get_regist_stun() != 0) {
                    value.addEffect(new EffectRegist_Stun(armorSets.get_regist_stun()));
                }
                if (armorSets.get_regist_stone() != 0) {
                    value.addEffect(new EffectRegist_Stone(armorSets.get_regist_stone()));
                }
                if (armorSets.get_regist_sleep() != 0) {
                    value.addEffect(new EffectRegist_Sleep(armorSets.get_regist_sleep()));
                }
                if (armorSets.get_regist_freeze() != 0) {
                    value.addEffect(new EffectRegist_Freeze(armorSets.get_regist_freeze()));
                }
                if (armorSets.get_regist_sustain() != 0) {
                    value.addEffect(new EffectRegist_Sustain(armorSets.get_regist_sustain()));
                }
                if (armorSets.get_regist_blind() != 0) {
                    value.addEffect(new EffectRegist_Blind(armorSets.get_regist_blind()));
                }
                if (armorSets.getStr() != 0) {
                    value.addEffect(new EffectStat_Str(armorSets.getStr()));
                }
                if (armorSets.getDex() != 0) {
                    value.addEffect(new EffectStat_Dex(armorSets.getDex()));
                }
                if (armorSets.getCon() != 0) {
                    value.addEffect(new EffectStat_Con(armorSets.getCon()));
                }
                if (armorSets.getWis() != 0) {
                    value.addEffect(new EffectStat_Wis(armorSets.getWis()));
                }
                if (armorSets.getCha() != 0) {
                    value.addEffect(new EffectStat_Cha(armorSets.getCha()));
                }
                if (armorSets.getIntl() != 0) {
                    value.addEffect(new EffectStat_Int(armorSets.getIntl()));
                }
                if (armorSets.get_modifier_dmg() != 0) {
                    value.addEffect(new Effect_Modifier_dmg(armorSets.get_modifier_dmg()));
                }
                if (armorSets.get_reduction_dmg() != 0) {
                    value.addEffect(new Effect_Reduction_dmg(armorSets.get_reduction_dmg()));
                }
                if (armorSets.get_magic_modifier_dmg() != 0) {
                    value.addEffect(new Effect_Magic_modifier_dmg(armorSets.get_magic_modifier_dmg()));
                }
                if (armorSets.get_magic_reduction_dmg() != 0) {
                    value.addEffect(new Effect_Magic_reduction_dmg(armorSets.get_magic_reduction_dmg()));
                }
                if (armorSets.get_bow_modifier_dmg() != 0) {
                    value.addEffect(new Effect_Bow_modifier_dmg(armorSets.get_bow_modifier_dmg()));
                }
                if (armorSets.get_haste() != 0) {
                    value.addEffect(new EffectHaste(armorSets.get_haste()));
                }
                if (armorSets.get_sp() != 0) {
                    value.addEffect(new EffectSp(armorSets.get_sp()));
                }
                if (armorSets.get_hit_modifier() != 0) {
                    value.addEffect(new Effect_Hit_modifier(armorSets.get_hit_modifier()));
                }
                if (armorSets.get_bow_hit_modifier() != 0) {
                    value.addEffect(new Effect_Bow_Hit_modifier(armorSets.get_bow_hit_modifier()));
                }
                if (armorSets.get_magiccritical_chance() != 0) {
                    value.addEffect(new Effect_MagicCritical_chance(armorSets.get_magiccritical_chance()));
                }
                if (armorSets.getweaponskillpro() != 0) {
                    value.addEffect(new Effect_getweaponskillpro(armorSets.getweaponskillpro()));
                }
                if (armorSets.getweaponskilldmg() != 0) {
                    value.addEffect(new Effect_getweaponskilldmg(armorSets.getweaponskilldmg()));
                }
                if (armorSets.getInterval1() != 0 && armorSets.getEffectId1() != 0) {
                    value.addEffect(new Effect_SkillSound(armorSets.getEffectId1(), armorSets.getInterval1()));
                }
                ArmorSet._allSet.put(armorSets.getId(), value);
            }
        }
        catch (Exception e) {
            ArmorSet._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            ItemTable.get().se_mode();
        }
    }
    
    private static int[] getArray(int id, String s) {
        String[] clientStrAry = s.split(",");
        int[] array = new int[clientStrAry.length];
        try {
            for (int i = 0; i < clientStrAry.length; ++i) {
                array[i] = Integer.parseInt(clientStrAry[i]);
            }
        }
        catch (Exception e) {
            ArmorSet._log.error("編號:" + id + " 套件設置錯誤!!檢查資料庫!!", e);
        }
        return array;
    }
}
