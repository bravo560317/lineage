package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.BroadcastController;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class BroadcastCard extends ItemExecutor
{
    private BroadcastCard() {
    }
    
    public static ItemExecutor get() {
        return new BroadcastCard();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (BroadcastController.getInstance().isStop()) {
            pc.sendPackets(new S_SystemMessage("廣播系統目前已被管理員關閉。"));
            if (pc.isGm()) {
                pc.sendPackets(new S_SystemMessage("需要重新開啟廣播系統請輸入  \"開啟\"。"));
                pc.setSkillEffect(8008, 30000);
            }
            return;
        }
        else {
            pc.sendPackets(new S_SystemMessage("請在30秒內輸入想要廣播的訊息 (目前已有 " + BroadcastController.getInstance().getMsgSize() + "筆訊息等候廣播)"));
            pc.setSkillEffect(8008, 30000);
            return;
        }
    }
}
