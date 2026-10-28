package com.lineage.data.item_etcitem.extra;

import com.add.system.CardPolySet_doll;
import com.add.system.ACard_doll;
import com.add.system.CardSetTable_doll;
import com.lineage.server.serverpackets.S_SkillSound;
import com.add.system.ACardTable_doll;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Cards_doll7 extends ItemExecutor
{
    private int _quest;
    private int _gfx;
    private static Cards_doll7 _instance;
    
    public static ItemExecutor get() {
        return new Cards_doll7();
    }
    
    public static Cards_doll7 getInstance() {
        if (Cards_doll7._instance == null) {
            Cards_doll7._instance = new Cards_doll7();
        }
        return Cards_doll7._instance;
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else if (pc.isPrivateShop()) {
            pc.sendPackets(new S_SystemMessage("擺攤狀態下 無法使用"));
            return;
        }
        else if (pc.isFishing()) {
            pc.sendPackets(new S_SystemMessage("釣魚狀態下 無法使用"));
            return;
        }
        else {
            boolean ok = false;
            int i = 0;
            while (i <= ACardTable_doll.get().ACardSize()) {
                ACard_doll card = ACardTable_doll.get().getCard(i);
                if (card != null && card.getQuestId() == this._quest) {
                    if (pc.getQuest().get_step(card.getQuestId()) == 0) {
                        pc.getQuest().set_step(card.getQuestId(), 1);
                        pc.getInventory().removeItem(item, 1L);
                        pc.sendPackets(new S_SystemMessage("你已成功登錄此娃娃卡片 請重新登入獲得能力加成 並享有此變身能力"));
                        pc.sendPacketsAll(new S_SkillSound(pc.getId(), this._gfx));
                        ok = true;
                        break;
                    }
                    else {
                        pc.sendPackets(new S_SystemMessage("無法重複登錄娃娃卡片"));
                        break;
                    }
                }
                else {
                    ++i;
                }
            }
            if (ok) {
                for (i = 0; i <= CardSetTable_doll.get().CardCardSize(); ++i) {
                    CardPolySet_doll cards = CardSetTable_doll.get().getCard(i);
                    if (cards != null) {
                        int k = 0;
                        for (int j = 0; j < cards.getNeedQuest().length; ++j) {
                            if (pc.getQuest().get_step(cards.getNeedQuest()[j]) != 0) {
                                ++k;
                            }
                        }
                        if (k == cards.getNeedQuest().length) {
                            pc.getQuest().set_step(cards.getQuestId(), 1);
                            pc.sendPackets(new S_SystemMessage("恭喜你啟動此娃娃卡套裝卡能力"));
                        }
                    }
                }
            }
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._quest = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._gfx = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
    }
}
