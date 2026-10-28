package com.lineage.data.item_etcitem.extra;

import com.lineage.server.templates.L1BookConfig;
import com.lineage.server.serverpackets.S_Bookmarks;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigAlt;
import com.lineage.server.datatables.lock.CharBookConfigReading;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class MemoryExpansionGem extends ItemExecutor
{
    private MemoryExpansionGem() {
    }
    
    public static ItemExecutor get() {
        return new MemoryExpansionGem();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        L1BookConfig bookConfig = CharBookConfigReading.get().get(pc.getId());
        if (bookConfig != null) {
            if (bookConfig.getMaxSize() >= ConfigAlt.CHAR_BOOK_MAX_CHARGE * 10) {
                pc.sendPackets(new S_ServerMessage(2962));
                return;
            }
            else {
                pc.getInventory().removeItem(item, 1L);
                int expansion_count = 10;
                bookConfig.setMaxSize(bookConfig.getMaxSize() + 10);
                CharBookConfigReading.get().updateCharBookMaxSize(pc.getId(), bookConfig.getMaxSize());
                pc.sendPackets(new S_Bookmarks(ConfigAlt.CHAR_BOOK_INIT_COUNT + bookConfig.getMaxSize()));
            }
        }
    }
}
