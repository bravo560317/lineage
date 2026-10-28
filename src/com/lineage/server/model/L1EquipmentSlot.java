package com.lineage.server.model;

import com.lineage.server.serverpackets.S_DelSkill;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_Ability;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.serverpackets.S_Invis;
import com.lineage.server.serverpackets.S_AddSkill;
import com.lineage.server.templates.L1ItemSpecialAttribute;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import com.lineage.data.ItemClass;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.william.L1WilliamEnchantOrginal;
import com.lineage.server.datatables.ItemSpecialAttributeTable;
import com.lineage.william.Eq_give_skill;
import com.lineage.data.event.Eqitemskill;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.datatables.lock.CharItemsTimeReading;
import java.sql.Timestamp;
import java.util.Calendar;
import com.lineage.server.datatables.ItemTimeTable;
import java.util.Iterator;
import com.lineage.server.templates.L1ItemPower_name;
import com.lineage.server.templates.L1Item;
import com.lineage.william.L1WilliamEnchantAccessory;
import com.add.En_count;
import com.lineage.data.event.En_lv;
import com.lineage.server.datatables.RewardArmorTable;
import com.lineage.data.event.weaponlvdmg;
import com.lineage.william.weapon_lv_gfx;
import com.add.Attr_En;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.datatables.PowerItemTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.data.item_armor.set.ArmorSet;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.Log;

public class L1EquipmentSlot
{
    public static final Log _log;
    private static final L1PcInstance _pc;
    private L1PcInstance _owner;
    private ArrayList<ArmorSet> _currentArmorSet;
    private ArrayList<L1ItemInstance> _armors;
    private L1ItemInstance _weapon;
    private final PowerItemTable _l1power;
    
    static {
        _log = LogFactory.getLog(L1EquipmentSlot.class);
        _pc = null;
    }
    
    public L1EquipmentSlot(L1PcInstance owner) {
        this._owner = owner;
        this._armors = new ArrayList();
        this._currentArmorSet = new ArrayList();
        this._l1power = PowerItemTable.get();
    }
    
    public L1ItemInstance getWeapon() {
        return this._weapon;
    }
    
    public ArrayList<L1ItemInstance> getArmors() {
        return this._armors;
    }
    
    private void setWeapon(L1ItemInstance weapon) {
        this._owner.setWeapon(weapon);
        this._owner.setCurrentWeapon(weapon.getItem().getType1());
        weapon.startEquipmentTimer(this._owner);
        this._weapon = weapon;
        if (this._weapon.getItem().getMagicDmgModifier() != 0) {
            this._owner.add_magic_modifier_dmg(this._weapon.getItem().getMagicDmgModifier());
        }
        if (this._weapon.getItem().getPVPdmg() != 0) {
            this._owner.addPVPdmg(this._weapon.getItem().getPVPdmg());
        }
        if (this._weapon.getItem().getPVPdmgReduction() != 0) {
            this._owner.addPVPdmgReduction(this._weapon.getItem().getPVPdmgReduction());
        }
        if (this._weapon.getItem().getpenetrate() == 1) {
            this._owner.setpenetrate(1);
        }
        if (this._weapon.getItem().getNoweaponRedmg() > 0) {
            this._owner.setNoweaponRedmg(this._weapon.getItem().getNoweaponRedmg());
        }
        if (this._weapon.getItem().getaddStunLevel() > 0) {
            this._owner.addaddStunLevel(this._weapon.getItem().getaddStunLevel());
        }
        if (weapon.getAttrEnchantKind() > 0 && weapon.getAttrEnchantLevel() > 0) {
            Attr_En.forIntensifyArmor(this._owner, weapon);
        }
        if (weapon.getEnchantLevel() > weapon.getItem().get_safeenchant()) {
            int level = weapon.getEnchantLevel() - weapon.getItem().get_safeenchant();
            weapon_lv_gfx.forWeapon(this._owner, weapon, level);
        }
    }
    
    private void removeWeapon(L1ItemInstance weapon) {
        this._owner.setWeapon(null);
        this._owner.setCurrentWeapon(0);
        weapon.stopEquipmentTimer(this._owner);
        if (this._weapon.getItem().getMagicDmgModifier() != 0) {
            this._owner.add_magic_modifier_dmg(-this._weapon.getItem().getMagicDmgModifier());
        }
        if (this._weapon.getItem().getPVPdmg() != 0) {
            this._owner.addPVPdmg(-this._weapon.getItem().getPVPdmg());
        }
        if (this._weapon.getItem().getPVPdmgReduction() != 0) {
            this._owner.addPVPdmgReduction(-this._weapon.getItem().getPVPdmgReduction());
        }
        if (this._weapon.getItem().getpenetrate() == 1) {
            this._owner.setpenetrate(0);
        }
        if (this._weapon.getItem().getNoweaponRedmg() > 0) {
            this._owner.setNoweaponRedmg(0);
        }
        if (this._weapon.getItem().getaddStunLevel() > 0) {
            this._owner.addaddStunLevel(-this._weapon.getItem().getaddStunLevel());
        }
        if (weapon.getAttrEnchantKind() > 0 && weapon.getAttrEnchantLevel() > 0) {
            Attr_En.forIntensifyArmor1(this._owner, weapon);
        }
        if (weapon.getEnchantLevel() > weapon.getItem().get_safeenchant()) {
            int level = weapon.getEnchantLevel() - weapon.getItem().get_safeenchant();
            weapon_lv_gfx.forWeapon1(this._owner, weapon, level);
        }
        this._weapon = null;
        if (this._owner.hasSkillEffect(91)) {
            this._owner.removeSkillEffect(91);
        }
    }
    
    private void setArmor(L1ItemInstance armor) {
        L1Item item = armor.getItem();
        int itemId = armor.getItem().getItemId();
        int use_type = armor.getItem().getUseType();
        switch (use_type) {
            case 2:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 25:
            case 47: {
                this._owner.addAc(item.get_ac() - armor.getEnchantLevel() - armor.getAcByMagic() - armor.getItemAc());
                break;
            }
            case 23:
            case 24:
            case 37:
            case 40: {
                if (item.get_ac() != 0) {
                    this._owner.addAc(item.get_ac() - armor.getItemAc());
                    break;
                }
                else {
                    break;
                }
            }
            case 43:
            case 44:
            case 45:
            case 48:
            case 49:
            case 51: {
                if (item.get_ac() != 0) {
                    this._owner.addAc(item.get_ac());
                    break;
                }
                else {
                    break;
                }
            }
        }
        L1ItemPower_name power = armor.get_power_name();
        if (power != null) {
            this.skillEffice1(power.get_hole_1(), this._owner, armor);
            this.skillEffice2(power.get_hole_2(), this._owner, armor);
            this.skillEffice3(power.get_hole_3(), this._owner, armor);
            this.skillEffice4(power.get_hole_4(), this._owner, armor);
            this.skillEffice5(power.get_hole_5(), this._owner, armor);
        }
        if (weaponlvdmg.START && armor.getItem().get_safeenchant() != -1 && armor.getEnchantLevel() > armor.getItem().get_safeenchant()) {
            int level = armor.getEnchantLevel() - armor.getItem().get_safeenchant();
            RewardArmorTable.forArmor(this._owner, armor, level);
        }
        if (item.getadenapoint() > 0) {
            this._owner.addadenapoint(item.getadenapoint());
        }
        if (En_lv.START && (armor.getItem().getUseType() == 2 || armor.getItem().getUseType() == 22 || armor.getItem().getUseType() == 21 || armor.getItem().getUseType() == 19 || armor.getItem().getUseType() == 18 || armor.getItem().getUseType() == 20)) {
            En_count.forIntensifyArmor1(this._owner, armor);
            int en = armor.getEnchantLevel() - item.get_safeenchant();
            if (en > 0) {
                this._owner.setArmorCount1(this._owner.getArmorCount1() + en);
            }
            En_count.forIntensifyArmor(this._owner, armor);
        }
        this.set_time_item(armor);
        L1WilliamEnchantAccessory.getAddArmorOrginal(this._owner, armor);
        this._owner.add_up_hp_potion(item.get_up_hp_potion());
        this._owner.add_uhp_number(item.get_uhp_number());
        this._owner.addDamageReductionByArmor(armor.getDamageReduction() + armor.getItemReductionDmg());
        this._owner.addWeightReduction(item.getWeightReduction());
        int hit = armor.getHitModifierByArmor() + armor.getItemHit();
        int dmg = item.getDmgModifierByArmor() + armor.getItemAttack();
        this._owner.addHitModifierByArmor(hit);
        this._owner.addDmgModifierByArmor(dmg);
        this._owner.addBowHitModifierByArmor(item.getBowHitModifierByArmor() + armor.getItemHit());
        this._owner.addBowDmgModifierByArmor(item.getBowDmgModifierByArmor() + armor.getItemAttack());
        this._owner.addOriginalMagicHit(item.getMagicHitModifierByArmor());
        int addFire = item.get_defense_fire();
        this._owner.addFire(addFire);
        int addWater = item.get_defense_water();
        this._owner.addWater(addWater);
        int addWind = item.get_defense_wind();
        this._owner.addWind(addWind);
        int addEarth = item.get_defense_earth();
        this._owner.addEarth(addEarth);
        int add_regist_freeze = item.get_regist_freeze();
        this._owner.add_regist_freeze(add_regist_freeze);
        int addRegistStone = item.get_regist_stone();
        this._owner.addRegistStone(addRegistStone);
        int addRegistSleep = item.get_regist_sleep();
        this._owner.addRegistSleep(addRegistSleep);
        int addRegistBlind = item.get_regist_blind();
        this._owner.addRegistBlind(addRegistBlind);
        int addRegistStun = item.get_regist_stun();
        this._owner.addRegistStun(addRegistStun);
        int addRegistSustain = item.get_regist_sustain();
        this._owner.addRegistSustain(addRegistSustain);
        this._armors.add(armor);
        if (armor.getItem().getweaponskillpro() != 0) {
            this._owner.addweaponskillpro(armor.getItem().getweaponskillpro());
        }
        if (armor.getItem().getweaponskilldmg() != 0) {
            this._owner.addweaponskilldmg(armor.getItem().getweaponskilldmg());
        }
        if (armor.getItem().getPVPdmg() != 0) {
            this._owner.addPVPdmg(armor.getItem().getPVPdmg());
        }
        if (armor.getItem().getPVPdmgReduction() != 0) {
            this._owner.addPVPdmgReduction(armor.getItem().getPVPdmgReduction());
        }
        if (armor.getItemhppotion() > 0) {
            this._owner.add_potion_heal(armor.getItemhppotion());
        }
        for (Integer key : ArmorSet.getAllSet().keySet()) {
            ArmorSet armorSet = ArmorSet.getAllSet().get(key);
            if (armorSet.isPartOfSet(itemId) && armorSet.isValid(this._owner)) {
                if (armor.getItem().getUseType() == 23) {
                    if (!armorSet.isEquippedRingOfArmorSet(this._owner)) {
                        armorSet.giveEffect(this._owner);
                        this._currentArmorSet.add(armorSet);
                        this._owner.getInventory().setPartMode(armorSet, true);
                    }
                    else {
                        continue;
                    }
                }
                else {
                    this._owner.setarmor_setgive(true);
                    armorSet.giveEffect(this._owner);
                    this._currentArmorSet.add(armorSet);
                    this._owner.getInventory().setPartMode(armorSet, true);
                }
            }
        }
        armor.startEquipmentTimer(this._owner);
    }
    
