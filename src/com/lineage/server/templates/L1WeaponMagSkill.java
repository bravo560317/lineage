package com.lineage.server.templates;

import java.util.ArrayList;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.serverpackets.S_UseAttackSkill;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.L1WeaponSkill;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.List;
import java.util.Random;

public class L1WeaponMagSkill
{
    private static final Random _random;
    private final int _item_id;
    private final String _skill_name;
    private final int _success_random;
    private final int _max_use_time;
    private final String _success_msg;
    private final String _failure_msg;
    private final int _probability;
    private final boolean _isLongRange;
    private final int _fixDamage;
    private final int _randomDamage;
    private final double _doubleDmgValue;
    private final int _gfxId;
    private final boolean _gfxIdTarget;
    private final List<int[]> _gfxIdOtherLoc;
    private final int _area;
    private final boolean _arrowType;
    private final int _effectId;
    private final int _effectTime;
    private final int _attr;
    private final int _hpAbsorb;
    private final int _mpAbsorb;
    
    static {
        _random = new Random();
    }
    
    public L1WeaponMagSkill(int item_id, String skill_name, int success_random, int max_use_time, String success_msg, String failure_msg, int probability, boolean isLongRange, int fixDamage, int randomDamage, double doubleDmgValue, int gfxId, boolean gfxIdTarget, List<int[]> gfxIdOtherLoc, int area, boolean arrowType, int effectId, int effectTime, int attr, int hpAbsorb, int mpAbsorb) {
        this._item_id = item_id;
        this._skill_name = skill_name;
        this._success_random = success_random;
        this._max_use_time = max_use_time;
        this._success_msg = success_msg;
        this._failure_msg = failure_msg;
        this._probability = probability;
        this._isLongRange = isLongRange;
        this._fixDamage = fixDamage;
        this._randomDamage = randomDamage;
        this._doubleDmgValue = doubleDmgValue;
        this._gfxId = gfxId;
        this._gfxIdTarget = gfxIdTarget;
        this._gfxIdOtherLoc = gfxIdOtherLoc;
        this._area = area;
        this._arrowType = arrowType;
        this._effectId = effectId;
        this._effectTime = effectTime;
        this._attr = attr;
        this._hpAbsorb = hpAbsorb;
        this._mpAbsorb = mpAbsorb;
    }
    
    public final int getItemId() {
        return this._item_id;
    }
    
    public final int geteffectTime() {
        return this._effectTime;
    }
    
    public final String getSkillName() {
        return this._skill_name;
    }
    
    public final int getSuccessRandom() {
        return this._success_random;
    }
    
    public final int getMaxUseTime() {
        return this._max_use_time;
    }
    
    public final String getSuccessMsg() {
        return this._success_msg;
    }
    
    public final String getFailureMsg() {
        return this._failure_msg;
    }
    
