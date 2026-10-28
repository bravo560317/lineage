package com.lineage.data.item_etcitem.quest;

import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.L1Location;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class OrcEmissaryFlute extends ItemExecutor
{
    public static ItemExecutor get() {
        return new OrcEmissaryFlute();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getMapId() != 61) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            L1Location loc = pc.getLocation().randomLocation(2, false);
            pc.sendPacketsXR(new S_EffectLocation(loc, 3992), 8);
            L1MonsterInstance mob = L1SpawnUtil.spawnX(84005, loc, pc.get_showId());
            mob.setLink(pc);
            pc.getInventory().removeItem(item);
            return;
        }
    }
}
