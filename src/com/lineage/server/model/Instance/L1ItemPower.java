package com.lineage.server.model.Instance;

import java.util.HashMap;
import java.util.Map;

public class L1ItemPower
{
    private final L1ItemInstance _itemInstance;
    public static final Map<Integer, Integer> MR;
    public static final Map<Integer, Integer> MPR;
    public static final Map<Integer, Integer> SP;
    public static final Map<Integer, Integer> X2;
    public static final Map<Integer, Integer> HIT;
    public static final Map<Integer, Integer> HP;
    public static final Map<Integer, Integer> DMG_REDUCE;
    public static final Map<Integer, Integer> Attack;
    public static final Map<Integer, Integer> BowAttack;
    public static final Map<Integer, Integer> weightReductionByEnchant;
    
    static {
        MR = new HashMap();
        MPR = new HashMap();
        SP = new HashMap();
        X2 = new HashMap();
        HIT = new HashMap();
        HP = new HashMap();
        DMG_REDUCE = new HashMap();
        Attack = new HashMap();
        BowAttack = new HashMap();
        weightReductionByEnchant = new HashMap();
    }
    
    public static void load() {
        L1ItemPower.MR.put(20011, new Integer(1));
        L1ItemPower.MR.put(120011, new Integer(1));
        L1ItemPower.MR.put(20110, new Integer(1));
        L1ItemPower.MR.put(120110, new Integer(1));
        L1ItemPower.MR.put(20056, new Integer(2));
        L1ItemPower.MR.put(120056, new Integer(2));
        L1ItemPower.MR.put(330005, new Integer(1));
        L1ItemPower.MR.put(20117, new Integer(1));
        L1ItemPower.MR.put(20049, new Integer(2));
        L1ItemPower.MR.put(20050, new Integer(2));
        L1ItemPower.MR.put(20078, new Integer(3));
        L1ItemPower.MR.put(400019, new Integer(1));
        L1ItemPower.MR.put(400023, new Integer(1));
        L1ItemPower.MR.put(300427, new Integer(1));
        L1ItemPower.MR.put(300433, new Integer(3));
        L1ItemPower.MR.put(1300433, new Integer(3));
        L1ItemPower.MR.put(310126, new Integer(2));
        L1ItemPower.MR.put(70092, new Integer(2));
        L1ItemPower.MR.put(70093, new Integer(2));
        L1ItemPower.MR.put(401028, new Integer(1));
        L1ItemPower.MR.put(401029, new Integer(1));
        L1ItemPower.MR.put(401030, new Integer(1));
        L1ItemPower.MR.put(401031, new Integer(1));
        L1ItemPower.MR.put(400052, new Integer(2));
        L1ItemPower.weightReductionByEnchant.put(310132, new Integer(60));
        L1ItemPower.MPR.put(410163, new Integer(1));
        L1ItemPower.MPR.put(1410163, new Integer(1));
        L1ItemPower.SP.put(20107, new Integer(1));
        L1ItemPower.SP.put(120107, new Integer(1));
        L1ItemPower.SP.put(300431, new Integer(1));
        L1ItemPower.SP.put(300433, new Integer(1));
        L1ItemPower.SP.put(1300433, new Integer(1));
        L1ItemPower.SP.put(300432, new Integer(1));
        L1ItemPower.SP.put(1300432, new Integer(1));
        L1ItemPower.SP.put(1300432, new Integer(1));
        L1ItemPower.SP.put(124, new Integer(1));
        L1ItemPower.SP.put(410158, new Integer(1));
        L1ItemPower.X2.put(410188, new Integer(1));
        L1ItemPower.HIT.put(400051, new Integer(1));
        L1ItemPower.HP.put(300429, new Integer(25));
        L1ItemPower.Attack.put(21106, new Integer(1));
        L1ItemPower.BowAttack.put(21105, new Integer(1));
        L1ItemPower.DMG_REDUCE.put(120489, new Integer(1));
        L1ItemPower.DMG_REDUCE.put(120490, new Integer(1));
        L1ItemPower.DMG_REDUCE.put(120491, new Integer(1));
        L1ItemPower.DMG_REDUCE.put(120492, new Integer(1));
        L1ItemPower.DMG_REDUCE.put(310125, new Integer(1));
    }
    
