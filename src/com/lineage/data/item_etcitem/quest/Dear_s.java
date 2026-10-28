package com.lineage.data.item_etcitem.quest;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Dear_s extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Dear_s();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "dear_s"));
    }
}