    public static final double getWeaponSkillDamage(L1PcInstance pc, L1Character cha, double damage, L1WeaponMagSkill magicWeapon, boolean isLongRange) {
        if (pc == null || cha == null || magicWeapon == null) {
            return 0.0;
        }
        else {
            int chance;
            if (isLongRange && magicWeapon._isLongRange) {
                chance = L1WeaponMagSkill._random.nextInt(200);
            }
            else {
                chance = L1WeaponMagSkill._random.nextInt(100);
            }
            if (magicWeapon._probability < chance) {
                return 0.0;
            }
            else {
                int gfxId = magicWeapon._gfxId;
                if (gfxId != 0) {
                    int locX;
                    int locY;
                    int targetId;
                    if (magicWeapon._gfxIdTarget) {
                        locX = cha.getX();
                        locY = cha.getY();
                        targetId = cha.getId();
                    }
                    else {
                        locX = pc.getX();
                        locY = pc.getY();
                        targetId = pc.getId();
                    }
                    sendGfxids(pc, magicWeapon, locX, locY, targetId, gfxId);
                    if (magicWeapon._gfxIdOtherLoc != null && !magicWeapon._gfxIdOtherLoc.isEmpty()) {
                        for (int[] location : magicWeapon._gfxIdOtherLoc) {
                            sendGfxids(pc, magicWeapon, locX + location[0], locY + location[1], targetId, gfxId);
                        }
                    }
                }
                int effectTime = magicWeapon._effectTime;
                if (effectTime > 0) {
                    effectTime *= 1000;
                }
                int effectId = magicWeapon._effectId;
                if (effectId > 0) {
                    L1Character target;
                    if (magicWeapon._gfxIdTarget) {
                        target = cha;
                    }
                    else {
                        target = pc;
                    }
                    L1SkillUse l1skilluse = new L1SkillUse();
                    l1skilluse.handleCommands(pc, magicWeapon._effectId, target.getId(), target.getX(), target.getY(), magicWeapon._effectTime, 4);
                }
                double fixDamage = (double)magicWeapon._fixDamage;
                int randomDamage = magicWeapon._randomDamage;
                if (randomDamage > 0) {
                    fixDamage += (double)L1WeaponMagSkill._random.nextInt(randomDamage);
                }
                double doubleDmgValue = magicWeapon._doubleDmgValue;
                if (doubleDmgValue > 0.0) {
                    fixDamage += damage * doubleDmgValue;
                }
                if (magicWeapon._hpAbsorb > 0) {
                    pc.setCurrentHp(pc.getCurrentHp() + magicWeapon._hpAbsorb);
                }
                if (magicWeapon._mpAbsorb > 0 && cha.getCurrentMp() > 0) {
                    cha.setCurrentMp(Math.max(cha.getCurrentMp() - magicWeapon._mpAbsorb, 0));
                    pc.setCurrentMp(pc.getCurrentMp() + magicWeapon._mpAbsorb);
                }
                int area = magicWeapon._area;
                if (area != 0) {
                    for (L1Object object : World.get().getVisibleObjects(cha, area)) {
                        if (object == null) {
                            continue;
                        }
                        else if (!(object instanceof L1Character)) {
                            continue;
                        }
                        else if (object.getId() == pc.getId()) {
                            continue;
                        }
                        else if (object.getId() == cha.getId()) {
                            continue;
                        }
                        else if (cha instanceof L1MonsterInstance && !(object instanceof L1MonsterInstance)) {
                            continue;
                        }
                        else if ((cha instanceof L1PcInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) && !(object instanceof L1PcInstance) && !(object instanceof L1SummonInstance) && !(object instanceof L1PetInstance) && !(object instanceof L1MonsterInstance)) {
                            continue;
                        }
                        else {
                            double fixDamageArea = L1WeaponSkill.calcDamageReduction(pc, (L1Character)object, fixDamage * 0.5, magicWeapon._attr);
                            if (fixDamageArea <= 0.0) {
                                continue;
                            }
                            else if (object instanceof L1PcInstance) {
                                L1PcInstance targetPc = (L1PcInstance)object;
                                targetPc.sendPacketsX8(new S_DoActionGFX(targetPc.getId(), 2));
                                targetPc.receiveDamage(pc, (double)(int)fixDamageArea, false, false);
                            }
                            else if (object instanceof L1SummonInstance || object instanceof L1PetInstance || object instanceof L1MonsterInstance) {
                                L1NpcInstance targetNpc = (L1NpcInstance)object;
                                targetNpc.broadcastPacketX8(new S_DoActionGFX(targetNpc.getId(), 2));
                                targetNpc.receiveDamage(pc, (int)fixDamageArea);
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
                return L1WeaponSkill.calcDamageReduction(pc, cha, fixDamage, magicWeapon._attr);
            }
        }
    }
    
    private static final void sendGfxids(L1PcInstance pc, L1WeaponMagSkill magicWeapon, int locX, int locY, int targetId, int gfxId) {
        ArrayList<L1PcInstance> pc_list = World.get().getVisiblePlayer(pc, 10);
        if (magicWeapon._arrowType) {
            S_UseAttackSkill packet = new S_UseAttackSkill(pc, targetId, gfxId, locX, locY, 1, false);
            pc.sendPackets(packet);
            for (L1PcInstance otherPc : pc_list) {
                otherPc.sendPackets(packet);
            }
        }
        else {
            S_EffectLocation packet2 = new S_EffectLocation(locX, locY, gfxId);
            pc.sendPackets(packet2);
            for (L1PcInstance otherPc : pc_list) {
                otherPc.sendPackets(packet2);
            }
        }
    }
}
