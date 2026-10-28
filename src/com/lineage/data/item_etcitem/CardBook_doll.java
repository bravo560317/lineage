package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class CardBook_doll extends ItemExecutor
{
    private static CardBook_doll _instance;
    
    public static ItemExecutor get() {
        return new CardBook_doll();
    }
    
    public static CardBook_doll getInstance() {
        if (CardBook_doll._instance == null) {
            CardBook_doll._instance = new CardBook_doll();
        }
        return CardBook_doll._instance;
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
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_01_doll"));
            return;
        }
    }
}