    private void set_time_item(L1ItemInstance item) {
        if (item.get_time() == null) {
            int[] date = null;
            if (ItemTimeTable.TIME.get((Integer)item.getItemId()) != null) {
                date = ItemTimeTable.TIME.get((Integer)item.getItemId());
            }
            if (date != null) {
                Calendar cal = Calendar.getInstance();
                if (date[0] != 0) {
                    cal.add(5, date[0]);
                }
                if (date[1] != 0) {
                    cal.add(11, date[1]);
                }
                if (date[2] != 0) {
                    cal.add(12, date[2]);
                }
                Timestamp ts = new Timestamp(cal.getTimeInMillis());
                item.set_time(ts);
                CharItemsTimeReading.get().addTime(item.getId(), ts);
                this._owner.sendPackets(new S_ItemName(item));
            }
        }
    }
    
    private void removeArmor(L1ItemInstance armor) {
        L1Item item = armor.getItem();
        int itemId = armor.getItem().getItemId();
        int use_type = armor.getItem().getUseType();
        switch (use_type) {
            case 2:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 25:
            case 47: {
                this._owner.addAc(-(item.get_ac() - armor.getEnchantLevel() - armor.getAcByMagic() - armor.getItemAc()));
                break;
            }
            case 23:
            case 24:
            case 37:
            case 40: {
                if (item.get_ac() != 0) {
                    this._owner.addAc(-item.get_ac() - armor.getItemAc());
                    break;
                }
                else {
                    break;
                }
            }
            case 43:
            case 44:
            case 45:
            case 48:
            case 49:
            case 51: {
                if (item.get_ac() != 0) {
                    this._owner.addAc(-item.get_ac());
                    break;
                }
                else {
                    break;
                }
            }
        }
        if (Eqitemskill.START) {
            Eq_give_skill.forIntensifyArmor1(this._owner, armor.getItem().getItemId());
        }
        if (weaponlvdmg.START && armor.getItem().get_safeenchant() != -1 && armor.getEnchantLevel() > armor.getItem().get_safeenchant()) {
            int level = armor.getEnchantLevel() - armor.getItem().get_safeenchant();
            RewardArmorTable.forArmor1(this._owner, armor, level);
        }
        if (item.getadenapoint() > 0) {
            this._owner.addadenapoint(-item.getadenapoint());
        }
        if (En_lv.START && (armor.getItem().getUseType() == 2 || armor.getItem().getUseType() == 22 || armor.getItem().getUseType() == 21 || armor.getItem().getUseType() == 19 || armor.getItem().getUseType() == 18 || armor.getItem().getUseType() == 20)) {
            En_count.forIntensifyArmor1(this._owner, armor);
            int en = armor.getEnchantLevel() - item.get_safeenchant();
            if (en > 0) {
                this._owner.setArmorCount1(this._owner.getArmorCount1() - en);
            }
            if (this._owner.getArmorCount1() > 0) {
                En_count.forIntensifyArmor(this._owner, armor);
            }
        }
        L1WilliamEnchantAccessory.getReductionArmorOrginal(this._owner, armor);
        this._owner.add_up_hp_potion(-item.get_up_hp_potion());
        this._owner.add_uhp_number(-item.get_uhp_number());
        this._owner.addDamageReductionByArmor(-armor.getDamageReduction() - armor.getItemReductionDmg());
        this._owner.addWeightReduction(-item.getWeightReduction());
        int hit = armor.getHitModifierByArmor();
        int dmg = item.getDmgModifierByArmor();
        this._owner.addHitModifierByArmor(-hit - armor.getItemHit());
        this._owner.addDmgModifierByArmor(-dmg - armor.getItemAttack());
        this._owner.addBowHitModifierByArmor(-item.getBowHitModifierByArmor() - armor.getItemHit());
        this._owner.addBowDmgModifierByArmor(-item.getBowDmgModifierByArmor() - armor.getItemAttack());
        this._owner.addOriginalMagicHit(-item.getMagicHitModifierByArmor());
        int addFire = item.get_defense_fire();
        this._owner.addFire(-addFire);
        int addWater = item.get_defense_water();
        this._owner.addWater(-addWater);
        int addWind = item.get_defense_wind();
        this._owner.addWind(-addWind);
        int addEarth = item.get_defense_earth();
        this._owner.addEarth(-addEarth);
        int add_regist_freeze = item.get_regist_freeze();
        this._owner.add_regist_freeze(-add_regist_freeze);
        int addRegistStone = item.get_regist_stone();
        this._owner.addRegistStone(-addRegistStone);
        int addRegistSleep = item.get_regist_sleep();
        this._owner.addRegistSleep(-addRegistSleep);
        int addRegistBlind = item.get_regist_blind();
        this._owner.addRegistBlind(-addRegistBlind);
        int addRegistStun = item.get_regist_stun();
        this._owner.addRegistStun(-addRegistStun);
        int addRegistSustain = item.get_regist_sustain();
        this._owner.addRegistSustain(-addRegistSustain);
        for (Integer key : ArmorSet.getAllSet().keySet()) {
            ArmorSet armorSet = ArmorSet.getAllSet().get(key);
            if (armorSet.isPartOfSet(itemId) && this._currentArmorSet.contains(armorSet) && !armorSet.isValid(this._owner)) {
                this._owner.setarmor_setgive(false);
                armorSet.cancelEffect(this._owner);
                this._currentArmorSet.remove(armorSet);
                this._owner.getInventory().setPartMode(armorSet, false);
            }
        }
        armor.stopEquipmentTimer(this._owner);
        this._armors.remove(armor);
        if (armor.getItem().getweaponskillpro() != 0) {
            this._owner.addweaponskillpro(-armor.getItem().getweaponskillpro());
        }
        if (armor.getItem().getweaponskilldmg() != 0) {
            this._owner.addweaponskilldmg(-armor.getItem().getweaponskilldmg());
        }
        if (armor.getItem().getPVPdmg() != 0) {
            this._owner.addPVPdmg(-armor.getItem().getPVPdmg());
        }
        if (armor.getItem().getPVPdmgReduction() != 0) {
            this._owner.addPVPdmgReduction(-armor.getItem().getPVPdmgReduction());
        }
        int hppontion = armor.getItemhppotion();
        if (hppontion > 0) {
            this._owner.add_potion_heal(-hppontion);
        }
    }
    
