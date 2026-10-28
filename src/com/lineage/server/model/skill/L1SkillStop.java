package com.lineage.server.model.skill;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.L1Clan;
import com.lineage.server.templates.L1Skills;
import java.util.Iterator;
import com.lineage.server.model.skill.skillmode.SkillMode;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.serverpackets.S_CharTitle;
import com.lineage.server.datatables.ServerQuestMaPTable;
import com.lineage.william.ExcavateTable;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_PacketBoxIcon1;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.world.WorldClan;
import com.lineage.server.model.L1CastleLocation;
import java.sql.Timestamp;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_PinkName;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_PacketBoxCooking;
import com.lineage.server.serverpackets.S_PacketBoxWisdomPotion;
import com.lineage.server.serverpackets.S_SkillIconBlessOfEva;
import com.lineage.server.serverpackets.S_Liquor;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_PacketBoxWaterLife;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_PacketBoxIconAura;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_Poison;
import com.lineage.server.serverpackets.S_Strup;
import com.lineage.server.serverpackets.S_CurseBlind;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_Dexup;
import com.lineage.server.serverpackets.S_SkillIconShield;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.ItemUseEXTable;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import com.lineage.echo.ClientExecutor;
import com.lineage.server.utils.Random;
import org.apache.commons.logging.Log;

public class L1SkillStop
{
    private static final Log _log;
    private static final Random _random;
    private static ClientExecutor _client;
    
    static {
        _log = LogFactory.getLog(L1SkillStop.class);
        _random = new Random();
    }
    
