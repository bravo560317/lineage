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

public class RingsExpansionGem76 extends ItemExecutor {

    private RingsExpansionGem76() {
    }

    public static ItemExecutor get() {
        return new RingsExpansionGem76();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {

        // 已擴充過
        if (pc.getQuest().get_step(58003) == 1) {
            pc.sendPackets(new S_ServerMessage("無法再擴充Lv.76戒指耳環欄位。"));
            return;
        }

        // 移除道具
        pc.getInventory().removeItem(item, 1L);

        // 設定 quest_step（由核心系統寫入 character_quests.quest_step）
        pc.getQuest().set_step(58003, 1);

        // ★★ 正式寫入 note ★★
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement(
                "UPDATE character_quests SET note = ? WHERE char_id = ? AND quest_id = ?"
            );
            pstm.setString(1, "Lv.76戒指擴充石");
            pstm.setInt(2, pc.getId());
            pstm.setInt(3, 58003);
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }

        // 彈出 UI 視窗
        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "slot9"));
    }
}