    public void set(L1ItemInstance eq) {
        L1Item item = eq.getItem();
        if (item.getType2() == 0) {
            return;
        }
        else {
            int attr_str = 0;
            int attr_con = 0;
            int attr_dex = 0;
            int attr_int = 0;
            int attr_wis = 0;
            int attr_cha = 0;
            int attr_hp = 0;
            int attr_mp = 0;
            int attr_sp = 0;
            int attr_def = 0;
            int pvp_dmg = 0;
            int pvp_redmg = 0;
            int potion_heal = 0;
            int attr_物理格檔 = 0;
            int attr_魔法格檔 = 0;
            int attr_hpr = 0;
            int attr_mpr = 0;
            int attr_dmgR = 0;
            L1ItemSpecialAttributeChar attr_char = eq.get_ItemAttrName();
            if (attr_char != null) {
                L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_char.get_attr_id());
                attr_str = attr.get_add_str();
                attr_con = attr.get_add_con();
                attr_dex = attr.get_add_dex();
                attr_int = attr.get_add_int();
                attr_wis = attr.get_add_wis();
                attr_cha = attr.get_add_cha();
                attr_hp = attr.get_add_hp();
                attr_mp = attr.get_add_mp();
                attr_sp = attr.get_add_sp();
                attr_def = attr.get_add_m_def();
                pvp_dmg = attr.get_add_pvp_dmg();
                pvp_redmg = attr.get_add_pvp_redmg();
                potion_heal = attr.get_add_potion_heal();
                attr_物理格檔 = attr.get物理格檔();
                attr_魔法格檔 = attr.get魔法格檔();
                attr_hpr = attr.get_add_hpr();
                attr_mpr = attr.get_add_mpr();
                attr_dmgR = attr.get_add_dmgR();
            }
            L1WilliamEnchantOrginal.getAddArmorOrginal(this._owner, eq);
            int addhp = eq.getItemHp() + attr_hp + eq.get_addhp();
            int addmp = item.get_addmp() + eq.getItemMp() + attr_mp;
            int get_addstr = item.get_addstr() + eq.getItemStr() + attr_str;
            int get_adddex = item.get_adddex() + eq.getItemDex() + attr_dex;
            int get_addcon = item.get_addcon() + eq.getItemCon() + attr_con;
            int get_addwis = item.get_addwis() + eq.getItemWis() + attr_wis;
            int get_addint = item.get_addint() + eq.getItemInt() + attr_int;
            int get_addcha = item.get_addcha() + eq.getItemCha() + attr_cha;
            int addMr = 0;
            int addSp = 0 + attr_sp;
            int addAc = 0;
            int addHpr = 0;
            int addMpr = 0;
            int addDmgModifier = 0;
            int addHitModifier = 0;
            int bow_hit_modifier = 0;
            int bow_dmg_modifier = 0;
            if (eq.get_power_name() != null) {
                L1ItemPower_name power = eq.get_power_name();
                addAc += this._l1power.getItem(power.get_hole_1()).add_ac;
                get_addstr += this._l1power.getItem(power.get_hole_1()).add_str;
                get_adddex += this._l1power.getItem(power.get_hole_1()).add_dex;
                get_addcon += this._l1power.getItem(power.get_hole_1()).add_con;
                get_addwis += this._l1power.getItem(power.get_hole_1()).add_wis;
                get_addint += this._l1power.getItem(power.get_hole_1()).add_int;
                get_addcha += this._l1power.getItem(power.get_hole_1()).add_cha;
                addhp += this._l1power.getItem(power.get_hole_1()).addMaxHP;
                addmp += this._l1power.getItem(power.get_hole_1()).addMaxMP;
                addDmgModifier += this._l1power.getItem(power.get_hole_1()).dmg_modifier;
                addHitModifier += this._l1power.getItem(power.get_hole_1()).hit_modifier;
                addMr += this._l1power.getItem(power.get_hole_1()).m_def;
                addSp += this._l1power.getItem(power.get_hole_1()).add_sp;
                addHpr += this._l1power.getItem(power.get_hole_1()).add_hpr;
                addMpr += this._l1power.getItem(power.get_hole_1()).add_mpr;
                bow_hit_modifier += this._l1power.getItem(power.get_hole_1()).bow_hit_modifier;
                bow_dmg_modifier += this._l1power.getItem(power.get_hole_1()).bow_dmg_modifier;
                addAc += this._l1power.getItem(power.get_hole_2()).add_ac;
                get_addstr += this._l1power.getItem(power.get_hole_2()).add_str;
                get_adddex += this._l1power.getItem(power.get_hole_2()).add_dex;
                get_addcon += this._l1power.getItem(power.get_hole_2()).add_con;
                get_addwis += this._l1power.getItem(power.get_hole_2()).add_wis;
                get_addint += this._l1power.getItem(power.get_hole_2()).add_int;
                get_addcha += this._l1power.getItem(power.get_hole_2()).add_cha;
                addhp += this._l1power.getItem(power.get_hole_2()).addMaxHP;
                addmp += this._l1power.getItem(power.get_hole_2()).addMaxMP;
                addHpr += this._l1power.getItem(power.get_hole_2()).add_hpr;
                addMpr += this._l1power.getItem(power.get_hole_2()).add_mpr;
                addDmgModifier += this._l1power.getItem(power.get_hole_2()).dmg_modifier;
                addHitModifier += this._l1power.getItem(power.get_hole_2()).hit_modifier;
                addMr += this._l1power.getItem(power.get_hole_2()).m_def;
                addSp += this._l1power.getItem(power.get_hole_2()).add_sp;
                bow_hit_modifier += this._l1power.getItem(power.get_hole_2()).bow_hit_modifier;
                bow_dmg_modifier += this._l1power.getItem(power.get_hole_2()).bow_dmg_modifier;
                addAc += this._l1power.getItem(power.get_hole_3()).add_ac;
                get_addstr += this._l1power.getItem(power.get_hole_3()).add_str;
                get_adddex += this._l1power.getItem(power.get_hole_3()).add_dex;
                get_addcon += this._l1power.getItem(power.get_hole_3()).add_con;
                get_addwis += this._l1power.getItem(power.get_hole_3()).add_wis;
                get_addint += this._l1power.getItem(power.get_hole_3()).add_int;
                get_addcha += this._l1power.getItem(power.get_hole_3()).add_cha;
                addhp += this._l1power.getItem(power.get_hole_3()).addMaxHP;
                addmp += this._l1power.getItem(power.get_hole_3()).addMaxMP;
                addHpr += this._l1power.getItem(power.get_hole_3()).add_hpr;
                addMpr += this._l1power.getItem(power.get_hole_3()).add_mpr;
                addDmgModifier += this._l1power.getItem(power.get_hole_3()).dmg_modifier;
                addHitModifier += this._l1power.getItem(power.get_hole_3()).hit_modifier;
                addMr += this._l1power.getItem(power.get_hole_3()).m_def;
                addSp += this._l1power.getItem(power.get_hole_3()).add_sp;
                bow_hit_modifier += this._l1power.getItem(power.get_hole_3()).bow_hit_modifier;
                bow_dmg_modifier += this._l1power.getItem(power.get_hole_3()).bow_dmg_modifier;
                addAc += this._l1power.getItem(power.get_hole_4()).add_ac;
                get_addstr += this._l1power.getItem(power.get_hole_4()).add_str;
                get_adddex += this._l1power.getItem(power.get_hole_4()).add_dex;
                get_addcon += this._l1power.getItem(power.get_hole_4()).add_con;
                get_addwis += this._l1power.getItem(power.get_hole_4()).add_wis;
                get_addint += this._l1power.getItem(power.get_hole_4()).add_int;
                get_addcha += this._l1power.getItem(power.get_hole_4()).add_cha;
                addhp += this._l1power.getItem(power.get_hole_4()).addMaxHP;
                addmp += this._l1power.getItem(power.get_hole_4()).addMaxMP;
                addDmgModifier += this._l1power.getItem(power.get_hole_4()).dmg_modifier;
                addHitModifier += this._l1power.getItem(power.get_hole_4()).hit_modifier;
                addMr += this._l1power.getItem(power.get_hole_4()).m_def;
                addSp += this._l1power.getItem(power.get_hole_4()).add_sp;
                addHpr += this._l1power.getItem(power.get_hole_4()).add_hpr;
                addMpr += this._l1power.getItem(power.get_hole_4()).add_mpr;
                bow_hit_modifier += this._l1power.getItem(power.get_hole_4()).bow_hit_modifier;
                bow_dmg_modifier += this._l1power.getItem(power.get_hole_4()).bow_dmg_modifier;
                addAc += this._l1power.getItem(power.get_hole_5()).add_ac;
                get_addstr += this._l1power.getItem(power.get_hole_5()).add_str;
                get_adddex += this._l1power.getItem(power.get_hole_5()).add_dex;
                get_addcon += this._l1power.getItem(power.get_hole_5()).add_con;
                get_addwis += this._l1power.getItem(power.get_hole_5()).add_wis;
                get_addint += this._l1power.getItem(power.get_hole_5()).add_int;
                get_addcha += this._l1power.getItem(power.get_hole_5()).add_cha;
                addhp += this._l1power.getItem(power.get_hole_5()).addMaxHP;
                addmp += this._l1power.getItem(power.get_hole_5()).addMaxMP;
                addHpr += this._l1power.getItem(power.get_hole_5()).add_hpr;
                addMpr += this._l1power.getItem(power.get_hole_5()).add_mpr;
                addDmgModifier += this._l1power.getItem(power.get_hole_5()).dmg_modifier;
                addHitModifier += this._l1power.getItem(power.get_hole_5()).hit_modifier;
                addMr += this._l1power.getItem(power.get_hole_5()).m_def;
                addSp += this._l1power.getItem(power.get_hole_5()).add_sp;
                bow_hit_modifier += this._l1power.getItem(power.get_hole_5()).bow_hit_modifier;
                bow_dmg_modifier += this._l1power.getItem(power.get_hole_5()).bow_dmg_modifier;
            }
            if (pvp_dmg != 0) {
                this._owner.addPVPdmg(pvp_dmg);
            }
            if (pvp_redmg != 0) {
                this._owner.addPVPdmgReduction(pvp_redmg);
            }
            if (potion_heal != 0) {
                this._owner.addattr_potion_heal(potion_heal);
            }
            if (attr_物理格檔 != 0) {
                this._owner.addattr_物理格檔(attr_物理格檔);
            }
            if (attr_魔法格檔 != 0) {
                this._owner.addattr_魔法格檔(attr_魔法格檔);
            }
            if (attr_hpr != 0) {
                this._owner.addHpr(attr_hpr);
            }
            if (attr_mpr != 0) {
                this._owner.addMpr(attr_mpr);
            }
            if (attr_dmgR != 0) {
                this._owner.addother_ReductionDmg(attr_dmgR);
            }
            if (addhp != 0) {
                this._owner.addMaxHp(addhp);
            }
            if (addmp != 0) {
                this._owner.addMaxMp(addmp);
            }
            this._owner.addAc(-addAc - eq.getItemAc());
            this._owner.addBowDmgup(bow_dmg_modifier);
            this._owner.addBowHitup(bow_hit_modifier);
            this._owner.addDmgup(addDmgModifier);
            this._owner.addHitup(addHitModifier);
            this._owner.addStr(get_addstr);
            this._owner.addDex(get_adddex);
            this._owner.addCon(get_addcon);
            this._owner.addWis(get_addwis);
            if (get_addwis != 0) {
                this._owner.resetBaseMr();
            }
            this._owner.addInt(get_addint);
            this._owner.addCha(get_addcha);
            addMr = eq.getMr() + eq.getItemMr() + attr_def;
            if (eq.getName().equals("精靈盾牌") && this._owner.isElf()) {
                addMr += 5;
            }
            if (addMr != 0) {
                this._owner.addMr(addMr);
                this._owner.sendPackets(new S_SPMR(this._owner));
            }
            if (addHpr != 0) {
                this._owner.addHpr(addHpr);
            }
            if (addMpr != 0) {
                this._owner.addMpr(addMpr);
            }
            addSp += eq.getSp() + eq.getItemSp();
            if (addSp != 0) {
                this._owner.addSp(addSp);
                this._owner.sendPackets(new S_SPMR(this._owner));
            }
            if (eq.getItemistSustain() != 0) {
                this._owner.addRegistSustain(eq.getItemistSustain());
            }
            if (eq.getItemistStun() != 0) {
                this._owner.addRegistStun(eq.getItemistStun());
            }
            if (eq.getItemistStone() != 0) {
                this._owner.addRegistStone(eq.getItemistStone());
            }
            if (eq.getItemistSleep() != 0) {
                this._owner.addRegistSleep(eq.getItemistSleep());
            }
            if (eq.getItemistFreeze() != 0) {
                this._owner.add_regist_freeze(eq.getItemistFreeze());
            }
            if (eq.getItemistBlind() != 0) {
                this._owner.addRegistBlind(eq.getItemistBlind());
            }
            boolean isHasteItem = item.isHasteItem();
            if (isHasteItem) {
                this._owner.addHasteItemEquipped(1);
                this._owner.removeHasteSkillEffect();
                if (this._owner.getMoveSpeed() != 1) {
                    this._owner.setMoveSpeed(1);
                    this._owner.sendPackets(new S_SkillHaste(this._owner.getId(), 1, -1));
                    this._owner.broadcastPacketAll(new S_SkillHaste(this._owner.getId(), 1, 0));
                }
            }
            switch (item.getType2()) {
                case 1: {
                    this.setWeapon(eq);
                    ItemClass.get().item_weapon(true, this._owner, eq);
                    break;
                }
                case 2: {
                    this.setArmor(eq);
                    this.setMagic(eq);
                    ItemClass.get().item_armor(true, this._owner, eq);
                    break;
                }
            }
            this._owner.add_exp(this._owner.getExpPoint() + item.getExpPoint());
            return;
        }
    }
    
    public void remove(L1ItemInstance eq) {
        L1Item item = eq.getItem();
        if (item.getType2() == 0) {
            return;
        }
        else {
            int attr_str = 0;
            int attr_con = 0;
            int attr_dex = 0;
            int attr_int = 0;
            int attr_wis = 0;
            int attr_cha = 0;
            int attr_hp = 0;
            int attr_mp = 0;
            int attr_sp = 0;
            int attr_def = 0;
            int pvp_dmg = 0;
            int pvp_redmg = 0;
            int potion_heal = 0;
            int attr_物理格檔 = 0;
            int attr_魔法格檔 = 0;
            int attr_hpr = 0;
            int attr_mpr = 0;
            int attr_dmgR = 0;
            L1ItemSpecialAttributeChar attr_char = eq.get_ItemAttrName();
            if (attr_char != null) {
                L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_char.get_attr_id());
                attr_str = attr.get_add_str();
                attr_con = attr.get_add_con();
                attr_dex = attr.get_add_dex();
                attr_int = attr.get_add_int();
                attr_wis = attr.get_add_wis();
                attr_cha = attr.get_add_cha();
                attr_hp = attr.get_add_hp();
                attr_mp = attr.get_add_mp();
                attr_sp = attr.get_add_sp();
                attr_def = attr.get_add_m_def();
                pvp_dmg = attr.get_add_pvp_dmg();
                pvp_redmg = attr.get_add_pvp_redmg();
                potion_heal = attr.get_add_potion_heal();
                attr_物理格檔 = attr.get物理格檔();
                attr_魔法格檔 = attr.get魔法格檔();
                attr_hpr = attr.get_add_hpr();
                attr_mpr = attr.get_add_mpr();
                attr_dmgR = attr.get_add_dmgR();
            }
            L1WilliamEnchantOrginal.getReductionArmorOrginal(this._owner, eq);
            int addhp = eq.getItemHp() + attr_hp + eq.get_addhp();
            int addmp = item.get_addmp() + eq.getItemMp() + attr_mp;
            int get_addstr = item.get_addstr() + eq.getItemStr() + attr_str;
            int get_adddex = item.get_adddex() + eq.getItemDex() + attr_dex;
            int get_addcon = item.get_addcon() + eq.getItemCon() + attr_con;
            int get_addwis = item.get_addwis() + eq.getItemWis() + attr_wis;
            int get_addint = item.get_addint() + eq.getItemInt() + attr_int;
            int get_addcha = item.get_addcha() + eq.getItemCha() + attr_cha;
            int addMr = 0;
            int addAc = 0;
            int addSp = 0 + attr_sp;
            int addHpr = 0;
            int addMpr = 0;
            int addDmgModifier = 0;
            int addHitModifier = 0;
            int bow_hit_modifier = 0;
            int bow_dmg_modifier = 0;
            if (eq.get_power_name() != null) {
                L1ItemPower_name power = eq.get_power_name();
                addAc -= -this._l1power.getItem(power.get_hole_1()).add_ac;
                get_addstr += this._l1power.getItem(power.get_hole_1()).add_str;
                get_adddex += this._l1power.getItem(power.get_hole_1()).add_dex;
                get_addcon += this._l1power.getItem(power.get_hole_1()).add_con;
                get_addwis += this._l1power.getItem(power.get_hole_1()).add_wis;
                get_addint += this._l1power.getItem(power.get_hole_1()).add_int;
                get_addcha += this._l1power.getItem(power.get_hole_1()).add_cha;
                addhp += this._l1power.getItem(power.get_hole_1()).addMaxHP;
                addmp += this._l1power.getItem(power.get_hole_1()).addMaxMP;
                addDmgModifier += this._l1power.getItem(power.get_hole_1()).dmg_modifier;
                addHitModifier += this._l1power.getItem(power.get_hole_1()).hit_modifier;
                addMr += this._l1power.getItem(power.get_hole_1()).m_def;
                addSp += this._l1power.getItem(power.get_hole_1()).add_sp;
                addHpr += this._l1power.getItem(power.get_hole_1()).add_hpr;
                addMpr += this._l1power.getItem(power.get_hole_1()).add_mpr;
                bow_hit_modifier += this._l1power.getItem(power.get_hole_1()).bow_hit_modifier;
                bow_dmg_modifier += this._l1power.getItem(power.get_hole_1()).bow_dmg_modifier;
                addAc -= -this._l1power.getItem(power.get_hole_2()).add_ac;
                get_addstr += this._l1power.getItem(power.get_hole_2()).add_str;
                get_adddex += this._l1power.getItem(power.get_hole_2()).add_dex;
                get_addcon += this._l1power.getItem(power.get_hole_2()).add_con;
                get_addwis += this._l1power.getItem(power.get_hole_2()).add_wis;
                get_addint += this._l1power.getItem(power.get_hole_2()).add_int;
                get_addcha += this._l1power.getItem(power.get_hole_2()).add_cha;
                addhp += this._l1power.getItem(power.get_hole_2()).addMaxHP;
                addmp += this._l1power.getItem(power.get_hole_2()).addMaxMP;
                addHpr += this._l1power.getItem(power.get_hole_2()).add_hpr;
                addMpr += this._l1power.getItem(power.get_hole_2()).add_mpr;
                addDmgModifier += this._l1power.getItem(power.get_hole_2()).dmg_modifier;
                addHitModifier += this._l1power.getItem(power.get_hole_2()).hit_modifier;
                addMr += this._l1power.getItem(power.get_hole_2()).m_def;
                addSp += this._l1power.getItem(power.get_hole_2()).add_sp;
                bow_hit_modifier += this._l1power.getItem(power.get_hole_2()).bow_hit_modifier;
                bow_dmg_modifier += this._l1power.getItem(power.get_hole_2()).bow_dmg_modifier;
                addAc -= -this._l1power.getItem(power.get_hole_3()).add_ac;
                get_addstr += this._l1power.getItem(power.get_hole_3()).add_str;
                get_adddex += this._l1power.getItem(power.get_hole_3()).add_dex;
                get_addcon += this._l1power.getItem(power.get_hole_3()).add_con;
                get_addwis += this._l1power.getItem(power.get_hole_3()).add_wis;
                get_addint += this._l1power.getItem(power.get_hole_3()).add_int;
                get_addcha += this._l1power.getItem(power.get_hole_3()).add_cha;
                addhp += this._l1power.getItem(power.get_hole_3()).addMaxHP;
                addmp += this._l1power.getItem(power.get_hole_3()).addMaxMP;
                addHpr += this._l1power.getItem(power.get_hole_3()).add_hpr;
                addMpr += this._l1power.getItem(power.get_hole_3()).add_mpr;
                addDmgModifier += this._l1power.getItem(power.get_hole_3()).dmg_modifier;
                addHitModifier += this._l1power.getItem(power.get_hole_3()).hit_modifier;
                addMr += this._l1power.getItem(power.get_hole_3()).m_def;
                addSp += this._l1power.getItem(power.get_hole_3()).add_sp;
                bow_hit_modifier += this._l1power.getItem(power.get_hole_3()).bow_hit_modifier;
                bow_dmg_modifier += this._l1power.getItem(power.get_hole_3()).bow_dmg_modifier;
                addAc -= -this._l1power.getItem(power.get_hole_4()).add_ac;
                get_addstr += this._l1power.getItem(power.get_hole_4()).add_str;
                get_adddex += this._l1power.getItem(power.get_hole_4()).add_dex;
                get_addcon += this._l1power.getItem(power.get_hole_4()).add_con;
                get_addwis += this._l1power.getItem(power.get_hole_4()).add_wis;
                get_addint += this._l1power.getItem(power.get_hole_4()).add_int;
                get_addcha += this._l1power.getItem(power.get_hole_4()).add_cha;
                addhp += this._l1power.getItem(power.get_hole_4()).addMaxHP;
                addmp += this._l1power.getItem(power.get_hole_4()).addMaxMP;
                addHpr += this._l1power.getItem(power.get_hole_4()).add_hpr;
                addMpr += this._l1power.getItem(power.get_hole_4()).add_mpr;
                addDmgModifier += this._l1power.getItem(power.get_hole_4()).dmg_modifier;
                addHitModifier += this._l1power.getItem(power.get_hole_4()).hit_modifier;
                addMr += this._l1power.getItem(power.get_hole_4()).m_def;
                addSp += this._l1power.getItem(power.get_hole_4()).add_sp;
                bow_hit_modifier += this._l1power.getItem(power.get_hole_4()).bow_hit_modifier;
                bow_dmg_modifier += this._l1power.getItem(power.get_hole_4()).bow_dmg_modifier;
                addAc -= -this._l1power.getItem(power.get_hole_5()).add_ac;
                get_addstr += this._l1power.getItem(power.get_hole_5()).add_str;
                get_adddex += this._l1power.getItem(power.get_hole_5()).add_dex;
                get_addcon += this._l1power.getItem(power.get_hole_5()).add_con;
                get_addwis += this._l1power.getItem(power.get_hole_5()).add_wis;
                get_addint += this._l1power.getItem(power.get_hole_5()).add_int;
                get_addcha += this._l1power.getItem(power.get_hole_5()).add_cha;
                addhp += this._l1power.getItem(power.get_hole_5()).addMaxHP;
                addmp += this._l1power.getItem(power.get_hole_5()).addMaxMP;
                addHpr += this._l1power.getItem(power.get_hole_5()).add_hpr;
                addMpr += this._l1power.getItem(power.get_hole_5()).add_mpr;
                addDmgModifier += this._l1power.getItem(power.get_hole_5()).dmg_modifier;
                addHitModifier += this._l1power.getItem(power.get_hole_5()).hit_modifier;
                addMr += this._l1power.getItem(power.get_hole_5()).m_def;
                addSp += this._l1power.getItem(power.get_hole_5()).add_sp;
                bow_hit_modifier += this._l1power.getItem(power.get_hole_5()).bow_hit_modifier;
                bow_dmg_modifier += this._l1power.getItem(power.get_hole_5()).bow_dmg_modifier;
            }
            if (pvp_dmg != 0) {
                this._owner.addPVPdmg(-pvp_dmg);
            }
            if (pvp_redmg != 0) {
                this._owner.addPVPdmgReduction(-pvp_redmg);
            }
            if (potion_heal != 0) {
                this._owner.addattr_potion_heal(-potion_heal);
            }
            if (attr_物理格檔 != 0) {
                this._owner.addattr_物理格檔(-attr_物理格檔);
            }
            if (attr_魔法格檔 != 0) {
                this._owner.addattr_魔法格檔(-attr_魔法格檔);
            }
            if (attr_dmgR != 0) {
                this._owner.addother_ReductionDmg(-attr_dmgR);
            }
            if (attr_hpr != 0) {
                this._owner.addHpr(-attr_hpr);
            }
            if (attr_mpr != 0) {
                this._owner.addMpr(-attr_mpr);
            }
            if (addhp != 0) {
                this._owner.addMaxHp(-addhp);
            }
            if (addmp != 0) {
                this._owner.addMaxMp(-addmp);
            }
            this._owner.addAc(addAc + eq.getItemAc());
            this._owner.addBowDmgup(-bow_dmg_modifier);
            this._owner.addBowHitup(-bow_hit_modifier);
            this._owner.addDmgup(-addDmgModifier);
            this._owner.addHitup(-addHitModifier);
            this._owner.addStr((int)(byte)(-get_addstr));
            this._owner.addDex((int)(byte)(-get_adddex));
            this._owner.addCon((int)(byte)(-get_addcon));
            this._owner.addWis((int)(byte)(-get_addwis));
            if (get_addwis != 0) {
                this._owner.resetBaseMr();
            }
            this._owner.addInt((int)(byte)(-get_addint));
            this._owner.addCha((int)(byte)(-get_addcha));
            addMr = eq.getMr() + eq.getItemMr() + attr_def;
            if (eq.getName().equals("精靈盾牌") && this._owner.isElf()) {
                addMr = 5;
            }
            if (addMr != 0) {
                this._owner.addMr(-addMr);
                this._owner.sendPackets(new S_SPMR(this._owner));
            }
            addSp += eq.getSp() + eq.getItemSp();
            if (addSp != 0) {
                this._owner.addSp(-addSp);
                this._owner.sendPackets(new S_SPMR(this._owner));
            }
            if (addHpr != 0) {
                this._owner.addHpr(-addHpr);
            }
            if (addMpr != 0) {
                this._owner.addMpr(-addMpr);
            }
            if (eq.getItemistSustain() != 0) {
                this._owner.addRegistSustain(-eq.getItemistSustain());
            }
            if (eq.getItemistStun() != 0) {
                this._owner.addRegistStun(-eq.getItemistStun());
            }
            if (eq.getItemistStone() != 0) {
                this._owner.addRegistStone(-eq.getItemistStone());
            }
            if (eq.getItemistSleep() != 0) {
                this._owner.addRegistSleep(-eq.getItemistSleep());
            }
            if (eq.getItemistFreeze() != 0) {
                this._owner.add_regist_freeze(-eq.getItemistFreeze());
            }
            if (eq.getItemistBlind() != 0) {
                this._owner.addRegistBlind(-eq.getItemistBlind());
            }
            boolean isHasteItem = item.isHasteItem();
            if (isHasteItem) {
                this._owner.addHasteItemEquipped(-1);
                if (this._owner.getHasteItemEquipped() == 0) {
                    this._owner.setMoveSpeed(0);
                    this._owner.sendPacketsAll(new S_SkillHaste(this._owner.getId(), 0, 0));
                }
            }
            switch (item.getType2()) {
                case 1: {
                    this.removeWeapon(eq);
                    ItemClass.get().item_weapon(false, this._owner, eq);
                    break;
                }
                case 2: {
                    this.removeMagic(this._owner.getId(), eq);
                    this.removeArmor(eq);
                    ItemClass.get().item_armor(false, this._owner, eq);
                    break;
                }
            }
            this._owner.add_exp(-this._owner.getExpPoint() - item.getExpPoint());
            return;
        }
    }
    
    private void setMagic(L1ItemInstance item) {
        switch (item.getItemId()) {
            case 20008: {
                if (!this._owner.isSkillMastery(43)) {
                    this._owner.sendPackets(new S_AddSkill(this._owner, 43));
                    break;
                }
                else {
                    break;
                }
            }
            case 20013: {
                if (!this._owner.isSkillMastery(26)) {
                    this._owner.sendPackets(new S_AddSkill(this._owner, 26));
                }
                if (!this._owner.isSkillMastery(43)) {
                    this._owner.sendPackets(new S_AddSkill(this._owner, 43));
                    break;
                }
                else {
                    break;
                }
            }
            case 20014: {
                if (!this._owner.isSkillMastery(1)) {
                    this._owner.sendPackets(new S_AddSkill(this._owner, 1));
                }
                if (!this._owner.isSkillMastery(19)) {
                    this._owner.sendPackets(new S_AddSkill(this._owner, 19));
                    break;
                }
                else {
                    break;
                }
            }
            case 20015: {
                if (!this._owner.isSkillMastery(12)) {
                    this._owner.sendPackets(new S_AddSkill(this._owner, 12));
                }
                if (!this._owner.isSkillMastery(13)) {
                    this._owner.sendPackets(new S_AddSkill(this._owner, 13));
                }
                if (!this._owner.isSkillMastery(42)) {
                    this._owner.sendPackets(new S_AddSkill(this._owner, 42));
                    break;
                }
                else {
                    break;
                }
            }
            case 20023: {
                if (!this._owner.isSkillMastery(54)) {
                    this._owner.sendPackets(new S_AddSkill(this._owner, 54));
                    break;
                }
                else {
                    break;
                }
            }
            case 20062:
            case 20077:
            case 120077: {
                if (!this._owner.hasSkillEffect(60)) {
                    this._owner.killSkillEffectTimer(97);
                    this._owner.setSkillEffect(60, 0);
                    this._owner.sendPackets(new S_Invis(this._owner.getId(), 1));
                    this._owner.broadcastPacketAll(new S_RemoveObject(this._owner));
                    break;
                }
                else {
                    break;
                }
            }
            case 20281:
            case 120281: {
                this._owner.sendPackets(new S_Ability(2, true));
                break;
            }
            case 20284:
            case 120284: {
                this._owner.sendPackets(new S_Ability(5, true));
                break;
            }
            case 20288:
            case 120288: {
                this._owner.sendPackets(new S_Ability(1, true));
                break;
            }
            case 20383: {
                if (item.getChargeCount() != 0) {
                    item.setChargeCount(item.getChargeCount() - 1);
                    this._owner.getInventory().updateItem(item, 128);
                }
                if (this._owner.hasSkillEffect(1000)) {
                    this._owner.killSkillEffectTimer(1000);
                    this._owner.sendPacketsAll(new S_SkillBrave(this._owner.getId(), 0, 0));
                    this._owner.setBraveSpeed(0);
                    break;
                }
                else {
                    break;
                }
            }
            case 402000:
            case 402001:
            case 402002:
            case 402003: {
                if (!this._owner.hasSkillEffect(8010)) {
                    this._owner.setSkillEffect(8010, 0);
                    break;
                }
                else {
                    break;
                }
            }
            case 402004:
            case 402005:
            case 402006:
            case 402007: {
                if (!this._owner.hasSkillEffect(8020)) {
                    this._owner.setSkillEffect(8020, 0);
                    break;
                }
                else {
                    break;
                }
            }
        }
    }
    
    private void removeMagic(int objectId, L1ItemInstance item) {
        switch (item.getItemId()) {
            case 20008: {
                if (!CharSkillReading.get().spellCheck(objectId, 43)) {
                    this._owner.sendPackets(new S_DelSkill(this._owner, 43));
                    break;
                }
                else {
                    break;
                }
            }
            case 20013: {
                if (!CharSkillReading.get().spellCheck(objectId, 26)) {
                    this._owner.sendPackets(new S_DelSkill(this._owner, 26));
                }
                if (!CharSkillReading.get().spellCheck(objectId, 43)) {
                    this._owner.sendPackets(new S_DelSkill(this._owner, 43));
                    break;
                }
                else {
                    break;
                }
            }
            case 20014: {
                if (!CharSkillReading.get().spellCheck(objectId, 1)) {
                    this._owner.sendPackets(new S_DelSkill(this._owner, 1));
                }
                if (!CharSkillReading.get().spellCheck(objectId, 19)) {
                    this._owner.sendPackets(new S_DelSkill(this._owner, 19));
                    break;
                }
                else {
                    break;
                }
            }
            case 20015: {
                if (!CharSkillReading.get().spellCheck(objectId, 12)) {
                    this._owner.sendPackets(new S_DelSkill(this._owner, 12));
                }
                if (!CharSkillReading.get().spellCheck(objectId, 13)) {
                    this._owner.sendPackets(new S_DelSkill(this._owner, 13));
                }
                if (!CharSkillReading.get().spellCheck(objectId, 42)) {
                    this._owner.sendPackets(new S_DelSkill(this._owner, 42));
                    break;
                }
                else {
                    break;
                }
            }
            case 20023: {
                if (!CharSkillReading.get().spellCheck(objectId, 54)) {
                    this._owner.sendPackets(new S_DelSkill(this._owner, 54));
                    break;
                }
                else {
                    break;
                }
            }
            case 20062:
            case 20077:
            case 120077: {
                this._owner.delInvis();
                break;
            }
            case 20281:
            case 120281: {
                this._owner.sendPackets(new S_Ability(2, false));
                break;
            }
            case 20284:
            case 120284: {
                this._owner.sendPackets(new S_Ability(5, false));
                break;
            }
            case 20288:
            case 120288: {
                this._owner.sendPackets(new S_Ability(1, false));
                break;
            }
            case 402000:
            case 402001:
            case 402002:
            case 402003: {
                if (this._owner.hasSkillEffect(8010)) {
                    this._owner.removeSkillEffect(8010);
                    break;
                }
                else {
                    break;
                }
            }
            case 402004:
            case 402005:
            case 402006:
            case 402007: {
                if (this._owner.hasSkillEffect(8020)) {
                    this._owner.removeSkillEffect(8020);
                    break;
                }
                else {
                    break;
                }
            }
        }
    }
    
    private void skillEffice1(int hole, L1PcInstance _pc, L1ItemInstance armor) {
        if (hole > 0) {
            int skill_id = PowerItemTable.get().getItem(hole).自動skillid;
            int skill_id2 = PowerItemTable.get().getItem(hole).機率觸發;
            int skill_id3 = PowerItemTable.get().getItem(hole).觸發skilld;
            int skill_id4 = PowerItemTable.get().getItem(hole).是否被攻擊時施放;
            int skill_id5 = PowerItemTable.get().getItem(hole).是否攻擊敵人施放;
            int target_to = PowerItemTable.get().getItem(hole).target_to;
            if (armor.getItem().getUseType() == 22) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(1, skill_id);
                    _pc.setarmortype(2, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(3, skill_id2);
                        _pc.setarmortype(4, skill_id3);
                        _pc.setarmortype(5, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(3, skill_id2);
                        _pc.setarmortype(4, skill_id3);
                        _pc.setarmortype(6, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 18) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(43, skill_id);
                    _pc.setarmortype(44, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(45, skill_id2);
                        _pc.setarmortype(46, skill_id3);
                        _pc.setarmortype(47, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(45, skill_id2);
                        _pc.setarmortype(46, skill_id3);
                        _pc.setarmortype(48, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 2) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(85, skill_id);
                    _pc.setarmortype(86, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(87, skill_id2);
                        _pc.setarmortype(88, skill_id3);
                        _pc.setarmortype(89, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(87, skill_id2);
                        _pc.setarmortype(88, skill_id3);
                        _pc.setarmortype(90, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 19) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(127, skill_id);
                    _pc.setarmortype(128, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(129, skill_id2);
                        _pc.setarmortype(130, skill_id3);
                        _pc.setarmortype(131, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(129, skill_id2);
                        _pc.setarmortype(130, skill_id3);
                        _pc.setarmortype(132, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 21) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(169, skill_id);
                    _pc.setarmortype(170, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(171, skill_id2);
                        _pc.setarmortype(172, skill_id3);
                        _pc.setarmortype(173, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(171, skill_id2);
                        _pc.setarmortype(172, skill_id3);
                        _pc.setarmortype(174, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 20) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(211, skill_id);
                    _pc.setarmortype(212, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(213, skill_id2);
                        _pc.setarmortype(214, skill_id3);
                        _pc.setarmortype(215, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(213, skill_id2);
                        _pc.setarmortype(214, skill_id3);
                        _pc.setarmortype(216, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 25) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(253, skill_id);
                    _pc.setarmortype(254, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(255, skill_id2);
                        _pc.setarmortype(256, skill_id3);
                        _pc.setarmortype(257, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(255, skill_id2);
                        _pc.setarmortype(256, skill_id3);
                        _pc.setarmortype(258, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 1) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(295, skill_id);
                    _pc.setarmortype(296, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(297, skill_id2);
                        _pc.setarmortype(298, skill_id3);
                        _pc.setarmortype(299, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(297, skill_id2);
                        _pc.setarmortype(298, skill_id3);
                        _pc.setarmortype(300, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 19) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(127, skill_id);
                    _pc.setarmortype(128, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(129, skill_id2);
                        _pc.setarmortype(130, skill_id3);
                        _pc.setarmortype(131, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(129, skill_id2);
                        _pc.setarmortype(130, skill_id3);
                        _pc.setarmortype(132, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 21) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(169, skill_id);
                    _pc.setarmortype(170, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(171, skill_id2);
                        _pc.setarmortype(172, skill_id3);
                        _pc.setarmortype(173, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(171, skill_id2);
                        _pc.setarmortype(172, skill_id3);
                        _pc.setarmortype(174, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 20) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(211, skill_id);
                    _pc.setarmortype(212, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(213, skill_id2);
                        _pc.setarmortype(214, skill_id3);
                        _pc.setarmortype(215, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(213, skill_id2);
                        _pc.setarmortype(214, skill_id3);
                        _pc.setarmortype(216, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 25) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(253, skill_id);
                    _pc.setarmortype(254, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(255, skill_id2);
                        _pc.setarmortype(256, skill_id3);
                        _pc.setarmortype(257, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(255, skill_id2);
                        _pc.setarmortype(256, skill_id3);
                        _pc.setarmortype(258, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 1) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(295, skill_id);
                    _pc.setarmortype(296, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(297, skill_id2);
                        _pc.setarmortype(298, skill_id3);
                        _pc.setarmortype(299, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(297, skill_id2);
                        _pc.setarmortype(298, skill_id3);
                        _pc.setarmortype(300, skill_id5);
                    }
                }
            }
        }
    }
    
    private void skillEffice2(int hole, L1PcInstance _pc, L1ItemInstance armor) {
        if (hole > 0) {
            int skill_id = PowerItemTable.get().getItem(hole).自動skillid;
            int skill_id2 = PowerItemTable.get().getItem(hole).機率觸發;
            int skill_id3 = PowerItemTable.get().getItem(hole).觸發skilld;
            int skill_id4 = PowerItemTable.get().getItem(hole).是否被攻擊時施放;
            int skill_id5 = PowerItemTable.get().getItem(hole).是否攻擊敵人施放;
            int target_to = PowerItemTable.get().getItem(hole).target_to;
            if (armor.getItem().getUseType() == 1) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(301, skill_id);
                    _pc.setarmortype(302, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(303, skill_id2);
                        _pc.setarmortype(304, skill_id3);
                        _pc.setarmortype(305, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(303, skill_id2);
                        _pc.setarmortype(304, skill_id3);
                        _pc.setarmortype(306, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 22) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(7, skill_id);
                    _pc.setarmortype(8, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(9, skill_id2);
                        _pc.setarmortype(10, skill_id3);
                        _pc.setarmortype(11, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(9, skill_id2);
                        _pc.setarmortype(10, skill_id3);
                        _pc.setarmortype(12, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 18) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(49, skill_id);
                    _pc.setarmortype(50, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(51, skill_id2);
                        _pc.setarmortype(52, skill_id3);
                        _pc.setarmortype(53, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(51, skill_id2);
                        _pc.setarmortype(52, skill_id3);
                        _pc.setarmortype(54, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 2) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(91, skill_id);
                    _pc.setarmortype(92, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(93, skill_id2);
                        _pc.setarmortype(94, skill_id3);
                        _pc.setarmortype(95, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(93, skill_id2);
                        _pc.setarmortype(94, skill_id3);
                        _pc.setarmortype(96, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 19) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(133, skill_id);
                    _pc.setarmortype(134, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(135, skill_id2);
                        _pc.setarmortype(136, skill_id3);
                        _pc.setarmortype(137, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(135, skill_id2);
                        _pc.setarmortype(136, skill_id3);
                        _pc.setarmortype(138, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 21) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(175, skill_id);
                    _pc.setarmortype(176, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(177, skill_id2);
                        _pc.setarmortype(178, skill_id3);
                        _pc.setarmortype(179, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(177, skill_id2);
                        _pc.setarmortype(178, skill_id3);
                        _pc.setarmortype(180, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 20) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(217, skill_id);
                    _pc.setarmortype(218, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(219, skill_id2);
                        _pc.setarmortype(220, skill_id3);
                        _pc.setarmortype(221, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(219, skill_id2);
                        _pc.setarmortype(220, skill_id3);
                        _pc.setarmortype(222, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 25) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(259, skill_id);
                    _pc.setarmortype(260, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(261, skill_id2);
                        _pc.setarmortype(262, skill_id3);
                        _pc.setarmortype(263, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(261, skill_id2);
                        _pc.setarmortype(262, skill_id3);
                        _pc.setarmortype(264, skill_id5);
                    }
                }
            }
        }
    }
    
    private void skillEffice3(int hole, L1PcInstance _pc, L1ItemInstance armor) {
        if (hole > 0) {
            int skill_id = PowerItemTable.get().getItem(hole).自動skillid;
            int skill_id2 = PowerItemTable.get().getItem(hole).機率觸發;
            int skill_id3 = PowerItemTable.get().getItem(hole).觸發skilld;
            int skill_id4 = PowerItemTable.get().getItem(hole).是否被攻擊時施放;
            int skill_id5 = PowerItemTable.get().getItem(hole).是否攻擊敵人施放;
            int target_to = PowerItemTable.get().getItem(hole).target_to;
            if (armor.getItem().getUseType() == 1) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(307, skill_id);
                    _pc.setarmortype(308, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(309, skill_id2);
                        _pc.setarmortype(310, skill_id3);
                        _pc.setarmortype(311, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(309, skill_id2);
                        _pc.setarmortype(310, skill_id3);
                        _pc.setarmortype(312, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 22) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(13, skill_id);
                    _pc.setarmortype(14, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(15, skill_id2);
                        _pc.setarmortype(16, skill_id3);
                        _pc.setarmortype(17, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(15, skill_id2);
                        _pc.setarmortype(16, skill_id3);
                        _pc.setarmortype(18, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 18) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(55, skill_id);
                    _pc.setarmortype(56, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(57, skill_id2);
                        _pc.setarmortype(58, skill_id3);
                        _pc.setarmortype(59, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(57, skill_id2);
                        _pc.setarmortype(58, skill_id3);
                        _pc.setarmortype(60, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 2) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(97, skill_id);
                    _pc.setarmortype(98, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(99, skill_id2);
                        _pc.setarmortype(100, skill_id3);
                        _pc.setarmortype(101, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(99, skill_id2);
                        _pc.setarmortype(100, skill_id3);
                        _pc.setarmortype(102, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 19) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(139, skill_id);
                    _pc.setarmortype(140, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(141, skill_id2);
                        _pc.setarmortype(142, skill_id3);
                        _pc.setarmortype(143, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(141, skill_id2);
                        _pc.setarmortype(142, skill_id3);
                        _pc.setarmortype(144, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 21) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(181, skill_id);
                    _pc.setarmortype(182, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(183, skill_id2);
                        _pc.setarmortype(184, skill_id3);
                        _pc.setarmortype(185, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(185, skill_id2);
                        _pc.setarmortype(184, skill_id3);
                        _pc.setarmortype(186, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 20) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(223, skill_id);
                    _pc.setarmortype(224, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(225, skill_id2);
                        _pc.setarmortype(226, skill_id3);
                        _pc.setarmortype(227, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(225, skill_id2);
                        _pc.setarmortype(226, skill_id3);
                        _pc.setarmortype(228, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 25) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(265, skill_id);
                    _pc.setarmortype(266, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(267, skill_id2);
                        _pc.setarmortype(268, skill_id3);
                        _pc.setarmortype(269, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(267, skill_id2);
                        _pc.setarmortype(268, skill_id3);
                        _pc.setarmortype(270, skill_id5);
                    }
                }
            }
        }
    }
    
    private void skillEffice4(int hole, L1PcInstance _pc, L1ItemInstance armor) {
        if (hole > 0) {
            int skill_id = PowerItemTable.get().getItem(hole).自動skillid;
            int skill_id2 = PowerItemTable.get().getItem(hole).機率觸發;
            int skill_id3 = PowerItemTable.get().getItem(hole).觸發skilld;
            int skill_id4 = PowerItemTable.get().getItem(hole).是否被攻擊時施放;
            int skill_id5 = PowerItemTable.get().getItem(hole).是否攻擊敵人施放;
            int target_to = PowerItemTable.get().getItem(hole).target_to;
            if (armor.getItem().getUseType() == 1) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(313, skill_id);
                    _pc.setarmortype(314, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(315, skill_id2);
                        _pc.setarmortype(316, skill_id3);
                        _pc.setarmortype(317, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(315, skill_id2);
                        _pc.setarmortype(316, skill_id3);
                        _pc.setarmortype(318, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 22) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(19, skill_id);
                    _pc.setarmortype(20, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(21, skill_id2);
                        _pc.setarmortype(22, skill_id3);
                        _pc.setarmortype(23, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(21, skill_id2);
                        _pc.setarmortype(22, skill_id3);
                        _pc.setarmortype(24, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 18) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(61, skill_id);
                    _pc.setarmortype(62, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(63, skill_id2);
                        _pc.setarmortype(64, skill_id3);
                        _pc.setarmortype(65, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(63, skill_id2);
                        _pc.setarmortype(64, skill_id3);
                        _pc.setarmortype(66, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 2) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(103, skill_id);
                    _pc.setarmortype(104, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(105, skill_id2);
                        _pc.setarmortype(106, skill_id3);
                        _pc.setarmortype(107, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(105, skill_id2);
                        _pc.setarmortype(106, skill_id3);
                        _pc.setarmortype(108, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 19) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(145, skill_id);
                    _pc.setarmortype(146, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(147, skill_id2);
                        _pc.setarmortype(148, skill_id3);
                        _pc.setarmortype(149, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(147, skill_id2);
                        _pc.setarmortype(148, skill_id3);
                        _pc.setarmortype(150, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 21) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(187, skill_id);
                    _pc.setarmortype(188, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(189, skill_id2);
                        _pc.setarmortype(190, skill_id3);
                        _pc.setarmortype(191, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(189, skill_id2);
                        _pc.setarmortype(190, skill_id3);
                        _pc.setarmortype(192, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 20) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(229, skill_id);
                    _pc.setarmortype(230, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(231, skill_id2);
                        _pc.setarmortype(232, skill_id3);
                        _pc.setarmortype(233, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(231, skill_id2);
                        _pc.setarmortype(232, skill_id3);
                        _pc.setarmortype(234, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 25) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(271, skill_id);
                    _pc.setarmortype(272, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(273, skill_id2);
                        _pc.setarmortype(274, skill_id3);
                        _pc.setarmortype(275, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(273, skill_id2);
                        _pc.setarmortype(274, skill_id3);
                        _pc.setarmortype(276, skill_id5);
                    }
                }
            }
        }
    }
    
    private void skillEffice5(int hole, L1PcInstance _pc, L1ItemInstance armor) {
        if (hole > 0) {
            int skill_id = PowerItemTable.get().getItem(hole).自動skillid;
            int skill_id2 = PowerItemTable.get().getItem(hole).機率觸發;
            int skill_id3 = PowerItemTable.get().getItem(hole).觸發skilld;
            int skill_id4 = PowerItemTable.get().getItem(hole).是否被攻擊時施放;
            int skill_id5 = PowerItemTable.get().getItem(hole).是否攻擊敵人施放;
            int target_to = PowerItemTable.get().getItem(hole).target_to;
            if (armor.getItem().getUseType() == 1) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(319, skill_id);
                    _pc.setarmortype(320, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(321, skill_id2);
                        _pc.setarmortype(322, skill_id3);
                        _pc.setarmortype(323, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(321, skill_id2);
                        _pc.setarmortype(322, skill_id3);
                        _pc.setarmortype(324, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 22) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(25, skill_id);
                    _pc.setarmortype(26, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(27, skill_id2);
                        _pc.setarmortype(28, skill_id3);
                        _pc.setarmortype(29, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(27, skill_id2);
                        _pc.setarmortype(28, skill_id3);
                        _pc.setarmortype(30, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 18) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(67, skill_id);
                    _pc.setarmortype(68, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(69, skill_id2);
                        _pc.setarmortype(70, skill_id3);
                        _pc.setarmortype(71, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(69, skill_id2);
                        _pc.setarmortype(70, skill_id3);
                        _pc.setarmortype(72, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 2) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(109, skill_id);
                    _pc.setarmortype(110, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(111, skill_id2);
                        _pc.setarmortype(112, skill_id3);
                        _pc.setarmortype(113, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(111, skill_id2);
                        _pc.setarmortype(112, skill_id3);
                        _pc.setarmortype(114, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 19) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(151, skill_id);
                    _pc.setarmortype(152, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(153, skill_id2);
                        _pc.setarmortype(154, skill_id3);
                        _pc.setarmortype(155, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(153, skill_id2);
                        _pc.setarmortype(154, skill_id3);
                        _pc.setarmortype(156, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 21) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(193, skill_id);
                    _pc.setarmortype(194, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(195, skill_id2);
                        _pc.setarmortype(196, skill_id3);
                        _pc.setarmortype(197, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(195, skill_id2);
                        _pc.setarmortype(196, skill_id3);
                        _pc.setarmortype(198, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 20) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(235, skill_id);
                    _pc.setarmortype(236, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(237, skill_id2);
                        _pc.setarmortype(238, skill_id3);
                        _pc.setarmortype(239, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(237, skill_id2);
                        _pc.setarmortype(238, skill_id3);
                        _pc.setarmortype(240, skill_id5);
                    }
                }
            }
            if (armor.getItem().getUseType() == 25) {
                if (skill_id > 0 && !_pc.hasSkillEffect(skill_id) && target_to == 0) {
                    _pc.setarmortype(277, skill_id);
                    _pc.setarmortype(278, target_to);
                }
                if (skill_id2 > 0 && skill_id3 > 0) {
                    if (skill_id4 > 0 && skill_id5 == 0) {
                        _pc.setarmortype(279, skill_id2);
                        _pc.setarmortype(280, skill_id3);
                        _pc.setarmortype(281, skill_id4);
                    }
                    if (skill_id4 == 0 && skill_id5 > 0) {
                        _pc.setarmortype(279, skill_id2);
                        _pc.setarmortype(280, skill_id3);
                        _pc.setarmortype(282, skill_id5);
                    }
                }
            }
        }
    }

    public void triggerPowerItemAutoSkills(int type) {
        try {
            for (L1ItemInstance armor : this._armors) {
                if (armor != null && armor.get_power_name() != null) {
                    L1ItemPower_name power = armor.get_power_name();
                    this.skillEffice1(power.get_hole_1(), this._owner, armor);
                    this.skillEffice2(power.get_hole_2(), this._owner, armor);
                    this.skillEffice3(power.get_hole_3(), this._owner, armor);
                    this.skillEffice4(power.get_hole_4(), this._owner, armor);
                    this.skillEffice5(power.get_hole_5(), this._owner, armor);
                }
            }
        }
        catch (Exception e) {
            _log.error("觸發卡片技能時發生錯誤", e);
        }
    }
}
