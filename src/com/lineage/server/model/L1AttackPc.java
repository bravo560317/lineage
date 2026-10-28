package com.lineage.server.model;

import com.lineage.config.ConfigAlt;
import com.lineage.server.serverpackets.S_Liquor;
import com.lineage.server.serverpackets.S_UseArrowSkill;
import com.lineage.server.serverpackets.S_AttackPacketPc;
import com.lineage.server.model.poison.L1DamagePoison;
import java.util.ArrayList;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.model.weaponskill.WeaponSkillStart;
import com.lineage.server.serverpackets.S_PacketBoxDk;
import java.util.Calendar;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1Mon;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.utils.L1SpawnUtil;
import com.add.PeepCard;
import com.lineage.config.Config_Pc_Damage;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.serverpackets.S_CharTitle;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.data.event.FeatureItemSet;
import com.lineage.server.templates.L1MagicWeapon;
import com.lineage.server.model.Instance.L1DeInstance;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.config.ConfigOther;
import com.lineage.config.ConfigGuaji;
import com.lineage.server.templates.L1ItemPower_name;
import com.lineage.server.templates.L1ItemSpecialAttribute;
import com.lineage.william.StrDmg;
import com.lineage.william.DexDmg;
import com.lineage.server.datatables.ItemSpecialAttributeTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.weaponskill.L1WeaponSkillType;
import com.lineage.server.model.weaponskill.W_SK0016;
import com.lineage.william.L1WilliamGfxIdOrginal;
import com.lineage.server.datatables.PowerItemTable;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import org.apache.commons.logging.Log;

public class L1AttackPc extends L1AttackMode
{
    private static final Log _log;
    private byte _attackType;
    private int _weaponDamage;
    private int _weaponTotalDamage;
    private int hit_rnd; // 命中隨機值 (Hit random value)
    private boolean _isWeaponSkillArrowFree; // 新增：武器是否因技能而免箭矢 (New: Is the weapon arrow-free due to a skill?)
    L1ItemSpecialAttributeChar item_attr_char;
    Random random;
    
    static {
        _log = LogFactory.getLog(L1AttackPc.class);
    }
    
    private void skillEffice(int hole, L1Character target) {
        if (hole > 0) {
            Random random = new Random();
            int skill_id = PowerItemTable.get().getItem(hole).skill_id;
            int probability = PowerItemTable.get().getItem(hole).probability;
            int target_to = PowerItemTable.get().getItem(hole).target_to;
            int rnd = random.nextInt(100) + 1;
            if (!this._pc.hasSkillEffect(90000000 + skill_id) && skill_id > 0 && rnd <= probability) {
                if (L1WilliamGfxIdOrginal.Cancellation(target.getTempCharGfx())) {
                    return;
                }
                else {
                    L1Skills skill = SkillsTable.get().getTemplate(skill_id);
                    this._pc.setSkillEffect(90000000 + skill_id, 2000);
                    if (skill.getTarget().equals("none")) {
                        if (target_to == 0) {
                            new L1SkillUse().handleCommands(this._pc, skill_id, this._pc.getId(), this._pc.getX(), this._pc.getY(), skill.getBuffDuration(), 5);
                        }
                        else if (target_to == 1) {
                            new L1SkillUse().handleCommands(this._pc, skill_id, target.getId(), target.getX(), target.getY(), skill.getBuffDuration(), 5);
                        }
                    }
                    else if (skill.getTarget().equals("attack") && target_to == 1) {
                        new L1SkillUse().handleCommands(this._pc, skill_id, target.getId(), target.getX(), target.getY(), skill.getBuffDuration(), 5);
                    }
                }
            }
            boolean unequipment = PowerItemTable.get().getItem(hole).unequipment;
            probability = PowerItemTable.get().getItem(hole).probability_unequi;
            rnd = random.nextInt(100) + 1;
            if (unequipment && rnd <= probability && !this._targetPc.isSafetyZone() && target instanceof L1PcInstance) {
                L1PcInstance targetPC = (L1PcInstance)target;
                targetPC.getInventory().setEquipped(targetPC.getWeapon(), false, false, false);
                this._pc.sendPackets(new S_SystemMessage("成功解除對方裝備!"));
                targetPC.sendPackets(new S_SystemMessage("你已被對方解除武器!"));
            }
        }
    }
    
    private boolean polyidEictff(int hole, L1Character target) {
        if (hole > 0 && target instanceof L1PcInstance) {
            Random random = new Random();
            int polyid = PowerItemTable.get().getItem(hole).polyid;
            int polyid_time = PowerItemTable.get().getItem(hole).polyid_time;
            int probability = PowerItemTable.get().getItem(hole).probability_polyid;
            int rnd = random.nextInt(100) + 1;
            if (polyid > 0 && !this._targetPc.isSafetyZone() && rnd <= probability) {
                if (L1WilliamGfxIdOrginal.Cancellation(target.getTempCharGfx())) {
                    return false;
                }
                else {
                    L1PolyMorph.doPoly(target, polyid, polyid_time, 2);
                    return true;
                }
            }
        }
        return false;
    }
    
    public L1AttackPc(L1PcInstance attacker, L1Character target) {
        this._attackType = 0;
        this.item_attr_char = null;
        this.random = new Random();
        if (target == null) {
            return;
        }
        else if (target.isDead()) {
            return;
        }
        else {
            this._pc = attacker;
            if (target instanceof L1PcInstance) {
                this._targetPc = (L1PcInstance)target;
                this._calcType = 1;
            }
            else if (target instanceof L1NpcInstance) {
                this._targetNpc = (L1NpcInstance)target;
                this._calcType = 2;
            }
            this._weapon = this._pc.getWeapon();
            if (this._weapon != null) {
                this.item_attr_char = this._weapon.get_ItemAttrName();
                int attr_DmgSmall = 0;
                int attr_DmgLarge = 0;
                int attr_HitModifier = 0;
                int attr_DmgModifier = 0;
                if (this.item_attr_char != null) {
                    L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(this.item_attr_char.get_attr_id());
                    attr_DmgSmall = attr.get_dmg_small();
                    attr_DmgLarge = attr.get_dmg_large();
                    attr_HitModifier = attr.get_hitmodifier();
                    attr_DmgModifier = attr.get_dmgmodifier();
                }
                this._weaponId = this._weapon.getItem().getItemId();
                this._weaponType = this._weapon.getItem().getType1();
                this._weaponType2 = this._weapon.getItem().getType();
                this._weaponAddHit = this._weapon.getItem().getHitModifier() + this._weapon.getHitByMagic() + this._weapon.getItemHit() + this._weapon.getItemBowHit() + attr_HitModifier;
                this._weaponAddDmg = this._weapon.getItem().getDmgModifier() + this._weapon.getDmgByMagic() + this._weapon.getItemAttack() + this._weapon.getItemBowAttack() + attr_DmgModifier;
                this._weaponSmall = this._weapon.getItem().getDmgSmall() + attr_DmgSmall;
                this._weaponLarge = this._weapon.getItem().getDmgLarge() + attr_DmgLarge;
                this._weaponRange = this._weapon.getItem().getRange();
                this._weaponBless = this._weapon.getItem().getBless();
                L1ItemPower_name power = this._weapon.get_power_name();
                if (power != null) {
                    this.skillEffice(power.get_hole_1(), target);
                    this.skillEffice(power.get_hole_2(), target);
                    this.skillEffice(power.get_hole_3(), target);
                    this.skillEffice(power.get_hole_4(), target);
                    this.skillEffice(power.get_hole_5(), target);
                    boolean polyid = this.polyidEictff(power.get_hole_1(), target);
                    if (!polyid) {
                        polyid = this.polyidEictff(power.get_hole_2(), target);
                        if (!polyid) {
                            polyid = this.polyidEictff(power.get_hole_3(), target);
                            if (!polyid) {
                                polyid = this.polyidEictff(power.get_hole_4(), target);
                                if (!polyid) {
                                    polyid = this.polyidEictff(power.get_hole_5(), target);
                                }
                            }
                        }
                    }
                }
                if (this._weaponType != 20 && this._weaponType != 62) {
                    this._weaponEnchant = this._weapon.getEnchantLevel() - this._weapon.get_durability();
                }
                else {
                    this._weaponEnchant = this._weapon.getEnchantLevel();
                }
                this._weaponMaterial = this._weapon.getItem().getMaterial();
                if (this._weaponType == 20) {
                    this._arrow = this._pc.getInventory().getArrow();
                    if (this._arrow != null) {
                        this._weaponBless = this._arrow.getItem().getBless();
                        this._weaponMaterial = this._arrow.getItem().getMaterial();
                    }
                }
                if (this._weaponType == 62) {
                    this._sting = this._pc.getInventory().getSting();
                    if (this._sting != null) {
                        this._weaponBless = this._sting.getItem().getBless();
                        this._weaponMaterial = this._sting.getItem().getMaterial();
                    }
                }
                this._weaponDoubleDmgChance = this._weapon.getDoubleDmgChance();
                this._weaponAttrEnchantKind = this._weapon.getAttrEnchantKind();
                this._weaponAttrEnchantLevel = this._weapon.getAttrEnchantLevel();

            }
            if (this._weaponType == 20 || this._weaponType == 62) {
                int dmg = DexDmg.getDexDmgSkill(this._pc, (int)this._pc.getDex());
                if (dmg != 0) {
                    this._statusDamage = dmg;
                }
            }
            else {
                int dmg = StrDmg.getStrDmgSkill(this._pc, (int)this._pc.getStr());
                if (dmg != 0) {
                    this._statusDamage = dmg;
                }
            }
            this._target = target;
            this._targetId = target.getId();
            this._targetX = target.getX();
            this._targetY = target.getY();
            return;
        }
    }
    
    @Override
    public boolean calcHit() {
        if (this._target == null) {
            return this._isHit = false;
        }
        else {
            if (this._weaponRange != -1) {
                int location = this._pc.getLocation().getTileLineDistance(this._target.getLocation());
                if (location > this._weaponRange + 1) {
                    return this._isHit = false;
                }
            }
            else if (!this._pc.getLocation().isInScreen(this._target.getLocation())) {
                return this._isHit = false;
            }
            if (this._weaponType == 20 && this._weaponId != 190 && this._arrow == null && !this._isWeaponSkillArrowFree) { // 增加免箭矢技能判斷 (Add arrow-free skill check)
                this._isHit = false;
            }
            else if (this._weaponType == 62 && this._sting == null) {
                this._isHit = false;
            }
            // 新增：判斷是否具有免箭矢的武器技能
            if (this._weapon != null) {
                ArrayList<L1WeaponSkillType> weaponSkills = WeaponSkillStart.getWeaponSkillsForWeapon(this._weapon);
                if (weaponSkills != null) {
                    for (L1WeaponSkillType skill : weaponSkills) {
                        if (skill instanceof W_SK0016 && this._weapon.getEnchantLevel() >= skill.get_type1()) {
                            this._isWeaponSkillArrowFree = true;
                            break;
                        }
                    }
                }
            }
            if (!this._pc.glanceCheck(this._targetX, this._targetY)) {
                this._isHit = false;
            }
            else if (this._weaponId == 247 || this._weaponId == 248 || this._weaponId == 249) {
                this._isHit = false;
            }
            else if (this._calcType == 1) {
                this._isHit = this.calcPcHit();
            }
            else if (this._calcType == 2) {
                this._isHit = this.calcNpcHit();
            }
            return this._isHit;
        }
    }
    
    private int str_dex_Hit() {
        int hitRate = 0;
        int hitDex = DexDmg.getDexHitSkill(this._pc, (int)this._pc.getDex());
        if (hitDex != 0) {
            hitRate += hitDex;
        }
        int hitStr = StrDmg.getStrHitSkill(this._pc, (int)this._pc.getStr());
        if (hitStr != 0) {
            hitRate += hitStr;
        }
        if (this._pc.isWizard()) {
            hitRate += 5;
        }
        return hitRate;
    }
    
