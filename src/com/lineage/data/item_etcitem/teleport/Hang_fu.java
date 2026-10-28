package com.lineage.data.item_etcitem.teleport;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.L1Trade;
import com.lineage.server.Shutdown;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.timecontroller.server.ServerRestartTimer;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Hang_fu extends ItemExecutor
{
    private Hang_fu() {
    }
    
    public static ItemExecutor get() {
        return new Hang_fu();
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
            return;
        }
        else if (pc.isFishing()) {
            return;
        }
        else if (ServerRestartTimer.isRtartTime()) {
            pc.sendPackets(new S_SystemMessage("伺服器即將關機 無法使用"));
            return;
        }
        else if (Shutdown.SHUTDOWN) {
            pc.sendPackets(new S_SystemMessage("伺服器即將關機 無法使用"));
            return;
        }
        else {
            if (pc.getTradeID() != 0) {
                L1Trade trade = new L1Trade();
                trade.tradeCancel(pc);
            }
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "AI"));
            return;
        }
    }
}