    public static void stopSkill(L1Character cha, int skillId) {
        try {
            SkillMode mode = L1SkillMode.get().getSkill(skillId);
            if (mode != null) {
                mode.stop(cha);
            }
            else if (ItemUseEXTable.get().checkItem(skillId)) {
                if (cha instanceof L1PcInstance) {
                    L1PcInstance pc = (L1PcInstance)cha;
                    ItemUseEXTable.get().remove(pc, skillId);
                }
            }
            else {
                try {
                    if (cha instanceof L1PcInstance) {
                        L1PcInstance pc = (L1PcInstance)cha;
                        if (pc.BuffSkillList() != null) {
                            boolean ok = false;
                            for (Integer list : pc.BuffSkillList()) {
                                if (skillId == list) {
                                    ok = true;
                                    break;
                                }
                            }
                            if (ok) {
                                L1Skills skill = SkillsTable.get().getTemplate(skillId);
                                L1SkillUse skillUse = new L1SkillUse();
                                skillUse.handleCommands(pc, skillId, pc.getId(), pc.getX(), pc.getY(), skill.getBuffDuration(), 4);
                            }
                        }
                    }
                }
                catch (Exception e) {
                    L1SkillStop._log.error(e.getLocalizedMessage(), e);
                }
                L1PcInstance pc;
                switch (skillId) {
                    case 9990:
                        Label_3873: {
                            if (cha instanceof L1PcInstance) {
                                pc = (L1PcInstance)cha;
                                L1Teleport.teleport(pc, 33443, 32797, (short)4, 5, true);
                                break Label_3873;
                            }
                            else {
                                break Label_3873;
                            }
                        }
                    case 2: {
                        if (cha instanceof L1PcInstance && !cha.isInvisble()) {
                            pc = (L1PcInstance)cha;
                            pc.turnOnOffLight();
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3: {
                        cha.addAc(2);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_SkillIconShield(5, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 26: {
                        cha.addDex(-5);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_Dexup(pc, 5, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 29:
                    case 76:
                    case 152: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                        }
                        cha.setMoveSpeed(0);
                        break;
                    }
                    case 40: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_CurseBlind(0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 42: {
                        cha.addStr(-5);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_Strup(pc, 5, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 43:
                    case 54: {
                        cha.setMoveSpeed(0);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 47: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addDmgup(5);
                            pc.addHitup(1);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 50:
                    case 80:
                    case 194: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPacketsAll(new S_Poison(pc.getId(), 0));
                            pc.sendPackets(new S_Paralysis(4, false));
                            break;
                        }
                        else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
                            L1NpcInstance npc = (L1NpcInstance)cha;
                            npc.broadcastPacketAll(new S_Poison(npc.getId(), 0));
                            npc.setParalyzed(false);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 52:
                    case 101:
                    case 150: {
                        cha.setBraveSpeed(0);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.setBraveSpeed(0);
                            pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 55: {
                        cha.addAc(-10);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.startHpRegeneration();
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 56: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addHitup(6);
                            pc.addAc(-12);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 66: {
                        cha.setSleeped(false);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_Paralysis(3, false));
                            pc.sendPackets(new S_OwnCharStatus(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 78: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.startHpRegeneration();
                            pc.startMpRegeneration();
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 97: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.delBlindHiding();
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 109: {
                        cha.addStr(-3);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_Strup(pc, 3, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 110: {
                        cha.addDex(-3);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_Dexup(pc, 3, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 114: { // 114: 激勵士氣 (魔法結束)
                        cha.addHitup(-5);    // 扣除 近戰命中率 5
                        cha.addBowHitup(-5); // 扣除 遠距離命中率 5
                        cha.addMr(-20);      // 扣除 魔法防禦(MR) 20
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_SPMR(pc));
                            pc.sendPackets(new S_PacketBoxIconAura(113, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 115: { // 115: 鋼鐵士氣 (魔法結束)
                        cha.addAc(8);        // 恢復 防禦力 8
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxIconAura(114, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 117: { // 117: 衝擊士氣 (魔法結束)
                        cha.addDmgup(-10);   // 扣除 近戰攻擊力 10
                        cha.addHitup(-5);    // 扣除 近戰命中率 5
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxIconAura(116, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 119: { // 119: 王者加護 (魔法結束)
                        cha.removeSkillEffect(68); // 移除聖結界減傷狀態
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBox(40, 0)); // 關閉聖結界圖示
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 129: {
                        cha.addMr(-10);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_SPMR(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 137: {
                        cha.addWis(-3);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.resetBaseMr();
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 138: {
                        cha.addWind(-10);
                        cha.addWater(-10);
                        cha.addFire(-10);
                        cha.addEarth(-10);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_OwnCharAttrDef(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 147: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            int attr = pc.getElfAttr();
                            if (attr == 1) {
                                cha.addEarth(-50);
                            }
                            else if (attr == 2) {
                                cha.addFire(-50);
                            }
                            else if (attr == 4) {
                                cha.addWater(-50);
                            }
                            else if (attr == 8) {
                                cha.addWind(-50);
                            }
                            pc.sendPackets(new S_OwnCharAttrDef(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 148: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxIconAura(147, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 149: {
                        cha.addBowHitup(-6);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxIconAura(148, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 151: {
                        cha.addAc(6);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_SkillIconShield(6, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 153: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxIconAura(152, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 155: {
                        cha.addDmgup(-4);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxIconAura(154, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 156: {
                        cha.addBowHitup(-2);
                        cha.addBowDmgup(-3);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxIconAura(155, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 157: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPacketsAll(new S_Poison(pc.getId(), 0));
                            pc.sendPackets(new S_Paralysis(4, false));
                            break;
                        }
                        else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
                            L1NpcInstance npc = (L1NpcInstance)cha;
                            npc.broadcastPacketAll(new S_Poison(npc.getId(), 0));
                            npc.setParalyzed(false);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 159: {
                        cha.addAc(7);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_SkillIconShield(7, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 163: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxIconAura(162, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 166: {
                        cha.addBowDmgup(-6);
                        cha.addBowHitup(1);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxIconAura(165, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 168: {
                        cha.addAc(10);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_SkillIconShield(10, 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 170: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxWaterLife());
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 174: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBox(132, pc.getEr()));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 183: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addAc(-10);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 193: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addStr(3);
                            pc.addInt(3);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 204: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addDmgup(-4);
                            pc.addHitup(-4);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 214: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addAc(20);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 998: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPacketsAll(new S_Liquor(pc.getId(), 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 1000: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
                        }
                        cha.setBraveSpeed(0);
                        break;
                    }
                    case 1001: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                        }
                        cha.setMoveSpeed(0);
                        break;
                    }
                    case 1003: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_SkillIconBlessOfEva(pc.getId(), 0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 1004: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addSp(-2);
                            pc.addMpr(-2);
                            pc.sendPackets(new S_PacketBoxWisdomPotion(0));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 1006: {
                        cha.curePoison();
                        break;
                    }
                    case 1016: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
                        }
                        cha.setBraveSpeed(0);
                        break;
                    }
                    case 1017: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.setBraveSpeed(0);
                            pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                            break;
                        }
                        else {
                            cha.setBraveSpeed(0);
                            break;
                        }
                    }
                    case 1018: {
                        cha.addFire(-30);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_OwnCharAttrDef(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 1020: {
                        cha.addEarth(-30);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_OwnCharAttrDef(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 1022: {
                        cha.addWind(-30);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_OwnCharAttrDef(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3000:
                    case 3008: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addWind(-10);
                            pc.addWater(-10);
                            pc.addFire(-10);
                            pc.addEarth(-10);
                            pc.sendPackets(new S_OwnCharAttrDef(pc));
                            pc.sendPackets(new S_PacketBoxCooking(pc, 0, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3001:
                    case 3009: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addMaxHp(-30);
                            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                            if (pc.isInParty()) {
                                pc.getParty().updateMiniHP(pc);
                            }
                            pc.sendPackets(new S_PacketBoxCooking(pc, 1, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3002:
                    case 3010: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxCooking(pc, 2, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3003:
                    case 3011: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addAc(1);
                            pc.sendPackets(new S_PacketBoxCooking(pc, 3, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3004:
                    case 3012: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addMaxMp(-20);
                            pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                            pc.sendPackets(new S_PacketBoxCooking(pc, 4, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3005:
                    case 3013: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxCooking(pc, 5, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3006:
                    case 3014: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addMr(-5);
                            pc.sendPackets(new S_SPMR(pc));
                            pc.sendPackets(new S_PacketBoxCooking(pc, 6, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3007:
                    case 3015: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxCooking(pc, 7, 0));
                            pc.setDessertId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3016:
                    case 3024: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxCooking(pc, 16, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3017:
                    case 3025: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addMaxHp(-30);
                            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                            if (pc.isInParty()) {
                                pc.getParty().updateMiniHP(pc);
                            }
                            pc.addMaxMp(-30);
                            pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                            pc.sendPackets(new S_PacketBoxCooking(pc, 17, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3018:
                    case 3026: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addAc(2);
                            pc.sendPackets(new S_PacketBoxCooking(pc, 18, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3019:
                    case 3027: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxCooking(pc, 19, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3020:
                    case 3028: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxCooking(pc, 20, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3021:
                    case 3029: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addMr(-10);
                            pc.sendPackets(new S_SPMR(pc));
                            pc.sendPackets(new S_PacketBoxCooking(pc, 21, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3022:
                    case 3030: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addSp(-1);
                            pc.sendPackets(new S_SPMR(pc));
                            pc.sendPackets(new S_PacketBoxCooking(pc, 22, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3023:
                    case 3031: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxCooking(pc, 32, 0));
                            pc.setDessertId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3032:
                    case 3040: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxCooking(pc, 37, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3033:
                    case 3041: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addMaxHp(-50);
                            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                            if (pc.isInParty()) {
                                pc.getParty().updateMiniHP(pc);
                            }
                            pc.addMaxMp(-50);
                            pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                            pc.sendPackets(new S_PacketBoxCooking(pc, 38, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3034:
                    case 3042: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxCooking(pc, 39, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3035:
                    case 3043: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addAc(3);
                            pc.sendPackets(new S_PacketBoxCooking(pc, 40, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3036:
                    case 3044: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addMr(-15);
                            pc.sendPackets(new S_SPMR(pc));
                            pc.addWind(-10);
                            pc.addWater(-10);
                            pc.addFire(-10);
                            pc.addEarth(-10);
                            pc.sendPackets(new S_OwnCharAttrDef(pc));
                            pc.sendPackets(new S_PacketBoxCooking(pc, 41, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3037:
                    case 3045: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addSp(-2);
                            pc.sendPackets(new S_SPMR(pc));
                            pc.sendPackets(new S_PacketBoxCooking(pc, 42, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3038:
                    case 3046: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addMaxHp(-30);
                            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                            if (pc.isInParty()) {
                                pc.getParty().updateMiniHP(pc);
                            }
                            pc.sendPackets(new S_PacketBoxCooking(pc, 43, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3039:
                    case 3047: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxCooking(pc, 44, 0));
                            pc.setDessertId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3048: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addMr(-10);
                            pc.addEarth(-10);
                            pc.addWater(-10);
                            pc.addFire(-10);
                            pc.addWind(-10);
                            pc.addHpr(-2);
                            pc.addMpr(-2);
                            pc.sendPackets(new S_SPMR(pc));
                            pc.sendPackets(new S_OwnCharAttrDef(pc));
                            pc.sendPackets(new S_PacketBoxCooking(pc, 157, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3049: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addMr(-10);
                            pc.addEarth(-10);
                            pc.addWater(-10);
                            pc.addFire(-10);
                            pc.addWind(-10);
                            pc.addHpr(-2);
                            pc.addMpr(-2);
                            pc.sendPackets(new S_SPMR(pc));
                            pc.sendPackets(new S_OwnCharAttrDef(pc));
                            pc.sendPackets(new S_PacketBoxCooking(pc, 158, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3050: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addSp(-2);
                            pc.addMr(-10);
                            pc.addEarth(-10);
                            pc.addWater(-10);
                            pc.addFire(-10);
                            pc.addWind(-10);
                            pc.addHpr(-2);
                            pc.addMpr(-3);
                            pc.sendPackets(new S_SPMR(pc));
                            pc.sendPackets(new S_OwnCharAttrDef(pc));
                            pc.sendPackets(new S_PacketBoxCooking(pc, 159, 0));
                            pc.setCookingId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3051: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_PacketBoxCooking(pc, 160, 0));
                            pc.setDessertId(0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 4002: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_ServerMessage(288));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 4017: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_Paralysis(6, false));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 5000:
                    case 5001:
                    case 5002:
                    case 5003:
                    case 5004:
                    case 5005:
                    case 5006:
                    case 5007:
                    case 5008:
                    case 5009:
                    case 5010:
                    case 5011:
                    case 5012:
                    case 5013:
                    case 5014: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_ServerMessage("第二段神力藥水效果消失。"));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 5122: {
                        if (cha instanceof L1PcInstance) {
                            L1PcInstance tgpc = (L1PcInstance)cha;
                            tgpc.sendPackets(new S_PinkName(tgpc.getId(), 0));
                            tgpc.setPinkName(false);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 5198: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.setEsotericSkill(0);
                            pc.setEsotericCount(0);
                            pc.sendPackets(new S_SystemMessage("魔擊累積的效果已消失。"));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 5923: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            long adenaCount = pc.getInventory().countItems(44070);
                            if (adenaCount > 0L) {
                                long difference = adenaCount - pc.getShopAdenaRecord();
                                if (difference >= 100L) {
                                    元寶差異紀錄("元寶差異紀錄 IP(" + pc.getNetConnection().getIp() + ")玩家:【" + pc.getName() + "】的在線元寶數量增加:【" + difference + "】個, 時間:(" + new Timestamp(System.currentTimeMillis()) + ")。");
                                }
                                else if (difference <= -100L) {
                                    元寶差異紀錄("元寶差異紀錄 IP(" + pc.getNetConnection().getIp() + ")玩家:【" + pc.getName() + "】的在線元寶數量減少:【" + difference + "】個, 時間:(" + new Timestamp(System.currentTimeMillis()) + ")。");
                                }
                                pc.setShopAdenaRecord(adenaCount);
                            }
                            pc.setSkillEffect(5923, 15000);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 6666:
                    case 6667:
                    case 6668:
                    case 6669:
                    case 6670:
                    case 6671:
                    case 6672:
                    case 6673:
                    case 6674:
                    case 6675:
                    case 6676:
                    case 6677:
                    case 6678:
                    case 6679:
                    case 6680:
                    case 6681: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_ServerMessage("第一段經驗藥水效果消失。"));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 6797: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addAc(2);
                            pc.addWater(-50);
                            pc.sendPackets(new S_OwnCharAttrDef(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 6798: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addHpr(-3);
                            pc.addMpr(-1);
                            pc.addWind(-50);
                            pc.sendPackets(new S_OwnCharAttrDef(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 6799: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addFire(-50);
                            pc.sendPackets(new S_OwnCharAttrDef(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 6930: {
                        if (cha instanceof L1PcInstance) {
                            L1PcInstance tgpc = (L1PcInstance)cha;
                            tgpc.setSkillEffect(6931, 0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 6932: {
                        if (cha instanceof L1PcInstance) {
                            L1PcInstance tgpc = (L1PcInstance)cha;
                            if (!tgpc.isActived() && tgpc.get_followmaster() == null) {
                                tgpc.setSkillEffect(6933, 120000);
                                tgpc.setnewai1(1 + Random.nextInt(8));
                                tgpc.setnewai2(1 + Random.nextInt(8));
                                tgpc.setnewai3(1 + Random.nextInt(8));
                                newai(tgpc);
                                break;
                            }
                            else {
                                tgpc.setSkillEffect(6930, 600000);
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                    case 6933: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            if (L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())) {
                                return;
                            }
                            else {
                                pc.saveInventory();
                                KickPc(pc);
                                L1SkillStop._log.info(String.format("玩家 : %s 因逾時未通過外掛偵測，已強制切斷其連線", pc.getName()));
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                    case 7002: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_ServerMessage("陣營積分2倍卷軸效果消失。"));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 7003: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_ServerMessage("陣營積分3倍卷軸效果消失 "));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 7944: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                            if (clan != null && clan.getWarehouseUsingChar() == pc.getId()) {
                                clan.setWarehouseUsingChar(0);
                                pc.sendPackets(new S_ServerMessage("盟倉使用權已被中斷。"));
                                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_who"));
                                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_who"));
                                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_who"));
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
                    case 8030: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addMaxHp(-20);
                            pc.addMaxMp(-20);
                            pc.addDmgup(-3);
                            pc.addBowDmgup(-3);
                            pc.addSp(-3);
                            pc.addDamageReductionByArmor(-2);
                            pc.addHpr(-2);
                            pc.addMpr(-2);
                            pc.sendPackets(new S_HPUpdate(pc));
                            if (pc.isInParty()) {
                                pc.getParty().updateMiniHP(pc);
                            }
                            pc.sendPackets(new S_MPUpdate(pc));
                            pc.sendPackets(new S_SPMR(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 8040: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addMaxHp(-100);
                            pc.addMaxMp(-100);
                            pc.addDmgup(-6);
                            pc.addBowDmgup(-6);
                            pc.addSp(-4);
                            pc.addDamageReductionByArmor(-4);
                            pc.sendPackets(new S_HPUpdate(pc));
                            if (pc.isInParty()) {
                                pc.getParty().updateMiniHP(pc);
                            }
                            pc.sendPackets(new S_MPUpdate(pc));
                            pc.sendPackets(new S_SPMR(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 8041: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addMaxHp(-100);
                            pc.addMaxMp(-100);
                            pc.addHpr(-5);
                            pc.addMpr(-5);
                            pc.addStr(-1);
                            pc.addDex(-1);
                            pc.addCon(-1);
                            pc.addInt(-1);
                            pc.addWis(-1);
                            pc.addCha(-1);
                            pc.addOriginalEr(-5);
                            pc.add_dodge(-1);
                            pc.addDmgup(-5);
                            pc.addBowDmgup(-5);
                            pc.addSp(-3);
                            pc.addAc(-5);
                            pc.addDamageReductionByArmor(-5);
                            pc.sendPackets(new S_OwnCharStatus2(pc));
                            pc.sendPackets(new S_PacketBox(132, pc.getEr()));
                            pc.sendPackets(new S_PacketBoxIcon1(true, pc.get_dodge()));
                            pc.sendPackets(new S_HPUpdate(pc));
                            if (pc.isInParty()) {
                                pc.getParty().updateMiniHP(pc);
                            }
                            pc.sendPackets(new S_MPUpdate(pc));
                            pc.sendPackets(new S_SPMR(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 8060: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addHitup(-3);
                            pc.addDmgup(-3);
                            pc.addBowHitup(-3);
                            pc.addBowDmgup(-3);
                            pc.addSp(-3);
                            pc.sendPackets(new S_SPMR(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 8065: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addStr(-1);
                            pc.addDex(-1);
                            pc.addInt(-1);
                            pc.addMr(-10);
                            pc.addRegistStun(-2);
                            pc.addRegistSustain(-2);
                            pc.sendPackets(new S_SPMR(pc));
                            pc.sendPackets(new S_OwnCharStatus2(pc));
                            pc.sendPackets(new S_PacketBox(180, 0, 479));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 8591: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_ServerMessage("媽祖的祝福效果消失。"));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 8853: {
                        if (cha instanceof L1PcInstance) {
                            L1PcInstance tgpc = (L1PcInstance)cha;
                            if (tgpc.isActived() && tgpc.getAu_AutoSet(0) == 0) {
                                L1Teleport.randomTeleportai(tgpc);
                                tgpc.setSkillEffect(8853, 60000);
                                if (tgpc.getAutoX() > 0) {
                                    tgpc.setAutoX(0);
                                    tgpc.setAutoY(0);
                                    tgpc.setAutoMap(0);
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
                        else {
                            break;
                        }
                    }
                    case 8865: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addMaxHp(-500);
                            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                            pc.sendPackets(new S_ServerMessage("特殊技能-體能倍化恢復了"));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 8866: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addSp(-2);
                            pc.sendPackets(new S_SPMR(pc));
                            pc.sendPackets(new S_ServerMessage("魔法增壓似乎消失了!!"));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 8868: {
                        cha.addDex(-4);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_ServerMessage("特殊技能-能力祝福消失了"));
                            pc.sendPackets(new S_OwnCharStatus2(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 8869: {
                        cha.addStr(-4);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_ServerMessage("特殊技能-能力祝福消失了"));
                            pc.sendPackets(new S_OwnCharStatus2(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 8870: {
                        cha.addInt(-4);
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_ServerMessage("特殊技能-能力祝福消失了"));
                            pc.sendPackets(new S_OwnCharStatus2(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 8877: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_Paralysis(5, false));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 8906: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addHitup(-30);
                            pc.addDmgup(-30);
                            pc.addBowHitup(-30);
                            pc.addBowDmgup(-30);
                            pc.addSp(-30);
                            pc.sendPackets(new S_SPMR(pc));
                            pc.sendPackets(new S_SystemMessage("下層戰鬥強化卷軸效果消失！"));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 8907: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addAc(50);
                            pc.sendPackets(new S_SystemMessage("下層防禦強化卷軸效果消失！"));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 10010: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            ExcavateTable.forExcavate(pc);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 14009: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.sendPackets(new S_Paralysis(6, false));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 79501: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            if (!pc.isParalyzed_guaji()) {
                                L1Teleport.randomTeleportai(pc);
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
                    case 80552: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            if (pc.get_other3().get_type1() == 0) {
                                ServerQuestMaPTable.checkcount(pc);
                                break;
                            }
                            else if (pc.get_other3().get_type1() == -1) {
                                ServerQuestMaPTable.checkcount(pc);
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
                    case 85501: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.addMaxHp(-100);
                            pc.addMaxMp(-100);
                            pc.addDmgup(-5);
                            pc.addHitup(-10);
                            pc.addBowDmgup(-5);
                            pc.addBowHitup(-10);
                            pc.addSp(-5);
                            pc.addAc(10);
                            pc.addMr(-10);
                            pc.sendPackets(new S_SPMR(pc));
                            pc.sendPackets(new S_OwnCharAttrDef(pc));
                            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                            pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                            pc.sendPackets(new S_OwnCharStatus2(pc));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 85578: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.set_outChat(null);
                            pc.sendPackets(new S_ServerMessage("\\fY解除控制假人"));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 98766: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            pc.set_savetitle(pc.getTitle());
                            pc.setrantitle(Random.nextInt(9999));
                            pc.sendPackets(new S_CharTitle(pc.getId(), "驗證碼:" + pc.getrantitle()));
                            pc.sendPackets(new S_SystemMessage("\\fT請在60秒內在[對話框]輸入封號上的驗證碼"));
                            pc.sendPackets(new S_SystemMessage("\\fT請在60秒內在[對話框]輸入封號上的驗證碼"));
                            pc.sendPackets(new S_SystemMessage("\\fT請在60秒內在[對話框]輸入封號上的驗證碼"));
                            pc.sendPackets(new S_SystemMessage("\\fT請在60秒內在[對話框]輸入封號上的驗證碼"));
                            pc.sendPackets(new S_SystemMessage("\\fT請在60秒內在[對話框]輸入封號上的驗證碼"));
                            pc.sendPackets(new S_SystemMessage("\\fT請在60秒內在[對話框]輸入封號上的驗證碼"));
                            pc.sendPackets(new S_SystemMessage("\\fT請在60秒內在[對話框]輸入封號上的驗證碼"));
                            pc.sendPackets(new S_SystemMessage("\\fU[請注意輸入法是否是數字]"));
                            pc.sendPackets(new S_PacketBoxGree(2, "\\fT請在60秒內在[對話框]輸入稱號上的驗證碼"));
                            pc.setSkillEffect(98767, 60000);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 98767: {
                        if (cha instanceof L1PcInstance) {
                            pc = (L1PcInstance)cha;
                            if (L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())) {
                                return;
                            }
                            else {
                                pc.saveInventory();
                                KickPc(pc);
                                L1SkillStop._log.info(String.format("玩家 : %s 因逾時未通過外掛偵測，已強制切斷其連線", pc.getName()));
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception e2) {
            L1SkillStop._log.error(e2.getLocalizedMessage(), e2);
        }
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc2 = (L1PcInstance)cha;
            sendStopMessage(pc2, skillId);
            pc2.sendPackets(new S_OwnCharStatus(pc2));
        }
    }
    
    private static void sendStopMessage(L1PcInstance charaPc, int skillid) {
        L1Skills l1skills = SkillsTable.get().getTemplate(skillid);
        if (l1skills == null || charaPc == null) {
            return;
        }
        else {
            int msgID = l1skills.getSysmsgIdStop();
            if (msgID > 0) {
                charaPc.sendPackets(new S_ServerMessage(msgID));
            }
            return;
        }
    }
    
    private static void KickPc(L1PcInstance pc) {
        L1SkillStop._client = pc.getNetConnection();
        try {
            Thread.sleep(5000L);
            L1SkillStop._client.kick();
        }
        catch (InterruptedException e) {
            L1SkillStop._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void ai(L1PcInstance pc) {
        try {
            StringBuilder title = new StringBuilder();
            int newaicount = 0;
            switch (Random.nextInt(86) + 1) {
                case 1: {
                    pc.setnewaititle("●");
                    title.append("\\f2幾個(●):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 2: {
                    pc.setnewaititle("● ●");
                    title.append("\\f2幾個(●):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 3: {
                    pc.setnewaititle("● ● ●");
                    title.append("\\f2幾個(●):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 4: {
                    pc.setnewaititle("● ● ● ●");
                    title.append("\\f2幾個(●):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 5: {
                    pc.setnewaititle("▲");
                    title.append("\\f2幾個(▲):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 6: {
                    pc.setnewaititle("▲▲");
                    title.append("\\f2幾個(▲):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 7: {
                    pc.setnewaititle("▲ ▲ ▲");
                    title.append("\\f2幾個(▲):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 8: {
                    pc.setnewaititle("▲▲▲▲");
                    title.append("\\f2幾個(▲):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 9: {
                    pc.setnewaititle("▲▲▲▲▲");
                    title.append("\\f2幾個(▲):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 5;
                    break;
                }
                case 10: {
                    pc.setnewaititle("９");
                    title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 11: {
                    pc.setnewaititle("９９");
                    title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 12: {
                    pc.setnewaititle("９９９");
                    title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 13: {
                    pc.setnewaititle("９９９９");
                    title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 14: {
                    pc.setnewaititle("９９９９９");
                    title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 5;
                    break;
                }
                case 15: {
                    pc.setnewaititle("８８８８８８");
                    title.append("\\f2幾個(8):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 6;
                    break;
                }
                case 16: {
                    pc.setnewaititle("８８８８８");
                    title.append("\\f2幾個(8):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 5;
                    break;
                }
                case 17: {
                    pc.setnewaititle("８８８８");
                    title.append("\\f2幾個(8):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 18: {
                    pc.setnewaititle("８８８");
                    title.append("\\f2幾個(8):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 19: {
                    pc.setnewaititle("８８");
                    title.append("\\f2幾個(8):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 20: {
                    pc.setnewaititle("８");
                    title.append("\\f2幾個(8):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 21: {
                    pc.setnewaititle("♂");
                    title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 22: {
                    pc.setnewaititle("♂ ♂");
                    title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 23: {
                    pc.setnewaititle("♂ ♂ ♂");
                    title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 24: {
                    pc.setnewaititle("♂ ♂ ♂ ♂");
                    title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 25: {
                    pc.setnewaititle("♂♂♂♂♂");
                    title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 5;
                    break;
                }
                case 26: {
                    pc.setnewaititle("X");
                    title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 27: {
                    pc.setnewaititle("X X");
                    title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 28: {
                    pc.setnewaititle("X X X");
                    title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 29: {
                    pc.setnewaititle("X X X X");
                    title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 30: {
                    pc.setnewaititle("XXXXX");
                    title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 5;
                    break;
                }
                case 31: {
                    pc.setnewaititle("■");
                    title.append("\\f2幾個方形:[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 32: {
                    pc.setnewaititle("■ ■");
                    title.append("\\f2幾個方形:[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 33: {
                    pc.setnewaititle("■ ■ ■");
                    title.append("\\f2幾個方形:[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 34: {
                    pc.setnewaititle("■ ■ ■ ■");
                    title.append("\\f2幾個方形:[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 35: {
                    pc.setnewaititle("卍");
                    title.append("\\f2幾個(卍):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 36: {
                    pc.setnewaititle("卍卍");
                    title.append("\\f2幾個(卍):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 37: {
                    pc.setnewaititle("卍卍卍");
                    title.append("\\f2幾個(卍):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 38: {
                    pc.setnewaititle("卍卍卍卍");
                    title.append("\\f2幾個(卍):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 39: {
                    pc.setnewaititle("卍卍卍卍卍");
                    title.append("\\f2幾個(卍):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 5;
                    break;
                }
                case 40: {
                    pc.setnewaititle("力");
                    title.append("\\f2幾個(力):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 41: {
                    pc.setnewaititle("力力");
                    title.append("\\f2幾個(力):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 42: {
                    pc.setnewaititle("力力力");
                    title.append("\\f2幾個(力):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 43: {
                    pc.setnewaititle("力力力力");
                    title.append("\\f2幾個(力):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 44: {
                    pc.setnewaititle("力力力力力");
                    title.append("\\f2幾個(力):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 5;
                    break;
                }
                case 45: {
                    pc.setnewaititle("力力力力力力");
                    title.append("\\f2幾個(力):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 6;
                    break;
                }
                case 46: {
                    pc.setnewaititle("９");
                    title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 47: {
                    pc.setnewaititle("９９");
                    title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 48: {
                    pc.setnewaititle("９９９");
                    title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 49: {
                    pc.setnewaititle("９９９９");
                    title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 50: {
                    pc.setnewaititle("９９９９９");
                    title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 5;
                    break;
                }
                case 51: {
                    pc.setnewaititle("♂♂♂");
                    title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 52: {
                    pc.setnewaititle("♂");
                    title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 53: {
                    pc.setnewaititle("♂♂");
                    title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 54: {
                    pc.setnewaititle("♂♂♂♂");
                    title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 55: {
                    pc.setnewaititle("♂♂♂♂♂");
                    title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 5;
                    break;
                }
                case 56: {
                    pc.setnewaititle("♂♂♂♂♂♂");
                    title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 6;
                    break;
                }
                case 57: {
                    pc.setnewaititle("XX");
                    title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 58: {
                    pc.setnewaititle("X");
                    title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 59: {
                    pc.setnewaititle("XXX");
                    title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 60: {
                    pc.setnewaititle("XXXX");
                    title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 61: {
                    pc.setnewaititle("XXXXX");
                    title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 5;
                    break;
                }
                case 62: {
                    pc.setnewaititle("XXXXXX");
                    title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 6;
                    break;
                }
                case 63: {
                    pc.setnewaititle("5");
                    title.append("\\f2幾個(5):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 64: {
                    pc.setnewaititle("55");
                    title.append("\\f2幾個(5):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 65: {
                    pc.setnewaititle("555");
                    title.append("\\f2幾個(5):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 66: {
                    pc.setnewaititle("5555");
                    title.append("\\f2幾個(5):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 67: {
                    pc.setnewaititle("55555");
                    title.append("\\f2幾個(5):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 5;
                    break;
                }
                case 68: {
                    pc.setnewaititle("555555");
                    title.append("\\f2幾個(5):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 6;
                    break;
                }
                case 69: {
                    pc.setnewaititle("Q");
                    title.append("\\f2幾個(Q):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 70: {
                    pc.setnewaititle("QQ");
                    title.append("\\f2幾個(Q):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 71: {
                    pc.setnewaititle("QQQ");
                    title.append("\\f2幾個(Q):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 72: {
                    pc.setnewaititle("QQQQ");
                    title.append("\\f2幾個(Q):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 73: {
                    pc.setnewaititle("QQQQQ");
                    title.append("\\f2幾個(Q):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 5;
                    break;
                }
                case 74: {
                    pc.setnewaititle("QQQQQQ");
                    title.append("\\f2幾個(Q):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 6;
                    break;
                }
                case 75: {
                    pc.setnewaititle("A");
                    title.append("\\f2幾個(A):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 76: {
                    pc.setnewaititle("AA");
                    title.append("\\f2幾個(A):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 77: {
                    pc.setnewaititle("AAA");
                    title.append("\\f2幾個(A):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 78: {
                    pc.setnewaititle("AAAA");
                    title.append("\\f2幾個(A):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 79: {
                    pc.setnewaititle("AAAAA");
                    title.append("\\f2幾個(A):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 5;
                    break;
                }
                case 80: {
                    pc.setnewaititle("AAAAAA");
                    title.append("\\f2幾個(A):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 6;
                    break;
                }
                case 81: {
                    pc.setnewaititle("@");
                    title.append("\\f2幾個(@):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 1;
                    break;
                }
                case 82: {
                    pc.setnewaititle("@@");
                    title.append("\\f2幾個(@):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 2;
                    break;
                }
                case 83: {
                    pc.setnewaititle("@@@");
                    title.append("\\f2幾個(@):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 3;
                    break;
                }
                case 84: {
                    pc.setnewaititle("@@@@");
                    title.append("\\f2幾個(@):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 4;
                    break;
                }
                case 85: {
                    pc.setnewaititle("@@@@@");
                    title.append("\\f2幾個(@):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 5;
                    break;
                }
                case 86: {
                    pc.setnewaititle("@@@@@@");
                    title.append("\\f2幾個(@):[ " + pc.getnewaititle() + " ]");
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    newaicount = 6;
                    break;
                }
            }
            pc.sendPackets(new S_SkillSound(pc.getId(), 227));
            pc.setnewaicount(newaicount);
        }
        catch (Exception e) {
            L1SkillStop._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void 元寶差異紀錄(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[元寶差異紀錄].txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void newai(L1PcInstance pc) {
        String[] info = new String[34];
        info[1] = String.valueOf(pc.getnewai1());
        info[2] = String.valueOf(pc.getnewai2());
        info[3] = String.valueOf(pc.getnewai3());
        info[4] = String.valueOf(pc.getnewai4());
        info[5] = String.valueOf(pc.getnewai5());
        info[6] = String.valueOf(pc.getnewai6());
        switch (Random.nextInt(10) + 1) {
            case 1: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai", info));
                break;
            }
            case 2: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai1", info));
                break;
            }
            case 3: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai2", info));
                break;
            }
            case 4: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai3", info));
                break;
            }
            case 5: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai4", info));
                break;
            }
            case 6: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai5", info));
                break;
            }
            case 7: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai6", info));
                break;
            }
            case 8: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai7", info));
                break;
            }
            case 9: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai8", info));
                break;
            }
            case 10: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai9", info));
                break;
            }
        }
    }
}
