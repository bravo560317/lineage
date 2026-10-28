package com.lineage.server.model;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigAlt;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.utils.Random;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import org.apache.commons.logging.Log;

public class L1MagicNpc extends L1MagicMode
{
    private static final Log _log;
    L1ItemSpecialAttributeChar item_attr_char;
    protected L1ItemInstance _weapon;
    
    static {
        _log = LogFactory.getLog(L1MagicNpc.class);
    }
    
    public L1MagicNpc(L1NpcInstance attacker, L1Character target) {
        this.item_attr_char = null;
        if (attacker == null) {
            return;
        }
        else {
            if (target instanceof L1PcInstance) {
                this._calcType = 3;
                this._npc = attacker;
                this._targetPc = (L1PcInstance)target;
            }
            else {
                this._calcType = 4;
                this._npc = attacker;
                this._targetNpc = (L1NpcInstance)target;
            }
            return;
        }
    }
    
    private int getMagicLevel() {
        int magicLevel = this._npc.getMagicLevel();
        return magicLevel;
    }
    
    private int getMagicBonus() {
        int magicBonus = this._npc.getMagicBonus();
        return magicBonus;
    }
    
    private int getLawful() {
        int lawful = this._npc.getLawful();
        return lawful;
    }
    
    @Override
    public boolean calcProbabilityMagic(int skillId) {
        int probability = 0;
        boolean isSuccess = false;
        switch (this._calcType) {
            case 3: {
                if (this._targetPc.getattr_魔法格檔() > 0 && L1MagicNpc._random.nextInt(100) < this._targetPc.getattr_魔法格檔()) {
                    return false;
                }
                else if (this._targetPc.hasSkillEffect(50) && skillId != 44) {
                    return false;
                }
                else if (this._targetPc.hasSkillEffect(157) && skillId != 44) {
                    return false;
                }
                else if (skillId == 44) {
                    return true;
                }
                else {
                    break;
                }
            }
            case 4: {
                if (this._targetNpc.hasSkillEffect(50) && skillId != 44) {
                    return false;
                }
                else if (this._targetNpc.hasSkillEffect(157) && skillId != 44) {
                    return false;
                }
                else {
                    break;
                }
            }
        }
        probability = this.calcProbability(skillId);
        int rnd = L1MagicNpc._random.nextInt(100) + 1;
        probability = Math.min(probability, 90);
        isSuccess = (probability >= rnd);
        return !this.calcEvasion() && isSuccess;
    }
    
