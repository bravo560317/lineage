package com.lineage.server.model;

import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_NPCPack;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_EffectLocation;
import java.util.Iterator;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_UseAttackSkill;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.skill.L1SkillMode;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.datatables.WeaponSkillTable;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;

public class L1WeaponSkill
{
    private static Random _random;
    private int _weaponId;
    private int _probability;
    private int _fixDamage;
    private int _randomDamage;
    private int _area;
    private int _skillId;
    private int _skillTime;
    private int _effectId;
    private int _effectTarget;
    private boolean _isArrowType;
    private int _attr;
    private int _weapon_lv;
    private int _weapon_addpro;
    private double _x_str;
    private double _x_dex;
    private double _x_int;
    private double _x_sp;
    private int _c_hp;
    private int _c_mp;
    private int _COUNTER_MAGIC;
    private int _gfxId;
    private int _gfxId2;
    private int _gfxId3;
    private int _gfxId4;
    private int _gfxId5;
    
    static {
        L1WeaponSkill._random = new Random();
    }
    
    public L1WeaponSkill(int weaponId, int probability, int fixDamage, int randomDamage, int area, int skillId, int skillTime, int effectId, int effectTarget, boolean isArrowType, int attr, int gfxId, int gfxId2, int gfxId3, int gfxId4, int gfxId5, int c_hp, int c_mp, double x_str, double x_dex, double x_int, double x_sp, int weapon_lv, int weapon_addpro, int COUNTER_MAGIC) {
        this._weaponId = weaponId;
        this._probability = probability;
        this._fixDamage = fixDamage;
        this._randomDamage = randomDamage;
        this._area = area;
        this._skillId = skillId;
        this._skillTime = skillTime;
        this._effectId = effectId;
        this._effectTarget = effectTarget;
        this._isArrowType = isArrowType;
        this._attr = attr;
        this._weapon_lv = weapon_lv;
        this._weapon_addpro = weapon_addpro;
        this._gfxId = gfxId;
        this._gfxId2 = gfxId2;
        this._gfxId3 = gfxId3;
        this._gfxId4 = gfxId4;
        this._gfxId5 = gfxId5;
        this._x_str = x_str;
        this._x_dex = x_dex;
        this._x_int = x_int;
        this._x_sp = x_sp;
        this._c_hp = c_hp;
        this._c_mp = c_mp;
        this._COUNTER_MAGIC = COUNTER_MAGIC;
    }
    
    public int getWeaponId() {
        return this._weaponId;
    }
    
    public int getProbability() {
        return this._probability;
    }
    
    public int getFixDamage() {
        return this._fixDamage;
    }
    
    public int getRandomDamage() {
        return this._randomDamage;
    }
    
    public int getArea() {
        return this._area;
    }
    
    public int getSkillId() {
        return this._skillId;
    }
    
    public int getSkillTime() {
        return this._skillTime;
    }
    
    public int getEffectId() {
        return this._effectId;
    }
    
    public int getEffectTarget() {
        return this._effectTarget;
    }
    
    public boolean isArrowType() {
        return this._isArrowType;
    }
    
    public int getAttr() {
        return this._attr;
    }
    
    public int getGfxId() {
        return this._gfxId;
    }
    
    public int getGfxId2() {
        return this._gfxId2;
    }
    
    public int getGfxId3() {
        return this._gfxId3;
    }
    
    public int getGfxId4() {
        return this._gfxId4;
    }
    
    public int getGfxId5() {
        return this._gfxId5;
    }
    
    public int getweapon_lv() {
        return this._weapon_lv;
    }
    
    public void setweapon_lv(int weapon_lv) {
        this._weapon_lv = weapon_lv;
    }
    
    public int getweapon_addpro() {
        return this._weapon_addpro;
    }
    
    public void setweapon_addpro(int weapon_addpro) {
        this._weapon_addpro = weapon_addpro;
    }
    
    public double get_x_str() {
        return this._x_str;
    }
    
    public void set_x_str(double i) {
        this._x_str = i;
    }
    
    public double get_x_dex() {
        return this._x_dex;
    }
    
    public void set_x_dex(double i) {
        this._x_dex = i;
    }
    
    public double get_x_int() {
        return this._x_int;
    }
    
