package com.lineage.data.item_etcitem.wand;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_DoActionGFX;
import java.util.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Dark_Ante_Branch extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Dark_Ante_Branch();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        Random _random = new Random();
        if (pc.getMap().isUsePainwand()) {
            pc.sendPacketsX8(new S_DoActionGFX(pc.getId(), 17));
            int[] mobArray = { 45008, 45140, 45016, 45021, 45025, 45033, 45099, 45147, 45123, 45130, 45046, 45092, 45138, 45098, 45127, 45143, 45149, 45171, 45040, 45155, 45192, 45173, 45213, 45079, 45144 };
            int rnd = _random.nextInt(mobArray.length);
            L1SpawnUtil.spawn(pc, mobArray[rnd], 0, 300);
            pc.getInventory().removeItem(item, 1L);
        }
        else {
            pc.sendPackets(new S_ServerMessage(79));
        }
    }
}
