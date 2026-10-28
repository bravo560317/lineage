package com.lineage.server.model;

import com.lineage.william.IntSp;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.config.ConfigOther;
import com.lineage.config.ConfigRate;
import com.lineage.config.Config_Pc_Damage;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.utils.Random;
import com.lineage.william.PcMr;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigAlt;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.config.ConfigGuaji;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import org.apache.commons.logging.Log;

public class L1MagicPc extends L1MagicMode
{
    private static final Log _log;
    L1ItemSpecialAttributeChar item_attr_char;
	
	private int _lastProbability = 0; // 暫存最後一次計算的機率

public int getLastProbability() {
    return this._lastProbability;
}
    protected L1ItemInstance _weapon;
    
    static {
        _log = LogFactory.getLog(L1MagicPc.class);
    }
    
    public L1MagicPc(L1PcInstance attacker, L1Character target) {
        this.item_attr_char = null;
        if (attacker == null) {
            return;
        }
        else {
            this._pc = attacker;
            if (target instanceof L1PcInstance) {
                this._calcType = 1;
                this._targetPc = (L1PcInstance)target;
            }
            else {
                this._calcType = 2;
                this._targetNpc = (L1NpcInstance)target;
            }
            return;
        }
    }
    
    private int getMagicLevel() {
        return this._pc.getMagicLevel();
    }
    
    private int getMagicBonus() {
        return this._pc.getMagicBonus();
    }
    
    private int getLawful() {
        return this._pc.getLawful();
    }
    