    public void set_x_int(double i) {
        this._x_int = i;
    }
    
    public double get_x_sp() {
        return this._x_sp;
    }
    
    public void set_x_sp(double i) {
        this._x_sp = i;
    }
    
    public int get_c_hp() {
        return this._c_hp;
    }
    
    public void set_c_hp(int i) {
        this._c_hp = i;
    }
    
    public int get_c_mp() {
        return this._c_mp;
    }
    
    public void set_c_mp(int i) {
        this._c_mp = i;
    }
    
    public int get_COUNTER_MAGIC() {
        return this._COUNTER_MAGIC;
    }
    
    public void set_COUNTER_MAGIC(int i) {
        this._COUNTER_MAGIC = i;
    }
    
    public static double getWeaponSkillDamage(L1PcInstance pc, L1Character cha, int weaponId) {
        L1WeaponSkill weaponSkill = WeaponSkillTable.get().getTemplate(weaponId);
        L1ItemInstance weapon = pc.getWeapon();
        if (pc == null || cha == null || weaponSkill == null) {
            return 0.0;
        }
        else {
            int c_hp = weaponSkill.get_c_hp();
            if (c_hp != 0) {
                int pchp = pc.getCurrentHp();
                int tghp = cha.getCurrentHp();
                if (tghp >= c_hp) {
                    pchp += c_hp;
                    tghp -= c_hp;
                    pc.setCurrentHp(pchp);
                    cha.setCurrentHp(tghp);
                }
            }
            int c_mp = weaponSkill.get_c_mp();
            if (c_mp != 0) {
                int pcmp = pc.getCurrentMp();
                int tgmp = cha.getCurrentMp();
                if (tgmp >= c_mp) {
                    pcmp += c_mp;
                    tgmp -= c_mp;
                    pc.setCurrentMp(pcmp);
                    cha.setCurrentMp(tgmp);
                }
            }
            int id = 0;
            if (pc.getWeapon().getItem().getType2() == 1 && pc.getWeapon().getItemprobability() > 0) {
                id = pc.getWeapon().getItemprobability();
            }
            int lv = 0;
            if (weaponSkill.getweapon_lv() != 0) {
                if (weapon.getEnchantLevel() == weaponSkill.getweapon_lv()) {
                    lv = 1;
                }
                else if (weapon.getEnchantLevel() > weaponSkill.getweapon_lv()) {
                    lv = weapon.getEnchantLevel() - weaponSkill.getweapon_lv() + 1;
                }
            }
            int weaponskillpro = 0;
            if (pc.getweaponskillpro() > 0) {
                weaponskillpro = pc.getweaponskillpro();
            }
            int MDC = 0;
            if (pc.getweaponMDC() > 0) {
                MDC = pc.getweaponMDC();
            }
            int getWeaponSkillChance = 0;
            if (pc.getWeaponSkillChance() > 0) {
                getWeaponSkillChance = pc.getWeaponSkillChance();
            }
            int chance = L1WeaponSkill._random.nextInt(100) + 1;
            if (weaponSkill.getProbability() + MDC + id + getWeaponSkillChance + lv + weaponskillpro < chance) {
                return 0.0;
            }
            else if (cha.hasSkillEffect(31) && weaponSkill.get_COUNTER_MAGIC() == 0) {
                L1SkillUse.isUseCounterMagic1(cha);
                return 0.0;
            }
            else {
                int skillId = weaponSkill.getSkillId();
                if (skillId != 153 && cha.hasSkillEffect(153) && skillId != 34 && L1SkillMode.get().isCantErasEMagic(skillId)) {
                    cha.removeSkillEffect(153);
                }
                if (skillId != 0) {
                    L1Skills skill = SkillsTable.get().getTemplate(skillId);
                    L1SkillUse l1skilluse = new L1SkillUse();
                    int skillTime = weaponSkill.getSkillTime();
                    if (skill != null) {
                        l1skilluse.handleCommands(pc, skillId, cha.getId(), cha.getX(), cha.getY(), skillTime, 4);
                    }
                }
                int effectId = weaponSkill.getEffectId();
                if (effectId > 0) {
                    int chaId = 0;
                    if (weaponSkill.getEffectTarget() == 0) {
                        chaId = cha.getId();
                    }
                    else {
                        chaId = pc.getId();
                    }
                    if (weaponSkill.getGfxId() != 0) {
                        pc.sendPacketsYN(new S_SkillSound(cha.getId(), weaponSkill.getGfxId()));
                    }
                    if (weaponSkill.getGfxId2() != 0) {
                        pc.sendPacketsYN(new S_SkillSound(cha.getId(), weaponSkill.getGfxId2()));
                    }
                    if (weaponSkill.getGfxId3() != 0) {
                        pc.sendPacketsYN(new S_SkillSound(cha.getId(), weaponSkill.getGfxId3()));
                    }
                    if (weaponSkill.getGfxId4() != 0) {
                        pc.sendPacketsYN(new S_SkillSound(cha.getId(), weaponSkill.getGfxId4()));
                    }
                    if (weaponSkill.getGfxId5() != 0) {
                        pc.sendPacketsYN(new S_SkillSound(cha.getId(), weaponSkill.getGfxId5()));
                    }
                    boolean isArrowType = weaponSkill.isArrowType();
                    if (!isArrowType) {
                        pc.sendPacketsYN(new S_SkillSound(chaId, effectId));
                    }
                    else {
                        int[] data = { 1, 0, effectId, 6 };
                        S_UseAttackSkill packet = new S_UseAttackSkill(pc, cha.getId(), cha.getX(), cha.getY(), data, false);
                        pc.sendPackets(packet);
                        pc.broadcastPacketAll(packet);
                    }
                }
                double damage = 0.0;
                int randomDamage = weaponSkill.getRandomDamage();
                if (randomDamage != 0) {
                    damage = (double)L1WeaponSkill._random.nextInt(randomDamage);
                }
                int weaponskilldmg = 0;
                if (pc.getweaponskilldmg() > 0) {
                    weaponskilldmg = pc.getweaponskilldmg();
                }
                damage += (double)(weaponSkill.getFixDamage() + weaponskilldmg);
                int pcstr = pc.getStr();
                double dmgstr = weaponSkill.get_x_str();
                if (dmgstr != 0.0 && pcstr > 0) {
                    damage += (double)pcstr * dmgstr;
                }
                int pcdex = pc.getDex();
                double dmgdex = weaponSkill.get_x_dex();
                if (dmgdex != 0.0 && pcdex > 0) {
                    damage += (double)pcdex * dmgdex;
                }
                int pcint = pc.getInt();
                double dmgint = weaponSkill.get_x_int();
                if (dmgint != 0.0 && pcint > 0) {
                    damage += (double)pcint * dmgint;
                }
                int pcsp = pc.getSp();
                double dmgsp = weaponSkill.get_x_sp();
                if (dmgsp != 0.0 && pcsp > 0) {
                    damage += (double)pcsp * dmgsp;
                }
                int MD = 0;
                if (pc.getweaponMD() > 0) {
                    MD = pc.getweaponMD();
                }
                damage += (double)MD;
                int area = weaponSkill.getArea();
                if (area > 0 || area == -1) {
                    for (L1Object object : World.get().getVisibleObjects(cha, area)) {
                        if (object != null && object instanceof L1Character && object.getId() != pc.getId() && object.getId() != cha.getId() && (!(cha instanceof L1MonsterInstance) || object instanceof L1MonsterInstance) && ((!(cha instanceof L1PcInstance) && !(cha instanceof L1SummonInstance) && !(cha instanceof L1PetInstance)) || object instanceof L1PcInstance || object instanceof L1SummonInstance || object instanceof L1PetInstance || object instanceof L1MonsterInstance)) {
                            damage = calcDamageReduction(pc, (L1Character)object, damage, weaponSkill.getAttr());
                            if (damage <= 0.0) {
                                continue;
                            }
                            else if (object instanceof L1PcInstance) {
                                L1PcInstance targetPc = (L1PcInstance)object;
                                targetPc.sendPacketsAll(new S_DoActionGFX(targetPc.getId(), 2));
                                targetPc.receiveDamage(pc, (double)(int)damage, false, false);
                            }
                            else if (!(object instanceof L1SummonInstance) && !(object instanceof L1PetInstance) && !(object instanceof L1MonsterInstance)) {
                                continue;
                            }
                            else {
                                L1NpcInstance targetNpc = (L1NpcInstance)object;
                                targetNpc.broadcastPacketAll(new S_DoActionGFX(targetNpc.getId(), 2));
                                targetNpc.receiveDamage(pc, (int)damage);
                            }
                        }
                    }
                }
                if (pc.isKnight() && pc.getlogpcpower_SkillFor1() > 0) {
                    damage = (double)(int)(damage * (1.0 + (double)pc.getlogpcpower_SkillFor1() * 0.03));
                    if (pc.isGm()) {
                        pc.sendPackets(new S_SystemMessage("GM顯示 騎士 魔傷其發:" + damage));
                    }
                }
                return calcDamageReduction(pc, cha, damage, weaponSkill.getAttr());
            }
        }
    }
    
