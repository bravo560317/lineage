package com.lineage.server.model;

import java.util.Iterator;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.templates.L1AttrWeapon;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_CurseBlind;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.ExtraAttrWeaponTable;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import org.apache.commons.logging.Log;

public class L1AttackPower
{
    private static final Log _log;
    private static final Random _random;
    private final L1PcInstance _pc;
    private final L1Character _target;
    private L1PcInstance _targetPc;
    private L1NpcInstance _targetNpc;
    private int _weaponAttrEnchantKind;
    private int _weaponAttrEnchantLevel;
    
    static {
        _log = LogFactory.getLog(L1AttackPower.class);
        _random = new Random();
    }
    
    public L1AttackPower(L1PcInstance attacker, L1Character target, int weaponAttrEnchantKind, int weaponAttrEnchantLevel) {
        this._pc = attacker;
        this._target = target;
        if (this._target instanceof L1NpcInstance) {
            this._targetNpc = (L1NpcInstance)this._target;
        }
        else if (this._target instanceof L1PcInstance) {
            this._targetPc = (L1PcInstance)this._target;
        }
        this._weaponAttrEnchantKind = weaponAttrEnchantKind;
        this._weaponAttrEnchantLevel = weaponAttrEnchantLevel;
    }
    
    public void AttackPower1(int weaponAttrEnchantKind, int weaponAttrEnchantLevel) {
        this._weaponAttrEnchantKind = weaponAttrEnchantKind;
        this._weaponAttrEnchantLevel = weaponAttrEnchantLevel;
    }
    
