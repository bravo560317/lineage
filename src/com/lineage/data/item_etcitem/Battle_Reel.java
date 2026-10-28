package com.lineage.data.item_etcitem;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.ServerBasePacket;

public class Battle_Reel extends ItemExecutor {

    public static ItemExecutor get() {
        return new Battle_Reel();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc == null || item == null) {
            return;
        }

        final int SKILL_ID = 8060;
        final int DURATION = 3600000; // 1小時
        final int ANIMATION_ID = 6995; // 特效圖案 ID

        if (!pc.hasSkillEffect(SKILL_ID)) {
            pc.setSkillEffect(SKILL_ID, DURATION);
            pc.getInventory().removeItem(item, 1L);

            pc.addHitup(3);
            pc.addDmgup(3);
            pc.addBowHitup(3);
            pc.addBowDmgup(3);
            pc.addSp(3);

            pc.sendPackets(new S_SPMR(pc));
            pc.sendPacketsAll(new S_SkillSound(pc.getId(), ANIMATION_ID)); // ✅新增特效
        } else {
            int time = pc.getSkillEffectTimeSec(SKILL_ID);
            pc.sendPackets(new S_ServerMessage("戰鬥強化卷軸 剩餘時間(秒):" + time));
        }
    }
}
