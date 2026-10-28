package com.add;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.config.Configtf;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.logging.Logger;

public class TFUpdate
{
    private static Logger _log;
    private static TFUpdate _instance;
    
    static {
        TFUpdate._log = Logger.getLogger(TFUpdate.class.getName());
    }
    
    public static TFUpdate getInstance() {
        if (TFUpdate._instance == null) {
            TFUpdate._instance = new TFUpdate();
        }
        return TFUpdate._instance;
    }
    
    public boolean PcCommand(L1PcInstance pc, String cmd) {
        if (cmd.equalsIgnoreCase("aaa")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(1)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_Str() >= Configtf.tfup1) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_Str(pc.get_other2().get_Str() + Configtf.tftype1);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(1));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(1));
                    pc.addStr(Configtf.tftype1);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("bbb")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(2)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_Dex() >= Configtf.tfup2) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_Dex(pc.get_other2().get_Dex() + Configtf.tftype2);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(2));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(2));
                    pc.addDex(Configtf.tftype2);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("ccc")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(3)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_Int() >= Configtf.tfup3) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_Int(pc.get_other2().get_Int() + Configtf.tftype3);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(3));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(3));
                    pc.addInt(Configtf.tftype3);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("ddd")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(4)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_Attack() >= Configtf.tfup4) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_Attack(pc.get_other2().get_Attack() + Configtf.tftype4);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(4));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(4));
                    pc.addDmgup(Configtf.tftype4);
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("eee")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(5)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_BowAttack() >= Configtf.tfup5) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_BowAttack(pc.get_other2().get_BowAttack() + Configtf.tftype5);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(5));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(5));
                    pc.addBowDmgup(Configtf.tftype5);
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("fff")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(6)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_Hp() >= Configtf.tfup6) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_Hp(pc.get_other2().get_Hp() + Configtf.tftype6);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(6));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(6));
                    pc.addMaxHp(Configtf.tftype6);
                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("ggg")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(7)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_Mp() >= Configtf.tfup7) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_Mp(pc.get_other2().get_Mp() + Configtf.tftype7);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(7));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(7));
                    pc.addMaxMp(Configtf.tftype7);
                    pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("hhh")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(8)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_ReductionDmg() >= Configtf.tfup8) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_ReductionDmg(pc.get_other2().get_ReductionDmg() + Configtf.tftype8);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(8));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(8));
                    pc.addother_ReductionDmg(Configtf.tftype8);
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("iii")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(9)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_Sp() >= Configtf.tfup9) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_Sp(pc.get_other2().get_Sp() + Configtf.tftype9);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(9));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(9));
                    pc.addSp(Configtf.tftype9);
                    pc.sendPackets(new S_SPMR(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("jjj")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(10)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_Mr() >= Configtf.tfup10) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_Mr(pc.get_other2().get_Mr() + Configtf.tftype10);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(10));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(10));
                    pc.addMr(Configtf.tftype10);
                    pc.sendPackets(new S_SPMR(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("kkk")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(11)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_Con() >= Configtf.tfup11) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_Con(pc.get_other2().get_Con() + Configtf.tftype11);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(11));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(11));
                    pc.addCon(Configtf.tftype11);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("lll")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(12)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_Cha() >= Configtf.tfup12) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_Cha(pc.get_other2().get_Cha() + Configtf.tftype12);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(12));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(12));
                    pc.addCha(Configtf.tftype12);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("mmm")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(13)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_Wis() >= Configtf.tfup13) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_Wis(pc.get_other2().get_Wis() + Configtf.tftype13);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(13));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(13));
                    pc.addWis(Configtf.tftype13);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("nnn")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(14)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_Hpr() >= Configtf.tfup14) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_Hpr(pc.get_other2().get_Hpr() + Configtf.tftype14);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(14));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(14));
                    pc.addHpr(Configtf.tftype14);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("ooo")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(15)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_Mpr() >= Configtf.tfup15) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_Mpr(pc.get_other2().get_Mpr() + Configtf.tftype15);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(15));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(15));
                    pc.addMpr(Configtf.tftype15);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("ppp")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(16)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_hppotion() >= Configtf.tfup16) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_hppotion(pc.get_other2().get_hppotion() + Configtf.tftype16);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(16));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(16));
                    pc.add_hppotion(Configtf.tftype16);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("qqq")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(17)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_exp() >= Configtf.tfup17) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_exp(pc.get_other2().get_exp() + Configtf.tftype17);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(17));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(17));
                    pc.add_exp(Configtf.tftype17);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