    public static double getBaphometStaffDamage(L1PcInstance pc, L1Character cha) {
        double dmg = 0.0;
        int chance = L1WeaponSkill._random.nextInt(100) + 1;
        if (14 >= chance) {
            int locx = cha.getX();
            int locy = cha.getY();
            int sp = pc.getSp();
            int intel = pc.getInt();
            dmg = (double)(intel + sp) * 1.8 + (double)L1WeaponSkill._random.nextInt(intel + sp) * 1.8;
            pc.sendPacketsAll(new S_EffectLocation(locx, locy, 129));
        }
        return calcDamageReduction(pc, cha, dmg, 1);
    }
    
    public static double getDiceDaggerDamage(L1PcInstance pc, L1PcInstance targetPc, L1ItemInstance weapon) {
        double dmg = 0.0;
        int chance = L1WeaponSkill._random.nextInt(100) + 1;
        if (3 >= chance) {
            dmg = (double)(targetPc.getCurrentHp() * 2 / 3);
            if ((double)targetPc.getCurrentHp() - dmg < 0.0) {
                dmg = 0.0;
            }
            String msg = weapon.getLogName();
            pc.sendPackets(new S_ServerMessage(158, msg));
            pc.getInventory().removeItem(weapon, 1L);
        }
        return dmg;
    }
    
