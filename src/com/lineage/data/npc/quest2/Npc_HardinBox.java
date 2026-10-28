package com.lineage.data.npc.quest2;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_HardinBox extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_HardinBox.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_HardinBox();
    }
    
    @Override
    public int type() {
        return 8;
    }
    
    @Override
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        try {
            if (npc.get_quest_id() > 0) {
                ArrayList<L1Character> targetList = npc.getHateList().toTargetArrayList();
                if (!targetList.isEmpty()) {
                    for (L1Character cha : targetList) {
                        if (cha instanceof L1PcInstance) {
                            L1PcInstance pc = (L1PcInstance)cha;
                            if (pc.getMapId() == 9101) {
                                CreateNewItem.createNewItem(pc, npc.get_quest_id(), 1L);
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            Npc_HardinBox._log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
}
