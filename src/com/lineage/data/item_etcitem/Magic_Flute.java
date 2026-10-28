package com.lineage.data.item_etcitem;

import java.util.Iterator;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1GuardianInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_Sound;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Magic_Flute extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Magic_Flute();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.sendPacketsX8(new S_Sound(165));
        for (L1Object visible : pc.getKnownObjects()) {
            if (visible instanceof L1GuardianInstance) {
                L1GuardianInstance guardian = (L1GuardianInstance)visible;
                if (guardian.getNpcTemplate().get_npcId() == 70850 && CreateNewItem.createNewItem(pc, 88, 1L)) {
                    pc.getInventory().removeItem(item, 1L);
                }
                else {
                    continue;
                }
            }
        }
    }
}