    protected L1ItemPower(L1ItemInstance itemInstance) {
        this._itemInstance = itemInstance;
    }
    
    protected int getMr() {
        int mr = this._itemInstance.getItem().get_mdef();
        Integer integer = L1ItemPower.MR.get((Integer)this._itemInstance.getItemId());
        if (integer != null) {
            mr += this._itemInstance.getEnchantLevel() * integer;
        }
        return mr;
    }
    
    protected int getMpr() {
        int mpr = this._itemInstance.getItem().get_addmpr();
        Integer integer = L1ItemPower.MPR.get((Integer)this._itemInstance.getItemId());
        if (integer != null) {
            mpr += this._itemInstance.getEnchantLevel() * integer;
        }
        return mpr;
    }
    
    protected int getweightReductionByEnchant() {
        int Weight = this._itemInstance.getItem().getWeight();
        Integer integer = L1ItemPower.weightReductionByEnchant.get((Integer)this._itemInstance.getItemId());
        if (integer != null) {
            if (this._itemInstance.getItemId() == 310132) {
                if (this._itemInstance.getEnchantLevel() >= 5) {
                    Weight += (this._itemInstance.getEnchantLevel() - 4) * integer;
                }
            }
            else {
                Weight += this._itemInstance.getEnchantLevel() * integer;
            }
        }
        return Weight;
    }
    
    protected int getSp() {
        int sp = this._itemInstance.getItem().get_addsp();
        Integer integer = L1ItemPower.SP.get((Integer)this._itemInstance.getItemId());
        if (integer != null) {
            if (this._itemInstance.getItemId() == 20107 || this._itemInstance.getItemId() == 120107) {
                if (this._itemInstance.getEnchantLevel() >= 3) {
                    sp += (this._itemInstance.getEnchantLevel() - 2) * integer;
                }
            }
            else if (this._itemInstance.getItemId() == 300433) {
                if (this._itemInstance.getEnchantLevel() >= 7) {
                    sp += (this._itemInstance.getEnchantLevel() - 6) * integer;
                }
            }
            else if (this._itemInstance.getItemId() == 300432 || this._itemInstance.getItemId() == 1300432) {
                if (this._itemInstance.getEnchantLevel() >= 8) {
                    sp += (this._itemInstance.getEnchantLevel() - 7) * integer;
                }
            }
            else if (this._itemInstance.getItemId() == 124 || this._itemInstance.getItemId() == 410158) {
                if (this._itemInstance.getEnchantLevel() >= 7) {
                    sp += (this._itemInstance.getEnchantLevel() - 6) * integer;
                }
            }
            else if (this._itemInstance.getItemId() == 300431) {
                int enchantlvl = this._itemInstance.getEnchantLevel();
                if (enchantlvl >= 5 && enchantlvl <= 6) {
                    sp += integer;
                }
                else if (enchantlvl >= 7 && enchantlvl <= 8) {
                    sp += integer * 2;
                }
                else if (enchantlvl >= 9) {
                    sp += integer * 3;
                }
            }
            else if (this._itemInstance.getItemId() == 1300433) {
                if (this._itemInstance.getEnchantLevel() >= 7) {
                    sp += (this._itemInstance.getEnchantLevel() - 6) * integer;
                }
            }
            else {
                sp += this._itemInstance.getEnchantLevel() * integer;
            }
        }
        return sp;
    }
    
    protected int getDoubleDmgChance() {
        int x2 = this._itemInstance.getItem().getDoubleDmgChance();
        Integer integer = L1ItemPower.X2.get((Integer)this._itemInstance.getItemId());
        if (integer != null) {
            x2 += this._itemInstance.getEnchantLevel() * integer;
        }
        return x2;
    }
    
    protected int getHitModifierByArmor() {
        int hit = this._itemInstance.getItem().getHitModifierByArmor();
        Integer integer = L1ItemPower.HIT.get((Integer)this._itemInstance.getItemId());
        if (integer != null) {
            if (this._itemInstance.getItemId() == 400051) {
                if (this._itemInstance.getEnchantLevel() >= 5) {
                    hit += (this._itemInstance.getEnchantLevel() - 4) * integer;
                }
            }
            else {
                hit += this._itemInstance.getEnchantLevel() * integer;
            }
        }
        return hit;
    }
    
