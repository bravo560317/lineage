package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class bossroomglass extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(bossroomglass.class);
    }
    
    private bossroomglass() {
    }
    
    public static ItemExecutor get() {
        return new bossroomglass();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getQuest().get_step(90558) != 1) {
            pc.sendPackets(new S_SystemMessage("今日尚未進入武道館無法使用此道具。"));
            return;
        }
        else if (pc.getQuest().get_step(90559) == 2) {
            pc.sendPackets(new S_SystemMessage("每日此藥水限定使用1灌。"));
            return;
        }
        else {
            pc.getQuest().set_step(90559, 1);
            pc.sendPackets(new S_SystemMessage("您可在進入武道館闖關額外多一次。"));
            pc.getInventory().removeItem(item, 1L);
            return;
        }
    }
}
