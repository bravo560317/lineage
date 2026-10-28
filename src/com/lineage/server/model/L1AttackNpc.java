package com.lineage.server.model;

import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigAlt;
import com.lineage.server.serverpackets.S_AttackPacketNpc;
import com.lineage.server.serverpackets.S_UseAttackSkill;
import com.lineage.server.serverpackets.S_UseArrowSkill;
import com.lineage.server.model.poison.L1ParalysisPoison;
import com.lineage.server.model.poison.L1SilencePoison;
import com.lineage.server.model.poison.L1DamagePoison;
import com.lineage.server.model.gametime.L1GameTimeClock;
import java.util.Iterator;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.config.ConfigOther;
import com.lineage.config.ConfigRate;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.william.MapNpcDmg;
import com.lineage.data.event.NpcMapDmg;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.types.Point;
import com.lineage.config.Config_Pc_Damage;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import org.apache.commons.logging.Log;
import com.lineage.server.model.Instance.L1DeInstance;


public class L1AttackNpc extends L1AttackMode
{
    private static final Log _log;
    L1ItemSpecialAttributeChar item_attr_char;
    Random random;
    
    static {
        _log = LogFactory.getLog(L1AttackNpc.class);
    }
    
    public L1AttackNpc(L1NpcInstance attacker, L1Character target) {
        this.item_attr_char = null;
        this.random = new Random();
        if (attacker == null) {
            return;
        }
        else if (target == null) {
            return;
        }
        else if (target.isDead()) {
            return;
        }
        else if (target.getCurrentHp() <= 0) {
            return;
        }
        else {
            this._npc = attacker;
            if (target instanceof L1PcInstance) {
                this._targetPc = (L1PcInstance)target;
                this._calcType = 3;
            }
            else if (target instanceof L1NpcInstance) {
                this._targetNpc = (L1NpcInstance)target;
                this._calcType = 4;
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
            switch (this._calcType) {
                case 3: {
                    this._isHit = this.calcPcHit();
                    break;
                }
                case 4: {
                    this._isHit = this.calcNpcHit();
                    break;
                }
            }
            return this._isHit;
        }
    }
    
    private boolean calcPcHit() {
        if (L1AttackMode.dmg0(this._targetPc)) {
            return false;
        }
        else if (this.calcEvasion()) {
            return false;
        }
        else if (this._targetPc.getattr_物理格檔() > 0 && L1AttackNpc._random.nextInt(100) < this._targetPc.getattr_物理格檔()) {
            return false;
        }
        else {
            this._hitRate += this._npc.getLevel() + 5;
            if (this._npc instanceof L1PetInstance) {
                this._hitRate += ((L1PetInstance)this._npc).getHitByWeapon();
            }
            this._hitRate += this._npc.getHitup();
            int attackerDice = L1AttackNpc._random.nextInt(20) + 1 + this._hitRate - 3;
            int tgChaDodgeDown = this._targetPc.get_dodge() * 10;
            if (tgChaDodgeDown > 0) {
                attackerDice -= tgChaDodgeDown;
            }
            int defenderDice = 0;
            int defenderValue = this._targetPc.getAc() * -1;
            if (this._targetPc.getAc() >= 0) {
                defenderDice = 10 - this._targetPc.getAc();
            }
            else if (this._targetPc.getAc() < 0) {
                defenderDice = 10 + L1AttackNpc._random.nextInt(defenderValue) + 1;
            }
            int fumble = this._hitRate;
            int critical = this._hitRate + 19;
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
            if (this._npc.getNpcTemplate().is_boss()) {
                this._hitRate += Config_Pc_Damage.BossMobHit_chance;
            }
            if (!this._npc.getNpcTemplate().is_boss()) {
                this._hitRate += Config_Pc_Damage.AllMobHit_chance;
            }
            if (this._targetPc.isCrown()) {
                this._hitRate += Config_Pc_Damage.Crown_ADD_HIT;
            }
            else if (this._targetPc.isKnight()) {
                this._hitRate += Config_Pc_Damage.Knight_ADD_HIT;
            }
            else if (this._targetPc.isElf()) {
                this._hitRate += Config_Pc_Damage.Elf_ADD_HIT;
            }
            else if (this._targetPc.isDarkelf()) {
                this._hitRate += Config_Pc_Damage.Darkelf_ADD_HIT;
            }
            else if (this._targetPc.isWizard()) {
                this._hitRate += Config_Pc_Damage.Wizard_ADD_HIT;
            }
            else if (this._targetPc.isDragonKnight()) {
                this._hitRate += Config_Pc_Damage.DragonKnight_ADD_HIT;
            }
            else if (this._targetPc.isIllusionist()) {
                this._hitRate += Config_Pc_Damage.Illusionist_ADD_HIT;
            }
            int rnd = L1AttackNpc._random.nextInt(100) + 1;
            if (this._npc.get_ranged() >= 10 && this._hitRate > rnd && this._npc.getLocation().getTileLineDistance(new Point(this._targetX, this._targetY)) >= 2) {
                return this.calcErEvasion();
            }
            else {
                return this._hitRate >= rnd;
            }
        }
    }
    
    private boolean calcNpcHit() {
        if (L1AttackMode.dmg0(this._targetNpc)) {
            return false;
        }
        else {
            this._hitRate += this._npc.getLevel() + 3;
            if (this._npc instanceof L1PetInstance) {
                this._hitRate += ((L1PetInstance)this._npc).getHitByWeapon();
            }
            this._hitRate += this._npc.getHitup();
            int attackerDice = L1AttackNpc._random.nextInt(20) + 1 + this._hitRate - 3;
            attackerDice += L1AttackMode.attackerDice(this._targetNpc);
            if (this._npc.getNpcTemplate().get_nameid().startsWith("BOSS")) {
                attackerDice += 30;
            }
            int defenderDice = 0;
            int defenderValue = this._targetNpc.getAc() * -1;
            if (this._targetNpc.getAc() >= 0) {
                defenderDice = 10 - this._targetNpc.getAc();
            }
            else if (this._targetNpc.getAc() < 0) {
                defenderDice = 10 + L1AttackNpc._random.nextInt(defenderValue) + 1;
            }
            int fumble = this._hitRate;
            int critical = this._hitRate + 19;
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
            int rnd = L1AttackNpc._random.nextInt(100) + 1;
            return this._hitRate >= rnd;
        }
    }
    
    @Override
    public int calcDamage() {
        switch (this._calcType) {
            case 3: {
                this._damage = this.calcPcDamage();
                break;
            }
            case 4: {
                this._damage = this.calcNpcDamage();
                break;
            }
        }
        return this._damage;
    }
    
    private double npcDmgMode(double dmg) {
        if (this._npc.getNpcTemplate().get_nameid().startsWith("BOSS")) {
            dmg *= 2.0;
        }
        if (L1AttackNpc._random.nextInt(100) < 15) {
            dmg *= 1.8;
        }
        dmg += (double)this._npc.getDmgup();
        if (this.isUndeadDamage()) {
            dmg *= 1.1;
        }
        dmg = (double)(int)(dmg * ((double)this.getLeverage() / 10.0));
        if (this._npc.isWeaponBreaked()) {
            dmg /= 2.0;
        }
        return dmg;
    }
    
    private int calcPcDamage() {
        if (this._targetPc == null) {
            return 0;
        }
        else if (L1AttackMode.dmg0(this._targetPc)) {
            this._isHit = false;
            return 0;
        }
        else if (!this._isHit) {
            return 0;
        }
        else {
            if (this._targetPc.hasSkillEffect(91)) {
                L1MagicMode magic = new L1MagicPc(this._targetPc, this._npc);
                boolean isProbability = magic.calcProbabilityMagic(91);
                boolean isShortDistance = this.isShortDistance();
                if (isProbability && isShortDistance) {
                    this.commitCounterBarrier();
                    this._npc.broadcastPacketAll(new S_SkillSound(this._targetPc.getId(), 10710));
                    return 0;
                }
            }
            int lvl = this._npc.getLevel();
            double dmg = 0.0;
            Integer dmgStr = L1AttackList.STRD.get((Integer)(int)this._npc.getStr());
            if (this._targetPc.getMapId() == 1936) {
                dmg = 30.0 + (double)this._npc.getStr() * 0.8 + (double)(int)dmgStr;
            }
            else {
                dmg = (double)L1AttackNpc._random.nextInt(lvl) + (double)this._npc.getStr() * 0.8 + (double)(int)dmgStr;
            }
            if (this._npc instanceof L1PetInstance) {
                dmg += (double)(lvl / 7);
                dmg += (double)((L1PetInstance)this._npc).getDamageByWeapon();
            }
            dmg = this.npcDmgMode(dmg);
            dmg -= (double)this.calcPcDefense();
            dmg -= (double)(this._targetPc.getDamageReductionByArmor() + this._targetPc.getother_ReductionDmg() + this._targetPc.getClan_ReductionDmg() + this._targetPc.get_reduction_dmg() + this._targetPc.getdolldamageReductionByArmor());
            dmg -= (double)this._targetPc.dmgDowe();
            if (this._targetPc.getClanid() != 0) {
                dmg -= L1AttackMode.getDamageReductionByClan(this._targetPc);
            }
            if (this._targetPc.hasSkillEffect(8867)) {
                dmg = 0.0;
            }
            if (NpcMapDmg.START) {
                double dmg2 = MapNpcDmg.getStrDmgSkill(this._npc, this._npc.getMapId());
                if (dmg2 != 0.0) {
                    dmg *= dmg2 / 100.0;
                }
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
            if (this._targetPc.hasSkillEffect(168) && this._targetPc.isElf() && this._targetPc.getlogpcpower_SkillFor3() != 0 && this._targetPc.getElfAttr() == 1 && RandomArrayList.getInc(100, 1) <= this._targetPc.getlogpcpower_SkillFor3()) {
                dmg *= 0.3;
                this._targetPc.sendPackets(new S_SkillSound(this._targetPc.getId(), 5377));
                this._targetPc.sendPackets(new S_SystemMessage("觸發 精通能量 減免傷害"));
            }
            if (this._targetPc.isDragonKnight() && this._targetPc.getlogpcpower_SkillFor3() != 0 && RandomArrayList.getInc(100, 1) <= this._targetPc.getlogpcpower_SkillFor3()) {
                dmg -= (double)(int)dmg * ((double)this._targetPc.getlogpcpower_SkillFor3() * 0.01);
                this._targetPc.sendPackets(new S_SystemMessage("觸發 強之護鎧"));
            }
            int ran = L1AttackNpc._random.nextInt(100) + 1;
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
            if (this._targetPc.getDmgReductionChance() > 0 && this._targetPc.getDmgReductionDmg() > 0 && ran <= this._targetPc.getDmgReductionChance()) {
                dmg -= (double)this._targetPc.getDmgReductionDmg();
            }
            if (this._targetPc.hasSkillEffect(68)) {
                dmg /= ConfigOther.IMMUNE_TO_HARM_NPC;
            }
            boolean isNowWar = false;
            int castleId = L1CastleLocation.getCastleIdByArea(this._npc);
            if (castleId > 0) {
                isNowWar = ServerWarExecutor.get().isNowWar(castleId);
            }
            if (!isNowWar) {
                if (this._npc instanceof L1PetInstance) {
                    dmg *= ConfigOther.petdmgotherpc;
                }
                if (this._npc instanceof L1SummonInstance) {
                    L1SummonInstance summon = (L1SummonInstance)this._npc;
                    if (summon.isExsistMaster()) {
                        dmg *= ConfigOther.summondmgotherpc;
                    }
                }
            }
            else if (isNowWar) {
                if (this._npc instanceof L1PetInstance) {
                    dmg *= ConfigOther.petdmgotherpc_war;
                }
                if (this._npc instanceof L1SummonInstance) {
                    L1SummonInstance summon = (L1SummonInstance)this._npc;
                    if (summon.isExsistMaster()) {
                        dmg *= ConfigOther.summondmgotherpc_war;
                    }
                }
            }
            if (dmg <= 0.0) {
                this._isHit = false;
            }
            this.addNpcPoisonAttack(this._targetPc);
            if (!this._isHit) {
                dmg = 0.0;
            }
            return (int)dmg;
        }
    }
    
    private int calcNpcDamage() {
        if (this._targetNpc == null) {
            return 0;
        }
        else if (L1AttackMode.dmg0(this._targetNpc)) {
            this._isHit = false;
            return 0;
        }
        else {
            int lvl = this._npc.getLevel();
            double dmg = 0.0;
            if (this._npc instanceof L1PetInstance) {
                dmg = (double)(L1AttackNpc._random.nextInt(this._npc.getNpcTemplate().get_level()) + this._npc.getStr() / 2 + 1);
                dmg += (double)(lvl / 14);
                dmg += (double)((L1PetInstance)this._npc).getDamageByWeapon();
                if (ConfigOther.petdmgother > 0.0) {
                    dmg *= ConfigOther.petdmgother;
                }
            }
            else if (this._npc instanceof L1SummonInstance) {
                Integer dmgStr = L1AttackList.STRD.get((Integer)(int)this._npc.getStr());
                dmg = (double)(L1AttackNpc._random.nextInt(lvl) + this._npc.getStr() / 2 + dmgStr);
                if (ConfigOther.summondmgother > 0.0) {
                    dmg *= ConfigOther.summondmgother;
                }
            }
            else {
                Integer dmgStr = L1AttackList.STRD.get((Integer)(int)this._npc.getStr());
                dmg = (double)(L1AttackNpc._random.nextInt(lvl) + this._npc.getStr() / 2 + dmgStr);
            }
            dmg = this.npcDmgMode(dmg);
            dmg -= (double)this.calcNpcDamageReduction();
            this.addNpcPoisonAttack(this._targetNpc);
            if (this._targetNpc.hasSkillEffect(68)) {
                dmg /= 2.0;
            }
            if (dmg <= 0.0) {
                this._isHit = false;
            }
            if (!this._isHit) {
                dmg = 0.0;
            }
            return (int)dmg;
        }
    }
    
    private boolean isUndeadDamage() {
        boolean flag = false;
        int undead = this._npc.getNpcTemplate().get_undead();
        boolean isNight = L1GameTimeClock.getInstance().currentTime().isNight();
        if (isNight) {
            switch (undead) {
                case 1:
                case 3:
                case 4: {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
    
    private void addNpcPoisonAttack(L1Character target) {
        switch (this._npc.getNpcTemplate().get_poisonatk()) {
            case 1: {
                if (15 >= L1AttackNpc._random.nextInt(100) + 1) {
                    L1DamagePoison.doInfection(this._npc, target, 3000, 20);
                    break;
                }
                else {
                    break;
                }
            }
            case 2: {
                if (15 >= L1AttackNpc._random.nextInt(100) + 1) {
                    L1SilencePoison.doInfection(target);
                    break;
                }
                else {
                    break;
                }
            }
            case 4: {
                if (15 >= L1AttackNpc._random.nextInt(100) + 1) {
                    L1ParalysisPoison.doInfection(target, 20000, 45000);
                    break;
                }
                else {
                    break;
                }
            }
        }
    }
    
    @Override
    public void action() {
        try {
            if (this._npc == null) {
                return;
            }
            else if (this._npc.isDead()) {
                return;
            }
            else {
                this._npc.setHeading(this._npc.targetDirection(this._targetX, this._targetY));
                boolean isLongRange = this._npc.getLocation().getTileLineDistance(new Point(this._targetX, this._targetY)) > 1;
                int bowActId = this._npc.getBowActId();
                if (isLongRange && bowActId > 0) {
                    this.actionX1();
                }
                else {
                    this.actionX2();
                }
            }
        }
        catch (Exception e) {
            L1AttackNpc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void actionX1() {
        try {
            int bowActId = this._npc.getBowActId();
            int actId = 1;
            if (this.getActId() > 1) {
                actId = this.getActId();
            }
            if (this._isHit) {
                this._npc.broadcastPacketAll(new S_UseArrowSkill(this._npc, this._targetId, bowActId, this._targetX, this._targetY, this._damage, actId));
            }
            else {
                this._npc.broadcastPacketAll(new S_UseArrowSkill(this._npc, bowActId, this._targetX, this._targetY, actId));
            }
        }
        catch (Exception e) {
            L1AttackNpc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void actionX2() {
        try {
            int actId = 1;
            if (this.getActId() > 1) {
                actId = this.getActId();
            }
            switch (this._npc.getTempCharGfx()) {
                case 1780:
                case 7430:
                case 13076: {
                    actId = 30;
                    break;
                }
                case 2757:
                case 4104:
                case 13096: {
                    actId = 18;
                    break;
                }
            }
            if (this._isHit) {
                if (this.getGfxId() > 0) {
                    this._npc.broadcastPacketAll(new S_UseAttackSkill(this._npc, this._target.getId(), this.getGfxId(), this._targetX, this._targetY, actId, this._damage));
                }
                else {
                    this.gfx7049();
                    this._npc.broadcastPacketAll(new S_AttackPacketNpc(this._npc, this._target, actId, this._damage));
                }
            }
            else if (this.getGfxId() > 0) {
                this._npc.broadcastPacketAll(new S_UseAttackSkill(this._target, this._npc.getId(), this.getGfxId(), this._targetX, this._targetY, actId, 0));
            }
            else {
                this._npc.broadcastPacketAll(new S_AttackPacketNpc(this._npc, this._target, actId));
            }
        }
        catch (Exception e) {
            L1AttackNpc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void gfx7049() {
        if (this._npc.getStatus() != 58) {
            return;
        }
        else {
            boolean is = false;
            if (this._npc.getTempCharGfx() == 6671) {
                is = true;
            }
            if (this._npc.getTempCharGfx() == 6650) {
                is = true;
            }
            if (is) {
                this._npc.broadcastPacketAll(new S_SkillSound(this._npc.getId(), 7049));
            }
            return;
        }
    }
    
    @Override
    public void commit() {
        if (this._isHit) {
            switch (this._calcType) {
                case 3: {
                    this.commitPc();
                    break;
                }
                case 4: {
                    this.commitNpc();
                    break;
                }
            }
        }
        if (!ConfigAlt.ALT_ATKMSG) {
            return;
        }
        else if (this._calcType == 4) {
            return;
        }
        else if (!this._targetPc.isGm()) {
            return;
        }
        else {
            String srcatk = this._npc.getName();
            String tgatk = this._targetPc.getName();
            String dmginfo = this._isHit ? ("傷害:" + this._damage) : "失敗";
            String hitinfo = " 命中:" + this._hitRate + "% 剩餘hp:" + this._targetPc.getCurrentHp();
            String x = srcatk + ">" + tgatk + " " + dmginfo + hitinfo;
            this._targetPc.sendPackets(new S_ServerMessage(166, "受到NPC攻擊: " + x));
            return;
        }
    }
    
    private void commitPc() {
        this._targetPc.receiveDamage(this._npc, (double)this._damage, false, false);
    }
    
    private void commitNpc() {
        this._targetNpc.receiveDamage(this._npc, this._damage);

		// 若攻擊者是寵物或召喚獸，且其主人是玩家，則為主人累積對 NPC 的傷害
		if (this._npc instanceof L1PetInstance || this._npc instanceof L1SummonInstance) {
			L1Character master = this._npc.getMaster();
			if (master instanceof L1PcInstance) {
				if (ConfigRate.PetNpcDamage) {
					L1AttackList.accumulateNpcDamage((L1PcInstance) master, this._targetNpc, this._damage);
				}
			}
		}
    }

    
    @Override
    public boolean isShortDistance() {
        boolean isShortDistance = true;
        boolean isLongRange = this._npc.getLocation().getTileLineDistance(new Point(this._targetX, this._targetY)) > 1;
        int bowActId = this._npc.getBowActId();
        if (isLongRange && bowActId > 0) {
            isShortDistance = false;
        }
        return isShortDistance;
    }
    
    @Override
    public void commitCounterBarrier() {
        int damage = this.calcCounterBarrierDamage();
        if (damage == 0) {
            return;
        }
        else {
            if (this._npc.hasSkillEffect(68)) {
                damage /= 2;
            }
            this._npc.receiveDamage(this._target, damage);
            this._npc.broadcastPacketAll(new S_DoActionGFX(this._npc.getId(), 2));
            this._npc.broadcastPacketAll(new S_SkillSound(this._target.getId(), 10710));
            return;
        }
    }
    
    @Override
    public void calcStaffOfMana() {
    }
}
