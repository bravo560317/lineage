package com.lineage.server.model;

import java.util.List;
import com.lineage.server.utils.RandomArrayList;
import java.util.ArrayList;
import java.util.Map;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import java.util.HashMap;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_PacketBoxWindShackle;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.skill.L1SkillUse;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.model.skill.L1SkillDelayforMob;
import com.lineage.server.datatables.MobSkillTable;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.templates.L1MobSkill;
import org.apache.commons.logging.Log;

public class L1MobSkillUse
{
    private static final Log _log;
    private L1MobSkill _mobSkillTemplate;
    private L1NpcInstance _attacker;
    private L1Character _target;
    private static final Random _rnd;
    private int _sleepTime;
    private int[] _skillUseCount;
    private int _skillSize;
    
    static {
        _log = LogFactory.getLog(L1MobSkillUse.class);
        _rnd = new Random();
    }
    
    public L1MobSkillUse(L1NpcInstance npc) {
        this._mobSkillTemplate = null;
        this._attacker = null;
        this._target = null;
        this._sleepTime = 0;
        try {
            this._sleepTime = 0;
            this._mobSkillTemplate = MobSkillTable.getInstance().getTemplate(npc.getNpcTemplate().get_npcId());
            if (this._mobSkillTemplate == null) {
                return;
            }
            else {
                this._attacker = npc;
                this._skillSize = this.getMobSkillTemplate().getSkillSize();
                this._skillUseCount = new int[this._skillSize];
            }
        }
        catch (Exception e) {
            L1MobSkillUse._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private int getSkillUseCount(int idx) {
        return this._skillUseCount[idx];
    }
    
    private void skillUseCountUp(int idx) {
        int[] skillUseCount = this._skillUseCount;
        ++skillUseCount[idx];
    }
    
    public void resetAllSkillUseCount() {
        if (this._mobSkillTemplate == null) {
            return;
        }
        else {
            for (int i = 0; i < this._skillSize; ++i) {
                this._skillUseCount[i] = 0;
            }
            return;
        }
    }
    
    public int getSleepTime() {
        return this._sleepTime;
    }
    
    public void setSleepTime(int i) {
        this._sleepTime = i;
    }
    
    public L1MobSkill getMobSkillTemplate() {
        return this._mobSkillTemplate;
    }
    
    public boolean isSkillTrigger(L1Character tg) {
        if (this._mobSkillTemplate == null) {
            return false;
        }
        else {
            this._target = tg;
            int type = this.getMobSkillTemplate().getType(0);
            if (type == 0) {
                return false;
            }
            else {
                int i = 0;
                while (i < this.getMobSkillTemplate().getSkillSize() && this.getMobSkillTemplate().getType(i) != 0) {
                    if (this.isSkillUseble(i, false)) {
                        return true;
                    }
                    else {
                        ++i;
                    }
                }
                return false;
            }
        }
    }
    
    public final boolean skillUse(L1Character tg, boolean isTriRnd) {
        if (this._mobSkillTemplate == null) {
            return false;
        }
        else {
            int type = this._mobSkillTemplate.getType(0);
            if (type == 0) {
                return false;
            }
            else {
                int[] skills = null;
                int skillSizeCounter = 0;
                int skillSize = this._mobSkillTemplate.getSkillSize();
                if (skillSize >= 0) {
                    skills = new int[skillSize];
                }
                int i;
                int changeType;
                for (i = 0, i = 0; i < this.getMobSkillTemplate().getSkillSize() && this.getMobSkillTemplate().getType(i) != 0; ++i) {
                    changeType = this.getMobSkillTemplate().getChangeTarget(i);
                    if (changeType > 0) {
                        this._target = this.changeTarget(changeType, i);
                    }
                    else {
                        this._target = tg;
                    }
                    if (this.isSkillUseble(i, isTriRnd)) {
                        skills[skillSizeCounter] = i;
                        ++skillSizeCounter;
                    }
                }
                if (skillSizeCounter != 0) {
                    int actNo = skills[L1MobSkillUse._rnd.nextInt(skillSizeCounter)];
                    int changeType2 = this._mobSkillTemplate.getChangeTarget(actNo);
                    if (changeType2 > 0) {
                        this._target = this.changeTarget(changeType2, i);
                    }
                    else {
                        this._target = tg;
                    }
                    if (this.useSkill(actNo)) {
                        return true;
                    }
                }
                return false;
            }
        }
    }
    
    private void setDelay(int idx) {
        if (this._mobSkillTemplate.getReuseDelay(idx) > 0) {
            L1SkillDelayforMob.onSkillUse(this._attacker, this._mobSkillTemplate.getReuseDelay(idx), idx);
        }
    }
    
    private boolean useSkill(int idx) {
        if (this._mobSkillTemplate.isSkillDelayIdx(idx)) {
            return false;
        }
        else {
            boolean isUseSkill = false;
            int type = this.getMobSkillTemplate().getType(idx);
            switch (type) {
                case 1: {
                    if (this.physicalAttack(idx)) {
                        this.skillUseCountUp(idx);
                        isUseSkill = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if (this.magicAttack(idx)) {
                        this.skillUseCountUp(idx);
                        this.setDelay(idx);
                        isUseSkill = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 3: {
                    if (this.summon(idx)) {
                        this.skillUseCountUp(idx);
                        this.setDelay(idx);
                        isUseSkill = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 4: {
                    if (this.poly(idx)) {
                        this.skillUseCountUp(idx);
                        this.setDelay(idx);
                        isUseSkill = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 5: {
                    if (this.areashock_stun(idx)) {
                        this.skillUseCountUp(idx);
                        this.setDelay(idx);
                        isUseSkill = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 6: {
                    if (this.areacancellation(idx)) {
                        this.skillUseCountUp(idx);
                        this.setDelay(idx);
                        isUseSkill = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 7: {
                    if (this.weapon_break(idx)) {
                        this.skillUseCountUp(idx);
                        this.setDelay(idx);
                        isUseSkill = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 8: {
                    if (this.potionturntodmg(idx)) {
                        this.skillUseCountUp(idx);
                        this.setDelay(idx);
                        isUseSkill = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 9: {
                    if (this.pollutewaterwave(idx)) {
                        this.skillUseCountUp(idx);
                        this.setDelay(idx);
                        isUseSkill = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 10: {
                    if (this.healturntodmg(idx)) {
                        this.skillUseCountUp(idx);
                        this.setDelay(idx);
                        isUseSkill = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 11: {
                    if (this.areasilence(idx)) {
                        this.skillUseCountUp(idx);
                        this.setDelay(idx);
                        isUseSkill = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 12: {
                    if (this.areadecaypotion(idx)) {
                        this.skillUseCountUp(idx);
                        this.setDelay(idx);
                        isUseSkill = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 13: {
                    if (this.areawindshackle(idx)) {
                        this.skillUseCountUp(idx);
                        this.setDelay(idx);
                        isUseSkill = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 14: {
                    if (this.areadebuff(idx)) {
                        this.skillUseCountUp(idx);
                        this.setDelay(idx);
                        isUseSkill = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 15: {
                    if (this.area_poison(idx)) {
                        this.skillUseCountUp(idx);
                        this.setDelay(idx);
                        isUseSkill = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            return isUseSkill;
        }
    }
    
    private boolean area_poison(int idx) {
        if (this._attacker.getMapId() == 93) {
            return false;
        }
        else {
            int actId = this.getMobSkillTemplate().getActid(idx);
            int actionid = 1;
            if (actId > 0) {
                actionid = actId;
            }
            for (L1PcInstance pc : World.get().getVisiblePlayer(this._attacker)) {
                if (!pc.isGmInvis()) {
                    L1SpawnUtil.spawnEffect(86125, 3, pc.getX(), pc.getY(), this._attacker.getMapId(), this._attacker, 0);
                }
            }
            S_DoActionGFX gfx = new S_DoActionGFX(this._attacker.getId(), actionid);
            this._attacker.broadcastPacketAll(gfx);
            this._sleepTime = this._attacker.getNpcTemplate().getSubMagicSpeed();
            return true;
        }
    }
    
    private boolean areadebuff(int idx) {
        if (this._attacker.getMapId() == 93) {
            return false;
        }
        else {
            int skillid = this.getMobSkillTemplate().getSkillId(idx);
            int actId = this.getMobSkillTemplate().getActid(idx);
            int gfxId = this.getMobSkillTemplate().getGfxid(idx);
            int time = this.getMobSkillTemplate().getLeverage(idx);
            for (L1PcInstance pc : World.get().getVisiblePlayer(this._attacker)) {
                if (skillid == 71 && pc.hasSkillEffect(4011)) {
                    continue;
                }
                else {
                    L1SkillUse skillUse = new L1SkillUse();
                    boolean canUseSkill = false;
                    if (skillid > 0) {
                        canUseSkill = skillUse.checkUseSkill(null, skillid, pc.getId(), pc.getX(), pc.getY(), time, 4, this._attacker, actId, gfxId);
                    }
                    if (canUseSkill && !pc.hasSkillEffect(skillid)) {
                        skillUse.handleCommands(null, skillid, pc.getId(), pc.getX(), pc.getY(), time, 4, this._attacker);
                    }
                    else {
                        continue;
                    }
                }
            }
            int actionid = 19;
            if (actId > 0) {
                actionid = actId;
            }
            this._attacker.broadcastPacketAll(new S_DoActionGFX(this._attacker.getId(), actionid));
            this._sleepTime = this._attacker.getNpcTemplate().getSubMagicSpeed();
            return true;
        }
    }
    
    private boolean areawindshackle(int idx) {
        if (this._attacker.getMapId() == 93) {
            return false;
        }
        else {
            int actId = this.getMobSkillTemplate().getActid(idx);
            int actionid = 19;
            if (actId > 0) {
                actionid = actId;
            }
            int gfxId = this.getMobSkillTemplate().getGfxid(idx);
            for (L1PcInstance pc : World.get().getVisiblePlayer(this._attacker)) {
                if (!pc.hasSkillEffect(167)) {
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), gfxId));
                    pc.sendPackets(new S_PacketBoxWindShackle(pc.getId(), 16));
                    pc.setSkillEffect(167, 16000);
                }
            }
            S_DoActionGFX gfx = new S_DoActionGFX(this._attacker.getId(), actionid);
            this._attacker.broadcastPacketAll(gfx);
            this._sleepTime = this._attacker.getNpcTemplate().getSubMagicSpeed();
            return true;
        }
    }
    
    private boolean areadecaypotion(int idx) {
        if (this._attacker.getMapId() == 93) {
            return false;
        }
        else {
            int actId = this.getMobSkillTemplate().getActid(idx);
            int actionid = 19;
            if (actId > 0) {
                actionid = actId;
            }
            for (L1PcInstance pc : World.get().getVisiblePlayer(this._attacker)) {
                if (!pc.hasSkillEffect(71)) {
                    new L1SkillUse().handleCommands(pc, 71, pc.getId(), pc.getX(), pc.getY(), 0, 4);
                }
            }
            S_DoActionGFX gfx = new S_DoActionGFX(this._attacker.getId(), actionid);
            this._attacker.broadcastPacketAll(gfx);
            this._sleepTime = this._attacker.getNpcTemplate().getSubMagicSpeed();
            return true;
        }
    }
    
    private boolean areasilence(int idx) {
        if (this._attacker.getMapId() == 93) {
            return false;
        }
        else {
            int actId = this.getMobSkillTemplate().getActid(idx);
            int actionid = 19;
            if (actId > 0) {
                actionid = actId;
            }
            for (L1PcInstance pc : World.get().getVisiblePlayer(this._attacker)) {
                if (!pc.hasSkillEffect(64) && !pc.hasSkillEffect(161)) {
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 10708));
                    pc.setSkillEffect(64, 16000);
                }
            }
            S_DoActionGFX gfx = new S_DoActionGFX(this._attacker.getId(), actionid);
            this._attacker.broadcastPacketAll(gfx);
            this._sleepTime = this._attacker.getNpcTemplate().getSubMagicSpeed();
            return true;
        }
    }
    
    private boolean healturntodmg(int idx) {
        if (this._attacker.getMapId() == 93) {
            return false;
        }
        else {
            int actId = this.getMobSkillTemplate().getActid(idx);
            int actionid = 19;
            if (actId > 0) {
                actionid = actId;
            }
            for (L1PcInstance pc : World.get().getVisiblePlayer(this._attacker)) {
                if (!pc.hasSkillEffect(4011) && !pc.hasSkillEffect(4012) && !pc.hasSkillEffect(4013)) {
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 7780));
                    pc.setSkillEffect(4013, 12000);
                }
            }
            S_DoActionGFX gfx = new S_DoActionGFX(this._attacker.getId(), actionid);
            this._attacker.broadcastPacketAll(gfx);
            this._sleepTime = this._attacker.getNpcTemplate().getSubMagicSpeed();
            return true;
        }
    }
    
    private boolean pollutewaterwave(int idx) {
        if (this._attacker.getMapId() == 93) {
            return false;
        }
        else {
            int actId = this.getMobSkillTemplate().getActid(idx);
            int actionid = 19;
            if (actId > 0) {
                actionid = actId;
            }
            for (L1PcInstance pc : World.get().getVisiblePlayer(this._attacker)) {
                if (!pc.hasSkillEffect(4011) && !pc.hasSkillEffect(4012) && !pc.hasSkillEffect(4013)) {
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 7782));
                    pc.setSkillEffect(4012, 12000);
                }
            }
            S_DoActionGFX gfx = new S_DoActionGFX(this._attacker.getId(), actionid);
            this._attacker.broadcastPacketAll(gfx);
            this._sleepTime = this._attacker.getNpcTemplate().getSubMagicSpeed();
            return true;
        }
    }
    
    private boolean potionturntodmg(int idx) {
        if (this._attacker.getMapId() == 93) {
            return false;
        }
        else {
            int actId = this.getMobSkillTemplate().getActid(idx);
            int actionid = 19;
            if (actId > 0) {
                actionid = actId;
            }
            for (L1PcInstance pc : World.get().getVisiblePlayer(this._attacker)) {
                if (!pc.hasSkillEffect(4011) && !pc.hasSkillEffect(4012) && !pc.hasSkillEffect(4013) && !pc.hasSkillEffect(71)) {
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 7781));
                    pc.setSkillEffect(4011, 12000);
                }
            }
            S_DoActionGFX gfx = new S_DoActionGFX(this._attacker.getId(), actionid);
            this._attacker.broadcastPacketAll(gfx);
            this._sleepTime = this._attacker.getNpcTemplate().getSubMagicSpeed();
            return true;
        }
    }
    
    private boolean weapon_break(int idx) {
        if (this._attacker.getMapId() == 93) {
            return false;
        }
        else {
            int actId = this.getMobSkillTemplate().getActid(idx);
            int actionid = 19;
            if (actId > 0) {
                actionid = actId;
            }
            for (L1PcInstance pc : World.get().getVisiblePlayer(this._attacker)) {
                L1ItemInstance weapon = pc.getWeapon();
                if (weapon != null) {
                    int weaponDamage = L1MobSkillUse._rnd.nextInt(this._attacker.getInt() / 3) + 1;
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 172));
                    pc.sendPackets(new S_ServerMessage(268, weapon.getLogName()));
                    pc.getInventory().receiveDamage(weapon, weaponDamage);
                }
            }
            S_DoActionGFX gfx = new S_DoActionGFX(this._attacker.getId(), actionid);
            this._attacker.broadcastPacketAll(gfx);
            this._sleepTime = this._attacker.getNpcTemplate().getSubMagicSpeed();
            return true;
        }
    }
    
    private boolean areacancellation(int idx) {
        if (this._attacker.getMapId() == 93) {
            return false;
        }
        else {
            int actId = this.getMobSkillTemplate().getActid(idx);
            int actionid = 19;
            if (actId > 0) {
                actionid = actId;
            }
            for (L1PcInstance pc : World.get().getVisiblePlayer(this._attacker)) {
                new L1SkillUse().handleCommands(pc, 44, pc.getId(), pc.getX(), pc.getY(), 0, 4);
            }
            S_DoActionGFX gfx = new S_DoActionGFX(this._attacker.getId(), actionid);
            this._attacker.broadcastPacketAll(gfx);
            this._sleepTime = this._attacker.getNpcTemplate().getSubMagicSpeed();
            return true;
        }
    }
    
    private boolean areashock_stun(int idx) {
        if (this._attacker.getMapId() == 93) {
            return false;
        }
        else {
            int actId = this.getMobSkillTemplate().getActid(idx);
            int actionid = 1;
            if (actId > 0) {
                actionid = actId;
            }
            for (L1PcInstance pc : World.get().getVisiblePlayer(this._attacker)) {
                Random random = new Random();
                int shock = random.nextInt(4) + 1;
                if (pc.hasSkillEffect(87)) {
                    shock += pc.getSkillEffectTimeSec(87);
                }
                if (shock > 4) {
                    shock = 4;
                }
                if (!pc.isGmInvis()) {
                    pc.setSkillEffect(87, shock * 1000);
                    pc.sendPackets(new S_Paralysis(5, true));
                    L1SpawnUtil.spawnEffect(81162, shock, pc.getX(), pc.getY(), this._attacker.getMapId(), this._attacker, 0);
                }
            }
            S_DoActionGFX gfx = new S_DoActionGFX(this._attacker.getId(), actionid);
            this._attacker.broadcastPacketAll(gfx);
            this._sleepTime = this._attacker.getNpcTemplate().getSubMagicSpeed();
            return true;
        }
    }
    
    private boolean summon(int idx) {
        if (this._attacker.getMapId() == 93) {
            return false;
        }
        else {
            int actId = this.getMobSkillTemplate().getActid(idx);
            int actionid = 19;
            if (actId > 0) {
                actionid = actId;
            }
            int summonId = this.getMobSkillTemplate().getSummon(idx);
            int min = this.getMobSkillTemplate().getSummonMin(idx);
            int max = this.getMobSkillTemplate().getSummonMax(idx);
            int count = 0;
            if (summonId == 0) {
                return false;
            }
            else {
                count = L1MobSkillUse._rnd.nextInt(max) + min;
                L1MobSkillUseSpawn skillUseSpawn = new L1MobSkillUseSpawn();
                skillUseSpawn.mobspawn(this._attacker, this._target, summonId, count);
                this._attacker.broadcastPacketAll(new S_SkillSound(this._attacker.getId(), 761));
                S_DoActionGFX gfx = new S_DoActionGFX(this._attacker.getId(), actionid);
                this._attacker.broadcastPacketAll(gfx);
                this._sleepTime = this._attacker.getNpcTemplate().getSubMagicSpeed();
                return true;
            }
        }
    }
    
    private boolean poly(int idx) {
        if (this._attacker.getMapId() == 93) {
            return false;
        }
        else {
            int polyId = this.getMobSkillTemplate().getPolyId(idx);
            int actId = this.getMobSkillTemplate().getActid(idx);
            int actionid = 19;
            if (actId > 0) {
                actionid = actId;
            }
            boolean usePoly = false;
            if (polyId == 0) {
                return false;
            }
            else {
                for (L1PcInstance pc : World.get().getVisiblePlayer(this._attacker)) {
                    if (!pc.isDead() && !pc.isGhost() && !pc.isGmInvis() && this._attacker.glanceCheck(pc.getX(), pc.getY())) {
                        int npcId = this._attacker.getNpcTemplate().get_npcId();
                        switch (npcId) {
                            case 81082: {
                                pc.getInventory().takeoffEquip(945);
                                break;
                            }
                        }
                        L1PolyMorph.doPoly(pc, polyId, 1800, 4);
                        usePoly = true;
                    }
                }
                if (usePoly) {
                    Iterator<L1PcInstance> localIterator = (Iterator)World.get().getVisiblePlayer(this._attacker).iterator();
                    if (localIterator.hasNext()) {
                        L1PcInstance pc = localIterator.next();
                        pc.sendPacketsAll(new S_SkillSound(pc.getId(), 230));
                    }
                    S_DoActionGFX gfx = new S_DoActionGFX(this._attacker.getId(), actionid);
                    this._attacker.broadcastPacketAll(gfx);
                    this._sleepTime = this._attacker.getNpcTemplate().getSubMagicSpeed();
                }
                return usePoly;
            }
        }
    }
    
    private boolean magicAttack(int idx) {
        L1SkillUse skillUse = new L1SkillUse();
        int skillid = this.getMobSkillTemplate().getSkillId(idx);
        int actId = this.getMobSkillTemplate().getActid(idx);
        int gfxId = this.getMobSkillTemplate().getGfxid(idx);
        boolean canUseSkill = false;
        if (skillid > 0) {
            canUseSkill = skillUse.checkUseSkill(null, skillid, this._target.getId(), this._target.getX(), this._target.getY(), 0, 0, this._attacker, actId, gfxId);
        }
        if (canUseSkill) {
            if (this.getMobSkillTemplate().getLeverage(idx) > 0) {
                skillUse.setLeverage(this.getMobSkillTemplate().getLeverage(idx));
            }
            skillUse.handleCommands(null, skillid, this._target.getId(), this._target.getX(), this._target.getX(), 0, 0, this._attacker);
            L1Skills skill = SkillsTable.get().getTemplate(skillid);
            if (skill.getTarget().equals("attack") && skillid != 18) {
                this._sleepTime = this._attacker.getNpcTemplate().getAtkMagicSpeed();
            }
            else {
                this._sleepTime = this._attacker.getNpcTemplate().getSubMagicSpeed();
            }
            return true;
        }
        else {
            return false;
        }
    }
    
    private boolean physicalAttack(int idx) {
        Map<Integer, Integer> targetList = new HashMap();
        int areaWidth = this.getMobSkillTemplate().getAreaWidth(idx);
        int areaHeight = this.getMobSkillTemplate().getAreaHeight(idx);
        int range = this.getMobSkillTemplate().getRange(idx);
        int actId = this.getMobSkillTemplate().getActid(idx);
        int gfxId = this.getMobSkillTemplate().getGfxid(idx);
        if (this._attacker.getLocation().getTileLineDistance(this._target.getLocation()) > range) {
            return false;
        }
        else if (!this._attacker.glanceCheck(this._target.getX(), this._target.getY())) {
            return false;
        }
        else {
            this._attacker.setHeading(this._attacker.targetDirection(this._target.getX(), this._target.getY()));
            if (areaHeight > 0) {
                ArrayList<L1Object> objs = World.get().getVisibleBoxObjects(this._attacker, this._attacker.getHeading(), areaWidth, areaHeight);
                for (L1Object obj : objs) {
                    if (!(obj instanceof L1Character)) {
                        continue;
                    }
                    else {
                        L1Character cha = (L1Character)obj;
                        if (cha.isDead()) {
                            continue;
                        }
                        else if (cha instanceof L1PcInstance && ((L1PcInstance)cha).isGhost()) {
                            continue;
                        }
                        else if (!this._attacker.glanceCheck(cha.getX(), cha.getY())) {
                            continue;
                        }
                        else if (this._target instanceof L1PcInstance || this._target instanceof L1SummonInstance || this._target instanceof L1PetInstance) {
                            if ((obj instanceof L1PcInstance && !((L1PcInstance)obj).isGhost() && !((L1PcInstance)obj).isGmInvis()) || obj instanceof L1SummonInstance || obj instanceof L1PetInstance) {
                                targetList.put(obj.getId(), 0);
                            }
                            else {
                                continue;
                            }
                        }
                        else if (obj instanceof L1MonsterInstance) {
                            targetList.put(obj.getId(), 0);
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            else {
                targetList.put(this._target.getId(), 0);
            }
            if (targetList.size() == 0) {
                return false;
            }
            else {
                Iterator<Integer> ite = (Iterator)targetList.keySet().iterator();
                while (ite.hasNext()) {
                    int targetId = ite.next();
                    L1Object object = World.get().findObject(targetId);
                    L1AttackMode attack = new L1AttackNpc(this._attacker, (L1Character)object);
                    if (attack.calcHit()) {
                        if (this.getMobSkillTemplate().getLeverage(idx) > 0) {
                            attack.setLeverage(this.getMobSkillTemplate().getLeverage(idx));
                        }
                        attack.calcDamage();
                    }
                    if (actId > 0) {
                        attack.setActId(actId);
                    }
                    if (targetId == this._target.getId()) {
                        if (gfxId > 0) {
                            this._attacker.broadcastPacketAll(new S_SkillSound(this._attacker.getId(), gfxId));
                        }
                        attack.action();
                    }
                    attack.commit();
                }
                this._sleepTime = this._attacker.getAtkspeed();
                return true;
            }
        }
    }
    
    private boolean isSkillUseble(int skillIdx, boolean isTriRnd) {
if (this._target == null) {
    return false;
}
        boolean useble = false;
        int type = this.getMobSkillTemplate().getType(skillIdx);
        int chance = RandomArrayList.getInc(100, 1);
        if (chance > this.getMobSkillTemplate().getTriggerRandom(skillIdx)) {
            return false;
        }
        else {
            if (isTriRnd || type == 3 || type == 4) {
                useble = true;
            }
            if (this.getMobSkillTemplate().getTriggerHp(skillIdx) > 0) {
                int hpRatio = this._attacker.getCurrentHp() * 100 / this._attacker.getMaxHp();
                if (hpRatio <= this.getMobSkillTemplate().getTriggerHp(skillIdx)) {
                    useble = true;
                }
                else {
                    return false;
                }
            }
            if (this.getMobSkillTemplate().getTriggerCompanionHp(skillIdx) > 0) {
                L1NpcInstance companionNpc = this.searchMinCompanionHp();
                if (companionNpc != null) {
                    int hpRatio2 = companionNpc.getCurrentHp() * 100 / companionNpc.getMaxHp();
                    if (hpRatio2 <= this.getMobSkillTemplate().getTriggerCompanionHp(skillIdx)) {
                        useble = true;
                        this._target = companionNpc;
                    }
                    else {
                        return false;
                    }
                }
            }
            if (this.getMobSkillTemplate().getTriggerRange(skillIdx) != 0) {
                int distance = this._attacker.getLocation().getTileLineDistance(this._target.getLocation());
                if (this.getMobSkillTemplate().isTriggerDistance(skillIdx, distance)) {
                    useble = true;
                }
                else {
                    return false;
                }
            }
            if (this.getMobSkillTemplate().getTriggerCount(skillIdx) > 0) {
                if (this.getSkillUseCount(skillIdx) < this.getMobSkillTemplate().getTriggerCount(skillIdx)) {
                    useble = true;
                }
                else {
                    return false;
                }
            }
            return useble;
        }
    }
    
    private L1NpcInstance searchMinCompanionHp() {
        L1NpcInstance minHpNpc = null;
        int family = this._attacker.getNpcTemplate().get_family();
        for (L1Object object : World.get().getVisibleObjects(this._attacker)) {
            if (object instanceof L1NpcInstance) {
                L1NpcInstance npc = (L1NpcInstance)object;
                if (npc.getNpcTemplate().get_family() == family) {
                    minHpNpc = npc;
                }
                else {
                    continue;
                }
            }
        }
        return minHpNpc;
    }
    
    private L1Character changeTarget(int type, int idx) {
        L1Character target = null;
        switch (type) {
            case 2: {
                target = this._attacker;
                break;
            }
            case 3: {
                List<L1Character> targetList = new ArrayList();
                for (L1Object obj : World.get().getVisibleObjects(this._attacker)) {
                    if (obj instanceof L1PcInstance || obj instanceof L1PetInstance || obj instanceof L1SummonInstance || (this._attacker.getMapId() == 93 && obj instanceof L1MonsterInstance)) {
                        L1Character cha = (L1Character)obj;
                        int distance = this._attacker.getLocation().getTileLineDistance(cha.getLocation());
                        if (!this.getMobSkillTemplate().isTriggerDistance(idx, distance)) {
                            continue;
                        }
                        else if (!this._attacker.glanceCheck(cha.getX(), cha.getY())) {
                            continue;
                        }
                        else if (this._attacker.getMapId() != 93 && !this._attacker.getHateList().containsKey(cha)) {
                            continue;
                        }
                        else if (cha.isDead()) {
                            continue;
                        }
                        else {
                            if (cha instanceof L1PcInstance) {
                                if (((L1PcInstance)cha).isGhost()) {
                                    continue;
                                }
                                else if (((L1PcInstance)cha).isGmInvis()) {
                                    continue;
                                }
                            }
                            targetList.add((L1Character)obj);
                        }
                    }
                }
                if (targetList.size() == 0) {
                    target = this._target;
                    break;
                }
                else {
                    int targetIndex = L1MobSkillUse._rnd.nextInt(targetList.size());
                    target = targetList.get(targetIndex);
                    break;
                }
            }
            default: {
                target = this._target;
                break;
            }
        }
        return target;
    }
}
