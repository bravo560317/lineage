package com.lineage.data.item_etcitem.quest;

import java.util.Iterator;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import java.util.HashMap;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class DeLv50Key extends ItemExecutor
{
    private DeLv50Key() {
    }
    
    public static ItemExecutor get() {
        return new DeLv50Key();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (!pc.isDarkelf()) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            HashMap<Integer, L1Object> mapList = new HashMap();
            mapList.putAll(World.get().getVisibleObjects(306));
            int i = 0;
            for (L1Object tgobj : mapList.values()) {
                if (tgobj instanceof L1NpcInstance) {
                    L1NpcInstance tgnpc = (L1NpcInstance)tgobj;
                    if (tgnpc.getNpcId() == 70905 && tgnpc.get_showId() == pc.get_showId()) {
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
            else if (pc.getMapId() == 306) {
                pc.getInventory().removeItem(item, 1L);
                L1Teleport.teleport(pc, 32591, 32813, (short)306, 5, true);
            }
            else {
                pc.sendPackets(new S_ServerMessage(79));
            }
            mapList.clear();
            return;
        }
    }
}