    private int calcProbability(int skillId) {
        L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
        int attackLevel = this._npc.getLevel();
        int defenseLevel = 0;
        int probability = 0;
        switch (this._calcType) {
            case 3: {
                defenseLevel = this._targetPc.getLevel();
                break;
            }
            case 4: {
                defenseLevel = this._targetNpc.getLevel();
                if (skillId == 145 && this._targetNpc instanceof L1SummonInstance) {
                    L1SummonInstance summon = (L1SummonInstance)this._targetNpc;
                    defenseLevel = summon.getMaster().getLevel();
                    break;
                }
                else {
                    break;
                }
            }
        }
        switch (skillId) {
            case 87: {
                if (attackLevel > defenseLevel) {
                    probability = 70;
                }
                else if (attackLevel == defenseLevel) {
                    probability = 50;
                }
                else if (attackLevel < defenseLevel) {
                    probability = 30;
                }
                probability = (int)((double)probability * ((double)this.getLeverage() / 10.0));
                break;
            }
            case 91: {
                probability = 20;
                break;
            }
            case 103:
            case 112: {
                probability = 38 + (attackLevel - defenseLevel) * (L1MagicNpc._random.nextInt(3) + 2);
                probability = (int)((double)probability * ((double)this.getLeverage() / 10.0));
                break;
            }
            case 133:
            case 145:
            case 152:
            case 153:
            case 157:
            case 161:
            case 167:
            case 173:
            case 174: {
                probability = (int)((double)l1skills.getProbabilityDice() / 10.0 * (double)(attackLevel - defenseLevel));
                probability += l1skills.getProbabilityValue();
                probability -= this.getTargetMr() / 10;
                probability = (int)((double)probability * ((double)this.getLeverage() / 10.0));
                break;
            }
            case 183:
            case 188:
            case 192:
            case 193: {
                probability = (int)((double)l1skills.getProbabilityDice() / 10.0 * (double)(attackLevel - defenseLevel));
                probability += l1skills.getProbabilityValue();
                probability = (int)((double)probability * ((double)this.getLeverage() / 10.0));
                break;
            }
            case 202:
            case 208:
            case 212:
            case 217: {
                probability = Random.nextInt(11) + 20;
                probability += (attackLevel - defenseLevel) * 2;
                probability = (int)((double)probability * ((double)this.getLeverage() / 10.0));
                break;
            }
            default: {
                int dice2 = l1skills.getProbabilityDice();
                int diceCount2 = 0;
                diceCount2 = this.getMagicBonus() + this.getMagicLevel();
                diceCount2 = Math.max(diceCount2, 1);
                for (int i = 0; i < diceCount2; ++i) {
                    if (dice2 > 0) {
                        probability += L1MagicNpc._random.nextInt(dice2) + 1;
                    }
                }
                probability = (int)((double)probability * ((double)this.getLeverage() / 10.0));
                probability -= this.getTargetMr();
                break;
            }
        }
        if (this._calcType == 3) {
            switch (skillId) {
                case 20:
                case 40:
                case 50: {
                    probability -= this._targetPc.getRegistBlind();
                    break;
                }
                case 33: {
                    probability -= this._targetPc.getRegistStone();
                    break;
                }
                case 87:
                case 208: {
                    probability -= this._targetPc.getRegistStun();
                    break;
                }
                case 103:
                case 212: {
                    probability -= this._targetPc.getRegistSleep();
                    break;
                }
                case 157:
                case 192: {
                    probability -= this._targetPc.getRegistSustain();
                    break;
                }
            }
        }
        return probability;
    }
    
    @Override
    public int calcMagicDamage(int skillId) {
        int damage = 0;
        switch (this._calcType) {
            case 3: {
                damage = this.calcPcMagicDamage(skillId);
                break;
            }
            case 4: {
                damage = this.calcNpcMagicDamage(skillId);
                break;
            }
        }
        damage = this.calcMrDefense(damage);
        return damage;
    }
    
