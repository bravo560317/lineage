package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class CardBook extends ItemExecutor
{
    private static CardBook _instance;
    
    public static ItemExecutor get() {
        return new CardBook();
    }
    
    public static CardBook getInstance() {
        if (CardBook._instance == null) {
            CardBook._instance = new CardBook();
        }
        return CardBook._instance;
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
            pc.setCarId(-1);
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_01"));
            return;
        }
    }
}