    @Override
    public boolean calcProbabilityMagic(int skillId) {
        int probability = 0;
        boolean isSuccess = false;
        switch (this._calcType) {
            case 1: {
                if (this._targetPc.getattr_魔法格檔() > 0 && L1MagicPc._random.nextInt(100) < this._targetPc.getattr_魔法格檔()) {
                    return false;
                }
                else {
                    if (skillId == 44 && this._pc != null && this._targetPc != null) {
                        if (this._pc.getId() == this._targetPc.getId()) {
                            return true;
                        }
                        else if (this._pc.getClanid() > 0 && this._pc.getClanid() == this._targetPc.getClanid()) {
                            return true;
                        }
                    }
                    if (skillId == 67) {
                        if (this._pc.getId() == this._targetPc.getId()) {
                            return true;
                        }
                        else if (this._pc.getClanid() > 0 && this._pc.getClanid() == this._targetPc.getClanid()) {
                            return true;
                        }
                    }
                    if (ConfigGuaji.Guaji_save && this._targetPc.isActived() && this._targetPc.getLevel() <= ConfigGuaji.Guaji_level) {
                        return false;
                    }
                    else if (this._targetPc == this._pc) {
                        return false;
                    }
                    else {
                        boolean castle_area = L1CastleLocation.checkInAllWarArea(this._targetPc.getX(), this._targetPc.getY(), this._targetPc.getMapId());
                        if (this._targetPc.getnewcharpra()) {
                            return false;
                        }
                        else if (this._pc.getnewcharpra()) {
                            return false;
                        }
                        else if (!this.checkZone(skillId)) {
                            return false;
                        }
                        else if (this._targetPc.hasSkillEffect(50) && skillId != 44) {
                            return false;
                        }
                        else if (this._targetPc.hasSkillEffect(157) && skillId != 44) {
                            return false;
                        }
                        else if (this.calcEvasion()) {
                            return false;
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            case 2: {
                if (this._targetNpc != null) {
                    if (this._targetNpc instanceof L1DeInstance && !this.checkZoneDE(skillId)) {
                        return false;
                    }
                    else {
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
                        int npcId = this._targetNpc.getNpcTemplate().get_npcId();
                        Integer tgskill = L1AttackList.SKNPC.get((Integer)npcId);
                        if (tgskill != null && !this._pc.hasSkillEffect(tgskill)) {
                            return false;
                        }
                        else {
                            Integer tgpoly = L1AttackList.PLNPC.get((Integer)npcId);
                            if (tgpoly != null && tgpoly.equals((Integer)this._pc.getTempCharGfx())) {
                                return false;
                            }
                            else {
                                boolean dgskill = L1AttackList.DNNPC.containsKey((Integer)npcId);
                                if (dgskill) {
                                    Integer[] dgskillids = L1AttackList.DNNPC.get((Integer)npcId);
                                    Integer[] array;
                                    int length = (array = dgskillids).length;
                                    int i = 0;
                                    while (i < length) {
                                        Integer dgskillid = array[i];
                                        if (dgskillid.equals((Integer)skillId)) {
                                            return false;
                                        }
                                        else {
                                            ++i;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (skillId == 44) {
                    return true;
                }
                else if (skillId == 67 && this._targetNpc.getLevel() >= 50) {
                    return false;
                }
                else if (this._targetNpc.hasSkillEffect(50) && skillId != 27 && skillId != 44) {
                    return false;
                }
                else if (this._targetNpc.hasSkillEffect(157) && skillId != 27 && skillId != 44) {
                    return false;
                }
                else {
                    break;
                }
            }
        }
        probability = this.calcProbability(skillId);
		
		// ⭐ 新增：將計算出的機率存起來
		probability -= this.getTargetMr() / 5;
this._lastProbability = probability;

        if ((this._calcType == 1 || this._calcType == 2) && this._pc.isWizard() && this._pc.getlogpcpower_SkillFor3() != 0) {
            probability += this._pc.getlogpcpower_SkillFor3();
        }
        if (skillId == 208 && this._pc.getlogpcpower_SkillFor3() != 0) {
            probability += this._pc.getlogpcpower_SkillFor3();
        }
        int rnd = L1MagicPc._random.nextInt(100) + 1;
        if (probability >= rnd) {
            isSuccess = true;
            if (this._pc.isGm()) {
                this._pc.sendPackets(new S_SystemMessage("玩家發動:" + probability + "大於系統發動值" + rnd + "觸發"));
            }
        }
        else {
            isSuccess = false;
            if (this._pc.isGm()) {
                this._pc.sendPackets(new S_SystemMessage("玩家發動:" + probability + "小於系統發動值" + rnd + "失敗"));
            }
        }
        if (!ConfigAlt.ALT_ATKMSG) {
            return isSuccess;
        }
        else {
            switch (this._calcType) {
                case 1: {
                    if (!this._pc.isGm() && !this._targetPc.isGm()) {
                        return isSuccess;
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if (!this._pc.isGm()) {
                        return isSuccess;
                    }
                    else {
                        break;
                    }
                }
            }
            switch (this._calcType) {
                case 1: {
                    if (this._pc.isGm()) {
                        StringBuilder atkMsg = new StringBuilder();
                        atkMsg.append("對PC送出技能: ");
                        atkMsg.append(this._pc.getName() + ">");
                        atkMsg.append(this._targetPc.getName() + " ");
                        atkMsg.append(isSuccess ? "成功" : "失敗");
                        atkMsg.append(" 成功機率:" + probability + "%");
                        this._pc.sendPackets(new S_ServerMessage(166, atkMsg.toString()));
                    }
                    if (this._targetPc.isGm()) {
                        StringBuilder atkMsg = new StringBuilder();
                        atkMsg.append("受到PC技能: ");
                        atkMsg.append(this._pc.getName() + ">");
                        atkMsg.append(this._targetPc.getName() + " ");
                        atkMsg.append(isSuccess ? "成功" : "失敗");
                        atkMsg.append(" 成功機率:" + probability + "%");
                        this._targetPc.sendPackets(new S_ServerMessage(166, atkMsg.toString()));
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if (this._pc.isGm()) {
                        StringBuilder atkMsg = new StringBuilder();
                        atkMsg.append("對NPC送出技能: ");
                        atkMsg.append(this._pc.getName() + ">");
                        atkMsg.append(this._targetNpc.getName() + " ");
                        atkMsg.append(isSuccess ? "成功" : "失敗");
                        atkMsg.append(" 成功機率:" + probability + "%");
                        this._pc.sendPackets(new S_ServerMessage(166, atkMsg.toString()));
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            return isSuccess;
        }
    }
    
    private boolean checkZone(int skillId) {
        if (this._pc != null && this._targetPc != null && (this._pc.isSafetyZone() || this._targetPc.isSafetyZone())) {
            Boolean isBoolean = L1AttackList.NZONE.get((Integer)skillId);
            if (isBoolean != null) {
                this._pc.sendPackets(new S_ServerMessage("在安全區域無法使用此技能。"));
                return false;
            }
        }
        return true;
    }
    
    private boolean checkZoneDE(int skillId) {
        if (this._pc != null && this._targetNpc != null && (this._pc.isSafetyZone() || this._targetNpc.isSafetyZone())) {
            Boolean isBoolean = L1AttackList.NZONE.get((Integer)skillId);
            if (isBoolean != null) {
                this._pc.sendPackets(new S_ServerMessage("在安全區域無法使用此技能。"));
                return false;
            }
        }
        return true;
    }
    
    private int calcProbability(int skillId) {
        L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
        int attackLevel = this._pc.getLevel();
        int defenseLevel = 0;
        int probability = 0;
        switch (this._calcType) {
            case 1: {
                defenseLevel = this._targetPc.getLevel();
                break;
            }
            case 2: {
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
        Integer magichit = L1AttackList.INTH.get((Integer)(int)this._pc.getInt());
        if (magichit == null) {
            magichit = 0;
        }
        if (this._calcType == 1 && (skillId == 27 || skillId == 29 || skillId == 33 || skillId == 47 || skillId == 50 || skillId == 59 || skillId == 64 || skillId == 66 || skillId == 71 || skillId == 152 || skillId == 167 || skillId == 153 || skillId == 157 || skillId == 161 || skillId == 173 || skillId == 174 || skillId == 39 || skillId == 103 || skillId == 183 || skillId == 188 || skillId == 193 || skillId == 202 || skillId == 212 || skillId == 217 || skillId == 11 || skillId == 20 || skillId == 40 || skillId == 76) && !this.isInWarAreaAndWarTime(this._pc, this._targetPc) && !this._targetPc.isPinkName() && !this._targetPc.isCombatZone()) {
            L1PinkName.runPinkNameTimer(this._pc);
        }
        switch (skillId) {
            case 18: {
                probability = l1skills.getProbabilityValue();
                if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
                    probability = l1skills.gettype1();
                }
                if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
                    probability = l1skills.gettype2();
                }
                if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
                    probability = l1skills.gettype3();
                }
                if (l1skills.gettype4() != 0) {
                    probability += l1skills.gettype4();
                }
                if (l1skills.gettype5() != 0) {
                    probability += l1skills.gettype5() * ((attackLevel - defenseLevel) / 10);
                }
                if (l1skills.gettype6() != 0) {
                    probability += l1skills.gettype6() * this._pc.getInt() / 10;
                }
                if (l1skills.gettype7() != 0) {
                    probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
                }
                if (l1skills.gettype8() != 0) {
                    probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
                }
                if (l1skills.gettype9() != 0) {
                    probability += l1skills.gettype9() + (attackLevel - defenseLevel);
                }
                if (l1skills.gettype10() != 0 && this._pc.getInt() >= l1skills.gettype10()) {
                    probability += 3;
                }
                if (l1skills.gettype11() != 0) {
                    probability += l1skills.gettype11() * (attackLevel - defenseLevel);
                }
                if (l1skills.gettype12() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype12() + (double)(this._pc.getMr() - l1skills.gettype12()) * 0.02));
                }
                if (l1skills.gettype13() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype13() + (double)(this._pc.getMr() - l1skills.gettype13()) * 0.04));
                }
                if (this._calcType == 1 && l1skills.gettype14() != 0 && this._targetPc.getMr() >= l1skills.gettype14()) {
                    probability -= l1skills.gettype14() / 7;
                }
                if (this._calcType == 1 && l1skills.gettype15() != 0 && this._targetPc.getMr() >= l1skills.gettype15()) {
                    probability -= l1skills.gettype15() / 9;
                }
                if (this._calcType == 1 && l1skills.gettype16() != 0 && this._targetPc.getMr() >= l1skills.gettype16()) {
                    probability -= l1skills.gettype16() / 13;
                }
                if (this._calcType == 1 && l1skills.gettype17() != 0 && this._targetPc.getMr() >= l1skills.gettype17()) {
                    probability -= l1skills.gettype17() / 15;
                }
                if (this._calcType == 1 && l1skills.gettype18() != 0 && this._targetPc.getMr() >= l1skills.gettype18()) {
                    probability -= l1skills.gettype18() / 20;
                }
                if (this._calcType == 1 && l1skills.gettype19() != 0 && this._targetPc.getMr() >= l1skills.gettype19()) {
                    probability -= 15;
                }
                if (l1skills.gettype20() != 0 && attackLevel - defenseLevel > l1skills.gettype20()) {
                    probability = 70;
                }
                if (l1skills.gettype21() != 0 && probability >= l1skills.gettype21()) {
                    probability = l1skills.gettype21();
                }
                if (this._calcType == 1 && l1skills.gettype22() != 0 && this._targetPc.getMr() >= l1skills.gettype22()) {
                    probability = 5;
                }
                if (this._calcType == 1 && l1skills.gettype24() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
                }
                if (this._calcType == 1 && l1skills.gettype25() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
                }
                if (this._calcType == 1 && l1skills.gettype26() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
                }
                if (this._calcType == 1 && l1skills.gettype27() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
                }
                if (this._calcType == 1 && l1skills.gettype28() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
                }
                if (this._pc.isWizard() && probability > 70) {
                    probability = 70;
                }
                else if (this._pc.isElf()) {
                    if (probability > 70) {
                        probability = 55;
                    }
                    else {
                        probability /= 2;
                    }
                }
                probability += this._pc.getDoll_MagicHit();
                break;
            }
            case 27:
            case 29:
            case 33:
            case 39:
            case 44:
            case 50:
            case 64:
            case 66:
            case 71: {
                probability = (int)((double)l1skills.getProbabilityDice() / 10.0 * (double)(attackLevel - defenseLevel));
                if (this._pc.isGm()) {
                    this._pc.sendPackets(new S_ServerMessage("type1;" + probability));
                    this._pc.sendPackets(new S_ServerMessage("1公式;(dice/10)*(我等-對等)級"));
                }
                probability += l1skills.getProbabilityValue();
                if (this._pc.isGm()) {
                    this._pc.sendPackets(new S_ServerMessage("type2;" + probability));
                    this._pc.sendPackets(new S_ServerMessage("2公式:+SKILLS欄位ProbabilityValue"));
                }
                if (l1skills.gettype23() != 0 && this._pc.getOriginalMagicHit() > 0) {
                    this._pc.addOriginalMagicHit(this._pc.getOriginalMagicHit() * (l1skills.gettype23() / 100));
                }
                probability += this._pc.getOriginalMagicHit();
                if (this._pc.isGm()) {
                    this._pc.sendPackets(new S_ServerMessage("type3;" + probability));
                    this._pc.sendPackets(new S_ServerMessage("3公式:自身魔法命中增加成功機率"));
                }
                probability += magichit;
                if (this._pc.isGm()) {
                    this._pc.sendPackets(new S_ServerMessage("type4;" + probability));
                    this._pc.sendPackets(new S_ServerMessage("4公式:智力魔法命中補正"));
                }
                if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
                    probability = l1skills.gettype1();
                }
                if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
                    probability = l1skills.gettype2();
                }
                if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
                    probability = l1skills.gettype3();
                }
                if (l1skills.gettype4() != 0) {
                    probability += l1skills.gettype4();
                }
                if (l1skills.gettype5() != 0) {
                    probability += l1skills.gettype5() * ((attackLevel - defenseLevel) / 10);
                }
                if (l1skills.gettype6() != 0) {
                    probability += l1skills.gettype6() * this._pc.getInt() / 10;
                }
                if (l1skills.gettype7() != 0) {
                    probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
                }
                if (l1skills.gettype8() != 0) {
                    probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
                }
                if (l1skills.gettype9() != 0) {
                    probability += l1skills.gettype9() + (attackLevel - defenseLevel);
                }
                if (l1skills.gettype10() != 0 && this._pc.getInt() >= l1skills.gettype10()) {
                    probability += 3;
                }
                if (l1skills.gettype11() != 0) {
                    probability += l1skills.gettype11() * (attackLevel - defenseLevel);
                }
                if (l1skills.gettype12() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype12() + (double)(this._pc.getMr() - l1skills.gettype12()) * 0.02));
                }
                if (l1skills.gettype13() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype13() + (double)(this._pc.getMr() - l1skills.gettype13()) * 0.04));
                }
                if (this._calcType == 1 && l1skills.gettype14() != 0 && this._targetPc.getMr() >= l1skills.gettype14()) {
                    probability -= l1skills.gettype14() / 7;
                }
                if (this._calcType == 1 && l1skills.gettype15() != 0 && this._targetPc.getMr() >= l1skills.gettype15()) {
                    probability -= l1skills.gettype15() / 9;
                }
                if (this._calcType == 1 && l1skills.gettype16() != 0 && this._targetPc.getMr() >= l1skills.gettype16()) {
                    probability -= l1skills.gettype16() / 13;
                }
                if (this._calcType == 1 && l1skills.gettype17() != 0 && this._targetPc.getMr() >= l1skills.gettype17()) {
                    probability -= l1skills.gettype17() / 15;
                }
                if (this._calcType == 1 && l1skills.gettype18() != 0 && this._targetPc.getMr() >= l1skills.gettype18()) {
                    probability -= l1skills.gettype18() / 20;
                }
                if (this._calcType == 1 && l1skills.gettype19() != 0 && this._targetPc.getMr() >= l1skills.gettype19()) {
                    probability -= 15;
                }
                if (l1skills.gettype20() != 0 && attackLevel - defenseLevel > l1skills.gettype20()) {
                    probability = 70;
                }
                if (l1skills.gettype21() != 0 && probability >= l1skills.gettype21()) {
                    probability = l1skills.gettype21();
                }
                if (this._calcType == 1 && l1skills.gettype22() != 0 && this._targetPc.getMr() >= l1skills.gettype22()) {
                    probability = 5;
                }
                if (this._calcType == 1 && l1skills.gettype24() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
                }
                if (this._calcType == 1 && l1skills.gettype25() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
                }
                if (this._calcType == 1 && l1skills.gettype26() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
                }
                if (this._calcType == 1 && l1skills.gettype27() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
                }
                if (this._calcType == 1 && l1skills.gettype28() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
                }
                probability -= this.getTargetMr() / 10;
                if (this._pc.isGm()) {
                    this._pc.sendPackets(new S_ServerMessage("type5:最終值對方抗/10=" + probability));
                }
                if (this._calcType == 1) {
                    int othersp = PcMr.getIntSpSkill(this._targetPc.getMr());
                    if (othersp != 0) {
                        probability -= othersp;
                        if (probability < 0) {
                            probability = 0;
                        }
                    }
                }
                if (l1skills.gettype22() != 0 && this._targetPc.getMr() >= l1skills.gettype22()) {
                    probability = 5;
                }
                probability += this._pc.getDoll_MagicHit();
				// ⭐ 新增這行：判定目標抗魔 (MR / 10)
                probability -= this.getTargetMr() / 10;
                break;
            }
            case 67: {
                probability = 3 * (attackLevel - defenseLevel) + 225 - this.getTargetMr();
                probability += (int)((double)l1skills.getProbabilityDice() / 10.0 * (double)(attackLevel - defenseLevel));
                probability += this._pc.getDoll_MagicHit();
                break;
            }
            case 87: {
                probability += this._pc.getOriginalMagicHit();
                if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
                    probability = l1skills.gettype1();
                }
                if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
                    probability = l1skills.gettype2();
                }
                if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
                    probability = l1skills.gettype3();
                }
                if (l1skills.gettype4() != 0) {
                    probability += l1skills.gettype4();
                }
                if (l1skills.gettype5() != 0) {
                    probability += l1skills.gettype5() * ((attackLevel - defenseLevel) / 10);
                }
                if (l1skills.gettype6() != 0) {
                    probability += l1skills.gettype6() * this._pc.getInt() / 10;
                }
                if (l1skills.gettype7() != 0) {
                    probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
                }
                if (l1skills.gettype8() != 0) {
                    probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
                }
                if (l1skills.gettype9() != 0) {
                    probability += l1skills.gettype9() + (attackLevel - defenseLevel);
                }
                if (l1skills.gettype10() != 0 && this._pc.getInt() >= l1skills.gettype10()) {
                    probability += 3;
                }
                if (l1skills.gettype11() != 0) {
                    probability += l1skills.gettype11() * (attackLevel - defenseLevel);
                }
                if (l1skills.gettype12() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype12() + (double)(this._pc.getMr() - l1skills.gettype12()) * 0.02));
                }
                if (l1skills.gettype13() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype13() + (double)(this._pc.getMr() - l1skills.gettype13()) * 0.04));
                }
                if (this._calcType == 1 && l1skills.gettype14() != 0 && this._targetPc.getMr() >= l1skills.gettype14()) {
                    probability -= l1skills.gettype14() / 7;
                }
                if (this._calcType == 1 && l1skills.gettype15() != 0 && this._targetPc.getMr() >= l1skills.gettype15()) {
                    probability -= l1skills.gettype15() / 9;
                }
                if (this._calcType == 1 && l1skills.gettype16() != 0 && this._targetPc.getMr() >= l1skills.gettype16()) {
                    probability -= l1skills.gettype16() / 13;
                }
                if (this._calcType == 1 && l1skills.gettype17() != 0 && this._targetPc.getMr() >= l1skills.gettype17()) {
                    probability -= l1skills.gettype17() / 15;
                }
                if (this._calcType == 1 && l1skills.gettype18() != 0 && this._targetPc.getMr() >= l1skills.gettype18()) {
                    probability -= l1skills.gettype18() / 20;
                }
                if (this._calcType == 1 && l1skills.gettype19() != 0 && this._targetPc.getMr() >= l1skills.gettype19()) {
                    probability -= 15;
                }
                if (l1skills.gettype20() != 0 && attackLevel - defenseLevel > l1skills.gettype20()) {
                    probability = 70;
                }
                if (l1skills.gettype21() != 0 && probability >= l1skills.gettype21()) {
                    probability = l1skills.gettype21();
                }
                if (this._calcType == 1 && l1skills.gettype22() != 0 && this._targetPc.getMr() >= l1skills.gettype22()) {
                    probability = 5;
                }
                if (this._calcType == 1 && l1skills.gettype24() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
                }
                if (this._calcType == 1 && l1skills.gettype25() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
                }
                if (this._calcType == 1 && l1skills.gettype26() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
                }
                if (this._calcType == 1 && l1skills.gettype27() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
                }
                if (this._calcType == 1 && l1skills.gettype28() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
                }
                probability += this._pc.getaddStunLevel();
                probability += this._pc.getDoll_MagicHit();
                break;
            }
            case 91: {
                probability = l1skills.getProbabilityValue();
                if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
                    probability = l1skills.gettype1();
                }
                if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
                    probability = l1skills.gettype2();
                }
                if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
                    probability = l1skills.gettype3();
                }
                if (l1skills.gettype4() != 0) {
                    probability += l1skills.gettype4();
                }
                if (l1skills.gettype5() != 0) {
                    probability += l1skills.gettype5() * ((attackLevel - defenseLevel) / 10);
                }
                if (l1skills.gettype6() != 0) {
                    probability += l1skills.gettype6() * this._pc.getInt() / 10;
                }
                if (l1skills.gettype7() != 0) {
                    probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
                }
                if (l1skills.gettype8() != 0) {
                    probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
                }
                if (l1skills.gettype9() != 0) {
                    probability += l1skills.gettype9() + (attackLevel - defenseLevel);
                }
                if (l1skills.gettype10() != 0 && this._pc.getInt() >= l1skills.gettype10()) {
                    probability += 3;
                }
                if (l1skills.gettype11() != 0) {
                    probability += l1skills.gettype11() * (attackLevel - defenseLevel);
                }
                if (l1skills.gettype12() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype12() + (double)(this._pc.getMr() - l1skills.gettype12()) * 0.02));
                }
                if (l1skills.gettype13() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype13() + (double)(this._pc.getMr() - l1skills.gettype13()) * 0.04));
                }
                if (this._calcType == 1 && l1skills.gettype14() != 0 && this._targetPc.getMr() >= l1skills.gettype14()) {
                    probability -= l1skills.gettype14() / 7;
                }
                if (this._calcType == 1 && l1skills.gettype15() != 0 && this._targetPc.getMr() >= l1skills.gettype15()) {
                    probability -= l1skills.gettype15() / 9;
                }
                if (this._calcType == 1 && l1skills.gettype16() != 0 && this._targetPc.getMr() >= l1skills.gettype16()) {
                    probability -= l1skills.gettype16() / 13;
                }
                if (this._calcType == 1 && l1skills.gettype17() != 0 && this._targetPc.getMr() >= l1skills.gettype17()) {
                    probability -= l1skills.gettype17() / 15;
                }
                if (this._calcType == 1 && l1skills.gettype18() != 0 && this._targetPc.getMr() >= l1skills.gettype18()) {
                    probability -= l1skills.gettype18() / 20;
                }
                if (this._calcType == 1 && l1skills.gettype19() != 0 && this._targetPc.getMr() >= l1skills.gettype19()) {
                    probability -= 15;
                }
                if (l1skills.gettype20() != 0 && attackLevel - defenseLevel > l1skills.gettype20()) {
                    probability = 70;
                }
                if (l1skills.gettype21() != 0 && probability >= l1skills.gettype21()) {
                    probability = l1skills.gettype21();
                }
                if (this._calcType == 1 && l1skills.gettype22() != 0 && this._targetPc.getMr() >= l1skills.gettype22()) {
                    probability = 5;
                }
                if (this._calcType == 1 && l1skills.gettype24() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
                }
                if (this._calcType == 1 && l1skills.gettype25() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
                }
                if (this._calcType == 1 && l1skills.gettype26() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
                }
                if (this._calcType == 1 && l1skills.gettype27() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
                }
                if (this._calcType == 1 && l1skills.gettype28() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
                }
                probability += this._pc.getDoll_MagicHit();
                break;
            }
            case 103:
            case 112: {
                probability += this._pc.getArmorBreakLevel();
                probability += this._pc.getOriginalMagicHit();
                if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
                    probability = l1skills.gettype1();
                }
                if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
                    probability = l1skills.gettype2();
                }
                if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
                    probability = l1skills.gettype3();
                }
                if (l1skills.gettype4() != 0) {
                    probability += l1skills.gettype4();
                }
                if (l1skills.gettype5() != 0) {
                    probability += l1skills.gettype5() * ((attackLevel - defenseLevel) / 10);
                }
                if (l1skills.gettype6() != 0) {
                    probability += l1skills.gettype6() * this._pc.getInt() / 10;
                }
                if (l1skills.gettype7() != 0) {
                    probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
                }
                if (l1skills.gettype8() != 0) {
                    probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
                }
                if (l1skills.gettype9() != 0) {
                    probability += l1skills.gettype9() + (attackLevel - defenseLevel);
                }
                if (l1skills.gettype10() != 0 && this._pc.getInt() >= l1skills.gettype10()) {
                    probability += 3;
                }
                if (l1skills.gettype11() != 0) {
                    probability += l1skills.gettype11() * (attackLevel - defenseLevel);
                }
                if (l1skills.gettype12() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype12() + (double)(this._pc.getMr() - l1skills.gettype12()) * 0.02));
                }
                if (l1skills.gettype13() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype13() + (double)(this._pc.getMr() - l1skills.gettype13()) * 0.04));
                }
                if (this._calcType == 1 && l1skills.gettype14() != 0 && this._targetPc.getMr() >= l1skills.gettype14()) {
                    probability -= l1skills.gettype14() / 7;
                }
                if (this._calcType == 1 && l1skills.gettype15() != 0 && this._targetPc.getMr() >= l1skills.gettype15()) {
                    probability -= l1skills.gettype15() / 9;
                }
                if (this._calcType == 1 && l1skills.gettype16() != 0 && this._targetPc.getMr() >= l1skills.gettype16()) {
                    probability -= l1skills.gettype16() / 13;
                }
                if (this._calcType == 1 && l1skills.gettype17() != 0 && this._targetPc.getMr() >= l1skills.gettype17()) {
                    probability -= l1skills.gettype17() / 15;
                }
                if (this._calcType == 1 && l1skills.gettype18() != 0 && this._targetPc.getMr() >= l1skills.gettype18()) {
                    probability -= l1skills.gettype18() / 20;
                }
                if (this._calcType == 1 && l1skills.gettype19() != 0 && this._targetPc.getMr() >= l1skills.gettype19()) {
                    probability -= 15;
                }
                if (l1skills.gettype20() != 0 && attackLevel - defenseLevel > l1skills.gettype20()) {
                    probability = 70;
                }
                if (l1skills.gettype21() != 0 && probability >= l1skills.gettype21()) {
                    probability = l1skills.gettype21();
                }
                if (this._calcType == 1 && l1skills.gettype22() != 0 && this._targetPc.getMr() >= l1skills.gettype22()) {
                    probability = 5;
                }
                if (this._calcType == 1 && l1skills.gettype24() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
                }
                if (this._calcType == 1 && l1skills.gettype25() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
                }
                if (this._calcType == 1 && l1skills.gettype26() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
                }
                if (this._calcType == 1 && l1skills.gettype27() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
                }
                if (this._calcType == 1 && l1skills.gettype28() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
                }
                probability += this._pc.getDoll_MagicHit();
                break;
            }
            case 133:
            case 152:
            case 153:
            case 157:
            case 161:
            case 167:
            case 173:
            case 174: {
                if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
                    probability = l1skills.gettype1();
                }
                if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
                    probability = l1skills.gettype2();
                }
                if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
                    probability = l1skills.gettype3();
                }
                if (l1skills.gettype4() != 0) {
                    probability += l1skills.gettype4();
                }
                if (l1skills.gettype5() != 0) {
                    probability += l1skills.gettype5() * ((attackLevel - defenseLevel) / 10);
                }
                if (l1skills.gettype6() != 0) {
                    probability += l1skills.gettype6() * this._pc.getInt() / 10;
                }
                if (l1skills.gettype7() != 0) {
                    probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
                }
                if (l1skills.gettype8() != 0) {
                    probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
                }
                if (l1skills.gettype9() != 0) {
                    probability += l1skills.gettype9() + (attackLevel - defenseLevel);
                }
                if (l1skills.gettype10() != 0 && this._pc.getInt() >= l1skills.gettype10()) {
                    probability += 3;
                }
                if (l1skills.gettype11() != 0) {
                    probability += l1skills.gettype11() * (attackLevel - defenseLevel);
                }
                if (l1skills.gettype12() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype12() + (double)(this._pc.getMr() - l1skills.gettype12()) * 0.02));
                }
                if (l1skills.gettype13() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype13() + (double)(this._pc.getMr() - l1skills.gettype13()) * 0.04));
                }
                if (this._calcType == 1 && l1skills.gettype14() != 0 && this._targetPc.getMr() >= l1skills.gettype14()) {
                    probability -= l1skills.gettype14() / 7;
                }
                if (this._calcType == 1 && l1skills.gettype15() != 0 && this._targetPc.getMr() >= l1skills.gettype15()) {
                    probability -= l1skills.gettype15() / 9;
                }
                if (this._calcType == 1 && l1skills.gettype16() != 0 && this._targetPc.getMr() >= l1skills.gettype16()) {
                    probability -= l1skills.gettype16() / 13;
                }
                if (this._calcType == 1 && l1skills.gettype17() != 0 && this._targetPc.getMr() >= l1skills.gettype17()) {
                    probability -= l1skills.gettype17() / 15;
                }
                if (this._calcType == 1 && l1skills.gettype18() != 0 && this._targetPc.getMr() >= l1skills.gettype18()) {
                    probability -= l1skills.gettype18() / 20;
                }
                if (this._calcType == 1 && l1skills.gettype19() != 0 && this._targetPc.getMr() >= l1skills.gettype19()) {
                    probability -= 15;
                }
                if (l1skills.gettype20() != 0 && attackLevel - defenseLevel > l1skills.gettype20()) {
                    probability = 70;
                }
                if (l1skills.gettype21() != 0 && probability >= l1skills.gettype21()) {
                    probability = l1skills.gettype21();
                }
                if (this._calcType == 1 && l1skills.gettype22() != 0 && this._targetPc.getMr() >= l1skills.gettype22()) {
                    probability = 5;
                }
                if (this._calcType == 1 && l1skills.gettype24() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
                }
                if (this._calcType == 1 && l1skills.gettype25() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
                }
                if (this._calcType == 1 && l1skills.gettype26() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
                }
                if (this._calcType == 1 && l1skills.gettype27() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
                }
                if (this._calcType == 1 && l1skills.gettype28() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
                }
                probability += this._pc.getDoll_MagicHit();
                break;
            }
            case 145: {
                probability = (int)((double)l1skills.getProbabilityDice() / 10.0 * (double)(attackLevel - defenseLevel));
                probability += l1skills.getProbabilityValue();
                probability -= this.getTargetMr() / 10;
                probability += this._pc.getOriginalMagicHit();
                break;
            }
            case 183:
            case 188:
            case 193: {
                probability += l1skills.getProbabilityValue();
                probability += this._pc.getOriginalMagicHit();
                probability += (int)((double)l1skills.getProbabilityDice() / 10.0 * (double)(attackLevel - defenseLevel));
                probability += this._pc.getDoll_MagicHit();
                break;
            }
            case 202:
            case 212:
            case 217: {
                probability = Random.nextInt(11) + 20;
                probability += (attackLevel - defenseLevel) * 2;
                probability += this._pc.getOriginalMagicHit();
                probability += magichit;
                if (this._pc.isIllusionist() && this._pc.getlogpcpower_SkillFor1() != 0) {
                    probability += this._pc.getlogpcpower_SkillFor1();
                }
                probability += (int)((double)l1skills.getProbabilityDice() / 10.0 * (double)(attackLevel - defenseLevel));
                if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
                    probability = l1skills.gettype1();
                }
                if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
                    probability = l1skills.gettype2();
                }
                if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
                    probability = l1skills.gettype3();
                }
                if (l1skills.gettype4() != 0) {
                    probability += l1skills.gettype4();
                }
                if (l1skills.gettype5() != 0) {
                    probability += l1skills.gettype5() * ((attackLevel - defenseLevel) / 10);
                }
                if (l1skills.gettype6() != 0) {
                    probability += l1skills.gettype6() * this._pc.getInt() / 10;
                }
                if (l1skills.gettype7() != 0) {
                    probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
                }
                if (l1skills.gettype8() != 0) {
                    probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
                }
                if (l1skills.gettype9() != 0) {
                    probability += l1skills.gettype9() + (attackLevel - defenseLevel);
                }
                if (l1skills.gettype10() != 0 && this._pc.getInt() >= l1skills.gettype10()) {
                    probability += 3;
                }
                if (l1skills.gettype11() != 0) {
                    probability += l1skills.gettype11() * (attackLevel - defenseLevel);
                }
                if (l1skills.gettype12() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype12() + (double)(this._pc.getMr() - l1skills.gettype12()) * 0.02));
                }
                if (l1skills.gettype13() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype13() + (double)(this._pc.getMr() - l1skills.gettype13()) * 0.04));
                }
                if (this._calcType == 1 && l1skills.gettype14() != 0 && this._targetPc.getMr() >= l1skills.gettype14()) {
                    probability -= l1skills.gettype14() / 7;
                }
                if (this._calcType == 1 && l1skills.gettype15() != 0 && this._targetPc.getMr() >= l1skills.gettype15()) {
                    probability -= l1skills.gettype15() / 9;
                }
                if (this._calcType == 1 && l1skills.gettype16() != 0 && this._targetPc.getMr() >= l1skills.gettype16()) {
                    probability -= l1skills.gettype16() / 13;
                }
                if (this._calcType == 1 && l1skills.gettype17() != 0 && this._targetPc.getMr() >= l1skills.gettype17()) {
                    probability -= l1skills.gettype17() / 15;
                }
                if (this._calcType == 1 && l1skills.gettype18() != 0 && this._targetPc.getMr() >= l1skills.gettype18()) {
                    probability -= l1skills.gettype18() / 20;
                }
                if (this._calcType == 1 && l1skills.gettype19() != 0 && this._targetPc.getMr() >= l1skills.gettype19()) {
                    probability -= 15;
                }
                if (l1skills.gettype20() != 0 && attackLevel - defenseLevel > l1skills.gettype20()) {
                    probability = 70;
                }
                if (l1skills.gettype21() != 0 && probability >= l1skills.gettype21()) {
                    probability = l1skills.gettype21();
                }
                if (this._calcType == 1 && l1skills.gettype22() != 0 && this._targetPc.getMr() >= l1skills.gettype22()) {
                    probability = 5;
                }
                if (this._calcType == 1 && l1skills.gettype24() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
                }
                if (this._calcType == 1 && l1skills.gettype25() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
                }
                if (this._calcType == 1 && l1skills.gettype26() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
                }
                if (this._calcType == 1 && l1skills.gettype27() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
                }
                if (this._calcType == 1 && l1skills.gettype28() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
                }
                probability += this._pc.getDoll_MagicHit();
                break;
            }
            case 208: {
                probability += this._pc.getStunLevel() + this._pc.getaddStunLevel();
                probability += magichit;
                if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
                    probability = l1skills.gettype1();
                }
                if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
                    probability = l1skills.gettype2();
                }
                if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
                    probability = l1skills.gettype3();
                }
                if (l1skills.gettype4() != 0) {
                    probability += l1skills.gettype4();
                }
                if (l1skills.gettype5() != 0) {
                    probability += l1skills.gettype5() * ((attackLevel - defenseLevel) / 10);
                }
                if (l1skills.gettype6() != 0) {
                    probability += l1skills.gettype6() * this._pc.getInt() / 10;
                }
                if (l1skills.gettype7() != 0) {
                    probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
                }
                if (l1skills.gettype8() != 0) {
                    probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
                }
                if (l1skills.gettype9() != 0) {
                    probability += l1skills.gettype9() + (attackLevel - defenseLevel);
                }
                if (l1skills.gettype10() != 0 && this._pc.getInt() >= l1skills.gettype10()) {
                    probability += 3;
                }
                if (l1skills.gettype11() != 0) {
                    probability += l1skills.gettype11() * (attackLevel - defenseLevel);
                }
                if (l1skills.gettype12() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype12() + (double)(this._pc.getMr() - l1skills.gettype12()) * 0.02));
                }
                if (l1skills.gettype13() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype13() + (double)(this._pc.getMr() - l1skills.gettype13()) * 0.04));
                }
                if (this._calcType == 1 && l1skills.gettype14() != 0 && this._targetPc.getMr() >= l1skills.gettype14()) {
                    probability -= l1skills.gettype14() / 7;
                }
                if (this._calcType == 1 && l1skills.gettype15() != 0 && this._targetPc.getMr() >= l1skills.gettype15()) {
                    probability -= l1skills.gettype15() / 9;
                }
                if (this._calcType == 1 && l1skills.gettype16() != 0 && this._targetPc.getMr() >= l1skills.gettype16()) {
                    probability -= l1skills.gettype16() / 13;
                }
                if (this._calcType == 1 && l1skills.gettype17() != 0 && this._targetPc.getMr() >= l1skills.gettype17()) {
                    probability -= l1skills.gettype17() / 15;
                }
                if (this._calcType == 1 && l1skills.gettype18() != 0 && this._targetPc.getMr() >= l1skills.gettype18()) {
                    probability -= l1skills.gettype18() / 20;
                }
                if (this._calcType == 1 && l1skills.gettype19() != 0 && this._targetPc.getMr() >= l1skills.gettype19()) {
                    probability -= 15;
                }
                if (l1skills.gettype20() != 0 && attackLevel - defenseLevel > l1skills.gettype20()) {
                    probability = 70;
                }
                if (l1skills.gettype21() != 0 && probability >= l1skills.gettype21()) {
                    probability = l1skills.gettype21();
                }
                if (this._calcType == 1 && l1skills.gettype22() != 0 && this._targetPc.getMr() >= l1skills.gettype22()) {
                    probability = 5;
                }
                if (this._calcType == 1 && l1skills.gettype24() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
                }
                if (this._calcType == 1 && l1skills.gettype25() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
                }
                if (this._calcType == 1 && l1skills.gettype26() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
                }
                if (this._calcType == 1 && l1skills.gettype27() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
                }
                if (this._calcType == 1 && l1skills.gettype28() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
                }
                probability += this._pc.getDoll_MagicHit();
                break;
            }
            default: {
                int dice2 = l1skills.getProbabilityDice();
                int diceCount2 = 0;
                diceCount2 = this.getMagicBonus() + this.getMagicLevel();
                diceCount2 = Math.max(diceCount2, 1);
                for (int i = 0; i < diceCount2; ++i) {
                    if (dice2 > 0) {
                        probability += L1MagicPc._random.nextInt(dice2) + 1;
                    }
                }
                probability += this._pc.getOriginalMagicHit();
                probability += magichit;
                probability -= this.getTargetMr() / 10;
                probability += this._pc.getDoll_MagicHit();
                if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
                    probability = l1skills.gettype1();
                }
                if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
                    probability = l1skills.gettype2();
                }
                if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
                    probability = l1skills.gettype3();
                }
                if (l1skills.gettype4() != 0) {
                    probability += l1skills.gettype4();
                }
                if (l1skills.gettype5() != 0) {
                    probability += l1skills.gettype5() * ((attackLevel - defenseLevel) / 10);
                }
                if (l1skills.gettype6() != 0) {
                    probability += l1skills.gettype6() * this._pc.getInt() / 10;
                }
                if (l1skills.gettype7() != 0) {
                    probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
                }
                if (l1skills.gettype8() != 0) {
                    probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
                }
                if (l1skills.gettype9() != 0) {
                    probability += l1skills.gettype9() + (attackLevel - defenseLevel);
                }
                if (l1skills.gettype10() != 0 && this._pc.getInt() >= l1skills.gettype10()) {
                    probability += 3;
                }
                if (l1skills.gettype11() != 0) {
                    probability += l1skills.gettype11() * (attackLevel - defenseLevel);
                }
                if (l1skills.gettype12() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype12() + (double)(this._pc.getMr() - l1skills.gettype12()) * 0.02));
                }
                if (l1skills.gettype13() != 0) {
                    probability = (int)((double)probability + ((double)l1skills.gettype13() + (double)(this._pc.getMr() - l1skills.gettype13()) * 0.04));
                }
                if (this._calcType == 1 && l1skills.gettype14() != 0 && this._targetPc.getMr() >= l1skills.gettype14()) {
                    probability -= l1skills.gettype14() / 7;
                }
                if (this._calcType == 1 && l1skills.gettype15() != 0 && this._targetPc.getMr() >= l1skills.gettype15()) {
                    probability -= l1skills.gettype15() / 9;
                }
                if (this._calcType == 1 && l1skills.gettype16() != 0 && this._targetPc.getMr() >= l1skills.gettype16()) {
                    probability -= l1skills.gettype16() / 13;
                }
                if (this._calcType == 1 && l1skills.gettype17() != 0 && this._targetPc.getMr() >= l1skills.gettype17()) {
                    probability -= l1skills.gettype17() / 15;
                }
                if (this._calcType == 1 && l1skills.gettype18() != 0 && this._targetPc.getMr() >= l1skills.gettype18()) {
                    probability -= l1skills.gettype18() / 20;
                }
                if (this._calcType == 1 && l1skills.gettype19() != 0 && this._targetPc.getMr() >= l1skills.gettype19()) {
                    probability -= 15;
                }
                if (l1skills.gettype20() != 0 && attackLevel - defenseLevel > l1skills.gettype20()) {
                    probability = 70;
                }
                if (l1skills.gettype21() != 0 && probability >= l1skills.gettype21()) {
                    probability = l1skills.gettype21();
                }
                if (this._calcType == 1 && l1skills.gettype22() != 0 && this._targetPc.getMr() >= l1skills.gettype22()) {
                    probability = 5;
                }
                if (this._calcType == 1 && l1skills.gettype24() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
                }
                if (this._calcType == 1 && l1skills.gettype25() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
                }
                if (this._calcType == 1 && l1skills.gettype26() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
                }
                if (this._calcType == 1 && l1skills.gettype27() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
                }
                if (this._calcType == 1 && l1skills.gettype28() != 0) {
                    probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
                }
                if (skillId == 36) {
                    double probabilityRevision = 1.0;
                    if (this._targetNpc.getMaxHp() * 1 / 4 > this._targetNpc.getCurrentHp()) {
                        probabilityRevision = 1.3;
                    }
                    else if (this._targetNpc.getMaxHp() * 2 / 4 > this._targetNpc.getCurrentHp()) {
                        probabilityRevision = 1.2;
                    }
                    else if (this._targetNpc.getMaxHp() * 3 / 4 > this._targetNpc.getCurrentHp()) {
                        probabilityRevision = 1.1;
                    }
                    probability = (int)((double)probability * probabilityRevision);
                    break;
                }
                else {
                    break;
                }
            }
        }
        switch (this._calcType) {
            case 1: {
                switch (skillId) {
                    case 20:
                    case 40: {
                        probability -= this._targetPc.getRegistBlind();
                        break;
                    }
                    case 33: {
                        probability -= this._targetPc.getRegistStone();
                        break;
                    }
                    case 50:
                    case 80: {
                        probability -= this._targetPc.getRegistFreeze();
                        break;
                    }
                    case 66:
                    case 103:
                    case 212: {
                        probability -= this._targetPc.getRegistSleep();
                        break;
                    }
                    case 87: {
                        probability -= this._targetPc.getRegistStun();
                        break;
                    }
                    case 157:
                    case 192: {
                        probability -= this._targetPc.getRegistSustain();
                        break;
                    }
                }
            }
        }
        return probability;
    }
    
    @Override
    public int calcMagicDamage(int skillId) {
        int damage = 0;
        switch (this._calcType) {
            case 1: {
                damage = this.calcPcMagicDamage(skillId);
                break;
            }
            case 2: {
                damage = this.calcNpcMagicDamage(skillId);
                break;
            }
        }
        L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
        if (l1skills.getTarget().equals("attack") && l1skills.getArea() == 0) {
            this.AttrAmuletEffect();
            this.MoonAmuletEffect();
            this.Imperius_Tshirt_Effect();
        }
        damage = this.calcMrDefense(damage);
        // ✅ 加入 ConfigRate.MagicNpcDamage 控制開關
        if (ConfigRate.MagicNpcDamage && this._calcType == 2 && this._targetNpc != null) {
            L1AttackList.accumulateNpcDamage(this._pc, this._targetNpc, (int) damage);
        }
        return damage;
    }
    
    private int calcPcMagicDamage(int skillId) {
        if (this._targetPc == null) {
            return 0;
        }
        else if (this._targetPc == this._pc) {
            return 0;
        }
        else if (L1MagicMode.dmg0(this._targetPc)) {
            return 0;
        }
        else if (this.calcEvasion()) {
            return 0;
        }
        else if (this._targetPc.getnewcharpra()) {
            return 0;
        }
        else if (this._pc.getnewcharpra()) {
            return 0;
        }
        else {
            int dmg = 0;
            if (skillId == 108) {
                dmg = this._pc.getCurrentMp();
            }
            else {
                dmg = this.calcMagicDiceDamage(skillId);
                dmg = (int)((double)dmg * ((double)this.getLeverage() / 10.0));
            }
            if (this._pc.get_other().get_Ran_PvP_magic_b() > 0 && L1MagicPc._random.nextInt(100) + 1 <= this._pc.get_other().get_Ran_PvP_magic_b()) {
                if (this._pc.get_other().getPvP_magic_b() >= 1.0) {
                    dmg = (int)((double)dmg * this._pc.get_other().getPvP_magic_b());
                }
                else {
                    dmg = (int)((double)dmg * (1.0 + this._pc.get_other().getPvP_magic_b()));
                }
            }
            if (this._targetPc.get_other().get_PvP_magic_r() > 0) {
                dmg -= this._targetPc.get_other().get_PvP_magic_r();
            }
            dmg -= this._targetPc.getMagicDmgReduction() + this._targetPc.get_magic_reduction_dmg();
            dmg -= this._targetPc.dmgDowe();
            if (this._targetPc.getClanid() != 0) {
                dmg = (int)((double)dmg - L1MagicMode.getDamageReductionByClan(this._targetPc));
            }
            if (this._targetPc.isActived() && !this._targetPc.isSimpleAutoHunt() && this._targetPc.getAu_OtherSet(1) > 0 && this._targetPc.getMap().isTeleportable() && this._targetPc.getAu_AutoSet(0) == 0 && !this._targetPc.hasSkillEffect(79501)) {
                this._targetPc.setSkillEffect(79501, 2000);
            }
            if (this._pc.getmagicdmg() > 0) {
                dmg += this._pc.getmagicdmg();
            }
            if (this._targetPc.hasSkillEffect(88)) {
                int targetPcLvl = Math.max(this._targetPc.getLevel(), 50);
                dmg -= (targetPcLvl - 50) / 5 + 1;
            }
            if (this._pc.isCrown()) {
                dmg = (int)((double)dmg * Config_Pc_Damage.Crown_ADD_MagicPC);
            }
            else if (this._pc.isKnight()) {
                dmg = (int)((double)dmg * Config_Pc_Damage.Knight_ADD_MagicPC);
            }
            else if (this._pc.isElf()) {
                dmg = (int)((double)dmg * Config_Pc_Damage.Elf_ADD_MagicPC);
            }
            else if (this._pc.isDarkelf()) {
                dmg = (int)((double)dmg * Config_Pc_Damage.Darkelf_ADD_MagicPC);
            }
            else if (this._pc.isWizard()) {
                dmg = (int)((double)dmg * Config_Pc_Damage.Wizard_ADD_MagicPC);
            }
            else if (this._pc.isDragonKnight()) {
                dmg = (int)((double)dmg * Config_Pc_Damage.DragonKnight_ADD_MagicPC);
            }
            else if (this._pc.isIllusionist()) {
                dmg = (int)((double)dmg * Config_Pc_Damage.Illusionist_ADD_MagicPC);
            }
            int ran = L1MagicPc._random.nextInt(100) + 1;
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
            if (this._pc.isWizard() && this._pc.getlogpcpower_SkillFor1() != 0 && this._pc.getlogpcpower_SkillFor1() >= 1) {
                if (RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor1()) {
                    this._pc.sendPackets(new S_SkillSound(this._pc.getId(), 5377));
                    this._pc.sendPackets(new S_SystemMessage("觸發 致命一擊"));
                    this._pc.setchecklogpc(true);
                }
                else {
                    this._pc.setchecklogpc(false);
                }
            }
            if (this._pc.isWizard() && this._pc.getEsotericCount() != 0) {
                if (this._pc.getEsotericCount() == 4) {
                    dmg = (int)((double)dmg * (1.0 + 0.12 * (double)this._pc.getlogpcpower_SkillFor5()));
                }
                else if (this._pc.getEsotericCount() == 3) {
                    dmg = (int)((double)dmg * (1.0 + 0.09 * (double)this._pc.getlogpcpower_SkillFor5()));
                }
                else if (this._pc.getEsotericCount() == 2) {
                    dmg = (int)((double)dmg * (1.0 + 0.06 * (double)this._pc.getlogpcpower_SkillFor5()));
                }
                else if (this._pc.getEsotericCount() == 1) {
                    dmg = (int)((double)dmg * (1.0 + 0.03 * (double)this._pc.getlogpcpower_SkillFor5()));
                }
            }
            if (this._pc.get魔法爆擊發動率() > 0 && this._pc.get魔法爆擊倍率() > 0.0 && ran <= this._pc.get魔法爆擊發動率()) {
                dmg = (int)((double)dmg * this._pc.get魔法爆擊倍率());
            }
            if (this._targetPc.hasSkillEffect(68)) {
                dmg = (int)((double)dmg / ConfigOther.IMMUNE_TO_HARM);
            }
            if ((this._targetPc.hasSkillEffect(6685) || this._targetPc.hasSkillEffect(6687) || this._targetPc.hasSkillEffect(6688) || this._targetPc.hasSkillEffect(6689)) && L1MagicPc._random.nextInt(100) < 10) {
                dmg /= 2;
                this._targetPc.sendPacketsAll(new S_SkillSound(this._targetPc.getId(), 6320));
            }
            if (this._targetPc.hasSkillEffect(134) && this._calcType == 1 && this._targetPc.getWis() >= L1MagicPc._random.nextInt(100)) {
                this._pc.sendPacketsAll(new S_DoActionGFX(this._pc.getId(), 2));
                this._pc.receiveDamage(this._targetPc, (double)dmg, false, false);
                this._pc.sendPacketsAll(new S_SkillSound(this._targetPc.getId(), 4395));
                dmg = 0;
                this._targetPc.killSkillEffectTimer(134);
            }
            return Math.max(dmg, 0);
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
            int npcId = this._targetNpc.getNpcTemplate().get_npcId();
            Integer tgskill = L1AttackList.SKNPC.get((Integer)npcId);
            if (tgskill != null && !this._pc.hasSkillEffect(tgskill)) {
                return 0;
            }
            else {
                Integer tgpoly = L1AttackList.PLNPC.get((Integer)npcId);
                if (tgpoly != null && tgpoly.equals((Integer)this._pc.getTempCharGfx())) {
                    return 0;
                }
                else {
                    int dmg = 0;
                    if (skillId == 108) {
                        dmg = this._pc.getCurrentMp();
                    }
                    else {
                        dmg = this.calcMagicDiceDamage(skillId);
                        dmg = (int)((double)dmg * ((double)this.getLeverage() / 10.0));
                    }
                    boolean isNowWar = false;
                    int castleId = L1CastleLocation.getCastleIdByArea(this._targetNpc);
                    if (castleId > 0) {
                        isNowWar = ServerWarExecutor.get().isNowWar(castleId);
                    }
                    boolean isPet = false;
                    if (this._targetNpc instanceof L1PetInstance) {
                        isPet = true;
                        if (this._targetNpc.getMaster().equals(this._pc)) {
                            dmg = 0;
                        }
                    }
                    if (this._targetNpc instanceof L1SummonInstance) {
                        L1SummonInstance summon = (L1SummonInstance)this._targetNpc;
                        if (summon.isExsistMaster()) {
                            isPet = true;
                        }
                        if (this._targetNpc.getMaster().equals(this._pc)) {
                            dmg = 0;
                        }
                    }
                    if (!isNowWar && isPet && dmg != 0) {
                        dmg /= 8;
                    }
                    if (this._pc.get_followmaster() != null) {
                        dmg = (int)((double)dmg * 0.5);
                    }
                    if (!this._targetNpc.getNpcTemplate().is_boss()) {
                        L1Npc template = NpcTable.get().getTemplate(this._targetNpc.getNpcId());
                        if (template.getImpl().equals("L1Monster") && !this._pc.isActived() && this._pc.get_followmaster() == null && this._pc.hasSkillEffect(6931)) {
                            this._pc.killSkillEffectTimer(6931);
                            this._pc.setSkillEffect(6932, 1000);
                        }
                    }
                    if (this._pc.get魔法爆擊發動率() > 0 && this._pc.get魔法爆擊倍率() > 0.0 && L1MagicPc._random.nextInt(100) + 1 <= this._pc.get魔法爆擊發動率()) {
                        dmg = (int)((double)dmg * this._pc.get魔法爆擊倍率());
                    }
                    if (this._targetNpc.hasSkillEffect(68)) {
                        dmg /= 2;
                    }
                    if (this._targetNpc.hasSkillEffect(134) && this._calcType == 2 && this._targetNpc.getWis() >= L1MagicPc._random.nextInt(100)) {
                        this._pc.sendPacketsAll(new S_DoActionGFX(this._pc.getId(), 2));
                        this._pc.receiveDamage(this._targetNpc, (double)dmg, false, false);
                        this._targetNpc.broadcastPacketAll(new S_SkillSound(this._targetNpc.getId(), 4395));
                        dmg = 0;
                        this._targetNpc.killSkillEffectTimer(134);
                    }
                    if (this._targetNpc.hasSkillEffect(11059) && this._calcType == 2) {
                        this._pc.sendPacketsAll(new S_DoActionGFX(this._pc.getId(), 2));
                        this._pc.receiveDamage(this._targetNpc, (double)dmg, false, false);
                        this._targetNpc.broadcastPacketAll(new S_SkillSound(this._targetNpc.getId(), 4395));
                        dmg = 0;
                    }
                    return dmg;
                }
            }
        }
    }
    
    private static boolean isInWarAreaAndWarTime(L1PcInstance pc) {
        int castleId = L1CastleLocation.getCastleIdByArea(pc);
        return castleId != 0 && ServerWarExecutor.get().isNowWar(castleId);
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
                    if (L1MagicPc._random.nextInt(1000) < rnd) {
                        if (this._targetPc.hasSkillEffect(68)) {
                            dmg /= 2;
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
                    if (L1MagicPc._random.nextInt(1000) < rnd) {
                        if (this._targetNpc.hasSkillEffect(68)) {
                            dmg /= 2;
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
    
    private int calcMagicDiceDamage(int skillId) {
        L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
        int dice = l1skills.getDamageDice();
        int diceCount = l1skills.getDamageDiceCount();
        int value = l1skills.getDamageValue();
        int magicDamage = 0;
        int charaIntelligence = 0;
        for (int i = 0; i < diceCount; ++i) {
            magicDamage += L1MagicPc._random.nextInt(dice) + 1;
        }
        magicDamage += value;
        if (this._pc.getClanid() != 0) {
            magicDamage = (int)((double)magicDamage + L1MagicMode.getDamageUpByClan(this._pc));
        }
        int spByItem = this.getTargetSp();
        charaIntelligence = Math.max(this._pc.getInt() + spByItem - 12, 1);
        double attrDeffence = this.calcAttrResistance(l1skills.getAttr());
        double coefficient = Math.max(1.0 - attrDeffence + (double)charaIntelligence * 6.0 / 32.0, 0.0);
        magicDamage = (int)((double)magicDamage * coefficient);
        int rnd = L1MagicPc._random.nextInt(100) + 1;
        if ((l1skills.getSkillLevel() <= 6 || skillId == 77) && rnd <= 10 + this._pc.getOriginalMagicCritical()) {
            double criticalCoefficient = 1.5;
            magicDamage = (int)((double)magicDamage * criticalCoefficient);
            this._pc.setMagicCritical(true);
        }
        magicDamage += this._pc.getOriginalMagicDamage() + this._pc.getMagicDmgModifier();
        return magicDamage;
    }
    
    @Override
    public int calcHealing(int skillId) {
        L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
        int dice = l1skills.getDamageDice();
        int value = l1skills.getDamageValue();
        int magicDamage = 0;
        int magicBonus = Math.min(this.getMagicBonus(), 30);
        for (int diceCount = value + magicBonus, i = 0; i < diceCount; ++i) {
            magicDamage += L1MagicPc._random.nextInt(dice) + 1;
        }
        double alignmentRevision = 1.0;
        if (this.getLawful() > 0) {
            alignmentRevision += (double)this.getLawful() / 32768.0;
        }
        magicDamage = (int)((double)magicDamage * alignmentRevision);
        magicDamage = (int)((double)magicDamage * ((double)this.getLeverage() / 10.0));
        return magicDamage;
    }
    
    private void Imperius_Tshirt_Effect() {
        int value = 0;
        int rnd = this._pc.get_Imperius_Tshirt_rnd();
        int min = this._pc.get_Tshirt_drainingHP_min();
        int max = this._pc.get_Tshirt_drainingHP_max();
        if (rnd <= 0) {
            return;
        }
        else {
            switch (this._calcType) {
                case 1: {
                    if (L1MagicPc._random.nextInt(1000) < rnd) {
                        value = L1MagicPc._random.nextInt(max - min + 1) + min;
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
                    if (L1MagicPc._random.nextInt(1000) < rnd) {
                        value = L1MagicPc._random.nextInt(max - min + 1) + min;
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
    
    private int calcMrDefense(int dmg) {
        int mr = this.getTargetMr();
        double mrFloor = 0.0;
        if (mr < 100) {
            mrFloor = Math.floor((double)((mr - this._pc.getOriginalMagicHit()) / 2));
        }
        else if (mr >= 100) {
            mrFloor = Math.floor((double)((mr - this._pc.getOriginalMagicHit()) / 10));
        }
        double mrCoefficient = 0.0;
        if (mr < 100) {
            mrCoefficient = 1.0 - 0.01 * mrFloor;
        }
        else if (mr >= 100) {
            mrCoefficient = 0.6 - 0.01 * mrFloor;
        }
        int Mgs = 0;
        int othersp = IntSp.getIntSpSkillMgs(this._pc, (int)this._pc.getInt());
        if (othersp != 0) {
            Mgs += othersp;
            if (Mgs < 0) {
                Mgs = 0;
            }
        }
        if (!this._pc.getchecklogpc() && Mgs < L1MagicPc._random.nextInt(100) + 1) {
            dmg = (int)((double)dmg * mrCoefficient);
        }
        this._pc.setchecklogpc(false);
        if (this._calcType == 1) {
            int othersp2 = PcMr.getIntSpSkillMgs(this._targetPc.getMr());
            if (othersp2 != 0) {
                dmg -= othersp2;
                if (dmg < 0) {
                    dmg = 0;
                }
            }
        }
        return dmg;
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
                    if (L1MagicPc._random.nextInt(1000) < rnd) {
                        damage = L1MagicPc._random.nextInt(dmgmax - dmgmin + 1) + dmgmin;
                        if (this._targetPc.hasSkillEffect(68)) {
                            damage /= 2;
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
                    if (L1MagicPc._random.nextInt(1000) < rnd) {
                        damage = L1MagicPc._random.nextInt(dmgmax - dmgmin + 1) + dmgmin;
                        if (this._targetNpc.hasSkillEffect(68)) {
                            damage /= 2;
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
    public void commit(int damage, int drainMana) {
        if (this._calcType == 1 && ConfigGuaji.Guaji_save && this._targetPc.isActived() && this._targetPc.getLevel() <= ConfigGuaji.Guaji_level) {
            return;
        }
        else {
            int i = (int)((double)damage / Math.pow(10.0, 0.0) % 10.0) + ConfigOther.Attack_1;
            int k = (int)((double)damage / Math.pow(10.0, 1.0) % 10.0) + ConfigOther.Attack_2;
            int h = (int)((double)damage / Math.pow(10.0, 2.0) % 10.0) + ConfigOther.Attack_3;
            int s = (int)((double)damage / Math.pow(10.0, 3.0) % 10.0) + ConfigOther.Attack_4;
            int m = (int)((double)damage / Math.pow(10.0, 4.0) % 10.0) + ConfigOther.Attack_5;
            switch (this._calcType) {
                case 1: {
                    this.commitPc(damage, drainMana);
                    if (this._pc.hasSkillEffect(1688) && !this._pc.isActived()) {
                        if (damage > 0 && damage < 10) {
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), i));
                            break;
                        }
                        else if (damage >= 10 && damage < 100) {
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), i));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), k));
                            break;
                        }
                        else if (damage >= 100 && damage < 1000) {
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), i));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), k));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), h));
                            break;
                        }
                        else if (damage >= 1000 && damage < 10000) {
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), i));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), k));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), h));
                            this._pc.sendPackets(new S_SkillSound(this._targetPc.getId(), s));
                            break;
                        }
                        else if (damage >= 10000) {
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
                    this.commitNpc(damage, drainMana);
                    if (this._pc.hasSkillEffect(1688) && !this._pc.isActived()) {
                        if (damage > 0 && damage < 10) {
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), i));
                            break;
                        }
                        else if (damage >= 10 && damage < 100) {
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), i));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), k));
                            break;
                        }
                        else if (damage >= 100 && damage < 1000) {
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), i));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), k));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), h));
                            break;
                        }
                        else if (damage >= 1000 && damage < 10000) {
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), i));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), k));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), h));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), s));
                            break;
                        }
                        else if (damage >= 10000) {
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), i));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), k));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), h));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), s));
                            this._pc.sendPackets(new S_SkillSound(this._targetNpc.getId(), m));
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
            if (!ConfigAlt.ALT_ATKMSG) {
                return;
            }
            else {
                switch (this._calcType) {
                    case 1: {
                        if (this._pc.getAccessLevel() == 0 && this._targetPc.getAccessLevel() == 0) {
                            return;
                        }
                        else {
                            break;
                        }
                    }
                    case 2: {
                        if (this._pc.getAccessLevel() == 0) {
                            return;
                        }
                        else {
                            break;
                        }
                    }
                }
                switch (this._calcType) {
                    case 1: {
                        if (this._pc.getAccessLevel() > 0) {
                            StringBuilder atkMsg = new StringBuilder();
                            atkMsg.append("對PC送出技能: ");
                            atkMsg.append(this._pc.getName() + ">");
                            atkMsg.append(this._targetPc.getName() + " ");
                            atkMsg.append("傷害: " + damage);
                            atkMsg.append(" 目標HP:" + this._targetPc.getCurrentHp());
                            this._pc.sendPackets(new S_ServerMessage(166, atkMsg.toString()));
                        }
                        if (this._targetPc.getAccessLevel() > 0) {
                            StringBuilder atkMsg = new StringBuilder();
                            atkMsg.append("受到PC技能: ");
                            atkMsg.append(this._pc.getName() + ">");
                            atkMsg.append(this._targetPc.getName() + " ");
                            atkMsg.append("傷害: " + damage);
                            atkMsg.append(" 目標HP:" + this._targetPc.getCurrentHp());
                            this._targetPc.sendPackets(new S_ServerMessage(166, atkMsg.toString()));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 2: {
                        if (this._pc.getAccessLevel() > 0) {
                            StringBuilder atkMsg = new StringBuilder();
                            atkMsg.append("對NPC送出技能: ");
                            atkMsg.append(this._pc.getName() + ">");
                            atkMsg.append(this._targetNpc.getNameId() + " ");
                            atkMsg.append("傷害: " + damage);
                            atkMsg.append(" 目標HP:" + this._targetNpc.getCurrentHp());
                            this._pc.sendPackets(new S_ServerMessage(166, atkMsg.toString()));
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
    }
    
    private void commitPc(int damage, int drainMana) {
        try {
            if (drainMana > 0) {
                if (this._targetPc.getCurrentMp() > 0) {
                    drainMana = Math.min(drainMana, this._targetPc.getCurrentMp());
                    int newMp = this._pc.getCurrentMp() + drainMana;
                    this._pc.setCurrentMp(newMp);
                }
                else {
                    drainMana = 0;
                }
            }
            this._targetPc.receiveManaDamage(this._pc, drainMana);
            this._targetPc.receiveDamage(this._pc, (double)damage, true, false);
        }
        catch (Exception e) {
            L1MagicPc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private boolean isInWarAreaAndWarTime(L1PcInstance pc, L1PcInstance target) {
        int castleId = L1CastleLocation.getCastleIdByArea(pc);
        int targetCastleId = L1CastleLocation.getCastleIdByArea(target);
        return castleId != 0 && targetCastleId != 0 && castleId == targetCastleId && ServerWarExecutor.get().isNowWar(castleId);
    }
    
    private void commitNpc(int damage, int drainMana) {
        try {
            if (drainMana > 0) {
                if (this._targetNpc.getCurrentMp() > 0) {
                    int drainValue = this._targetNpc.drainMana(drainMana);
                    int newMp = this._pc.getCurrentMp() + drainValue;
                    this._pc.setCurrentMp(newMp);
                }
                else {
                    drainMana = 0;
                }
            }
            this._targetNpc.ReceiveManaDamage(this._pc, drainMana);
            this._targetNpc.receiveDamage(this._pc, damage);
        }
        catch (Exception e) {
            L1MagicPc._log.error(e.getLocalizedMessage(), e);
        }
    }
}
