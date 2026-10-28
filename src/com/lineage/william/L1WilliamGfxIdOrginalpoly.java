package com.lineage.william;

import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1WilliamGfxIdOrginalpoly
{
    private int _gfxId;
    private boolean _deadExp;
    private boolean _cancellation;
    private byte _addStr;
    private byte _addDex;
    private byte _addCon;
    private byte _addInt;
    private byte _addWis;
    private byte _addCha;
    private int _addAc;
    private int _addMaxHp;
    private int _addMaxMp;
    private int _addHpr;
    private int _addMpr;
    private int _addDmg;
    private int _addBowDmg;
    private int _addHit;
    private int _addBowHit;
    private int _reduction_dmg;
    private int _reduction_magic_dmg;
    private int _addMr;
    private int _addSp;
    private int _addFire;
    private int _addWind;
    private int _addEarth;
    private int _addWater;
    private double _addExp;
    private final int _potion_heal;
    private final int _PVPdmg;
    private final int _PVPdmgReduction;
    private final int _magic_hit;
    private int _regist_stun;
    private int _regist_stone;
    private int _regist_sleep;
    private int _regist_freeze;
    private int _regist_sustain;
    private int _regist_blind;
    
    public static boolean DeadExp(int gfxId) {
        L1WilliamGfxIdOrginalpoly gfxIdOrginalpoly = GfxIdOrginalpoly.getInstance().getTemplate(gfxId);
        return gfxIdOrginalpoly != null && gfxIdOrginalpoly.getDeadExp();
    }
    
    public static boolean Cancellation(int gfxId) {
        L1WilliamGfxIdOrginalpoly gfxIdOrginalpoly = GfxIdOrginalpoly.getInstance().getTemplate(gfxId);
        return gfxIdOrginalpoly != null && gfxIdOrginalpoly.getCancellation();
    }
    
    public static void getAddGfxIdOrginalpoly(L1PcInstance pc, int gfxId) {
        L1WilliamGfxIdOrginalpoly gfxIdOrginalpoly = GfxIdOrginalpoly.getInstance().getTemplate(gfxId);
        if (gfxIdOrginalpoly == null) {
            return;
        }
        else {
            pc.sendPackets(new S_ServerMessage("\\aD[此變身能力獲得以下能力]"));
            if (gfxIdOrginalpoly.getAddStr() != 0) {
                pc.addStr((int)gfxIdOrginalpoly.getAddStr());
                pc.sendPackets(new S_ServerMessage("\\aE力量+" + (int)gfxIdOrginalpoly.getAddStr()));
            }
            if (gfxIdOrginalpoly.getAddDex() != 0) {
                pc.addDex((int)gfxIdOrginalpoly.getAddDex());
                pc.sendPackets(new S_ServerMessage("\\aE敏捷+" + (int)gfxIdOrginalpoly.getAddDex()));
            }
            if (gfxIdOrginalpoly.getAddCon() != 0) {
                pc.addCon((int)gfxIdOrginalpoly.getAddCon());
                pc.sendPackets(new S_ServerMessage("\\aE體質+" + (int)gfxIdOrginalpoly.getAddCon()));
            }
            if (gfxIdOrginalpoly.getAddInt() != 0) {
                pc.addInt((int)gfxIdOrginalpoly.getAddInt());
                pc.sendPackets(new S_ServerMessage("\\aE智力+" + (int)gfxIdOrginalpoly.getAddInt()));
            }
            if (gfxIdOrginalpoly.getAddWis() != 0) {
                pc.addWis((int)gfxIdOrginalpoly.getAddWis());
                pc.sendPackets(new S_ServerMessage("\\aE精神+" + (int)gfxIdOrginalpoly.getAddWis()));
            }
            if (gfxIdOrginalpoly.getAddCha() != 0) {
                pc.addCha((int)gfxIdOrginalpoly.getAddCha());
                pc.sendPackets(new S_ServerMessage("\\aE魅力+" + (int)gfxIdOrginalpoly.getAddCha()));
            }
            if (gfxIdOrginalpoly.getAddAc() != 0) {
                pc.addAc(-gfxIdOrginalpoly.getAddAc());
                pc.sendPackets(new S_ServerMessage("\\aE防禦+" + gfxIdOrginalpoly.getAddAc()));
            }
            if (gfxIdOrginalpoly.getAddMaxHp() != 0) {
                pc.addMaxHp(gfxIdOrginalpoly.getAddMaxHp());
                pc.sendPackets(new S_ServerMessage("\\aE血量增加+" + gfxIdOrginalpoly.getAddMaxHp()));
                pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            }
            if (gfxIdOrginalpoly.getAddMaxMp() != 0) {
                pc.addMaxMp(gfxIdOrginalpoly.getAddMaxMp());
                pc.sendPackets(new S_ServerMessage("\\aE魔量增加+" + gfxIdOrginalpoly.getAddMaxMp()));
                pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
            }
            if (gfxIdOrginalpoly.getAddHpr() != 0) {
                pc.addHpr(gfxIdOrginalpoly.getAddHpr());
                pc.sendPackets(new S_ServerMessage("\\aE回血+" + gfxIdOrginalpoly.getAddHpr()));
                pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            }
            if (gfxIdOrginalpoly.getAddMpr() != 0) {
                pc.addMpr(gfxIdOrginalpoly.getAddMpr());
                pc.sendPackets(new S_ServerMessage("\\aE回魔+" + gfxIdOrginalpoly.getAddMpr()));
                pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
            }
            if (gfxIdOrginalpoly.getAddDmg() != 0) {
                pc.addDmgup(gfxIdOrginalpoly.getAddDmg());
                pc.sendPackets(new S_ServerMessage("\\aE近戰傷害+" + gfxIdOrginalpoly.getAddDmg()));
            }
            if (gfxIdOrginalpoly.getAddHit() != 0) {
                pc.addHitup(gfxIdOrginalpoly.getAddHit());
                pc.sendPackets(new S_ServerMessage("\\aE近戰命中+" + gfxIdOrginalpoly.getAddHit()));
            }
            if (gfxIdOrginalpoly.getAddBowDmg() != 0) {
                pc.addBowDmgup(gfxIdOrginalpoly.getAddBowDmg());
                pc.sendPackets(new S_ServerMessage("\\aE遠戰傷害+" + gfxIdOrginalpoly.getAddBowDmg()));
            }
            if (gfxIdOrginalpoly.getAddBowHit() != 0) {
                pc.addBowHitup(gfxIdOrginalpoly.getAddBowHit());
                pc.sendPackets(new S_ServerMessage("\\aE遠戰命中+" + gfxIdOrginalpoly.getAddBowHit()));
            }
            if (gfxIdOrginalpoly.getReduction_dmg() != 0) {
                pc.addother_ReductionDmg(gfxIdOrginalpoly.getReduction_dmg());
                pc.sendPackets(new S_ServerMessage("\\aE減免物理傷害+" + gfxIdOrginalpoly.getReduction_dmg()));
if (gfxIdOrginalpoly.getDeadExp()) {
    pc.sendPackets(new S_ServerMessage("\\aE死亡不扣經驗值"));
}
if (gfxIdOrginalpoly.getCancellation()) {
    pc.sendPackets(new S_ServerMessage("\\aE免疫魔法相消術"));
}

            }
            if (gfxIdOrginalpoly.getAddMr() != 0) {
                pc.addMr(gfxIdOrginalpoly.getAddMr());
                pc.sendPackets(new S_ServerMessage("\\aE抗魔+" + gfxIdOrginalpoly.getAddMr()));
            }
            if (gfxIdOrginalpoly.getAddSp() != 0) {
                pc.addSp(gfxIdOrginalpoly.getAddSp());
                pc.sendPackets(new S_ServerMessage("\\aE魔攻+" + gfxIdOrginalpoly.getAddSp()));
            }
            if (gfxIdOrginalpoly.getAddFire() != 0) {
                pc.addFire(gfxIdOrginalpoly.getAddFire());
                pc.sendPackets(new S_ServerMessage("\\aE+抗火屬性" + gfxIdOrginalpoly.getAddFire()));
            }
            if (gfxIdOrginalpoly.getAddWind() != 0) {
                pc.addWind(gfxIdOrginalpoly.getAddWind());
                pc.sendPackets(new S_ServerMessage("\\aE抗風屬性+" + gfxIdOrginalpoly.getAddWind()));
            }
            if (gfxIdOrginalpoly.getAddEarth() != 0) {
                pc.addEarth(gfxIdOrginalpoly.getAddEarth());
                pc.sendPackets(new S_ServerMessage("\\aE抗地屬性+" + gfxIdOrginalpoly.getAddEarth()));
            }
            if (gfxIdOrginalpoly.getAddWater() != 0) {
                pc.addWater(gfxIdOrginalpoly.getAddWater());
                pc.sendPackets(new S_ServerMessage("\\aE抗水屬性+" + gfxIdOrginalpoly.getAddWater()));
            }
            if (gfxIdOrginalpoly.getAddExp() != 0.0) {
                pc.addExpByArmor(gfxIdOrginalpoly.getAddExp());
                pc.sendPackets(new S_ServerMessage("\\aE經驗+" + gfxIdOrginalpoly.getAddExp() + "%"));
            }
            if (gfxIdOrginalpoly.getPotion_Heal() != 0) {
                pc.add_potion_heal(gfxIdOrginalpoly.getPotion_Heal());
                pc.sendPackets(new S_ServerMessage("\\aE藥水回復量+" + gfxIdOrginalpoly.getPotion_Heal() + "%"));
            }
            if (gfxIdOrginalpoly.getPVPdmg() != 0) {
                pc.add_PVPdmgg(gfxIdOrginalpoly.getPVPdmg());
                pc.sendPackets(new S_ServerMessage("\\aEPVP傷害+" + gfxIdOrginalpoly.getPVPdmg()));
            }
            if (gfxIdOrginalpoly.getPVPdmgReduction() != 0) {
                pc.addPVPdmgReduction(gfxIdOrginalpoly.getPVPdmgReduction());
                pc.sendPackets(new S_ServerMessage("\\aEPVP減傷+" + gfxIdOrginalpoly.getPVPdmgReduction()));
            }
            if (gfxIdOrginalpoly.getAddMagicHit() != 0) {
                pc.addOriginalMagicHit(gfxIdOrginalpoly.getAddMagicHit());
                pc.sendPackets(new S_ServerMessage("\\aE魔法命中+" + gfxIdOrginalpoly.getAddMagicHit()));
            }
            if (gfxIdOrginalpoly.getRegist_stun() != 0) {
                pc.addRegistStun(gfxIdOrginalpoly.getRegist_stun());
                pc.sendPackets(new S_ServerMessage("\\aE昏迷耐性+" + gfxIdOrginalpoly.getRegist_stun()));
            }
            if (gfxIdOrginalpoly.getRegist_stone() != 0) {
                pc.addRegistStone(gfxIdOrginalpoly.getRegist_stone());
                pc.sendPackets(new S_ServerMessage("\\aE石化耐性+" + gfxIdOrginalpoly.getRegist_stone()));
            }
            if (gfxIdOrginalpoly.getRegist_sleep() != 0) {
                pc.addRegistSleep(gfxIdOrginalpoly.getRegist_sleep());
                pc.sendPackets(new S_ServerMessage("\\aE睡眠耐性+" + gfxIdOrginalpoly.getRegist_sleep()));
            }
            if (gfxIdOrginalpoly.getRegist_freeze() != 0) {
                pc.add_regist_freeze(gfxIdOrginalpoly.getRegist_freeze());
                pc.sendPackets(new S_ServerMessage("\\aE冰耐性性+" + gfxIdOrginalpoly.getRegist_freeze()));
            }
            if (gfxIdOrginalpoly.getRegist_sustain() != 0) {
                pc.addRegistSustain(gfxIdOrginalpoly.getRegist_sustain());
                pc.sendPackets(new S_ServerMessage("\\aE支撐耐性+" + gfxIdOrginalpoly.getRegist_sustain()));
            }
            if (gfxIdOrginalpoly.getRegist_blind() != 0) {
                pc.addRegistBlind(gfxIdOrginalpoly.getRegist_blind());
                pc.sendPackets(new S_ServerMessage("\\aE暗黑耐性+" + gfxIdOrginalpoly.getRegist_blind()));
            }
            pc.sendPackets(new S_SPMR(pc));
            pc.sendPackets(new S_OwnCharStatus(pc));
            pc.sendPackets(new S_OwnCharStatus2(pc));
            return;
        }
    }
    
    public static void getReductionGfxIdOrginalpoly(L1PcInstance pc, int gfxId) {
        L1WilliamGfxIdOrginalpoly gfxIdOrginalpoly = GfxIdOrginalpoly.getInstance().getTemplate(gfxId);
        if (gfxIdOrginalpoly == null) {
            return;
        }
        else {
            pc.sendPackets(new S_ServerMessage("\\aD[變身能力消失.恢復正常]"));
            if (gfxIdOrginalpoly.getAddStr() != 0) {
                pc.addStr(-gfxIdOrginalpoly.getAddStr());
                pc.sendPackets(new S_ServerMessage("\\aE力量+" + (int)gfxIdOrginalpoly.getAddStr() + "[能力消失了]"));
            }
if (gfxIdOrginalpoly.getDeadExp()) {
    pc.sendPackets(new S_ServerMessage("\\aE死亡不扣經驗值[能力消失了]"));
}

if (gfxIdOrginalpoly.getCancellation()) {
    pc.sendPackets(new S_ServerMessage("\\aE免疫魔法相消術[能力消失了]"));
}

            if (gfxIdOrginalpoly.getAddDex() != 0) {
                pc.addDex(-gfxIdOrginalpoly.getAddDex());
                pc.sendPackets(new S_ServerMessage("\\aE敏捷+" + (int)gfxIdOrginalpoly.getAddDex() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddCon() != 0) {
                pc.addCon(-gfxIdOrginalpoly.getAddCon());
                pc.sendPackets(new S_ServerMessage("\\aE體質+" + (int)gfxIdOrginalpoly.getAddCon() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddInt() != 0) {
                pc.addInt(-gfxIdOrginalpoly.getAddInt());
                pc.sendPackets(new S_ServerMessage("\\aE智力+" + (int)gfxIdOrginalpoly.getAddInt() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddWis() != 0) {
                pc.addWis(-gfxIdOrginalpoly.getAddWis());
                pc.sendPackets(new S_ServerMessage("\\aE精神+" + (int)gfxIdOrginalpoly.getAddWis() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddCha() != 0) {
                pc.addCha(-gfxIdOrginalpoly.getAddCha());
                pc.sendPackets(new S_ServerMessage("\\aE魅力+" + (int)gfxIdOrginalpoly.getAddCha() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddAc() != 0) {
                pc.addAc(gfxIdOrginalpoly.getAddAc());
                pc.sendPackets(new S_ServerMessage("\\aE防禦+" + gfxIdOrginalpoly.getAddAc() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddMaxHp() != 0) {
                pc.addMaxHp(-gfxIdOrginalpoly.getAddMaxHp());
                pc.sendPackets(new S_ServerMessage("\\aE血量增加+" + gfxIdOrginalpoly.getAddMaxHp() + "[能力消失了]"));
                pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            }
            if (gfxIdOrginalpoly.getAddMaxMp() != 0) {
                pc.addMaxMp(-gfxIdOrginalpoly.getAddMaxMp());
                pc.sendPackets(new S_ServerMessage("\\aE魔量增加+" + gfxIdOrginalpoly.getAddMaxMp() + "[能力消失了]"));
                pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
            }
            if (gfxIdOrginalpoly.getAddHpr() != 0) {
                pc.addHpr(-gfxIdOrginalpoly.getAddHpr());
                pc.sendPackets(new S_ServerMessage("\\aE回血+" + gfxIdOrginalpoly.getAddHpr() + "[能力消失了]"));
                pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            }
            if (gfxIdOrginalpoly.getAddMpr() != 0) {
                pc.addMpr(-gfxIdOrginalpoly.getAddMpr());
                pc.sendPackets(new S_ServerMessage("\\aE回魔+" + gfxIdOrginalpoly.getAddMpr() + "[能力消失了]"));
                pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
            }
            if (gfxIdOrginalpoly.getAddDmg() != 0) {
                pc.addDmgup(-gfxIdOrginalpoly.getAddDmg());
                pc.sendPackets(new S_ServerMessage("\\aE近戰傷害+" + gfxIdOrginalpoly.getAddDmg() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddHit() != 0) {
                pc.addHitup(-gfxIdOrginalpoly.getAddHit());
                pc.sendPackets(new S_ServerMessage("\\aE近戰命中+" + gfxIdOrginalpoly.getAddHit() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddBowDmg() != 0) {
                pc.addBowDmgup(-gfxIdOrginalpoly.getAddBowDmg());
                pc.sendPackets(new S_ServerMessage("\\aE遠戰傷害+" + gfxIdOrginalpoly.getAddBowDmg() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddBowHit() != 0) {
                pc.addBowHitup(-gfxIdOrginalpoly.getAddBowHit());
                pc.sendPackets(new S_ServerMessage("\\aE遠戰命中+" + gfxIdOrginalpoly.getAddBowHit() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getReduction_dmg() != 0) {
                pc.addother_ReductionDmg(-gfxIdOrginalpoly.getReduction_dmg());
                pc.sendPackets(new S_ServerMessage("\\aE減免物理傷害+" + gfxIdOrginalpoly.getReduction_dmg() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddMr() != 0) {
                pc.addMr(-gfxIdOrginalpoly.getAddMr());
                pc.sendPackets(new S_ServerMessage("\\aE抗魔+" + gfxIdOrginalpoly.getAddMr() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddSp() != 0) {
                pc.addSp(-gfxIdOrginalpoly.getAddSp());
                pc.sendPackets(new S_ServerMessage("\\aE魔攻+" + gfxIdOrginalpoly.getAddSp() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddFire() != 0) {
                pc.addFire(-gfxIdOrginalpoly.getAddFire());
                pc.sendPackets(new S_ServerMessage("\\aE+抗火屬性" + gfxIdOrginalpoly.getAddFire() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddWind() != 0) {
                pc.addWind(-gfxIdOrginalpoly.getAddWind());
                pc.sendPackets(new S_ServerMessage("\\aE抗風屬性+" + gfxIdOrginalpoly.getAddWind() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddEarth() != 0) {
                pc.addEarth(-gfxIdOrginalpoly.getAddEarth());
                pc.sendPackets(new S_ServerMessage("\\aE抗地屬性+" + gfxIdOrginalpoly.getAddEarth() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddWater() != 0) {
                pc.addWater(-gfxIdOrginalpoly.getAddWater());
                pc.sendPackets(new S_ServerMessage("\\aE抗水屬性+" + gfxIdOrginalpoly.getAddWater() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddExp() != 0.0) {
                pc.addExpByArmor(-gfxIdOrginalpoly.getAddExp());
                pc.sendPackets(new S_ServerMessage("\\aE經驗" + gfxIdOrginalpoly.getAddExp() + "%.[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getPotion_Heal() != 0) {
                pc.add_potion_heal(-gfxIdOrginalpoly.getPotion_Heal());
                pc.sendPackets(new S_ServerMessage("\\aE藥水回復量+" + gfxIdOrginalpoly.getPotion_Heal() + "%.[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getPVPdmg() != 0) {
                pc.add_PVPdmgg(-gfxIdOrginalpoly.getPVPdmg());
                pc.sendPackets(new S_ServerMessage("\\aEPVP傷害+" + gfxIdOrginalpoly.getPVPdmg() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getPVPdmgReduction() != 0) {
                pc.addPVPdmgReduction(-gfxIdOrginalpoly.getPVPdmgReduction());
                pc.sendPackets(new S_ServerMessage("\\aEPVP減傷+" + gfxIdOrginalpoly.getPVPdmgReduction() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getAddMagicHit() != 0) {
                pc.addOriginalMagicHit(-gfxIdOrginalpoly.getAddMagicHit());
                pc.sendPackets(new S_ServerMessage("\\aE魔法命中+" + gfxIdOrginalpoly.getAddMagicHit() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getRegist_stun() != 0) {
                pc.addRegistStun(-gfxIdOrginalpoly.getRegist_stun());
                pc.sendPackets(new S_ServerMessage("\\aE昏迷耐性+" + gfxIdOrginalpoly.getRegist_stun() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getRegist_stone() != 0) {
                pc.addRegistStone(-gfxIdOrginalpoly.getRegist_stone());
                pc.sendPackets(new S_ServerMessage("\\aE石化耐性+" + gfxIdOrginalpoly.getRegist_stone() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getRegist_sleep() != 0) {
                pc.addRegistSleep(-gfxIdOrginalpoly.getRegist_sleep());
                pc.sendPackets(new S_ServerMessage("\\aE睡眠耐性+" + gfxIdOrginalpoly.getRegist_sleep() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getRegist_freeze() != 0) {
                pc.add_regist_freeze(-gfxIdOrginalpoly.getRegist_freeze());
                pc.sendPackets(new S_ServerMessage("\\aE冰耐性性+" + gfxIdOrginalpoly.getRegist_freeze() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getRegist_sustain() != 0) {
                pc.addRegistSustain(-gfxIdOrginalpoly.getRegist_sustain());
                pc.sendPackets(new S_ServerMessage("\\aE支撐耐性+" + gfxIdOrginalpoly.getRegist_sustain() + "[能力消失了]"));
            }
            if (gfxIdOrginalpoly.getRegist_blind() != 0) {
                pc.addRegistBlind(-gfxIdOrginalpoly.getRegist_blind());
                pc.sendPackets(new S_ServerMessage("\\aE暗黑耐性+" + gfxIdOrginalpoly.getRegist_blind() + "[能力消失了]"));
            }
            pc.sendPackets(new S_SPMR(pc));
            pc.sendPackets(new S_OwnCharStatus(pc));
            pc.sendPackets(new S_OwnCharStatus2(pc));
            return;
        }
    }
    
    public L1WilliamGfxIdOrginalpoly(int gfxId, boolean deadExp, boolean cancellation, byte addStr, byte addDex, byte addCon, byte addInt, byte addWis, byte addCha, int addAc, int addMaxHp, int addMaxMp, int addHpr, int addMpr, int addDmg, int addBowDmg, int addHit, int addBowHit, int reduction_dmg, int reduction_magic_dmg, int addMr, int addSp, int addFire, int addWind, int addEarth, int addWater, double addExp, int potion_heal, int PVPdmg, int PVPdmgReduction, int magic_hit, int regist_stun, int regist_stone, int regist_sleep, int regist_freeze, int regist_sustain, int regist_blind) {
        this._gfxId = gfxId;
        this._deadExp = deadExp;
        this._cancellation = cancellation;
        this._addStr = addStr;
        this._addDex = addDex;
        this._addCon = addCon;
        this._addInt = addInt;
        this._addWis = addWis;
        this._addCha = addCha;
        this._addAc = addAc;
        this._addMaxHp = addMaxHp;
        this._addMaxMp = addMaxMp;
        this._addHpr = addHpr;
        this._addMpr = addMpr;
        this._addDmg = addDmg;
        this._addBowDmg = addBowDmg;
        this._addHit = addHit;
        this._addBowHit = addBowHit;
        this._reduction_dmg = reduction_dmg;
        this._reduction_magic_dmg = reduction_magic_dmg;
        this._addMr = addMr;
        this._addSp = addSp;
        this._addFire = addFire;
        this._addWind = addWind;
        this._addEarth = addEarth;
        this._addWater = addWater;
        this._addExp = addExp;
        this._potion_heal = potion_heal;
        this._PVPdmg = PVPdmg;
        this._PVPdmgReduction = PVPdmgReduction;
        this._magic_hit = magic_hit;
        this._regist_stun = regist_stun;
        this._regist_stone = regist_stone;
        this._regist_sleep = regist_sleep;
        this._regist_freeze = regist_freeze;
        this._regist_sustain = regist_sustain;
        this._regist_blind = regist_blind;
    }
    
    public int getGfxId() {
        return this._gfxId;
    }
    
    public boolean getDeadExp() {
        return this._deadExp;
    }
    
    public boolean getCancellation() {
        return this._cancellation;
    }
    
    public byte getAddStr() {
        return this._addStr;
    }
    
    public byte getAddDex() {
        return this._addDex;
    }
    
    public byte getAddCon() {
        return this._addCon;
    }
    
    public byte getAddInt() {
        return this._addInt;
    }
    
    public byte getAddWis() {
        return this._addWis;
    }
    
    public byte getAddCha() {
        return this._addCha;
    }
    
    public int getAddAc() {
        return this._addAc;
    }
    
    public int getAddMaxHp() {
        return this._addMaxHp;
    }
    
    public int getAddMaxMp() {
        return this._addMaxMp;
    }
    
    public int getAddHpr() {
        return this._addHpr;
    }
    
    public int getAddMpr() {
        return this._addMpr;
    }
    
    public int getAddDmg() {
        return this._addDmg;
    }
    
    public int getAddBowDmg() {
        return this._addBowDmg;
    }
    
    public int getAddHit() {
        return this._addHit;
    }
    
    public int getAddBowHit() {
        return this._addBowHit;
    }
    
    public int getReduction_dmg() {
        return this._reduction_dmg;
    }
    
    public int getReduction_magic_dmg() {
        return this._reduction_magic_dmg;
    }
    
    public int getAddMr() {
        return this._addMr;
    }
    
    public int getAddSp() {
        return this._addSp;
    }
    
    public int getAddFire() {
        return this._addFire;
    }
    
    public int getAddWind() {
        return this._addWind;
    }
    
    public int getAddEarth() {
        return this._addEarth;
    }
    
    public int getAddWater() {
        return this._addWater;
    }
    
    public double getAddExp() {
        return this._addExp;
    }
    
    public int getPVPdmg() {
        return this._PVPdmg;
    }
    
    public int getPVPdmgReduction() {
        return this._PVPdmgReduction;
    }
    
    public int getPotion_Heal() {
        return this._potion_heal;
    }
    
    public int getAddMagicHit() {
        return this._magic_hit;
    }
    
    public int getRegist_stun() {
        return this._regist_stun;
    }
    
    public void setRegist_stun(int regist_stun) {
        this._regist_stun = regist_stun;
    }
    
    public int getRegist_stone() {
        return this._regist_stone;
    }
    
    public void setRegist_stone(int regist_stone) {
        this._regist_stone = regist_stone;
    }
    
    public int getRegist_sleep() {
        return this._regist_sleep;
    }
    
    public void setRegist_sleep(int regist_sleep) {
        this._regist_sleep = regist_sleep;
    }
    
    public int getRegist_freeze() {
        return this._regist_freeze;
    }
    
    public void setRegist_freeze(int regist_freeze) {
        this._regist_freeze = regist_freeze;
    }
    
    public int getRegist_sustain() {
        return this._regist_sustain;
    }
    
    public void setRegist_sustain(int regist_sustain) {
        this._regist_sustain = regist_sustain;
    }
    
    public int getRegist_blind() {
        return this._regist_blind;
    }
    
    public void setRegist_blind(int regist_blind) {
        this._regist_blind = regist_blind;
    }
}
