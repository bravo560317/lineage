package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.william.ItemOutburst;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ItemFire extends ItemExecutor
{
    private ItemFire() {
    }
    
    public static ItemExecutor get() {
        return new ItemFire();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else {
            L1ItemInstance AiITEM1 = pc.getInventory().findItemId(92508);
            if (AiITEM1 != null) {
                pc.getInventory().removeItem(92508);
            }
            try {
                Thread.sleep(1L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            pc.getInventory().storeItem(92508, 1L);
            if (pc.getOutbur()) {
                ItemOutburst.falseOutburst(pc, item);
                pc.setOutbur(false);
                pc.sendPackets(new S_ServerMessage("關閉[爆氣]"));
                pc.sendPackets(new S_ServerMessage("目前爆氣值:" + pc.getQuburcount()));
            }
            else if (pc.getQuburcount() == 0) {
                pc.setOutbur(false);
                pc.sendPackets(new S_ServerMessage("\\fU爆氣值不足無法開啟"));
                return;
            }
            else {
                ItemOutburst.trueOutburst(pc, item);
                pc.setOutbur(true);
                pc.sendPackets(new S_ServerMessage("開啟[爆氣]"));
                pc.sendPackets(new S_ServerMessage("目前爆氣值:" + pc.getQuburcount()));
            }
            return;
        }
    }
}