    private boolean calcPcHit() {
        if (ConfigGuaji.Guaji_save && this._targetPc.isActived() && this._targetPc.getLevel() <= ConfigGuaji.Guaji_level) {
            return false;
        }
        else if (this._targetPc == this._pc) {
            return false;
        }
        else if (this._targetPc == null) {
            return false;
        }
        else if (L1AttackMode.dmg0(this._targetPc)) {
            return false;
        }
        else if (ConfigOther.newcharpra && this._targetPc.getnewcharpra()) {
            return false;
        }
        else if (ConfigOther.newcharpra && this._pc.getnewcharpra()) {
            return false;
        }
        else if (this.calcEvasion()) {
            return false;
        }
        else if (this._targetPc.getattr_物理格檔() > 0 && L1AttackPc._random.nextInt(100) < this._targetPc.getattr_物理格檔()) {
            return false;
        }
        else if (this._weaponType2 == 17) {
            return true;
        }
        else {
            this._hitRate = this._pc.getLevel();
            this._hitRate += this.str_dex_Hit();
            if (this._weaponType != 20 && this._weaponType != 62) {
                this._hitRate = (int)((double)this._hitRate + ((double)(this._weaponAddHit + this._pc.getHitup() + this._pc.getOriginalHitup()) + (double)this._weaponEnchant * 0.6));
            }
            else {
                this._hitRate = (int)((double)this._hitRate + ((double)(this._weaponAddHit + this._pc.getBowHitup() + this._pc.getOriginalBowHitup()) + (double)this._weaponEnchant * 0.6));
            }
            if (this._weaponType != 20 && this._weaponType != 62) {
                this._hitRate += this._pc.getHitModifierByArmor();
            }
            else {
                this._hitRate += this._pc.getBowHitModifierByArmor();
            }
            int weight240 = this._pc.getInventory().getWeight240();
            if (weight240 > 80) {
                if (80 < weight240 && 120 >= weight240) {
                    --this._hitRate;
                }
                else if (121 <= weight240 && 160 >= weight240) {
                    this._hitRate -= 3;
                }
                else if (161 <= weight240 && 200 >= weight240) {
                    this._hitRate -= 5;
                }
            }
            if (this._targetPc.isDarkelf() && this._targetPc.getlogpcpower_SkillFor3() != 0 && this._targetPc.getlogpcpower_SkillFor3() >= 1) {
                this._hitRate -= this._targetPc.getlogpcpower_SkillFor3();
            }
            this._hitRate += this.hitUp();
            if (this._pc.is_mazu()) {
                this._hitRate += 5;
            }
            if (this._pc.get_other().get_PvP_hit() > 0) {
                this._hitRate += this._pc.get_other().get_PvP_hit();
            }
            if ((this._pc.get_other().get_PvP_bow_hit() > 0 && this._weaponType == 20) || this._weaponType == 62) {
                this._hitRate += this._pc.get_other().get_PvP_bow_hit();
            }
            int attackerDice = L1AttackPc._random.nextInt(20) + 1 + this._hitRate - 10;
            attackerDice += L1AttackMode.attackerDice(this._targetPc);
            int defenderDice = 0;
            int defenderValue = (int)((double)this._targetPc.getAc() * 1.5) * -1;
            if (this._targetPc.getAc() >= 0) {
                defenderDice = 10 - this._targetPc.getAc();
            }
            else if (this._targetPc.getAc() < 0) {
                defenderDice = 10 + L1AttackPc._random.nextInt(defenderValue) + 1;
            }
            int fumble = this._hitRate - 9;
            int critical = this._hitRate + 10;
            if (this._pc.isElf() && this._pc.getElfAttr() == 2) {
                ++attackerDice;
            }
            if (attackerDice <= fumble) {
                this._hitRate = 15;
            }
            else if (attackerDice >= critical) {
                this._hitRate = 100;
            }
            else if (attackerDice > defenderDice) {
                this._hitRate = 100;
            }
            else if (attackerDice <= defenderDice) {
                this._hitRate = 15;
            }
            int rnd = L1AttackPc._random.nextInt(100) + 1;
            if (this._weaponType == 20) {
                if (this._hitRate > rnd) {
                    return this.calcErEvasion();
                }
                else if (this._hitRate > 95) {
                    this._hitRate = 95;
                }
            }
            return this._hitRate >= rnd;
        }
    }
    
    private boolean calcNpcHit() {
        int gfxid = this._targetNpc.getNpcTemplate().get_gfxid();
        switch (gfxid) {
            case 2412: {
                if (!this._pc.getInventory().checkEquipped(20046)) {
                    return false;
                }
                else {
                    break;
                }
            }
        }
        if (L1AttackMode.dmg0(this._targetNpc)) {
            return false;
        }
        else if (this._targetNpc.isShop()) {
            return false;
        }
        else if (this._weaponType2 == 17) {
            return true;
        }
        else {
            if (this._targetNpc.hasSkillEffect(91)) {
                L1Magic magic = new L1Magic(this._targetNpc, this._pc);
                boolean isCounterBarrier = false;
                boolean isProbability = magic.calcProbabilityMagic(91);
                boolean isShortDistance = this.isShortDistance();
                L1ItemInstance weapon = this._pc.getWeapon();
                if (isProbability && isShortDistance && weapon.getItem().getType() != 17) {
                    isCounterBarrier = true;
                }
                if (isCounterBarrier) {
                    this.commitCounterBarrier();
                    return false;
                }
            }
            if (this._targetNpc.hasSkillEffect(11060)) {
                this.commitCounterBarrier();
                return false;
            }
            else {
                this._hitRate = this._pc.getLevel();
                this._hitRate += this.str_dex_Hit();
                if (this._weaponType != 20 && this._weaponType != 62) {
                    this._hitRate = (int)((double)this._hitRate + ((double)(this._weaponAddHit + this._pc.getHitup() + this._pc.getOriginalHitup()) + (double)this._weaponEnchant * 0.6));
                }
                else {
                    this._hitRate = (int)((double)this._hitRate + ((double)(this._weaponAddHit + this._pc.getBowHitup() + this._pc.getOriginalBowHitup()) + (double)this._weaponEnchant * 0.6));
                }
                if (this._weaponType != 20 && this._weaponType != 62) {
                    this._hitRate += this._pc.getHitModifierByArmor();
                }
                else {
                    this._hitRate += this._pc.getBowHitModifierByArmor();
                }
                int weight240 = this._pc.getInventory().getWeight240();
                if (weight240 > 80) {
                    if (80 < weight240 && 120 >= weight240) {
                        --this._hitRate;
                    }
                    else if (121 <= weight240 && 160 >= weight240) {
                        this._hitRate -= 3;
                    }
                    else if (161 <= weight240 && 200 >= weight240) {
                        this._hitRate -= 5;
                    }
                }
                this._hitRate += this.hitUp();
                if (this._pc.is_mazu()) {
                    this._hitRate += 5;
                }
                int attackerDice = L1AttackPc._random.nextInt(20) + 2 + this._hitRate - 10;
                attackerDice += L1AttackMode.attackerDice(this._targetNpc);
                int defenderDice = 10 - this._targetNpc.getAc();
                int fumble = this._hitRate - 9;
                int critical = this._hitRate + 10;
                if (attackerDice <= fumble) {
                    this._hitRate = 0;
                }
                else if (attackerDice >= critical) {
                    this._hitRate = 100;
                }
                else if (attackerDice > defenderDice) {
                    this._hitRate = 100;
                }
                else if (attackerDice <= defenderDice) {
                    this._hitRate = 0;
                }
                int npcId = this._targetNpc.getNpcTemplate().get_npcId();
                Integer tgskill = L1AttackList.SKNPC.get((Integer)npcId);
                if (tgskill != null && !this._pc.hasSkillEffect(tgskill)) {
                    this._hitRate = 0;
                }
                Integer tgpoly = L1AttackList.PLNPC.get((Integer)npcId);
                if (tgpoly != null && tgpoly.equals((Integer)this._pc.getTempCharGfx())) {
                    this._hitRate = 0;
                }
                int rnd = L1AttackPc._random.nextInt(100) + 1;
                return this._hitRate >= rnd;
            }
        }
    }
    
    private int hitUp() {
        int hitUp = 0;
        if (this._pc.getSkillEffect().size() <= 0) {
            return hitUp;
        }
        else {
            if (!this._pc.getSkillisEmpty()) {
                try {
                    if (this._weaponType != 20 && this._weaponType != 62) {
                        for (Integer key : this._pc.getSkillEffect()) {
                            Integer integer = L1AttackList.SKU1.get(key);
                            if (integer != null) {
                                hitUp += integer;
                            }
                        }
                    }
                    else {
                        for (Integer key : this._pc.getSkillEffect()) {
                            Integer integer = L1AttackList.SKU2.get(key);
                            if (integer != null) {
                                hitUp += integer;
                            }
                        }
                    }
                }
                catch (ConcurrentModificationException ex) {}
                catch (Exception e) {
                    L1AttackPc._log.error(e.getLocalizedMessage(), e);
                }
            }
            return hitUp;
        }
    }
    
    @Override
    public int calcDamage() {
        switch (this._calcType) {
            case 1: {
                this._damage = this.calcPcDamage();
                break;
            }
            case 2: {
                this._damage = this.calcNpcDamage();
                break;
            }
        }
        if (this._targetNpc instanceof L1DeInstance && !this._targetNpc.isSafetyZone()) {
            this._damage = 0;
        }
        if (this._isHit) {
            if (this._weapon != null && this._weapon.get_magic_weapon() != null) {
                boolean isLongRange = this._weaponType == 20 || this._weaponType == 62;
                this._damage = (int)((double)this._damage + L1MagicWeapon.getWeaponSkillDamage(this._pc, this._target, (double)this._damage, this._weapon.get_magic_weapon(), isLongRange));
            }
            if (this._weapon != null && FeatureItemSet.POWER_START) {
                L1AttackPower attackPower = new L1AttackPower(this._pc, this._target, this._weaponAttrEnchantKind, this._weaponAttrEnchantLevel);
                this._damage = attackPower.set_item_power(this._damage);
            }
            if (!this._pc.isFoeSlayer()) {
                this.dk_dmgUp();
            }
            this.addPcPoisonAttack(this._target);
            this.AttrAmuletEffect();
            this.MoonAmuletEffect();
            this.Imperius_Tshirt_Effect();
            if (this._pc.isFoeSlayer()) {
                if (this._pc.get_weaknss() == 1) {
                    this._damage += 10;
                    this._damage += this._pc.get_FoeSlayerBonusDmg();
                }
                else if (this._pc.get_weaknss() == 2) {
                    this._damage += 25;
                    this._damage += this._pc.get_FoeSlayerBonusDmg();
                }
                else if (this._pc.get_weaknss() == 3) {
                    this._damage += 40;
                    this._damage += this._pc.get_FoeSlayerBonusDmg();
                }
            }
        }
        if (this._damage > 0) {
            this.soulHp();
        }
        return this._damage;
    }
    
