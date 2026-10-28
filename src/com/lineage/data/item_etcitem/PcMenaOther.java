package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class PcMenaOther extends ItemExecutor {

    private PcMenaOther() {
    }

    public static ItemExecutor get() {
        return new PcMenaOther();
    }

    // ⭐ 道具點擊 → 開 UI
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        showPcMenaOther(pc);
    }

    // ⭐ 給 action 呼叫用
    public void showPcMenaOther(L1PcInstance pc) {

        if (pc == null) {
            return;
        }

        String[] info = new String[39];

        if (pc.getName() != null) {
            info[1] = pc.getName();
        }

        info[2] = String.valueOf(pc.get_other().get_Str());
        info[3] = String.valueOf(pc.get_other().get_Dex());
        info[4] = String.valueOf(pc.get_other().get_Int());
        info[5] = String.valueOf(pc.get_other().get_Con());
        info[6] = String.valueOf(pc.get_other().get_Wis());
        info[7] = String.valueOf(pc.get_other().get_Cha());

        info[8] = String.valueOf(pc.get_other().get_Attack());
        info[9] = String.valueOf(pc.get_other().get_BowAttack());
        info[10] = String.valueOf(pc.get_other().get_Hit());
        info[11] = String.valueOf(pc.get_other().get_BowHit());
        info[12] = String.valueOf(pc.get_other().get_Sp());

        info[13] = String.valueOf(pc.get_other().get_Hp());
        info[14] = String.valueOf(pc.get_other().get_Mp());
        info[15] = String.valueOf(pc.get_other().get_Mr());

        info[16] = String.valueOf(pc.get_other().get_ReductionDmg());
        info[17] = String.valueOf(pc.get_other().get_Hpr());
        info[18] = String.valueOf(pc.get_other().get_Mpr());

        info[19] = String.valueOf(pc.get_other().get_hppotion());
        info[20] = String.valueOf(pc.get_other().get_exp());
        info[21] = String.valueOf(pc.get_other().get_ac());
        info[22] = String.valueOf(pc.get_other().get_weight());

        info[23] = String.valueOf(pc.get_other().get_regist_stun());
        info[24] = String.valueOf(pc.get_other().get_regist_stone());
        info[25] = String.valueOf(pc.get_other().get_regist_sleep());
        info[26] = String.valueOf(pc.get_other().get_regist_freeze());
        info[27] = String.valueOf(pc.get_other().get_regist_sustain());
        info[28] = String.valueOf(pc.get_other().get_regist_blind());

        info[29] = String.valueOf(pc.get_other().get_PvP_dmg());
        info[30] = String.valueOf(pc.get_other().get_PvP_bow_dmg());
        info[31] = String.valueOf(pc.get_other().get_PvP_hit());
        info[32] = String.valueOf(pc.get_other().get_PvP_bow_hit());
        info[33] = String.valueOf(pc.get_other().get_PvP_dmg_r());
        info[34] = String.valueOf(pc.get_other().get_PvP_magic_r());
        
        info[35] = String.valueOf(pc.get_other().get_Ran_PvP_dmg_b());
        info[36] = String.valueOf(pc.get_other().getPvP_dmg_b());
        info[37] = String.valueOf(pc.get_other().get_Ran_PvP_magic_b());
        info[38] = String.valueOf(pc.get_other().getPvP_magic_b());

        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "pcmenaother", info));
    }
}