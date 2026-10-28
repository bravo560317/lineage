package com.lineage.data.item_etcitem;

import java.util.Iterator;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.world.WorldMob;
import com.lineage.server.serverpackets.S_Sound;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Silver_Flute extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Silver_Flute();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.sendPacketsX8(new S_Sound(10));
        if (pc.getX() >= 32619 && pc.getX() <= 32623 && pc.getY() >= 33120 && pc.getY() <= 33124 && pc.getMapId() == 440) {
            boolean found = false;
            for (L1MonsterInstance mob : WorldMob.get().all()) {
                if (mob != null && mob.getNpcTemplate().get_npcId() == 45875) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                L1SpawnUtil.spawn(pc, 45875, 0, 0);
            }
        }
    }
}