else if (cmd.equalsIgnoreCase("rrr")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(18)) {
                isTalent = true;
            }
            if (isTalent) {
                // 注意：因為 AC 是越低越好，這裡的上限判斷或累加方向需配合您的 Configtf 定義
                // 假設 tfup18 是玩家可以點到的最低負數極限（例如 -50），或者如果是存累積點數，請依需求調整
if (Math.abs(pc.get_other2().get_ac()) >= Configtf.tfup18) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    // 修改處：AC 點下去應該是減少數值（例如 0 -> -1 -> -2）
                    pc.get_other2().set_ac(pc.get_other2().get_ac() - Configtf.tftype18);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(18));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(18));
                    
                    // 角色能力加成也改為負數
                    pc.addAc(-Configtf.tftype18);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("sss")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(19)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_weight() >= Configtf.tfup19) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_weight(pc.get_other2().get_weight() + Configtf.tftype19);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(19));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(19));
                    pc.addWeightReduction(Configtf.tftype19);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("ttt")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(20)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_regist_stun() >= Configtf.tfup20) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_regist_stun(pc.get_other2().get_regist_stun() + Configtf.tftype20);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(20));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(20));
                    pc.addRegistStun(Configtf.tftype20);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("uuu")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(21)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_regist_stone() >= Configtf.tfup21) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_regist_stone(pc.get_other2().get_regist_stone() + Configtf.tftype21);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(21));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(21));
                    pc.addRegistStone(Configtf.tftype21);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("vvv")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(22)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_regist_sleep() >= Configtf.tfup22) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_regist_sleep(pc.get_other2().get_regist_sleep() + Configtf.tftype22);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(22));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(22));
                    pc.addRegistSleep(Configtf.tftype22);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("www")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(23)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_regist_freeze() >= Configtf.tfup23) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_regist_freeze(pc.get_other2().get_regist_freeze() + Configtf.tftype23);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(23));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(23));
                    pc.add_regist_freeze(Configtf.tftype23);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("xxx")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(24)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_regist_sustain() >= Configtf.tfup24) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_regist_sustain(pc.get_other2().get_regist_sustain() + Configtf.tftype24);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(24));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(24));
                    pc.addRegistSustain(Configtf.tftype24);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("yyy")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(25)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_regist_blind() >= Configtf.tfup25) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_regist_blind(pc.get_other2().get_regist_blind() + Configtf.tftype25);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(25));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(25));
                    pc.addRegistBlind(Configtf.tftype25);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("zzz")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(26)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_pvp() >= Configtf.tfup26) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_pvp(pc.get_other2().get_pvp() + Configtf.tftype26);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(26));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(26));
                    pc.add_pvp(Configtf.tftype26);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else if (cmd.equalsIgnoreCase("aaaa")) {
            boolean isTalent = false;
            if (pc.get_other2().get_TfCount() >= Configtf.getTalentCost(27)) {
                isTalent = true;
            }
            if (isTalent) {
                if (pc.get_other2().get_bowpvp() >= Configtf.tfup27) {
                    pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                    isTalent = false;
                }
                else {
                    pc.get_other2().set_bowpvp(pc.get_other2().get_bowpvp() + Configtf.tftype27);
                    pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() - Configtf.getTalentCost(27));
                    pc.get_other2().set_TfCount1(pc.get_other2().get_TfCount1() + Configtf.getTalentCost(27));
                    pc.add_bowpvp(Configtf.tftype27);
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
            }
            this.TF(pc);
        }
        else {
            return false;
        }
        return true;
    }
    
    public String[] buildTalentInfo(L1PcInstance pc) {
        // 需要容納: 0~27 角色當前值, 101~127 天賦上限, 201~227 天賦消耗點數, 301~327 天賦加成值
        String[] info = new String[400];
        if (pc.get_other2().get_TfCount() > 0) {
            info[0] = String.valueOf(pc.get_other2().get_TfCount());
        }
        else {
            info[0] = "0";
        }
        if (pc.get_other2().get_Str() > 0) {
            info[1] = String.valueOf(pc.get_other2().get_Str());
        }
        else {
            info[1] = "0";
        }
        if (pc.get_other2().get_Dex() > 0) {
            info[2] = String.valueOf(pc.get_other2().get_Dex());
        }
        else {
            info[2] = "0";
        }
        if (pc.get_other2().get_Int() > 0) {
            info[3] = String.valueOf(pc.get_other2().get_Int());
        }
        else {
            info[3] = "0";
        }
        if (pc.get_other2().get_Attack() > 0) {
            info[4] = String.valueOf(pc.get_other2().get_Attack());
        }
        else {
            info[4] = "0";
        }
        if (pc.get_other2().get_BowAttack() > 0) {
            info[5] = String.valueOf(pc.get_other2().get_BowAttack());
        }
        else {
            info[5] = "0";
        }
        if (pc.get_other2().get_Hp() > 0) {
            info[6] = String.valueOf(pc.get_other2().get_Hp());
        }
        else {
            info[6] = "0";
        }
        if (pc.get_other2().get_Mp() > 0) {
            info[7] = String.valueOf(pc.get_other2().get_Mp());
        }
        else {
            info[7] = "0";
        }
        if (pc.get_other2().get_ReductionDmg() > 0) {
            info[8] = String.valueOf(pc.get_other2().get_ReductionDmg());
        }
        else {
            info[8] = "0";
        }
        if (pc.get_other2().get_Sp() > 0) {
            info[9] = String.valueOf(pc.get_other2().get_Sp());
        }
        else {
            info[9] = "0";
        }
        if (pc.get_other2().get_Mr() > 0) {
            info[10] = String.valueOf(pc.get_other2().get_Mr());
        }
        else {
            info[10] = "0";
        }
        if (pc.get_other2().get_Con() > 0) {
            info[11] = String.valueOf(pc.get_other2().get_Con());
        }
        else {
            info[11] = "0";
        }
        if (pc.get_other2().get_Cha() > 0) {
            info[12] = String.valueOf(pc.get_other2().get_Cha());
        }
        else {
            info[12] = "0";
        }
        if (pc.get_other2().get_Wis() > 0) {
            info[13] = String.valueOf(pc.get_other2().get_Wis());
        }
        else {
            info[13] = "0";
        }
        if (pc.get_other2().get_Hpr() > 0) {
            info[14] = String.valueOf(pc.get_other2().get_Hpr());
        }
        else {
            info[14] = "0";
        }
        if (pc.get_other2().get_Mpr() > 0) {
            info[15] = String.valueOf(pc.get_other2().get_Mpr());
        }
        else {
            info[15] = "0";
        }
        if (pc.get_other2().get_hppotion() > 0) {
            info[16] = String.valueOf(pc.get_other2().get_hppotion());
        }
        else {
            info[16] = "0";
        }
        if (pc.get_other2().get_exp() > 0) {
            info[17] = String.valueOf(pc.get_other2().get_exp());
        }
        else {
            info[17] = "0";
        }
// 把負數的 AC 轉成正數的等級顯示（例如 -2 變成 2）
        int acLevel = Math.abs(pc.get_other2().get_ac());
        if (acLevel > 0) {
            info[18] = String.valueOf(acLevel);
        }
        else {
            info[18] = "0";
        }
        if (pc.get_other2().get_weight() > 0) {
            info[19] = String.valueOf(pc.get_other2().get_weight());
        }
        else {
            info[19] = "0";
        }
        if (pc.get_other2().get_regist_stun() > 0) {
            info[20] = String.valueOf(pc.get_other2().get_regist_stun());
        }
        else {
            info[20] = "0";
        }
        if (pc.get_other2().get_regist_stone() > 0) {
            info[21] = String.valueOf(pc.get_other2().get_regist_stone());
        }
        else {
            info[21] = "0";
        }
        if (pc.get_other2().get_regist_sleep() > 0) {
            info[22] = String.valueOf(pc.get_other2().get_regist_sleep());
        }
        else {
            info[22] = "0";
        }
        if (pc.get_other2().get_regist_freeze() > 0) {
            info[23] = String.valueOf(pc.get_other2().get_regist_freeze());
        }
        else {
            info[23] = "0";
        }
        if (pc.get_other2().get_regist_sustain() > 0) {
            info[24] = String.valueOf(pc.get_other2().get_regist_sustain());
        }
        else {
            info[24] = "0";
        }
        if (pc.get_other2().get_regist_blind() > 0) {
            info[25] = String.valueOf(pc.get_other2().get_regist_blind());
        }
        else {
            info[25] = "0";
        }
        if (pc.get_other2().get_pvp() > 0) {
            info[26] = String.valueOf(pc.get_other2().get_pvp());
        }
        else {
            info[26] = "0";
        }
        if (pc.get_other2().get_bowpvp() > 0) {
            info[27] = String.valueOf(pc.get_other2().get_bowpvp());
        }
        else {
            info[27] = "0";
        }
        
        // 讓對話檔直接抓設定檔的最大值，不再寫死在 HTML 裡
        info[101] = String.valueOf(Configtf.tfup1);
        info[102] = String.valueOf(Configtf.tfup2);
        info[103] = String.valueOf(Configtf.tfup3);
        info[104] = String.valueOf(Configtf.tfup4);
        info[105] = String.valueOf(Configtf.tfup5);
        info[106] = String.valueOf(Configtf.tfup6);
        info[107] = String.valueOf(Configtf.tfup7);
        info[108] = String.valueOf(Configtf.tfup8);
        info[109] = String.valueOf(Configtf.tfup9);
        info[110] = String.valueOf(Configtf.tfup10);
        info[111] = String.valueOf(Configtf.tfup11);
        info[112] = String.valueOf(Configtf.tfup12);
        info[113] = String.valueOf(Configtf.tfup13);
        info[114] = String.valueOf(Configtf.tfup14);
        info[115] = String.valueOf(Configtf.tfup15);
        info[116] = String.valueOf(Configtf.tfup16);
        info[117] = String.valueOf(Configtf.tfup17);
        info[118] = String.valueOf(Configtf.tfup18);
        info[119] = String.valueOf(Configtf.tfup19);
        info[120] = String.valueOf(Configtf.tfup20);
        info[121] = String.valueOf(Configtf.tfup21);
        info[122] = String.valueOf(Configtf.tfup22);
        info[123] = String.valueOf(Configtf.tfup23);
        info[124] = String.valueOf(Configtf.tfup24);
        info[125] = String.valueOf(Configtf.tfup25);
        info[126] = String.valueOf(Configtf.tfup26);
        info[127] = String.valueOf(Configtf.tfup27);
        
        // 讓對話檔可直接抓取每個天賦的消耗點數
        info[201] = String.valueOf(Configtf.tfcount1);
        info[202] = String.valueOf(Configtf.tfcount2);
        info[203] = String.valueOf(Configtf.tfcount3);
        info[204] = String.valueOf(Configtf.tfcount4);
        info[205] = String.valueOf(Configtf.tfcount5);
        info[206] = String.valueOf(Configtf.tfcount6);
        info[207] = String.valueOf(Configtf.tfcount7);
        info[208] = String.valueOf(Configtf.tfcount8);
        info[209] = String.valueOf(Configtf.tfcount9);
        info[210] = String.valueOf(Configtf.tfcount10);
        info[211] = String.valueOf(Configtf.tfcount11);
        info[212] = String.valueOf(Configtf.tfcount12);
        info[213] = String.valueOf(Configtf.tfcount13);
        info[214] = String.valueOf(Configtf.tfcount14);
        info[215] = String.valueOf(Configtf.tfcount15);
        info[216] = String.valueOf(Configtf.tfcount16);
        info[217] = String.valueOf(Configtf.tfcount17);
        info[218] = String.valueOf(Configtf.tfcount18);
        info[219] = String.valueOf(Configtf.tfcount19);
        info[220] = String.valueOf(Configtf.tfcount20);
        info[221] = String.valueOf(Configtf.tfcount21);
        info[222] = String.valueOf(Configtf.tfcount22);
        info[223] = String.valueOf(Configtf.tfcount23);
        info[224] = String.valueOf(Configtf.tfcount24);
        info[225] = String.valueOf(Configtf.tfcount25);
        info[226] = String.valueOf(Configtf.tfcount26);
        info[227] = String.valueOf(Configtf.tfcount27);
        
        info[301] = String.valueOf(Configtf.tftype1);
        info[302] = String.valueOf(Configtf.tftype2);
        info[303] = String.valueOf(Configtf.tftype3);
        info[304] = String.valueOf(Configtf.tftype4);
        info[305] = String.valueOf(Configtf.tftype5);
        info[306] = String.valueOf(Configtf.tftype6);
        info[307] = String.valueOf(Configtf.tftype7);
        info[308] = String.valueOf(Configtf.tftype8);
        info[309] = String.valueOf(Configtf.tftype9);
        info[310] = String.valueOf(Configtf.tftype10);
        info[311] = String.valueOf(Configtf.tftype11);
        info[312] = String.valueOf(Configtf.tftype12);
        info[313] = String.valueOf(Configtf.tftype13);
        info[314] = String.valueOf(Configtf.tftype14);
        info[315] = String.valueOf(Configtf.tftype15);
        info[316] = String.valueOf(Configtf.tftype16);
        info[317] = String.valueOf(Configtf.tftype17);
        info[318] = String.valueOf(Configtf.tftype18);
        info[319] = String.valueOf(Configtf.tftype19);
        info[320] = String.valueOf(Configtf.tftype20);
        info[321] = String.valueOf(Configtf.tftype21);
        info[322] = String.valueOf(Configtf.tftype22);
        info[323] = String.valueOf(Configtf.tftype23);
        info[324] = String.valueOf(Configtf.tftype24);
        info[325] = String.valueOf(Configtf.tftype25);
        info[326] = String.valueOf(Configtf.tftype26);
        info[327] = String.valueOf(Configtf.tftype27);
        
        return info;
    }

    private int getSpentTalentPoints(L1PcInstance pc) {
        int total = 0;
        total += pc.get_other2().get_Str() > 0 ? (pc.get_other2().get_Str() / Configtf.tftype1) * Configtf.tfcount1 : 0;
        total += pc.get_other2().get_Dex() > 0 ? (pc.get_other2().get_Dex() / Configtf.tftype2) * Configtf.tfcount2 : 0;
        total += pc.get_other2().get_Int() > 0 ? (pc.get_other2().get_Int() / Configtf.tftype3) * Configtf.tfcount3 : 0;
        total += pc.get_other2().get_Attack() > 0 ? (pc.get_other2().get_Attack() / Configtf.tftype4) * Configtf.tfcount4 : 0;
        total += pc.get_other2().get_BowAttack() > 0 ? (pc.get_other2().get_BowAttack() / Configtf.tftype5) * Configtf.tfcount5 : 0;
        total += pc.get_other2().get_Hp() > 0 ? (pc.get_other2().get_Hp() / Configtf.tftype6) * Configtf.tfcount6 : 0;
        total += pc.get_other2().get_Mp() > 0 ? (pc.get_other2().get_Mp() / Configtf.tftype7) * Configtf.tfcount7 : 0;
        total += pc.get_other2().get_ReductionDmg() > 0 ? (pc.get_other2().get_ReductionDmg() / Configtf.tftype8) * Configtf.tfcount8 : 0;
        total += pc.get_other2().get_Sp() > 0 ? (pc.get_other2().get_Sp() / Configtf.tftype9) * Configtf.tfcount9 : 0;
        total += pc.get_other2().get_Mr() > 0 ? (pc.get_other2().get_Mr() / Configtf.tftype10) * Configtf.tfcount10 : 0;
        total += pc.get_other2().get_Con() > 0 ? (pc.get_other2().get_Con() / Configtf.tftype11) * Configtf.tfcount11 : 0;
        total += pc.get_other2().get_Cha() > 0 ? (pc.get_other2().get_Cha() / Configtf.tftype12) * Configtf.tfcount12 : 0;
        total += pc.get_other2().get_Wis() > 0 ? (pc.get_other2().get_Wis() / Configtf.tftype13) * Configtf.tfcount13 : 0;
        total += pc.get_other2().get_Hpr() > 0 ? (pc.get_other2().get_Hpr() / Configtf.tftype14) * Configtf.tfcount14 : 0;
        total += pc.get_other2().get_Mpr() > 0 ? (pc.get_other2().get_Mpr() / Configtf.tftype15) * Configtf.tfcount15 : 0;
        total += pc.get_other2().get_hppotion() > 0 ? (pc.get_other2().get_hppotion() / Configtf.tftype16) * Configtf.tfcount16 : 0;
        total += pc.get_other2().get_exp() > 0 ? (pc.get_other2().get_exp() / Configtf.tftype17) * Configtf.tfcount17 : 0;
        total += pc.get_other2().get_ac() != 0 ? (Math.abs(pc.get_other2().get_ac()) / Configtf.tftype18) * Configtf.tfcount18 : 0;
        total += pc.get_other2().get_weight() > 0 ? (pc.get_other2().get_weight() / Configtf.tftype19) * Configtf.tfcount19 : 0;
        total += pc.get_other2().get_regist_stun() > 0 ? (pc.get_other2().get_regist_stun() / Configtf.tftype20) * Configtf.tfcount20 : 0;
        total += pc.get_other2().get_regist_stone() > 0 ? (pc.get_other2().get_regist_stone() / Configtf.tftype21) * Configtf.tfcount21 : 0;
        total += pc.get_other2().get_regist_sleep() > 0 ? (pc.get_other2().get_regist_sleep() / Configtf.tftype22) * Configtf.tfcount22 : 0;
        total += pc.get_other2().get_regist_freeze() > 0 ? (pc.get_other2().get_regist_freeze() / Configtf.tftype23) * Configtf.tfcount23 : 0;
        total += pc.get_other2().get_regist_sustain() > 0 ? (pc.get_other2().get_regist_sustain() / Configtf.tftype24) * Configtf.tfcount24 : 0;
        total += pc.get_other2().get_regist_blind() > 0 ? (pc.get_other2().get_regist_blind() / Configtf.tftype25) * Configtf.tfcount25 : 0;
        total += pc.get_other2().get_pvp() > 0 ? (pc.get_other2().get_pvp() / Configtf.tftype26) * Configtf.tfcount26 : 0;
        total += pc.get_other2().get_bowpvp() > 0 ? (pc.get_other2().get_bowpvp() / Configtf.tftype27) * Configtf.tfcount27 : 0;
        return total;
    }

    public boolean resetTalent(L1PcInstance pc) {
        if (pc == null) {
            return false;
        }

        if (!pc.getInventory().checkItem(Configtf.retfitem, (long)Configtf.retfitemcount)) {
            String message = (Configtf.msg0 == null || Configtf.msg0.trim().isEmpty())
                ? "需要重置道具才能重置天賦。"
                : Configtf.msg0;
            pc.sendPackets(new S_SystemMessage(message));
            return false;
        }

        int spent = this.getSpentTalentPoints(pc);
        if (spent <= 0) {
            pc.sendPackets(new S_SystemMessage("目前沒有可重置的天賦點數"));
            return false;
        }

        pc.getInventory().consumeItem(Configtf.retfitem, (long)Configtf.retfitemcount);
        pc.get_other2().set_TfCount(pc.get_other2().get_TfCount() + spent);
        pc.get_other2().set_TfCount1(0);

        pc.addDmgup(-pc.get_other2().get_Attack());
        pc.addBowDmgup(-pc.get_other2().get_BowAttack());
        pc.addSp(-pc.get_other2().get_Sp());
        pc.addStr(-pc.get_other2().get_Str());
        pc.addDex(-pc.get_other2().get_Dex());
        pc.addInt(-pc.get_other2().get_Int());
        pc.addMaxHp(-pc.get_other2().get_Hp());
        pc.addMaxMp(-pc.get_other2().get_Mp());
        pc.addMr(-pc.get_other2().get_Mr());
        pc.addother_ReductionDmg(-pc.get_other2().get_ReductionDmg());
        pc.addCon(-pc.get_other2().get_Con());
        pc.addCha(-pc.get_other2().get_Cha());
        pc.addWis(-pc.get_other2().get_Wis());
        pc.addHpr(-pc.get_other2().get_Hpr());
        pc.addMpr(-pc.get_other2().get_Mpr());
        pc.add_hppotion(-pc.get_other2().get_hppotion());
        pc.add_exp(-pc.get_other2().get_exp());
        pc.addAc(-pc.get_other2().get_ac());
        pc.addWeightReduction(-pc.get_other2().get_weight());
        pc.addRegistStun(-pc.get_other2().get_regist_stun());
        pc.addRegistStone(-pc.get_other2().get_regist_stone());
        pc.addRegistSleep(-pc.get_other2().get_regist_sleep());
        pc.add_regist_freeze(-pc.get_other2().get_regist_freeze());
        pc.addRegistSustain(-pc.get_other2().get_regist_sustain());
        pc.addRegistBlind(-pc.get_other2().get_regist_blind());
        pc.add_pvp(-pc.get_other2().get_pvp());
        pc.add_bowpvp(-pc.get_other2().get_bowpvp());

        pc.get_other2().set_Str(0);
        pc.get_other2().set_Dex(0);
        pc.get_other2().set_Int(0);
        pc.get_other2().set_Attack(0);
        pc.get_other2().set_BowAttack(0);
        pc.get_other2().set_Hp(0);
        pc.get_other2().set_Mp(0);
        pc.get_other2().set_ReductionDmg(0);
        pc.get_other2().set_Sp(0);
        pc.get_other2().set_Mr(0);
        pc.get_other2().set_Con(0);
        pc.get_other2().set_Cha(0);
        pc.get_other2().set_Wis(0);
        pc.get_other2().set_Hpr(0);
        pc.get_other2().set_Mpr(0);
        pc.get_other2().set_hppotion(0);
        pc.get_other2().set_exp(0);
        pc.get_other2().set_ac(0);
        pc.get_other2().set_weight(0);
        pc.get_other2().set_regist_stun(0);
        pc.get_other2().set_regist_stone(0);
        pc.get_other2().set_regist_sleep(0);
        pc.get_other2().set_regist_freeze(0);
        pc.get_other2().set_regist_sustain(0);
        pc.get_other2().set_regist_blind(0);
        pc.get_other2().set_pvp(0);
        pc.get_other2().set_bowpvp(0);

        pc.sendPackets(new S_SystemMessage("天賦已重置，已回復 " + spent + " 點天賦點數"));
        pc.sendPackets(new S_OwnCharStatus2(pc));
        this.TF(pc);
        return true;
    }

    public void TF(L1PcInstance pc) {
        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tfcount", this.buildTalentInfo(pc)));
    }
}