    private int calcPcMagicDamage(int skillId) {
        if (this._targetPc == null) {
            return 0;
        }
        else if ((this._npc instanceof L1PetInstance || this._npc instanceof L1SummonInstance) && this._targetPc.getZoneType() == 1) {
            return 0;
        }
        else if (L1MagicMode.dmg0(this._targetPc)) {
            return 0;
        }
        else if (this.calcEvasion()) {
            return 0;
        }
        else {
            int dmg = 0;
            if (skillId == 108) {
                dmg = this._npc.getCurrentMp();
            }
            else {
                dmg = this.calcMagicDiceDamage(skillId);
                dmg = (int)((double)dmg * ((double)this.getLeverage() / 10.0));
                if (this._npc.getNpcTemplate().get_nameid().startsWith("BOSS")) {
                    dmg *= 2;
                }
            }
            dmg -= this._targetPc.getMagicDmgReduction() + this._targetPc.get_Clanmagic_reduction_dmg() + this._targetPc.get_magic_reduction_dmg();
            dmg -= this._targetPc.dmgDowe();
            if (this._targetPc.getClanid() != 0) {
                dmg = (int)((double)dmg - L1MagicMode.getDamageReductionByClan(this._targetPc));
            }
            if (this._targetPc.hasSkillEffect(88)) {
                int targetPcLvl = Math.max(this._targetPc.getLevel(), 50);
                dmg -= (targetPcLvl - 50) / 5 + 1;
            }
            int ran = L1MagicNpc._random.nextInt(100) + 1;
            if (this._targetPc.getInventory().checkSkillType(133) && ran <= ConfigOther.armor_type21) {
                dmg = (int)((double)dmg * 0.99);
            }
            if (this._targetPc.getInventory().checkSkillType(134) && ran <= ConfigOther.armor_type22) {
                dmg = (int)((double)dmg * 0.95);
            }
            if (this._targetPc.getInventory().checkSkillType(135) && ran <= ConfigOther.armor_type23) {
                dmg = (int)((double)dmg * 0.9);
            }
            if (this._targetPc.getInventory().checkSkillType(136) && ran <= ConfigOther.armor_type24) {
                dmg = (int)((double)dmg * 0.85);
            }
            if (this._targetPc.getInventory().checkSkillType(137) && ran <= ConfigOther.armor_type25) {
                dmg = (int)((double)dmg * 0.8);
            }
            boolean isNowWar = false;
            int castleId = L1CastleLocation.getCastleIdByArea(this._targetPc);
            if (castleId > 0) {
                isNowWar = ServerWarExecutor.get().isNowWar(castleId);
            }
            if (!isNowWar) {
                if (this._npc instanceof L1PetInstance) {
                    dmg >>= 3;
                }
                if (this._npc instanceof L1SummonInstance) {
                    L1SummonInstance summon = (L1SummonInstance)this._npc;
                    if (summon.isExsistMaster()) {
                        dmg >>= 3;
                    }
                }
            }
            if (this._targetPc.hasSkillEffect(68)) {
                dmg = (int)((double)dmg / ConfigOther.IMMUNE_TO_HARM_NPC);
            }
            if ((this._targetPc.hasSkillEffect(6685) || this._targetPc.hasSkillEffect(6687) || this._targetPc.hasSkillEffect(6688) || this._targetPc.hasSkillEffect(6689)) && L1MagicNpc._random.nextInt(100) < 10) {
                dmg /= 2;
                this._targetPc.sendPacketsAll(new S_SkillSound(this._targetPc.getId(), 6320));
            }
            if (this._targetPc.hasSkillEffect(134)) {
                int npcId = this._npc.getNpcTemplate().get_npcId();
                switch (npcId) {
                    case 45681:
                    case 45682:
                    case 45683:
                    case 45684:
                    case 71014:
                    case 71015:
                    case 71016:
                    case 71026:
                    case 71027:
                    case 71028:
                    case 97204:
                    case 97205:
                    case 97206:
                    case 97207:
                    case 97208:
                    case 97209: {
                        break;
                    }
                    default: {
                        if (this._npc.getNpcTemplate().get_IsErase() && this._targetPc.getWis() >= L1MagicNpc._random.nextInt(100)) {
                            this._npc.broadcastPacketAll(new S_DoActionGFX(this._npc.getId(), 2));
                            this._npc.receiveDamage(this._targetPc, dmg);
                            this._npc.broadcastPacketAll(new S_SkillSound(this._targetPc.getId(), 4395));
                            dmg = 0;
                            this._targetPc.killSkillEffectTimer(134);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            int dmgOut = Math.max(dmg, 0);
            return dmgOut;
        }
    }
    
    private int calcNpcMagicDamage(int skillId) {
        if (this._targetNpc == null) {
            return 0;
        }
        else if (L1MagicMode.dmg0(this._targetNpc)) {
            return 0;
        }
        else {
            int dmg = 0;
            if (skillId == 108) {
                dmg = this._npc.getCurrentMp();
            }
            else {
                dmg = this.calcMagicDiceDamage(skillId);
                dmg = (int)((double)dmg * ((double)this.getLeverage() / 10.0));
                if (this._npc.getNpcTemplate().get_nameid().startsWith("BOSS")) {
                    dmg *= 2;
                }
            }
            if (this._targetNpc.hasSkillEffect(68)) {
                dmg /= 2;
            }
            if (this._targetNpc.hasSkillEffect(134) && this._npc.getNpcTemplate().get_IsErase() && this._targetNpc.getWis() >= L1MagicNpc._random.nextInt(100)) {
                this._npc.broadcastPacketAll(new S_DoActionGFX(this._npc.getId(), 2));
                this._npc.receiveDamage(this._targetNpc, dmg);
                this._npc.broadcastPacketAll(new S_SkillSound(this._targetNpc.getId(), 4395));
                dmg = 0;
                this._targetNpc.killSkillEffectTimer(134);
            }
            if (this._targetNpc.hasSkillEffect(11059) && this._npc.getNpcTemplate().get_IsErase()) {
                this._npc.broadcastPacketAll(new S_DoActionGFX(this._npc.getId(), 2));
                this._npc.receiveDamage(this._targetNpc, dmg);
                this._npc.broadcastPacketAll(new S_SkillSound(this._targetNpc.getId(), 4395));
                dmg = 0;
            }
            return dmg;
        }
    }
    
    private int calcMagicDiceDamage(int skillId) {
        L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
        int dice = l1skills.getDamageDice();
        int diceCount = l1skills.getDamageDiceCount();
        int value = l1skills.getDamageValue();
        int magicDamage = 0;
        int charaIntelligence = 0;
        for (int i = 0; i < diceCount; ++i) {
            magicDamage += L1MagicNpc._random.nextInt(dice) + 1;
        }
        magicDamage += value;
        int spByItem = this.getTargetSp();
        charaIntelligence = Math.max(this._npc.getInt() + spByItem - 12, 1);
        double attrDeffence = this.calcAttrResistance(l1skills.getAttr());
        double coefficient = Math.max(1.0 - attrDeffence + (double)charaIntelligence * 3.0 / 32.0, 0.0);
        magicDamage = (int)((double)magicDamage * coefficient);
        return magicDamage;
    }
    
    @Override
    public int calcHealing(int skillId) {
        L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
        int dice = l1skills.getDamageDice();
        int value = l1skills.getDamageValue();
        int magicDamage = 0;
        int magicBonus = Math.min(this.getMagicBonus(), 10);
        for (int diceCount = value + magicBonus, i = 0; i < diceCount; ++i) {
            magicDamage += L1MagicNpc._random.nextInt(dice) + 1;
        }
        double alignmentRevision = 1.0;
        if (this.getLawful() > 0) {
            alignmentRevision += (double)this.getLawful() / 32768.0;
        }
        magicDamage = (int)((double)magicDamage * alignmentRevision);
        magicDamage = (int)((double)magicDamage * ((double)this.getLeverage() / 10.0));
        return magicDamage;
    }
    
    private int calcMrDefense(int dmg) {
        int mr = this.getTargetMr();
        int rnd = L1MagicNpc._random.nextInt(100) + 1;
        if (mr >= rnd) {
            dmg /= 2;
        }
        return dmg;
    }
    
    @Override
    public void commit(int damage, int drainMana) {
        switch (this._calcType) {
            case 3: {
                this.commitPc(damage, drainMana);
                break;
            }
            case 4: {
                this.commitNpc(damage, drainMana);
                break;
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
            StringBuilder atkMsg = new StringBuilder();
            atkMsg.append("受到NPC技能: ");
            atkMsg.append(this._npc.getNameId() + ">");
            atkMsg.append(this._targetPc.getName() + " ");
            atkMsg.append("傷害: " + damage);
            this._targetPc.sendPackets(new S_ServerMessage(166, atkMsg.toString()));
            return;
        }
    }
    
    private void commitPc(int damage, int drainMana) {
        try {
            this._targetPc.receiveDamage(this._npc, (double)damage, true, false);
        }
        catch (Exception e) {
            L1MagicNpc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void commitNpc(int damage, int drainMana) {
        try {
            this._targetNpc.receiveDamage(this._npc, damage);
        }
        catch (Exception e) {
            L1MagicNpc._log.error(e.getLocalizedMessage(), e);
        }
    }
}
