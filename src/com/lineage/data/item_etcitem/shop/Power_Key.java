package com.lineage.data.item_etcitem.shop;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemBoxTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Power_Key extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Power_Key.class);
    }
    
    public static ItemExecutor get() {
        return new Power_Key();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            int itemobj = data[0];
            L1ItemInstance tgitem = pc.getInventory().getItem(itemobj);
            if (tgitem == null) {
                return;
            }
            else {
                int itemid = item.getItemId();
                if (tgitem.getItem().getType() == 16) {
                    if (ItemBoxTable.get().is_key(tgitem.getItemId(), itemid)) {
                        if (pc.getInventory().removeItem(item, 1L) != 1L) {
                            return;
                        }
                        else {
                            ItemBoxTable.get().get_key(pc, tgitem, itemid);
                        }
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(79));
                    }
                }
            }
        }
        catch (Exception e) {
            Power_Key._log.error(e.getLocalizedMessage(), e);
        }
    }
}
