package com.lineage.william;

import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1WilliamEnchantAccessory
{
    private final int _id;
    private final int _strength;
    private final int _type;
    private final int _level;
    private final byte _addStr;
    private final byte _addDex;
    private final byte _addCon;
    private final byte _addInt;
    private final byte _addWis;
    private final byte _addCha;
    private final int _addAc;
    private final int _addMaxHp;
    private final int _addMaxMp;
    private final int _addHpr;
    private final int _addMpr;
    private final int _addDmg;
    private final int _addBowDmg;
    private final int _addHit;
    private final int _addBowHit;
    private final int _addDmgReduction;
    private final int _addMr;
    private final int _addSp;
    private final int _PVPdmg;
    private final int _PVPdmgReduction;
    private final int _potion_heal;
    private final int _potion_healling;
    private final int _magic_hit;
    
    public static void getAddArmorOrginal(L1PcInstance pc, L1ItemInstance item) {
        L1WilliamEnchantAccessory armorOrginal = null;
        L1WilliamEnchantAccessory armorOrginalOk = null;
        L1WilliamEnchantAccessory[] armorOrginalSize = EnchantAccessory.getInstance().getArmorList();
        int i = 0;
        while (i < armorOrginalSize.length) {
            armorOrginalOk = EnchantAccessory.getInstance().getTemplate(i);
            if (armorOrginalOk.getType() == item.getItem().getUseType() && armorOrginalOk.getStrength() == item.getItem().get_greater() && armorOrginalOk.getLevel() == item.getEnchantLevel()) {
                armorOrginal = armorOrginalOk;
                break;
            }
            else {
                ++i;
            }
        }
        if (armorOrginal == null) {
            return;
        }
        else {
            if (armorOrginal.getAddStr() != 0) {
                pc.addStr((int)armorOrginal.getAddStr());
            }
            if (armorOrginal.getAddDex() != 0) {
                pc.addDex((int)armorOrginal.getAddDex());
            }
            if (armorOrginal.getAddCon() != 0) {
                pc.addCon((int)armorOrginal.getAddCon());
            }
            if (armorOrginal.getAddInt() != 0) {
                pc.addInt((int)armorOrginal.getAddInt());
            }
            if (armorOrginal.getAddWis() != 0) {
                pc.addWis((int)armorOrginal.getAddWis());
            }
            if (armorOrginal.getAddCha() != 0) {
                pc.addCha((int)armorOrginal.getAddCha());
            }
            if (armorOrginal.getAddAc() != 0) {
                pc.addAc(-armorOrginal.getAddAc());
            }
            if (armorOrginal.getAddMaxHp() != 0) {
                pc.addMaxHp(armorOrginal.getAddMaxHp());
            }
            if (armorOrginal.getAddMaxMp() != 0) {
                pc.addMaxMp(armorOrginal.getAddMaxMp());
            }
            if (armorOrginal.getAddHpr() != 0) {
                pc.addHpr(armorOrginal.getAddHpr());
            }
            if (armorOrginal.getAddMpr() != 0) {
                pc.addMpr(armorOrginal.getAddMpr());
            }
            if (armorOrginal.getAddDmg() != 0) {
                pc.addDmgup(armorOrginal.getAddDmg());
            }
            if (armorOrginal.getAddHit() != 0) {
                pc.addHitup(armorOrginal.getAddHit());
            }
            if (armorOrginal.getAddBowDmg() != 0) {
                pc.addBowDmgup(armorOrginal.getAddBowDmg());
            }
            if (armorOrginal.getAddBowHit() != 0) {
                pc.addBowHitup(armorOrginal.getAddBowHit());
            }
            if (armorOrginal.getAddDmgReduction() != 0) {
                pc.addDamageReductionByArmor(armorOrginal.getAddDmgReduction());
            }
            if (armorOrginal.getAddMr() != 0) {
                pc.addMr(armorOrginal.getAddMr());
            }
            if (armorOrginal.getAddSp() != 0) {
                pc.addSp(armorOrginal.getAddSp());
            }
            if (armorOrginal.getPVPdmg() != 0) {
                pc.add_PVPdmgg(armorOrginal.getPVPdmg());
            }
            if (armorOrginal.getPVPdmgReduction() != 0) {
                pc.addPVPdmgReduction(armorOrginal.getPVPdmgReduction());
            }
            if (armorOrginal.getPotion_Heal() != 0) {
                pc.add_potion_heal(armorOrginal.getPotion_Heal());
            }
            if (armorOrginal.getPotion_Healling() != 0) {
                pc.add_potion_healling(armorOrginal.getPotion_Healling());
            }
            if (armorOrginal.getAddMagicHit() != 0) {
                pc.addOriginalMagicHit(armorOrginal.getAddMagicHit());
            }
            pc.sendPackets(new S_SPMR(pc));
            pc.sendPackets(new S_OwnCharStatus(pc));
            return;
        }
    }
    
    public static void getReductionArmorOrginal(L1PcInstance pc, L1ItemInstance item) {
        L1WilliamEnchantAccessory armorOrginal = null;
        L1WilliamEnchantAccessory armorOrginalOk = null;
        L1WilliamEnchantAccessory[] armorOrginalSize = EnchantAccessory.getInstance().getArmorList();
        int i = 0;
        while (i < armorOrginalSize.length) {
            armorOrginalOk = EnchantAccessory.getInstance().getTemplate(i);
            if (armorOrginalOk.getType() == item.getItem().getUseType() && armorOrginalOk.getStrength() == item.getItem().get_greater() && armorOrginalOk.getLevel() == item.getEnchantLevel()) {
                armorOrginal = armorOrginalOk;
                break;
            }
            else {
                ++i;
            }
        }
        if (armorOrginal == null) {
            return;
        }
        else {
            if (armorOrginal.getAddStr() != 0) {
                pc.addStr(-armorOrginal.getAddStr());
            }
            if (armorOrginal.getAddDex() != 0) {
                pc.addDex(-armorOrginal.getAddDex());
            }
            if (armorOrginal.getAddCon() != 0) {
                pc.addCon(-armorOrginal.getAddCon());
            }
            if (armorOrginal.getAddInt() != 0) {
                pc.addInt(-armorOrginal.getAddInt());
            }
            if (armorOrginal.getAddWis() != 0) {
                pc.addWis(-armorOrginal.getAddWis());
            }
            if (armorOrginal.getAddCha() != 0) {
                pc.addCha(-armorOrginal.getAddCha());
            }
            if (armorOrginal.getAddAc() != 0) {
                pc.addAc(armorOrginal.getAddAc());
            }
            if (armorOrginal.getAddMaxHp() != 0) {
                pc.addMaxHp(-armorOrginal.getAddMaxHp());
            }
            if (armorOrginal.getAddMaxMp() != 0) {
                pc.addMaxMp(-armorOrginal.getAddMaxMp());
            }
            if (armorOrginal.getAddHpr() != 0) {
                pc.addHpr(-armorOrginal.getAddHpr());
            }
            if (armorOrginal.getAddMpr() != 0) {
                pc.addMpr(-armorOrginal.getAddMpr());
            }
            if (armorOrginal.getAddDmg() != 0) {
                pc.addDmgup(-armorOrginal.getAddDmg());
            }
            if (armorOrginal.getAddHit() != 0) {
                pc.addHitup(-armorOrginal.getAddHit());
            }
            if (armorOrginal.getAddBowDmg() != 0) {
                pc.addBowDmgup(-armorOrginal.getAddBowDmg());
            }
            if (armorOrginal.getAddBowHit() != 0) {
                pc.addBowHitup(-armorOrginal.getAddBowHit());
            }
            if (armorOrginal.getAddDmgReduction() != 0) {
                pc.addDamageReductionByArmor(-armorOrginal.getAddDmgReduction());
            }
            if (armorOrginal.getAddMr() != 0) {
                pc.addMr(-armorOrginal.getAddMr());
            }
            if (armorOrginal.getAddSp() != 0) {
                pc.addSp(-armorOrginal.getAddSp());
            }
            if (armorOrginal.getPVPdmg() != 0) {
                pc.add_PVPdmgg(-armorOrginal.getPVPdmg());
            }
            if (armorOrginal.getPVPdmgReduction() != 0) {
                pc.addPVPdmgReduction(-armorOrginal.getPVPdmgReduction());
            }
            if (armorOrginal.getPotion_Heal() != 0) {
                pc.add_potion_heal(-armorOrginal.getPotion_Heal());
            }
            if (armorOrginal.getPotion_Healling() != 0) {
                pc.add_potion_healling(-armorOrginal.getPotion_Healling());
            }
            if (armorOrginal.getAddMagicHit() != 0) {
                pc.addOriginalMagicHit(-armorOrginal.getAddMagicHit());
            }
            pc.sendPackets(new S_SPMR(pc));
            pc.sendPackets(new S_OwnCharStatus(pc));
            return;
        }
    }
    
    public L1WilliamEnchantAccessory(int id, int type, int strength, int level, byte addStr, byte addDex, byte addCon, byte addInt, byte addWis, byte addCha, int addAc, int addMaxHp, int addMaxMp, int addHpr, int addMpr, int addDmg, int addBowDmg, int addHit, int addBowHit, int addDmgReduction, int addMr, int addSp, int PVPdmg, int PVPdmgReduction, int potion_heal, int potion_healling, int magic_hit) {
        this._id = id;
        this._type = type;
        this._strength = strength;
        this._level = level;
        this._addStr = addStr;
        this._addDex = addDex;
        this._addCon = addCon;
        this._addInt = addInt;
        this._addWis = addWis;
        this._addCha = addCha;
        this._addAc = addAc;
        this._addMaxHp = addMaxHp;
        this._addMaxMp = addMaxMp;
        this._addHpr = addHpr;
        this._addMpr = addMpr;
        this._addDmg = addDmg;
        this._addBowDmg = addBowDmg;
        this._addHit = addHit;
        this._addBowHit = addBowHit;
        this._addDmgReduction = addDmgReduction;
        this._addMr = addMr;
        this._addSp = addSp;
        this._PVPdmg = PVPdmg;
        this._PVPdmgReduction = PVPdmgReduction;
        this._potion_heal = potion_heal;
        this._potion_healling = potion_healling;
        this._magic_hit = magic_hit;
    }
    
    public int getId() {
        return this._id;
    }
    
    public int getType() {
        return this._type;
    }
    
    public int getStrength() {
        return this._strength;
    }
    
    public int getLevel() {
        return this._level;
    }
    
    public byte getAddStr() {
        return this._addStr;
    }
    
    public byte getAddDex() {
        return this._addDex;
    }
    
    public byte getAddCon() {
        return this._addCon;
    }
    
    public byte getAddInt() {
        return this._addInt;
    }
    
    public byte getAddWis() {
        return this._addWis;
    }
    
    public byte getAddCha() {
        return this._addCha;
    }
    
    public int getAddAc() {
        return this._addAc;
    }
    
    public int getAddMaxHp() {
        return this._addMaxHp;
    }
    
    public int getAddMaxMp() {
        return this._addMaxMp;
    }
    
    public int getAddHpr() {
        return this._addHpr;
    }
    
    public int getAddMpr() {
        return this._addMpr;
    }
    
    public int getAddDmg() {
        return this._addDmg;
    }
    
    public int getAddBowDmg() {
        return this._addBowDmg;
    }
    
    public int getAddHit() {
        return this._addHit;
    }
    
    public int getAddBowHit() {
        return this._addBowHit;
    }
    
    public int getAddDmgReduction() {
        return this._addDmgReduction;
    }
    
    public int getAddMr() {
        return this._addMr;
    }
    
    public int getAddSp() {
        return this._addSp;
    }
    
    public int getPVPdmg() {
        return this._PVPdmg;
    }
    
    public int getPVPdmgReduction() {
        return this._PVPdmgReduction;
    }
    
    public int getPotion_Heal() {
        return this._potion_heal;
    }
    
    public int getPotion_Healling() {
        return this._potion_healling;
    }
    
    public int getAddMagicHit() {
        return this._magic_hit;
    }
}
