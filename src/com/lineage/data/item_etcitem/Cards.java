package com.lineage.data.item_etcitem;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.add.system.CardPolySet;
import com.add.system.ACard;
import com.add.system.CardSetTable;
import com.lineage.server.serverpackets.S_SkillSound;
import com.add.system.ACardTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.SQLUtil;

public class Cards extends ItemExecutor {

    private int _quest;
    private int _gfx;
    private static Cards _instance;

    public static ItemExecutor get() {
        return new Cards();
    }

    public static Cards getInstance() {
        if (_instance == null) {
            _instance = new Cards();
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

        int i = 0;
        while (i <= ACardTable.get().ACardSize()) {
            ACard card = ACardTable.get().getCard(i);

            if (card != null && card.getQuestId() == this._quest) {

                if (pc.getQuest().get_step(card.getQuestId()) == 0) {

                    pc.getQuest().set_step(card.getQuestId(), 1);
                    pc.getInventory().removeItem(item, 1L);

                    pc.sendPackets(new S_SystemMessage("你已成功登錄此卡片 請重新登入獲得能力加成 並享有此變身能力"));
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), this._gfx));

                    ok = true;

                    // ★★★ 寫入 note ★★★
                    saveCardNameToQuest(pc, card.getQuestId(), item.getItem().getName());

                    break;
                } else {
                    pc.sendPackets(new S_SystemMessage("無法重複登錄卡片"));
                    break;
                }
            } else {
                i++;
            }
        }

        if (ok) {
            for (i = 0; i <= CardSetTable.get().CardCardSize(); i++) {
                CardPolySet cards = CardSetTable.get().getCard(i);

                if (cards != null) {
                    int k = 0;
                    for (int j = 0; j < cards.getNeedQuest().length; j++) {
                        if (pc.getQuest().get_step(cards.getNeedQuest()[j]) != 0) {
                            k++;
                        }
                    }

                    if (k == cards.getNeedQuest().length) {
                        pc.getQuest().set_step(cards.getQuestId(), 1);
                        pc.sendPackets(new S_SystemMessage("恭喜你啟動此變身卡套裝卡能力"));
                    }
                }
            }
        }
    }

    @Override
    public void set_set(String[] set) {
        try {
            this._quest = Integer.parseInt(set[1]);
        } catch (Exception ex) {}

        try {
            this._gfx = Integer.parseInt(set[2]);
        } catch (Exception ex2) {}
    }

    // ★★★ 新增：寫入卡片名稱到 note ★★★
    private void saveCardNameToQuest(L1PcInstance pc, int questId, String itemName) {

        Connection con = null;
        PreparedStatement pstm = null;

        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement(
                "UPDATE character_quests SET note=? WHERE char_id=? AND quest_id=?"
            );

            pstm.setString(1, itemName);
            pstm.setInt(2, pc.getId());
            pstm.setInt(3, questId);

            int count = pstm.executeUpdate();
            SQLUtil.close(pstm);

            // 若該任務紀錄不存在 → 自動新增
            if (count == 0) {
                pstm = con.prepareStatement(
                    "INSERT INTO character_quests (char_id, quest_id, quest_step, note, mob_count, clean) " +
                    "VALUES (?, ?, 1, ?, 0, 0)"
                );
                pstm.setInt(1, pc.getId());
                pstm.setInt(2, questId);
                pstm.setString(3, itemName);
                pstm.execute();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
