package com.lineage.data.item_etcitem.extra;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.SQLUtil;

public class EarringsExpansionGem extends ItemExecutor {

    private EarringsExpansionGem() {
    }

    public static ItemExecutor get() {
        return new EarringsExpansionGem();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {

        // 若已擴充過
        if (pc.getQuest().get_step(58001) == 1) {
            pc.sendPackets(new S_ServerMessage("無法再擴充耳環欄位。"));
            return;
        }

        // 移除道具
        pc.getInventory().removeItem(item, 1L);

        // 設定 quest_step（核心處理 character_quests.quest_step）
        pc.getQuest().set_step(58001, 1);

        // ★★ 寫入 note 欄位（耳環擴充石）★★
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement(
                "UPDATE character_quests SET note = ? WHERE char_id = ? AND quest_id = ?"
            );
            pstm.setString(1, "耳環擴充石");
            pstm.setInt(2, pc.getId());
            pstm.setInt(3, 58001);
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }

        // 返回 UI
        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "slot9"));
    }
}
