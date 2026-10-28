package com.lineage.data.item_etcitem.extra;

import java.util.Iterator;
import com.lineage.server.serverpackets.S_DragonDoor;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.config.ConfigAlt;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class DragonKey extends ItemExecutor
{
    public static ItemExecutor get() {
        return new DragonKey();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int castleId = L1CastleLocation.getCastleIdByArea(pc);
        if (castleId > 0 && ServerWarExecutor.get().isNowWar(castleId)) {
            pc.sendPackets(new S_ServerMessage(1892));
            return;
        }
        else {
            boolean isChecked = false;
            Iterator<?> localIterator1 = ConfigAlt.DRAGON_KEY_MAP_LIST.iterator();
            while (localIterator1.hasNext()) {
                int mapid = (Integer)localIterator1.next();
                if (mapid == pc.getMapId()) {
                    isChecked = true;
                    break;
                }
            }
            if (!isChecked) {
                pc.sendPackets(new S_ServerMessage(1892));
                return;
            }
            else {
                int a = 6;
                int b = 6;
                int c = 6;
                int d = 0;
                for (L1Object object : World.get().getObject()) {
                    if (object instanceof L1NpcInstance) {
                        L1NpcInstance npc = (L1NpcInstance)object;
                        if (npc.getNpcTemplate().get_npcId() == 70932) {
                            --a;
                        }
                        else if (npc.getNpcTemplate().get_npcId() == 70937) {
                            --b;
                        }
                        else if (npc.getNpcTemplate().get_npcId() == 70934) {
                            --c;
                        }
                        else {
                            continue;
                        }
                    }
                }
                pc.sendPackets(new S_DragonDoor(item.getId(), a, b, c, d));
                return;
            }
        }
    }
}
