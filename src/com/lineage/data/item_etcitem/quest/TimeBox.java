package com.lineage.data.item_etcitem.quest;

import java.sql.Timestamp;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class TimeBox extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(TimeBox.class);
    }
    
    public static ItemExecutor get() {
        return new TimeBox();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            CreateNewItem.createNewItem(pc, 56214, 1L);
            CreateNewItem.createNewItem(pc, 56215, 1L);
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            item.setLastUsed(ts);
            pc.getInventory().updateItem(item, 32);
            pc.getInventory().saveItem(item, 32);
        }
        catch (Exception e) {
            TimeBox._log.error(e.getLocalizedMessage(), e);
        }
    }
}