    public static double getChaserDamage(L1PcInstance pc, L1Character cha) {
        double dmg = 0.0;
        int chance = L1WeaponSkill._random.nextInt(100) + 1;
        if (4 >= chance) {
            dmg = 4.0;
            //pc.sendPacketsAll(new S_EffectLocation(cha.getX(), cha.getY(), 7025));
        }
        return dmg;
    }
    
    public static double getKiringkuDamage(L1PcInstance pc, L1Character cha) {
        int dmg = 0;
        int dice = 5;
        int diceCount = 2;
        int value = 14;
        int kiringkuDamage = 0;
        int charaIntelligence = 0;
        if (pc.getWeapon().getItem().getItemId() == 270) {
            value = 16;
        }
        else {
            value = 14;
        }
        for (int i = 0; i < diceCount; ++i) {
            kiringkuDamage += L1WeaponSkill._random.nextInt(dice) + 1;
        }
        kiringkuDamage += value;
        int weaponAddDmg = 0;
        L1ItemInstance weapon = pc.getWeapon();
        if (weapon != null) {
            weaponAddDmg = weapon.getItem().getMagicDmgModifier();
        }
        kiringkuDamage += weaponAddDmg;
        int spByItem = pc.getSp() - pc.getTrueSp();
        charaIntelligence = pc.getInt() + spByItem - 12;
        if (charaIntelligence < 1) {
            charaIntelligence = 1;
        }
        double kiringkuCoefficientA = 1.0 + (double)charaIntelligence * 3.0 / 32.0;
        kiringkuDamage = (int)((double)kiringkuDamage * kiringkuCoefficientA);
        double kiringkuFloor = Math.floor((double)kiringkuDamage);
        dmg = (int)((double)dmg + (kiringkuFloor + (double)pc.getOriginalMagicDamage()));
        if (pc.hasSkillEffect(219)) {
            dmg += 10;
        }
        if (pc.getHeading() != pc.targetDirection(cha.getX(), cha.getY())) {
            pc.setHeading(pc.targetDirection(cha.getX(), cha.getY()));
            pc.sendPacketsAll(new S_ChangeHeading(pc));
        }
        if (pc.getTempCharGfx() == 13739 || pc.getTempCharGfx() == 13741) {
            L1Location loc = cha.getLocation();
            L1NpcInstance dummy = L1SpawnUtil.spawnS(loc, 86132, pc.get_showId(), 1, pc.getHeading());
            dummy.broadcastPacketAll(new S_NPCPack(dummy));
            dummy.broadcastPacketAll(new S_SkillSound(dummy.getId(), 13396));
        }
        else if (pc.getWeapon().getItem().getItemId() == 270) {
            pc.sendPacketsAll(new S_SkillSound(pc.getId(), 6983));
        }
        else {
            pc.sendPacketsAll(new S_SkillSound(pc.getId(), 7049));
        }
        return calcDamageReduction(pc, cha, (double)dmg, 0);
    }
    
