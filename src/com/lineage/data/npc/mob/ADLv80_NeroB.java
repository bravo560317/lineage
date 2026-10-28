package com.lineage.data.npc.mob;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class ADLv80_NeroB extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(ADLv80_NeroB.class);
    }
    
    public static NpcExecutor get() {
        return new ADLv80_NeroB();
    }
    
    @Override
    public int type() {
        return 8;
    }
    
    @Override
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        try {
            L1PcInstance pc = CheckUtil.checkAtkPc(lastAttacker);
            if (pc != null && pc.getMapId() == 1011) {
                for (L1Object obj : World.get().getVisibleObjects(1011).values()) {
                    if (obj instanceof L1DoorInstance) {
                        L1DoorInstance door = (L1DoorInstance)obj;
                        if (door.get_showId() == pc.get_showId() && door.getDoorId() == 10009) {
                            door.open();
                            door.deleteMe();
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            ADLv80_NeroB._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
