package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.config.Configtf;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class TfRe extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(TfRe.class);
    }
    
    public static ItemExecutor get() {
        return new TfRe();
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
        total += pc.get_other2().get_ac() > 0 ? (pc.get_other2().get_ac() / Configtf.tftype18) * Configtf.tfcount18 : 0;
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
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }

        int spent = this.getSpentTalentPoints(pc);
        if (!pc.getInventory().checkItem(Configtf.retfitem, (long)Configtf.retfitemcount)) {
            String message = (Configtf.msg0 == null || Configtf.msg0.trim().isEmpty())
                ? "需要重置道具才能重置天賦。"
                : Configtf.msg0;
            pc.sendPackets(new S_SystemMessage(message));
            return;
        }
        else if (spent <= 0) {
            pc.sendPackets(new S_SystemMessage("目前沒有可重置的天賦點數"));
            return;
        }
        else {
            pc.getInventory().consumeItem(Configtf.retfitem, (long)Configtf.retfitemcount);
            pc.getInventory().removeItem(item, 1L);
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
            pc.sendPackets(new S_SystemMessage("天賦能力已還原"));
            pc.sendPackets(new S_SPMR(pc));
            pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            pc.sendPackets(new S_OwnCharStatus2(pc));
            pc.sendPackets(new S_CloseList(pc.getId()));
            return;
        }
    }

}
