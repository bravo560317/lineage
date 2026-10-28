package com.lineage.data.item_etcitem;

import java.util.Iterator;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Fuel extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Fuel();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        for (L1Object object : World.get().getVisibleObjects(pc, 3)) {
            if (object instanceof L1EffectInstance && ((L1NpcInstance)object).getNpcTemplate().get_npcId() == 81170) {
                pc.sendPackets(new S_ServerMessage(1162));
                return;
            }
        }
        int[] loc = new int[2];
        loc = pc.getFrontLoc();
        L1SpawnUtil.spawnEffect(81170, 600, loc[0], loc[1], pc.getMapId(), null, 0);
        pc.getInventory().removeItem(item, 1L);
    }
}