    protected int get_addhp() {
        int hp = this._itemInstance.getItem().get_addhp();
        Integer integer = L1ItemPower.HP.get((Integer)this._itemInstance.getItemId());
        if (integer != null) {
            if (this._itemInstance.getItemId() == 300429) {
                int enchantlvl = this._itemInstance.getEnchantLevel();
                if (enchantlvl >= 5 && enchantlvl <= 6) {
                    hp += integer;
                }
                else if (enchantlvl >= 7 && enchantlvl <= 8) {
                    hp += integer * 2;
                }
                else if (enchantlvl >= 9) {
                    hp += integer * 3;
                }
            }
            else {
                hp += this._itemInstance.getEnchantLevel() * integer;
            }
        }
        return hp;
    }
    
    protected int get_addattack() {
        int attack = this._itemInstance.getItem().get_addattack();
        Integer integer = L1ItemPower.Attack.get((Integer)this._itemInstance.getItemId());
        if (integer != null) {
            if (this._itemInstance.getItemId() == 21106) {
                int enchantlvl = this._itemInstance.getEnchantLevel();
                if (enchantlvl >= 5 && enchantlvl <= 6) {
                    attack += integer;
                }
                else if (enchantlvl >= 7 && enchantlvl <= 8) {
                    attack += integer * 2;
                }
                else if (enchantlvl >= 9) {
                    attack += integer * 3;
                }
            }
            else {
                attack += this._itemInstance.getEnchantLevel() * integer;
            }
        }
        return attack;
    }
    
    protected int get_addbowattack() {
        int bowattack = this._itemInstance.getItem().get_addbowattack();
        Integer integer = L1ItemPower.BowAttack.get((Integer)this._itemInstance.getItemId());
        if (integer != null) {
            if (this._itemInstance.getItemId() == 21105) {
                int enchantlvl = this._itemInstance.getEnchantLevel();
                if (enchantlvl >= 5 && enchantlvl <= 6) {
                    bowattack += integer;
                }
                else if (enchantlvl >= 7 && enchantlvl <= 8) {
                    bowattack += integer * 2;
                }
                else if (enchantlvl >= 9) {
                    bowattack += integer * 3;
                }
            }
            else {
                bowattack += this._itemInstance.getEnchantLevel() * integer;
            }
        }
        return bowattack;
    }
    
    protected int getDamageReduction() {
        int reduce = this._itemInstance.getItem().getDamageReduction();
        Integer integer = L1ItemPower.DMG_REDUCE.get((Integer)this._itemInstance.getItemId());
        if (integer != null && this._itemInstance.getItemId() == 300430) {
            int enchantlvl = this._itemInstance.getEnchantLevel();
            if (enchantlvl >= 5 && enchantlvl <= 6) {
                reduce += integer;
            }
            else if (enchantlvl >= 7 && enchantlvl <= 8) {
                reduce += integer * 2;
            }
            else if (enchantlvl >= 9) {
                reduce += integer * 3;
            }
            else if (this._itemInstance.getItemId() == 310125 || this._itemInstance.getItemId() == 1310125) {
                if (this._itemInstance.getEnchantLevel() >= 7) {
                    reduce += (this._itemInstance.getEnchantLevel() - 6) * integer;
                }
            }
            else {
                reduce += this._itemInstance.getEnchantLevel() * integer;
            }
        }
        return reduce;
    }
    
