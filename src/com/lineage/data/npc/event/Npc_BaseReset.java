package com.lineage.data.npc.event;

import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.clientpackets.C_CreateChar;
import com.lineage.config.ConfigAlt;
import com.lineage.server.utils.CalcStat;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.utils.CalcInitHpMp;
import com.lineage.data.event.BaseResetSet;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_BaseReset extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_BaseReset.class);
    }
    
    private Npc_BaseReset() {
    }
    
    public static NpcExecutor get() {
        return new Npc_BaseReset();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        if (pc.getMeteLevel() > 0) {
            pc.sendPackets(new S_ServerMessage("回憶蠟燭使用轉身限制0轉"));
            return;
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "yuleX1_1", null));
            return;
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        String[] info = null;
        if (cmd.equalsIgnoreCase("c")) {
            L1ItemInstance item = pc.getInventory().findItemId(49142);
            if (item != null) {
                pc.get_otherList().clear_uplevelList();
                info = showInfo(pc, 2);
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ySrc_E", info));
            }
        }
        else if (cmd.equalsIgnoreCase("s1")) {
            info = showInfoX(pc, 1);
        }
        else if (cmd.equalsIgnoreCase("s2")) {
            info = showInfoX(pc, 2);
        }
        else if (cmd.equalsIgnoreCase("d1")) {
            info = showInfoX(pc, 3);
        }
        else if (cmd.equalsIgnoreCase("d2")) {
            info = showInfoX(pc, 4);
        }
        else if (cmd.equalsIgnoreCase("c1")) {
            info = showInfoX(pc, 5);
        }
        else if (cmd.equalsIgnoreCase("c2")) {
            info = showInfoX(pc, 6);
        }
        else if (cmd.equalsIgnoreCase("w1")) {
            info = showInfoX(pc, 7);
        }
        else if (cmd.equalsIgnoreCase("w2")) {
            info = showInfoX(pc, 8);
        }
        else if (cmd.equalsIgnoreCase("i1")) {
            info = showInfoX(pc, 9);
        }
        else if (cmd.equalsIgnoreCase("i2")) {
            info = showInfoX(pc, 10);
        }
        else if (cmd.equalsIgnoreCase("h1")) {
            info = showInfoX(pc, 11);
        }
        else if (cmd.equalsIgnoreCase("h2")) {
            info = showInfoX(pc, 12);
        }
        else if (cmd.equalsIgnoreCase("x")) {
            L1ItemInstance item = pc.getInventory().findItemId(49142);
            if (item != null) {
                int elixirStats = pc.get_otherList().get_uplevelList(0);
                if (elixirStats > 0) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "yuleX4", new String[] { String.valueOf(elixirStats) }));
                    return;
                }
                else {
                    stopSkill(pc);
                    info = showInfo(pc, 0);
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ySrc_E", info));
            }
        }
        else if (cmd.equalsIgnoreCase("b")) {
            L1ItemInstance item = pc.getInventory().findItemId(49142);
            if (item != null) {
                int elixirStats = pc.get_otherList().get_uplevelList(0);
                if (elixirStats > 0) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "yuleX4", new String[] { String.valueOf(elixirStats) }));
                    return;
                }
                else {
                    stopSkill(pc);
                    pc.getInventory().removeItem(item, 1L);
                    int hp = 0;
                    int mp = 0;
                    if (BaseResetSet.RETAIN != 0) {
                        hp = pc.getMaxHp() * BaseResetSet.RETAIN / 100;
                        mp = pc.getMaxMp() * BaseResetSet.RETAIN / 100;
                    }
                    else {
                        hp = CalcInitHpMp.calcInitHp(pc);
                        mp = CalcInitHpMp.calcInitMp(pc);
                    }
                    int baseStr = pc.getBaseStr();
                    int baseDex = pc.getBaseDex();
                    int baseCon = pc.getBaseCon();
                    int baseWis = pc.getBaseWis();
                    int baseInt = pc.getBaseInt();
                    int baseCha = pc.getBaseCha();
                    pc.addBaseStr((int)(byte)(-baseStr));
                    pc.addBaseDex((int)(byte)(-baseDex));
                    pc.addBaseCon((int)(byte)(-baseCon));
                    pc.addBaseWis((int)(byte)(-baseWis));
                    pc.addBaseInt((int)(byte)(-baseInt));
                    pc.addBaseCha((int)(byte)(-baseCha));
                    int originalStr = pc.get_otherList().get_uplevelList(1);
                    int originalDex = pc.get_otherList().get_uplevelList(2);
                    int originalCon = pc.get_otherList().get_uplevelList(3);
                    int originalWis = pc.get_otherList().get_uplevelList(4);
                    int originalInt = pc.get_otherList().get_uplevelList(5);
                    int originalCha = pc.get_otherList().get_uplevelList(6);
                    int addStr = pc.get_otherList().get_uplevelList(7);
                    int addDex = pc.get_otherList().get_uplevelList(8);
                    int addCon = pc.get_otherList().get_uplevelList(9);
                    int addWis = pc.get_otherList().get_uplevelList(10);
                    int addInt = pc.get_otherList().get_uplevelList(11);
                    int addCha = pc.get_otherList().get_uplevelList(12);
                    pc.addBaseStr((int)(byte)(addStr + originalStr - 1));
                    pc.addBaseDex((int)(byte)(addDex + originalDex - 1));
                    pc.addBaseCon((int)(byte)(addCon + originalCon - 1));
                    pc.addBaseWis((int)(byte)(addWis + originalWis - 1));
                    pc.addBaseInt((int)(byte)(addInt + originalInt - 1));
                    pc.addBaseCha((int)(byte)(addCha + originalCha - 1));
                    pc.setOriginalStr(pc.get_otherList().get_newPcOriginal()[0]);
                    pc.setOriginalDex(pc.get_otherList().get_newPcOriginal()[1]);
                    pc.setOriginalCon(pc.get_otherList().get_newPcOriginal()[2]);
                    pc.setOriginalWis(pc.get_otherList().get_newPcOriginal()[3]);
                    pc.setOriginalInt(pc.get_otherList().get_newPcOriginal()[4]);
                    pc.setOriginalCha(pc.get_otherList().get_newPcOriginal()[5]);
                    pc.addBaseMaxHp((short)(hp - pc.getBaseMaxHp()));
                    pc.addBaseMaxMp((short)(mp - pc.getBaseMaxMp()));
                    pc.refresh();
                    setHpMp(pc);
                    pc.setCurrentHp(pc.getMaxHp());
                    pc.setCurrentMp(pc.getMaxMp());
                    try {
                        pc.sendPackets(new S_OwnCharStatus2(pc));
                        pc.sendPackets(new S_OwnCharAttrDef(pc));
                        pc.sendPackets(new S_OwnCharStatus(pc));
                        pc.sendPackets(new S_SPMR(pc));
                        pc.save();
                    }
                    catch (Exception e) {
                        Npc_BaseReset._log.error(e.getLocalizedMessage(), e);
                    }
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ySrc_E", info));
            }
            pc.sendPackets(new S_CloseList(pc.getId()));
            pc.get_otherList().clear_uplevelList();
        }
        if (info != null) {
            int xmode = pc.get_otherList().get_uplevelList(13);
            switch (xmode) {
                case 0: {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "yuleX2", info));
                    break;
                }
                case 1: {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "yuleX2_1", info));
                    break;
                }
                case 2: {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ySrc_1", info));
                    break;
                }
            }
        }
    }
    
    public static void setHpMp(L1PcInstance pc) {
        for (int i = 0; i < pc.getLevel(); ++i) {
            short randomHp = CalcStat.calcStatHp(pc.getType(), (int)pc.getBaseMaxHp(), pc.getBaseCon(), pc.getOriginalHpup());
            short randomMp = CalcStat.calcStatMp(pc.getType(), (int)pc.getBaseMaxMp(), pc.getBaseWis(), pc.getOriginalMpup());
            pc.addBaseMaxHp(randomHp);
            pc.addBaseMaxMp(randomMp);
        }
    }
    
    public static String[] showInfoX(L1PcInstance pc, int mode) {
        int xmode = pc.get_otherList().get_uplevelList(13);
        int[] max = new int[6];
        switch (xmode) {
            case 0: {
                max = new int[] { ConfigAlt.POWER, ConfigAlt.POWER, ConfigAlt.POWER, ConfigAlt.POWER, ConfigAlt.POWER, ConfigAlt.POWER };
                break;
            }
            case 1: {
                max = new int[] { ConfigAlt.POWERMEDICINE, ConfigAlt.POWERMEDICINE, ConfigAlt.POWERMEDICINE, ConfigAlt.POWERMEDICINE, ConfigAlt.POWERMEDICINE, ConfigAlt.POWERMEDICINE };
                break;
            }
            case 2: {
                int type = pc.getType();
                switch (type) {
                    case 0: {
                        max = new int[] { 20, 18, 18, 18, 18, 18 };
                        break;
                    }
                    case 1: {
                        max = new int[] { 20, 16, 18, 13, 12, 16 };
                        break;
                    }
                    case 2: {
                        max = new int[] { 18, 18, 18, 18, 18, 16 };
                        break;
                    }
                    case 3: {
                        max = new int[] { 20, 18, 18, 18, 18, 18 };
                        break;
                    }
                    case 4: {
                        max = new int[] { 18, 18, 18, 18, 18, 18 };
                        break;
                    }
                    case 5: {
                        max = new int[] { 19, 16, 18, 17, 17, 14 };
                        break;
                    }
                    case 6: {
                        max = new int[] { 19, 16, 18, 18, 18, 18 };
                        break;
                    }
                }
            }
        }
        int elixirStats = pc.get_otherList().get_uplevelList(0);
        int originalStr = pc.get_otherList().get_uplevelList(1);
        int originalDex = pc.get_otherList().get_uplevelList(2);
        int originalCon = pc.get_otherList().get_uplevelList(3);
        int originalWis = pc.get_otherList().get_uplevelList(4);
        int originalInt = pc.get_otherList().get_uplevelList(5);
        int originalCha = pc.get_otherList().get_uplevelList(6);
        int addStr = pc.get_otherList().get_uplevelList(7);
        int addDex = pc.get_otherList().get_uplevelList(8);
        int addCon = pc.get_otherList().get_uplevelList(9);
        int addWis = pc.get_otherList().get_uplevelList(10);
        int addInt = pc.get_otherList().get_uplevelList(11);
        int addCha = pc.get_otherList().get_uplevelList(12);
        switch (mode) {
            case 1: {
                if (--elixirStats < 0) {
                    return null;
                }
                else if (++addStr > max[0] - originalStr || addStr < 0) {
                    return null;
                }
                else {
                    pc.get_otherList().add_levelList(7, addStr);
                    break;
                }
            }
            case 2: {
                if (++elixirStats < 0) {
                    return null;
                }
                else if (--addStr > max[0] - originalStr || addStr < 0) {
                    return null;
                }
                else {
                    pc.get_otherList().add_levelList(7, addStr);
                    break;
                }
            }
            case 3: {
                if (--elixirStats < 0) {
                    return null;
                }
                else if (++addDex > max[1] - originalDex || addDex < 0) {
                    return null;
                }
                else {
                    pc.get_otherList().add_levelList(8, addDex);
                    break;
                }
            }
            case 4: {
                if (++elixirStats < 0) {
                    return null;
                }
                else if (--addDex > max[1] - originalDex || addDex < 0) {
                    return null;
                }
                else {
                    pc.get_otherList().add_levelList(8, addDex);
                    break;
                }
            }
            case 5: {
                if (--elixirStats < 0) {
                    return null;
                }
                else if (++addCon > max[2] - originalCon || addCon < 0) {
                    return null;
                }
                else {
                    pc.get_otherList().add_levelList(9, addCon);
                    break;
                }
            }
            case 6: {
                if (++elixirStats < 0) {
                    return null;
                }
                else if (--addCon > max[2] - originalCon || addCon < 0) {
                    return null;
                }
                else {
                    pc.get_otherList().add_levelList(9, addCon);
                    break;
                }
            }
            case 7: {
                if (--elixirStats < 0) {
                    return null;
                }
                else if (++addWis > max[3] - originalWis || addWis < 0) {
                    return null;
                }
                else {
                    pc.get_otherList().add_levelList(10, addWis);
                    break;
                }
            }
            case 8: {
                if (++elixirStats < 0) {
                    return null;
                }
                else if (--addWis > max[3] - originalWis || addWis < 0) {
                    return null;
                }
                else {
                    pc.get_otherList().add_levelList(10, addWis);
                    break;
                }
            }
            case 9: {
                if (--elixirStats < 0) {
                    return null;
                }
                else if (++addInt > max[4] - originalInt || addInt < 0) {
                    return null;
                }
                else {
                    pc.get_otherList().add_levelList(11, addInt);
                    break;
                }
            }
            case 10: {
                if (++elixirStats < 0) {
                    return null;
                }
                else if (--addInt > max[4] - originalInt || addInt < 0) {
                    return null;
                }
                else {
                    pc.get_otherList().add_levelList(11, addInt);
                    break;
                }
            }
            case 11: {
                if (--elixirStats < 0) {
                    return null;
                }
                else if (++addCha > max[5] - originalCha || addCha < 0) {
                    return null;
                }
                else {
                    pc.get_otherList().add_levelList(12, addCha);
                    break;
                }
            }
            case 12: {
                if (++elixirStats < 0) {
                    return null;
                }
                else if (--addCha > max[5] - originalCha || addCha < 0) {
                    return null;
                }
                else {
                    pc.get_otherList().add_levelList(12, addCha);
                    break;
                }
            }
        }
        pc.get_otherList().add_levelList(0, elixirStats);
        return info(pc);
    }
    
    public static String[] showInfo(L1PcInstance pc, int mode) {
        int elixirStats = 0;
        int originalStr = 0;
        int originalDex = 0;
        int originalCon = 0;
        int originalWis = 0;
        int originalInt = 0;
        int originalCha = 0;
        int type = pc.getType();
        originalStr = C_CreateChar.ORIGINAL_STR[type];
        originalDex = C_CreateChar.ORIGINAL_DEX[type];
        originalCon = C_CreateChar.ORIGINAL_CON[type];
        originalWis = C_CreateChar.ORIGINAL_WIS[type];
        originalInt = C_CreateChar.ORIGINAL_INT[type];
        originalCha = C_CreateChar.ORIGINAL_CHA[type];
        switch (mode) {
            case 0: {
                if (pc.getBonusStats() > 0) {
                    elixirStats += pc.getBonusStats();
                }
                pc.get_otherList().add_levelList(13, 0);
                break;
            }
            case 1: {
                if (pc.getElixirStats() > 0) {
                    elixirStats += pc.getElixirStats();
                }
                originalStr = pc.get_otherList().get_uplevelList(1);
                originalDex = pc.get_otherList().get_uplevelList(2);
                originalCon = pc.get_otherList().get_uplevelList(3);
                originalWis = pc.get_otherList().get_uplevelList(4);
                originalInt = pc.get_otherList().get_uplevelList(5);
                originalCha = pc.get_otherList().get_uplevelList(6);
                pc.get_otherList().add_levelList(13, 1);
                break;
            }
            case 2: {
                elixirStats = C_CreateChar.ORIGINAL_AMOUNT[type];
                pc.get_otherList().add_levelList(13, 2);
                break;
            }
        }
        pc.get_otherList().add_levelList(0, elixirStats);
        switch (mode) {
            case 0: {
                int addStrS = pc.get_otherList().get_uplevelList(7);
                int addDexS = pc.get_otherList().get_uplevelList(8);
                int addConS = pc.get_otherList().get_uplevelList(9);
                int addWisS = pc.get_otherList().get_uplevelList(10);
                int addIntS = pc.get_otherList().get_uplevelList(11);
                int addChaS = pc.get_otherList().get_uplevelList(12);
                pc.get_otherList().add_levelList(1, originalStr + addStrS);
                pc.get_otherList().add_levelList(2, originalDex + addDexS);
                pc.get_otherList().add_levelList(3, originalCon + addConS);
                pc.get_otherList().add_levelList(4, originalWis + addWisS);
                pc.get_otherList().add_levelList(5, originalInt + addIntS);
                pc.get_otherList().add_levelList(6, originalCha + addChaS);
                pc.get_otherList().set_newPcOriginal(new int[] { originalStr + addStrS, originalDex + addDexS, originalCon + addConS, originalWis + addWisS, originalInt + addIntS, originalCha + addChaS });
                break;
            }
            case 1: {
                int addStrR = pc.get_otherList().get_uplevelList(7);
                int addDexR = pc.get_otherList().get_uplevelList(8);
                int addConR = pc.get_otherList().get_uplevelList(9);
                int addWisR = pc.get_otherList().get_uplevelList(10);
                int addIntR = pc.get_otherList().get_uplevelList(11);
                int addChaR = pc.get_otherList().get_uplevelList(12);
                pc.get_otherList().add_levelList(1, originalStr + addStrR);
                pc.get_otherList().add_levelList(2, originalDex + addDexR);
                pc.get_otherList().add_levelList(3, originalCon + addConR);
                pc.get_otherList().add_levelList(4, originalWis + addWisR);
                pc.get_otherList().add_levelList(5, originalInt + addIntR);
                pc.get_otherList().add_levelList(6, originalCha + addChaR);
                break;
            }
            case 2: {
                pc.get_otherList().add_levelList(1, originalStr);
                pc.get_otherList().add_levelList(2, originalDex);
                pc.get_otherList().add_levelList(3, originalCon);
                pc.get_otherList().add_levelList(4, originalWis);
                pc.get_otherList().add_levelList(5, originalInt);
                pc.get_otherList().add_levelList(6, originalCha);
                break;
            }
        }
        int addStr = 0;
        pc.get_otherList().add_levelList(7, addStr);
        int addDex = 0;
        pc.get_otherList().add_levelList(8, addDex);
        int addCon = 0;
        pc.get_otherList().add_levelList(9, addCon);
        int addWis = 0;
        pc.get_otherList().add_levelList(10, addWis);
        int addInt = 0;
        pc.get_otherList().add_levelList(11, addInt);
        int addCha = 0;
        pc.get_otherList().add_levelList(12, addCha);
        return info(pc);
    }
    
    private static String[] info(L1PcInstance pc) {
        String[] info = null;
        int p1 = pc.get_otherList().get_uplevelList(0);
        int p2 = pc.get_otherList().get_uplevelList(1);
        int p3 = pc.get_otherList().get_uplevelList(7);
        int p4 = pc.get_otherList().get_uplevelList(2);
        int p5 = pc.get_otherList().get_uplevelList(8);
        int p6 = pc.get_otherList().get_uplevelList(3);
        int p7 = pc.get_otherList().get_uplevelList(9);
        int p8 = pc.get_otherList().get_uplevelList(4);
        int p9 = pc.get_otherList().get_uplevelList(10);
        int p10 = pc.get_otherList().get_uplevelList(5);
        int p11 = pc.get_otherList().get_uplevelList(11);
        int p12 = pc.get_otherList().get_uplevelList(6);
        int p13 = pc.get_otherList().get_uplevelList(12);
        int xmode = pc.get_otherList().get_uplevelList(13);
        if (xmode == 2) {
            int type = pc.getType();
            int elixirStats = C_CreateChar.ORIGINAL_AMOUNT[type];
            String nameid = "";
            switch (type) {
                case 0: {
                    nameid = "$1127";
                    break;
                }
                case 1: {
                    nameid = "$1128";
                    break;
                }
                case 2: {
                    nameid = "$1129";
                    break;
                }
                case 3: {
                    nameid = "$1130";
                    break;
                }
                case 4: {
                    nameid = "$2503";
                    break;
                }
            }
            info = new String[] { nameid, String.valueOf(elixirStats), String.valueOf(p1), String.valueOf((p2 < 10) ? ("0" + p2) : (p2)), String.valueOf((p3 < 10) ? ("0" + p3) : (p3)), String.valueOf((p4 < 10) ? ("0" + p4) : (p4)), String.valueOf((p5 < 10) ? ("0" + p5) : (p5)), String.valueOf((p6 < 10) ? ("0" + p6) : (p6)), String.valueOf((p7 < 10) ? ("0" + p7) : (p7)), String.valueOf((p8 < 10) ? ("0" + p8) : (p8)), String.valueOf((p9 < 10) ? ("0" + p9) : (p9)), String.valueOf((p10 < 10) ? ("0" + p10) : (p10)), String.valueOf((p11 < 10) ? ("0" + p11) : (p11)), String.valueOf((p12 < 10) ? ("0" + p12) : (p12)), String.valueOf((p13 < 10) ? ("0" + p13) : (p13)) };
        }
        else {
            info = new String[] { String.valueOf(p1), String.valueOf((p2 < 10) ? ("0" + p2) : (p2)), String.valueOf((p3 < 10) ? ("0" + p3) : (p3)), String.valueOf((p4 < 10) ? ("0" + p4) : (p4)), String.valueOf((p5 < 10) ? ("0" + p5) : (p5)), String.valueOf((p6 < 10) ? ("0" + p6) : (p6)), String.valueOf((p7 < 10) ? ("0" + p7) : (p7)), String.valueOf((p8 < 10) ? ("0" + p8) : (p8)), String.valueOf((p9 < 10) ? ("0" + p9) : (p9)), String.valueOf((p10 < 10) ? ("0" + p10) : (p10)), String.valueOf((p11 < 10) ? ("0" + p11) : (p11)), String.valueOf((p12 < 10) ? ("0" + p12) : (p12)), String.valueOf((p13 < 10) ? ("0" + p13) : (p13)) };
        }
        return info;
    }
    
    public static void stopSkill(L1PcInstance pc) {
        pc.getInventory().takeoffEquip(945);
        for (int skillNum = 1; skillNum <= 220; ++skillNum) {
            pc.removeSkillEffect(skillNum);
        }
        pc.curePoison();
        pc.cureParalaysis();
        for (int skillNum = 998; skillNum <= 1027; ++skillNum) {
            pc.removeSkillEffect(skillNum);
        }
        for (int skillNum = 3000; skillNum <= 3051; ++skillNum) {
            pc.removeSkillEffect(skillNum);
        }
        pc.sendPackets(new S_CharVisualUpdate(pc));
    }
}