    public static double getAreaSkillWeaponDamage(L1PcInstance pc, L1Character cha, int weaponId) {
        double dmg = 0.0;
        int probability = 0;
        int attr = 0;
        int chance = L1WeaponSkill._random.nextInt(100) + 1;
        if (weaponId == 263) {
            probability = 5;
            attr = 4;
        }
        else if (weaponId == 260) {
            probability = 4;
            attr = 8;
        }
        if (probability >= chance) {
            int sp = pc.getSp();
            int intel = pc.getInt();
            int area = 0;
            int effectTargetId = 0;
            int effectId = 0;
            L1Character areaBase = cha;
            double damageRate = 0.0;
            if (weaponId == 263) {
                area = 3;
                damageRate = 1.4;
                effectTargetId = cha.getId();
                effectId = 1804;
                areaBase = cha;
            }
            else if (weaponId == 260) {
                area = 4;
                damageRate = 1.5;
                effectTargetId = pc.getId();
                effectId = 758;
                areaBase = pc;
            }
            dmg = (double)(intel + sp) * damageRate + (double)L1WeaponSkill._random.nextInt(intel + sp) * damageRate;
            pc.sendPacketsAll(new S_SkillSound(effectTargetId, effectId));
            for (L1Object object : World.get().getVisibleObjects(areaBase, area)) {
                if (object != null && object instanceof L1Character && object.getId() != pc.getId() && object.getId() != cha.getId() && (!(cha instanceof L1MonsterInstance) || object instanceof L1MonsterInstance) && ((!(cha instanceof L1PcInstance) && !(cha instanceof L1SummonInstance) && !(cha instanceof L1PetInstance)) || object instanceof L1PcInstance || object instanceof L1SummonInstance || object instanceof L1PetInstance || object instanceof L1MonsterInstance)) {
                    dmg = calcDamageReduction(pc, (L1Character)object, dmg, attr);
                    if (dmg > 0.0) {
                        if (object instanceof L1PcInstance) {
                            L1PcInstance targetPc = (L1PcInstance)object;
                            targetPc.sendPacketsAll(new S_DoActionGFX(targetPc.getId(), 2));
                            targetPc.receiveDamage(pc, (double)(int)dmg, false, false);
                        }
                        else if (object instanceof L1SummonInstance || object instanceof L1PetInstance || object instanceof L1MonsterInstance) {
                            L1NpcInstance targetNpc = (L1NpcInstance)object;
                            targetNpc.broadcastPacketAll(new S_DoActionGFX(targetNpc.getId(), 2));
                            targetNpc.receiveDamage(pc, (int)dmg);
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        return calcDamageReduction(pc, cha, dmg, attr);
    }
    
    public static double getLightningEdgeDamage(L1PcInstance pc, L1Character cha) {
        double dmg = 0.0;
        int chance = L1WeaponSkill._random.nextInt(100) + 1;
        if (4 >= chance) {
            int sp = pc.getSp();
            int intel = pc.getInt();
            dmg = (double)(intel + sp) * 2.0 + (double)(L1WeaponSkill._random.nextInt(intel + sp) * 2);
            pc.sendPacketsAll(new S_SkillSound(cha.getId(), 10));
        }
        return calcDamageReduction(pc, cha, dmg, 8);
    }
    
    public static void giveArkMageDiseaseEffect(L1PcInstance pc, L1Character cha) {
        int chance = L1WeaponSkill._random.nextInt(1000) + 1;
        int probability = (5 - cha.getMr() / 10 * 5) * 10;
        if (probability == 0) {
            probability = 10;
        }
        if (probability >= chance) {
            L1SkillUse l1skilluse = new L1SkillUse();
            l1skilluse.handleCommands(pc, 56, cha.getId(), cha.getX(), cha.getY(), 0, 4);
        }
    }
    
    public static void giveTurn_Undead(L1PcInstance pc, L1Character cha) {
        L1ItemInstance weapon = pc.getWeapon();
        int chance = weapon.getEnchantLevel() + 3;
        if (L1WeaponSkill._random.nextInt(1000) <= chance * 10) {
            L1SkillUse l1skilluse = new L1SkillUse();
            l1skilluse.handleCommands(pc, 18, cha.getId(), cha.getX(), cha.getY(), 0, 4);
        }
    }
    
    public static void giveFettersEffect(L1PcInstance pc, L1Character cha) {
        int fettersTime = 8;
        int chance = 1;
        if (isFreeze(cha)) {
            return;
        }
        else {
            if (L1WeaponSkill._random.nextInt(1000) <= chance * 10) {
                L1SpawnUtil.spawnEffect(81182, fettersTime, cha.getX(), cha.getY(), pc.getMapId(), pc, 0);
                if (cha instanceof L1PcInstance) {
                    L1PcInstance targetPc = (L1PcInstance)cha;
                    targetPc.setSkillEffect(4000, fettersTime * 1000);
                    targetPc.sendPacketsAll(new S_SkillSound(targetPc.getId(), 4184));
                    targetPc.sendPackets(new S_Paralysis(6, true));
                }
                else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
                    L1NpcInstance npc = (L1NpcInstance)cha;
                    npc.setSkillEffect(4000, fettersTime * 1000);
                    npc.broadcastPacketAll(new S_SkillSound(npc.getId(), 4184));
                    npc.setPassispeed(0);
                }
            }
            return;
        }
    }
    
    public static double calcDamageReduction(L1PcInstance pc, L1Character cha, double dmg, int attr) {
        if (isFreeze(cha)) {
            return 0.0;
        }
        else {
            int TotalMagicHit = 0;
            Integer magichit = L1AttackList.INTH.get((Integer)(int)pc.getInt());
            if (magichit != null) {
                TotalMagicHit += magichit;
            }
            if (pc.getBaseInt() >= 25 && pc.getBaseInt() <= 44) {
                TotalMagicHit += (pc.getBaseInt() - 15) / 10;
            }
            else if (pc.getBaseInt() >= 45) {
                TotalMagicHit += 5;
            }
            TotalMagicHit += pc.getOriginalMagicHit();
            int mr = cha.getMr();
            double mrFloor = 0.0;
            if (mr < 100) {
                mrFloor = Math.floor((double)((mr - TotalMagicHit) / 2));
            }
            else if (mr >= 100) {
                mrFloor = Math.floor((double)((mr - TotalMagicHit) / 10));
            }
            double mrCoefficient = 0.0;
            if (mr < 100) {
                mrCoefficient = 1.0 - 0.01 * mrFloor;
            }
            else if (mr >= 100) {
                mrCoefficient = 0.6 - 0.01 * mrFloor;
            }
            dmg *= mrCoefficient;
            int resist = 0;
            if (attr == 1) {
                resist = cha.getEarth();
            }
            else if (attr == 2) {
                resist = cha.getFire();
            }
            else if (attr == 4) {
                resist = cha.getWater();
            }
            else if (attr == 8) {
                resist = cha.getWind();
            }
            int resistFloor = (int)(0.16 * (double)Math.abs(resist));
            if (resist >= 0) {
                resistFloor *= 1;
            }
            else {
                resistFloor *= -1;
            }
            double attrDeffence = (double)resistFloor / 32.0;
            dmg *= 1.0 - attrDeffence;
            return dmg;
        }
    }
    
    public static boolean isFreeze(L1Character cha) {
        return cha.hasSkillEffect(78) || cha.hasSkillEffect(50) || cha.hasSkillEffect(157);
    }
}