    protected void GreaterAtEnchant(L1PcInstance owner, int randomELevel) {
        int level = this._itemInstance.getEnchantLevel();
        if (randomELevel == -1) {
            ++level;
        }
        switch (this._itemInstance.getItem().get_greater()) {
            case 0: {
                switch (level) {
                    case 1:
                    case 2: {
                        owner.addMaxHp(randomELevel * 5);
                        break;
                    }
                    case 3:
                    case 4: {
                        owner.addMaxHp(randomELevel * 10);
                        break;
                    }
                    case 5: {
                        owner.addMaxHp(randomELevel * 10);
                        owner.add_up_hp_potion(randomELevel * 2);
                        break;
                    }
                    case 6: {
                        owner.add_up_hp_potion(randomELevel * 2);
                        break;
                    }
                    case 7: {
                        owner.addMaxHp(randomELevel * 10);
                        owner.add_up_hp_potion(randomELevel * 2);
                        break;
                    }
                    case 8: {
                        owner.add_up_hp_potion(randomELevel * 2);
                        break;
                    }
                }
            }
            case 1: {
                switch (level) {
                    case 1:
                    case 2: {
                        owner.addMaxHp(randomELevel * 5);
                        break;
                    }
                    case 3:
                    case 4: {
                        owner.addMaxHp(randomELevel * 10);
                        break;
                    }
                    case 5: {
                        owner.addMaxHp(randomELevel * 10);
                        owner.addDmgModifierByArmor(randomELevel * 1);
                        owner.addBowDmgModifierByArmor(randomELevel * 1);
                        break;
                    }
                    case 6: {
                        owner.addDmgModifierByArmor(randomELevel * 1);
                        owner.addBowDmgModifierByArmor(randomELevel * 1);
                        break;
                    }
                    case 7: {
                        owner.addMaxHp(randomELevel * 10);
                        owner.addDmgModifierByArmor(randomELevel * 1);
                        owner.addBowDmgModifierByArmor(randomELevel * 1);
                        break;
                    }
                    case 8: {
                        owner.addDmgModifierByArmor(randomELevel * 1);
                        owner.addBowDmgModifierByArmor(randomELevel * 1);
                        break;
                    }
                }
            }
            case 2: {
                switch (level) {
                    case 0: {
                        break;
                    }
                    case 1:
                    case 2: {
                        owner.addMaxHp(randomELevel * 5);
                        break;
                    }
                    case 3:
                    case 4: {
                        owner.addMaxHp(randomELevel * 10);
                        break;
                    }
                    case 5: {
                        owner.addMaxHp(randomELevel * 10);
                        owner.addDamageReductionByArmor(randomELevel * 1);
                        break;
                    }
                    case 6: {
                        owner.addDamageReductionByArmor(randomELevel * 1);
                        break;
                    }
                    case 7: {
                        owner.addMaxHp(randomELevel * 10);
                        owner.addDamageReductionByArmor(randomELevel * 1);
                        break;
                    }
                    case 8: {
                        owner.addDamageReductionByArmor(randomELevel * 1);
                        break;
                    }
                }
            }
        }
    }
    
