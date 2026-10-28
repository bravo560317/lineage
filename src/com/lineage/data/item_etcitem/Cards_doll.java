package com.lineage.data.item_etcitem;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.add.system.CardPolySet_doll;
import com.add.system.ACard_doll;
import com.add.system.CardSetTable_doll;
import com.lineage.server.serverpackets.S_SkillSound;
import com.add.system.ACardTable_doll;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.SQLUtil;

public class Cards_doll extends ItemExecutor {

    private int _quest;
    private int _gfx;
    private static Cards_doll _instance;

    public static ItemExecutor get() {
        return new Cards_doll();
    }

    public static Cards_doll getInstance() {
        if (_instance == null) {
            _instance = new Cards_doll();
        }
        return _instance;
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {

        if (item == null || pc == null) {
            return;
        }

        if (pc.isPrivateShop()) {
            pc.sendPackets(new S_SystemMessage("擺攤狀態下 無法使用"));
            return;
        }

        if (pc.isFishing()) {
            pc.sendPackets(new S_SystemMessage("釣魚狀態下 無法使用"));
            return;
        }

        boolean ok = false;

        for (int i = 0; i <= ACardTable_doll.get().ACardSize(); i++) {

            ACard_doll card = ACardTable_doll.get().getCard(i);
            if (card == null) {
                continue;
            }

            // ★★★ 正確方法名稱：getQuestId() ★★★
            if (card.getQuestId() != this._quest) {
                continue;
            }

            // 尚未登錄
            if (pc.getQuest().get_step(card.getQuestId()) == 0) {

                pc.getQuest().set_step(card.getQuestId(), 1);
                pc.getInventory().removeItem(item, 1);

                pc.sendPackets(new S_SystemMessage("你已成功登錄此娃娃卡片 請重新登入獲得能力加成 並享有此變身能力"));
                pc.sendPacketsAll(new S_SkillSound(pc.getId(), this._gfx));

                ok = true;

                // ★★★ 新增：記錄 item_name 到 note ★★★
                saveCardNameToQuest(pc, card.getQuestId(), item.getItem().getName());

                break;
            }

            pc.sendPackets(new S_SystemMessage("無法重複登錄娃娃卡片"));
            return;
        }

        // 啟動套裝卡
        if (ok) {
            for (int i = 0; i <= CardSetTable_doll.get().CardCardSize(); i++) {

                CardPolySet_doll set = CardSetTable_doll.get().getCard(i);
                if (set == null) {
                    continue;
                }

                int okCount = 0;

                for (int questId : set.getNeedQuest()) {
                    if (pc.getQuest().get_step(questId) != 0) {
                        okCount++;
                    }
                }

                if (okCount == set.getNeedQuest().length) {
                    // ★★★ 同樣正確方法：getQuestId() ★★★
                    pc.getQuest().set_step(set.getQuestId(), 1);
                    pc.sendPackets(new S_SystemMessage("恭喜你啟動此娃娃卡套裝卡能力"));
                }
            }
        }
    }

    @Override
    public void set_set(String[] set) {
        try {
            _quest = Integer.parseInt(set[1]);
        } catch (Exception ignored) {}

        try {
            _gfx = Integer.parseInt(set[2]);
        } catch (Exception ignored) {}
    }

    private void saveCardNameToQuest(L1PcInstance pc, int questId, String itemName) {

        Connection con = null;
        PreparedStatement pstm = null;

        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement(
                    "UPDATE character_quests SET note=? WHERE char_id=? AND quest_id=?");

            pstm.setString(1, itemName);
            pstm.setInt(2, pc.getId());
            pstm.setInt(3, questId);

            pstm.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
