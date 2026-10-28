package com.lineage.server.model;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.william.ArrowGfxid;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_ChangeShape;
import com.lineage.william.L1WilliamPolyActid;
import com.lineage.william.L1WilliamGfxIdOrginal;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.timecontroller.event.ranking.RankingHeroTimer;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.datatables.PolyTable;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class L1PolyMorph
{
    private static final Log _log;
    private static final int DAGGER_EQUIP = 1;
    private static final int SWORD_EQUIP = 2;
    private static final int TWOHANDSWORD_EQUIP = 4;
    private static final int AXE_EQUIP = 8;
    private static final int SPEAR_EQUIP = 16;
    private static final int STAFF_EQUIP = 32;
    private static final int EDORYU_EQUIP = 64;
    private static final int CLAW_EQUIP = 128;
    private static final int BOW_EQUIP = 256;
    private static final int KIRINGKU_EQUIP = 512;
    private static final int CHAINSWORD_EQUIP = 1024;
    private static final int HELM_EQUIP = 1;
    private static final int AMULET_EQUIP = 2;
    private static final int EARRING_EQUIP = 4;
    private static final int TSHIRT_EQUIP = 8;
    private static final int ARMOR_EQUIP = 16;
    private static final int CLOAK_EQUIP = 32;
    private static final int BELT_EQUIP = 64;
    private static final int SHIELD_EQUIP = 128;
    private static final int GLOVE_EQUIP = 256;
    private static final int RING_EQUIP = 512;
    private static final int BOOTS_EQUIP = 1024;
    private static final int GUARDER_EQUIP = 2048;
    public static final int MORPH_BY_ITEMMAGIC = 1;
    public static final int MORPH_BY_GM = 2;
    public static final int MORPH_BY_NPC = 4;
    public static final int MORPH_BY_KEPLISHA = 8;
    public static final int MORPH_BY_LOGIN = 0;
    private static final Map<Integer, Integer> weaponFlgMap;
    private static final Map<Integer, Integer> armorFlgMap;
    private int _id;
    private String _name;
    private int _polyId;
    private int _minLevel;
    private int _weaponEquipFlg;
    private int _armorEquipFlg;
    private boolean _canUseSkill;
    private int _causeFlg;
    private int _arrowgifx;
    
    static {
        _log = LogFactory.getLog(L1PolyMorph.class);
        (weaponFlgMap = new HashMap()).put(1, 2);
        L1PolyMorph.weaponFlgMap.put(2, 1);
        L1PolyMorph.weaponFlgMap.put(3, 4);
        L1PolyMorph.weaponFlgMap.put(4, 256);
        L1PolyMorph.weaponFlgMap.put(5, 16);
        L1PolyMorph.weaponFlgMap.put(6, 8);
        L1PolyMorph.weaponFlgMap.put(7, 32);
        L1PolyMorph.weaponFlgMap.put(8, 256);
        L1PolyMorph.weaponFlgMap.put(9, 256);
        L1PolyMorph.weaponFlgMap.put(10, 256);
        L1PolyMorph.weaponFlgMap.put(11, 128);
        L1PolyMorph.weaponFlgMap.put(12, 64);
        L1PolyMorph.weaponFlgMap.put(13, 256);
        L1PolyMorph.weaponFlgMap.put(14, 16);
        L1PolyMorph.weaponFlgMap.put(15, 8);
        L1PolyMorph.weaponFlgMap.put(16, 32);
        L1PolyMorph.weaponFlgMap.put(17, 512);
        L1PolyMorph.weaponFlgMap.put(18, 1024);
        (armorFlgMap = new HashMap()).put(1, 1);
        L1PolyMorph.armorFlgMap.put(2, 16);
        L1PolyMorph.armorFlgMap.put(3, 8);
        L1PolyMorph.armorFlgMap.put(4, 32);
        L1PolyMorph.armorFlgMap.put(5, 256);
        L1PolyMorph.armorFlgMap.put(6, 1024);
        L1PolyMorph.armorFlgMap.put(7, 128);
        L1PolyMorph.armorFlgMap.put(8, 2);
        L1PolyMorph.armorFlgMap.put(9, 512);
        L1PolyMorph.armorFlgMap.put(10, 64);
        L1PolyMorph.armorFlgMap.put(12, 4);
        L1PolyMorph.armorFlgMap.put(13, 2048);
    }
    
    public L1PolyMorph(int id, String name, int polyId, int minLevel, int weaponEquipFlg, int armorEquipFlg, boolean canUseSkill, int causeFlg, int arrowgifx) {
        this._id = id;
        this._name = name;
        this._polyId = polyId;
        this._minLevel = minLevel;
        this._weaponEquipFlg = weaponEquipFlg;
        this._armorEquipFlg = armorEquipFlg;
        this._canUseSkill = canUseSkill;
        this._causeFlg = causeFlg;
        this._arrowgifx = arrowgifx;
    }
    
    public int getId() {
        return this._id;
    }
    
    public String getName() {
        return this._name;
    }
    
    public int getPolyId() {
        return this._polyId;
    }
    
    public int getMinLevel() {
        return this._minLevel;
    }
    
    public int getWeaponEquipFlg() {
        return this._weaponEquipFlg;
    }
    
    public int getArmorEquipFlg() {
        return this._armorEquipFlg;
    }
    
    public boolean canUseSkill() {
        return this._canUseSkill;
    }
    
    public int getCauseFlg() {
        return this._causeFlg;
    }
    
    public int getArrowGifx() {
        return this._arrowgifx;
    }
    
    public static void handleCommands(L1PcInstance pc, String s) {
        if (pc == null || pc.isDead()) {
            return;
        }
        else {
            L1PolyMorph poly = PolyTable.get().getTemplate(s);
            if (poly != null || s.equals("none")) {
                if (s.equals("none")) {
                    if (pc.getTempCharGfx() != 6034 && pc.getTempCharGfx() != 6035) {
                        undoPoly(pc);
                        pc.sendPackets(new S_CloseList(pc.getId()));
                    }
                }
                else if (pc.getLevel() >= poly.getMinLevel() || pc.isGm()) {
                    if (pc.getTempCharGfx() == 6034 || pc.getTempCharGfx() == 6035) {
                        pc.sendPackets(new S_ServerMessage(181));
                    }
                    else {
                        doPoly(pc, poly.getPolyId(), 7200, 1);
                        pc.sendPackets(new S_CloseList(pc.getId()));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(181));
                }
            }
            return;
        }
    }
    
    public static void doPoly(L1Character cha, int polyId, int timeSecs, int cause) {
        try {
            if (cha == null || cha.isDead()) {
                return;
            }
            else if (cha instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)cha;
                if (pc.getMapId() == 5300 || pc.getMapId() == 9000 || pc.getMapId() == 9100 || pc.getMapId() == 9101 || pc.getMapId() == 9102 || pc.getMapId() == 9202) {
                    pc.sendPackets(new S_ServerMessage(1170));
                    return;
                }
                else if (pc.getTempCharGfx() == 6034 || pc.getTempCharGfx() == 6035) {
                    pc.sendPackets(new S_ServerMessage(181));
                    return;
                }
                else if (!isMatchCause(polyId, cause)) {
                    pc.sendPackets(new S_ServerMessage(181));
                    return;
                }
                else {
                    if (pc.isItemPoly1()) {
                        boolean isPoly = true;
                        if (polyId >= 13715 && polyId <= 13745 && !RankingHeroTimer.get_top10().containsValue(pc.getName()) && !RankingHeroTimer.get_top3C().containsValue(pc.getName()) && !RankingHeroTimer.get_top3K().containsValue(pc.getName()) && !RankingHeroTimer.get_top3E().containsValue(pc.getName()) && !RankingHeroTimer.get_top3W().containsValue(pc.getName()) && !RankingHeroTimer.get_top3D().containsValue(pc.getName()) && !RankingHeroTimer.get_top3G().containsValue(pc.getName()) && !RankingHeroTimer.get_top3I().containsValue(pc.getName()) && !pc.isGm()) {
                            isPoly = false;
                        }
                        if (polyId == 13715 && (pc.get_sex() != 0 || !pc.isCrown())) {
                            isPoly = false;
                        }
                        else if (polyId == 13717 && (pc.get_sex() != 1 || !pc.isCrown())) {
                            isPoly = false;
                        }
                        else if (polyId == 13719 && (pc.get_sex() != 0 || !pc.isKnight())) {
                            isPoly = false;
                        }
                        else if (polyId == 13721 && (pc.get_sex() != 1 || !pc.isKnight())) {
                            isPoly = false;
                        }
                        else if (polyId == 13723 && (pc.get_sex() != 0 || !pc.isElf())) {
                            isPoly = false;
                        }
                        else if (polyId == 13725 && (pc.get_sex() != 1 || !pc.isElf())) {
                            isPoly = false;
                        }
                        else if (polyId == 13727 && (pc.get_sex() != 0 || !pc.isWizard())) {
                            isPoly = false;
                        }
                        else if (polyId == 13729 && (pc.get_sex() != 1 || !pc.isWizard())) {
                            isPoly = false;
                        }
                        else if (polyId == 13731 && (pc.get_sex() != 0 || !pc.isDarkelf())) {
                            isPoly = false;
                        }
                        else if (polyId == 13733 && (pc.get_sex() != 1 || !pc.isDarkelf())) {
                            isPoly = false;
                        }
                        else if (polyId == 13735 && (pc.get_sex() != 0 || !pc.isDragonKnight())) {
                            isPoly = false;
                        }
                        else if (polyId == 13737 && (pc.get_sex() != 1 || !pc.isDragonKnight())) {
                            isPoly = false;
                        }
                        else if (polyId == 13739 && (pc.get_sex() != 0 || !pc.isIllusionist())) {
                            isPoly = false;
                        }
                        else if (polyId == 13741 && (pc.get_sex() != 1 || !pc.isIllusionist())) {
                            isPoly = false;
                        }
                        else if (polyId >= 13216 && polyId <= 13220 && !pc.getInventory().checkItem(44212, 1L)) {
                            isPoly = false;
                        }
                        if (!isPoly && !pc.isGm()) {
                            pc.sendPackets(new S_ServerMessage(181));
                            return;
                        }
                    }
                    pc.removeSkillEffect(67);
                    pc.setSkillEffect(67, timeSecs * 1000);
                    if (polyId >= 13216 && polyId <= 13220 && pc.getInventory().checkItem(44212, 1L) && !pc.hasSkillEffect(8030)) {
                        pc.setSkillEffect(8030, timeSecs * 1000);
                        pc.addMaxHp(20);
                        pc.addMaxMp(20);
                        pc.addDmgup(3);
                        pc.addBowDmgup(3);
                        pc.addSp(3);
                        pc.addDamageReductionByArmor(2);
                        pc.addHpr(2);
                        pc.addMpr(2);
                        pc.sendPackets(new S_HPUpdate(pc));
                        if (pc.isInParty()) {
                            pc.getParty().updateMiniHP(pc);
                        }
                        pc.sendPackets(new S_MPUpdate(pc));
                        pc.sendPackets(new S_SPMR(pc));
                    }
                    if (pc.getTempCharGfx() != polyId) {
                        L1ItemInstance weapon = pc.getWeapon();
                        boolean weaponTakeoff = weapon != null && !isEquipableWeapon(polyId, weapon.getItem().getType());
                        L1WilliamGfxIdOrginal.getReductionGfxIdOrginal(pc, pc.getTempCharGfx());
                        L1WilliamPolyActid.getReductionGfxIdOrginal(pc, pc.getTempCharGfx());
                        pc.setTempCharGfx(polyId);
                        pc.sendPackets(new S_ChangeShape(pc, polyId, weaponTakeoff));
                        if (!pc.isGmInvis() && !pc.isInvisble()) {
                            pc.broadcastPacketAll(new S_ChangeShape(pc, polyId));
                        }
                        pc.getInventory().takeoffEquip(polyId);
                        if (weapon != null) {
                            pc.sendPacketsAll(new S_CharVisualUpdate(pc));
                        }
                    }
                    L1WilliamGfxIdOrginal.getAddGfxIdOrginal(pc, polyId);
                    L1WilliamPolyActid.getAddGfxIdOrginal(pc, polyId);
                    ArrowGfxid.forItemUSe(pc, polyId);
                    pc.sendPackets(new S_PacketBox(35, timeSecs));
                }
            }
            else if (cha instanceof L1MonsterInstance) {
                L1MonsterInstance mob = (L1MonsterInstance)cha;
                mob.removeSkillEffect(67);
                mob.setSkillEffect(67, timeSecs * 1000);
                if (mob.getTempCharGfx() != polyId) {
                    mob.setTempCharGfx(polyId);
                    mob.broadcastPacketAll(new S_ChangeShape(mob, polyId));
                }
            }
            else if (cha instanceof L1DeInstance) {
                L1DeInstance de = (L1DeInstance)cha;
                de.removeSkillEffect(67);
                de.setSkillEffect(67, timeSecs * 1000);
                if (de.getTempCharGfx() != polyId) {
                    de.setTempCharGfx(polyId);
                    de.broadcastPacketAll(new S_ChangeShape(de, polyId));
                }
            }
        }
        catch (Exception e) {
            L1PolyMorph._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void undoPoly(L1Character cha) {
        try {
            if (cha instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)cha;
                pc.setcardpoly(-1);
                L1WilliamGfxIdOrginal.getReductionGfxIdOrginal(pc, pc.getTempCharGfx());
                L1WilliamPolyActid.getReductionGfxIdOrginal(pc, pc.getTempCharGfx());
                ArrowGfxid.forItemUSe(pc, pc.getTempCharGfx());
                int classId = pc.getClassId();
                pc.setTempCharGfx(classId);
                pc.sendPacketsAll(new S_ChangeShape(pc, classId));
                pc.removeSkillEffect(67);
                if (pc.hasSkillEffect(8030)) {
                    pc.removeSkillEffect(8030);
                }
                if (pc.hasSkillEffect(8040)) {
                    pc.removeSkillEffect(8040);
                }
                if (pc.hasSkillEffect(8041)) {
                    pc.removeSkillEffect(8041);
                }
                L1ItemInstance weapon = pc.getWeapon();
                if (weapon != null) {
                    pc.sendPacketsAll(new S_CharVisualUpdate(pc));
                }
            }
            else if (cha instanceof L1MonsterInstance) {
                L1MonsterInstance mob = (L1MonsterInstance)cha;
                mob.setTempCharGfx(0);
                mob.broadcastPacketAll(new S_ChangeShape(mob, mob.getGfxId()));
            }
            else if (cha instanceof L1DeInstance) {
                L1DeInstance de = (L1DeInstance)cha;
                de.setTempCharGfx(0);
                de.broadcastPacketAll(new S_ChangeShape(de, de.getGfxId()));
            }
        }
        catch (Exception e) {
            L1PolyMorph._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void undoPoly_new(L1Character cha) {
        try {
            if (cha instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)cha;
                if (pc.getcardpoly() == 0) {
                    L1WilliamGfxIdOrginal.getReductionGfxIdOrginal(pc, pc.getTempCharGfx());
                }
                L1WilliamPolyActid.getReductionGfxIdOrginal(pc, pc.getTempCharGfx());
                ArrowGfxid.forItemUSe(pc, pc.getTempCharGfx());
                int classId = pc.getClassId();
                if (pc.getcardpoly() == 0) {
                    pc.setTempCharGfx(classId);
                    pc.sendPacketsAll(new S_ChangeShape(pc, classId));
                }
                pc.removeSkillEffect(67);
                if (pc.hasSkillEffect(8030)) {
                    pc.removeSkillEffect(8030);
                }
                if (pc.hasSkillEffect(8040)) {
                    pc.removeSkillEffect(8040);
                }
                if (pc.hasSkillEffect(8041)) {
                    pc.removeSkillEffect(8041);
                }
                L1ItemInstance weapon = pc.getWeapon();
                if (weapon != null) {
                    pc.sendPacketsAll(new S_CharVisualUpdate(pc));
                }
            }
            else if (cha instanceof L1MonsterInstance) {
                L1MonsterInstance mob = (L1MonsterInstance)cha;
                mob.setTempCharGfx(0);
                mob.broadcastPacketAll(new S_ChangeShape(mob, mob.getGfxId()));
            }
            else if (cha instanceof L1DeInstance) {
                L1DeInstance de = (L1DeInstance)cha;
                de.setTempCharGfx(0);
                de.broadcastPacketAll(new S_ChangeShape(de, de.getGfxId()));
            }
        }
        catch (Exception e) {
            L1PolyMorph._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static boolean isEquipableWeapon(int polyId, int weaponType) {
        try {
            L1PolyMorph poly = PolyTable.get().getTemplate(polyId);
            if (poly == null) {
                return true;
            }
            else {
                Integer flg = L1PolyMorph.weaponFlgMap.get((Integer)weaponType);
                return flg == null || (poly.getWeaponEquipFlg() & flg) != 0x0;
            }
        }
        catch (Exception e) {
            L1PolyMorph._log.error(e.getLocalizedMessage(), e);
            return true;
        }
    }
    
    public static boolean isEquipableArmor(int polyId, int armorType) {
        try {
            L1PolyMorph poly = PolyTable.get().getTemplate(polyId);
            if (poly == null) {
                return true;
            }
            else {
                Integer flg = L1PolyMorph.armorFlgMap.get((Integer)armorType);
                return flg == null || (poly.getArmorEquipFlg() & flg) != 0x0;
            }
        }
        catch (Exception e) {
            L1PolyMorph._log.error(e.getLocalizedMessage(), e);
            return true;
        }
    }
    
    public static boolean isMatchCause(int polyId, int cause) {
        try {
            L1PolyMorph poly = PolyTable.get().getTemplate(polyId);
            return poly == null || cause == 0 || (poly.getCauseFlg() & cause) != 0x0;
        }
        catch (Exception e) {
            L1PolyMorph._log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }
    
    public static void doPolyPraivateShop(L1Character cha, int polyIndex) {
        if (cha == null || cha.isDead()) {
            return;
        }
        else {
            if (cha instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)cha;
                int[] PolyList = { 11427, 11427, 10047, 9688, 11322, 10069, 10034, 10032 };
                int shoppolayid = PolyList[polyIndex - 1];
                if (pc.getWeapon() != null) {
                    pc.getInventory().setEquipped(pc.getWeapon(), false, false, false);
                }
                doPoly(pc, shoppolayid, 0, 1);
                pc.sendPacketsAll(new S_CharVisualUpdate(pc.getId(), 70));
            }
            return;
        }
    }
    
    public static void undoPolyPrivateShop(L1Character cha) {
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            int classId = pc.getClassId();
            pc.setTempCharGfx(classId);
            if (!pc.isDead()) {
                pc.sendPacketsAll(new S_ChangeShape(pc, classId));
                pc.sendPacketsAll(new S_CharVisualUpdate(pc, pc.getCurrentWeapon()));
            }
        }
    }
}