    protected void greater(L1PcInstance owner, boolean equipment) {
        int level = this._itemInstance.getEnchantLevel();
        if (level <= 0) {
            return;
        }
        else {
            if (equipment) {
                switch (this._itemInstance.getItem().get_greater()) {
                    case 0: {
                        switch (level) {
                            case 1: {
                                owner.addMaxHp(5);
                                break;
                            }
                            case 2: {
                                owner.addMaxHp(10);
                                break;
                            }
                            case 3: {
                                owner.addMaxHp(20);
                                break;
                            }
                            case 4: {
                                owner.addMaxHp(30);
                                owner.addDmgModifierByArmor(1);
                                owner.addBowDmgModifierByArmor(1);
                                break;
                            }
                            case 5: {
                                owner.addMaxHp(40);
                                owner.add_up_hp_potion(4);
                                owner.addDmgModifierByArmor(2);
                                owner.addBowDmgModifierByArmor(2);
                                owner.addDamageReductionByArmor(1);
                                break;
                            }
                            case 6: {
                                owner.addMaxHp(40);
                                owner.add_up_hp_potion(6);
                                owner.add_uhp_number(2);
                                owner.addDmgModifierByArmor(4);
                                owner.addBowDmgModifierByArmor(4);
                                owner.addOriginalMagicHit(2);
                                owner.addDamageReductionByArmor(2);
                                break;
                            }
                            case 7: {
                                owner.addMaxHp(50);
                                owner.add_up_hp_potion(10);
                                owner.add_uhp_number(4);
                                owner.addDmgModifierByArmor(5);
                                owner.addBowDmgModifierByArmor(5);
                                owner.addOriginalMagicHit(4);
                                owner.addPVPdmg(1);
                                owner.addDamageReductionByArmor(3);
                                break;
                            }
                            case 8: {
                                owner.addMaxHp(50);
                                owner.add_up_hp_potion(14);
                                owner.add_uhp_number(6);
                                owner.addDmgModifierByArmor(6);
                                owner.addBowDmgModifierByArmor(6);
                                owner.addOriginalMagicHit(8);
                                owner.addPVPdmg(2);
                                owner.addDamageReductionByArmor(3);
                                break;
                            }
                        }
                    }
                    case 1: {
                        switch (level) {
                            case 1: {
                                owner.addMaxHp(5);
                                break;
                            }
                            case 2: {
                                owner.addMaxHp(10);
                                break;
                            }
                            case 3: {
                                owner.addMaxHp(20);
                                break;
                            }
                            case 4: {
                                owner.addMaxHp(30);
                                owner.addDmgModifierByArmor(1);
                                owner.addBowDmgModifierByArmor(1);
                                owner.addDamageReductionByArmor(1);
                                break;
                            }
                            case 5: {
                                owner.addMaxHp(40);
                                owner.addDmgModifierByArmor(3);
                                owner.addBowDmgModifierByArmor(3);
                                owner.addAc(-1);
                                owner.addDamageReductionByArmor(3);
                                break;
                            }
                            case 6: {
                                owner.addMaxHp(40);
                                owner.addDmgModifierByArmor(4);
                                owner.addBowDmgModifierByArmor(4);
                                owner.addSp(1);
                                owner.addAc(-3);
                                owner.addDamageReductionByArmor(3);
                                break;
                            }
                            case 7: {
                                owner.addMaxHp(50);
                                owner.addDmgModifierByArmor(4);
                                owner.addBowDmgModifierByArmor(4);
                                owner.addSp(2);
                                owner.addAc(-4);
                                owner.addPVPdmg(1);
                                owner.addDamageReductionByArmor(4);
                                break;
                            }
                            case 8: {
                                owner.addMaxHp(50);
                                owner.addDmgModifierByArmor(5);
                                owner.addBowDmgModifierByArmor(5);
                                owner.addSp(4);
                                owner.addAc(-5);
                                owner.addPVPdmg(2);
                                owner.addDamageReductionByArmor(5);
                                break;
                            }
                        }
                    }
                    case 2: {
                        switch (level) {
                            case 1: {
                                owner.addMaxMp(5);
                                break;
                            }
                            case 2: {
                                owner.addMaxMp(10);
                                break;
                            }
                            case 3: {
                                owner.addMaxMp(20);
                                break;
                            }
                            case 4: {
                                owner.addMaxMp(30);
                                owner.addDamageReductionByArmor(1);
                                break;
                            }
                            case 5: {
                                owner.addMaxMp(40);
                                owner.addDamageReductionByArmor(2);
                                owner.addMr(3);
                                break;
                            }
                            case 6: {
                                owner.addMaxMp(40);
                                owner.addDamageReductionByArmor(4);
                                owner.addMr(5);
                                owner.addAc(-1);
                                break;
                            }
                            case 7: {
                                owner.addMaxMp(50);
                                owner.addDamageReductionByArmor(4);
                                owner.addMr(7);
                                owner.addAc(-2);
                                owner.addPVPdmgReduction(1);
                                break;
                            }
                            case 8: {
                                owner.addMaxMp(50);
                                owner.addDamageReductionByArmor(5);
                                owner.addMr(10);
                                owner.addAc(-3);
                                owner.addPVPdmgReduction(2);
                                break;
                            }
                        }
                    }
                }
            }
            else {
                switch (this._itemInstance.getItem().get_greater()) {
                    case 0: {
                        switch (level) {
                            case 1: {
                                owner.addMaxHp(-5);
                                break;
                            }
                            case 2: {
                                owner.addMaxHp(-10);
                                break;
                            }
                            case 3: {
                                owner.addMaxHp(-20);
                                break;
                            }
                            case 4: {
                                owner.addMaxHp(-30);
                                owner.addDmgModifierByArmor(-1);
                                owner.addBowDmgModifierByArmor(-1);
                                break;
                            }
                            case 5: {
                                owner.addMaxHp(-40);
                                owner.add_up_hp_potion(-4);
                                owner.addDmgModifierByArmor(-2);
                                owner.addBowDmgModifierByArmor(-2);
                                owner.addDamageReductionByArmor(-1);
                                break;
                            }
                            case 6: {
                                owner.addMaxHp(-40);
                                owner.add_up_hp_potion(-6);
                                owner.add_uhp_number(-2);
                                owner.addDmgModifierByArmor(-4);
                                owner.addBowDmgModifierByArmor(-4);
                                owner.addOriginalMagicHit(-2);
                                owner.addDamageReductionByArmor(-2);
                                break;
                            }
                            case 7: {
                                owner.addMaxHp(-50);
                                owner.add_up_hp_potion(-10);
                                owner.add_uhp_number(-4);
                                owner.addDmgModifierByArmor(-5);
                                owner.addBowDmgModifierByArmor(-5);
                                owner.addOriginalMagicHit(-4);
                                owner.addPVPdmg(-1);
                                owner.addDamageReductionByArmor(-3);
                                break;
                            }
                            case 8: {
                                owner.addMaxHp(-50);
                                owner.add_up_hp_potion(-14);
                                owner.add_uhp_number(-6);
                                owner.addDmgModifierByArmor(-6);
                                owner.addBowDmgModifierByArmor(-6);
                                owner.addOriginalMagicHit(-8);
                                owner.addPVPdmg(-2);
                                owner.addDamageReductionByArmor(-3);
                                break;
                            }
                        }
                    }
                    case 1: {
                        switch (level) {
                            case 1: {
                                owner.addMaxHp(-5);
                                break;
                            }
                            case 2: {
                                owner.addMaxHp(-10);
                                break;
                            }
                            case 3: {
                                owner.addMaxHp(-20);
                                break;
                            }
                            case 4: {
                                owner.addMaxHp(-30);
                                owner.addDmgModifierByArmor(-1);
                                owner.addBowDmgModifierByArmor(-1);
                                owner.addDamageReductionByArmor(-1);
                                break;
                            }
                            case 5: {
                                owner.addMaxHp(-40);
                                owner.addDmgModifierByArmor(-3);
                                owner.addBowDmgModifierByArmor(-3);
                                owner.addAc(1);
                                owner.addDamageReductionByArmor(-3);
                                break;
                            }
                            case 6: {
                                owner.addMaxHp(-40);
                                owner.addDmgModifierByArmor(-4);
                                owner.addBowDmgModifierByArmor(-4);
                                owner.addSp(-1);
                                owner.addAc(3);
                                owner.addDamageReductionByArmor(-3);
                                break;
                            }
                            case 7: {
                                owner.addMaxHp(-50);
                                owner.addDmgModifierByArmor(-4);
                                owner.addBowDmgModifierByArmor(-4);
                                owner.addSp(-2);
                                owner.addAc(4);
                                owner.addPVPdmg(-1);
                                owner.addDamageReductionByArmor(-4);
                                break;
                            }
                            case 8: {
                                owner.addMaxHp(-50);
                                owner.addDmgModifierByArmor(-5);
                                owner.addBowDmgModifierByArmor(-5);
                                owner.addSp(-4);
                                owner.addAc(5);
                                owner.addPVPdmg(-2);
                                owner.addDamageReductionByArmor(-5);
                                break;
                            }
                        }
                    }
                    case 2: {
                        switch (level) {
                            case 1: {
                                owner.addMaxMp(-5);
                                break;
                            }
                            case 2: {
                                owner.addMaxMp(-10);
                                break;
                            }
                            case 3: {
                                owner.addMaxMp(-20);
                                break;
                            }
                            case 4: {
                                owner.addMaxMp(-30);
                                owner.addDamageReductionByArmor(-1);
                                break;
                            }
                            case 5: {
                                owner.addMaxMp(-40);
                                owner.addDamageReductionByArmor(-2);
                                owner.addMr(-3);
                                break;
                            }
                            case 6: {
                                owner.addMaxMp(-40);
                                owner.addDamageReductionByArmor(-4);
                                owner.addMr(-5);
                                owner.addAc(1);
                                break;
                            }
                            case 7: {
                                owner.addMaxMp(-50);
                                owner.addDamageReductionByArmor(-4);
                                owner.addMr(-7);
                                owner.addAc(2);
                                owner.addPVPdmgReduction(-1);
                                break;
                            }
                            case 8: {
                                owner.addMaxMp(-50);
                                owner.addDamageReductionByArmor(-5);
                                owner.addMr(-10);
                                owner.addAc(3);
                                owner.addPVPdmgReduction(-2);
                                break;
                            }
                        }
                    }
                }
            }
            return;
        }
    }
}