    public int set_item_power(int damage) {
        int reset_dmg = damage;
        try {
            if (this._weaponAttrEnchantKind > 0) {
                L1AttrWeapon attrWeapon = ExtraAttrWeaponTable.getInstance().get(this._weaponAttrEnchantKind, this._weaponAttrEnchantLevel);
                if (attrWeapon == null) {
                    return damage;
                }
                else {
                    int skillfor3 = this._pc.getlogpcpower_SkillFor3() * 10;
                    if (attrWeapon.getProbability() + skillfor3 >= L1AttackPower._random.nextInt(100) + 1) {
                        if (this._targetPc != null && this._targetPc.getInventory().consumeItem(44073, 1L)) {
                            this._targetPc.sendPackets(new S_SystemMessage("成功抵抗屬性能力的發動"));
                            return damage;
                        }
                        else {
                            if (attrWeapon.getTypeBind() > 0.0 && !L1WeaponSkill.isFreeze(this._target)) {
                                int time = (int)(attrWeapon.getTypeBind() * 1000.0);
                                this._target.broadcastPacketAll(new S_SkillSound(this._target.getId(), attrWeapon.getgfixd()));
                                if (this._targetPc != null) {
                                    this._targetPc.setSkillEffect(14009, time);
                                    this._targetPc.sendPackets(new S_SkillSound(this._target.getId(), attrWeapon.getgfixd()));
                                    this._targetPc.sendPackets(new S_Paralysis(6, true));
                                }
                                else if (this._targetNpc != null && !this._targetNpc.getNpcTemplate().is_boss()) {
                                    this._targetNpc.setSkillEffect(4000, time);
                                    this._targetNpc.setPassispeed(0);
                                }
                            }
                            if (attrWeapon.getTypeDrainHp() > 0.0) {
                                int drainHp = 1 + L1AttackPower._random.nextInt((int)attrWeapon.getTypeDrainHp());
                                ServerBasePacket packet = new S_SkillSound(this._target.getId(), attrWeapon.getgfixd());
                                this._target.broadcastPacketX8(packet);
                                this._pc.setCurrentHp((int)(short)(this._pc.getCurrentHp() + drainHp));
                                if (this._targetPc != null) {
                                    this._targetPc.setCurrentHp(Math.max(this._targetPc.getCurrentHp() - drainHp, 0));
                                }
                            }
                            if (attrWeapon.getTypeDrainMp() > 0) {
                                int drainMp = 1 + L1AttackPower._random.nextInt(attrWeapon.getTypeDrainMp());
                                ServerBasePacket packet = new S_SkillSound(this._target.getId(), attrWeapon.getgfixd());
                                this._target.broadcastPacketX8(packet);
                                if (this._targetPc != null) {
                                    if (this._targetPc.getCurrentMp() > drainMp) {
                                        this._pc.setCurrentMp((int)(short)(this._pc.getCurrentMp() + drainMp));
                                        this._targetPc.setCurrentMp((int)(short)(this._targetPc.getCurrentMp() - drainMp));
                                    }
                                }
                                else if (this._targetNpc != null && this._targetNpc.getCurrentMp() > drainMp) {
                                    this._pc.setCurrentMp((int)(short)(this._pc.getCurrentMp() + drainMp));
                                    this._targetNpc.setCurrentMp((int)(short)(this._targetNpc.getCurrentMp() - drainMp));
                                }
                            }
                            if (attrWeapon.getTypeDmgup() > 0.0) {
                                ServerBasePacket packet2 = new S_SkillSound(this._target.getId(), attrWeapon.getgfixd());
                                this._target.broadcastPacketX8(packet2);
                                reset_dmg = (int)((double)reset_dmg * attrWeapon.getTypeDmgup());
                            }
                            if (attrWeapon.getTypeRange() > 0 && attrWeapon.getTypeRangeDmg() > 0) {
                                ServerBasePacket packet2 = new S_SkillSound(this._target.getId(), attrWeapon.getgfixd());
                                this._target.broadcastPacketX8(packet2);
                                int dmg = attrWeapon.getTypeRangeDmg() + L1AttackPower._random.nextInt(30);
                                if (this._targetPc != null) {
                                    for (L1Object tgobj : World.get().getVisibleObjects(this._targetPc, attrWeapon.getTypeRange())) {
                                        if (tgobj instanceof L1PcInstance) {
                                            L1PcInstance tgpc = (L1PcInstance)tgobj;
                                            if (!tgpc.isDead() && (tgpc.getClanid() != this._pc.getClanid() || tgpc.getClanid() == 0) && !tgpc.getMap().isSafetyZone(tgpc.getLocation())) {
                                                tgpc.receiveDamage(this._pc, (double)dmg, false, false);
                                                //this._pc.sendPackets(new S_ServerMessage("該0101"));
                                            }
                                            else {
                                                continue;
                                            }
                                        }
                                    }
                                }
                                else if (this._targetNpc != null) {
                                    for (L1Object tgobj : World.get().getVisibleObjects(this._targetNpc, attrWeapon.getTypeRange())) {
                                        if (tgobj instanceof L1MonsterInstance) {
                                            L1MonsterInstance tgmob = (L1MonsterInstance)tgobj;
                                            if (!tgmob.isDead()) {
                                                tgmob.receiveDamage(this._pc, dmg);
                                                //this._pc.sendPackets(new S_ServerMessage("該0202"));
                                            }
                                            else {
                                                continue;
                                            }
                                        }
                                    }
                                }
                            }
                            if (attrWeapon.getfix_damage() > 0) {
                                reset_dmg += attrWeapon.getfix_damage();
                                ServerBasePacket packet2 = new S_SkillSound(this._target.getId(), attrWeapon.getgfixd());
                                this._target.broadcastPacketX8(packet2);
                            }
                            if (attrWeapon.getrandom_damage() > 0) {
                                reset_dmg += L1AttackPower._random.nextInt(attrWeapon.getrandom_damage());
                            }
                            if (attrWeapon.getc_hp() > 0 && this._target.getCurrentHp() > attrWeapon.getc_hp()) {
                                ServerBasePacket packet2 = new S_SkillSound(this._target.getId(), attrWeapon.getgfixd());
                                this._target.broadcastPacketX8(packet2);
                                this._pc.setCurrentHp((int)(short)(this._pc.getCurrentHp() + attrWeapon.getc_hp()));
                                this._target.setCurrentHp((int)(short)(this._target.getCurrentHp() - attrWeapon.getc_hp()));
                            }
                            if (attrWeapon.getc_mp() > 0 && this._target.getCurrentMp() > attrWeapon.getc_mp()) {
                                ServerBasePacket packet2 = new S_SkillSound(this._target.getId(), attrWeapon.getgfixd());
                                this._target.broadcastPacketX8(packet2);
                                this._pc.setCurrentMp((int)(short)(this._pc.getCurrentMp() + attrWeapon.getc_mp()));
                                this._target.setCurrentMp((int)(short)(this._target.getCurrentMp() - attrWeapon.getc_mp()));
                            }
                            if (attrWeapon.getTypeLightDmg() > 0) {
                                int dmg2 = attrWeapon.getTypeLightDmg();
                                this._target.broadcastPacketAll(new S_SkillSound(this._target.getId(), attrWeapon.getgfixd()));
                                if (this._targetPc != null) {
                                    this._targetPc.sendPackets(new S_SkillSound(this._target.getId(), attrWeapon.getgfixd()));
                                    this._targetPc.receiveDamage(this._pc, (double)dmg2, false, false);
                                }
                                else if (this._targetNpc != null) {
                                    this._targetNpc.receiveDamage(this._pc, dmg2);
                                }
                            }
                            if (attrWeapon.getTypeSkill1() && attrWeapon.getTypeSkillTime() > 0.0) {
                                int timeSec = (int)attrWeapon.getTypeSkillTime();
                                if (this._targetPc != null && !this._target.hasSkillEffect(40)) {
                                    this._targetPc.sendPacketsAll(new S_SkillSound(this._targetPc.getId(), attrWeapon.getgfixd(), timeSec));
                                    this._targetPc.setSkillEffect(40, timeSec * 1000);
                                    this._targetPc.sendPackets(new S_CurseBlind(1));
                                }
                            }
                            if (attrWeapon.getTypeSkill2() && attrWeapon.getTypeSkillTime() > 0.0) {
                                int timeSec = (int)attrWeapon.getTypeSkillTime();
                                if (this._targetPc != null && !this._target.hasSkillEffect(64)) {
                                    this._targetPc.sendPacketsAll(new S_SkillSound(this._targetPc.getId(), attrWeapon.getgfixd(), timeSec));
                                    this._target.setSkillEffect(64, timeSec * 1000);
                                }
                            }
                            if (attrWeapon.getTypeSkill3() && attrWeapon.getTypeSkillTime() > 0.0 && attrWeapon.getTypePolyList() != null) {
                                int polyId = Integer.parseInt(attrWeapon.getTypePolyList()[L1AttackPower._random.nextInt(attrWeapon.getTypePolyList().length)]);
                                if (this._targetPc != null) {
                                    if (this._targetPc.getInventory().checkEquipped(20281) || this._targetPc.getInventory().checkEquipped(120281)) {
                                        return reset_dmg;
                                    }
                                    else {
                                        this._targetPc.sendPacketsAll(new S_SkillSound(this._target.getId(), attrWeapon.getgfixd()));
                                        L1PolyMorph.doPoly(this._targetPc, polyId, (int)attrWeapon.getTypeSkillTime(), 1);
                                    }
                                }
                                else if (this._targetNpc != null && !this._targetNpc.getNpcTemplate().is_boss()) {
                                    this._target.broadcastPacketAll(new S_SkillSound(this._target.getId(), attrWeapon.getgfixd()));
                                    L1PolyMorph.doPoly(this._target, polyId, (int)attrWeapon.getTypeSkillTime(), 1);
                                }
                            }
                            if (attrWeapon.getTypeSkill4() && attrWeapon.getTypeSkillTime() > 0.0) {
                                int timeSec = (int)attrWeapon.getTypeSkillTime();
                                if (this._targetPc != null && !this._targetPc.hasSkillEffect(87)) {
                                    this._targetPc.setSkillEffect(87, timeSec * 1000);
                                    L1SpawnUtil.spawnEffect(81162, timeSec, this._targetPc.getX(), this._targetPc.getY(), this._targetPc.getMapId(), this._targetPc, 0);
                                    this._targetPc.sendPackets(new S_Paralysis(5, true, timeSec * 1000));
                                }
                            }
                            if (attrWeapon.getTypeRemoveWeapon() && this._targetPc != null && this._targetPc.getWeapon() != null) {
                                this._targetPc.getInventory().setEquipped(this._targetPc.getWeapon(), false, false, false);
                                this._targetPc.sendPackets(new S_ServerMessage(1027));
                            }
                            if (attrWeapon.getTypeRemoveDoll() && this._targetPc != null && !this._targetPc.getDolls().isEmpty()) {
                                Iterator<L1DollInstance> iter = (Iterator)this._targetPc.getDolls().values().iterator();
                                if (iter.hasNext()) {
                                    L1DollInstance doll = iter.next();
                                    doll.deleteDoll();
                                }
                            }
                            if (attrWeapon.getTypeRemoveArmor() > 0 && this._targetPc != null) {
                                int counter = L1AttackPower._random.nextInt(attrWeapon.getTypeRemoveArmor()) + 1;
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
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            L1AttackPower._log.error(e.getLocalizedMessage(), e);
        }
        return reset_dmg;
    }
}
