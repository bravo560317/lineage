package com.lineage.data.item_etcitem.quest;

import com.lineage.server.model.L1Location;
import java.util.Iterator;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import java.util.HashMap;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class I50_Flute extends ItemExecutor
{
    private I50_Flute() {
    }
    
    public static ItemExecutor get() {
        return new I50_Flute();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getMapId() != 2004) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            HashMap<Integer, L1Object> mapList = new HashMap();
            mapList.putAll(World.get().getVisibleObjects((int)pc.getMapId()));
            int i = 0;
            for (L1Object tgobj : mapList.values()) {
                if (tgobj instanceof L1MonsterInstance) {
                    L1MonsterInstance tgnpc = (L1MonsterInstance)tgobj;
                    if (pc.get_showId() != tgnpc.get_showId()) {
                        continue;
                    }
                    else if (tgnpc.getNpcId() == 45026) {
                        ++i;
                    }
                    else {
                        continue;
                    }
                }
            }
            if (i > 0) {
                pc.sendPackets(new S_ServerMessage(79));
            }
            else {
                L1Location loc = pc.getLocation().randomLocation(5, false);
                pc.sendPacketsXR(new S_EffectLocation(loc, 7004), 8);
                L1MonsterInstance mob = L1SpawnUtil.spawnX(45026, loc, pc.get_showId());
                mob.setLink(pc);
                pc.getInventory().removeItem(item, 1L);
            }
            mapList.clear();
            return;
        }
    }
}
