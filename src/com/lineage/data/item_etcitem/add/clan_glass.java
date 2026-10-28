package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class clan_glass extends ItemExecutor
{
    private int a;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(clan_glass.class);
    }
    
    private clan_glass() {
    }
    
    public static ItemExecutor get() {
        return new clan_glass();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getClanid() == 0) {
            pc.sendPackets(new S_ServerMessage("\\fY您尚未加入血盟無法使用"));
            return;
        }
        else {
            pc.setPcContribution(pc.getPcContribution() + this.a);
            pc.sendPackets(new S_SystemMessage("獲得" + this.a + "點血盟能量值。"));
            pc.getInventory().removeItem(item, 1L);
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        this.a = Integer.parseInt(set[1]);
    }
}
