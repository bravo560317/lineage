package com.lineage.data.item_etcitem.quest;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Ham extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Ham();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            if (item == null) {
                return;
            }
            else if (pc == null) {
                return;
            }
            else if (pc.hasSkillEffect(52788)) {
                int time = pc.getSkillEffectTimeSec(52788) / 60;
                pc.sendPackets(new S_ServerMessage("距離下次開啟漢的袋子(分):" + time));
                return;
            }
            else {
                pc.setSkillEffect(52788, 21600000);
                pc.getInventory().storeItem(92479, 1L);
                pc.sendPackets(new S_ServerMessage("獲得:冷冽的氣息"));
            }
        }
        catch (Exception ex) {}
    }
}