    private int weaponDamage1(int weaponMaxDamage) {
        int weaponDamage = 0;
        switch (this._weaponType2) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18: {
                if (this._pc.hasSkillEffect(175)) {
                    weaponDamage = weaponMaxDamage;
                    break;
                }
                else {
                    weaponDamage = L1AttackPc._random.nextInt(weaponMaxDamage) + 1;
                    break;
                }
            }
            case 3: {
                weaponDamage = L1AttackPc._random.nextInt(weaponMaxDamage) + 1;
                if (this._weaponId == 217 && this._pc.getLawful() < 500) {
                    int a = this._pc.getLawful() / 1000;
                    int b = Math.abs(a);
                    weaponDamage += b;
                    break;
                }
                else if (this._weaponId == 410165 && this._pc.getLawful() < 500) {
                    int a = this._pc.getLawful() / 3000;
                    int b = Math.abs(a);
                    weaponDamage += b;
                    break;
                }
                else {
                    break;
                }
            }
            case 11: {
                weaponDamage = L1AttackPc._random.nextInt(weaponMaxDamage) + 1;
                if (L1AttackPc._random.nextInt(100) + 1 <= this._weaponDoubleDmgChance) {
                    weaponDamage = weaponMaxDamage;
                    this._attackType = 2;
                    break;
                }
                else {
                    break;
                }
            }
        }
        if (this._pc.getClanid() != 0) {
            weaponDamage = (int)((double)weaponDamage + L1AttackMode.getDamageUpByClan(this._pc));
        }
        switch (this._pc.guardianEncounter()) {
            case 3: {
                ++weaponDamage;
                break;
            }
            case 4: {
                weaponDamage += 3;
                break;
            }
            case 5: {
                weaponDamage += 5;
                break;
            }
        }
        return weaponDamage;
    }
    
    private double weaponDamage2(double weaponTotalDamage) {
        double dmg = 0.0;
        weaponTotalDamage += (double)this.calcAttrEnchantDmg();
        boolean doubleBrake = false;
        switch (this._weaponType2) {
            case 0: {
                dmg = (double)(L1AttackPc._random.nextInt(5) + 4 >> 2);
                break;
            }
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 14:
            case 15:
            case 16:
            case 18: {
                dmg = weaponTotalDamage + (double)this._statusDamage + (double)this._pc.getDmgup() + (double)this._pc.getOriginalDmgup();
                break;
            }
            case 4:
            case 13: {
                int add = this._statusDamage;
                dmg = weaponTotalDamage + (double)add + (double)this._pc.getBowDmgup() + (double)this._pc.getOriginalBowDmgup();
                if (this._arrow != null) {
                    int add_dmg = Math.max(this._arrow.getItem().getDmgSmall(), 1);
                    dmg = dmg + (double)L1AttackPc._random.nextInt(add_dmg) + 1.0;
                    break;
                }
                else if (this._weaponId == 190 || this._isWeaponSkillArrowFree) {
                    dmg += 10.0;
                    break;
                }
                else {
                    break;
                }
            }
            case 10: {
                dmg = weaponTotalDamage + (double)this._statusDamage + (double)this._pc.getBowDmgup() + (double)this._pc.getOriginalBowDmgup();
                if (this._sting != null) {
                    int add_dmg = Math.max(this._sting.getItem().getDmgSmall(), 1);
                    dmg += (double)(L1AttackPc._random.nextInt(add_dmg) + 1);
                    break;
                }
                else {
                    break;
                }
            }
            case 11: {
                int addchance = 0;
                if (this._pc.getLevel() > 45) {
                    addchance = (this._pc.getLevel() - 50) / 5;
                }
                int totalchance = 20 + addchance;
                if (this._pc.hasSkillEffect(105) && L1AttackPc._random.nextInt(100) < totalchance) {
                    weaponTotalDamage *= 2.0;
                }
                dmg = weaponTotalDamage + (double)this._statusDamage + (double)this._pc.getDmgup() + (double)this._pc.getOriginalDmgup();
                break;
            }
            case 12: {
                if (!this._pc.hasSkillEffect(96230) && L1AttackPc._random.nextInt(100) + 1 <= this._weaponDoubleDmgChance) {
                    this._attackType = 4;
                    weaponTotalDamage *= 1.8;
                    this._pc.setSkillEffect(96230, 2000);
                }
                int addchance2 = 0;
                if (this._pc.getLevel() > 45) {
                    addchance2 = (this._pc.getLevel() - 50) / 5;
                }
                int totalchance2 = 20 + addchance2;
                if (!this._pc.hasSkillEffect(96230) && this._pc.hasSkillEffect(105) && L1AttackPc._random.nextInt(100) < totalchance2) {
                    weaponTotalDamage *= 2.0;
                }
                dmg = weaponTotalDamage + (double)this._statusDamage + (double)this._pc.getDmgup() + (double)this._pc.getOriginalDmgup();
                break;
            }
            case 17: {
                dmg = L1WeaponSkill.getKiringkuDamage(this._pc, this._target);
                dmg += weaponTotalDamage;
                Integer dmgint = L1AttackList.INTD.get((Integer)(int)this._pc.getInt());
                if (dmgint != null) {
                    dmg += (double)(int)dmgint;
                    break;
                }
                else {
                    break;
                }
            }
        }
        if (this._weaponType2 != 0) {
            int add_dmg2 = this._weapon.getItem().get_add_dmg();
            if (add_dmg2 != 0) {
                dmg += (double)add_dmg2;
            }
        }
        return dmg;
    }
    
    private double pcDmgMode(double dmg) {
        dmg = this.calcBuffDamage(dmg);
        if (this._weaponType != 20 && this._weaponType != 62) {
            if (this._weaponType != 58) {
                dmg += (double)this._pc.getDmgModifierByArmor();
            }
        }
        else {
            dmg += (double)this._pc.getBowDmgModifierByArmor();
        }
        dmg += this.dmgUp();
        dmg += (double)this._pc.dmgAdd();
        if (this.item_attr_char != null) {
            L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(this.item_attr_char.get_attr_id());
            int drain_min_hp = attr.get_add_drain_min_hp();
            int drain_max_hp = attr.get_add_drain_max_hp();
            if (drain_min_hp > 0 && drain_max_hp > 0) {
                int random = L1AttackPc._random.nextInt(100) + 1;
                if (random <= attr.get_drain_hp_rand() && this._target != null) {
                    this._drainHp = Math.min(L1AttackPc._random.nextInt(drain_max_hp) + drain_min_hp, drain_max_hp);
                    this._pc.setCurrentHp((int)(short)(this._pc.getCurrentHp() + this._drainHp));
                }
            }
            int drain_min_mp = attr.get_add_drain_min_mp();
            int drain_max_mp = attr.get_add_drain_max_mp();
            if (drain_min_mp > 0 && drain_max_mp > 0) {
                int random2 = L1AttackPc._random.nextInt(100) + 1;
                if (random2 <= attr.get_drain_mp_rand() && this._target != null) {
                    this._drainMana = Math.min(L1AttackPc._random.nextInt(drain_max_mp) + drain_min_mp, drain_max_mp);
                    this._pc.setCurrentMp((int)(short)(this._pc.getCurrentMp() + this._drainMana));
                }
            }
            if (attr.get_add_skill_gfxid() > 0 && attr.get_add_skill_rand() >= L1AttackPc._random.nextInt(100) + 1) {
                this._pc.sendPacketsX10(new S_SkillSound(this._target.getId(), attr.get_add_skill_gfxid()));
                dmg += (double)attr.get_add_skill_dmg();
            }
        }
        if (this._pc.get_double_dmg() != 0 && L1AttackPc._random.nextInt(100) + 1 <= this._pc.get_double_dmg()) {
            dmg *= 2.0;
        }
        return dmg;
    }
    
    public int calcPcDamage() {
        if (this._targetPc == null) {
            return 0;
        }
        else if (this._targetPc == this._pc) {
            return 0;
        }
        else if (L1AttackMode.dmg0(this._targetPc)) {
            this._isHit = false;
            return this._drainHp = 0;
        }
        else if (!this._isHit) {
            return 0;
        }
        else {
            if (this._targetPc.isActived() && !this._targetPc.isSimpleAutoHunt() && this._targetPc.getAu_OtherSet(1) > 0 && this._targetPc.getMap().isTeleportable() && this._targetPc.getAu_AutoSet(0) == 0 && !this._targetPc.hasSkillEffect(79501)) {
                this._targetPc.setSkillEffect(79501, 2000);
            }
            int weaponMaxDamage = this._weaponSmall;
            this._weaponDamage = this.weaponDamage1(weaponMaxDamage);
            if (this._pc.is_mazu()) {
                this._weaponDamage += 5;
            }
            this._weaponTotalDamage = this._weaponDamage + this._weaponAddDmg + this._weaponEnchant;
            double dmg = this.weaponDamage2((double)this._weaponTotalDamage);
            dmg = this.pcDmgMode(dmg);
            if (this._targetPc.hasSkillEffect(113)) {
                double attackerlv = (double)this._pc.getLevel();
                double adddmg = attackerlv / 15.0 / 100.0 + 1.01;
                dmg *= adddmg;
            }
            if (this._weaponRange != -1 && this._targetPc.hasSkillEffect(112)) {
                dmg *= 1.58;
            }
            int DamageReduction = this._targetPc.getDamageReductionByArmor() + this._targetPc.getother_ReductionDmg() + this._targetPc.getClan_ReductionDmg() + this._targetPc.get_reduction_dmg();
            dmg -= (double)DamageReduction;
            dmg -= (double)this._targetPc.dmgDowe();
            if (this._targetPc.getClanid() != 0) {
                dmg -= L1AttackMode.getDamageReductionByClan(this._targetPc);
            }
            if (DamageReduction > 0 && this._targetPc.getNoweaponRedmg() > 0) {
                dmg += (double)this._targetPc.getNoweaponRedmg();
            }
            if (this._pc.hasSkillEffect(98767)) {
                this._pc.killSkillEffectTimer(98767);
                this._pc.sendPackets(new S_SystemMessage("已偵測您處於打架狀態,並取消驗證"));
                this._pc.sendPackets(new S_CharTitle(this._pc.getId(), this._pc.get_savetitle()));
                this._pc.setrantitle(0);
                this._pc.set_savetitle(null);
                this._pc.addaierrorcheck(0);
            }
            dmg += this.weaponSkill(this._pc, this._targetPc, (double)this._weaponTotalDamage);
            if (!this._pc.getDolls().isEmpty()) {
                for (L1DollInstance doll : this._pc.getDolls().values()) {
                    doll.startDollSkill(this._targetPc, dmg);
                }
            }
            if (this._targetPc.hasSkillEffect(88)) {
                int targetPcLvl = Math.max(this._targetPc.getLevel(), 50);
                dmg -= (double)((targetPcLvl - 50) / 5 + 10);
            }
            if (this._pc.get_other().get_PvP_dmg() > 0 && this._weaponType != 20) {
                dmg += (double)this._pc.get_other().get_PvP_dmg();
            }
            if (this._pc.get_other().get_PvP_bow_dmg() > 0 && this._weaponType == 20) {
                dmg += (double)this._pc.get_other().get_PvP_bow_dmg();
            }
            if (this._pc.get_other().get_Ran_PvP_dmg_b() > 0 && L1AttackPc._random.nextInt(100) + 1 <= this._pc.get_other().get_Ran_PvP_dmg_b()) {
                if (this._pc.get_other().getPvP_dmg_b() >= 1.0) {
                    dmg *= this._pc.get_other().getPvP_dmg_b();
                }
                else {
                    dmg *= 1.0 + this._pc.get_other().getPvP_dmg_b();
                }
            }
            if (this._targetPc.get_other().get_PvP_dmg_r() > 0) {
                dmg -= (double)this._targetPc.get_other().get_PvP_dmg_r();
            }
            if (this._pc.get_PVPdmgg() > 0) {
                dmg += (double)this._pc.get_PVPdmgg();
            }
            if (this._pc.getDmgdouble() > 0.0) {
                dmg *= this._pc.getDmgdouble();
            }
            if (this._pc.getweaponran() != 0 && L1AttackPc._random.nextInt(100) + 1 <= this._pc.getweaponran()) {
                if (this._pc.getweapondmg() != 0) {
                    dmg += (double)(L1AttackPc._random.nextInt(this._pc.getweapondmg()) + 1);
                }
                if (this._pc.getweaponchp() != 0) {
                    this._pc.setCurrentHp(this._pc.getCurrentHp() + this._pc.getweaponchp());
                }
                if (this._pc.getweaponcmp() != 0) {
                    this._pc.setCurrentMp(this._pc.getCurrentMp() + this._pc.getweaponcmp());
                }
                if (this._pc.getweapongfx() != 0) {
                    this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), this._pc.getweapongfx()));
                }
            }
            if (this._pc.isCrown()) {
                dmg *= Config_Pc_Damage.Other_To_isCrownpc;
            }
            else if (this._pc.isKnight()) {
                dmg *= Config_Pc_Damage.Other_To_isKnightpc;
            }
            else if (this._pc.isWizard()) {
                dmg *= Config_Pc_Damage.Other_To_isWizardpc;
            }
            else if (this._pc.isElf()) {
                dmg *= Config_Pc_Damage.Other_To_isElfpc;
            }
            else if (this._pc.isDarkelf()) {
                dmg *= Config_Pc_Damage.Other_To_isDarkelfpc;
            }
            else if (this._pc.isDragonKnight()) {
                dmg *= Config_Pc_Damage.Other_To_isDragonKnightpc;
            }
            else if (this._pc.isIllusionist()) {
                dmg *= Config_Pc_Damage.Other_To_isIllusionistpc;
            }
            if (this._targetPc.getPVPdmgReduction() > 0) {
                dmg -= (double)this._targetPc.getPVPdmgReduction();
            }
            if (this._pc.getPVPdmg() != 0) {
                dmg += (double)this._pc.getPVPdmg();
            }
            if (this._pc.get_weaknss() == 1) {
                dmg += 3.0;
            }
            else if (this._pc.get_weaknss() == 2) {
                dmg += 8.0;
            }
            else if (this._pc.get_weaknss() == 3) {
                dmg += 15.0;
            }
            if (this._pc.hasSkillEffect(8895) && L1AttackPc._random.nextInt(100) + 1 <= 3) {
                PeepCard.BackMagic(this._pc, this._targetPc);
            }
            if (this._pc.hasSkillEffect(8863)) {
                this._damage += 5;
            }
            if (this._targetPc.hasSkillEffect(8867)) {
                dmg = 0.0;
            }
            if (this._pc.hasSkillEffect(8871)) {
                dmg += (double)((int)dmg + this._pc.getStr());
            }
            if (this._pc.hasSkillEffect(8873)) {
                this._targetPc.receiveDamage(this._pc, (double)(200 + L1AttackPc._random.nextInt(150) + 1), false, false);
                L1PcInstance pc = (L1PcInstance)this._target;
                L1SpawnUtil.spawnEffect(81162, 2, pc.getX(), pc.getY(), pc.getMapId(), pc, 0);
                pc.setSkillEffect(87, 3000);
                pc.sendPackets(new S_Paralysis(5, true));
                pc.killSkillEffectTimer(8873);
            }
            if (this._targetPc.isIllusionist() && this._targetPc.getlogpcpower_SkillFor4() != 0) {
                int hp = this._targetPc.getMaxHp() / this._targetPc.getCurrentHp();
                if (hp > 1) {
                    if (hp > 10) {
                        hp = 10;
                    }
                    if (RandomArrayList.getInc(100, 1) <= this._targetPc.getlogpcpower_SkillFor4()) {
                        dmg -= (double)(hp * 40);
                        this._targetPc.sendPackets(new S_SystemMessage("觸發 痛苦化身 減免了" + hp * 40 + "滴傷害。"));
                        this._targetPc.sendPackets(new S_SkillSound(this._targetPc.getId(), 5377));
                    }
                }
            }
            if (this._pc.isIllusionist() && this._pc.getlogpcpower_SkillFor5() != 0 && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor5()) {
                this._targetPc.setSkillEffect(8007, 2000);
                this._targetPc.sendPackets(new S_SystemMessage("對方發動 冰封心智 使你無法攻擊二秒 。"));
                this._pc.sendPackets(new S_SystemMessage("觸發 冰封心智"));
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
            }
            if (this._targetPc.hasSkillEffect(9055)) {
                dmg = 0.0;
            }
            if (this._targetPc.isCrown() && this._targetPc.getlogpcpower_SkillFor1() != 0 && !this._targetPc.hasSkillEffect(9055) && RandomArrayList.getInc(100, 1) <= this._targetPc.getlogpcpower_SkillFor1()) {
                this._targetPc.setSkillEffect(9055, 1000);
                this._targetPc.sendPackets(new S_SkillSound(this._targetPc.getId(), 2234));
                this._targetPc.broadcastPacketAll(new S_SkillSound(this._targetPc.getId(), 2234));
                this._targetPc.sendPackets(new S_SystemMessage("觸發 王者無敵1秒"));
            }
            if (this._targetPc.isCrown() && this._targetPc.isEsoteric()) {
                dmg = (double)(int)(dmg * (1.0 + (double)this._targetPc.getlogpcpower_SkillFor2() * 0.01));
            }
            if (this._pc.isCrown() && this._pc.isEsoteric()) {
                dmg = (double)(int)(dmg * (1.0 + (double)this._pc.getlogpcpower_SkillFor2() * 0.02));
            }
            if (this._pc.isCrown() && this._pc.getlogpcpower_SkillFor4() != 0 && this._pc.getlogpcpower_SkillFor4() >= 1 && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor4()) {
                this._targetPc.setSkillEffect(4017, 1000);
                this._target.broadcastPacketX8(new S_SkillSound(this._target.getId(), 4184));
                this._targetPc.sendPackets(new S_SkillSound(this._target.getId(), 4184));
                this._targetPc.sendPackets(new S_Paralysis(6, true));
            }
            if (this._weaponType == 20 && this._pc.isTripleArrow() && this._pc.getlogpcpower_SkillFor1() != 0 && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor1()) {
                dmg += (double)(this._targetPc.getCurrentHp() * 2 / 170);
                this._pc.setTripleArrow(false);
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
                this._pc.sendPackets(new S_SystemMessage("觸發 射擊之箭"));
            }
            if (this._pc.hasSkillEffect(175) && this._weaponType != 20 && this._weaponType != 62 && this._pc.isElf() && this._pc.getlogpcpower_SkillFor3() != 0 && this._pc.getElfAttr() == 2 && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor3()) {
                dmg *= 3.0;
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
                this._pc.sendPackets(new S_SystemMessage("觸發精通能量 造成4倍傷害！"));
            }
            if (this._weaponType == 20 && this._pc.hasSkillEffect(166) && this._pc.getElfAttr() == 8 && this._pc.isElf() && this._pc.getlogpcpower_SkillFor3() != 0 && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor3()) {
                dmg *= 1.4;
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
                this._pc.sendPackets(new S_SystemMessage("觸發精通能量 造成2倍傷害！"));
            }
            if (this._targetPc.hasSkillEffect(168) && this._targetPc.isElf() && this._targetPc.getlogpcpower_SkillFor3() != 0 && this._targetPc.getElfAttr() == 1 && RandomArrayList.getInc(100, 1) <= this._targetPc.getlogpcpower_SkillFor3()) {
                dmg *= 0.3;
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
                this._targetPc.sendPackets(new S_SystemMessage("觸發 精通能量 減免傷害"));
            }
            if (this._pc.isElf() && this._pc.getlogpcpower_SkillFor4() != 0) {
                int rr = this._targetPc.getCurrentMp() / 100;
                if (RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor4()) {
                    this._targetPc.setCurrentMp(Math.max(this._targetPc.getCurrentMp() - rr, 0));
                }
                this._pc.sendPackets(new S_SystemMessage("觸發 吞噬魔力"));
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
            }
            if (this._pc.isElf() && this._pc.getlogpcpower_SkillFor5() != 0 && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor5()) {
                if (this._weaponType != 20 && this._weaponType != 62) {
                    dmg = (double)(int)(dmg * (1.0 + (double)this._pc.getlogpcpower_SkillFor5() * 0.03));
                }
                else {
                    dmg = (double)(int)(dmg * (1.0 + (double)this._pc.getlogpcpower_SkillFor5() * 0.02));
                }
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
                this._pc.sendPackets(new S_SystemMessage("觸發 傷害擊殺"));
            }
            if (this._pc.isWizard() && this._pc.getlogpcpower_SkillFor4() != 0 && this._pc.isEsoteric() && this._pc.getlogpcpower_SkillFor4() >= 1) {
                dmg = (double)(int)(dmg * ((double)this._pc.getlogpcpower_SkillFor4() * 0.03 + 1.0));
            }
            if (this._pc.isDarkelf() && this._pc.getlogpcpower_SkillFor5() != 0 && this._pc.isEsoteric()) {
                if (this._pc.getCurrentMp() > 2 * this._pc.getlogpcpower_SkillFor5()) {
                    dmg += (double)(5 * this._pc.getlogpcpower_SkillFor5());
                    this._pc.setCurrentMp(this._pc.getCurrentMp() - 2 * this._pc.getlogpcpower_SkillFor5());
                    this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 4592));
                    this._pc.broadcastPacketAll(new S_SkillSound(this._pc.getId(), 4592));
                }
                else {
                    this._pc.setEsoteric(false);
                    this._pc.sendPackets(new S_SystemMessage("\\fU關閉轉生技能(刀劍之影)"));
                }
            }
            if (this._pc.isDarkelf() && this._pc.getlogpcpower_SkillFor1() != 0) {
                boolean isSameAttr = false;
                if (this._targetPc.getHeading() == 0 && (this._pc.getHeading() == 7 || this._pc.getHeading() == 0 || this._pc.getHeading() == 1)) {
                    isSameAttr = true;
                }
                else if (this._targetPc.getHeading() == 7 && (this._pc.getHeading() == 6 || this._pc.getHeading() == 7 || this._pc.getHeading() == 0)) {
                    isSameAttr = true;
                }
                else if (this._targetPc.getHeading() == this._pc.getHeading() || this._targetPc.getHeading() == this._pc.getHeading() + 1 || this._targetPc.getHeading() == this._pc.getHeading() - 1) {
                    isSameAttr = true;
                }
                if (isSameAttr && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor1()) {
                    dmg = (double)(int)(dmg * 1.5);
                    this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
                    this._pc.sendPackets(new S_SystemMessage("觸發 背部襲擊 造成 1.5倍傷害"));
                }
            }
            if (this._pc.isDragonKnight() && this._pc.isFoeSlayer() && this._pc.getlogpcpower_SkillFor1() != 0 && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor1()) {
                dmg += (double)(this._target.getCurrentHp() * 5 / 100);
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
                this._pc.sendPackets(new S_SystemMessage("觸發 屠宰擊殺"));
            }
            if (this._pc.isDragonKnight() && this._pc.getlogpcpower_SkillFor2() != 0 && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor2()) {
                double chp = dmg;
                this._pc.setCurrentHp((int)((double)this._pc.getCurrentHp() + chp));
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 1609));
                this._pc.sendPackets(new S_SystemMessage("觸發 乾坤挪移 吸取 " + chp + "滴生命值"));
            }
            if (this._targetPc.isDragonKnight() && this._targetPc.getlogpcpower_SkillFor3() != 0 && RandomArrayList.getInc(100, 1) <= this._targetPc.getlogpcpower_SkillFor3()) {
                dmg -= (double)(int)dmg * ((double)this._targetPc.getlogpcpower_SkillFor3() * 0.01);
                this._pc.sendPackets(new S_SystemMessage("觸發 強之護鎧"));
            }
            if (this._pc.isDragonKnight() && this._pc.getlogpcpower_SkillFor4() != 0 && this._targetPc.getMaxHp() / 20 * 9 >= this._targetPc.getCurrentHp()) {
                double adddmg2 = 1.0;
                if (this._pc.getlogpcpower_SkillFor4() >= 1) {
                    adddmg2 += (double)this._pc.getlogpcpower_SkillFor4() * 0.3;
                }
                dmg *= adddmg2;
            }
            
            if (this._pc.isKnight() && this._pc.getlogpcpower_SkillFor4() != 0 && this._pc.getMaxHp() / 3 >= this._pc.getCurrentHp() && !this._pc.isEsoteric() && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor4()) {
                dmg *= 1.9;
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
                this._pc.sendPackets(new S_SystemMessage("觸發 狂暴致命給予3倍高傷害"));
            }
            int ran = L1AttackPc._random.nextInt(100) + 1;
            if (this._targetPc.getInventory().checkSkillType(113) && ran <= ConfigOther.armor_type1) {
                dmg *= 0.98;
            }
            if (this._targetPc.getInventory().checkSkillType(114) && ran <= ConfigOther.armor_type2) {
                dmg *= 0.95;
            }
            if (this._targetPc.getInventory().checkSkillType(115) && ran <= ConfigOther.armor_type3) {
                dmg *= 0.9;
            }
            if (this._targetPc.getInventory().checkSkillType(116) && ran <= ConfigOther.armor_type4) {
                dmg *= 0.85;
            }
            if (this._targetPc.getInventory().checkSkillType(117) && ran <= ConfigOther.armor_type5) {
                dmg *= 0.8;
            }

        
            if (this._pc.getInventory().checkSkillType(118) && ran <= ConfigOther.armor_type6) {
                dmg += 5.0;
            }
            if (this._pc.getInventory().checkSkillType(119) && ran <= ConfigOther.armor_type7) {
                dmg += 10.0;
            }
            if (this._pc.getInventory().checkSkillType(120) && ran <= ConfigOther.armor_type8) {
                dmg += 15.0;
            }
            if (this._pc.getInventory().checkSkillType(121) && ran <= ConfigOther.armor_type9) {
                dmg += 20.0;
            }
            if (this._pc.getInventory().checkSkillType(122) && ran <= ConfigOther.armor_type10) {
                dmg += 30.0;
            }
            if (this._pc.getInventory().checkSkillType(123) && ran <= ConfigOther.armor_type11) {
                dmg *= 1.2;
            }
            if (this._pc.getInventory().checkSkillType(124) && ran <= ConfigOther.armor_type12) {
                dmg *= 1.4;
            }
            if (this._pc.getInventory().checkSkillType(125) && ran <= ConfigOther.armor_type13) {
                dmg *= 1.6;
            }
            if (this._pc.getInventory().checkSkillType(126) && ran <= ConfigOther.armor_type14) {
                dmg *= 1.8;
            }
            if (this._pc.getInventory().checkSkillType(127) && ran <= ConfigOther.armor_type15) {
                dmg *= 2.0;
            }
            if (this._targetPc.getInventory().checkSkillType(128) && ran <= ConfigOther.armor_type16) {
                dmg = 0.0;
            }
            if (this._targetPc.getInventory().checkSkillType(129) && ran <= ConfigOther.armor_type17) {
                dmg = 0.0;
            }
            if (this._targetPc.getInventory().checkSkillType(130) && ran <= ConfigOther.armor_type18) {
                dmg = 0.0;
            }
            if (this._targetPc.getInventory().checkSkillType(131) && ran <= ConfigOther.armor_type19) {
                dmg = 0.0;
            }
            if (this._targetPc.getInventory().checkSkillType(132) && ran <= ConfigOther.armor_type20) {
                dmg = 0.0;
            }
        
            if (this._pc.getDmgup_b() > 0.0 && L1AttackPc._random.nextInt(100) + 1 <= this._pc.getDmgup_b_ran()) {
                dmg *= this._pc.getDmgup_b();
            }
            if (this._pc.get近戰爆擊發動率() > 0 && this._pc.get近戰爆擊倍率() > 0.0 && this._weaponType != 20 && this._weaponType != 62 && ran <= this._pc.get近戰爆擊發動率()) {
                dmg *= this._pc.get近戰爆擊倍率();
            }
            if (this._pc.get遠攻爆擊發動率() > 0 && this._pc.get遠攻爆擊倍率() > 0.0 && this._weaponType == 20 && this._weaponType == 62 && ran <= this._pc.get遠攻爆擊發動率()) {
                dmg *= this._pc.get遠攻爆擊倍率();
            }
            if (this._targetPc.getDmgReductionChance() > 0 && this._targetPc.getDmgReductionDmg() > 0 && ran <= this._targetPc.getDmgReductionChance()) {
                dmg -= (double)this._targetPc.getDmgReductionDmg();
            }
            if (this._pc.get吸取HP機率() > 0 && this._pc.get吸取HP固定值() > 0 && this._pc.get吸取HP隨機值() > 0 && this._pc.get吸取HP動畫() > 0 && ran <= this._pc.get吸取HP機率()) {
                this._pc.setCurrentHp((int)(short)(this._pc.getCurrentHp() + this._pc.get吸取HP固定值() + L1AttackPc._random.nextInt(this._pc.get吸取HP隨機值()) + 1));
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), this._pc.get吸取HP動畫()));
                this._pc.broadcastPacketAll(new S_SkillSound(this._pc.getId(), this._pc.get吸取HP動畫()));
            }
            if (this._pc.get吸取MP機率() > 0 && this._pc.get吸取MP固定值() > 0 && this._pc.get吸取MP隨機值() > 0 && this._pc.get吸取MP動畫() > 0 && ran <= this._pc.get吸取MP機率()) {
                this._pc.setCurrentMp((int)(short)(this._pc.getCurrentMp() + this._pc.get吸取MP固定值() + L1AttackPc._random.nextInt(this._pc.get吸取MP隨機值()) + 1));
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), this._pc.get吸取MP動畫()));
                this._pc.broadcastPacketAll(new S_SkillSound(this._pc.getId(), this._pc.get吸取MP動畫()));
            }
            if (this._pc.get攻擊力發動機率() > 0 && this._pc.get攻擊力固定值() > 0 && this._pc.get攻擊力隨機值() > 0 && this._pc.get攻擊力動畫() > 0 && ran <= this._pc.get攻擊力發動機率()) {
                dmg += (double)(this._pc.get攻擊力固定值() + L1AttackPc._random.nextInt(this._pc.get攻擊力隨機值()) + 1);
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), this._pc.get攻擊力動畫()));
                this._pc.broadcastPacketAll(new S_SkillSound(this._pc.getId(), this._pc.get攻擊力動畫()));
            }
            if (this._pc.getpolyactidran() > 0 && ran <= this._pc.getpolyactidran()) {
                dmg += (double)(this._pc.getpolyactiddmg() + L1AttackPc._random.nextInt(this._pc.getpolyactiddmg1()) + 1);
                this._pc.sendPacketsAll(new S_DoActionGFX(this._pc.getId(), this._pc.getpolyactidactid()));
            }
            for (L1ItemInstance item : this._targetPc.getInventory().getItems()) {
                if (item.getItemId() == 400041 && item.isEquipped()) {
                    Random random = new Random();
                    int r = random.nextInt(100) + 1;
                    if (item.getEnchantLevel() * 2 >= r) {
                        dmg -= 50.0;
                        this._targetPc.sendPacketsAll(new S_SkillSound(this._targetPc.getId(), 6320));
                    }
                    else {
                        continue;
                    }
                }
            }
            boolean dmgX2 = false;
            if (!this._targetPc.getSkillisEmpty() && this._targetPc.getSkillEffect().size() > 0) {
                try {
                    for (Integer key : this._targetPc.getSkillEffect()) {
                        Integer integer = L1AttackList.SKD3.get(key);
                        if (integer != null) {
                            if (integer.equals(key)) {
                                dmgX2 = true;
                            }
                            else {
                                dmg += (double)(int)integer;
                            }
                        }
                    }
                }
                catch (ConcurrentModificationException ex) {}
                catch (Exception e) {
                    L1AttackPc._log.error(e.getLocalizedMessage(), e);
                }
            }
            if (dmgX2) {
                dmg /= ConfigOther.IMMUNE_TO_HARM;
            }
            if (dmg <= 0.0) {
                this._isHit = false;
                this._drainHp = 0;
            }
            return (int)dmg;
        }
    }
    
    private int c3_power() {
        if (this._pc.hasSkillEffect(7005)) {
            return 1;
        }
        else if (this._pc.hasSkillEffect(7006)) {
            return 2;
        }
        else {
            return 0;
        }
    }
    
    private int c3_power_to_pc(int type) {
        int damage = this.c3_power_dmg(type);
        int resist = 0;
        switch (type) {
            case 1: {
                resist = this._targetPc.getFire();
                if (resist > 0) {
                    damage = this.c3_power_dmg_down(damage, Math.min(100, resist));
                    break;
                }
                else if (resist < 0) {
                    damage = this.c3_power_dmg_up(damage, Math.min(0, resist));
                    break;
                }
                else {
                    break;
                }
            }
            case 2: {
                resist = this._targetPc.getWater();
                if (resist > 0) {
                    damage = this.c3_power_dmg_down(damage, Math.min(100, resist));
                    break;
                }
                else if (resist < 0) {
                    damage = this.c3_power_dmg_up(damage, Math.min(0, resist));
                    break;
                }
                else {
                    break;
                }
            }
        }
        return damage;
    }
    
    private int c3_power_to_npc(int type) {
        int damage = this.c3_power_dmg(type);
        switch (type) {
            case 1: {
                if (this._targetNpc instanceof L1MonsterInstance) {
                    L1MonsterInstance tgmob = (L1MonsterInstance)this._targetNpc;
                    if (!tgmob.isDead()) {
                        tgmob.receiveDamage(this._pc, (double)damage, 2);
                        tgmob.broadcastPacketX8(new S_DoActionGFX(tgmob.getId(), 2));
                        break;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
            case 2: {
                if (this._targetNpc instanceof L1MonsterInstance) {
                    L1MonsterInstance tgmob = (L1MonsterInstance)this._targetNpc;
                    if (!tgmob.isDead()) {
                        tgmob.receiveDamage(this._pc, (double)damage, 4);
                        tgmob.broadcastPacketX8(new S_DoActionGFX(tgmob.getId(), 2));
                        break;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
        return 0;
    }
    
    private int c3_power_dmg_down(int damage, int resist) {
        int r = 100 - resist;
        int dmg = damage * r / 100;
        return Math.max(10, dmg);
    }
    
    private int c3_power_dmg_up(int damage, int resist) {
        int dmg = damage - damage * resist / 100;
        return Math.abs(dmg);
    }
    
    private int c3_power_dmg(int type) {
        int damage = 0;
        int level = this._pc.getLevel();
        switch (type) {
            case 1: {
                if (level >= 50 && level < 70) {
                    damage = this.random_dmg(40, 100);
                    break;
                }
                else if (level >= 70 && level < 90) {
                    damage = this.random_dmg(50, 120);
                    break;
                }
                else if (level >= 90 && level < 110) {
                    damage = this.random_dmg(60, 140);
                    break;
                }
                else if (level >= 110 && level < 130) {
                    damage = this.random_dmg(70, 160);
                    break;
                }
                else if (level >= 130 && level < 150) {
                    damage = this.random_dmg(80, 180);
                    break;
                }
                else if (level >= 150 && level < 175) {
                    damage = this.random_dmg(90, 200);
                    break;
                }
                else if (level >= 175 && level < 190) {
                    damage = this.random_dmg(100, 250);
                    break;
                }
                else if (level >= 190 && level < 200) {
                    damage = this.random_dmg(110, 300);
                    break;
                }
                else {
                    damage = this.random_dmg(200, 300);
                    break;
                }
            }
            case 2: {
                if (level >= 50 && level < 70) {
                    damage = this.random_dmg(40, 100);
                    break;
                }
                else if (level >= 70 && level < 90) {
                    damage = this.random_dmg(50, 120);
                    break;
                }
                else if (level >= 90 && level < 110) {
                    damage = this.random_dmg(60, 140);
                    break;
                }
                else if (level >= 110 && level < 130) {
                    damage = this.random_dmg(70, 160);
                    break;
                }
                else if (level >= 130 && level < 150) {
                    damage = this.random_dmg(80, 180);
                    break;
                }
                else if (level >= 150 && level < 175) {
                    damage = this.random_dmg(90, 200);
                    break;
                }
                else if (level >= 175 && level < 190) {
                    damage = this.random_dmg(100, 250);
                    break;
                }
                else if (level >= 190 && level < 200) {
                    damage = this.random_dmg(110, 300);
                    break;
                }
                else {
                    damage = this.random_dmg(200, 300);
                    break;
                }
            }
        }
        return damage;
    }
    
    private int random_dmg(int i, int j) {
        return L1AttackPc._random.nextInt(j - i) + i;
    }
    
    private int calcNpcDamage() {
        if (this._targetNpc == null) {
            return 0;
        }
        else if (L1AttackMode.dmg0(this._targetNpc)) {
            this._isHit = false;
            return this._drainHp = 0;
        }
        else if (!this._isHit) {
            return 0;
        }
        else {
            int weaponMaxDamage = 0;
            if (this._targetNpc.getNpcTemplate().isSmall() && this._weaponSmall > 0) {
                weaponMaxDamage = this._weaponSmall;
            }
            else if (this._targetNpc.getNpcTemplate().isLarge() && this._weaponLarge > 0) {
                weaponMaxDamage = this._weaponLarge;
            }
            else if (this._weaponSmall > 0) {
                weaponMaxDamage = this._weaponSmall;
            }
            this._weaponDamage = this.weaponDamage1(weaponMaxDamage);
            if (this._pc.is_mazu()) {
                this._weaponDamage += 5;
            }
            this._weaponTotalDamage = this._weaponDamage + this._weaponAddDmg + this._weaponEnchant;
            this._weaponTotalDamage += this.calcMaterialBlessDmg();
            double dmg = this.weaponDamage2((double)this._weaponTotalDamage);
            dmg = this.pcDmgMode(dmg);
            dmg -= (double)this.calcNpcDamageReduction();
            dmg -= (double)this.calcPcDefense();
            dmg += this.weaponSkill(this._pc, this._targetNpc, (double)this._weaponTotalDamage);
            boolean isNowWar = false;
            int castleId = L1CastleLocation.getCastleIdByArea(this._targetNpc);
            if (castleId > 0) {
                isNowWar = ServerWarExecutor.get().isNowWar(castleId);
            }
            if (!isNowWar) {
                if (this._targetNpc instanceof L1PetInstance) {
                    dmg *= ConfigOther.pcdmgpet;
                }
                if (this._targetNpc instanceof L1SummonInstance) {
                    L1SummonInstance summon = (L1SummonInstance)this._targetNpc;
                    if (summon.isExsistMaster()) {
                        dmg *= ConfigOther.pcdmgsumm;
                    }
                }
            }
            else if (isNowWar) {
                if (this._targetNpc instanceof L1PetInstance) {
                    dmg *= ConfigOther.pcdmgpet_war;
                }
                if (this._targetNpc instanceof L1SummonInstance) {
                    L1SummonInstance summon = (L1SummonInstance)this._targetNpc;
                    if (summon.isExsistMaster()) {
                        dmg *= ConfigOther.pcdmgsumm_war;
                    }
                }
            }
            if (this._pc.getweapon_b_gfx_r() > 0 && RandomArrayList.getInc(100, 1) >= this._pc.getweapon_b_gfx_r()) {
                int[] giveItem = this._pc.getweapon_b_gfx();
                int rndItem = RandomArrayList.getInt(giveItem.length);
                int giveItemGet = giveItem[rndItem];
                this._pc.sendPacketsYN(new S_SkillSound(this._targetNpc.getId(), giveItemGet));
            }
            if (this._pc.getweaponran() != 0 && L1AttackPc._random.nextInt(100) + 1 <= this._pc.getweaponran()) {
                if (this._pc.getweapondmg() != 0) {
                    dmg += (double)(L1AttackPc._random.nextInt(this._pc.getweapondmg()) + 1);
                }
                if (this._pc.getweaponchp() != 0) {
                    this._pc.setCurrentHp(this._pc.getCurrentHp() + this._pc.getweaponchp());
                }
                if (this._pc.getweaponcmp() != 0) {
                    this._pc.setCurrentMp(this._pc.getCurrentMp() + this._pc.getweaponcmp());
                }
                if (this._pc.getweapongfx() != 0) {
                    this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), this._pc.getweapongfx()));
                }
            }
            if (this._targetNpc.getNpcTemplate().is_hard() && this._pc.getpenetrate() != 1 && (this._weaponType == 20 || this._weaponType == 62)) {
                dmg -= 3.0;
            }
            if (this._pc.getDmgdouble() > 0.0) {
                dmg *= this._pc.getDmgdouble();
            }
            if (this._pc.isCrown()) {
                dmg *= Config_Pc_Damage.Other_To_isCrownnpc;
            }
            else if (this._pc.isKnight()) {
                dmg *= Config_Pc_Damage.Other_To_isKnightnpc;
            }
            else if (this._pc.isWizard()) {
                dmg *= Config_Pc_Damage.Other_To_isWizardnpc;
            }
            else if (this._pc.isElf()) {
                dmg *= Config_Pc_Damage.Other_To_isElfnpc;
            }
            else if (this._pc.isDarkelf()) {
                dmg *= Config_Pc_Damage.Other_To_isDarkelfnpc;
            }
            else if (this._pc.isDragonKnight()) {
                dmg *= Config_Pc_Damage.Other_To_isDragonKnightnpc;
            }
            else if (this._pc.isIllusionist()) {
                dmg *= Config_Pc_Damage.Other_To_isIllusionistnpc;
            }
            if (this._weaponEnchant >= 10) {
                dmg += (double)(this._weaponEnchant - 9);
            }
            if (this._pc.get_weaknss() == 1) {
                dmg += 3.0;
            }
            else if (this._pc.get_weaknss() == 2) {
                dmg += 8.0;
            }
            else if (this._pc.get_weaknss() == 3) {
                dmg += 15.0;
            }
            if (!this._targetNpc.getNpcTemplate().is_boss()) {
                L1Npc template = NpcTable.get().getTemplate(this._targetNpc.getNpcId());
                if (template.getImpl().equals("L1Monster") && this._pc.hasSkillEffect(98765)) {
                    this._pc.killSkillEffectTimer(98765);
                    this._pc.setSkillEffect(98766, 2000);
                }
            }
            if (!this._targetNpc.getNpcTemplate().is_boss()) {
                L1Npc template = NpcTable.get().getTemplate(this._targetNpc.getNpcId());
                if (template.getImpl().equals("L1Monster") && !this._pc.isActived() && this._pc.get_followmaster() == null && this._pc.hasSkillEffect(6931)) {
                    this._pc.killSkillEffectTimer(6931);
                    this._pc.setSkillEffect(6932, 1000);
                }
            }
            if (this._pc.hasSkillEffect(7951) && !this._pc.isGm() && !this._targetNpc.getNpcTemplate().is_boss() && !isInWarAreaAndWarTime(this._pc)) {
                this._pc.killSkillEffectTimer(7951);
                this._pc.setSkillEffect(7952, 2000);
            }
            if (this._weaponRange != -1 && this._targetNpc.hasSkillEffect(112) && !this._targetNpc.getNpcTemplate().is_boss()) {
                dmg *= 1.58;
            }
            if (this._pc.hasSkillEffect(8863)) {
                this._damage += 5;
            }
            if (this._pc.hasSkillEffect(8871)) {
                dmg += (double)((int)dmg + this._pc.getStr());
            }
            if (this._pc.getDmgup_b() > 0.0 && L1AttackPc._random.nextInt(100) + 1 <= this._pc.getDmgup_b_ran()) {
                dmg *= this._pc.getDmgup_b();
            }
            if (!this._pc.getDolls().isEmpty()) {
                for (L1DollInstance doll : this._pc.getDolls().values()) {
                    doll.startDollSkill(this._targetNpc, dmg);
                }
            }
            if (this._pc.isCrown() && this._pc.isEsoteric()) {
                dmg = (double)(int)(dmg * (1.0 + (double)this._pc.getlogpcpower_SkillFor2() * 0.02));
            }
            if (this._weaponType == 20 && this._pc.isTripleArrow() && this._pc.getlogpcpower_SkillFor1() != 0 && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor1()) {
                dmg += 110.0;
                this._pc.setTripleArrow(false);
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
                this._pc.sendPackets(new S_SystemMessage("觸發 射擊之箭"));
            }
            if (this._pc.hasSkillEffect(175) && this._weaponType != 20 && this._weaponType != 62 && this._pc.isElf() && this._pc.getlogpcpower_SkillFor3() != 0 && this._pc.getElfAttr() == 2 && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor3()) {
                dmg *= 3.0;
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
                this._pc.sendPackets(new S_SystemMessage("觸發精通元素 造成4倍傷害！"));
            }
            if (this._weaponType == 20 && this._pc.hasSkillEffect(166) && this._pc.getElfAttr() == 8 && this._pc.isElf() && this._pc.getlogpcpower_SkillFor3() != 0 && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor3()) {
                dmg *= 1.6;
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
                this._pc.sendPackets(new S_SystemMessage("觸發精通元素 造成2倍傷害！"));
            }
            if (this._pc.isElf() && this._pc.getlogpcpower_SkillFor5() != 0 && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor5()) {
                if (this._weaponType != 20 && this._weaponType != 62) {
                    dmg = (double)(int)(dmg * (1.0 + (double)this._pc.getlogpcpower_SkillFor5() * 0.03));
                }
                else {
                    dmg = (double)(int)(dmg * (1.0 + (double)this._pc.getlogpcpower_SkillFor5() * 0.02));
                }
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
                this._pc.sendPackets(new S_SystemMessage("觸發 傷害擊殺"));
            }
            if (this._pc.isWizard() && this._pc.getlogpcpower_SkillFor4() != 0 && this._pc.isEsoteric() && this._pc.getlogpcpower_SkillFor4() >= 1) {
                dmg = (double)(int)(dmg * ((double)this._pc.getlogpcpower_SkillFor4() * 0.03 + 1.0));
            }
            if (this._pc.isDarkelf() && this._pc.getlogpcpower_SkillFor5() != 0 && this._pc.isEsoteric()) {
                if (this._pc.getCurrentMp() > 2 * this._pc.getlogpcpower_SkillFor5()) {
                    dmg += (double)(5 * this._pc.getlogpcpower_SkillFor5());
                    this._pc.setCurrentMp(this._pc.getCurrentMp() - 2 * this._pc.getlogpcpower_SkillFor5());
                    this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 4592));
                    this._pc.broadcastPacketAll(new S_SkillSound(this._pc.getId(), 4592));
                }
                else {
                    this._pc.setEsoteric(false);
                    this._pc.sendPackets(new S_SystemMessage("\\fU關閉轉生技能(刀劍之影)"));
                }
            }
            if (this._pc.isDragonKnight() && this._pc.isFoeSlayer() && this._pc.getlogpcpower_SkillFor1() != 0 && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor1()) {
                dmg += (double)(this._targetNpc.getCurrentHp() * 3 / 100);
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
                this._pc.sendPackets(new S_SystemMessage("觸發 屠宰擊殺"));
            }
            if (this._pc.isDragonKnight() && this._pc.getlogpcpower_SkillFor2() != 0 && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor2()) {
                double chp = dmg;
                this._pc.setCurrentHp((int)((double)this._pc.getCurrentHp() + chp));
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 1609));
                this._pc.sendPackets(new S_SystemMessage("觸發 乾坤挪移 吸取 " + chp + "滴生命值"));
            }
            if (this._pc.isKnight() && this._pc.getlogpcpower_SkillFor4() != 0 && this._pc.getMaxHp() / 3 >= this._pc.getCurrentHp() && !this._pc.isEsoteric() && RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor4()) {
                dmg *= 1.9;
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
                this._pc.sendPackets(new S_SystemMessage("觸發 狂暴致命給予3倍高傷害"));
            }
            int ran = L1AttackPc._random.nextInt(100) + 1;
            if (this._pc.getInventory().checkSkillType(118) && ran <= ConfigOther.armor_type6) {
                dmg += 5.0;
            }
            if (this._pc.getInventory().checkSkillType(119) && ran <= ConfigOther.armor_type7) {
                dmg += 10.0;
            }
            if (this._pc.getInventory().checkSkillType(120) && ran <= ConfigOther.armor_type8) {
                dmg += 15.0;
            }
            if (this._pc.getInventory().checkSkillType(121) && ran <= ConfigOther.armor_type9) {
                dmg += 20.0;
            }
            if (this._pc.getInventory().checkSkillType(122) && ran <= ConfigOther.armor_type10) {
                dmg += 30.0;
            }
            if (this._pc.getInventory().checkSkillType(123) && ran <= ConfigOther.armor_type11) {
                dmg *= 1.2;
            }
            if (this._pc.getInventory().checkSkillType(124) && ran <= ConfigOther.armor_type12) {
                dmg *= 1.4;
            }
            if (this._pc.getInventory().checkSkillType(125) && ran <= ConfigOther.armor_type13) {
                dmg *= 1.6;
            }
            if (this._pc.getInventory().checkSkillType(126) && ran <= ConfigOther.armor_type14) {
                dmg *= 1.8;
            }
            if (this._pc.getInventory().checkSkillType(127) && ran <= ConfigOther.armor_type15) {
                dmg *= 2.0;
            }
            if (this._pc.get_followmaster() != null) {
                dmg /= 3.0;
            }
            if (this._pc.get近戰爆擊發動率() > 0 && this._pc.get近戰爆擊倍率() > 0.0 && this._weaponType != 20 && this._weaponType != 62 && ran <= this._pc.get近戰爆擊發動率()) {
                dmg *= this._pc.get近戰爆擊倍率();
            }
            if (this._pc.get遠攻爆擊發動率() > 0 && this._pc.get遠攻爆擊倍率() > 0.0 && this._weaponType == 20 && this._weaponType == 62 && ran <= this._pc.get遠攻爆擊發動率()) {
                dmg *= this._pc.get遠攻爆擊倍率();
            }
            if (this._pc.get吸取HP機率() > 0 && this._pc.get吸取HP固定值() > 0 && this._pc.get吸取HP隨機值() > 0 && this._pc.get吸取HP動畫() > 0 && ran <= this._pc.get吸取HP機率()) {
                this._pc.setCurrentHp((int)(short)(this._pc.getCurrentHp() + this._pc.get吸取HP固定值() + L1AttackPc._random.nextInt(this._pc.get吸取HP隨機值()) + 1));
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), this._pc.get吸取HP動畫()));
                this._pc.broadcastPacketAll(new S_SkillSound(this._pc.getId(), this._pc.get吸取HP動畫()));
            }
            if (this._pc.get吸取MP機率() > 0 && this._pc.get吸取MP固定值() > 0 && this._pc.get吸取MP隨機值() > 0 && this._pc.get吸取MP動畫() > 0 && ran <= this._pc.get吸取MP機率()) {
                this._pc.setCurrentMp((int)(short)(this._pc.getCurrentMp() + this._pc.get吸取MP固定值() + L1AttackPc._random.nextInt(this._pc.get吸取MP隨機值()) + 1));
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), this._pc.get吸取MP動畫()));
                this._pc.broadcastPacketAll(new S_SkillSound(this._pc.getId(), this._pc.get吸取MP動畫()));
            }
            if (this._pc.get攻擊力發動機率() > 0 && this._pc.get攻擊力固定值() > 0 && this._pc.get攻擊力隨機值() > 0 && this._pc.get攻擊力動畫() > 0 && ran <= this._pc.get攻擊力發動機率()) {
                dmg += (double)(this._pc.get攻擊力固定值() + L1AttackPc._random.nextInt(this._pc.get攻擊力隨機值()) + 1);
                this._pc.sendPackets(new S_SkillSound(this._pc.getId(), this._pc.get攻擊力動畫()));
                this._pc.broadcastPacketAll(new S_SkillSound(this._pc.getId(), this._pc.get攻擊力動畫()));
            }
            if (this._pc.getpolyactidran() > 0 && ran <= this._pc.getpolyactidran()) {
                dmg += (double)(this._pc.getpolyactiddmg() + L1AttackPc._random.nextInt(this._pc.getpolyactiddmg1()) + 1);
                this._pc.sendPacketsAll(new S_DoActionGFX(this._pc.getId(), this._pc.getpolyactidactid()));
            }
            if (this._targetNpc.hasSkillEffect(68)) {
                dmg /= ConfigOther.IMMUNE_TO_HARM;
            }
            if (dmg <= 0.0) {
                this._isHit = false;
                this._drainHp = 0;
            }
            return (int)dmg;
        }
    }
    
    private void dk_dmgUp() {
        if (this._pc.isDragonKnight() && this._weaponType2 == 18) {
            long h_time = Calendar.getInstance().getTimeInMillis() / 1000L;
            int random = L1AttackPc._random.nextInt(100);
            int weaponchance = 20;
            if (this._weapon.getItemId() == 410189) {
                weaponchance = 25;
            }
            if (this._pc.get_tmp_targetid() != this._targetId) {
                this._pc.set_weaknss(0, 0L);
                this._pc.sendPackets(new S_PacketBoxDk(0));
            }
            this._pc.set_tmp_targetid(this._targetId);
            switch (this._pc.get_weaknss()) {
                case 0: {
                    if (random < weaponchance) {
                        this._pc.set_weaknss(1, h_time);
                        this._pc.sendPackets(new S_PacketBoxDk(1));
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 1: {
                    if (random < weaponchance) {
                        this._pc.set_weaknss(1, h_time);
                        this._pc.sendPackets(new S_PacketBoxDk(1));
                        break;
                    }
                    else if (random >= weaponchance && random < weaponchance * 2) {
                        this._pc.set_weaknss(2, h_time);
                        this._pc.sendPackets(new S_PacketBoxDk(2));
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if (random < weaponchance) {
                        this._pc.set_weaknss(2, h_time);
                        this._pc.sendPackets(new S_PacketBoxDk(2));
                        break;
                    }
                    else if (random >= weaponchance && random < weaponchance * 2) {
                        this._pc.set_weaknss(3, h_time);
                        this._pc.sendPackets(new S_PacketBoxDk(3));
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 3: {
                    if (random < weaponchance) {
                        this._pc.set_weaknss(3, h_time);
                        this._pc.sendPackets(new S_PacketBoxDk(3));
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
        }
    }
    
    private double dmgUp() {
        double dmg = 0.0;
        if (this._pc.getSkillEffect().size() <= 0) {
            return dmg;
        }
        else {
            if (!this._pc.getSkillisEmpty()) {
                try {
                    if (this._weaponType != 20 && this._weaponType != 62) {
                        for (Integer key : this._pc.getSkillEffect()) {
                            Integer integer = L1AttackList.SKD1.get(key);
                            if (integer != null) {
                                dmg += (double)(int)integer;
                            }
                        }
                    }
                    else {
                        for (Integer key : this._pc.getSkillEffect()) {
                            Integer integer = L1AttackList.SKD2.get(key);
                            if (integer != null) {
                                dmg += (double)(int)integer;
                            }
                        }
                    }
                }
                catch (ConcurrentModificationException ex) {}
                catch (Exception e) {
                    L1AttackPc._log.error(e.getLocalizedMessage(), e);
                }
            }
            return dmg;
        }
    }
    
    private double weaponSkill(L1PcInstance pcInstance, L1Character character, double weaponTotalDamage) {
        double dmg = 0.0;
        dmg = WeaponSkillStart.start_weapon_skill(pcInstance, character, this._weapon, weaponTotalDamage);
        if (dmg != 0.0) {
            return dmg;
        }
        else {
            switch (this._weaponId) {
                case 124: {
                    dmg = L1WeaponSkill.getBaphometStaffDamage(this._pc, this._target);
                    break;
                }
                case 204:
                case 100204: {
                    L1WeaponSkill.giveFettersEffect(this._pc, this._target);
                    break;
                }
                case 260:
                case 263: {
                    dmg = L1WeaponSkill.getAreaSkillWeaponDamage(this._pc, this._target, this._weaponId);
                    break;
                }
                case 261: {
                    L1WeaponSkill.giveArkMageDiseaseEffect(this._pc, this._target);
                    break;
                }
                case 264: {
                    dmg = L1WeaponSkill.getLightningEdgeDamage(this._pc, this._target);
                    break;
                }
                case 410131: {
                    L1WeaponSkill.giveTurn_Undead(this._pc, this._target);
                    break;
                }
                default: {
                    dmg = L1WeaponSkill.getWeaponSkillDamage(this._pc, this._target, this._weaponId);
                    break;
                }
            }
            return dmg;
        }
    }
    
    private double calcBuffDamage(double dmg) {
        int random = L1AttackPc._random.nextInt(100) + 1;
        if (this._weaponType == 20) {
            return dmg;
        }
        else if (this._weaponType == 62) {
            return dmg;
        }
        else if (this._weaponType2 == 17) {
            return dmg;
        }
        else {
            if (this._pc.hasSkillEffect(171) && this._weaponType != 20 && random <= 33) {
                dmg *= 1.5;
            }
            if (this._pc.hasSkillEffect(102) && random <= 28) {
                dmg *= 1.6;
            }
            if (this._pc.hasSkillEffect(148)) {
                dmg += 4.0;
            }
            if (this._pc.hasSkillEffect(163)) {
                dmg += 6.0;
            }
            if (this._pc.hasSkillEffect(55)) {
                dmg += 5.0;
            }
            if (this._pc.hasSkillEffect(182)) {
                dmg += 10.0;
                this._pc.sendPacketsAll(new S_EffectLocation(this._targetX, this._targetY, 6591));
                this._pc.killSkillEffectTimer(182);
            }
            return dmg;
        }
    }
    
    private int calcMaterialBlessDmg() {
        int damage = 0;
        if (this._pc.getWeapon() != null) {
            int undead = this._targetNpc.getNpcTemplate().get_undead();
            switch (undead) {
                case 1:
                case 3:
                case 4: {
                    if (this._weaponMaterial == 14 || this._weaponMaterial == 17 || this._weaponMaterial == 22) {
                        damage += L1AttackPc._random.nextInt(20) + 1;
                    }
                    if (this._weaponBless == 0) {
                        damage += L1AttackPc._random.nextInt(4) + 1;
                    }
                    switch (this._weaponType) {
                        case 20:
                        case 62: {
                            break;
                        }
                        default: {
                            if (this._weapon.getHolyDmgByMagic() != 0) {
                                damage += this._weapon.getHolyDmgByMagic();
                                break;
                            }
                            else {
                                break;
                            }
                        }
                    }
                }
                case 2: {
                    if (this._weaponMaterial == 17 || this._weaponMaterial == 22) {
                        damage += L1AttackPc._random.nextInt(3) + 1;
                    }
                    if (this._weaponBless == 0) {
                        damage += L1AttackPc._random.nextInt(4) + 1;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 5: {
                    if (this._weaponMaterial == 14 || this._weaponMaterial == 17 || this._weaponMaterial == 22) {
                        damage += L1AttackPc._random.nextInt(20) + 1;
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
        }
        return damage;
    }
    
    private void soulHp() {
        switch (this._calcType) {
            case 1: {
                if (this._pc.isSoulHp() > 0) {
                    ArrayList<Integer> soulHp = new ArrayList();
                    soulHp = this._pc.get_soulHp();
                    int r = soulHp.get(0);
                    int min = soulHp.get(1);
                    int max = soulHp.get(2);
                    if (L1AttackPc._random.nextInt(100) < r) {
                        if (this._pc.isSoulHp() == 1) {
                            this._targetPc.sendPacketsAll(new S_SkillSound(this._targetPc.getId(), 11673));
                        }
                        else {
                            this._targetPc.sendPacketsAll(new S_SkillSound(this._targetPc.getId(), 11677));
                        }
                        int hpadd = L1AttackPc._random.nextInt(max - min) + min;
                        int newHp = this._pc.getCurrentHp() + hpadd;
                        if (newHp >= this._pc.getMaxHp()) {
                            this._pc.setCurrentHp(this._pc.getMaxHp());
                        }
                        else {
                            this._pc.setCurrentHp(newHp);
                        }
                        this._targetPc.receiveDamage(this._pc, (double)hpadd, false, true);
                        break;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
            case 2: {
                if (this._pc.isSoulHp() > 0 && this._targetNpc instanceof L1MonsterInstance) {
                    ArrayList<Integer> soulHp = new ArrayList();
                    soulHp = this._pc.get_soulHp();
                    int r = soulHp.get(0);
                    int min = soulHp.get(1);
                    int max = soulHp.get(2);
                    if (L1AttackPc._random.nextInt(100) < r) {
                        if (this._pc.isSoulHp() == 1) {
                            this._targetNpc.broadcastPacketAll(new S_SkillSound(this._targetNpc.getId(), 11673));
                        }
                        else {
                            this._targetNpc.broadcastPacketAll(new S_SkillSound(this._targetNpc.getId(), 11677));
                        }
                        int hpadd = L1AttackPc._random.nextInt(max - min) + min;
                        int newHp = this._pc.getCurrentHp() + hpadd;
                        if (newHp >= this._pc.getMaxHp()) {
                            this._pc.setCurrentHp(this._pc.getMaxHp());
                        }
                        else {
                            this._pc.setCurrentHp(newHp);
                        }
                        this._targetNpc.receiveDamage(this._pc, hpadd);
                        break;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
    }
    
    private void AttrAmuletEffect() {
        int rnd = this._pc.get_AttrAmulet_rnd();
        int dmg = this._pc.get_AttrAmulet_dmg();
        int gfxid = this._pc.get_AttrAmulet_gfxid();
        if (rnd <= 0) {
            return;
        }
        else {
            switch (this._calcType) {
                case 1: {
                    if (L1AttackPc._random.nextInt(1000) < rnd) {
                        if (this._targetPc.hasSkillEffect(68)) {
                            dmg = (int)((double)dmg / ConfigOther.IMMUNE_TO_HARM);
                        }
                        this._targetPc.sendPacketsAll(new S_SkillSound(this._targetPc.getId(), gfxid));
                        this._targetPc.receiveDamage(this._pc, (double)dmg, false, true);
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if (L1AttackPc._random.nextInt(1000) < rnd) {
                        if (this._targetNpc.hasSkillEffect(68)) {
                            dmg = (int)((double)dmg / ConfigOther.IMMUNE_TO_HARM);
                        }
                        this._targetNpc.broadcastPacketAll(new S_SkillSound(this._targetNpc.getId(), gfxid));
                        this._targetNpc.receiveDamage(this._pc, dmg);
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            return;
        }
    }
    
    private int calcAttrEnchantDmg() {
        int damage = 0;
        if (this._weaponType == 20 && this._weaponId != 190 && this._arrow != null && this._arrow.getItemId() >= 84077 && this._arrow.getItemId() <= 84080) {
            damage += 3;
        }
        if (this._weaponType == 20 && this._weaponId != 190 && this._arrow != null) {
            if (this._arrow.getItemId() == 84080) {
                this._arrowGfxid = 4579;
            }
            if (this._arrow.getItemId() == 84077) {
                this._arrowGfxid = 7191;
            }
            if (this._arrow.getItemId() == 84079) {
                this._arrowGfxid = 2546;
            }
            if (this._arrow.getItemId() == 84078) {
                this._arrowGfxid = 4432;
            }
        }
        int resist = 0;
        switch (this._calcType) {
            case 1: {
                switch (this._weaponAttrEnchantKind) {
                    case 1: {
                        resist = this._targetPc.getEarth();
                        break;
                    }
                    case 2: {
                        resist = this._targetPc.getFire();
                        break;
                    }
                    case 4: {
                        resist = this._targetPc.getWater();
                        break;
                    }
                    case 8: {
                        resist = this._targetPc.getWind();
                        break;
                    }
                }
            }
            case 2: {
                switch (this._weaponAttrEnchantKind) {
                    case 1: {
                        resist = this._targetNpc.getEarth();
                        break;
                    }
                    case 2: {
                        resist = this._targetNpc.getFire();
                        break;
                    }
                    case 4: {
                        resist = this._targetNpc.getWater();
                        break;
                    }
                    case 8: {
                        resist = this._targetNpc.getWind();
                        break;
                    }
                }
            }
        }
        int resistFloor = (int)(0.16 * (double)Math.abs(resist));
        if (resist < 0) {
            resistFloor *= -1;
        }
        double attrDeffence = (double)resistFloor / 32.0;
        double attrCoefficient = 1.0 - attrDeffence;
        damage = (int)((double)damage * attrCoefficient);
        return damage;
    }
    
    private void addPcPoisonAttack(L1Character target) {
        boolean isCheck = false;
        boolean isCheck2 = false;
        switch (this._weaponId) {
            case 0: {
                break;
            }
            case 13:
            case 14: {
                isCheck = true;
                break;
            }
            default: {
                if (this._pc.hasSkillEffect(98)) {
                    isCheck = true;
                }
                if (this._pc.hasSkillEffect(8864)) {
                    isCheck2 = true;
                    break;
                }
                else {
                    break;
                }
            }
        }
        int hp = 0;
        if (this._pc.isDarkelf() && this._pc.getlogpcpower_SkillFor2() != 0 && this._pc.getlogpcpower_SkillFor2() >= 1) {
            hp += this._pc.getlogpcpower_SkillFor2() * 15;
        }
        if (isCheck2) {
            L1DamagePoison.doInfection(this._pc, target, 1000, 50);
        }
        if (isCheck) {
            int chance = L1AttackPc._random.nextInt(100) + 1;
            if (chance <= 10) {
                L1DamagePoison.doInfection(this._pc, target, 3000, 5 + hp);
            }
        }
    }
    
    @Override
    public void action() {
        try {
            if (this._pc == null) {
                return;
            }
            else if (this._target == null) {
                return;
            }
            else {
                this._pc.setHeading(this._pc.targetDirection(this._targetX, this._targetY));
                if (this._weaponRange == -1) {
                    this.actionX1();
                }
                else {
                    this.actionX2();
                }
            }
        }
        catch (Exception e) {
            L1AttackPc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void actionX2() {
        try {
            if (this._isHit) {
                if (this._targetId > 0) {
                    this._pc.sendPacketsAll(new S_AttackPacketPc(this._pc, this._target, (int)this._attackType, this._damage));
                }
            }
            else if (this._targetId > 0) {
                this._pc.sendPacketsAll(new S_AttackPacketPc(this._pc, this._target));
            }
            else {
                this._pc.sendPacketsAll(new S_AttackPacketPc(this._pc));
            }
        }
        catch (Exception e) {
            L1AttackPc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void Imperius_Tshirt_Effect() {
        int rnd = this._pc.get_Imperius_Tshirt_rnd();
        int min = this._pc.get_Tshirt_drainingHP_min();
        int max = this._pc.get_Tshirt_drainingHP_max();
        if (rnd <= 0) {
            return;
        }
        else {
            int value = 0;
            switch (this._calcType) {
                case 1: {
                    if (L1AttackPc._random.nextInt(1000) < rnd) {
                        value = L1AttackPc._random.nextInt(max - min + 1) + min;
                        this._targetPc.sendPacketsAll(new S_SkillSound(this._targetPc.getId(), 11769));
                        this._targetPc.receiveDamage(this._pc, (double)value, false, true);
                        short newHp = (short)(this._pc.getCurrentHp() + value);
                        this._pc.setCurrentHp((int)newHp);
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if (L1AttackPc._random.nextInt(1000) < rnd) {
                        value = L1AttackPc._random.nextInt(max - min + 1) + min;
                        this._targetNpc.broadcastPacketAll(new S_SkillSound(this._targetNpc.getId(), 11769));
                        this._targetNpc.receiveDamage(this._pc, value);
                        short newHp = (short)(this._pc.getCurrentHp() + value);
                        this._pc.setCurrentHp((int)newHp);
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            return;
        }
    }
    
    private void actionX1() {
        try {
            if (this._pc.getpolyarrow() > 0) {
                this._arrowGfxid = this._pc.getpolyarrow();
            }
            if (this._pc.getTempCharGfx() >= 13715 && this._pc.getTempCharGfx() <= 13745) {
                this._arrowGfxid = 11762;
                this._stingGfxid = 11762;
            }
            if (this._isHit) {
                switch (this._weaponType) {
                    case 20: {
                        if (this._arrow != null) { 
                            this._pc.sendPacketsAll(new S_UseArrowSkill(this._pc, this._targetId, this._arrowGfxid, this._targetX, this._targetY, this._damage, 1));
                            this._pc.getInventory().removeItem(this._arrow, 1L);
                            break;
                        }
                        else if (this._weaponId == 190 || this._isWeaponSkillArrowFree) {
                            this._pc.sendPacketsAll(new S_UseArrowSkill(this._pc, 2349, this._targetX, this._targetY, 1)); // 使用魔法箭動畫
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 62: {
                        if (this._sting != null) {
                            this._pc.sendPacketsAll(new S_UseArrowSkill(this._pc, this._targetId, this._stingGfxid, this._targetX, this._targetY, this._damage, 1));
                            this._pc.getInventory().removeItem(this._sting, 1L);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            else if (this._targetId > 0) {
                switch (this._weaponType) {
                    case 20: {
                        if (this._arrow != null) { 
                            this._pc.sendPacketsAll(new S_UseArrowSkill(this._pc, this._arrowGfxid, this._targetX, this._targetY, 1));
                            this._pc.getInventory().removeItem(this._arrow, 1L);
                            break;
                        }
                        else if (this._weaponId == 190 || this._isWeaponSkillArrowFree) {
                            this._pc.sendPacketsAll(new S_UseArrowSkill(this._pc, this._targetId, 2349, this._targetX, this._targetY, this._damage, 1)); // 使用魔法箭動畫
                            break;
                        }
                        else {
                            this._pc.sendPacketsAll(new S_UseArrowSkill(this._pc));
                            break;
                        }
                    }
                    case 62: {
                        if (this._sting != null) {
                            this._pc.sendPacketsAll(new S_UseArrowSkill(this._pc, this._stingGfxid, this._targetX, this._targetY, 1));
                            this._pc.getInventory().removeItem(this._sting, 1L);
                            break;
                        }
                        else {
                            this._pc.sendPacketsAll(new S_UseArrowSkill(this._pc));
                            break;
                        }
                    }
                }
            }
            else {
                this._pc.sendPacketsAll(new S_UseArrowSkill(this._pc));
            }
        }
        catch (Exception e) {
            L1AttackPc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void commit() {
        if (this._isHit) {
            if (this._pc.dice_hp() != 0 && L1AttackPc._random.nextInt(100) + 1 <= this._pc.dice_hp()) {
                this._drainHp = this._pc.sucking_hp();
            }
            if (this._pc.dice_mp() != 0 && L1AttackPc._random.nextInt(100) + 1 <= this._pc.dice_mp()) {
                this._drainMana = this._pc.sucking_mp();
            }
            if (this._pc.has_powerid(6610)) {
                int rad = 3;
                int time = 5;
                if (L1AttackPc._random.nextInt(100) < rad && !this._pc.hasSkillEffect(998)) {
                    this._pc.setSkillEffect(998, time * 1000);
                    this._pc.sendPacketsAll(new S_Liquor(this._pc.getId(), 8));
                    this._pc.sendPacketsAll(new S_SkillSound(this._pc.getId(), 11817));
                }
            }
            int i = (int)((double)this._damage / Math.pow(10.0, 0.0) % 10.0) + ConfigOther.Attack_1;
            int k = (int)((double)this._damage / Math.pow(10.0, 1.0) % 10.0) + ConfigOther.Attack_2;
            int h = (int)((double)this._damage / Math.pow(10.0, 2.0) % 10.0) + ConfigOther.Attack_3;
            int s = (int)((double)this._damage / Math.pow(10.0, 3.0) % 10.0) + ConfigOther.Attack_4;
            int m = (int)((double)this._damage / Math.pow(10.0, 4.0) % 10.0) + ConfigOther.Attack_5;
            switch (this._calcType) {
                case 1: {
                    if (this._pc.lift() != 0) {
                        int counter = L1AttackPc._random.nextInt(this._pc.lift()) + 1;
                        StringBuffer sbr = new StringBuffer();
                        for (L1ItemInstance item : this._targetPc.getInventory().getItems()) {
                            if (item.getItem().getType2() == 2) {
                                if (!item.isEquipped()) {
                                    continue;
                                }
                                else {
                                    this._targetPc.getInventory().setEquipped(item, false, false, false);
                                    sbr.append("[").append(item.getNumberedName(1L)).append("]");
                                    if (--counter <= 0) {
                                        break;
                                    }
                                    else {
                                        continue;
                                    }
                                }
                            }
                        }
                        if (sbr.length() > 0) {
                            this._targetPc.sendPackets(new S_SystemMessage("以下裝備被對方卸除:" + sbr.toString()));
                            this._pc.sendPackets(new S_SystemMessage("成功卸除對方以下裝備:" + sbr.toString()));
                        }
                    }
                    this.commitPc();
                    if (this._pc.hasSkillEffect(1688) && !this._pc.isActived()) {
                        if (this._damage > 0 && this._damage < 10) {
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), i));
                            break;
                        }
                        else if (this._damage >= 10 && this._damage < 100) {
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), i));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), k));
                            break;
                        }
                        else if (this._damage >= 100 && this._damage < 1000) {
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), i));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), k));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), h));
                            break;
                        }
                        else if (this._damage >= 1000 && this._damage < 10000) {
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), i));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), k));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), h));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), s));
                            break;
                        }
                        else if (this._damage >= 10000) {
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), i));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), k));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), h));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), s));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), m));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if (this._pc.hasSkillEffect(1688) && !this._pc.isActived()) {
                        if (this._damage > 0 && this._damage < 10) {
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), i));
                        }
                        else if (this._damage >= 10 && this._damage < 100) {
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), i));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), k));
                        }
                        else if (this._damage >= 100 && this._damage < 1000) {
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), i));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), k));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), h));
                        }
                        else if (this._damage >= 1000 && this._damage < 10000) {
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), i));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), k));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), h));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), s));
                        }
                        else if (this._damage >= 10000) {
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), i));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), k));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), h));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), s));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), m));
                        }
                    }
                    this.commitNpc();
                    break;
                }
            }
        }
        if (!ConfigAlt.ALT_ATKMSG) {
            return;
        }
        else {
            switch (this._calcType) {
                case 1: {
                    if (!this._pc.isGm() && !this._targetPc.isGm()) {
                        return;
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if (!this._pc.isGm()) {
                        return;
                    }
                    else {
                        break;
                    }
                }
            }
            String srcatk = this._pc.getName();
            String tgatk = "";
            String hitinfo = "";
            String dmginfo = "";
            String x = "";
            switch (this._calcType) {
                case 1: {
                    tgatk = this._targetPc.getName();
                    hitinfo = "命中機率:" + this._hitRate + "% 剩餘hp:" + this._targetPc.getCurrentHp();
                    dmginfo = (this._isHit ? ("傷害:" + this._damage + " ") : "未命中 ");
                    x = srcatk + ">" + tgatk + " " + dmginfo + hitinfo;
                    if (this._pc.isGm()) {
                        this._pc.sendPackets(new S_ServerMessage(166, "對PC送出攻擊: " + x));
                    }
                    if (this._targetPc.isGm()) {
                        this._targetPc.sendPackets(new S_ServerMessage(166, "受到PC攻擊: " + x));
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    tgatk = this._targetNpc.getName();
                    hitinfo = "命中機率:" + this.hit_rnd + "% 剩餘hp:" + this._targetNpc.getCurrentHp();
                    dmginfo = (this._isHit ? ("傷害:" + this._damage + " ") : "未命中 ");
                    x = srcatk + ">" + tgatk + " " + dmginfo + hitinfo;
                    if (this._pc.isGm()) {
                        this._pc.sendPackets(new S_ServerMessage(166, "對NPC送出攻擊: " + x));
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            return;
        }
    }
    
    private void commitPc() {
        if (this._drainMana > 0 && this._targetPc.getCurrentMp() > 0) {
            if (this._drainMana > this._targetPc.getCurrentMp()) {
                this._drainMana = this._targetPc.getCurrentMp();
            }
            this._targetPc.receiveManaDamage(this._pc, this._drainMana);
            int newMp = this._pc.getCurrentMp() + this._drainMana;
            this._pc.setCurrentMp(newMp);
        }
        if (this._drainHp > 0) {
            short newHp = (short)(this._pc.getCurrentHp() + this._drainHp);
            this._pc.setCurrentHp((int)newHp);
        }
        this._targetPc.receiveDamage(this._pc, (double)this._damage, false, false);
    }
    
    private void commitNpc() {
        if (this._drainMana > 0) {
            int drainValue = this._targetNpc.drainMana(this._drainMana);
            if (drainValue > 0) {
                this._targetNpc.ReceiveManaDamage(this._pc, drainValue);
                int newMp = this._pc.getCurrentMp() + drainValue;
                this._pc.setCurrentMp(newMp);
            }
        }
        if (this._drainHp > 0) {
            short newHp = (short)(this._pc.getCurrentHp() + this._drainHp);
            this._pc.setCurrentHp((int)newHp);
        }
        this.damageNpcWeaponDurability();
        L1AttackList.accumulateNpcDamage(this._pc, this._targetNpc, this._damage);
        this._targetNpc.receiveDamage(this._pc, this._damage);
    }
    
    @Override
    public boolean isShortDistance() {
        boolean isShortDistance = true;
        if (this._weaponType == 20 || this._weaponType == 62) {
            isShortDistance = false;
        }
        return isShortDistance;
    }
    
    private void MoonAmuletEffect() {
        int rnd = this._pc.get_MoonAmulet_rnd();
        int dmgmin = this._pc.get_MoonAmulet_dmg_min();
        int dmgmax = this._pc.get_MoonAmulet_dmg_max();
        int gfxid = this._pc.get_MoonAmulet_gfxid();
        if (rnd <= 0) {
            return;
        }
        else {
            int damage = 0;
            switch (this._calcType) {
                case 1: {
                    if (L1AttackPc._random.nextInt(1000) < rnd) {
                        damage = L1AttackPc._random.nextInt(dmgmax - dmgmin + 1) + dmgmin;
                        if (this._targetPc.hasSkillEffect(68)) {
                            damage = (int)((double)damage / ConfigOther.IMMUNE_TO_HARM);
                        }
                        this._targetPc.sendPacketsAll(new S_SkillSound(this._targetPc.getId(), gfxid));
                        this._targetPc.receiveDamage(this._pc, (double)damage, false, true);
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if (L1AttackPc._random.nextInt(1000) < rnd) {
                        damage = L1AttackPc._random.nextInt(dmgmax - dmgmin + 1) + dmgmin;
                        if (this._targetNpc.hasSkillEffect(68)) {
                            damage = (int)((double)damage / ConfigOther.IMMUNE_TO_HARM);
                        }
                        this._targetNpc.broadcastPacketAll(new S_SkillSound(this._targetNpc.getId(), gfxid));
                        this._targetNpc.receiveDamage(this._pc, damage);
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            return;
        }
    }
    
    @Override
    public void commitCounterBarrier() {
        int damage = this.calcCounterBarrierDamage();
        if (damage == 0) {
            return;
        }
        else if (this._pc.getId() == this._target.getId()) {
            return;
        }
        else {
            if (this._pc.hasSkillEffect(68)) {
                damage = (int)((double)damage / ConfigOther.IMMUNE_TO_HARM);
            }
            this._pc.sendPacketsAll(new S_DoActionGFX(this._pc.getId(), 2));
            this._pc.sendPacketsAll(new S_SkillSound(this._target.getId(), 10710));
            this._pc.receiveDamage(this._target, (double)damage, false, true);
            return;
        }
    }
    
    private void damageNpcWeaponDurability() {
        if (this._calcType != 2) {
            return;
        }
        else if (!this._targetNpc.getNpcTemplate().is_hard()) {
            return;
        }
        else if (this._weaponType == 0) {
            return;
        }
        else if (this._weapon.getItem().get_canbedmg() == 0) {
            return;
        }
        else if (this._pc.hasSkillEffect(175)) {
            return;
        }
        else if (this._pc.getInventory().checkSkillTypebless(0) && this._weaponBless == 1) {
            return;
        }
        else {
            int random = L1AttackPc._random.nextInt(100) + 1;
            switch (this._weaponBless) {
                case 0: {
                    if (random < 3) {
                        this._pc.sendPackets(new S_ServerMessage(268, this._weapon.getLogName()));
                        this._pc.getInventory().receiveDamage(this._weapon);
                        this._pc.sendPacketsX8(new S_SkillSound(this._pc.getId(), 10712));
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 1:
                case 2: {
                    if (random < 10) {
                        this._pc.sendPackets(new S_ServerMessage(268, this._weapon.getLogName()));
                        this._pc.getInventory().receiveDamage(this._weapon);
                        this._pc.sendPacketsX8(new S_SkillSound(this._pc.getId(), 10712));
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            return;
        }
    }
    
    private void damagePcWeaponDurability() {
        if (this._calcType != 1) {
            return;
        }
        else if (this._weaponType == 0) {
            return;
        }
        else if (this._weaponType == 20) {
            return;
        }
        else if (this._weaponType == 62) {
            return;
        }
        else if (!this._targetPc.hasSkillEffect(89)) {
            return;
        }
        else if (this._pc.hasSkillEffect(175)) {
            return;
        }
        else {
            if (L1AttackPc._random.nextInt(100) + 1 <= 10) {
                this._pc.sendPackets(new S_ServerMessage(268, this._weapon.getLogName()));
                this._pc.getInventory().receiveDamage(this._weapon);
                this._pc.sendPacketsX8(new S_SkillSound(this._pc.getId(), 10712));
            }
            return;
        }
    }
    
    private static boolean isInWarAreaAndWarTime(L1PcInstance pc) {
        int castleId = L1CastleLocation.getCastleIdByArea(pc);
        return castleId != 0 && ServerWarExecutor.get().isNowWar(castleId);
    }
    
    @Override
    public void calcStaffOfMana() {
        switch (this._weaponId) {
            case 126:
            case 127:
            case 1012:
            case 1065: {
                int som_lvl = this._weaponEnchant + 3;
                if (som_lvl < 0) {
                    som_lvl = 0;
                }
                this._drainMana = Math.min(L1AttackPc._random.nextInt(som_lvl) + 1, 9);
                break;
            }
            case 259: {
                switch (this._calcType) {
                    case 1: {
                        if (this._targetPc.getMr() <= L1AttackPc._random.nextInt(100) + 1) {
                            this._drainMana = 1;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 2: {
                        if (this._targetNpc.getMr() <= L1AttackPc._random.nextInt(100) + 1) {
                            this._drainMana = 1;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                }
            }
        }
    }
}
